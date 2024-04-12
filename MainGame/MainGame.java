import javax.swing.*;
import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener; //old idea
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainGame {

    private JFrame frame;
    private JPanel panel;
    private JButton snakeGameButton, guessNumberGameButton, ticTacToeGameButton, brickBreakButton, exitButton;

    public MainGame() {
        // Setup main frame and panel
        frame = new JFrame("Game Center");
        panel = new JPanel(new GridLayout(5, 1, 10, 10)); // 5 rows for buttons

        // Initialize buttons
        snakeGameButton = new JButton("Play Snake Game");
        guessNumberGameButton = new JButton("Play Guess the Number Game");
        ticTacToeGameButton = new JButton("Play Tic Tac Toe");
        brickBreakButton = new JButton("Play Brick Breaker");
        exitButton = new JButton("Exit");

        // Snake Game button action
        snakeGameButton.addActionListener(e -> {
            frame.setVisible(false);
            SnakeGame snakeGame = new SnakeGame();
            snakeGame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    frame.setVisible(true);
                }
            });
        });

        // Guess Number Game button action
        guessNumberGameButton.addActionListener(e -> {
            frame.setVisible(false);
            GuessNumberGame guessNumberGame = new GuessNumberGame(100);
            guessNumberGame.setVisible(true);
            guessNumberGame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    frame.setVisible(true);
                }
            });
        });

        // Tic Tac Toe button action
        ticTacToeGameButton.addActionListener(e -> {
            frame.setVisible(false);
            ticTacToeGame ticTacToe = new ticTacToeGame();
            ticTacToe.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    frame.setVisible(true);
                }
            });
        });

        // Brick Breaker button action
        brickBreakButton.addActionListener(e -> {
            frame.setVisible(false);
            BrickBreak.main(new String[0]); // Launch Brick Breaker
            frame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    frame.setVisible(true);
                }
            });
        });

        // Exit button action
        exitButton.addActionListener(e -> System.exit(0));

        // Adding buttons to panel
        panel.add(snakeGameButton);
        panel.add(guessNumberGameButton);
        panel.add(ticTacToeGameButton);
        panel.add(brickBreakButton);
        panel.add(exitButton);

        // Frame setup
        frame.add(panel);
        frame.setSize(400, 250); // Size for 5 buttons
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new MainGame();
    }
}
