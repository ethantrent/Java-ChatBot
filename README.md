# ChatBot - Interactive Java Chatbot Application

## Project Description

An interactive chatbot application in Java that demonstrates core programming concepts including variables, conditionals, loops, functions, classes, and the Java Collection Framework (HashMap, ArrayList, TreeSet).

## Features

- **Conversational Interaction**: Responds to user questions using a knowledge base stored in a HashMap
- **Multiple Bot Personalities**: 
  - **JokeBot**: Tells jokes and responds to humor-related queries
  - **GameBot**: Plays word guessing games with the user
  - **AdviceBot**: Provides advice on programming, studying, time management, and career
- **Learning Capability**: Can learn new question-answer pairs that users teach it
- **Persistence**: Saves learned responses to a file for future sessions
- **Chat History**: Maintains conversation history using ArrayList
- **Topic Tracking**: Uses TreeSet to track different conversation topics
- **Extensible Design**: Uses inheritance (Bot base class) and interfaces (Respondable) for extensibility

## Requirements

- Java JDK 8 or higher
- No external dependencies - uses only standard Java libraries

## How to Run

1. Compile the Java files:
   ```bash
   javac -d out src/main/java/com/chatbot/*.java
   ```

2. Run the application:
   ```bash
   java -cp out com.chatbot.ChatBot
   ```

   Or from the out directory:
   ```bash
   java com.chatbot.ChatBot
   ```

## Usage

### Basic Commands

- `help` - Display all available commands
- `history` - View the conversation history
- `topics` - See all tracked conversation topics
- `exit` or `quit` - End the conversation

### Bot Personalities

- `jokebot` - Switch to JokeBot (tells jokes)
- `gamebot` - Switch to GameBot (word games)
- `advicebot` - Switch to AdviceBot (gives advice)

### Teaching the Bot

Teach the bot new information using this format:
```
teach me: [your question] -> [my answer]
```

Example:
```
teach me: what is java -> Java is a programming language
```

## Project Structure

```
src/main/java/com/chatbot/
├── Message.java       - Represents a single chat message
├── Respondable.java   - Interface for bot personalities
├── Bot.java          - Base bot class with core functionality
├── JokeBot.java      - Specialized bot for telling jokes
├── GameBot.java      - Specialized bot for word games
├── AdviceBot.java    - Specialized bot for giving advice
└── ChatBot.java      - Main application class
```

## Data Persistence

- **Learned responses**: Saved to `chatbot_data.txt`
- **Chat history**: Saved to `chat_history.txt` when exiting

## Java Concepts Demonstrated

- ✅ Variables (primitive and object types)
- ✅ Conditionals (if/else statements)
- ✅ Loops (while loop for main conversation)
- ✅ Functions/Methods (multiple methods across classes)
- ✅ Classes (multiple classes with different responsibilities)
- ✅ Collections:
  - HashMap (knowledge base storage)
  - ArrayList (chat history)
  - TreeSet (topic tracking)
- ✅ Inheritance (Bot base class, specialized bots extend it)
- ✅ Interfaces (Respondable interface)
- ✅ File I/O (reading/writing learned responses and chat history)
- ✅ Exception Handling (try-catch for file operations)

## Author

Ethan Trent  
CSE 310 - Applied Programming  
Module #3

