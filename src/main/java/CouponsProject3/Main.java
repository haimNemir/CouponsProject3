package CouponsProject3;
import CouponsProject3.Utils.CouponExpirationDailyJob;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Main.class, args);
        CouponExpirationDailyJob job = context.getBean(CouponExpirationDailyJob.class);
        job.start();
        System.out.println("App started");
    }

    @Bean
    public HashMap<String, String> activeTokens() {
        return new HashMap<>();
    }
}
//TODO: 1/10 - add AD to your app.
//TODO: 1/10 - add message in the home page about that tae website using cookies.
//TODO: 1/10 - remove from all input from the clients a wight space for avoiding exceptions when the client try to enter password or email and thing about all the relevant inputs
//TODO: 5/10 - add button of dark/light app.
//TODO: 7/10 - in the login page add popup {the admin email is:... and password is: ...  }
//TODO: 4/10 - add button of search coupons