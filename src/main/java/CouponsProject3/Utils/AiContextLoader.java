package CouponsProject3.Utils;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class AiContextLoader { // This class use for read the context from /resources/ai-context.txt . The test help to chatbot know more about my app.
    private final String context;

    public AiContextLoader() throws IOException {
        context = new String(
                getClass().getClassLoader().getResourceAsStream("ai-context.txt").readAllBytes(),
                StandardCharsets.UTF_8
        );
    }
    public String getContext() {
        return context;
    }
}
