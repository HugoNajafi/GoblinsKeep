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

    /**
     * Draws the main menu UI, including the title, background, and menu options.
     *
     * @param g2 the Graphics2D object used for drawing
     */
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
        String[] options = {play, instructions, quit};

        //draw menu options
        borderThickness = 2;
        g2.setFont(gameFont.deriveFont(40f));
        drawCursorOptionsCentered(g2, options, 8);

    }

    /**
     * Gets the current option selected in the menu.
     *
     * @return the current option, or null if no option is selected
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
