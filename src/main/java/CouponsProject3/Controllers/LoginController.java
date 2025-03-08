package CouponsProject3.Controllers;

import CouponsProject3.Exceptions.AuthorizationException;
import CouponsProject3.Exceptions.NotExistException;
import CouponsProject3.Utils.ClientType;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/users")
public class LoginController {
    private final HashMap<String, String> activeTokens; // in the first value we save unique email and in the last value we enter Token, if the client re-login it will delete the previous token on his name and save the new one.
    private final AdminController adminController;
    private final CompanyController companyController;
    private final CustomerController customerController;

    public LoginController(HashMap<String, String> activeTokens, AdminController adminController, CompanyController companyController, CustomerController customerController) {
        this.activeTokens = activeTokens;
        this.adminController = adminController;
        this.companyController = companyController;
        this.customerController = customerController;
    }

    @GetMapping("/logout")
    public void logout(@RequestParam String email, @RequestParam String role) {
        //add logic to check the info we get from user.
        activeTokens.remove(email + role); // +role - represent the role type of the client to prevent from company with email similar to a customer email to do "logout" to the customer instead the company.
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, @RequestParam String role, @RequestParam boolean rememberMe) throws AuthorizationException, NotExistException {
        switch (role) {
            case "Administrator" -> {
                if (adminController.login(email, password)) {
                    String emailKeyForList = email + "Administrator"; //  "Admin" - represent Admin in the token list
                    return createToken(ClientType.Administrator, email, emailKeyForList, rememberMe);
                }
            }
            case "Company" -> {
                if (companyController.login(email, password)) {
                    String emailKeyForList = email + "Company";//  "Company" - represent Company in the token list
                    return createToken(ClientType.Company, email, emailKeyForList, rememberMe);
                }
            }
            case "Customer" -> {
                if (customerController.login(email, password)) {
                    String emailKeyForList = email + "Customer";//  "Customer" - represent Customer in the token list
                    return createToken(ClientType.Customer, email, emailKeyForList, rememberMe);
                }
            }
        }
        return null;
    }


    public String createToken(ClientType role, String email, String emailKeyForList, boolean rememberMe) throws AuthorizationException {
        Date date = new Date();
        long expiredTime;
        LocalDate nextMonthDate = LocalDate.now().plusMonths(1);
        long expireTime30min = date.getTime() + 1_800_000; //1_800_000 == 30 min . Returns the number of milliseconds since January 1, 1970, represented by this date + 30 min.
        long expireTime1month = nextMonthDate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli(); // gets the number of milliseconds as long from January 1, 1970, + one month.
        if (!rememberMe) {
            expiredTime = expireTime30min;
        } else {
            expiredTime = expireTime1month;
        }
        String token = JWT.create()
                .withIssuer("Haim_coupons")
                .withIssuedAt(date)
                .withClaim("email", email)
                .withClaim("role", role.toString())
                .withExpiresAt(Instant.ofEpochMilli(expiredTime)) // TODO: write it on notebook: Important! in JWT object, the value "withExpiresAt" always request for milliseconds but will save the data as seconds!! so the Backend will get the value as seconds
                .sign(Algorithm.none());// + the number after the name of the algorithm is defined the size of the characters in the encryption, as big so hard to crack. now there is unencrypted.
        // + GWT.io - search in google to decode the encryption of the token.
        activeTokens.put(emailKeyForList, token);
//        TODO: you can use object: "ScheduledExecutorService" to remove expired tokens.
        return token;
    }

    @GetMapping("/{path:[^\\.]*}") // קולט כל בקשה חוץ מקבצים סטטיים עם נקודה (כמו .css, .js)
    public String redirect() {
        return "forward:/index.html";
    }
}
