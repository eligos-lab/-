import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShortenResponse {
    @JsonProperty("id")
    private String id;

    @JsonProperty("link")
    private String shortUrl;

    @JsonProperty("long_url")
    private String longUrl;

    @JsonProperty("created_at")
    private String createdAt;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getShortUrl() { return shortUrl; }
    public void setShortUrl(String shortUrl) { this.shortUrl = shortUrl; }

    public String getLongUrl() { return longUrl; }
    public void setLongUrl(String longUrl) { this.longUrl = longUrl; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}