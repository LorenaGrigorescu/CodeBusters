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

    public PizzaService(MenuRepository menuRepo, PaymentRepository payRepo) {
        this.menuRepo = menuRepo;
        this.payRepo = payRepo;
    }

    public PizzaService() {
    }

    public List<MenuDataModel> getMenuData() {
        return menuRepo.getMenu();
    }

    public List<Payment> getPayments() {
        return payRepo.getAll();
    }

    public void addPayment(int table, PaymentType type, double amount) {
        if (table < 0 || table > 8)
            throw new IllegalArgumentException("illegal table number");
        if (amount < 0)
            throw new IllegalArgumentException("illegal amount");
        Payment payment = new Payment(table, type, amount);
        payRepo.add(payment);
    }
    public void addPayment(Payment payment) {
        if(payment.getTableNumber() < 0 || payment.getTableNumber() > 8)
            throw new IllegalArgumentException("illegal table number");
        if(payment.getAmount() < 0)
            throw new IllegalArgumentException("illegal amount");
        payRepo.addInList(payment);
    }


    public double getTotalAmount(String type) {
        double total = 0.0f;
        List<Payment> l = getPayments();
        PaymentType paymentType;
        try {
            paymentType = PaymentType.valueOf(type);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid payment type");
        }
        paymentType = PaymentType.valueOf(type);
        if ((l == null))
            return total;
        if (l.isEmpty()) return total;
        for (Payment p : l) {
            if (p.getType().equals(paymentType))
                total += p.getAmount();
        }
        return total;


    }

}