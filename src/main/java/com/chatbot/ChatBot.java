package com.chatbot;

import java.util.Scanner;

/**
 * Main ChatBot application that coordinates different bot personalities.
 * Demonstrates core Java concepts: variables, conditionals, loops, functions, classes, and Collections.
 */
public class ChatBot {
    private Bot currentBot;
    private final Scanner scanner;
    private final boolean running;

    public ChatBot() {
        this.scanner = new Scanner(System.in);
        this.running = true;
        // Start with base Bot, but allow switching between personalities
        this.currentBot = new Bot("ChatBot");
        this.currentBot.initializeDefaultKnowledge();
    }

    /**
     * Main method to run the chatbot application.
     */
    public static void main(String[] args) {
        ChatBot app = new ChatBot();
        app.displayWelcomeMessage();
        app.run();
    }

    /**
     * Display welcome message and available commands.
     */
    private void displayWelcomeMessage() {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║      Welcome to the ChatBot App!      ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("\nI'm " + currentBot.getName() + ", your friendly chatbot!");
        System.out.println("\nAvailable commands:");
        System.out.println("  - Type 'help' to see all commands");
        System.out.println("  - Type 'history' to view chat history");
        System.out.println("  - Type 'topics' to see tracked topics");
        System.out.println("  - Type 'jokebot' to switch to JokeBot");
        System.out.println("  - Type 'gamebot' to switch to GameBot");
        System.out.println("  - Type 'advicebot' to switch to AdviceBot");
        System.out.println("  - Type 'teach me: [question] -> [answer]' to teach me something");
        System.out.println("  - Type 'exit' or 'quit' to end the conversation");
        System.out.println("\nLet's start chatting!\n");
    }

    /**
     * Main conversation loop.
     */
    public void run() {
        while (running) {
            try {
                System.out.print("You: ");
                String userInput = scanner.nextLine();
                
                if (userInput == null) {
                    // Handle EOF (Ctrl+D / Ctrl+Z)
                    System.out.println("\nDetected end of input. Exiting...");
                    handleExit();
                    break;
                }
                
                userInput = userInput.trim();

                if (userInput.isEmpty()) {
                    continue;
                }

                // Process commands
                String command = userInput.toLowerCase();
                
                if (command.equals("exit") || command.equals("quit")) {
                    handleExit();
                    break;
                } else if (command.equals("help")) {
                    displayHelp();
                    continue;
                } else if (command.equals("history")) {
                    displayHistory();
                    continue;
                } else if (command.equals("topics")) {
                    displayTopics();
                    continue;
                } else if (command.equals("jokebot")) {
                    switchToJokeBot();
                    continue;
                } else if (command.equals("gamebot")) {
                    switchToGameBot();
                    continue;
                } else if (command.equals("advicebot")) {
                    switchToAdviceBot();
                    continue;
                } else if (command.startsWith("teach me:")) {
                    handleTeaching(userInput);
                    continue;
                }

                // Get bot response
                String botResponse = currentBot.respond(userInput);
                System.out.println(currentBot.getName() + ": " + botResponse);
                
                // Add to history
                currentBot.addToHistory(userInput, botResponse);
            } catch (Exception e) {
                System.out.println("\nAn error occurred: " + e.getMessage());
                System.out.println("Please try again or type 'exit' to quit.\n");
            }
        }
        
        if (scanner != null) {
            scanner.close();
        }
    }

    /**
     * Display help information.
     */
    private void displayHelp() {
        System.out.println("\n═══════════════ HELP ═══════════════");
        System.out.println("Commands:");
        System.out.println("  help          - Show this help message");
        System.out.println("  history       - View conversation history");
        System.out.println("  topics        - See all tracked conversation topics");
        System.out.println("  jokebot       - Switch to JokeBot (tells jokes)");
        System.out.println("  gamebot       - Switch to GameBot (word games)");
        System.out.println("  advicebot     - Switch to AdviceBot (gives advice)");
        System.out.println("  teach me: ... -> ... - Teach me a new Q&A pair");
        System.out.println("  exit/quit     - End the conversation");
        System.out.println("═════════════════════════════════════\n");
    }

    /**
     * Display chat history.
     */
    private void displayHistory() {
        var history = currentBot.getChatHistory();
        if (history.isEmpty()) {
            System.out.println("No chat history yet. Start a conversation!");
        } else {
            System.out.println("\n═══════════════ CHAT HISTORY ═══════════════");
            for (int i = 0; i < history.size(); i++) {
                System.out.println("[" + (i + 1) + "] " + history.get(i).toString());
            }
            System.out.println("═══════════════════════════════════════════════\n");
        }
    }

    /**
     * Display tracked topics.
     */
    private void displayTopics() {
        var topics = currentBot.getTopics();
        if (topics.isEmpty()) {
            System.out.println("No topics tracked yet.");
        } else {
            System.out.println("\n═══════════════ TRACKED TOPICS ═══════════════");
            for (String topic : topics) {
                System.out.println("  - " + topic);
            }
            System.out.println("═════════════════════════════════════════════════\n");
        }
    }

    /**
     * Handle teaching the bot new information.
     */
    private void handleTeaching(String userInput) {
        try {
            // Format: "teach me: [question] -> [answer]"
            if (userInput.contains("->")) {
                String[] parts = userInput.split("->", 2);
                if (parts.length == 2) {
                    String question = parts[0].replaceFirst("(?i)teach me:", "").trim();
                    String answer = parts[1].trim();
                    
                    // Validate input
                    if (question.isEmpty()) {
                        System.out.println(currentBot.getName() + ": Please provide a question to teach me.");
                        return;
                    }
                    if (answer.isEmpty()) {
                        System.out.println(currentBot.getName() + ": Please provide an answer to teach me.");
                        return;
                    }
                    
                    // Limit length to prevent abuse
                    if (question.length() > 200) {
                        System.out.println(currentBot.getName() + ": Question is too long (max 200 characters).");
                        return;
                    }
                    if (answer.length() > 500) {
                        System.out.println(currentBot.getName() + ": Answer is too long (max 500 characters).");
                        return;
                    }
                    
                    String response = currentBot.learn(question, answer);
                    System.out.println(currentBot.getName() + ": " + response);
                    currentBot.addToHistory(userInput, response);
                    return;
                }
            }
            System.out.println(currentBot.getName() + ": I didn't understand that format. " +
                             "Please use: teach me: [your question] -> [my answer]");
        } catch (Exception e) {
            System.out.println(currentBot.getName() + ": Sorry, I had trouble learning that: " + 
                             e.getMessage() + ". Please try again.");
        }
    }

    /**
     * Switch to JokeBot personality.
     */
    private void switchToJokeBot() {
        currentBot = new JokeBot();
        System.out.println("\nSwitched to JokeBot! Want to hear a joke? Just ask!\n");
    }

    /**
     * Switch to GameBot personality.
     */
    private void switchToGameBot() {
        currentBot = new GameBot();
        System.out.println("\nSwitched to GameBot! Say 'game' or 'play' to start a word game!\n");
    }

    /**
     * Switch to AdviceBot personality.
     */
    private void switchToAdviceBot() {
        currentBot = new AdviceBot();
        System.out.println("\nSwitched to AdviceBot! Ask me for advice on programming, studying, time management, or career!\n");
    }

    /**
     * Handle exit and save data.
     */
    private void handleExit() {
        System.out.println("\nSaving chat history...");
        currentBot.saveChatHistory("chat_history.txt");
        System.out.println("Chat history saved to chat_history.txt");
        System.out.println("\nThanks for chatting! Goodbye!\n");
    }
}

