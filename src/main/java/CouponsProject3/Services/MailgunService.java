package CouponsProject3.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class MailgunService {

    private static final Logger logger = LoggerFactory.getLogger(MailgunService.class);

    @Value("${mailgun.api-key}")
    private String apiKey;

    @Value("${mailgun.domain}")
    private String domain;

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Send a simple email via Mailgun API.
     * @param to      recipient email address
     * @param subject email subject
     * @param text    email body (plain text)
     */
    public void sendSimpleEmail(String to, String subject, String text) {
        String url = "https://api.mailgun.net/v3/" + domain + "/messages";

        // יצירת Authorization Header
        String auth = "api:" + apiKey;
        String authHeader = "Basic " + Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", authHeader);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("from", "CouponsProject <postmaster@" + domain + ">");
        body.add("to", to);
        body.add("subject", subject);
        body.add("text", text);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                logger.info("✅ Mail sent successfully! Status: {}", response.getStatusCode());
            } else {
                logger.error("❌ Failed to send mail. Status: {}", response.getStatusCode());
                logger.error("❌ Response: {}", response.getBody());
            }
        } catch (HttpClientErrorException e) {
            logger.error("❌ Failed to send mail. Status: {}", e.getStatusCode());
            logger.error("❌ Response: {}", e.getResponseBodyAsString());
            // אתה יכול לבחור אם לזרוק Exception או רק ללוגג
            throw e;
        } catch (Exception e) {
            logger.error("❌ General error occurred: {}", e.getMessage(), e);
            throw e;
        }
    }
}
