package com.goblinskeep.UI;

import com.goblinskeep.app.GamePanel;
import com.goblinskeep.objects.Key;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class InstructionsUI extends DefaultUI{
    GamePanel gp;
    BufferedImage keyImage;
    private BufferedImage backgroundImage;

    double playTime;
    public InstructionsUI(GamePanel gp) {
        super(gp);
        this.gp = gp;
        Key key = new Key();
        keyImage = key.image;
        totalSelections = 1;
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
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // Reset the composite to full opacity
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        g2.setStroke(new BasicStroke(3));
        g2.setFont(gameFont.deriveFont(60f));
        String title = "INSTRUCTIONS";
        int x = getCenteredXAxisText(title, g2);
        int y = gp.tileSize * 2;
        g2.setColor(Color.WHITE);
        drawTextWithBorder(g2,title, x, y);



        String menu = "BACK TO MENU";

        borderThickness = 2;
        g2.setFont(gameFont.deriveFont(40f));
        drawTextWithBorder(g2,menu, getCenteredXAxisText(menu, g2), gp.tileSize * 11);
        if(cursorSelection == 0){
            drawTextWithBorder(g2,">",getCenteredXAxisText(menu, g2) - gp.tileSize, gp.tileSize * 11);
        }

    }

    @Override
    public Options getCurrentOption() {
        switch (cursorSelection){
            case 0:
                return Options.MENU;
        }
        return null;
    }
}