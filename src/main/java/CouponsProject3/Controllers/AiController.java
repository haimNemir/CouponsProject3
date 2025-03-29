package CouponsProject3.Controllers;

import CouponsProject3.Utils.AiContextLoader;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ai")
public class AiController {
    private final OpenAiService openAiService;
    private final AiContextLoader aiContextLoader;

    public AiController(AiContextLoader aiContextLoader, @Value("${openai.api.key}") String apiKey /*Get the value from application properties*/) {
        this.openAiService = new OpenAiService(apiKey);
        this.aiContextLoader = aiContextLoader;
    }

    @PostMapping("/chat")
    public ResponseEntity<String> chat(@RequestBody String prompt) {
        String messageForAiChat = aiContextLoader.getContext();
        String fullPrompt = messageForAiChat + "\n\n" + prompt;
        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(List.of(new ChatMessage("user", fullPrompt)))
                .build();

        ChatCompletionResult result = openAiService.createChatCompletion(request);
        String response = result.getChoices().get(0).getMessage().getContent();

        return ResponseEntity.ok(response);
    }

}
