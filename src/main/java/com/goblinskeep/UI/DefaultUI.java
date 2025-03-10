package com.goblinskeep.UI;

import com.goblinskeep.app.GamePanel;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public abstract class DefaultUI {
    Font gameFont;
    Font UIFont;
    GamePanel gp;

    int cursorSelection = 0;
    int totalSelections;
    protected int borderThickness = 3;

    protected DefaultUI(GamePanel gp) {
        this.gp = gp;

        gameFont = createNewFont("/font/SuperPixel-m2L8j.ttf");
        UIFont = createNewFont("/font/pixelpurl.ttf");


    }

    public void moveCursorUp(){
        cursorSelection--;
        if (cursorSelection < 0) {
            cursorSelection = totalSelections - 1;
        }
    }

    public void moveCursorDown(){
        cursorSelection++;
        if (cursorSelection >= totalSelections) {
            cursorSelection = 0;
        }
    }

    public int getCenteredXAxisText(String text, Graphics2D g2){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth/2 - length/2;
    }

    public abstract void draw(Graphics2D g2);

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

    public abstract Options getCurrentOption();

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
