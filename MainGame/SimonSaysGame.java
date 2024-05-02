import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class SimonSaysGame extends JFrame {
    private final String[] shapes = {"Square", "Circle", "Triangle", "Rectangle", "Pentagon"}; // Pentagon is the weird shape with the thin triangle on top
    private final Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.MAGENTA};
    private String[] currentShapes;
    private Color[] currentColors;
    private int[] shapePositionsX;
    private int[] shapePositionsY;
    private int score = 0;
    private JLabel commandLabel;
    private JLabel scoreLabel;
    private JLabel timerLabel; // Label to display the remaining time
    private JPanel drawingPanel;
    private int targetIndex;
    private Random rand = new Random();
    private Timer timer; // Timer object
    private int timeLeft = 10; // Time left, starting at 10 seconds // Change to 15 seconds//started with 60 too long

    public SimonSaysGame() {
        super("Simon Says Game");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        // Setup UI components
        commandLabel = new JLabel("Follow the command!", SwingConstants.CENTER);
        scoreLabel = new JLabel("Score: 0", SwingConstants.CENTER);
        timerLabel = new JLabel("Time left: 15", SwingConstants.CENTER);

        // Panel where shapes are drawn
        drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (int i = 0; i < currentShapes.length; i++) {
                    drawShape(g, currentShapes[i], currentColors[i], shapePositionsX[i], shapePositionsY[i]);
                }
            }
        };
        drawingPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                checkResponse(e.getX(), e.getY());
            }
        });

        // Add components to the frame
        add(commandLabel, BorderLayout.NORTH);
        add(drawingPanel, BorderLayout.CENTER);
        add(scoreLabel, BorderLayout.SOUTH);
        add(timerLabel, BorderLayout.EAST);

        // Initialize game state arrays
        currentShapes = new String[5];
        currentColors = new Color[5];
        shapePositionsX = new int[5];
        shapePositionsY = new int[5];

        // Setup the timer
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                timerLabel.setText("Time left: " + timeLeft);
                if (timeLeft <= 0) {
                    timer.stop();
                    gameEnd();
                }
            }
        });

        // Start the game
        issueCommand();
    }

    private void drawShape(Graphics g, String shape, Color color, int x, int y) {
        g.setColor(color);
        switch (shape) {
            case "Square": g.fillRect(x, y, 100, 100); break;
            case "Circle": g.fillOval(x, y, 100, 100); break;
            case "Triangle": g.fillPolygon(new int[]{x + 50, x, x + 100}, new int[]{y, y + 100, y + 100}, 3); break;
            case "Rectangle": g.fillRect(x, y, 150, 75); break;
            case "Pentagon": g.fillPolygon(new int[]{x + 50, x + 20, x + 80, x + 90, x - 10}, new int[]{y, y + 50, y + 50, y + 100, y + 100}, 5); break;
        }
    }

    private void issueCommand() {
        for (int i = 0; i < currentShapes.length; i++) {
            currentShapes[i] = shapes[rand.nextInt(shapes.length)];
            currentColors[i] = colors[rand.nextInt(colors.length)];
            shapePositionsX[i] = 50 + rand.nextInt(400);
            shapePositionsY[i] = 50 + rand.nextInt(250);
        }
        targetIndex = rand.nextInt(currentShapes.length);
        commandLabel.setText("Click the " + currentShapes[targetIndex] + " in " + getColorName(currentColors[targetIndex]));
        repaint();
        timeLeft = 10; // Reset the timer for each new round
        timerLabel.setText("Time left: " + timeLeft);
        timer.start();
    }

    private String getColorName(Color color) {
        if (color.equals(Color.RED)) return "red";
        if (color.equals(Color.BLUE)) return "blue";
        if (color.equals(Color.GREEN)) return "green";
        if (color.equals(Color.YELLOW)) return "yellow";
        if (color.equals(Color.MAGENTA)) return "magenta";
        return "unknown";
    }

    private void checkResponse(int x, int y) {
        boolean foundMatchingShape = false;
        for (int i = 0; i < currentShapes.length; i++) {
            int shapeX = shapePositionsX[i];
            int shapeY = shapePositionsY[i];
            int shapeWidth = getShapeWidth(currentShapes[i]);
            int shapeHeight = getShapeHeight(currentShapes[i]);

            if (x >= shapeX && x <= shapeX + shapeWidth && y >= shapeY && y <= shapeY + shapeHeight) {
                if (currentShapes[i].equals(currentShapes[targetIndex]) && currentColors[i].equals(currentColors[targetIndex])) {
                    foundMatchingShape = true;
                    break;
                }
            }
        }

        if (foundMatchingShape) {
            score++;
            scoreLabel.setText("Score: " + score);
            issueCommand();
        } else {
            gameEnd();
        }
    }

    private int getShapeWidth(String shape) {
        switch (shape) {
            case "Square":
            case "Circle": return 100;
            case "Triangle":
            case "Rectangle": return 150;
            case "Pentagon": return 140;
            default: return 100;
        }
    }

    private int getShapeHeight(String shape) {
        switch (shape) {
            case "Square":
            case "Circle": return 100;
            case "Triangle": return 100;
            case "Rectangle": return 75;
            case "Pentagon": return 100;
            default: return 100;
        }
    }

    private void gameEnd() {
        timer.stop();
        int response = JOptionPane.showConfirmDialog(this, "Game Over! Final Score: " + score + "\nWould you like to play again?", "Game Over", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            score = 0;
            scoreLabel.setText("Score: 0");
            timeLeft = 10;
            timerLabel.setText("Time left: " + timeLeft);
            timer.start();
            issueCommand();
        } else {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SimonSaysGame().setVisible(true));
    }
}
