@echo off
chcp 65001 > nul
echo ====================================
echo    🚀 ЗАПУСК СОКРАЩАТЕЛЯ ССЫЛОК
echo ====================================
echo.

echo 🔨 Компиляция проекта...
call mvn clean compile -q

if %errorlevel% neq 0 (
    echo.
    echo ❌ ОШИБКА: Не удалось скомпилировать проект
    echo Проверьте наличие Java и Maven
    pause
    exit /b 1
)

echo ✅ Проект скомпилирован успешно!
echo.

echo 🚀 Запуск приложения...
java -cp "target/classes;%USERPROFILE%\.m2\repository\com\fasterxml\jackson\core\jackson-core\2.15.2\*.jar;%USERPROFILE%\.m2\repository\com\fasterxml\jackson\core\jackson-databind\2.15.2\*.jar;%USERPROFILE%\.m2\repository\com\fasterxml\jackson\core\jackson-annotations\2.15.2\*.jar" MainUI

echo.
echo ====================================
echo    Приложение завершено
echo ====================================
pause