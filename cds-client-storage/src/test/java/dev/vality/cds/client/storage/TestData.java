package dev.vality.cds.client.storage;

import dev.vality.cds.storage.CardData;
import dev.vality.cds.storage.ExpDate;
import dev.vality.damsel.domain.BankCard;
import dev.vality.damsel.domain.BankCardExpDate;
import dev.vality.damsel.domain.DisposablePaymentResource;
import dev.vality.damsel.domain.PaymentTool;
import dev.vality.damsel.proxy_provider.InvoicePayment;
import dev.vality.damsel.proxy_provider.PaymentContext;
import dev.vality.damsel.proxy_provider.PaymentInfo;
import dev.vality.damsel.proxy_provider.PaymentResource;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestData {

    public static final String TOKEN = "some_token";
    public static final String CARDHOLDER_NAME = "CARDHOLDER_NAME";
    public static final String PAN = "1234123412341234";
    public static final byte MONTH = (byte) 4;
    public static final short YEAR = (short) 20;

    public static PaymentContext createPaymentContext() {
        PaymentTool paymentTool = new PaymentTool();
        paymentTool.setBankCard(createBankCardWithExpDate());

        PaymentResource paymentResource = new PaymentResource();
        paymentResource.setDisposablePaymentResource(new DisposablePaymentResource().setPaymentTool(paymentTool));
        return new PaymentContext()
                .setPaymentInfo(new PaymentInfo()
                        .setPayment(new InvoicePayment()
                                .setPaymentResource(paymentResource)
                        )
                );
    }

    public static BankCard createBankCardWithExpDate() {
        return createBankCard()
                .setExpDate(new BankCardExpDate()
                        .setMonth(TestData.MONTH)
                        .setYear(TestData.YEAR));
    }

    public static BankCard createBankCard() {
        return new BankCard()
                .setToken(TestData.TOKEN)
                .setCardholderName(TestData.CARDHOLDER_NAME);
    }

    public static CardData createCardDataWithExpDate() {
        return createCardData().setExpDate(new ExpDate().setMonth(TestData.MONTH)
                .setYear(TestData.YEAR));
    }

    public static CardData createCardData() {
        return new CardData().setPan(TestData.PAN);
    }
}
