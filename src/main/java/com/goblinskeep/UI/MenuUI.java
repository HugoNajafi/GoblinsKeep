package com.goblinskeep.UI;

import com.goblinskeep.App.GamePanel;
import com.goblinskeep.objects.Key;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MenuUI extends DefaultUI{
    GamePanel gp;
    BufferedImage keyImage;
    private BufferedImage backgroundImage;

    double playTime;
    public MenuUI(GamePanel gp) {
        super(gp);
        this.gp = gp;
        Key key = new Key();
        keyImage = key.image;
        totalSelections = 2;
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
        drawTextWithBorder(g2,title, x, y);

        String play = "PLAY";
        String quit = "QUIT";

        borderThickness = 2;
        g2.setFont(gameFont.deriveFont(40f));
        drawTextWithBorder(g2,play, getCenteredXAxisText(play, g2), gp.tileSize * 8);
        if(cursorSelection == 0){
            drawTextWithBorder(g2,">",getCenteredXAxisText(play, g2) - gp.tileSize, gp.tileSize * 8);
        }

        drawTextWithBorder(g2,quit, getCenteredXAxisText(quit, g2), gp.tileSize * 9);
        if(cursorSelection == 1){
            drawTextWithBorder(g2,">",getCenteredXAxisText(quit, g2) - gp.tileSize, gp.tileSize * 9);
        }





    }

    @Override
    public Options getCurrentOption() {
        switch (cursorSelection){
            case 0:
                return Options.RESTART;
            case 1:
                return Options.QUIT;
        }
        return null;
    }
}