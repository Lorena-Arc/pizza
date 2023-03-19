package pizzashop.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;

import static org.junit.jupiter.api.Assertions.*;

class PizzaServiceTest {

    private Payment payment;

    @BeforeEach
    void setUp() {
        int table = 7;
        PaymentType type = PaymentType.Cash;
        double amount = 19.3;
        payment = new Payment(table, type, amount);
    }

    @AfterEach
    void tearDown() {
    }

    @ParameterizedTest
    @ValueSource(ints = {1,2,3,4,5,6,7,8})
    @DisplayName("Test for table numbers")
    void tableNumberRange(int i){
        assertTrue((i>=1) && (i <= 8));
    }

    @Test
    @DisplayName("Test for checking a payment")
    @Tag("payment")
    void addPayment() {
        assertEquals(7, payment.getTableNumber(), "Table number should be 7");
        assertEquals(19.3, payment.getAmount(), "Amount should be 19.3");
    }
}