import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Properties properties = new Properties();

    static {
        try {
            String[] configPaths = {
                    "config.properties",
                    "/app/config.properties",
                    "src/main/resources/config.properties",
                    "resources/config.properties"
            };

            boolean loaded = false;
            for (String path : configPaths) {
                try (InputStream input = new FileInputStream(path)) {
                    properties.load(input);
                    System.out.println("✅ Конфиг загружен из: " + path);
                    loaded = true;
                    break;
                } catch (Exception e) {
                    // Пробуем следующий путь
                }
            }

            if (!loaded) {
                // Пробуем загрузить из classpath
                try (InputStream input = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
                    if (input != null) {
                        properties.load(input);
                        System.out.println("✅ Конфиг загружен из classpath");
                    } else {
                        System.out.println("❌ Файл config.properties не найден!");
                    }
                }
            }

        } catch (Exception ex) {
            System.out.println("Ошибка загрузки config.properties: " + ex.getMessage());
        }
    }

    public static String getBitlyToken() {
        return properties.getProperty("bitly.token");
    }

    public static String getBitlyBaseUrl() {
        return properties.getProperty("bitly.base.url", "https://api-ssl.bitly.com/v4");
    }
}