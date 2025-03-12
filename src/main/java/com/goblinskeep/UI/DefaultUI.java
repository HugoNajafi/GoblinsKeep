package com.goblinskeep.UI;

import com.goblinskeep.app.GamePanel;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * Abstract class representing the default UI component in the game.
 */
public abstract class DefaultUI {
    public Font gameFont;
    public Font UIFont;
    protected GamePanel gp;

    public int cursorSelection = 0;
    public int totalSelections;
    protected int borderThickness = 3;

    /**
     * Constructs a DefaultUI with the specified GamePanel.
     *
     * @param gp the GamePanel instance
     */
    protected DefaultUI(GamePanel gp) {
        this.gp = gp;

        gameFont = createNewFont("/font/SuperPixel-m2L8j.ttf");
        UIFont = createNewFont("/font/pixelpurl.ttf");


    }

    /**
     * Moves the cursor up in the UI.
     */
    public void moveCursorUp(){
        cursorSelection--;
        if (cursorSelection < 0) {
            cursorSelection = totalSelections - 1;
        }
    }

    /**
     * Moves the cursor down in the UI.
     */
    public void moveCursorDown(){
        cursorSelection++;
        if (cursorSelection >= totalSelections) {
            cursorSelection = 0;
        }
    }

    /**
     * Gets the centered X-axis position for the given text.
     *
     * @param text the text to center
     * @param g2 the Graphics2D object used for drawing
     * @return the X-axis position to center the text
     */
    public int getCenteredXAxisText(String text, Graphics2D g2){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth/2 - length/2;
    }

    /**
     * Draws the UI component.
     *
     * @param g2 the Graphics2D object used for drawing
     */
    public abstract void draw(Graphics2D g2);

    /**
     * Creates a new font from the specified font file.
     *
     * @param fontFile the path to the font file
     * @return the created Font object
     */
    private Font createNewFont(String fontFile) {
        Font newFont;
        try {

            //load custom font
            InputStream fontName = getClass().getResourceAsStream(fontFile);
            newFont = Font.createFont(Font.TRUETYPE_FONT, fontName).deriveFont(80f);
        } catch (FontFormatException | IOException e) {
            //if font fails
            newFont = new Font("Monospaced", Font.BOLD, 80);
        }
        return newFont;
    }

    /**
     * Gets the current option selected in the menu.
     *
     * @return the current option
     */
    public abstract Options getCurrentOption();

    /**
     * Draws text with a border.
     *
     * @param g2 the Graphics2D object used for drawing
     * @param text the text to draw
     * @param x the X-axis position
     * @param y the Y-axis position
     */
    public void drawTextWithBorder(Graphics2D g2, String text, int x, int y) {
        g2.setColor(Color.BLACK);
        g2.drawString(text, x-borderThickness, y-borderThickness);
        g2.drawString(text, x-borderThickness, y+borderThickness);
        g2.drawString(text, x+borderThickness, y-borderThickness);
        g2.drawString(text, x+borderThickness, y+borderThickness);
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);
    }
}
