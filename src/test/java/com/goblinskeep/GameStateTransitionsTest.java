package com.goblinskeep;

import com.goblinskeep.UI.*;
import com.goblinskeep.app.GamePanel;
import com.goblinskeep.app.GameStatus;
import com.goblinskeep.keyboard.MenuInputHandler;

import java.awt.event.KeyEvent;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


/**
 * Unit tests for verifying the correct game state transitions in response to
 * various user inputs during different game phases such as the main menu,
 * pause menu, and end menu.
 *
 * <p>This class ensures that:
 * <ul>
 *     <li>Game starts or navigates to instructions from the main menu.</li>
 *     <li>Game can be paused/resumed using 'P' or 'Esc' keys during play.</li>
 *     <li>Pause menu options resume, restart, or return to main menu as expected.</li>
 *     <li>End menu options restart the game or return to the main menu.</li>
 * </ul>
 *
 * <p>The logic for state transitions is verified using the {@code MenuInputHandler}
 * by simulating key events and menu selections. All assertions ensure that the
 * {@code GamePanel.status} reflects the expected {@code GameStatus} after each action.
 *
 * <p>Note: The transition logic itself is assumed to be correct and is not modified here.
 */
public class GameStateTransitionsTest {

    /**
     * Utility method to create a KeyEvent for simulation purposes.
     *
     * @param keyCode the integer code of the key to simulate
     * @return a KeyEvent representing a key press of the given keyCode
     */
    private KeyEvent createKeyEvent(int keyCode) {
        return new KeyEvent(
                new java.awt.Canvas(),         // source (dummy AWT component)
                KeyEvent.KEY_PRESSED,          // event type
                System.currentTimeMillis(),    // timestamp
                0,                             // modifiers (none)
                keyCode,                       // the key code you pass in
                KeyEvent.CHAR_UNDEFINED        // key char (optional)
        );
    }


    // --- Main Menu options testing ---

    @Test
    void gameStartsWhenPLAYOptionSelected()
    {
        GamePanel gp = new GamePanel();//Status is MENU by default
        MenuInputHandler inputHandler = new MenuInputHandler(gp);
        MenuUI mUI = new MenuUI(gp);
        mUI.cursorSelection = 0; // Index for Options.RESTART (starts game)

        inputHandler.handleMenuKeyEvent(mUI,GameStatus.MENU,KeyEvent.VK_ENTER);

        assertEquals(GameStatus.RESTART, gp.status);

    }

    @Test
    void instructionsPageShowsWhenINSTRUCTIONSOptionSelected()
    {
        GamePanel gp = new GamePanel();//Status is MENU by default
        MenuInputHandler inputHandler = new MenuInputHandler(gp);
        MenuUI mUI = new MenuUI(gp);
        mUI.cursorSelection = 1; //Index for Options.INSTRUCTIONS

        inputHandler.handleMenuKeyEvent(mUI,GameStatus.MENU,KeyEvent.VK_ENTER);

        assertEquals(GameStatus.INSTRUCTIONS, gp.status);
    }


    // --- Pause/Resume toggling using key presses while playing ---

    @Test
    void statusPausedWhenEscIsPressed() {
        GamePanel gp = new GamePanel();
        gp.status = GameStatus.PLAYING;
        MenuInputHandler inputHandler = new MenuInputHandler(gp);

        KeyEvent event = createKeyEvent(KeyEvent.VK_ESCAPE);
        inputHandler.keyPressed(event);

        assertEquals(GameStatus.PAUSED, gp.status);
    }

    @Test
    void statusPausedWhenPIsPressed(){
        GamePanel gp = new GamePanel();
        gp.status = GameStatus.PLAYING;
        MenuInputHandler inputHandler = new MenuInputHandler(gp);

        KeyEvent event = createKeyEvent(KeyEvent.VK_P);
        inputHandler.keyPressed(event);

        assertEquals(GameStatus.PAUSED, gp.status);
    }

