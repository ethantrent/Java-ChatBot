package com.chatbot;

import java.util.ArrayList;

/**
 * AdviceBot extends Bot and specializes in providing advice.
 */
public class AdviceBot extends Bot {
    private final ArrayList<String> adviceTopics;

    public AdviceBot() {
        super("AdviceBot");
        this.adviceTopics = new ArrayList<>();
        initializeAdviceTopics();
        initializeDefaultKnowledge();
    }

    private void initializeAdviceTopics() {
        adviceTopics.add("programming");
        adviceTopics.add("study");
        adviceTopics.add("time");
        adviceTopics.add("career");
        addTopic("advice");
    }

    @Override
    public String respond(String userInput) {
        String lowerInput = userInput.toLowerCase().trim();
        
        // Check for advice-related keywords
        if (lowerInput.contains("advice") || lowerInput.contains("help") || 
            lowerInput.contains("suggest") || lowerInput.contains("recommend")) {
            return provideAdvice(lowerInput);
        }
        
        if (lowerInput.contains("how") && (lowerInput.contains("should") || 
            lowerInput.contains("can") || lowerInput.contains("do"))) {
            return provideAdvice(lowerInput);
        }

        // Fall back to parent's response method
        return super.respond(userInput);
    }

    /**
     * Provide advice based on user input.
     */
    private String provideAdvice(String userInput) {
        String advice;
        
        if (userInput.contains("programming") || userInput.contains("code")) {
            advice = "My advice for programming: Practice regularly, read code written by others, " +
                    "and don't be afraid to break things and learn from mistakes!";
        } else if (userInput.contains("study") || userInput.contains("learn")) {
            advice = "Study advice: Break topics into smaller chunks, use active recall techniques, " +
                    "and take regular breaks to let your brain process information.";
        } else if (userInput.contains("time") || userInput.contains("manage")) {
            advice = "Time management tip: Prioritize tasks, use the Pomodoro technique (25 min work, 5 min break), " +
                    "and eliminate distractions during focused work time.";
        } else if (userInput.contains("career") || userInput.contains("job")) {
            advice = "Career advice: Build a portfolio, network with others in your field, " +
                    "and continuously learn new skills to stay relevant.";
        } else {
            advice = "Here's some general advice: Take things one step at a time, " +
                    "ask questions when you're stuck, and remember that progress is more important than perfection!";
        }
        
        addTopic("advice");
        return advice;
    }
}

