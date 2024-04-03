package pizzashop.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testing addPayment method...")
class PizzaServiceTest {

    @Test
    @Disabled
    void addPayment() {
    }

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private MenuRepository menuRepository;

    private PizzaService pizzaService;


    @BeforeEach
    void init() {
        pizzaService = new PizzaService(menuRepository, paymentRepository);
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 6, 7})
    void addPayment_TestCase1_ECP(int table) {
        PaymentType paymentType = PaymentType.CASH;
        double amount = 122.78;
        doNothing().when(paymentRepository).add(any());
        pizzaService.addPayment(table, paymentType, amount);
    }

    @Test
    void addPayment_TestCase2_ECP() {
        int table = 15;
        PaymentType paymentType = PaymentType.CASH;
        double amount = -122.78;
        Throwable throwable = Assertions.assertThrows(IllegalArgumentException.class, ()->pizzaService.addPayment(table, paymentType, amount));
        Assertions.assertEquals("illegal table number", throwable.getMessage());

    }

    @ParameterizedTest
    @ValueSource(ints = {1, 8})
    void addPayment_TestCase3_BVA(int table) {
        PaymentType paymentType = PaymentType.CASH;
        double amount = 122.78;
        doNothing().when(paymentRepository).add(any());
        pizzaService.addPayment(table, paymentType, amount);
    }

    @Test
    void addPayment_TestCase4_ECP() {
        int table = 9;
        PaymentType paymentType = PaymentType.CASH;
        double amount = -122.78;
        Throwable throwable = Assertions.assertThrows(IllegalArgumentException.class, ()->pizzaService.addPayment(table, paymentType, amount));
        Assertions.assertEquals("illegal table number", throwable.getMessage());
    }
}