package urbantech.business;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;



public class CardValidTest {
    CardPayment visa1;
    CardPayment mastercard1;
    CardPayment visa2;
    CardPayment mastercard2;

    @Before
    public void init() {
        visa1 = new CardPayment();
        mastercard1 = new CardPayment();
        visa2 = new CardPayment();
        mastercard2 = new CardPayment();
    }

    @Test
    public void testCardNumLength() {
        visa1.setCardNum("16733");
        assertTrue(visa1.validLuhn("16733"));
        assertFalse(visa1.validCardNum());
    }

    @Test
    public void testFirstNumber() {
        visa1.setCardNum("3499641710667067");
        assertTrue(visa1.validLuhn("3499641710667067"));
        assertEquals(16, visa1.getCardNum().length());
        assertFalse(visa1.validCardNum());
    }

    @Test
    public void visaCardTest() {
        visa1.setCardNum("4665977683369232");
        visa2.setCardNum("4527607048485814");
        assertEquals(16, visa1.getCardNum().length());
        assertEquals(16, visa2.getCardNum().length());
        assertTrue(visa1.validCardNum());
        assertFalse(visa2.validCardNum());
    }

    @Test
    public void mastercardTest() {
        mastercard1.setCardNum("5453960876334562");
        mastercard2.setCardNum("5299161235715876");
        assertEquals(16, mastercard1.getCardNum().length());
        assertEquals(16, mastercard2.getCardNum().length());
        assertTrue(mastercard1.validCardNum());
        assertTrue(mastercard2.validCardNum());
    }

    @Test
    public void testCVV() {
        mastercard1.setCvv("5987");
        mastercard2.setCvv("862");
        assertFalse(mastercard1.validCVV());
        assertTrue(mastercard2.validCVV());
    }

    @Test
    public void testExpiredDateFormat() {
        mastercard1.setExpireDate("2025");
        assertFalse(mastercard1.validExpireDate());

        mastercard2.setExpireDate("01256");
        assertFalse(mastercard2.validExpireDate());

        visa1.setExpireDate("0525");
        assertTrue(visa1.validExpireDate());
    }

    @Test
    public void testExpiredDateTime() {
        mastercard1.setExpireDate("0123");
        mastercard2.setExpireDate("0423");
        assertFalse(mastercard1.validExpireDate());
        assertTrue(mastercard2.validExpireDate());

        visa1.setExpireDate("0822");
        assertFalse(visa1.validExpireDate());

    }
}
