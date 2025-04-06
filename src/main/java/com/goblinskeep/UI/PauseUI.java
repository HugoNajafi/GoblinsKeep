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
        String[] options = {resume, restart, menu};

        //draw text options
        borderThickness = 2;
        g2.setFont(gameFont.deriveFont(40f));
        drawCursorOptionsCentered(g2, options, 8);

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
