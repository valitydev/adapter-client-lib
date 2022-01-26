package dev.vality.cds.client.storage.utils;

import dev.vality.cds.client.storage.TestData;
import dev.vality.cds.client.storage.exception.CdsStorageExpDateException;
import dev.vality.cds.storage.CardData;
import dev.vality.damsel.domain.BankCard;
import dev.vality.damsel.domain.BankCardExpDate;
import dev.vality.java.cds.utils.model.CardDataProxyModel;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class BankCardExtractorTest {

    @Test(expected = CdsStorageExpDateException.class)
    public void getCardDataProxyModelWithoutExpDate() {
        BankCard bankCard = TestData.createBankCard();
        CardData cardData = TestData.createCardData();
        BankCardExtractor.initCardDataProxyModel(bankCard, cardData);
    }

    @Test
    public void getCardDataProxyModelWithExpDate() {
        // Only bank card with exp date
        BankCard bankCard = TestData.createBankCardWithExpDate();
        CardData cardData = TestData.createCardData();
        CardDataProxyModel cardDataProxyModel = BankCardExtractor.initCardDataProxyModel(bankCard, cardData);

        assertEquals(TestData.YEAR, cardDataProxyModel.getExpYear());
        assertEquals(TestData.MONTH, cardDataProxyModel.getExpMonth());

        // Only card data with exp date
        bankCard = TestData.createBankCard();
        cardData = TestData.createCardDataWithExpDate();
        cardDataProxyModel = BankCardExtractor.initCardDataProxyModel(bankCard, cardData);

        assertEquals(TestData.YEAR, cardDataProxyModel.getExpYear());
        assertEquals(TestData.MONTH, cardDataProxyModel.getExpMonth());

        // Only all with exp date
        bankCard = TestData.createBankCardWithExpDate();
        cardData = TestData.createCardDataWithExpDate();
        cardDataProxyModel = BankCardExtractor.initCardDataProxyModel(bankCard, cardData);

        assertEquals(TestData.YEAR, cardDataProxyModel.getExpYear());
        assertEquals(TestData.MONTH, cardDataProxyModel.getExpMonth());
    }

    @Test
    public void testFakeGenerator() {
        CardDataProxyModel fakeModel1 = BankCardExtractor.initCardDataProxyModel(
                new BankCard().setExpDate(new BankCardExpDate()), new CardData());
        CardDataProxyModel fakeModel2 = BankCardExtractor.initCardDataProxyModel(
                new BankCard().setExpDate(new BankCardExpDate()), new CardData());
        assertNotEquals(fakeModel1.getCardholderName(), fakeModel2.getCardholderName());
    }
}