package com.goblinskeep.UI;

import com.goblinskeep.App.GamePanel;
import com.goblinskeep.App.GameStatus;
import com.goblinskeep.objects.Key;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI extends DefaultUI{
    GamePanel gp;
    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    public int messageCounter = 0;
    public boolean gameFinished = false;
    private Graphics2D g2;
    public PauseUI pauseUI;

    public double playTime;
    public UI(GamePanel gp) {
        super(gp);
        this.gp = gp;
        Key key = new Key();
        keyImage = key.image;
        this.pauseUI = new PauseUI(gp);
    }

    public void showMessage(String text){
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        if (gp.status == GameStatus.PLAYING) {
            drawPlaying(g2);
        } else if (gp.status == GameStatus.PAUSED){
            drawPaused(g2);
        }
    }

    public void drawPlaying(Graphics2D g2) {
        //draw key counter on screen
        borderThickness = 1;
        g2.setFont(gameFont.deriveFont(20f));
        g2.setColor(Color.white);
        g2.drawImage(keyImage, gp.tileSize/2, gp.tileSize/2 - 10, gp.tileSize, gp.tileSize, null);
        drawTextWithBorder(g2, "x = " + gp.map.getKeysCollected(), gp.tileSize * 3/2 , gp.tileSize);
        drawTextWithBorder(g2, "score = " + gp.map.getScore(), gp.tileSize * 6 + gp.tileSize/2 , gp.tileSize);

        //draw the timer on the screen
        playTime += (double) 1/60;
        drawTextWithBorder(g2, String.format("Time: %02d:%02d", (int) playTime / 60, (int) playTime % 60), gp.tileSize * 12, gp.tileSize);

        //draw any messages called
        if (messageOn){
            int oldBorderThickness = borderThickness;
            borderThickness = 1;
            g2.setFont(g2.getFont().deriveFont(10F));
            drawTextWithBorder(g2, message, getCenteredXAxisText(message, g2), gp.Player.screenY - 10);
            messageCounter++;
            if (messageCounter > 120) {
                messageCounter = 0;
                messageOn = false;
            }
            borderThickness = oldBorderThickness;
        }
    }

    public void drawPaused(Graphics2D g2){
        g2.setFont(gameFont.deriveFont(20f));
        g2.setColor(Color.white);
        g2.drawImage(keyImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
        g2.drawString("x = " + gp.map.getKeysCollected(), 74, 65);

        //draw the timer on the screen
        g2.drawString(String.format("Time: %02d:%02d", (int) playTime / 60, (int) playTime % 60), gp.tileSize * 11, 65);

        //opacity
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // Reset the composite to full opacity
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

        pauseUI.draw(g2);

        //draw any messages called

    }

    @Override
    public Options getCurrentOption() {
        return null;
    }
}