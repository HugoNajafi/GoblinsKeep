package com.goblinskeep.UI;

import com.goblinskeep.app.GamePanel;

import java.awt.*;

/**
 * Represents the pause menu UI component.
 */
public class PauseUI extends DefaultUI {

    /**
     * Constructs a PauseUI with the specified GamePanel.
     *
     * @param gp the GamePanel instance
     */
    public PauseUI(GamePanel gp){
        super(gp);
        totalSelections = 3;
    }

    /**
     * Draws the pause menu UI.
     *
     * @param g2 the Graphics2D object used for drawing
     */
    public void draw(Graphics2D g2) {
        //draw title text
        borderThickness = 3;
        g2.setStroke(new BasicStroke(3));
        g2.setFont(gameFont);
        String title = "PAUSED";
        int x = getCenteredXAxisText(title, g2);
        int y = gp.tileSize * 3;
        g2.setColor(Color.WHITE);
        drawTextWithBorder(g2,title, x, y);

        String resume = "RESUME";
        String menu = "BACK TO MENU";
        String restart = "RESTART";

        //draw text options
        borderThickness = 2;
        g2.setFont(gameFont.deriveFont(40f));
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

    /**
     * Gets the current option selected in the menu.
     *
     * @return the current option
     */
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
