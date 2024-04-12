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

    private void initUI() {
        setLayout(new FlowLayout());
        setTitle("Guess the Number Game");
        infoLabel = new JLabel("Guess a number between 1 and " + maxNumber);
        add(infoLabel);
        guessField = new JTextField(5);
        guessField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) submitGuess();
            }
        });
        add(guessField);
        addButton("Guess", e -> submitGuess());
        playAgainButton = new JButton("Play Again");
        playAgainButton.addActionListener(e -> restartGame());
        playAgainButton.setVisible(false);
        add(playAgainButton);
        addButton("Quit", e -> System.exit(0));
        messageLabel = new JLabel("Enter your guess.");
        add(messageLabel);
        guessCountLabel = new JLabel("Guesses: " + guessCount);
        add(guessCountLabel);
        scoreLabel = new JLabel("Wins: " + wins + " Losses: " + losses);
        add(scoreLabel);
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void generateNumber() {
        numberToGuess = new Random().nextInt(maxNumber) + 1;
    }

    private void submitGuess() {
        try {
            int guess = Integer.parseInt(guessField.getText());
            if (guessCount < MAX_GUESSES) { // Check guesses before incrementing
                guessCount++;
                guessCountLabel.setText("Guesses: " + guessCount);
                checkGuess(guess);
            } else {
                endGame();
            }
        } catch (NumberFormatException ex) {
            messageLabel.setText("Enter a valid number.");
        }
    }

    private void checkGuess(int guess) {
        if (guess == numberToGuess) {
            wins++;
            endGame();
        } else {
            messageLabel.setText(guess < numberToGuess ? "Higher!" : "Lower!");
        }
    }

    private void endGame() {
        if (guessCount >= MAX_GUESSES && numberToGuess != Integer.parseInt(guessField.getText())) { // Check if max guesses reached without a correct guess
            losses++; // Increment losses when no guesses are left and the last guess is incorrect
            messageLabel.setText("Out of guesses!");
        } else if (numberToGuess == Integer.parseInt(guessField.getText())) {
            messageLabel.setText("Correct!");
        }
        updateScore();
        playAgainButton.setVisible(true);
        guessField.setEnabled(false);
    }
    

    private void updateScore() {
        scoreLabel.setText("Wins: " + wins + " Losses: " + losses);
    }

    private void restartGame() {
        generateNumber();
        guessCount = 0;
        guessField.setText("");
        guessField.setEnabled(true);
        guessCountLabel.setText("Guesses: 0");
        messageLabel.setText("Guess again!");
        playAgainButton.setVisible(false);
    }

    private void addButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        add(button);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new GuessNumberGame(100).setVisible(true));
    }
}
