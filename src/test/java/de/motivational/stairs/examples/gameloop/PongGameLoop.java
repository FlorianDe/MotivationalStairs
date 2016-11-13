package de.motivational.stairs.examples.gameloop;

/**
 * Created by Florian on 13.11.2016.
 */
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;


public class PongGameLoop extends javax.swing.JFrame {

    DrawPanel jPanel;
    Ball[] balls;
    BasicStroke ballStroke;
    int showMode = 0;

    private static final long serialVersionUID = 1L;
    /* difference between time of update and world step time */
    float localTime = 0f;

    /** Creates new form Game */
    public PongGameLoop() {
        jPanel = new DrawPanel(this);
        this.add(jPanel);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setSize(800, 600);
    }

    /**
     * Starts the game loop in a new Thread.
     * @param fixedTimeStep
     * @param maxSubSteps maximum steps that should be processed to catch up with real time.
     */
    public final void start(final float fixedTimeStep, final int maxSubSteps) {
        //this.createBufferStrategy(2);
        init();
        new Thread() {
            {
                setDaemon(true);
            }

            @Override
            public void run() {
                long start = System.nanoTime();
                while (true) {
                    long now = System.nanoTime();
                    float elapsed = (now - start) / 1000000000f;
                    start = now;
                    internalUpdateWithFixedTimeStep(elapsed, maxSubSteps, fixedTimeStep);
                    internalUpdateGraphicsInterpolated();
                    if (1000000000 * fixedTimeStep - (System.nanoTime() - start) > 1000000) {
                        try {
                            Thread.sleep(0, 999999);
                        } catch (InterruptedException ex) {
                        }
                    }
                }
            }
        }.start();
    }

    /**
     * Updates game state if possible and sets localTime for interpolation.
     * @param elapsedSeconds
     * @param maxSubSteps
     * @param fixedTimeStep
     */
    private void internalUpdateWithFixedTimeStep(float elapsedSeconds, int maxSubSteps, float fixedTimeStep) {
        int numSubSteps = 0;
        if (maxSubSteps != 0) {
            // fixed timestep with interpolation
            localTime += elapsedSeconds;
            if (localTime >= fixedTimeStep) {
                numSubSteps = (int) (localTime / fixedTimeStep);
                localTime -= numSubSteps * fixedTimeStep;
            }
        }
        if (numSubSteps != 0) {
            // clamp the number of substeps, to prevent simulation grinding spiralling down to a halt
            int clampedSubSteps = (numSubSteps > maxSubSteps) ? maxSubSteps : numSubSteps;
            for (int i = 0; i < clampedSubSteps; i++) {
                update(fixedTimeStep);
            }
        }
    }

    /**
     * Calls render with Graphics2D context and takes care of double buffering.
     */
    private void internalUpdateGraphicsInterpolated2() {
        BufferStrategy bf = this.getBufferStrategy();
        Graphics2D g = null;
        try {
            g = (Graphics2D) bf.getDrawGraphics();
            render(g, localTime);
        } finally {
            g.dispose();
        }
        // Shows the contents of the backbuffer on the screen.
        bf.show();
        //Tell the System to do the Drawing now, otherwise it can take a few extra ms until
        //Drawing is done which looks very jerky
        Toolkit.getDefaultToolkit().sync();
    }
    private void  internalUpdateGraphicsInterpolated(){
        jPanel.setInterpolationTime(localTime);
        jPanel.repaint();
    }


    /**
     * init Game (override/replace)
     */
    protected void init() {
        balls = new Ball[10];
        int r = 20;
        for (int i = 0; i < balls.length; i++) {
            Ball ball = new Ball(getWidth() / 2, i * 2.5f * r + 80, 10 + i * 300 / balls.length, 0, r);
            balls[i] = ball;
        }
        ballStroke = new BasicStroke(3);
        this.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                showMode = ((showMode + 1) % 3);
            }
        });
    }
    /**
     * update game. elapsedTime is fixed.  (override/replace)
     * @param elapsedTime
     */
    protected void update(float elapsedTime) {
        for (Ball ball : balls) {
            ball.x += ball.vX * elapsedTime;
            ball.y += ball.vY * elapsedTime;
            if (ball.x > getWidth() - ball.r) {
                ball.vX *= -1;
            }
            if (ball.x < ball.r) {
                ball.vX *= -1;
            }

            if (ball.y > getHeight() - ball.r) {
                ball.vY *= -1;
            }
            if (ball.y < ball.r) {
                ball.vY *= -1;
            }
        }
    }

    /**
     * render the game  (override/replace)
     * @param g
     * @param interpolationTime time of the rendering within a fixed timestep (in seconds)
     */
    protected void render(Graphics2D g, float interpolationTime) {
        g.clearRect(0, 0, getWidth(), getHeight());
        if (showMode == 0) {
            g.drawString("red: raw, black: interpolated (click to switch modes)", 20, 50);
        }
        if (showMode == 1) {
            g.drawString("red: raw (click to switch modes)", 20, 50);
        }
        if (showMode == 2) {
            g.drawString("black: interpolated (click to switch modes)", 20, 50);
        }
        for (Ball ball : balls) {
            g.setStroke(ballStroke);
            if (showMode == 0 || showMode == 1) {
                //w/o interpolation
                g.setColor(Color.RED);
                g.drawOval((int) (ball.x - ball.r), (int) (ball.y - ball.r), (int) ball.r * 2, (int) ball.r * 2);
            }
            if (showMode == 0 || showMode == 2) {
                //with interpolation
                g.setColor(Color.BLACK);
                g.drawOval((int) (ball.x - ball.r + interpolationTime * ball.vX), (int) (ball.y - ball.r + interpolationTime * ball.vY), (int) ball.r * 2, (int) ball.r * 2);
            }
        }

    }

    public static class Ball {

        public float x, y, vX, vY, r;

        public Ball(float x, float y, float vX, float vY, float r) {
            this.x = x;
            this.y = y;
            this.vX = vX;
            this.vY = vY;
            this.r = r;
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            PongGameLoop game = new PongGameLoop();
            game.setVisible(true);
            game.start(1 / 60f, 5);
        });
    }

    private BufferedImage toCompatibleImage(BufferedImage image)
    {
        // obtain the current system graphical settings
        GraphicsConfiguration gfx_config = GraphicsEnvironment.
                getLocalGraphicsEnvironment().getDefaultScreenDevice().
                getDefaultConfiguration();

    /*
     * if image is already compatible and optimized for current system
     * settings, simply return it
     */
        if (image.getColorModel().equals(gfx_config.getColorModel()))
            return image;

        // image is not optimized, so create a new image that is
        BufferedImage new_image = gfx_config.createCompatibleImage(
                image.getWidth(), image.getHeight(), image.getTransparency());

        // get the graphics context of the new image to draw the old image on
        Graphics2D g2d = (Graphics2D) new_image.getGraphics();

        // actually draw the image and dispose of context no longer needed
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        // return the new optimized image
        return new_image;
    }
}
