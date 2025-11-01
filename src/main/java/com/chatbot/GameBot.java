package com.chatbot;

import java.util.Random;

/**
 * GameBot extends Bot and specializes in word games.
 */
public class GameBot extends Bot {
    private final Random random;
    private String currentWord;
    private int wordLength;
    private final StringBuilder guessedLetters;
    private int guessCount;

    public GameBot() {
        super("GameBot");
        this.random = new Random();
        this.guessedLetters = new StringBuilder();
        this.guessCount = 0;
        initializeDefaultKnowledge();
    }

    @Override
    public String respond(String userInput) {
        String lowerInput = userInput.toLowerCase().trim();
        
        // Check for game-related keywords
        if (lowerInput.contains("game") || lowerInput.contains("play")) {
            return startWordGame();
        }
        
        if (lowerInput.contains("guess") || lowerInput.contains("hint")) {
            return provideHint();
        }
        
        if (lowerInput.contains("word") && lowerInput.contains("game")) {
            return startWordGame();
        }

        // If we have an active game, check if user is guessing
        if (currentWord != null && lowerInput.length() == 1) {
            return checkGuess(lowerInput.charAt(0));
        }
        
        if (currentWord != null && lowerInput.length() == currentWord.length()) {
            return checkWordGuess(lowerInput);
        }

        // Fall back to parent's response method
        return super.respond(userInput);
    }

    /**
     * Start a word guessing game.
     */
    private String startWordGame() {
        String[] words = {"java", "chatbot", "programming", "computer", "algorithm", 
                          "function", "variable", "class", "object", "interface"};
        currentWord = words[random.nextInt(words.length)];
        wordLength = currentWord.length();
        guessedLetters.setLength(0);
        guessCount = 0;
        
        StringBuilder display = new StringBuilder();
        for (int i = 0; i < wordLength; i++) {
            display.append("_ ");
        }
        
        addTopic("word_game");
        return "Let's play a word guessing game! I'm thinking of a word with " + 
               wordLength + " letters: " + display.toString() + 
               "\nTry guessing a letter or the whole word! Type 'hint' for a hint.";
    }

    /**
     * Provide a hint for the current word.
     */
    private String provideHint() {
        if (currentWord == null) {
            return "No active game! Say 'game' or 'play' to start a word game.";
        }
        return "Here's a hint: The word has " + wordLength + " letters and starts with '" + 
               currentWord.charAt(0) + "'";
    }

    /**
     * Check a single letter guess.
     */
    private String checkGuess(char letter) {
        if (currentWord == null) {
            return "No active game! Say 'game' or 'play' to start a word game.";
        }
        
        char lowerLetter = Character.toLowerCase(letter);
        if (guessedLetters.indexOf(String.valueOf(lowerLetter)) >= 0) {
            return "You've already guessed '" + letter + "'! Try a different letter.";
        }
        
        guessedLetters.append(lowerLetter);
        guessCount++;
        
        if (currentWord.indexOf(lowerLetter) >= 0) {
            // Show progress
            StringBuilder display = new StringBuilder();
            boolean allFound = true;
            for (int i = 0; i < wordLength; i++) {
                char c = currentWord.charAt(i);
                if (guessedLetters.indexOf(String.valueOf(c)) >= 0) {
                    display.append(c).append(" ");
                } else {
                    display.append("_ ");
                    allFound = false;
                }
            }
            
            if (allFound) {
                String result = "Excellent! You've guessed all the letters! The word was '" + currentWord + 
                              "' (in " + guessCount + " guesses). Well done!";
                currentWord = null;
                return result;
            }
            
            return "Good guess! The letter '" + letter + "' is in the word!\nProgress: " + 
                   display.toString() + "\nKeep guessing!";
        } else {
            return "Sorry, the letter '" + letter + "' is not in the word. " +
                   "You've made " + guessCount + " guess(es). Try again!";
        }
    }

    /**
     * Check if the user guessed the whole word.
     */
    private String checkWordGuess(String guess) {
        if (currentWord == null) {
            return "No active game! Say 'game' or 'play' to start a word game.";
        }
        
        guessCount++;
        
        if (guess.toLowerCase().equals(currentWord)) {
            String result = "Congratulations! You guessed it! The word was '" + currentWord + 
                          "' (in " + guessCount + " guess(es)). Well done!";
            currentWord = null; // Reset game
            guessedLetters.setLength(0);
            guessCount = 0;
            return result;
        } else {
            // Check how close they were
            int correctChars = 0;
            for (int i = 0; i < Math.min(guess.length(), currentWord.length()); i++) {
                if (guess.charAt(i) == currentWord.charAt(i)) {
                    correctChars++;
                }
            }
            return "Not quite! You got " + correctChars + " letter(s) in the correct position. " +
                   "Try again or ask for a hint. You've made " + guessCount + " guess(es).";
        }
    }
}

