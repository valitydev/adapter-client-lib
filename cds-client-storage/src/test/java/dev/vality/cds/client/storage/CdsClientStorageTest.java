package dev.vality.cds.client.storage;

import dev.vality.cds.storage.CardData;
import dev.vality.cds.storage.SessionData;
import dev.vality.cds.storage.StorageSrv;
import dev.vality.damsel.proxy_provider.PaymentContext;
import dev.vality.damsel.withdrawals.domain.Destination;
import dev.vality.damsel.withdrawals.provider_adapter.Withdrawal;
import dev.vality.java.cds.utils.model.CardDataProxyModel;
import org.apache.thrift.TException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(SpringJUnit4ClassRunner.class)
public class CdsClientStorageTest {

    private CdsClientStorage client;

    @Mock
    private SessionData sessionData;

    @Mock
    private StorageSrv.Iface storageSrv;


    @Before
    public void setUp() {
        initMocks(CdsClientStorageTest.class);
        client = new CdsClientStorage(storageSrv);
    }

    @Test
    public void getCardData() throws TException {
        CardData cardData = TestData.createCardData();
        Mockito.when(storageSrv.getCardData(TestData.TOKEN)).thenReturn(cardData);

        PaymentContext context = TestData.createPaymentContext();
        CardDataProxyModel cardDataProxyModel = client.getCardData(context);
        assertEquals(cardData.getPan(), cardDataProxyModel.getPan());
        verify(storageSrv, times(1)).getCardData(eq(TestData.TOKEN));
    }

    @Test
    public void getCardDataWithdrawal() throws TException {
        CardData cardData = TestData.createCardData();
        Mockito.when(storageSrv.getCardData(TestData.TOKEN)).thenReturn(cardData);

        Destination destination = new Destination();
        destination.setBankCard(TestData.createBankCardWithExpDate());
        Withdrawal withdrawal = new Withdrawal()
                .setDestination(destination);

        CardDataProxyModel cardDataProxyModel = client.getCardData(withdrawal);
        assertEquals(TestData.PAN, cardDataProxyModel.getPan());
        assertEquals(TestData.MONTH, cardDataProxyModel.getExpMonth());
        assertEquals(TestData.YEAR, cardDataProxyModel.getExpYear());

        verify(storageSrv, times(1)).getCardData(eq(TestData.TOKEN));
    }

    @Test
    public void getCardDataEmpty() throws TException {
        CardData cardData = new CardData();
        Mockito.when(storageSrv.getCardData(TestData.TOKEN)).thenReturn(cardData);

        PaymentContext context = TestData.createPaymentContext();

        CardDataProxyModel cardDataProxyModel = client.getCardData(context);
        assertEquals(TestData.CARDHOLDER_NAME, cardDataProxyModel.getCardholderName());
        verify(storageSrv, times(1)).getCardData(eq(TestData.TOKEN));
    }

    @Test
    public void getSessionData() throws TException {
        Mockito.when(storageSrv.getSessionData(TestData.TOKEN)).thenReturn(sessionData);

        assertEquals(sessionData, client.getSessionDataBySessionId(TestData.TOKEN));
        verify(storageSrv, times(1)).getSessionData(eq(TestData.TOKEN));
    }

}