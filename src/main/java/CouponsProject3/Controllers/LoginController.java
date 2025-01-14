package CouponsProject3.Controllers;

import CouponsProject3.Exceptions.AuthorizationException;
import CouponsProject3.Exceptions.NotExistException;
import CouponsProject3.Utils.ClientType;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("users")
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
        activeTokens.remove(email+role); // +role - represent the role type of the client to prevent from company with email similar to a customer email to do "logout" to the customer instead the company.
    }

    @PostMapping("login")
    public String login(@RequestParam String email, @RequestParam String password, @RequestParam String role) throws AuthorizationException, NotExistException {
        switch (role) {
            case "Administrator" -> {
                if (adminController.login(email, password)) {
                    String emailKeyForList = email + "Administrator"; //  "Admin" - represent Admin in the token list
                    return createToken(ClientType.Administrator, email, emailKeyForList);
                }
            }
            case "Company" -> {
                if (companyController.login(email, password)) {
                    String emailKeyForList = email + "Company";//  "Company" - represent Company in the token list
                    return createToken(ClientType.Company, email, emailKeyForList);
                }
            }
            case "Customer" -> {
                if (customerController.login(email, password)) {
                    String emailKeyForList = email + "Customer";//  "Customer" - represent Customer in the token list
                    return createToken(ClientType.Customer, email, emailKeyForList);
                }
            }
        }
        return null;
    }


    public String createToken(ClientType role, String email, String emailKeyForList) throws AuthorizationException {
        long currentDate = new Date().getTime();
        long expires = currentDate + 1_800_000; //  1_800_000 == 30 min
        String token = JWT.create()
                .withIssuer("Coupons_company")
                .withIssuedAt(Instant.ofEpochSecond(currentDate))
                .withClaim("email", email)
                .withClaim("role", role.toString())
//                .withClaim("rememberMe", true)
                .withExpiresAt(Instant.ofEpochSecond(expires)) //  define expire date of token after 30 min with no any request.
                .sign(Algorithm.none());

        // + GWT.io - search in google to decode the encryption of the token.
        // + the number after the name of the algorithm is defined the size of the characters in the encryption, as big so hard to crack
        activeTokens.put(emailKeyForList, token);

//        TODO: consider using "remember me" option as ".withClaim()" ask GPT how.
//        TODO: every request for the server the expire time need to get 30 more minutes.
//        TODO: you can use object: "ScheduledExecutorService" to remove expired tokens.
        return token;
    }
}
