Java Game Center
Welcome to the Java Game Center, a collection of classic games implemented in Java using Swing for graphical user interface. This package includes several games, each accessible through a central launcher that allows users to select and play any game.

Prerequisites
Java Development Kit (JDK) - Java 11 or later recommended
Any IDE that supports Java, or a command-line tool to compile and run Java programs
Compilation and Execution
To compile and run the game center:

Compile the source files:
bash
Copy code
javac MainGame.java SnakeGame.java GuessNumberGame.java TicTacToeGame.java BrickBreak.java MineSweepers.java
Run the main launcher:
bash
Copy code
java MainGame
Games Included
Snake Game: Navigate the snake around the screen, collecting food and avoiding walls and your own tail.
Guess the Number Game: Try to guess a randomly selected number within a limited number of attempts.
Tic Tac Toe Game: Play the classic Tic Tac Toe game against the computer.
Brick Breaker Game: Break bricks with a ball while trying to prevent it from falling off the screen below the paddle.
Mine Sweepers Game: Clear a grid of hidden mines without detonating any of them.
Additional Features
Game Launcher: A GUI launcher that presents buttons for each game. Exiting a game brings you back to the launcher.
Exit Button: Safely exits the game center.
