import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.ArrayList;

public class MemoryGame extends JFrame implements ActionListener {
    private final int gridSize = 4;  // 4x4 grid of cards
    private ArrayList<String> cards = new ArrayList<>();
    private JButton[] buttons = new JButton[gridSize * gridSize];
    private String lastCard = "";
    private int lastButtonIndex = -1;
    private boolean allowInput = true;
    private int score = 0;
    private int timeLeft = 120; // 120 seconds for the game
    private int matchesFound = 0; // Track the number of matches found

    public MemoryGame() {
        loadCards();
        setupGUI();
        setupTimer();
    }

    private void loadCards() {
        char letter = 'A';
        for (int i = 0; i < (gridSize * gridSize) / 2; i++) {
            String card = Character.toString(letter);
            cards.add(card);
            cards.add(card);  // Add pairs
            letter++;  // Move to next letter in alphabet
        }
        Collections.shuffle(cards);
    }

    private void setupGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLayout(new GridLayout(gridSize, gridSize));
        getContentPane().setBackground(Color.LIGHT_GRAY);

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton();
            buttons[i].setBorder(BorderFactory.createLineBorder(Color.gray));
            buttons[i].setFocusPainted(false);
            buttons[i].setFont(new Font("Arial", Font.BOLD, 24));  // Larger font for visibility
            buttons[i].addActionListener(this);
            add(buttons[i]);
        }
        setVisible(true);
    }

    private void setupTimer() {
        Timer timer = new Timer(1000, evt -> {
            timeLeft--;
            if (timeLeft > 0) {
                setTitle("Memory Game - Time Left: " + timeLeft + "s - Score: " + score);
            } else {
                ((Timer) evt.getSource()).stop();
                endGame();
            }
        });
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!allowInput) return;
        JButton clickedButton = (JButton) e.getSource();
        int clickedIndex = java.util.Arrays.asList(buttons).indexOf(clickedButton);
        revealCard(clickedIndex);

        if (!lastCard.equals("") && clickedIndex != lastButtonIndex) {
            if (lastCard.equals(cards.get(clickedIndex))) {
                buttons[clickedIndex].setEnabled(false);
                buttons[lastButtonIndex].setEnabled(false);
                updateScore(true);
                matchesFound++;
                if (matchesFound == gridSize * gridSize / 2) {
                    endGame();
                }
                resetTurn();
            } else {
                updateScore(false);
                allowInput = false;
                Timer t = new Timer(500, evt -> {
                    buttons[clickedIndex].setText("");
                    buttons[lastButtonIndex].setText("");
                    resetTurn();
                });
                t.setRepeats(false);
                t.start();
            }
        }
    }

    private void revealCard(int index) {
        buttons[index].setText(cards.get(index));
        if (lastCard.equals("")) {
            lastCard = cards.get(index);
            lastButtonIndex = index;
        }
    }

    private void updateScore(boolean match) {
        if (match) {
            score += 10;
        } else {
            if (score > 0) score -= 2;  // penalty for wrong match
        }
        setTitle("Memory Game - Time Left: " + timeLeft + "s - Score: " + score);
    }

    private void resetTurn() {
        lastCard = "";
        lastButtonIndex = -1;
        allowInput = true;
    }

    private void endGame() {
        score += timeLeft;  // Add remaining time as bonus points
        JOptionPane.showMessageDialog(this, "Game Over! Final score: " + score + ".", "Game Over", JOptionPane.YES_NO_OPTION);
        int response = JOptionPane.showConfirmDialog(this, "Play again?", "Game Over", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            restartGame();
        } else {
            System.exit(0);
        }
    }

    private void restartGame() {
        cards.clear();
        loadCards();
        for (JButton button : buttons) {
            button.setEnabled(true);
            button.setText("");
        }
        score = 0;
        matchesFound = 0;
        timeLeft = 120;
        setupTimer();
        setTitle("Memory Game");
    }

    public static void main(String[] args) {
        new MemoryGame();
    }
}
