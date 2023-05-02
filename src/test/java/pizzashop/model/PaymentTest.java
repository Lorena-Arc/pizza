package pizzashop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    @Test
    void getType() {
        Payment payment = new Payment(1, PaymentType.Card, 12.2);
        assert payment.getType() == PaymentType.Card;
    }

    @Test
    void setType() {
        Payment payment = new Payment(1, PaymentType.Card, 12.2);
        payment.setType(PaymentType.Card);
        assert payment.getType() == PaymentType.Card;
    }
}