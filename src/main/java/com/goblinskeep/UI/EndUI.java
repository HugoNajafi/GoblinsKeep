package com.goblinskeep.UI;

import com.goblinskeep.app.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class EndUI extends DefaultUI {
    private BufferedImage winBackgroundImage;
    private BufferedImage loseBackgroundImage;

    public EndUI(GamePanel gp){
        super(gp);
        loadBackgroundImages();
        totalSelections = 3;
    }

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

        borderThickness = 3;
        g2.setStroke(new BasicStroke(3));
        int x = getCenteredXAxisText(title, g2);
        int y = gp.tileSize * 2;
        g2.setColor(Color.WHITE);
        drawTextWithBorder(g2,title, x, y);
        drawTextWithBorder(g2,title2, getCenteredXAxisText(title2,g2), y + gp.tileSize + gp.tileSize/2);

        borderThickness = 2;
        g2.setFont(gameFont.deriveFont(20f));
        int keysCollected = gp.map.getKeysCollected();
        int regularScore = gp.map.getScore();
        int totalScore = keysCollected * 250 + regularScore;
        int playTime =  (int) gp.ui.playTime;
        y += 3 * gp.tileSize;
        String scoreText = "Total Score: " + totalScore;
        String scoreBreakdown = "Keys Collected: " + keysCollected + " * 250 = " + (keysCollected * 250);
        String scoreBreakdown2 = "Regular Score: " + regularScore;
        String time = String.format("Time: %02d:%02d", playTime / 60, playTime % 60);
        drawTextWithBorder(g2, "Score Breakdown:", getCenteredXAxisText(scoreText, g2) - gp.tileSize * 5 + 17, y + gp.tileSize * 2);
        drawTextWithBorder(g2, scoreBreakdown, getCenteredXAxisText(scoreBreakdown, g2) - 3 * gp.tileSize, y + gp.tileSize * 2 + gp.tileSize/2);
        drawTextWithBorder(g2, scoreBreakdown2, getCenteredXAxisText(scoreBreakdown, g2) - 3 * gp.tileSize, y + gp.tileSize * 3);

        g2.setFont(gameFont.deriveFont(30f));
        drawTextWithBorder(g2, scoreText, getCenteredXAxisText(scoreText, g2) ,  gp.tileSize * 5);
        drawTextWithBorder(g2, time , getCenteredXAxisText(time, g2) , gp.tileSize * 6);

        String restart = "RESTART";
        String menu = "BACK TO MENU";
        String quit = "QUIT";


        borderThickness = 2;
        g2.setFont(gameFont.deriveFont(30f));
        drawTextWithBorder(g2,restart, getCenteredXAxisText(restart, g2), gp.tileSize * 9);
        if(cursorSelection == 0){
            drawTextWithBorder(g2,">",getCenteredXAxisText(restart, g2) - gp.tileSize, gp.tileSize * 9);
        }

        drawTextWithBorder(g2,menu, getCenteredXAxisText(menu, g2), gp.tileSize * 10);
        if(cursorSelection == 1){
            drawTextWithBorder(g2,">",getCenteredXAxisText(menu, g2) - gp.tileSize, gp.tileSize * 10);
        }

        drawTextWithBorder(g2,quit, getCenteredXAxisText(quit, g2), gp.tileSize * 11);
        if(cursorSelection == 2){
            drawTextWithBorder(g2,">",getCenteredXAxisText(quit, g2) - gp.tileSize, gp.tileSize * 11);
        }

    }

    private void loadBackgroundImages() {
        try {
            winBackgroundImage = ImageIO.read(getClass().getResourceAsStream("/backgrounds/win_background.png"));
            loseBackgroundImage = ImageIO.read(getClass().getResourceAsStream("/backgrounds/lose_background.png"));
        } catch (Exception e) {
            System.out.println("Need End screen images");
        }
    }

    private void drawBackground(Graphics g2, BufferedImage background){
        if (background != null) {
            g2.drawImage(background, 0, 0, gp.screenWidth, gp.screenHeight, null);
        }
    }

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
