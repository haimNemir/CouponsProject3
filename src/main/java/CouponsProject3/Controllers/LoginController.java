package CouponsProject3.Controllers;

import CouponsProject3.Exceptions.AuthorizationException;
import CouponsProject3.Exceptions.NotExistException;
import CouponsProject3.Services.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/users")
public class LoginController {
    private final LoginService service;
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class); // LoggerFactory.getLogger(LoginController.class) - gets the object Logger, and we enter inside the brackets the name of the class we want to save in the log, so we will know where the log was written


    public LoginController(LoginService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, @RequestParam String role, @RequestParam boolean rememberMe, HttpServletRequest request) throws AuthorizationException, NotExistException {
        String token = service.login(email, password, role, rememberMe);
        if (token != null){
            String ip = request.getRemoteAddr(); // show from witch IP user logged in.
            String userAgent = request.getHeader("User-Agent"); //show details on the user like if is android or widows.
            String referrer = request.getHeader("Referer"); // show from witch address user login.
            LocalDateTime loginTime = LocalDateTime.now();
            logger.info("User logged in \nIP: {} \nAgent: {} \nReferrer: {} \nLoginTime: {}\n\n",
                    ip, userAgent, referrer, loginTime);
        }
        return token;
    }

    @GetMapping("/logout")
    public void logout(@RequestParam String email, @RequestParam String role) {
        service.logout(email, role);
//        activeTokens.remove(email + role); // +role - represent the role type of the client to prevent from company with email similar to a customer email to do "logout" to the customer instead the company.
    }

    //TODO: check this method if its necessary
    @GetMapping("/{path:[^\\.]*}") // קולט כל בקשה חוץ מקבצים סטטיים עם נקודה (כמו .css, .js)
    public String redirect() {
        return "forward:/index.html";
    }
}
