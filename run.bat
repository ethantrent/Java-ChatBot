@echo off
echo Compiling ChatBot application...
javac -d out src/main/java/com/chatbot/*.java

if %ERRORLEVEL% EQU 0 (
    echo Compilation successful!
    echo.
    echo Running ChatBot...
    echo.
    java -cp out com.chatbot.ChatBot
) else (
    echo Compilation failed!
    pause
)

