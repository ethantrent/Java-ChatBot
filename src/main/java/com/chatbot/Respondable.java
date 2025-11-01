package com.chatbot;

/**
 * Interface for bots that can respond to user input.
 * This allows for extensible bot personalities.
 */
public interface Respondable {
    /**
     * Generate a response based on user input.
     * @param userInput The user's message
     * @return The bot's response
     */
    String respond(String userInput);
    
    /**
     * Get the bot's name/personality type.
     * @return The bot's name
     */
    String getName();
}

