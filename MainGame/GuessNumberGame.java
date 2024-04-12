import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.awt.event.ActionListener;

public class GuessNumberGame extends JFrame {
    private static final int MAX_GUESSES = 20; // Maximum number of guesses allowed
    private int numberToGuess, maxNumber, wins, losses, guessCount;
    private JTextField guessField;
    private JLabel messageLabel, infoLabel, scoreLabel, guessCountLabel;
    private JButton playAgainButton;

    public GuessNumberGame(int maxNumber) {
        this.maxNumber = maxNumber;
        wins = 0; losses = 0; guessCount = 0;
        initUI();
        generateNumber();
    }

    // Initialize UI components
    private void initUI() {
        setLayout(new FlowLayout());
        setTitle("Guess the Number Game");

        // Display information about the game
        infoLabel = new JLabel("Guess a number between 1 and " + maxNumber);
        add(infoLabel);

        // Input field for guesses
        guessField = new JTextField(5);
        guessField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) submitGuess();
            }
        });
        add(guessField);

        // Button for submitting guesses
        addButton("Guess", e -> submitGuess());

        // Button to play again (hidden initially)
        playAgainButton = new JButton("Play Again");
        playAgainButton.addActionListener(e -> restartGame());
        playAgainButton.setVisible(false);
        add(playAgainButton);

        // Button to quit the game
        addButton("Quit", e -> System.exit(0));

        // Labels for messages and score
        messageLabel = new JLabel("Enter your guess.");
        add(messageLabel);
        guessCountLabel = new JLabel("Guesses: " + guessCount);
        add(guessCountLabel);
        scoreLabel = new JLabel("Wins: " + wins + " Losses: " + losses);
        add(scoreLabel);

        // Setup window
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    // Generate random number to guess
    private void generateNumber() {
        numberToGuess = new Random().nextInt(maxNumber) + 1;
    }

    // Submit and check guess
    private void submitGuess() {
        try {
            int guess = Integer.parseInt(guessField.getText());
            guessCount++;
            guessCountLabel.setText("Guesses: " + guessCount);
            if (guessCount <= MAX_GUESSES) {
                checkGuess(guess);
            } else {
                endGame();
            }
        } catch (NumberFormatException ex) {
            messageLabel.setText("Enter a valid number.");
        }
    }

    // Check if guess is correct
    private void checkGuess(int guess) {
        if (guess == numberToGuess) {
            wins++;
            endGame();
        } else {
            messageLabel.setText(guess < numberToGuess ? "Higher!" : "Lower!");
        }
    }

    // Handle end of game
    private void endGame() {
        updateScore();
        messageLabel.setText(guessCount <= MAX_GUESSES ? "Correct!" : "Out of guesses!");
        playAgainButton.setVisible(true); // Show play again button
        guessField.setEnabled(false); // Disable further guessing
    }

    // Update the scoreboard
    private void updateScore() {
        scoreLabel.setText("Wins: " + wins + " Losses: " + losses);
    }

    // Reset game for a new round
    private void restartGame() {
        generateNumber();
        guessCount = 0;
        guessField.setText("");
        guessField.setEnabled(true);
        updateScore();
        guessCountLabel.setText("Guesses: 0");
        messageLabel.setText("Guess again!");
        playAgainButton.setVisible(false); // Hide play again button
        losses++; // Increment losses when restarting game
    }

    // Add button utility
    private void addButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        add(button);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new GuessNumberGame(100).setVisible(true));
    }
}
