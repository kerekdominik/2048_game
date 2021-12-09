package gui;

import gameCore.Game;
import gameCore.Leaderboards;
import other.DrawUtils;

import java.awt.*;
import java.util.ArrayList;

public class LeaderboardsPanel extends GuiPanel {

    private int backButtonWidth = 220;
    private int buttonY = 120;
    private int buttonHeight = 50;
    private int leaderboardsX = 130;
    private int leaderboardsY = buttonY + buttonHeight + 90;

    private Leaderboards lBoard;
    private String title = "Leaderboards";
    private Font titleFont = Game.main.deriveFont(48f);
    private Font scoreFont = Game.main.deriveFont(30f);


    public LeaderboardsPanel(){
        super();

        lBoard = Leaderboards.getInstance();

        GuiButton backButton = new GuiButton(Game.WIDTH / 2 - backButtonWidth / 2, 500, backButtonWidth, 60);
        backButton.addActionListener(e -> GuiScreen.getInstance().setCurrentPanel("Menu"));
        backButton.setText("Back");
        add(backButton);
    }

    private void drawLeaderboards(Graphics2D g){
        ArrayList<String> strings = convertToStrings(lBoard.getList());

        g.setColor(Color.black);
        g.setFont(scoreFont);

        for(int i = 0; i < strings.size(); i++){
            String s = (i + 1) + ". " + strings.get(i);
            g.drawString(s, leaderboardsX, leaderboardsY + i * 40);
        }
    }

    private ArrayList<String> convertToStrings(ArrayList<? extends Number> list){
        ArrayList<String> ret = new ArrayList<>();
        for(Number n : list){
            ret.add(n.toString());
        }
        return ret;
    }

    @Override
    public void update(){

    }

    @Override
    public void render(Graphics2D g){
        super.render(g);
        g.setColor(Color.black);
        g.drawString(title, Game.WIDTH / 2 - DrawUtils.getMessageWidth(title, titleFont, g) / 2, DrawUtils.getMessageHeight(title, titleFont, g) + 40);
        drawLeaderboards(g);
    }

}
