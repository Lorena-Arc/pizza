package pizzashop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import pizzashop.model.MenuDataModel;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PizzaServiceRIntegrationTest {

    private MenuRepository menuRepo;
    private PaymentRepository payRepo;
    private PizzaService pizzaService;
    private final List<Payment> payments = new ArrayList<>();
    private final List<MenuDataModel> menuItems = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        this.setMenuRepo();
        this.setPayRepo();
        pizzaService = new PizzaService(menuRepo, payRepo);
    }

    public void setMenuRepo() {
        MenuDataModel menuItem1 = mock(MenuDataModel.class);
        MenuDataModel menuItem2 = mock(MenuDataModel.class);
        menuItems.add(menuItem1);
        menuItems.add(menuItem2);
        menuRepo = new MenuRepository("data/test-menu.txt", menuItems);
    }

    public void setPayRepo() {
        Payment payment1 = mock(Payment.class);
        Payment payment2 = mock(Payment.class);
        payments.add(payment1);
        payments.add(payment2);
        payRepo = new PaymentRepository("data/test-payments.txt", payments);
    }

    @Test
    public void testAddPayment() {
        // given
        int tableNumber = 1;
        PaymentType paymentType = PaymentType.Cash;
        Double amount = 20.0;

        // when
        this.pizzaService.addPayment(tableNumber, paymentType, amount);

        // then
        List<Payment> payments = pizzaService.getPayments();
        assertEquals(payments.size(), 3);
    }

    @Test
    public void testGetMenuData() {
        // given
        when(menuItems.get(0).getQuantity()).thenReturn(5);
        when(menuItems.get(1).getQuantity()).thenReturn(6);

        // when
        List<MenuDataModel> menuData = pizzaService.getMenuData();

        // then
        assertEquals(menuData.size(), 2);
        assertEquals(menuData.get(0).getQuantity(), 5);
        assertEquals(menuData.get(1).getQuantity(), 6);
    }
}
