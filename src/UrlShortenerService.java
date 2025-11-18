import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class UrlShortenerService {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String baseUrl;
    private final String token;

    public UrlShortenerService() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        this.objectMapper = new ObjectMapper();
        this.baseUrl = Config.getBitlyBaseUrl();
        this.token = Config.getBitlyToken();
    }

    public String shortenUrl(String longUrl) {
        try {
            if (token == null || token.equals("YOUR_BITLY_ACCESS_TOKEN_HERE")) {
                throw new Exception("Не установлен Bitly токен в config.properties");
            }

            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("long_url", longUrl);
            requestBody.put("domain", "bit.ly");

            String jsonBody = objectMapper.writeValueAsString(requestBody);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/shorten"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + token)
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200 || response.statusCode() == 201) {
                ShortenResponse shortenResponse = objectMapper.readValue(response.body(), ShortenResponse.class);
                return shortenResponse.getShortUrl();
            } else {
                throw new Exception("Ошибка API: " + response.statusCode() + " - " + response.body());
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка сокращения URL: " + e.getMessage());
        }
    }
}