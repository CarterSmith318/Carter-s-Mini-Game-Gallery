import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SnakeGame extends JFrame {
    private final int WINDOW_WIDTH = 300;
    private final int WINDOW_HEIGHT = 300;
    private final int SIZE = 10;
    private final int ALL_DOTS = 900;
    private final int RANDOM_POSITION = 29;
    private final int[] x = new int[ALL_DOTS];
    private final int[] y = new int[ALL_DOTS];
    private int dots;
    private int apple_x;
    private int apple_y;
    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true;
    private Timer timer;
    private int score;
    private JButton playAgainButton;
    private JButton quitButton;

    public SnakeGame() {
        initGame();
    }

    private void initGame() {
        GamePanel gamePanel = new GamePanel();
        add(gamePanel);
        setTitle("Snake Game");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    private void locateApple() {
        // Adjust the range for generation to exclude a margin 
        int range = RANDOM_POSITION - 2; // Subtract 2 to account for a one-apple margin on each side
    
        int r = (int) (Math.random() * range) + 1; // Add 1 to start one apple size inwards
        apple_x = r * SIZE;
    
        r = (int) (Math.random() * range) + 1; // Repeat for the y-coordinate
        apple_y = r * SIZE;
    }
    

    private void checkApple() {
        if ((x[0] == apple_x) && (y[0] == apple_y)) {
            dots++;
            score++;
            locateApple();
        }
    }

    private void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[(i - 1)];
            y[i] = y[(i - 1)];
        }

        if (leftDirection) {
            x[0] -= SIZE;
            if (x[0] < 0) x[0] = WINDOW_WIDTH - SIZE;
        }

        if (rightDirection) {
            x[0] += SIZE;
            if (x[0] > WINDOW_WIDTH - SIZE) x[0] = 0;
        }

        if (upDirection) {
            y[0] -= SIZE;
            if (y[0] < 0) y[0] = WINDOW_HEIGHT - SIZE;
        }

        if (downDirection) {
            y[0] += SIZE;
            if (y[0] > WINDOW_HEIGHT - SIZE) y[0] = 0;
        }
    }

    private void checkCollision() {
        for (int i = dots; i > 0; i--) {
            if ((i > 4) && (x[0] == x[i]) && (y[0] == y[i])) {
                inGame = false;
            }
        }

        if (!inGame) {
            timer.stop();
        }
    }

    private void doGameCycle() {
        if (inGame) {
            checkApple();
            checkCollision();
            move();
        }
        repaint();
    }

    private class GamePanel extends JPanel implements ActionListener {

        public GamePanel() {
            setBackground(Color.black);
            setFocusable(true);
            timer = new Timer(140, this);
            timer.start();
            resetGame();
            setUpKeyBindings();
        }

        private void setUpKeyBindings() {
            getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"), "moveUp");
            getActionMap().put("moveUp", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!downDirection) {
                        upDirection = true;
                        rightDirection = false;
                        leftDirection = false;
                    }
                }
            });
        
            getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), "moveDown");
            getActionMap().put("moveDown", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!upDirection) {
                        downDirection = true;
                        rightDirection = false;
                        leftDirection = false;
                    }
                }
            });
        
            getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("A"), "moveLeft");
            getActionMap().put("moveLeft", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!rightDirection) {
                        leftDirection = true;
                        upDirection = false;
                        downDirection = false;
                    }
                }
            });
        
            getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("D"), "moveRight");
            getActionMap().put("moveRight", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!leftDirection) {
                        rightDirection = true;
                        upDirection = false;
                        downDirection = false;
                    }
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            draw(g);
        }

        private void draw(Graphics g) {
            if (inGame) {
                g.setColor(Color.red);
                g.fillRect(apple_x, apple_y, SIZE, SIZE);

                for (int z = 0; z < dots; z++) {
                    if (z == 0) {
                        g.setColor(Color.green);
                    } else {
                        g.setColor(Color.white);
                    }
                    g.fillRect(x[z], y[z], SIZE, SIZE);
                }

                g.setColor(Color.white);
                g.drawString("Score: " + score, 5, 15);
            } else {
                gameOver(g);
            }
        }

        private void gameOver(Graphics g) {
            String msg = "Game Over. Score: " + score;
            Font small = new Font("Helvetica", Font.BOLD, 14);
            FontMetrics metr = getFontMetrics(small);

            g.setColor(Color.white);
            g.setFont(small);
            g.drawString(msg, (WINDOW_WIDTH - metr.stringWidth(msg)) / 2, WINDOW_HEIGHT / 2);

            if (playAgainButton == null) {
                playAgainButton = new JButton("Play Again");
                playAgainButton.setBounds(WINDOW_WIDTH / 2 - 80, WINDOW_HEIGHT / 2 + 30, 160, 30);
                playAgainButton.addActionListener(e -> {
                    resetGame();
                    playAgainButton.setVisible(false);
                    quitButton.setVisible(false);
                });
                add(playAgainButton);
            } else {
                playAgainButton.setVisible(true);
            }

            if (quitButton == null) {
                quitButton = new JButton("Quit");
                quitButton.setBounds(WINDOW_WIDTH / 2 - 80, WINDOW_HEIGHT / 2 + 70, 160, 30);
                quitButton.addActionListener(e -> System.exit(0));
                add(quitButton);
            } else {
                quitButton.setVisible(true);
            }

            // Refresh the JPanel to display buttons
            revalidate();
            repaint();
        }

        private void resetGame() {
            dots = 3;
            score = 0;
        
            for (int z = 0; z < dots; z++) {
                x[z] = 50 - z * 10;
                y[z] = 50;
            }
        
            locateApple();
            rightDirection = true;
            leftDirection = false;
            upDirection = false;
            downDirection = false;
            inGame = true;
        
            // Hide the buttons
            if (playAgainButton != null) {
                playAgainButton.setVisible(false);
            }
            if (quitButton != null) {
                quitButton.setVisible(false);
            }
        
            // Restart the timer and request focus for key bindings
            timer.start();
            this.requestFocusInWindow();
        }
        

        @Override
        public void actionPerformed(ActionEvent e) {
            doGameCycle();
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame ex = new SnakeGame();
            ex.setVisible(true);
        });
    }
}
