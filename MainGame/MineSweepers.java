import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class MineSweepers extends JFrame {
    // Constants for window size and grid size
    private final int WIDTH = 400;
    private final int HEIGHT = 400;
    private final int GRID_SIZE = 10;

    // Grid components
    private final JButton[][] buttons = new JButton[GRID_SIZE][GRID_SIZE];
    private final boolean[][] mines = new boolean[GRID_SIZE][GRID_SIZE];
    private final boolean[][] revealed = new boolean[GRID_SIZE][GRID_SIZE];
    private final boolean[][] flagged = new boolean[GRID_SIZE][GRID_SIZE];

    // Game state variables
    private int revealedCount = 0;
    private final int mineCount = 15;

    public MineSweepers() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));
        
        initGame();  // Initialize the game logic
        initUI();    // Set up the user interface
    }

    private void initGame() {
        placeMines();  // Randomly place mines on the grid
    }

    private void placeMines() {
        Random random = new Random();
        for (int i = 0; i < mineCount; i++) {
            int x, y;
            do {
                x = random.nextInt(GRID_SIZE);
                y = random.nextInt(GRID_SIZE);
            } while (mines[x][y]);
            mines[x][y] = true;
        }
    }

    private void initUI() {
        // Initialize buttons for each cell
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].addMouseListener(new ButtonListener(i, j));
                add(buttons[i][j]);
            }
        }
    }

    private class ButtonListener extends MouseAdapter {
        private final int x;
        private final int y;

        public ButtonListener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            // Handle left and right mouse clicks
            if (SwingUtilities.isRightMouseButton(e)) {
                toggleFlag(x, y);  // Right-click: toggle flag
            } else {
                revealCell(x, y);  // Left-click: reveal cell
            }
        }
    }

    private void toggleFlag(int x, int y) {
        // Flag or unflag a cell
        JButton button = buttons[x][y];
        if (!revealed[x][y]) {
            if (!flagged[x][y]) {
                button.setText("F");
                flagged[x][y] = true;
            } else {
                button.setText("");
                flagged[x][y] = false;
            }
        }
    }

    private void revealCell(int x, int y) {
        // Reveal the cell and update its text
        if (revealed[x][y] || flagged[x][y]) return;

        revealed[x][y] = true;
        revealedCount++;

        JButton button = buttons[x][y];
        if (mines[x][y]) {
            button.setText("M");  // Mine
            gameOver(false);
        } else {
            int adjacentMines = countAdjacentMines(x, y);
            button.setText(adjacentMines > 0 ? String.valueOf(adjacentMines) : "");
            button.setEnabled(false);

            if (revealedCount == GRID_SIZE * GRID_SIZE - mineCount) {
                gameOver(true);  // Check for win
            }
        }
    }

    private int countAdjacentMines(int x, int y) {
        // Count mines adjacent to a specified cell
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (x + i >= 0 && x + i < GRID_SIZE && y + j >= 0 && y + j < GRID_SIZE) {
                    if (mines[x + i][y + j]) count++;
                }
            }
        }
        return count;
    }

    private void gameOver(boolean won) {
        // Handle game over scenario
        for (JButton[] buttonRow : buttons) {
            for (JButton button : buttonRow) {
                button.setEnabled(false);  // Disable all buttons
            }
        }

        // Show game over message and ask for replay
        JOptionPane.showMessageDialog(this, won ? "You win!" : "Game Over!");
        int result = JOptionPane.showConfirmDialog(this, "Play Again?", "Minesweeper", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            this.dispose();
            new MineSweepers().setVisible(true);
        } else {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new MineSweepers().setVisible(true);  // Start the game
    }
}
