package pizzashop.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    @Test
    void testGetType(){
        Payment payment = new Payment(1,PaymentType.CARD,100);
        PaymentType type = payment.getType();
        Assertions.assertEquals(PaymentType.CARD, type);
    }
    @Test
    void testSetType(){
        Payment payment = new Payment(1,PaymentType.CARD,100);
        payment.setType(PaymentType.CASH);
        PaymentType type = payment.getType();
        Assertions.assertEquals(PaymentType.CASH, type);
    }
}