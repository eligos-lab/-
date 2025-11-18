import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static UrlShortenerService urlService;
    private static Map<String, String> urlMap;
    private static Scanner scanner;

    public static void main(String[] args) {
        initialize();
        showWelcomeMessage();
        runApplication();
    }

    private static void initialize() {
        urlService = new UrlShortenerService();
        urlMap = new HashMap<>();
        scanner = new Scanner(System.in);
    }

    private static void showWelcomeMessage() {
        System.out.println("====================================");
        System.out.println("    СЕРВИС СОКРАЩЕНИЯ ССЫЛОК");
        System.out.println("====================================");
        System.out.println();
    }

    private static void runApplication() {
        while (true) {
            showMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    shortenUrl();
                    break;
                case "2":
                    listUrls();
                    break;
                case "3":
                    System.out.println("Выход из приложения...");
                    return;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }

            System.out.println("\nНажмите Enter для продолжения...");
            scanner.nextLine();
        }
    }

    private static void showMenu() {
        System.out.println("\n=== ГЛАВНОЕ МЕНЮ ===");
        System.out.println("1. Сократить URL");
        System.out.println("2. Показать историю ссылок");
        System.out.println("3. Выход");
        System.out.print("Выберите действие (1-3): ");
    }

    private static void shortenUrl() {
        System.out.print("Введите длинный URL для сокращения: ");
        String longUrl = scanner.nextLine().trim();

        if (longUrl.isEmpty()) {
            System.out.println("URL не может быть пустым.");
            return;
        }

        // Добавляем протокол если нужно
        if (!longUrl.startsWith("http://") && !longUrl.startsWith("https://")) {
            longUrl = "https://" + longUrl;
        }

        System.out.println("Сокращение URL...");
        String shortUrl = urlService.shortenUrl(longUrl);

        if (shortUrl != null) {
            urlMap.put(shortUrl, longUrl);
            System.out.println("✅ URL успешно сокращен!");
            System.out.println("🔗 Сокращенная ссылка: " + shortUrl);
            System.out.println("📎 Исходная ссылка: " + longUrl);
        } else {
            System.out.println("❌ Не удалось сократить URL.");
        }
    }

    private static void listUrls() {
        if (urlMap.isEmpty()) {
            System.out.println("📭 Нет сохраненных сокращенных ссылок.");
            return;
        }

        System.out.println("\n=== ИСТОРИЯ СОКРАЩЕННЫХ ССЫЛОК ===");
        int index = 1;
        for (Map.Entry<String, String> entry : urlMap.entrySet()) {
            System.out.println(index + ". 🔗 " + entry.getKey());
            System.out.println("   📎 " + entry.getValue());
            System.out.println();
            index++;
        }
        System.out.println("Всего ссылок: " + urlMap.size());
    }
}