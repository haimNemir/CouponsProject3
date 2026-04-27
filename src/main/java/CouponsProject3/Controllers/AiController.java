package CouponsProject3.Controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.SessionScope;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@SessionScope
public class AiController {

    private static final String SYSTEM_PROMPT = """
            You are an AI assistant integrated into a demo website created to showcase full-stack development skills. The website allows users to purchase discount coupons from well-known companies.

            The site has three types of clients:

            Administrator (ADMIN):

            Logs in using fixed credentials (email: admin@admin.com, password: admin).

            Can add new companies and customers. Companies and customers can log in only after being added by the Admin.

            Can update existing companies and customers, except their IDs and company names.

            Can delete companies (including their coupons and all purchase histories) and customers (including all their coupon purchase histories).

            Companies and customers cannot modify their own user details.

            Company:

            Logs in with email and password validated against the database.

            Can create new coupons but cannot use duplicate coupon titles within the same company (other companies may use identical coupon titles).

            Can update coupons but cannot change coupon ID or associated company ID.

            Deleting a coupon also removes all purchase histories related to that coupon.

            Customer:

            Logs in with email and password validated against the database.

            Can purchase coupons but cannot:

            Purchase the same coupon more than once.

            Purchase a coupon if its quantity is zero or if its expiration date has passed.

            Receive a refund for unused expired coupons.

            After purchase, the coupon stock quantity decreases by one.

            Technologies used:

            Backend: Java with Spring JPA and MySQL.

            Frontend: React with TypeScript.

            Deployment: Hosted on AWS with Docker.

            Website Rules:

            Customers can stay logged in for 30 minutes or 8 hours depending on the "Remember Me" option.

            Coupons past their expiration date are automatically deleted daily, and no refunds are provided for expired, unused coupons.

            When responding to users, answer briefly and match the language used by the user.

            The next message you'll receive is from a client using this website. Please respond accordingly.
            """;

    @Value("${openai.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private String previousResponseId;

    @SuppressWarnings("unchecked")
    @PostMapping("/chat")
    public ResponseEntity<String> chat(@RequestBody String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("model", "gpt-4o");
        body.put("instructions", SYSTEM_PROMPT);
        body.put("input", prompt);
        if (previousResponseId != null)
            body.put("previous_response_id", previousResponseId);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                "https://api.openai.com/v1/responses",
                new HttpEntity<>(body, headers),
                Map.class
        );

        previousResponseId = response.getBody().get("id").toString();

        List<Map<String, Object>> output = (List<Map<String, Object>>) response.getBody().get("output");
        List<Map<String, Object>> content = (List<Map<String, Object>>) output.get(0).get("content");
        String text = content.get(0).get("text").toString();

        return ResponseEntity.ok(text);
    }
}