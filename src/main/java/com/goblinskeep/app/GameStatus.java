package com.goblinskeep.app;

/**
 * Represents the different states of the game.
 * Used to control game flow and rendering behavior.
 */
public enum GameStatus {

    /** The game is currently in progress. */
    PLAYING,

    /** The game is temporarily paused. */
    PAUSED,

    /** The game is displaying the main menu. */
    MENU,

    /** The game has ended showing the game over screen. */
    END,

    /** The game is restarting from the beginning. */
    RESTART,

    /** The game is displaying instructions to the player. */
    INSTRUCTIONS
}
