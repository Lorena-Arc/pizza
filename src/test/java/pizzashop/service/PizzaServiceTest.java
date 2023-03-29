package pizzashop.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PizzaServiceTest {

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PizzaService pizzaService;

    @BeforeEach
    void setUp() {
        pizzaService = new PizzaService(menuRepository, paymentRepository);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @Order(1)
    @DisplayName("Test for checking a payment - ECP")
    @Tag("payment")
    void addPaymentSuccessfully() {
        // given
        Payment payment = new Payment(2, PaymentType.Cash, 19.3);
        System.out.println(payment.getTableNumber());

        // when
        pizzaService.addPayment(payment.getTableNumber(), payment.getType(), payment.getAmount());

        // then
        verify(paymentRepository).add(any());
        verifyNoMoreInteractions(paymentRepository);
        assertEquals(19.3, payment.getAmount(), "Amount should be 19.3");
        assertTrue(payment.getTableNumber() <= 8);
        assertEquals(2, payment.getTableNumber(), "Table number should be 2");
    }


    @Test
    @Order(2)
    @DisplayName("Test for invalid table number - ECP")
    void addPaymentWithInvalidTableNoECP() {
        // given
        Payment payment = new Payment(11, PaymentType.Cash, 230d);
        System.out.println(payment.getTableNumber());

        // when
        pizzaService.addPayment(payment.getTableNumber(), payment.getType(), payment.getAmount());

        // then
        verifyNoInteractions(paymentRepository);
        assertFalse(payment.getTableNumber() > 0 && payment.getTableNumber() < 9);
    }

    @Test
    @Order(3)
    @DisplayName("Test for invalid amount number - ECP")
    void addPaymentWithInvalidAmountECP() {
        // given
        Payment payment = new Payment(5, PaymentType.Cash, -60d);
        System.out.println(payment.getTableNumber());

        // when
        pizzaService.addPayment(payment.getTableNumber(), payment.getType(), payment.getAmount());

        // then
        verifyNoInteractions(paymentRepository);
        assertFalse(payment.getAmount() > 0);
    }


    @ParameterizedTest
    @Order(4)
    @ValueSource(ints = {1, 8})
    @DisplayName("Test for valid table number - BVA")
    void tableNumberRange(int i) {
        // given
        Payment payment = new Payment(i, PaymentType.Card, 0.01);

        // when
        pizzaService.addPayment(payment.getTableNumber(), payment.getType(), payment.getAmount());

        // then
        verify(paymentRepository).add(any());
        verifyNoMoreInteractions(paymentRepository);
        assertTrue(i >= 1 && i <= 8);
    }

    @Test
    @Order(5)
    @DisplayName("Test for 0 table number - BVA")
    void addPaymentWith0TableNo() {
        // given
        Payment payment1 = new Payment(0, PaymentType.Cash, 19.3);
        System.out.println(payment1.getTableNumber());

        // when
        pizzaService.addPayment(payment1.getTableNumber(), payment1.getType(), payment1.getAmount());

        // then
        verifyNoInteractions(paymentRepository);
        assertFalse(payment1.getTableNumber() > 0 && payment1.getTableNumber() < 9);
    }

    @Test
    @Order(6)
    @DisplayName("Test for 9 table number - BVA")
    void addPaymentWith9TableNo() {
        // given
        Payment payment = new Payment(9, PaymentType.Card, 0.01);
        System.out.println(payment.getTableNumber());

        // when
        pizzaService.addPayment(payment.getTableNumber(), payment.getType(), payment.getAmount());

        // then
        verifyNoInteractions(paymentRepository);
        assertFalse(payment.getTableNumber() > 0 && payment.getTableNumber() < 9);
    }

    @Test
    @Order(7)
    @Timeout(1)
    @DisplayName("Test for zero amount - BVA")
    void addPaymentWithZeroAmount() {
        // given
        Payment payment = new Payment(7, PaymentType.Cash, 0.0);

        // when
        pizzaService.addPayment(payment.getTableNumber(), payment.getType(), payment.getAmount());

        // then
        verifyNoInteractions(paymentRepository);
        assertFalse(payment.getAmount() > 0);
    }

    @Test
    @Order(8)
    @Timeout(1)
    @DisplayName("Test for negative amount - BVA")
    void addPaymentWithInvalidAmount() {
        // given
        Payment payment1 = new Payment(7, PaymentType.Cash, -0.1);

        // when
        pizzaService.addPayment(payment1.getTableNumber(), payment1.getType(), payment1.getAmount());

        // then
        verifyNoInteractions(paymentRepository);
        assertFalse(payment1.getAmount() > 0);
    }

    @Test
    @Order(9)
    @Timeout(1)
    @DisplayName("Test for positive amount - BVA")
    void addPaymentWithSomePositiveAmount() {
        // given
        Payment payment1 = new Payment(7, PaymentType.Cash, 0.01);

        // when
        pizzaService.addPayment(payment1.getTableNumber(), payment1.getType(), payment1.getAmount());

        // then
        verify(paymentRepository).add(any());
        verifyNoMoreInteractions(paymentRepository);
        assertFalse(payment1.getAmount() < 0);
    }


    //lab03 tests
    @Test
    @DisplayName("Test valid WBT")
    void getTotalAmount0() {
        //given
        Double totalCash, totalCard;

        //when
        totalCash = pizzaService.getTotalAmount(PaymentType.Cash);
        totalCard = pizzaService.getTotalAmount(PaymentType.Card);

        //then
        assertEquals(0, totalCash);
        assertEquals(0, totalCard);
    }

    @Test
    @DisplayName("Test invalid WBT")
    void getTotalAmountSuccessfully() {
        //given
        Double totalCash = 0.0d, totalCard = 0.0d;
        Payment payment1 = new Payment(7, PaymentType.Cash, 0.01);
        Payment payment2 = new Payment(1, PaymentType.Cash, 5.01);
        Payment payment3 = new Payment(1, PaymentType.Cash, 5.01);
        Payment payment4 = new Payment(5, PaymentType.Card, 23.3);
        Payment payment5 = new Payment(8, PaymentType.Card, 14.5);

        //when
        List<Payment> payments = pizzaService.getPayments();
        payments.add(payment1);
        payments.add(payment2);
        payments.add(payment3);
        payments.add(payment4);
        payments.add(payment5);
        when(paymentRepository.getAll()).thenReturn(payments);

        totalCash = pizzaService.getTotalAmount(PaymentType.Cash);
        totalCard = pizzaService.getTotalAmount(PaymentType.Cash);

        //then
        assertEquals(totalCash, 10.03);
        assertTrue(totalCard > 0);
    }

    @Test
    @DisplayName("Test invalid WBT")
    void getTotalAmount00() {
        //given
        Double totalCash, totalCard;

        //when
        totalCash = pizzaService.getTotalAmount(null);
        totalCard = pizzaService.getTotalAmount(null);
        System.out.println(totalCard);
        //then
        assertEquals(totalCash, 0.0);
        assertEquals(totalCard, 0.0);
    }

    @Test
    @DisplayName("Test valid WBT")
    void getTotalAmountNull() {
        //given
        Double totalCash, totalCard;
        List<Payment> payments = null;
        when(paymentRepository.getAll()).thenReturn(payments);

        //when
        totalCash = pizzaService.getTotalAmount(PaymentType.Cash);
        totalCard = pizzaService.getTotalAmount(PaymentType.Card);

        //then
        assertNull(payments);
        assertEquals(0.0, totalCard);
        assertEquals(0.0, totalCash);
    }

}