package CouponsProject3;
import CouponsProject3.Exceptions.AlreadyExistException;
import CouponsProject3.Exceptions.ExpiredDateException;
import CouponsProject3.Exceptions.NotExistException;
import CouponsProject3.Exceptions.OutOfStockException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Main.class, args);
        System.out.println("starting");
        TestAll testAll = context.getBean(TestAll.class);
        System.out.println("App started");
        try {
             testAll.test();
        } catch (NotExistException | AlreadyExistException |  OutOfStockException | ExpiredDateException | RuntimeException e) {
            testAll.getCurrentThread().interrupt(); // prevent where we get exceptions in the middle of the proses to continue with "job" forever.
            throw new RuntimeException(e.getMessage());
        }
    }

    @Bean
    public List<String> activeTokens(){
        return new ArrayList<String>();
    }
}
