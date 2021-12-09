package gameCore;

import control.Keys;
import gui.GuiScreen;
import gui.LeaderboardsPanel;
import gui.MainMenuPanel;
import gui.PlayPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class Game extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener {

    //variables
    public static final int WIDTH = 480;
    public static final int HEIGHT = 630;
    private Thread game;
    private boolean running;
    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    public static final Font main = new Font("Bebas Neue Regular", Font.PLAIN, 28);

    private GuiScreen screen;

    //contructor
    public Game() {
        setFocusable(true);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);

        screen = GuiScreen.getInstance();
        screen.add("Menu", new MainMenuPanel());
        screen.add("Play", new PlayPanel());
        screen.add("Leaderboards", new LeaderboardsPanel());
        screen.setCurrentPanel("Menu");
    }

    //thread things

    @Override
    public void run() {
        int fps = 0, updates = 0;
        long fpsTimer = System.currentTimeMillis();
        double nsPerUpdate = 1000000000.0 / 60;

        // last update time in nanoseconds
        double then = System.nanoTime();
        double unprocessed = 0;

        while (running) {

            boolean shouldRender = false;

            double now = System.nanoTime();
            unprocessed += (now - then) / nsPerUpdate;
            then = now;

            // Update queue
            while (unprocessed >= 1) {

                // update
                updates++;
                update();
                unprocessed--;
                shouldRender = true;
            }

            // Render
            if (shouldRender) {
                fps++;
                render();
                shouldRender = false;
            }
            else {
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // FPS timer
            if (System.currentTimeMillis() - fpsTimer > 1000) {
                /*System.out.printf("%d fps %d updates", fps, updates);
                System.out.println("");*/
                fps = 0;
                updates = 0;
                fpsTimer += 1000;
            }
        }
    }

    public synchronized void start() {
        if (running) return;
        running = true;
        game = new Thread(this, "game");
        game.start();
    }

    public synchronized void stop() {
        if (!running) return;
        running = false;
        System.exit(0);
    }

    //render és update

    private void render() {
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        screen.render(g);
        g.dispose();

        Graphics2D g2d = (Graphics2D) getGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
    }

    private void update() {
        screen.update();
        Keys.update();
    }

    //keyboard things

    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyPressed(KeyEvent e) {
        Keys.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Keys.keyReleased(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }


    //mouse things
    @Override
    public void mousePressed(MouseEvent e) {
        screen.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        screen.mouseReleased(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        screen.mouseDragged(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        screen.mouseMoved(e);
    }

}
