package com.chatbot;

/**
 * Message class to represent a single message in the chat history.
 * Stores the user's input and the bot's response.
 */
public class Message {
    private String userInput;
    private String botResponse;
    private String timestamp;

    public Message(String userInput, String botResponse) {
        this.userInput = userInput;
        this.botResponse = botResponse;
        this.timestamp = java.time.LocalDateTime.now().toString();
    }

    public String getUserInput() {
        return userInput;
    }

    public String getBotResponse() {
        return botResponse;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "[" + timestamp + "] User: " + userInput + "\nBot: " + botResponse;
    }
}

