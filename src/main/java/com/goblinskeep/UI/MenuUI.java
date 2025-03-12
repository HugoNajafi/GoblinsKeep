package com.goblinskeep.UI;

import com.goblinskeep.app.GamePanel;
import com.goblinskeep.objects.Key;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Represents the main menu UI component.
 */
public class MenuUI extends DefaultUI{
    public GamePanel gp;
    public BufferedImage keyImage;
    private BufferedImage backgroundImage;


    /**
     * Constructs a MenuUI with the specified GamePanel.
     *
     * @param gp the GamePanel instance
     */
    public MenuUI(GamePanel gp) {
        super(gp);
        this.gp = gp;
        Key key = new Key();
        keyImage = key.image;
        totalSelections = 3;
        try {
            backgroundImage = ImageIO.read(getClass().getResourceAsStream("/UI_img/titleScreen.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void draw(Graphics2D g2) {
        if (backgroundImage != null) {
            g2.drawImage(backgroundImage, 0, 0, gp.screenWidth, gp.screenHeight, null);
        }
        borderThickness = 3;
        g2.setStroke(new BasicStroke(3));
        g2.setFont(gameFont);
        String title = "Goblins Keep";
        int x = getCenteredXAxisText(title, g2);
        int y = gp.tileSize * 3;
        g2.setColor(Color.WHITE);
        //draw title text
        drawTextWithBorder(g2,title, x, y);

        String play = "PLAY";
        String instructions = "INSTRUCTIONS";
        String quit = "QUIT";

        //draw menu options
        borderThickness = 2;
        g2.setFont(gameFont.deriveFont(40f));
        drawTextWithBorder(g2,play, getCenteredXAxisText(play, g2), gp.tileSize * 8);
        if(cursorSelection == 0){
            drawTextWithBorder(g2,">",getCenteredXAxisText(play, g2) - gp.tileSize, gp.tileSize * 8);
        }
        drawTextWithBorder(g2,instructions, getCenteredXAxisText(instructions, g2), gp.tileSize * 9);
        if(cursorSelection == 1){
            drawTextWithBorder(g2,">",getCenteredXAxisText(instructions, g2) - gp.tileSize, gp.tileSize * 9);
        }

        drawTextWithBorder(g2,quit, getCenteredXAxisText(quit, g2), gp.tileSize * 10);
        if(cursorSelection == 2){
            drawTextWithBorder(g2,">",getCenteredXAxisText(quit, g2) - gp.tileSize, gp.tileSize * 10);
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
                return Options.RESTART;
            case 1:
                return Options.INSTRUCTIONS;
            case 2:
                return Options.QUIT;
        }
        return null;
    }
}