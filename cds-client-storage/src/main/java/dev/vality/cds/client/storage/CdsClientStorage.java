package dev.vality.cds.client.storage;

import dev.vality.cds.client.storage.exception.CdsStorageException;
import dev.vality.cds.client.storage.utils.BankCardExtractor;
import dev.vality.cds.storage.CardData;
import dev.vality.cds.storage.SessionData;
import dev.vality.cds.storage.StorageSrv;
import dev.vality.damsel.domain.BankCard;
import dev.vality.damsel.domain.DisposablePaymentResource;
import dev.vality.damsel.proxy_provider.PaymentContext;
import dev.vality.damsel.proxy_provider.PaymentResource;
import dev.vality.damsel.proxy_provider.RecurrentTokenContext;
import dev.vality.damsel.withdrawals.domain.Destination;
import dev.vality.damsel.withdrawals.provider_adapter.Withdrawal;
import dev.vality.java.cds.utils.model.CardDataProxyModel;
import dev.vality.java.damsel.utils.extractors.ProxyProviderPackageExtractors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import static dev.vality.java.damsel.utils.extractors.ProxyProviderPackageExtractors.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class CdsClientStorage {

    private final StorageSrv.Iface storageSrv;

    @Cacheable("cardData")
    public CardData getCardData(String token) {
        log.info("Get card data by token: {}", token);
        try {
            return storageSrv.getCardData(token);
        } catch (TException ex) {
            throw new CdsStorageException(String.format("Can't get card data with token: %s", token), ex);
        }
    }

    public CardDataProxyModel getCardData(PaymentContext context,
                                          dev.vality.damsel.proxy_provider.PaymentResource paymentResource) {
        if (paymentResource.isSetDisposablePaymentResource()) {
            String cardToken = ProxyProviderPackageExtractors.extractBankCardToken(paymentResource);
            CardData cardData = getCardData(cardToken);
            BankCard bankCard = ProxyProviderPackageExtractors.extractBankCard(context);
            return BankCardExtractor.initCardDataProxyModel(bankCard, cardData);
        }
        return getCardData(context);
    }

    public CardDataProxyModel getCardData(PaymentContext context) {
        CardData cardData = getCardData(extractBankCardToken(extractPaymentResource(context)));
        BankCard bankCard = extractBankCard(context.getPaymentInfo());
        return BankCardExtractor.initCardDataProxyModel(bankCard, cardData);
    }

    public CardDataProxyModel getCardData(Withdrawal withdrawal) {
        Destination destination = withdrawal.getDestination();
        if (!destination.isSetBankCard()) {
            throw new CdsStorageException("Token must be set for card data, withdrawalId " + withdrawal.getId());
        }
        BankCard bankCard = destination.getBankCard();
        return BankCardExtractor.initCardDataProxyModel(bankCard, getCardData(bankCard.getToken()));
    }

    public CardDataProxyModel getCardData(RecurrentTokenContext context) {
        DisposablePaymentResource disposablePaymentResource = extractDisposablePaymentResource(context);
        if (!disposablePaymentResource.isSetPaymentSessionId()) {
            throw new CdsStorageException("Session Id must be set, recurrentId " + extractRecurrentId(context));
        }
        BankCard bankCard = extractBankCard(context);
        return BankCardExtractor.initCardDataProxyModel(bankCard,
                getCardData(extractBankCardToken(disposablePaymentResource)));
    }

    public CardDataProxyModel getCardData(PaymentResource paymentResource) {
        if (!paymentResource.isSetDisposablePaymentResource()
                && !paymentResource.getDisposablePaymentResource().getPaymentTool().isSetBankCard()) {
            throw new CdsStorageException("Exception when getCardData CdsClientStorage!");
        }
        BankCard bankCard = paymentResource.getDisposablePaymentResource().getPaymentTool().getBankCard();
        return BankCardExtractor.initCardDataProxyModel(bankCard, getCardData(bankCard.getToken()));
    }

    public SessionData getSessionData(PaymentContext context) {
        DisposablePaymentResource disposablePaymentResource = extractDisposablePaymentResource(context);
        if (!disposablePaymentResource.isSetPaymentSessionId()) {
            throw new CdsStorageException("Session must be set for session data, invoiceId " +
                    extractInvoiceId(context));
        }
        return getSessionDataBySessionId(disposablePaymentResource.getPaymentSessionId());
    }

    public SessionData getSessionData(RecurrentTokenContext context) {
        DisposablePaymentResource disposablePaymentResource = extractDisposablePaymentResource(context);
        if (!disposablePaymentResource.isSetPaymentSessionId()) {
            throw new CdsStorageException("Session Id must be set, recurrentId " + extractRecurrentId(context));
        }
        return getSessionDataBySessionId(disposablePaymentResource.getPaymentSessionId());
    }

    @Cacheable("sessionData")
    public SessionData getSessionDataBySessionId(String sessionId) {
        try {
            return storageSrv.getSessionData(sessionId);
        } catch (TException ex) {
            throw new CdsStorageException("Can't get session data by session Id " + sessionId, ex);
        }
    }
}
