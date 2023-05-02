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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class PizzaServiceEIntegrationTest {

    public MenuRepository menuRepo;
    public PaymentRepository payRepo;
    public PizzaService pizzaService;

    @BeforeEach
    public void setUp() throws FileNotFoundException {
        this.setMenuRepo();
        this.setPayRepo();
        pizzaService = new PizzaService(menuRepo, payRepo);
    }

    public void setMenuRepo() {
        menuRepo = new MenuRepository("data/test-menu.txt", null);
    }

    public void setPayRepo() throws FileNotFoundException {
        File file = new File("data/test-payments.txt");
        PrintWriter writer = new PrintWriter(file);
        writer.print("");
        writer.close();

        payRepo = new PaymentRepository("data/test-payments.txt", null);
    }

    @Test
    public void testAddPayment() {
        // when
        this.pizzaService.addPayment(1, PaymentType.Cash, 20.0);
        this.pizzaService.addPayment(2, PaymentType.Card, 30.0);
        this.pizzaService.addPayment(3, PaymentType.Cash, 40.0);

        // then
        List<Payment> payments = pizzaService.getPayments();
        assertEquals(payments.size(), 3);
    }

    @Test
    public void testGetMenuData() {
        // when
        List<MenuDataModel> menuData = pizzaService.getMenuData();

        // then
        assertEquals(menuData.size(), 9);
        assertEquals(menuData.get(0).getPrice(), 7.49);
        assertEquals(menuData.get(1).getPrice(), 8.0);
    }
}
