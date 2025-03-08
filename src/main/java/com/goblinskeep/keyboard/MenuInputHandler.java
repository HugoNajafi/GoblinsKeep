package com.goblinskeep.keyboard;


import com.goblinskeep.app.GamePanel;
import com.goblinskeep.app.GameStatus;
import com.goblinskeep.UI.DefaultUI;
import com.goblinskeep.UI.EndUI;
import com.goblinskeep.UI.MenuUI;
import com.goblinskeep.UI.PauseUI;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MenuInputHandler implements KeyListener {
    private GamePanel gp;
    private MenuUI menuUI;
    private PauseUI pauseUI;
    private EndUI endUI;
    private boolean cursorRelease = true;


    public MenuInputHandler(GamePanel gp) {
        this.gp = gp;
        menuUI = gp.getMenuUI();
        pauseUI = gp.ui.pauseUI;
        endUI = gp.endUI;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

    @Override
    public void keyPressed(KeyEvent e){
        int code = e.getKeyCode();

        switch(code){
            case KeyEvent.VK_ESCAPE:
            case KeyEvent.VK_P:
                if (gp.status == GameStatus.PLAYING){
                    gp.status = GameStatus.PAUSED;
                } else if (gp.status == GameStatus.PAUSED){
                    gp.status = GameStatus.PLAYING;
                }
                break;
            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_ENTER:
            case KeyEvent.VK_UP:
            case KeyEvent.VK_DOWN:
                handleMenuKeyEvent(menuUI, GameStatus.MENU, code);
                handleMenuKeyEvent(pauseUI, GameStatus.PAUSED, code);
                handleMenuKeyEvent(endUI, GameStatus.END, code);
                break;
        }

    }

    private void handleMenuKeyEvent(DefaultUI ui, GameStatus status, int keyCode) {
        if (gp.status == status) {
            switch (keyCode) {
                //for selecting an option
                case KeyEvent.VK_SPACE:
                case KeyEvent.VK_ENTER:
                    switch (ui.getCurrentOption()) {
                        case RESUME:
                            gp.status = GameStatus.PLAYING;
                            break;
                        case RESTART:
                            gp.status = GameStatus.PLAYING;
                            break;
                        case QUIT:
                            System.exit(0);
                            break;
                        case MENU:
                            gp.status = GameStatus.MENU;
                            break;
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (cursorRelease) {
                        ui.moveCursorUp();
                        cursorRelease = false;
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (cursorRelease) {
                        ui.moveCursorDown();
                        cursorRelease = false;
                    }
                    break;
            }
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_UP || code == KeyEvent.VK_DOWN) {
            cursorRelease = true;
        }
    }



}
