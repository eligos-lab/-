<div align="center">
  <!-- Colored HTML hero block -->
  <div style="background:linear-gradient(90deg,#0f172a,#0ea5a4);padding:18px;border-radius:12px;color:#fff;max-width:900px;margin:0 auto;">
    <h1 style="margin:0;padding:0;font-size:28px">URL Shortener</h1>
    <p style="margin:6px 0 0; opacity:0.95">Сокращение ссылок • Локальный Java UI</p>
  </div>

  <!-- Badges -->
  <p style="margin-top:12px">
    <img alt="license" src="https://img.shields.io/badge/license-MIT-brightgreen" />
    <img alt="java" src="https://img.shields.io/badge/Java-17%2B-blue" />
    <img alt="maven" src="https://img.shields.io/badge/Maven-✔-darkblue" />
    <img alt="build" src="https://img.shields.io/badge/build-pass-success" />
  </p>
</div>

---

## ✨ Ключевые возможности

- 🔗 Моментальное сокращение URL
- 🪟 Графический интерфейс на Java Swing (MainUI.java)
- ⚙️ Конфигурация через `config.properties`
- 🧩 Модульная архитектура: сервисы, модели, UI

---

## 🧭 Структура проекта

```
--main/                         # Загруженный архив проекта
  ├─ config.properties          # Настройки приложения
  ├─ pom.xml                    # Maven-конфигурация
  └─ src/
      ├─ ClickStats.java
      ├─ Config.java
      ├─ MainUI.java
      ├─ ShortenResponse.java
      └─ UrlShortenerService.java
```

---

## 🛠️ Установка и запуск

### Сборка (локально)

```bash
# Клонировать (или распаковать архив)
# git clone https://github.com/eligos-lab/-.git
# cd url-shortener

mvn clean package
java -jar target/url-shortener-1.0.jar
```

---

## 🧩 Архитектура

```
  [MainUI] -- вызывает --> [UrlShortenerService] -- обращается --> [Public Shortener API]
                             |
                             --> парсит ответ -> ShortenResponse
                             --> обновляет локальную модель ClickStats
```

---

## 🧪 Тесты

```bash
mvn test
```

---


## 📄 License

MIT — свободное использование и модификация.

---

## 👤 Author

**Тузов Дамир (ИТ/3-23)**
