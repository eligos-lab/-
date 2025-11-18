# Используем официальный образ Maven с Java 11
FROM maven:3.8.5-openjdk-11 AS build

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем исходный код
COPY pom.xml .
COPY src ./src
COPY resources ./resources

# Собираем приложение
RUN mvn clean compile assembly:single -DskipTests

# Создаем образ для запуска - используем работающий тег
FROM openjdk:11.0-jre

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем собранный JAR
COPY --from=build /app/target/url-shortener-1.0-SNAPSHOT-jar-with-dependencies.jar app.jar

# Копируем конфигурацию
COPY config.properties .

# Указываем точку входа
ENTRYPOINT ["java", "-jar", "app.jar"]