package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GuiPanel extends JPanel {

    //variables
    private ArrayList<GuiButton> buttons;

    //constructor
    public GuiPanel(){
        buttons = new ArrayList<GuiButton>();
    }

    //add és remove
    public void add(GuiButton button){
        buttons.add(button);
    }

    public void remove(GuiButton button){
        buttons.remove(button);
    }

    //mouse things
    public void mousePressed(MouseEvent e){
        for(GuiButton b : buttons){
            b.mousePressed(e);
        }
    }

    public void mouseReleased(MouseEvent e){
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).mouseReleased(e);
        }
    }

    public void mouseDragged(MouseEvent e){
        for(GuiButton b : buttons){
            b.mouseDragged(e);
        }
    }

    public void mouseMoved(MouseEvent e){
        for(GuiButton b : buttons){
            b.mouseMoved(e);
        }
    }

    //render és update
    public void update(){
        for(GuiButton b : buttons){
            b.update();
        }
    }

    public void render(Graphics2D g){
        for(GuiButton b : buttons){
            b.render(g);
        }
    }
}