    @Test
    void statusPlayingWhenEscIsPressedAfterPause(){
        GamePanel gp = new GamePanel();
        gp.status = GameStatus.PAUSED;
        MenuInputHandler inputHandler = new MenuInputHandler(gp);

        KeyEvent event = createKeyEvent(KeyEvent.VK_ESCAPE);
        inputHandler.keyPressed(event);

        assertEquals(GameStatus.PLAYING, gp.status);
    }

    @Test
    void statusPlayingWhenPIsPressedAfterPause(){
        GamePanel gp = new GamePanel();
        gp.status = GameStatus.PAUSED;
        MenuInputHandler inputHandler = new MenuInputHandler(gp);

        KeyEvent event = createKeyEvent(KeyEvent.VK_P);
        inputHandler.keyPressed(event);

        assertEquals(GameStatus.PLAYING, gp.status);
    }


    // --- Pause Menu options testing ---

    @Test
    void gameResumesWhenOptionRESUMESelectedAtPauseMenu()
    {
        GamePanel gp = new GamePanel();
        gp.status = GameStatus.PAUSED;

        MenuInputHandler inputHandler = new MenuInputHandler(gp);

        PauseUI pUI = new PauseUI(gp);
        pUI.cursorSelection = 0; //Index for Options.RESUME

        inputHandler.handleMenuKeyEvent(pUI,GameStatus.PAUSED,KeyEvent.VK_ENTER);

        assertEquals(GameStatus.PLAYING, gp.status);
    }

    @Test
    void gameRestartsWhenRESTARTOptionSelectedAtPauseMenu()
    {
        GamePanel gp = new GamePanel();
        gp.status = GameStatus.PAUSED;

        MenuInputHandler inputHandler = new MenuInputHandler(gp);

        PauseUI pUI = new PauseUI(gp);
        pUI.cursorSelection = 1; //Index for Options.RESTART

        inputHandler.handleMenuKeyEvent(pUI,GameStatus.PAUSED,KeyEvent.VK_ENTER);

        assertEquals(GameStatus.RESTART, gp.status);
    }


    @Test
    void backToMainMenuWhenBACKTOMENUSelectedAtPauseMenu()
    {
        GamePanel gp = new GamePanel();
        gp.status = GameStatus.PAUSED;

        MenuInputHandler inputHandler = new MenuInputHandler(gp);

        PauseUI pUI = new PauseUI(gp);
        pUI.cursorSelection = 2; //Index for Options.MENU

        inputHandler.handleMenuKeyEvent(pUI,GameStatus.PAUSED,KeyEvent.VK_ENTER);

        assertEquals(GameStatus.MENU, gp.status);
    }


    // --- End Menu options testing ---

    @Test
    void gameRestartsWhenRESTARTOptionSelectedAtEndMenu()
    {
        GamePanel gp = new GamePanel();
        gp.status = GameStatus.END;

        MenuInputHandler inputHandler = new MenuInputHandler(gp);

        EndUI eUI = new EndUI(gp);
        eUI.cursorSelection = 0; //Index for Options.RESTART

        inputHandler.handleMenuKeyEvent(eUI,GameStatus.END,KeyEvent.VK_ENTER);

        assertEquals(GameStatus.RESTART, gp.status);
    }

    @Test
    void backToMainMenuWhenBACKTOMENUOptionSelectedAtEndMenu()
    {
        GamePanel gp = new GamePanel();
        gp.status = GameStatus.END;

        MenuInputHandler inputHandler = new MenuInputHandler(gp);

        EndUI eUI = new EndUI(gp);
        eUI.cursorSelection = 1; //Index for Options.MENU

        inputHandler.handleMenuKeyEvent(eUI,GameStatus.END,KeyEvent.VK_ENTER);

        assertEquals(GameStatus.MENU, gp.status);
    }

}
