package pizzashop.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.PaymentRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class PizzaServiceTestWithMockito {

    @Mock
    PaymentRepository paymentRepository;

    @InjectMocks
    PizzaService pizzaService;

    @Test
    public void getAllTest() {
        Payment payment1 = new Payment(1, PaymentType.Cash, 10.0);
        Payment payment2 = new Payment(2, PaymentType.Card, 11.0);
        List<Payment> paymentList = new ArrayList<>();
        paymentList.add(payment1);
        paymentList.add(payment2);
        Mockito.when(paymentRepository.getAll()).thenReturn(paymentList);
        List<Payment> paymentTestList = pizzaService.getPayments();
        assertEquals(paymentTestList, paymentList);
        assertEquals(paymentTestList.size(), 2);
    }

    @Test
    public void testAddPayment() {
        int tableNumber = 1;
        PaymentType paymentType = PaymentType.Cash;
        Double amount = 20.0;

        this.pizzaService.addPayment(tableNumber, paymentType, amount);

        Mockito.verify(paymentRepository, Mockito.times(1)).add(
                Mockito.argThat(payment ->
                        payment.getTableNumber() == tableNumber &&
                        payment.getType() == paymentType &&
                        payment.getAmount().equals(amount))
        );
    }
}