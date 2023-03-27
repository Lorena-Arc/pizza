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

import static org.junit.jupiter.api.Assertions.*;

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
    @DisplayName("Test for checking a payment")
    @Tag("payment")
    void addPaymentSuccessfully() {
        Payment payment = new Payment(1, PaymentType.Cash, 19.3);
        System.out.println(payment.getTableNumber());
        pizzaService.addPayment(payment.getTableNumber(), payment.getType(), payment.getAmount());
        assertEquals(19.3, payment.getAmount(), "Amount should be 19.3");
        assertTrue(payment.getTableNumber() <= 8);
        assertEquals(1, payment.getTableNumber(), "Table number should be 7");

    }
    @ParameterizedTest
    @Order(2)
    @ValueSource(ints = {1,2,3,4,5,6,7,8})
    @DisplayName("Test for table numbers")
    void tableNumberRange(int i){
        Payment payment = new Payment(i, PaymentType.Cash, 19.3);
        pizzaService.addPayment(payment.getTableNumber(), payment.getType(), payment.getAmount());
        assertTrue(i >= 1 && i <= 8);
    }


    @Test
    @Order(4)
    @DisplayName("Test for invalid table number")
    void addPaymentWithInvalidTableNo(){
        //table number = 9
        Payment payment = new Payment(9, PaymentType.Cash, 19.3);
        System.out.println(payment.getTableNumber());
        pizzaService.addPayment(payment.getTableNumber(), payment.getType(), payment.getAmount());
        assertFalse(payment.getTableNumber()>0 && payment.getTableNumber()<9);

        //table number = 0
        Payment payment1 = new Payment(0, PaymentType.Cash, 19.3);
        System.out.println(payment1.getTableNumber());
        pizzaService.addPayment(payment1.getTableNumber(), payment1.getType(), payment1.getAmount());
        assertFalse(payment1.getTableNumber()>0 && payment1.getTableNumber()<9);
    }

    @Test
    @Order(3)
    @Timeout(1)
    @DisplayName("Test for invalid amount")
    void addPaymentWithInvalidAmount(){
        //amount = 0
        Payment payment = new Payment(7, PaymentType.Cash, 0.0);
        pizzaService.addPayment(payment.getTableNumber(), payment.getType(), payment.getAmount());
        assertFalse(payment.getAmount() > 0);

        //amount < 0
        Payment payment1 = new Payment(7, PaymentType.Cash, -0.1);
        pizzaService.addPayment(payment1.getTableNumber(), payment1.getType(), payment1.getAmount());
        assertFalse(payment1.getAmount() > 0);
    }
}