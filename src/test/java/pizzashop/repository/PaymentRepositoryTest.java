package pizzashop.repository;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

class PaymentRepositoryTest {


    @Nested
    @DisplayName("Isolation testing R")
    class IsolationPaymentRepositoryTest {
        private final PaymentRepository paymentRepository = new PaymentRepository("src/test/resources/payments.txt");

        @Mock
        Payment payment;

        void initializeFile() {
            Path filePath = Paths.get("src/test/resources/payments.txt");
            if (Files.exists(filePath)) return;
            try {
                Files.createFile(filePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Test
        void addTest() throws FileNotFoundException {
            String filename = "src/test/resources/payments.txt";
            initializeFile();
            Payment payment = new Payment(1, PaymentType.CARD, 100);
            paymentRepository.add(payment);

            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    Assertions.assertEquals("1,CARD,100.0", line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            PrintWriter writer = new PrintWriter(filename);
            writer.print("");
            writer.close();
        }

        @Test
        void addInListTest() {
            initializeFile();
            paymentRepository.addInList(payment);
            List<Payment> payments = paymentRepository.getAll();
            Assertions.assertEquals(payments.size(), 1);
        }
    }

}