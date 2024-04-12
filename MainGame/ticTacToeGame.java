import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;

public class ticTacToeGame extends JFrame implements ActionListener, ComponentListener {
    private final JButton[] buttons = new JButton[9];
    private boolean playerTurn = true; // True for Player 1 (X), False for Player 2 (O)
    private int scorePlayer1 = 0;
    private int scorePlayer2 = 0;
    private String player1Name = "Player 1";
    private String player2Name = "Player 2";
    private String player1Symbol = "X";
    private String player2Symbol = "O";

    public ticTacToeGame() {
        super("Tic Tac Toe");
        getPlayerDetails();
        setLayout(new GridLayout(3, 3));
        initializeButtons();
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // Add ComponentListener to the JFrame
        this.addComponentListener(this);
    }

    private void initializeButtons() {
        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            buttons[i].setText("");
            buttons[i].addActionListener(this);
            add(buttons[i]);
        }
    }

    private void getPlayerDetails() {
        player1Name = JOptionPane.showInputDialog(this, "Enter name for Player 1:", "Player 1");
        player1Symbol = JOptionPane.showInputDialog(this, "Enter symbol for " + player1Name + ":", "X");
        player2Name = JOptionPane.showInputDialog(this, "Enter name for Player 2:", "Player 2");
        player2Symbol = JOptionPane.showInputDialog(this, "Enter symbol for " + player2Name + ":", "O");
    }

    public void actionPerformed(ActionEvent e) {
        JButton buttonClicked = (JButton)e.getSource();
        if (playerTurn) {
            if (buttonClicked.getText().equals("")) {
                buttonClicked.setText(player1Symbol);
                playerTurn = false;
                checkForWinner();
            }
        } else {
            if (buttonClicked.getText().equals("")) {
                buttonClicked.setText(player2Symbol);
                playerTurn = true;
                checkForWinner();
            }
        }
    }

    private void checkForWinner() {
        if (checkCombination(0, 1, 2) || checkCombination(3, 4, 5) || checkCombination(6, 7, 8) ||
            checkCombination(0, 3, 6) || checkCombination(1, 4, 7) || checkCombination(2, 5, 8) ||
            checkCombination(0, 4, 8) || checkCombination(2, 4, 6)) {
            updateScores();
            displayScores();
            showEndGameOptions("Player " + (!playerTurn ? player1Name + " (" + player1Symbol + ")" : player2Name + " (" + player2Symbol + ")") + " wins!");
        } else if (isGridFull()) {
            showEndGameOptions("The game is a draw!");
        }
    }

    private boolean checkCombination(int a, int b, int c) {
        return !buttons[a].getText().equals("") &&
               buttons[a].getText().equals(buttons[b].getText()) &&
               buttons[b].getText().equals(buttons[c].getText());
    }

    private boolean isGridFull() {
        for (JButton button : buttons) {
            if (button.getText().equals("")) {
                return false;
            }
        }
        return true;
    }

    private void updateScores() {
        if (!playerTurn) {
            scorePlayer1++;
        } else {
            scorePlayer2++;
        }
    }

    private void displayScores() {
        JOptionPane.showMessageDialog(this, player1Name + ": " + scorePlayer1 + "\n" + 
                                      player2Name + ": " + scorePlayer2, "Scores", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showEndGameOptions(String message) {
        int option = JOptionPane.showOptionDialog(this, message + "\nWhat would you like to do?",
                                                  "Game Over", JOptionPane.YES_NO_OPTION,
                                                  JOptionPane.QUESTION_MESSAGE, null,
                                                  new Object[]{"Play Again", "Quit"}, "Play Again");

        if (option == JOptionPane.YES_OPTION) {
            resetButtons();
        } else {
            System.exit(0);
        }
    }

    private void resetButtons() {
        for (JButton button : buttons) {
            button.setText("");
        }
        playerTurn = true;
    }

    // Implement the componentResized method
    @Override
    public void componentResized(ComponentEvent e) {
        int buttonFontSize = this.getWidth() / 10; // Example formula for font size
        Font buttonFont = new Font("Arial", Font.PLAIN, buttonFontSize);
        for (JButton button : buttons) {
            button.setFont(buttonFont);
        }
    }

    // Other ComponentListener methods that you need to override but can leave empty
    @Override
    public void componentMoved(ComponentEvent e) {}

    @Override
    public void componentShown(ComponentEvent e) {}

    @Override
    public void componentHidden(ComponentEvent e) {}

    public static void main(String[] args) {
        new ticTacToeGame();
    }
}
