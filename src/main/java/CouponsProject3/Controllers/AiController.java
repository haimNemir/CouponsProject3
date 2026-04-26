package CouponsProject3.Controllers;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.SessionScope;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@SessionScope
public class AiController {

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.assistant.id}")
    private String assistantId;

    private final RestTemplate restTemplate = new RestTemplate();
    private String threadId;

    @PostConstruct
    private void createThread() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        headers.set("OpenAI-Beta", "assistants=v2");
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                "https://api.openai.com/v1/threads",
                new HttpEntity<>("{}", headers),
                Map.class
        );

        threadId = response.getBody().get("id").toString();
    }

    @PostMapping("/chat")
    public ResponseEntity<String> chat(@RequestBody String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        headers.set("OpenAI-Beta", "assistants=v2");
        headers.setContentType(MediaType.APPLICATION_JSON);

        restTemplate.postForEntity(
                "https://api.openai.com/v1/threads/" + threadId + "/messages",
                new HttpEntity<>(Map.of("role", "user", "content", prompt), headers),
                Map.class
        );

        ResponseEntity<Map> runResponse = restTemplate.postForEntity(
                "https://api.openai.com/v1/threads/" + threadId + "/runs",
                new HttpEntity<>(Map.of("assistant_id", assistantId), headers),
                Map.class
        );

        String runId = runResponse.getBody().get("id").toString();

        String status = "";
        int attempts = 0;
        while (attempts++ < 45) {
            ResponseEntity<Map> runStatus = restTemplate.exchange(
                    "https://api.openai.com/v1/threads/" + threadId + "/runs/" + runId,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    Map.class
            );
            status = runStatus.getBody().get("status").toString();
            if (status.equals("completed")) break;
            if (status.equals("failed") || status.equals("cancelled") || status.equals("expired"))
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Assistant run ended with status: " + status);
            try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        }
        if (!"completed".equals(status))
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body("Assistant run timed out.");

        ResponseEntity<Map> messagesResponse = restTemplate.exchange(
                "https://api.openai.com/v1/threads/" + threadId + "/messages",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                Map.class
        );

        List<Map<String, Object>> messages = (List<Map<String, Object>>) messagesResponse.getBody().get("data");
        Map<String, Object> firstMessage = messages.get(0);
        List<Map<String, Object>> contentList = (List<Map<String, Object>>) firstMessage.get("content");
        Map<String, Object> textObject = (Map<String, Object>) contentList.get(0).get("text");
        String response = textObject.get("value").toString();

        return ResponseEntity.ok(response);
    }
}
