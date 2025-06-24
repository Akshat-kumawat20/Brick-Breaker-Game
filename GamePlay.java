import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import javax.swing.JPanel;
public class GamePlay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private int score = 0;

    private int totalBricks = 21; // total number of bricks in the game
    private Timer timer; // timer to control the game speed
    private int delay = 8; // delay in milliseconds for the timer
    private int playerX = 310; // initial position of the paddle
    private int ballposX = 120; // initial x position of the ball
    private int ballposY = 350; // initial y position of the ball
    private int ballXdir = 2; // initial x direction of the ball
    private int ballYdir = -4; // initial y direction of the ball

    private MapGenerator map; 

    public GamePlay() {
        map = new MapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();

    }
    public void paint(Graphics g) {
        // Background
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);

        // Draw the map
        map.draw((Graphics2D) g);

        // Borders
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);

        // the score
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString( "Score:"+ score, 590, 30);


        // the paddle
        g.setColor(Color.green);
        g.fillRect(playerX, 550, 100, 8);

        // the ball
        g.setColor(Color.yellow);
        g.fillOval(ballposX, ballposY, 20, 20);

        if(totalBricks <= 0) {
            play = false; // if all bricks are broken, the game is over
            ballXdir = 0; // stop the ball
            ballYdir = 0; // stop the ball
            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("You Won, Scores: "+ score, 190, 300);

            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Press Enter to Restart ", 190, 350);

        }

        if(ballposY > 570) {
            play = false; // if the ball goes below the paddle, the game is over
           ballXdir = 0; // stop the ball
           ballYdir = 0; // stop the ball
           g.setColor(Color.RED);
           g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Game Over, Scores: "+ score, 190, 300);

            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Press Enter to Restart ", 190, 350);



        }

        g.dispose();

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(play) {
            // check for collision with the paddle
            if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
                ballYdir = -ballYdir; // reverse the direction of the ball when it hits the paddle
            }
            // check for collision with bricks
            A:for(int i = 0; i <map.map.length; i++) {
                for(int j = 0; j<map.map[0].length; j++) {
                    if(map.map[i][j] > 0) {
                        int brickX = j* map.brickWidth + 80; // calculate the x position of the brick
                        int brickY = i * map.brickHeight + 50; // calculate the y position of the brick
                        int brickWidth = map.brickWidth; // width of the brick
                        int brickHeight = map.brickHeight; // height of the brick

                        // create a rectangle for the brick

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20); // create a rectangle for the ball
                        Rectangle brickRect = rect;

                        // check for collision between the ball and the brick
                        if(ballRect.intersects(brickRect)) {
                            map.setBrickValue(0, i, j); // set the value of the brick to 0 (indicating it is broken)
                            totalBricks--; // decrease the total number of bricks
                            score += 5; // increase the score
                            if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
                                ballXdir = -ballXdir; // reverse the x direction of the ball
                            } else {
                                ballYdir = -ballYdir; // reverse the y direction of the ball
                            }
                            break A;
                        }


                    }
                }

            }
           



            ballposX += ballXdir;
            ballposY += ballYdir;
            if(ballposX < 0) {
                ballXdir = -ballXdir; // if the ball hits the left wall, reverse direction
            }
            if(ballposY < 0) {
                ballYdir = -ballYdir; // if the ball hits the top wall, reverse direction
            }
            if(ballposX > 670) { 
                ballXdir = -ballXdir; // if the ball hits the right wall, reverse direction
            }
        }


        repaint();

    }


    @Override
    public void keyTyped(KeyEvent e) { }
    @Override
    public void keyPressed(KeyEvent e) { }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if(playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }

        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            if(playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
            
        }
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            if(!play) {
                play = true;
                ballposX = 120; // reset the ball position
                ballposY = 350; // reset the ball position
                ballXdir = 2; // reset the x direction of the ball
                ballYdir = -4; // reset the y direction of the ball
                playerX = 310; // reset the paddle position
                score = 0; // reset the score
                totalBricks = 21; // reset the total number of bricks
                map = new MapGenerator(3, 7); // reset the map

                repaint(); // repaint the game





            }
        }
       
    }
    public void moveRight() {
        play = true;
        playerX += 20;
    }
    public void moveLeft() {
        play = true;
        playerX -= 20;
    }


   
    
}
