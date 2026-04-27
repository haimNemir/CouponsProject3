package CouponsProject3.Controllers;

import CouponsProject3.Exceptions.AuthorizationException;
import CouponsProject3.Exceptions.NotExistException;
import CouponsProject3.Services.LoginService;
import CouponsProject3.Services.MailgunService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class LoginController {
    private final LoginService service;
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private MailgunService mailgunService;

    public LoginController(LoginService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        @RequestParam String role,
                        @RequestParam boolean rememberMe,
                        HttpServletRequest request) throws AuthorizationException, NotExistException {

        String token = service.login(email, password, role, rememberMe);
        if (token != null) {
            String ip = request.getRemoteAddr();
            String userAgent = request.getHeader("User-Agent");
            String os = getOS(userAgent);
            String browser = getBrowser(userAgent);
            LocalDateTime loginTime = LocalDateTime.now();
            String formattedLoginTime = loginTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm"));
            String location = getGeoLocation(ip);

            String subject = "New user login to coupon projects";
            String notificationNote =
                    "To disable the automatic email notification service, simply delete your Mailgun API key from:\n" +
                            "https://app.mailgun.com/ → Dashboard → API keys → Mailgun API keys. ( https://app.mailgun.com/settings/api_security )\n" +
                            "You can later create a new API key and add it to your application-secret.properties file to re-enable the email service.\n";

            String body = String.format(
                            "שעת התחברות: %s\n" +
                            "מערכת הפעלה: %s\n" +
                            "דפדפן: %s\n" +
                            "מיקום גיאוגרפי: %s\n" +
                            "מייל: %s\n" +
                            "IP: %s\n\n" +
                            "%s",
                    formattedLoginTime, os, browser,
                    location, email, ip, notificationNote
            );


            try {
                mailgunService.sendSimpleEmail("chimnem@gmail.com", subject, body);
                logger.info("Login email sent with user info: {}", body);
            } catch (Exception e) {
                logger.error("❌ Failed to send login notification via Mailgun", e);
            }
        }
        return token;
    }

    @GetMapping("/logout")
    public void logout(@RequestParam String email, @RequestParam String role) {
        service.logout(email, role);
    }

    @GetMapping("/{path:[^\\.]*}")
    public String redirect() {
        return "forward:/index.html";
    }

    private static String getOS(String userAgent) {
        if (userAgent == null) return "Unknown";
        userAgent = userAgent.toLowerCase();
        if (userAgent.contains("windows")) return "Windows";
        if (userAgent.contains("mac")) return "MacOS";
        if (userAgent.contains("x11") || userAgent.contains("linux")) return "Linux";
        if (userAgent.contains("android")) return "Android";
        if (userAgent.contains("iphone") || userAgent.contains("ipad")) return "iOS";
        return "Unknown";
    }

    private static String getBrowser(String userAgent) {
        if (userAgent == null) return "Unknown";
        userAgent = userAgent.toLowerCase();
        if (userAgent.contains("firefox")) return "Firefox";
        if (userAgent.contains("edg")) return "Edge";
        if (userAgent.contains("chrome") && !userAgent.contains("edg")) return "Chrome";
        if (userAgent.contains("safari") && !userAgent.contains("chrome")) return "Safari";
        return "Unknown";
    }

    // Using free API ipapi.co - up to 30,000 requests/month free
    private static String getGeoLocation(String ip) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "https://ipapi.co/" + ip + "/json/";
            Map<String, Object> map = restTemplate.getForObject(url, Map.class);
            if (map == null) return "לא ידוע";
            String city = (String) map.getOrDefault("city", "לא ידוע");
            String country = (String) map.getOrDefault("country_name", "לא ידוע");
            if (city == null || city.trim().isEmpty()) city = "לא ידוע";
            if (country == null || country.trim().isEmpty()) country = "לא ידוע";
            return city + " - " + country;
        } catch (Exception e) {
            return "לא ידוע";
        }
    }
}
