import javax.swing.*;
import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener; //old idea
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;



public class MainGame {
    private JFrame frame;
    private JPanel panel;
    private JButton snakeGameButton, guessNumberGameButton, ticTacToeGameButton, brickBreakButton, minesweepersButton, MemoryGame, SimonSaysGame, exitButton;

    public MainGame() {
        frame = new JFrame("Game Center");
        panel = new JPanel(new GridLayout(6, 1, 10, 10)); // Updated to accommodate an additional button

        snakeGameButton = new JButton("Play Snake Game");
        guessNumberGameButton = new JButton("Play Guess the Number Game");
        ticTacToeGameButton = new JButton("Play Tic Tac Toe");
        brickBreakButton = new JButton("Play Brick Breaker");
        minesweepersButton = new JButton("Play Minesweepers");
        MemoryGame = new JButton("Play Memory Game");
        SimonSaysGame = new JButton("Play Simon Says Game");
        exitButton = new JButton("Exit");

        snakeGameButton.addActionListener(e -> {
            // frame.setVisible(false); old
            SnakeGame snakeGame = new SnakeGame();
            snakeGame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    frame.setVisible(true);
                }
            });
        });

        guessNumberGameButton.addActionListener(e -> {
            //frame.setVisible(false); 
            GuessNumberGame guessNumberGame = new GuessNumberGame(100);
            guessNumberGame.setVisible(true);
            guessNumberGame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    frame.setVisible(true);
                }
            });
        });

        ticTacToeGameButton.addActionListener(e -> {
            //frame.setVisible(false);
            ticTacToeGame ticTacToe = new ticTacToeGame();
            ticTacToe.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    frame.setVisible(true);
                }
            });
        });

        brickBreakButton.addActionListener(e -> {
            //frame.setVisible(false);
            BrickBreak.main(new String[0]); // Launch Brick Breaker
            frame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    frame.setVisible(true);
                }
            });
        });

        minesweepersButton.addActionListener(e -> {
            //frame.setVisible(false);
            MineSweepers mineSweepersGame = new MineSweepers();
            mineSweepersGame.setVisible(true);
            mineSweepersGame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    frame.setVisible(true);
                }
            });
        });

        MemoryGame.addActionListener(e -> {
            //frame.setVisible(false);
            MemoryGame memoryGame = new MemoryGame();
            memoryGame.setVisible(true);
            memoryGame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    frame.setVisible(true);
                }
            });
        });

        SimonSaysGame.addActionListener(e -> {
            //frame.setVisible(false);
            SimonSaysGame simonSaysGame = new SimonSaysGame();
            simonSaysGame.setVisible(true);
            simonSaysGame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    frame.setVisible(true);
                }
            });
        });

        exitButton.addActionListener(e -> System.exit(0));

        panel.add(snakeGameButton);
        panel.add(guessNumberGameButton);
        panel.add(ticTacToeGameButton);
        panel.add(brickBreakButton);
        panel.add(minesweepersButton);
        panel.add(MemoryGame);
        panel.add(SimonSaysGame);
        panel.add(exitButton);

        frame.add(panel);
        frame.setSize(400, 300); // Adjusted size for more buttons
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new MainGame();
    }
}
