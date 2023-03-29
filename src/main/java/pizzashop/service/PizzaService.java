package pizzashop.service;

import pizzashop.model.MenuDataModel;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;

import java.util.List;

public class PizzaService {

    private MenuRepository menuRepo;
    private PaymentRepository payRepo;

    public PizzaService(MenuRepository menuRepo, PaymentRepository payRepo){
        this.menuRepo=menuRepo;
        this.payRepo=payRepo;
    }

    public List<MenuDataModel> getMenuData(){return menuRepo.getMenu();}

    public List<Payment> getPayments(){return payRepo.getAll(); }

    public void addPayment(int table, PaymentType type, Double amount){
        Payment payment= new Payment(table, type, amount);
        if(table >=1 && table <= 8 && amount > 0){
            payRepo.add(payment);
        }
        else if(table < 0 || table > 8) {
            System.out.println("Table number should be between 1 and 8");
        }
        else if(amount <= 0) {
            System.out.println("Amount should be a strict positive number");
        }
    }

    public Double getTotalAmount(PaymentType type){
        Double total=0.0d;
        List<Payment> l=getPayments();
        if(type != PaymentType.Card && type != PaymentType.Cash){
            return total;
        }
        if (l == null){
            return total;
        }
        if(l.size() == 0) {
            return total;
        }
        for (Payment p:l){
            if (p.getType().equals(type))
                total+=p.getAmount();
        }
        return total;
    }

}
