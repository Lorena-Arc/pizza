package pizzashop.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentRepositoryTestWithMockito {

    @Mock
    private PaymentRepository paymentRepository;

    @Test
    public void testGetAll() {
        Payment payment = new Payment(2, PaymentType.Card, 10.0);
        when(paymentRepository.getAll()).thenReturn(List.of(payment));
        List<Payment> resultList = paymentRepository.getAll();

        assertEquals(resultList.size(), 1);
        assertEquals(resultList, List.of(payment));
    }

    @Test
    public void testAddPayment() {
        Payment payment = new Payment(1, PaymentType.Cash, 11.0);
        when(paymentRepository.getAll()).thenReturn(List.of(payment));
        Mockito.doNothing().when(paymentRepository).add(payment);
        paymentRepository.add(payment);
        List<Payment> resultList = paymentRepository.getAll();

        assertEquals(resultList.size(), 1);
        assertEquals(resultList, List.of(payment));
    }
}