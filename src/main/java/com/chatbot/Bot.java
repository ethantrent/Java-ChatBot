package com.chatbot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * Base Bot class that provides core chatbot functionality.
 * Uses HashMap for Q&A storage, ArrayList for chat history, and TreeSet for topics.
 */
public class Bot implements Respondable {
    protected String name;
    protected HashMap<String, String> knowledgeBase; // Stores question-answer pairs
    protected ArrayList<Message> chatHistory; // Stores conversation history
    protected TreeSet<String> topics; // Tracks different conversation topics
    protected String dataFile = "chatbot_data.txt"; // File for persisting learned responses

    public Bot(String name) {
        this.name = name;
        this.knowledgeBase = new HashMap<>();
        this.chatHistory = new ArrayList<>();
        this.topics = new TreeSet<>();
        loadKnowledgeBase(); // Load previously learned responses
    }

    /**
     * Initialize the knowledge base with default responses.
     */
    protected void initializeDefaultKnowledge() {
        knowledgeBase.put("hello", "Hello! How can I help you today?");
        knowledgeBase.put("hi", "Hi there! What would you like to talk about?");
        knowledgeBase.put("how are you", "I'm doing great, thanks for asking! How about you?");
        knowledgeBase.put("what is your name", "My name is " + name + ". Nice to meet you!");
        knowledgeBase.put("bye", "Goodbye! It was nice chatting with you!");
        knowledgeBase.put("goodbye", "See you later! Have a great day!");
        
        topics.add("greetings");
        topics.add("introduction");
        topics.add("farewell");
    }

    /**
     * Main response method that processes user input.
     */
    @Override
    public String respond(String userInput) {
        if (userInput == null || userInput.trim().isEmpty()) {
            return "I didn't catch that. Could you please say something?";
        }

        String lowerInput = userInput.toLowerCase().trim();
        
        // Check if we know the answer
        String response = knowledgeBase.get(lowerInput);
        
        if (response != null) {
            return response;
        }

        // Check for partial matches (only if input is at least 3 characters to avoid false matches)
        if (lowerInput.length() >= 3) {
            for (String key : knowledgeBase.keySet()) {
                // Only match if key is at least 3 characters and there's meaningful overlap
                if (key.length() >= 3 && (lowerInput.contains(key) || key.contains(lowerInput))) {
                    return knowledgeBase.get(key);
                }
            }
        }

        // Default response if no match found
        return "That's interesting! Could you tell me more about that, or teach me by saying 'teach me: [your question] -> [my answer]'?";
    }

    /**
     * Learn a new question-answer pair from the user.
     */
    public String learn(String question, String answer) {
        String key = question.toLowerCase().trim();
        knowledgeBase.put(key, answer);
        saveKnowledgeBase(); // Persist to file
        return "Thanks! I've learned that. I'll remember: '" + question + "' -> '" + answer + "'";
    }

    /**
     * Add a message to chat history.
     */
    public void addToHistory(String userInput, String botResponse) {
        Message message = new Message(userInput, botResponse);
        chatHistory.add(message);
    }

    /**
     * Get the chat history.
     */
    public ArrayList<Message> getChatHistory() {
        return chatHistory;
    }

    /**
     * Get all tracked topics.
     */
    public TreeSet<String> getTopics() {
        return topics;
    }

    /**
     * Add a topic to tracking.
     */
    public void addTopic(String topic) {
        topics.add(topic.toLowerCase());
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * Save the knowledge base to a file.
     * Uses a custom delimiter (|||) to avoid conflicts with user data containing pipes.
     */
    protected void saveKnowledgeBase() {
        try (PrintWriter writer = new PrintWriter(dataFile)) {
            for (String key : knowledgeBase.keySet()) {
                // Use ||| as delimiter to avoid conflicts with user data containing pipes
                String escapedKey = key.replace("\n", "\\n").replace("|||", "|");
                String escapedValue = knowledgeBase.get(key).replace("\n", "\\n").replace("|||", "|");
                writer.println(escapedKey + "|||" + escapedValue);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Warning: Could not save knowledge base: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error saving knowledge base: " + e.getMessage());
        }
    }

    /**
     * Load the knowledge base from a file.
     */
    protected final void loadKnowledgeBase() {
        File file = new File(dataFile);
        if (!file.exists()) {
            initializeDefaultKnowledge();
            return;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty() && line.contains("|||")) {
                    String[] parts = line.split("\\|\\|\\|", 2);
                    if (parts.length == 2) {
                        // Unescape newlines and restore pipes
                        String key = parts[0].replace("\\n", "\n").replace("|", "|||");
                        String value = parts[1].replace("\\n", "\n").replace("|", "|||");
                        knowledgeBase.put(key, value);
                    }
                } else if (!line.isEmpty() && line.contains("|")) {
                    // Backward compatibility with old format
                    String[] parts = line.split("\\|", 2);
                    if (parts.length == 2) {
                        knowledgeBase.put(parts[0], parts[1]);
                    }
                }
            }
            // If file was empty or had no valid entries, initialize defaults
            if (knowledgeBase.isEmpty()) {
                initializeDefaultKnowledge();
            }
        } catch (FileNotFoundException e) {
            // File doesn't exist yet, that's fine - use defaults
            initializeDefaultKnowledge();
        } catch (Exception e) {
            System.err.println("Warning: Error loading knowledge base: " + e.getMessage());
            System.err.println("Initializing with default knowledge...");
            initializeDefaultKnowledge();
        }
    }

    /**
     * Save chat history to a file.
     */
    public void saveChatHistory(String filename) {
        try (PrintWriter writer = new PrintWriter(filename)) {
            if (chatHistory.isEmpty()) {
                writer.println("No chat history to save.");
                return;
            }
            writer.println("Chat History - " + java.time.LocalDateTime.now());
            writer.println("Total messages: " + chatHistory.size());
            writer.println("===========================================");
            for (Message message : chatHistory) {
                writer.println(message.toString());
                writer.println("---");
            }
        } catch (FileNotFoundException e) {
            System.err.println("Warning: Could not save chat history: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error saving chat history: " + e.getMessage());
        }
    }
}

