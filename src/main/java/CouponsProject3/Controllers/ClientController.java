package CouponsProject3.Controllers;
import CouponsProject3.Beans.Company;
import CouponsProject3.Beans.Customer;
import CouponsProject3.Exceptions.AuthorizationException;
import CouponsProject3.Exceptions.NotExistException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public abstract class ClientController {
    private final ArrayList<String> activeTokens;


    public ClientController(ArrayList<String> activeTokens) {
        this.activeTokens = activeTokens;
    }

    public String login(String email, String password) throws NotExistException, AuthorizationException {
        return null;
    }

    public String createToken(ClientController role) throws AuthorizationException {
        String name, email, clientRole;
        if (role instanceof AdminController) {
            name = "Admin";
            email = "admin@admin.com";
            clientRole = "Admin";
        } else if (role instanceof CompanyController) {
            Company company = ((CompanyController) role).getCompanyDetails();
            name = company.getName();
            email = company.getEmail();
            clientRole = "Company";
        } else {
            Customer customer = ((CustomerController) role).getCustomerDetails();
            name = customer.getFirstName() + " " + customer.getLastName();
            email = customer.getEmail();
            clientRole = "Customer";
        }
        Date currentDate = new Date();
        long expires = currentDate.getTime() + 1_800_000; //  1_800_000 == 30 min
        String token = JWT.create()
                .withIssuer("Coupons_company")
                .withIssuedAt(new Date())
                .withClaim("username", name)
                .withClaim("email", email)
                .withClaim("role", clientRole)
//                .withClaim("rememberMe", true)
                .withExpiresAt(Instant.ofEpochSecond(expires)) //  define expire date of token after 30 min with no any request.
                .sign(Algorithm.none());

        // + GWT.io - search in google to decode the encryption of the token.
        // + the number after the name of the algorithm is defined the size of the characters in the encryption, as big so hard to crack
        activeTokens.add(token);
//        TODO: consider using "remember me" option as ".withClaim()" ask GPT how.
//        TODO: every request for the server the expire time need to get 30 more minutes.
//        TODO: you can use object: "ScheduledExecutorService" to remove expired tokens.
        return token;
    }

}
