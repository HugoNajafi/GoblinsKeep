package com.goblinskeep.UI;

import com.goblinskeep.app.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Represents the Game ENDED UI component, displaying the win or lose screen.
 */
public class EndUI extends DefaultUI {
    private BufferedImage winBackgroundImage;
    private BufferedImage loseBackgroundImage;

    /**
     * Constructs an EndUI with the specified GamePanel.
     *
     * @param gp the GamePanel instance
     */
    public EndUI(GamePanel gp){
        super(gp);
        loadBackgroundImages();
        totalSelections = 3;
    }

    /**
     * Draws the end game UI.
     *
     * @param g2 the Graphics2D object used for drawing
     */
    public void draw(Graphics2D g2) {
        g2.setFont(gameFont);
        String title = "";
        String title2 = "";
        if(gp.map.isGameWin()){
            drawBackground(g2, winBackgroundImage);
            g2.setFont(gameFont.deriveFont(55f));
            title = "CONGRATULATIONS";
            title2 = "YOU WON!";
        } else {
            drawBackground(g2, loseBackgroundImage);
            title = "YOU LOSE";
        }
        // composite for dimming background image
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.35f));
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // Reset the composite to full opacity
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

        //displaying Title text
        borderThickness = 3;
        g2.setStroke(new BasicStroke(3));
        int x = getCenteredXAxisText(title, g2);
        int y = gp.tileSize * 2;
        g2.setColor(Color.WHITE);
        drawTextWithBorder(g2,title, x, y);
        drawTextWithBorder(g2,title2, getCenteredXAxisText(title2,g2), y + gp.tileSize + gp.tileSize/2);

        //displaying score texts
        borderThickness = 2;
        g2.setFont(UIFont.deriveFont(35f));
        int keysCollected = gp.map.getKeysCollected();
        int regularScore = gp.map.getScore();
        int totalScore = keysCollected * 250 + regularScore;
        int playTime =  (int) gp.ui.playTime;
        y += 3 * gp.tileSize;
        String scoreText = "Total Score: " + totalScore;
        String scoreBreakdown = "Keys Collected: " + keysCollected + " * 250 = " + (keysCollected * 250);
        String scoreBreakdown2 = "Regular Score: " + regularScore;
        String time = String.format("Time: %02d:%02d", playTime / 60, playTime % 60);
        drawTextWithBorder(g2, "Score Breakdown:", getCenteredXAxisText(scoreText, g2) - gp.tileSize * 5 + 19, y + gp.tileSize * 2);
        drawTextWithBorder(g2, scoreBreakdown, getCenteredXAxisText(scoreBreakdown, g2) - 3 * gp.tileSize, y + gp.tileSize * 2 + gp.tileSize/2);
        drawTextWithBorder(g2, scoreBreakdown2, getCenteredXAxisText(scoreBreakdown, g2) - 3 * gp.tileSize, y + gp.tileSize * 3);

        g2.setFont(UIFont.deriveFont(60f));
        drawTextWithBorder(g2, scoreText, getCenteredXAxisText(scoreText, g2) ,  gp.tileSize * 5);
        drawTextWithBorder(g2, time , getCenteredXAxisText(time, g2) , gp.tileSize * 6);


        String restart = "RESTART";
        String menu = "BACK TO MENU";
        String quit = "QUIT";
        String[] options = {restart, menu, quit};

        //displaying UI menu options
        borderThickness = 2;
        g2.setFont(gameFont.deriveFont(30f));
        drawCursorOptionsCentered(g2, options, 9);
    }

    /**
     * Loads the background images for the win and lose screens.
     */
    private void loadBackgroundImages() {
        try {
            winBackgroundImage = ImageIO.read(getClass().getResourceAsStream("/UI_img/win.png"));
            loseBackgroundImage = ImageIO.read(getClass().getResourceAsStream("/UI_img/lose.png"));
        } catch (Exception e) {
            System.out.println("Need End screen images");
        }
    }

    /**
     * Draws the background image.
     *
     * @param g2 the Graphics object used for drawing
     * @param background the background image to draw
     */
    private void drawBackground(Graphics g2, BufferedImage background){
        if (background != null) {
            g2.drawImage(background, 0, 0, gp.screenWidth, gp.screenHeight, null);
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
                return Options.MENU;
            case 2:
                return Options.QUIT;
        }
        return null;
    }
}
