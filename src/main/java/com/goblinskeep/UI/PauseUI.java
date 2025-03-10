package com.goblinskeep.UI;

import com.goblinskeep.app.GamePanel;

import java.awt.*;

public class PauseUI extends DefaultUI {

    public PauseUI(GamePanel gp){
        super(gp);
        totalSelections = 3;
    }

    public void draw(Graphics2D g2) {

        borderThickness = 3;
        g2.setStroke(new BasicStroke(3));
        g2.setFont(UIFont);
        String title = "PAUSED";
        int x = getCenteredXAxisText(title, g2);
        int y = gp.tileSize * 3;
        g2.setColor(Color.WHITE);
        drawTextWithBorder(g2,title, x, y);

        String resume = "RESUME";
        String menu = "BACK TO MENU";
        String restart = "RESTART";

        borderThickness = 2;
        g2.setFont(UIFont.deriveFont(40f));
        drawTextWithBorder(g2,resume, getCenteredXAxisText(resume, g2), gp.tileSize * 8);
        if(cursorSelection == 0){
            drawTextWithBorder(g2,">",getCenteredXAxisText(resume, g2) - gp.tileSize, gp.tileSize * 8);
        }

        drawTextWithBorder(g2,restart, getCenteredXAxisText(restart, g2), gp.tileSize * 9);
        if(cursorSelection == 1){
            drawTextWithBorder(g2,">",getCenteredXAxisText(restart, g2) - gp.tileSize, gp.tileSize * 9);
        }

        drawTextWithBorder(g2,menu, getCenteredXAxisText(menu, g2), gp.tileSize * 10);
        if(cursorSelection == 2){
            drawTextWithBorder(g2,">",getCenteredXAxisText(menu, g2) - gp.tileSize, gp.tileSize * 10);
        }

    }

    @Override
    public Options getCurrentOption() {
        switch (cursorSelection){
            case 0:
                return Options.RESUME;
            case 1:
                return Options.RESTART;
            case 2:
                return Options.MENU;
        }
        return null;
    }
}
