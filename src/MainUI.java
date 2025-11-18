import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class MainUI extends JFrame {
    private final UrlShortenerService urlService;
    private final List<UrlPair> urlHistory;

    // Компоненты UI
    private JTextField urlField;
    private JButton shortenButton;
    private JTextArea resultArea;
    private JPanel historyPanel;
    private JLabel statusLabel;

    // Цвета кнопок
    private final Color PRIMARY_BUTTON_COLOR = new Color(70, 130, 180);    // Синий
    private final Color SUCCESS_BUTTON_COLOR = new Color(34, 139, 34);     // Зеленый
    private final Color HISTORY_BUTTON_COLOR = new Color(100, 100, 100);   // Серый

    // Размеры кнопок
    private final Dimension BUTTON_SIZE = new Dimension(120, 35);

    public MainUI() {
        urlService = new UrlShortenerService();
        urlHistory = new ArrayList<>();

        initializeUI();
        setupEventListeners();
    }

    private void initializeUI() {
        setTitle("🔗 Сокращатель Ссылок");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null); // Центрируем окно
        setResizable(false);

        // Устанавливаем красивый вид
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            showError("Ошибка настройки внешнего вида: " + e.getMessage());
        }

        createMainPanel();
    }

    private void createMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(Color.WHITE);

        // Заголовок
        JLabel titleLabel = new JLabel("Сокращатель Ссылок", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 100, 200));
        titleLabel.setBorder(new EmptyBorder(0, 0, 20, 0));

        // Панель ввода
        JPanel inputPanel = createInputPanel();

        // Панель результата
        JPanel resultPanel = createResultPanel();

        // Панель истории
        JPanel historyContainer = createHistoryPanel();

        // Статус бар
        statusLabel = new JLabel("Готов к работе");
        statusLabel.setBorder(BorderFactory.createLoweredBevelBorder());

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(resultPanel, BorderLayout.SOUTH);

        JScrollPane historyScroll = new JScrollPane(historyContainer);
        historyScroll.setPreferredSize(new Dimension(600, 200));
        historyScroll.setBorder(BorderFactory.createTitledBorder("📜 История ссылок"));

        add(mainPanel, BorderLayout.NORTH);
        add(historyScroll, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("🔗 Введите URL для сокращения"));

        urlField = new JTextField();
        urlField.setFont(new Font("Arial", Font.PLAIN, 14));
        urlField.setToolTipText("Введите длинную ссылку для сокращения");

        shortenButton = createStyledButton("Сократить", PRIMARY_BUTTON_COLOR);

        panel.add(urlField, BorderLayout.CENTER);
        panel.add(shortenButton, BorderLayout.EAST);

        return panel;
    }

    private JPanel createResultPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("📋 Результат"));

        resultArea = new JTextArea(3, 40);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 14));
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setBackground(new Color(240, 240, 240));

        JScrollPane scrollPane = new JScrollPane(resultArea);

        JButton copyButton = createStyledButton("Копировать", SUCCESS_BUTTON_COLOR);

        copyButton.addActionListener(e -> {
            String text = resultArea.getText();
            if (!text.isEmpty()) {
                StringSelection selection = new StringSelection(text);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(selection, selection);
                JOptionPane.showMessageDialog(this, "Ссылка скопирована в буфер обмена!", "Успех", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(copyButton, BorderLayout.EAST);

        return panel;
    }

    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false); // Убираем стандартную границу
        button.setOpaque(true); // Делаем кнопку полностью непрозрачной
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(BUTTON_SIZE);
        button.setMinimumSize(BUTTON_SIZE);
        button.setMaximumSize(BUTTON_SIZE);

        // Добавляем эффект при наведении
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor);
            }
        });

        return button;
    }

    private JPanel createHistoryPanel() {
        historyPanel = new JPanel();
        historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.Y_AXIS));
        historyPanel.setBackground(Color.WHITE);

        return historyPanel;
    }

    private void setupEventListeners() {
        shortenButton.addActionListener(this::shortenUrlAction);

        // Обработка нажатия Enter в поле ввода
        urlField.addActionListener(this::shortenUrlAction);
    }

    private void shortenUrlAction(ActionEvent e) {
        shortenUrl();
    }

    private void shortenUrl() {
        String longUrl = urlField.getText().trim();

        if (longUrl.isEmpty()) {
            showError("Пожалуйста, введите URL");
            return;
        }

        // Добавляем протокол если нужно
        if (!longUrl.startsWith("http://") && !longUrl.startsWith("https://")) {
            longUrl = "https://" + longUrl;
        }

        // Сохраняем в final переменные для использования в лямбде
        final String finalLongUrl = longUrl;

        // Показываем прогресс
        setStatus("Сокращаем ссылку...");
        shortenButton.setEnabled(false);

        // Запускаем в отдельном потоке чтобы не блокировать UI
        new Thread(() -> {
            try {
                // Убираем избыточную переменную shortUrl
                final String shortUrl = urlService.shortenUrl(finalLongUrl);

                // Обновляем UI в EDT
                SwingUtilities.invokeLater(() -> {
                    resultArea.setText(shortUrl);
                    urlHistory.add(0, new UrlPair(finalLongUrl, shortUrl)); // Добавляем в начало
                    updateHistoryPanel();
                    setStatus("Ссылка успешно сокращена!");
                    urlField.setText("");
                    shortenButton.setEnabled(true);
                });

            } catch (Exception ex) {
                final String errorMessage = ex.getMessage();
                SwingUtilities.invokeLater(() -> {
                    showError(errorMessage);
                    setStatus("Ошибка сокращения");
                    shortenButton.setEnabled(true);
                });
            }
        }).start();
    }

    private void updateHistoryPanel() {
        historyPanel.removeAll();

        if (urlHistory.isEmpty()) {
            JLabel emptyLabel = new JLabel("История пуста");
            emptyLabel.setFont(new Font("Arial", Font.ITALIC, 14));
            emptyLabel.setForeground(Color.GRAY);
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            historyPanel.add(emptyLabel);
        } else {
            for (UrlPair pair : urlHistory) {
                historyPanel.add(createHistoryItem(pair));
                historyPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Отступ
            }
        }

        historyPanel.revalidate();
        historyPanel.repaint();
    }

    private JPanel createHistoryItem(UrlPair pair) {
        JPanel itemPanel = new JPanel(new BorderLayout(5, 5));
        itemPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        itemPanel.setBackground(new Color(250, 250, 250));
        itemPanel.setMaximumSize(new Dimension(Short.MAX_VALUE, 60));

        // Короткая ссылка (кликабельная)
        JLabel shortUrlLabel = new JLabel(pair.shortUrl);
        shortUrlLabel.setFont(new Font("Arial", Font.BOLD, 12));
        shortUrlLabel.setForeground(new Color(0, 100, 200));
        shortUrlLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Длинная ссылка
        JLabel longUrlLabel = new JLabel(pair.longUrl);
        longUrlLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        longUrlLabel.setForeground(Color.DARK_GRAY);

        // Кнопка копирования
        JButton copyBtn = createStyledButton("📋 Копировать", HISTORY_BUTTON_COLOR);
        copyBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        copyBtn.setPreferredSize(new Dimension(100, 25));
        copyBtn.setToolTipText("Копировать короткую ссылку");

        copyBtn.addActionListener(e -> copyToClipboard(pair.shortUrl, "Ссылка скопирована: " + pair.shortUrl));

        // Клик по короткой ссылке
        shortUrlLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                try {
                    Desktop.getDesktop().browse(new java.net.URI(pair.shortUrl));
                } catch (Exception ex) {
                    showError("Не удалось открыть ссылку: " + ex.getMessage());
                }
            }
        });

        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setBackground(new Color(250, 250, 250));
        textPanel.add(shortUrlLabel);
        textPanel.add(longUrlLabel);

        itemPanel.add(textPanel, BorderLayout.CENTER);
        itemPanel.add(copyBtn, BorderLayout.EAST);

        return itemPanel;
    }

    private void copyToClipboard(String text, String successMessage) {
        StringSelection selection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
        setStatus(successMessage);
    }

    private void setStatus(String message) {
        statusLabel.setText(" " + message);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Ошибка", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        // Запускаем UI в Event Dispatch Thread
        SwingUtilities.invokeLater(() -> new MainUI().setVisible(true));
    }

    // Вспомогательный класс для хранения пар ссылок
    private static class UrlPair {
        final String longUrl;
        final String shortUrl;

        UrlPair(String longUrl, String shortUrl) {
            this.longUrl = longUrl;
            this.shortUrl = shortUrl;
        }
    }
}