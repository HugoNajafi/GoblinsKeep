package com.goblinskeep.keyboard;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Handles keyboard input for player movement.
 */
public class PlayerInputHandler implements KeyListener {
    public boolean up, down, left, right;

    /**
     * This method is not used but must be implemented as part of the KeyListener interface.
     *
     * @param e the KeyEvent
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // This method is not used
    }

    /**
     * Handles the event when a key is pressed.
     * Handles arrow keys and WASD for movement
     * Sets the corresponding direction flag to true based on the key pressed.
     *
     * @param e the KeyEvent
     */
    @Override
    public void keyPressed(KeyEvent e){
        int code = e.getKeyCode();

        switch(code){
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                up = true;
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                down = true;
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                left = true;
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                right = true;
                break;
        }

    }


    /**
     * Handles the event when a key is released.
     * Sets the corresponding direction flag to false based on the key released.
     *
     * @param e the KeyEvent
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        switch(code){
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                up = false;
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                down = false;
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                left = false;
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                right = false;
                break;
        }
    }



}
