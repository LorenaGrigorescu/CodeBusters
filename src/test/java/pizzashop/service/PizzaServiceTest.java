package pizzashop.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testing addPayment method...")
class PizzaServiceTest {

    @Test
    @Disabled
    void addPayment() {
    }

    @Mock
    Payment payment;

    Payment payment1 = new Payment(1, PaymentType.CARD, 100);

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private MenuRepository menuRepository;

    private PizzaService pizzaService;

    @BeforeEach
    void init() {
        pizzaService = new PizzaService(menuRepository, paymentRepository);
    }

    @Test
    void addPayment_TestCase1_ECP() {
        for(int _table=5; _table<=7; _table++) {
            int table = _table;
            PaymentType paymentType = PaymentType.CASH;
            double amount = 122.78;
            doNothing().when(paymentRepository).add(any());

            Assertions.assertDoesNotThrow(() -> pizzaService.addPayment(table, paymentType, amount));
        }
            verify(paymentRepository, times(3)).add(any());

    }

    @Test
    void addPayment_TestCase2_ECP() {
        int table = 15;
        PaymentType paymentType = PaymentType.CASH;
        double amount = -122.78;
        Throwable throwable = Assertions.assertThrows(IllegalArgumentException.class, () -> pizzaService.addPayment(table, paymentType, amount));
        Assertions.assertEquals("illegal table number", throwable.getMessage());

    }

    @Test
    void addPayment_TestCase3_BVA() {
        for(int _table=1; _table<=8; _table++) {
            int table = _table;
            PaymentType paymentType = PaymentType.CASH;
            double amount = 122.78;
            doNothing().when(paymentRepository).add(any());
            Assertions.assertDoesNotThrow(() -> pizzaService.addPayment(table, paymentType, amount));
        }
            verify(paymentRepository, times(8)).add(any());

    }

    @Test
    void addPayment_TestCase4_ECP() {
        int table = 9;
        PaymentType paymentType = PaymentType.CASH;
        double amount = -122.78;
        Throwable throwable = Assertions.assertThrows(IllegalArgumentException.class, () -> pizzaService.addPayment(table, paymentType, amount));
        Assertions.assertEquals("illegal table number", throwable.getMessage());
    }

    @Test
    void getTotalAmount_TestCase1() {
        when(paymentRepository.getAll()).thenReturn(getPayments());
        double allPaymentsCard = pizzaService.getTotalAmount("CARD");
        Assertions.assertEquals(500, allPaymentsCard);
    }

    @Test
    void getTotalAmount_TestCase2() {
        Throwable throwable = Assertions.assertThrows(IllegalArgumentException.class, () -> pizzaService.getTotalAmount("CARDD"));
        Assertions.assertEquals("Invalid payment type", throwable.getMessage());
        Mockito.verify(paymentRepository).getAll();
    }

    private List<Payment> getPayments() {
        Payment payment1 = new Payment(1, PaymentType.CARD, 200);
        Payment payment2 = new Payment(7, PaymentType.CARD, 300);
        Payment payment3 = new Payment(6, PaymentType.CASH, 100);

        return Arrays.asList(payment1, payment2, payment3);
    }

    @Test
    void addPayment_Integration_S_R() {
        initialize();
        pizzaService.addPayment(payment1);
        List<Payment> payments = pizzaService.getPayments();
        Assertions.assertEquals(payments.size(), 1);
    }

    @Test
    void getTotalAmount_Integration_S_R() {
        double allPaymentsCard = pizzaService.getTotalAmount("CARD");
        Assertions.assertEquals(0, allPaymentsCard);
    }

    @Test
    void addPayment_Integration_S_R_E() {
        initialize();
        pizzaService.addPayment(payment1);
        List<Payment> payments = pizzaService.getPayments();
        Assertions.assertEquals(payments.size(), 1);
    }

    @Test
    void getTotalAmount_Integration_S_R_E() {
        pizzaService.addPayment(payment1);
        double allPaymentsCard = pizzaService.getTotalAmount("CARD");
        Assertions.assertEquals(0, allPaymentsCard);
    }

    void initialize() {
        paymentRepository = new PaymentRepository("src/test/resources/payments.txt");
        pizzaService = new PizzaService(menuRepository, paymentRepository);
        Path filePath = Paths.get("src/test/resources/payments.txt");
        if (Files.exists(filePath)) return;
        try {
            Files.createDirectories(filePath.getParent());
            Files.createFile(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}