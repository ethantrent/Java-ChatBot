package com.chatbot;

import java.util.ArrayList;
import java.util.Random;

/**
 * JokeBot extends Bot and specializes in telling jokes.
 */
public class JokeBot extends Bot {
    private final ArrayList<String> jokes;
    private final Random random;

    public JokeBot() {
        super("JokeBot");
        this.random = new Random();
        this.jokes = new ArrayList<>();
        initializeJokes();
        initializeDefaultKnowledge();
    }

    private void initializeJokes() {
        jokes.add("Why don't scientists trust atoms? Because they make up everything!");
        jokes.add("Why did the scarecrow win an award? He was outstanding in his field!");
        jokes.add("Why don't eggs tell jokes? They'd crack each other up!");
        jokes.add("What do you call a fake noodle? An impasta!");
        jokes.add("Why did the math book look so sad? Because it had too many problems!");
        jokes.add("What do you call a bear with no teeth? A gummy bear!");
        jokes.add("Why don't programmers like nature? It has too many bugs!");
        jokes.add("How do you comfort a JavaScript bug? You console it!");
    }

    @Override
    public String respond(String userInput) {
        String lowerInput = userInput.toLowerCase().trim();
        
        // Check for joke-related keywords
        if (lowerInput.contains("joke") || lowerInput.contains("funny") || 
            lowerInput.contains("humor") || lowerInput.contains("laugh")) {
            return tellJoke();
        }
        
        if (lowerInput.contains("another") && lowerInput.contains("joke")) {
            return tellJoke();
        }

        // Fall back to parent's response method
        return super.respond(userInput);
    }

    /**
     * Tell a random joke.
     */
    public String tellJoke() {
        if (jokes.isEmpty()) {
            return "I'm all out of jokes! But here's a thought: Why did the chatbot go to therapy? Because it had too many unresolved conversations!";
        }
        int index = random.nextInt(jokes.size());
        return jokes.get(index);
    }

    /**
     * Add a new joke to the collection.
     */
    public void addJoke(String joke) {
        jokes.add(joke);
    }
}

