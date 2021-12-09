package gui;

import gameCore.Game;
import gameCore.GameBoard;
import gameCore.ScoreManager;
import other.DrawUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PlayPanel extends GuiPanel {

    //variables
    private GameBoard board;
    private BufferedImage info;
    private ScoreManager scores;
    private Font scoreFont;

    private GuiButton tryAgain;
    private GuiButton mainMenu;

    private int spacing = 20;
    private int largeButtonWidth = 340;
    private int buttonHeight = 50;

    private boolean added;
    private int alpha = 0;
    private Font gameOverFont;

    //constructor
    public PlayPanel() {
        scoreFont = Game.main.deriveFont(24f);
        gameOverFont = Game.main.deriveFont(70f);
        board = new GameBoard(Game.WIDTH / 2 - GameBoard.BOARD_WIDTH / 2, Game.HEIGHT - GameBoard.BOARD_HEIGHT - 20);
        //System.out.println("playpanel const");
        scores = board.getScores();

        info = new BufferedImage(Game.WIDTH, 200, BufferedImage.TYPE_INT_RGB);

        mainMenu = new GuiButton(Game.WIDTH / 2 - largeButtonWidth / 2, 450, largeButtonWidth, buttonHeight);
        tryAgain = new GuiButton(mainMenu.getX(), mainMenu.getY() - spacing - buttonHeight, largeButtonWidth, buttonHeight);

        tryAgain.setText("Try Again");
        mainMenu.setText("Back to Main Menu");

        tryAgain.addActionListener(e -> {

            board.reset();
            alpha = 0;

            remove(tryAgain);
            remove(mainMenu);

            added = false;
        });

        mainMenu.addActionListener(e -> GuiScreen.getInstance().setCurrentPanel("Menu"));
    }

    //drawing
    private void drawGui(Graphics2D g) {
        Graphics2D g2d = (Graphics2D) info.getGraphics();
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, info.getWidth(), info.getHeight());
        g2d.setColor(Color.lightGray);
        g2d.setFont(scoreFont);
        g2d.drawString("Score: " + scores.getLastScore(), 30, 40);
        g2d.setColor(Color.red);
        g2d.drawString("Best: " + scores.getHighScore(), Game.WIDTH - DrawUtils.getMessageWidth("Best: " + 100, scoreFont, g2d) - 20, 40);

        g2d.dispose();
        g.drawImage(info, 0, 0, null);
    }

    public void drawGameOver(Graphics2D g) {
        g.setColor(new Color(222, 222, 222, alpha));
        g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
        g.setColor(Color.red);
        g.drawString("Game Over!", Game.WIDTH / 2 - DrawUtils.getMessageWidth("Game Over!", gameOverFont, g) / 2, 250);
    }

    //render és update
    @Override
    public void update() {
        board.update();

        //fade
        if (board.isDead()) {
            alpha++;
            if (alpha > 170) alpha = 170;
        }
    }

    @Override
    public void render(Graphics2D g) {
        drawGui(g);
        board.render(g);
        if (board.isDead()) {
            if (!added) {
                added = true;
                add(mainMenu);
                add(tryAgain);
            }
            drawGameOver(g);
        }
        super.render(g);
    }

}
