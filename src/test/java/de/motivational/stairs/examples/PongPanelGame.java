package de.motivational.stairs.examples;

/**
 * Created by Florian on 12.11.2016.
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class PongPanelGame extends JPanel implements ActionListener, KeyListener{

    public static void main(String[] args){
        JFrame jFrame = new JFrame();
        jFrame.add(new PongPanelGame());
        jFrame.setVisible(true);
    }

    private boolean showTitleScreen = true;
    private boolean playing = false;
    private boolean gameOver = false;

    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean wPressed = false;
    private boolean sPressed = false;

    private int ballX = 250;
    private int ballY = 250;
    private int diameter = 20;
    private int ballDeltaX = -1;
    private int ballDeltaY = 3;

    private int playerOneX = 25;
    private int playerOneY = 250;
    private int playerOneWidth = 10;
    private int playerOneHeight = 50;

    private int playerTwoX = 465;
    private int playerTwoY = 250;
    private int playerTwoWidth = 10;
    private int playerTwoHeight = 50;

    private int paddleSpeed = 5;

    private int playerOneScore = 0;
    private int playerTwoScore = 0;


    //construct a PongPanelGame
    public PongPanelGame(){
        setBackground(Color.BLACK);

        //listen to key presses
        setFocusable(true);
        addKeyListener(this);

        //call step() 60 fps /60
        Timer timer = new Timer(1000/100, this);
        timer.start();
    }


    public void actionPerformed(ActionEvent e){
        step();
    }

    public void step(){

        if(playing){
            //move player 1
            if (upPressed) {
                if (playerOneY-paddleSpeed > 0) {
                    playerOneY -= paddleSpeed;
                }
            }
            if (downPressed) {
                if (playerOneY + paddleSpeed + playerOneHeight < getHeight()) {
                    playerOneY += paddleSpeed;
                }
            }

            //move player 2
            if (wPressed) {
                if (playerTwoY-paddleSpeed > 0) {
                    playerTwoY -= paddleSpeed;
                }
            }
            if (sPressed) {
                if (playerTwoY + paddleSpeed + playerTwoHeight < getHeight()) {
                    playerTwoY += paddleSpeed;
                }
            }



            //where will the ball be after it moves?
            int nextBallLeft = ballX + ballDeltaX;
            int nextBallRight = ballX + diameter + ballDeltaX;
            int nextBallTop = ballY + ballDeltaY;
            int nextBallBottom = ballY + diameter + ballDeltaY;

            int playerOneRight = playerOneX + playerOneWidth;
            int playerOneTop = playerOneY;
            int playerOneBottom = playerOneY + playerOneHeight;

            float playerTwoLeft = playerTwoX;
            float playerTwoTop = playerTwoY;
            float playerTwoBottom = playerTwoY + playerTwoHeight;


            //ball bounces off top and bottom of screen
            if (nextBallTop < 0 || nextBallBottom > getHeight()) {
                ballDeltaY *= -1;
            }

            //will the ball go off the left side?
            if (nextBallLeft < playerOneRight) {
                //is it going to miss the paddle?
                if (nextBallTop > playerOneBottom || nextBallBottom < playerOneTop) {

                    playerTwoScore ++;

                    if (playerTwoScore == 3) {
                        playing = false;
                        gameOver = true;
                    }

                    ballX = 250;
                    ballY = 250;
                }
                else {
                    ballDeltaX *= -1;
                }
            }

            //will the ball go off the right side?
            if (nextBallRight > playerTwoLeft) {
                //is it going to miss the paddle?
                if (nextBallTop > playerTwoBottom || nextBallBottom < playerTwoTop) {

                    playerOneScore ++;

                    if (playerOneScore == 3) {
                        playing = false;
                        gameOver = true;
                    }

                    ballX = 250;
                    ballY = 250;
                }
                else {
                    ballDeltaX *= -1;
                }
            }

            //move the ball
            ballX += ballDeltaX;
            ballY += ballDeltaY;
        }

        //stuff has moved, tell this JPanel to repaint itself
        repaint();
    }

    //paint the game screen
    public void paintComponent(Graphics g){

        super.paintComponent(g);
        g.setColor(Color.WHITE);

        if (showTitleScreen) {

            g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
            g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
            g.drawString("Pong", 165, 100);

            g.setFont(new Font(Font.DIALOG, Font.BOLD, 18));

            g.drawString("Press 'P' to play.", 175, 400);
        }
        else if (playing) {


            int playerOneRight = playerOneX + playerOneWidth;
            int playerTwoLeft =  playerTwoX;

            //draw dashed line down center
            for (int lineY = 0; lineY < getHeight(); lineY += 50) {
                g.drawLine(250, lineY, 250, lineY+25);
            }

            //draw "goal lines" on each side
            g.drawLine(playerOneRight, 0, playerOneRight, getHeight());
            g.drawLine(playerTwoLeft, 0, playerTwoLeft, getHeight());

            //draw the scores
            g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
            g.drawString(String.valueOf(playerOneScore), 100, 100);
            g.drawString(String.valueOf(playerTwoScore), 400, 100);

            //draw the ball
            g.fillOval(ballX, ballY, diameter, diameter);

            //draw the paddles
            g.fillRect(playerOneX, playerOneY, playerOneWidth, playerOneHeight);
            g.fillRect(playerTwoX, playerTwoY, playerTwoWidth, playerTwoHeight);
        }
        else if (gameOver) {

            g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
            g.drawString(String.valueOf(playerOneScore), 100, 100);
            g.drawString(String.valueOf(playerTwoScore), 400, 100);

            g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
            if (playerOneScore > playerTwoScore) {
                g.drawString("Player 1 Wins!", 165, 200);
            }
            else {
                g.drawString("Player 2 Wins!", 165, 200);
            }

            g.setFont(new Font(Font.DIALOG, Font.BOLD, 18));
            g.drawString("Press space to restart.", 150, 400);
        }
    }



    public void keyTyped(KeyEvent e) {}



    public void keyPressed(KeyEvent e) {
        if (showTitleScreen) {
            if (e.getKeyCode() == KeyEvent.VK_P) {
                showTitleScreen = false;
                playing = true;
            }
        }
        else if(playing){
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                upPressed = true;
            }
            else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                downPressed = true;
            }
            else if (e.getKeyCode() == KeyEvent.VK_W) {
                wPressed = true;
            }
            else if (e.getKeyCode() == KeyEvent.VK_S) {
                sPressed = true;
            }
        }
        else if (gameOver) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                gameOver = false;
                showTitleScreen = true;
                playerOneY = 250;
                playerTwoY = 250;
                ballX = 250;
                ballY = 250;
                playerOneScore = 0;
                playerTwoScore = 0;
            }
        }
    }


    public void keyReleased(KeyEvent e) {
        if (playing) {
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                upPressed = false;
            }
            else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                downPressed = false;
            }
            else if (e.getKeyCode() == KeyEvent.VK_W) {
                wPressed = false;
            }
            else if (e.getKeyCode() == KeyEvent.VK_S) {
                sPressed = false;
            }
        }
    }

}
