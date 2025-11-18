@echo off
chcp 65001 > nul
echo ====================================
echo    🚀 ЗАПУСК СОКРАЩАТЕЛЯ ССЫЛОК
echo ====================================
echo.

echo 🔨 Очистка и компиляция проекта...
call mvn clean compile -q

if %errorlevel% neq 0 (
    echo.
    echo ❌ ОШИБКА: Не удалось скомпилировать проект
    echo.
    echo Проверьте:
    echo 1. Наличие Java 11+
    echo 2. Наличие Maven
    echo 3. Корректность Java файлов в папке src
    pause
    exit /b 1
)

echo ✅ Проект скомпилирован успешно!
echo.

echo 📁 Проверка скомпилированных классов...
dir target\classes\*.class > nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Классы не найдены в target\classes\
    pause
    exit /b 1
)

echo 🚀 Запуск приложения...
java -cp "target/classes;%USERPROFILE%\.m2\repository\com\fasterxml\jackson\core\jackson-databind\2.15.2\jackson-databind-2.15.2.jar;%USERPROFILE%\.m2\repository\com\fasterxml\jackson\core\jackson-core\2.15.2\jackson-core-2.15.2.jar;%USERPROFILE%\.m2\repository\com\fasterxml\jackson\core\jackson-annotations\2.15.2\jackson-annotations-2.15.2.jar" MainUI

echo.
echo ====================================
echo    Приложение завершено
echo ====================================
pause