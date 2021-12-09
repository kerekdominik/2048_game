package gui;

import gameCore.Game;
import other.DrawUtils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuPanel extends GuiPanel {
    //variables
    private Font titleFont = Game.main.deriveFont(100f);

    private String title = "2048";
    private int buttonWidth = 220;

    //constructor
    public MainMenuPanel() {
        super();

        //play gomb
        GuiButton playButton = new GuiButton(Game.WIDTH / 2 - buttonWidth / 2, 220, buttonWidth, 60);
        playButton.addActionListener(e -> GuiScreen.getInstance().setCurrentPanel("Play"));
        playButton.setText("Play");
        add(playButton);

        //scores gomb
        GuiButton scoresButton = new GuiButton(Game.WIDTH / 2 - buttonWidth / 2, 310, buttonWidth, 60);
        scoresButton.addActionListener(e -> GuiScreen.getInstance().setCurrentPanel("Leaderboards"));
        scoresButton.setText("Scores");
        add(scoresButton);

        //quit gomb
        GuiButton quitButton = new GuiButton(Game.WIDTH / 2 - buttonWidth / 2, 400, buttonWidth, 60);
        quitButton.addActionListener(e -> System.exit(0));
        quitButton.setText("Quit");
        add(quitButton);
    }

    //render és update
    @Override
    public void render(Graphics2D g){
        super.render(g);
        g.setFont(titleFont);
        g.setColor(Color.black);
        g.drawString(title, Game.WIDTH / 2 - DrawUtils.getMessageWidth(title, titleFont, g) / 2, 150);
    }

}
