// Project 4: Brick Breaker Game
// Project Name: "Brick Buster"
// Description: Create a simple Brick Breaker game where a player controls a paddle to hit a ball and break bricks. The 
// game should have different levels, score tracking, and game-over conditions.
// Features:
//  - Player controls a paddle to hit the ball and break bricks.
//  - Multiple Levels with increasing difficulty.
//  - Score Tracking to display current and high scores.
//  - Lives System to give players a limited number of retries.
//  - Game Over and Winning conditions with appropriate messages.
// Technologies Used: Java (Core Java, OOP concepts), Java Swing/JavaFX for graphics, and event handling for player 
// inputs


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class BrickBuster extends JPanel implements ActionListener, KeyListener {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;
    private static final int PADDLE_WIDTH = 100;
    private static final int PADDLE_HEIGHT = 10;
    private static final int BALL_SIZE = 20;
    private static final int BRICK_ROWS = 3;
    private static final int BRICK_COLUMNS = 7;
    private static final int BRICK_WIDTH = 80;
    private static final int BRICK_HEIGHT = 20;
    private static final int DELAY = 10;

    private Timer timer;
    private int paddleX;
    private int ballX, ballY;
    private int ballSpeedX, ballSpeedY;
    private boolean[][] bricks;
    private int score;
    private int lives;
    private boolean gameOver;
    private boolean gameWon;

    public BrickBuster() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        paddleX = WIDTH / 2 - PADDLE_WIDTH / 2;
        ballX = WIDTH / 2 - BALL_SIZE / 2;
        ballY = HEIGHT - 100;
        ballSpeedX = 3;
        ballSpeedY = -3;
        bricks = new boolean[BRICK_ROWS][BRICK_COLUMNS];
        for (int i = 0; i < BRICK_ROWS; i++) {
            for (int j = 0; j < BRICK_COLUMNS; j++) {
                bricks[i][j] = true;
            }
        }
        score = 0;
        lives = 3;
        gameOver = false;
        gameWon = false;

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw paddle
        g.setColor(Color.WHITE);
        g.fillRect(paddleX, HEIGHT - 50, PADDLE_WIDTH, PADDLE_HEIGHT);

        // Draw ball
        g.setColor(Color.RED);
        g.fillOval(ballX, ballY, BALL_SIZE, BALL_SIZE);

        // Draw bricks
        for (int i = 0; i < BRICK_ROWS; i++) {
            for (int j = 0; j < BRICK_COLUMNS; j++) {
                if (bricks[i][j]) {
                    g.setColor(Color.GREEN);
                    g.fillRect(j * BRICK_WIDTH + 10, i * BRICK_HEIGHT + 50, BRICK_WIDTH - 5, BRICK_HEIGHT - 5);
                }
            }
        }

        // Draw score and lives
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 10, 20);
        g.drawString("Lives: " + lives, WIDTH - 80, 20);

        // Draw game over or win message
        if (gameOver) {
            g.setColor(Color.RED);
            g.drawString("Game Over! Press R to Restart", WIDTH / 2 - 100, HEIGHT / 2);
        }
        if (gameWon) {
            g.setColor(Color.GREEN);
            g.drawString("You Win! Press R to Restart", WIDTH / 2 - 100, HEIGHT / 2);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver && !gameWon) {
            // Move ball
            ballX += ballSpeedX;
            ballY += ballSpeedY;

            // Ball collision with walls
            if (ballX <= 0 || ballX >= WIDTH - BALL_SIZE) {
                ballSpeedX = -ballSpeedX;
            }
            if (ballY <= 0) {
                ballSpeedY = -ballSpeedY;
            }

            // Ball collision with paddle
            if (ballY >= HEIGHT - 50 - BALL_SIZE && ballX >= paddleX && ballX <= paddleX + PADDLE_WIDTH) {
                ballSpeedY = -ballSpeedY;
            }

            // Ball out of bounds (lose life)
            if (ballY >= HEIGHT - BALL_SIZE) {
                lives--;
                if (lives == 0) {
                    gameOver = true;
                } else {
                    resetBall();
                }
            }

            // Ball collision with bricks
            for (int i = 0; i < BRICK_ROWS; i++) {
                for (int j = 0; j < BRICK_COLUMNS; j++) {
                    if (bricks[i][j]) {
                        int brickX = j * BRICK_WIDTH + 10;
                        int brickY = i * BRICK_HEIGHT + 50;
                        if (ballX + BALL_SIZE >= brickX && ballX <= brickX + BRICK_WIDTH &&
                            ballY + BALL_SIZE >= brickY && ballY <= brickY + BRICK_HEIGHT) {
                            bricks[i][j] = false;
                            ballSpeedY = -ballSpeedY;
                            score += 10;
                            if (score == BRICK_ROWS * BRICK_COLUMNS * 10) {
                                gameWon = true;
                            }
                        }
                    }
                }
            }
        }

        repaint();
    }

    private void resetBall() {
        ballX = WIDTH / 2 - BALL_SIZE / 2;
        ballY = HEIGHT - 100;
        ballSpeedX = 3;
        ballSpeedY = -3;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT && paddleX > 0) {
            paddleX -= 20;
        }
        if (key == KeyEvent.VK_RIGHT && paddleX < WIDTH - PADDLE_WIDTH) {
            paddleX += 20;
        }
        if ((gameOver || gameWon) && key == KeyEvent.VK_R) {
            restartGame();
        }
    }

    private void restartGame() {
        for (int i = 0; i < BRICK_ROWS; i++) {
            for (int j = 0; j < BRICK_COLUMNS; j++) {
                bricks[i][j] = true;
            }
        }
        score = 0;
        lives = 3;
        gameOver = false;
        gameWon = false;
        resetBall();
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Brick Buster");
        BrickBuster game = new BrickBuster();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}