package com.goblinskeep.Keyboard;

import com.goblinskeep.App.GamePanel;
import com.goblinskeep.App.GameStatus;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MenuInputHandler implements KeyListener {
    private GamePanel gp;


    public MenuInputHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

    @Override
    public void keyPressed(KeyEvent e){
        int code = e.getKeyCode();

        switch(code){
            case KeyEvent.VK_P:
                if (gp.status == GameStatus.PLAYING){
                    gp.status = GameStatus.PAUSED;
                } else if (gp.status == GameStatus.PAUSED){
                    gp.status = GameStatus.PLAYING;
                }
                break;
            case KeyEvent.VK_ENTER:
                if (gp.status == GameStatus.MENU){
                    gp.status = GameStatus.PLAYING;
                }
        }

    }


    @Override
    public void keyReleased(KeyEvent e) {

    }



}
