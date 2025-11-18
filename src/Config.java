import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private static final Properties properties = new Properties();

    static {
        try {
            // Пробуем загрузить из разных мест
            String[] configPaths = {
                    "config.properties",
                    "src/main/resources/config.properties",
                    "resources/config.properties"
            };

            boolean loaded = false;
            for (String path : configPaths) {
                try (FileInputStream input = new FileInputStream(path)) {
                    properties.load(input);
                    System.out.println("✅ Конфиг загружен из: " + path);
                    loaded = true;
                    break;
                } catch (IOException e) {
                    // Пробуем следующий путь
                }
            }

            if (!loaded) {
                System.out.println("❌ Файл config.properties не найден!");
                System.out.println("Создайте файл config.properties с вашим Bitly токеном");
            }

        } catch (Exception ex) {
            System.out.println("Ошибка загрузки config.properties: " + ex.getMessage());
        }
    }

    public static String getBitlyToken() {
        String token = properties.getProperty("bitly.token");
        if (token == null || token.equals("YOUR_BITLY_ACCESS_TOKEN_HERE")) {
            System.out.println("⚠️  Не забудьте установить ваш Bitly токен в config.properties!");
        }
        return token;
    }

    public static String getBitlyBaseUrl() {
        return properties.getProperty("bitly.base.url", "https://api-ssl.bitly.com/v4");
    }
}