package CouponsProject3;

import CouponsProject3.Utils.CouponExpirationDailyJob;
import CouponsProject3.Utils.CreateDataInDB;
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
        CreateDataInDB createDataInDB = new CreateDataInDB(context);
        createDataInDB.startCreatingData();
        System.out.println("App started");
    }

    //active token list available for the entire app:
    @Bean
    public HashMap<String, String> activeTokens() {
        return new HashMap<>();
    }
}
//TODO: 1/10 - add AD to your app.
//TODO: 1/10 - add message in the home page about that the website using cookies.
//TODO: 1/10 - remove from all input from the clients a wight space for avoiding exceptions when the client try to enter password or email and thing about all the relevant inputs
//TODO: 4/10 - add button of search coupons
// i was changed the url of the controllers endpoints check if then still works
//TODO: 10/10 - when your refresh the page inside the app we return unauthorized even you have a token.