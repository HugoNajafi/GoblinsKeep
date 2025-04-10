package com.goblinskeep.app;

import com.goblinskeep.entity.Goblin;
import com.goblinskeep.objects.MainObject;
import com.goblinskeep.objects.Bonus;
import com.goblinskeep.tile.TileManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit and integration tests for validating the functionality of the MapHandler class.
 * */
public class MapGeneratorTest {

    private GamePanel gp;
    private MapHandler map;

    /**
     * Initializes the GamePanel and gets its internal MapHandler before each test.
     */
    @BeforeEach
    void setup() {
        gp = new GamePanel();
        map = new MapHandler(gp); // Ensure MapHandler is properly instantiated
        gp.map = map; // Set the map in GamePanel
    }


    // === Map Parsing ===

    /**
     * Ensures that each tile number is correctly mapped to a tile with the expected collision behavior.
     * For example, tile 1 (wall) should have collision enabled, while tiles like 0 or 8 should not.
     */
    @Test
    void tileMappingIsCorrect() {
        TileManager tileM = gp.tileM;
        int[][] tileNums = tileM.mapTileNum;

        for (int x = 0; x < gp.maxWorldCol; x++) {
            for (int y = 0; y < gp.maxWorldRow; y++) {
                int num = tileNums[x][y];
                if (num == 1) {
                    assertTrue(tileM.checkCollisionOfTile(num), "Wall tiles should have collision");
                } else if (num == 0 || num == 8 || num == 9 || num == 14) {
                    assertFalse(tileM.checkCollisionOfTile(num), "Non-wall tiles should not have collision");
                }
            }
        }
    }
//
    /**
     * Verifies that the parsed tile map matches the dimensions defined by the world size constants.
     */
    @Test
    void mapDimensionsMatchGameWorld() {
        assertEquals(gp.maxWorldCol, gp.tileM.mapTileNum.length, "Column size mismatch");
        assertEquals(gp.maxWorldRow, gp.tileM.mapTileNum[0].length, "Row size mismatch");
    }



    /**
     * Ensures that all goblins are spawned on tiles that are walkable,
     * i.e., tile code 0 after map processing.
     */
    @Test
    void goblinSpawnPointsAreSetCorrectly() {
        for (Iterator<Goblin> it = gp.getGoblinIterator(); it.hasNext(); ) {
            Goblin goblin = it.next();
            int x = goblin.getX() / gp.tileSize;
            int y = goblin.getY() / gp.tileSize;
            int tileNum = gp.tileM.mapTileNum[x][y];
            assertEquals(0, tileNum, "Goblin spawn tile should be walkable (non-wall)");
        }
    }


    /**
     * Ensures the initial state of the map generator is correct.
     */
    @Test
    void initialState(){
        assertEquals(0, map.getScore());
        assertFalse(map.isGameWin());
        assertFalse(map.gameEnded());
        assertEquals(0, map.getKeysCollected());
    }

    /**
     * Verifies that collecting a key increases the key count by one.
     */
    @Test
    void keyCollectedIncreasesCount() {
        int initialKeys = map.getKeysCollected();
        map.keyCollected();
        assertEquals(initialKeys + 1, map.getKeysCollected(), "Key count should increase by one when a key is collected");
    }

    /**
     * Tests that touching the lever with sufficient keys changes the lever's image.
     */
    @Test
    void leverTouchedWithSufficientKeys() {
        for (int i = 0; i < map.keysNeeded; i++){
            map.keyCollected();
        }
        MainObject lever = gp.obj.findLever();
        BufferedImage initialImage = lever.image;
        map.leverTouched();
        assertNotEquals(initialImage, lever.image);
    }

    /**
     * Tests that touching the lever with insufficient keys does not change the lever's image.
     */
    @Test
    void leverTouchedWithInsufficientKeys() {
        for (int i = 0; i < map.keysNeeded - 1; i++){
            map.keyCollected();
        }
        MainObject lever = gp.obj.findLever();
        BufferedImage initialImage = lever.image;
        map.leverTouched();
        assertEquals(initialImage, lever.image);
    }

    /**
     * Verifies that touching the exit when it is open ends the game and marks it as won.
     */
    @Test
    void exitTouchedWhenOpen() {
        for (int i = 0; i < map.keysNeeded; i++){
            map.keyCollected();
        }
        map.leverTouched();
        map.exitTouched();
        assertTrue(map.gameEnded(), "Game should end when exit is touched and open");
        assertTrue(map.isGameWin(), "Game should be won when exit is touched and open");
    }

    /**
     * Verifies that touching the exit when it is closed does not end the game.
     */
    @Test
    void exitTouchedWhenClosed() {
        map.exitTouched();
        assertFalse(map.gameEnded(), "Game should not end when exit is touched and closed");
    }

    /**
     * Ensures that the game ends and is not won when the player collides with an enemy.
     */
    @Test
    void playerCollisionWithEnemyEndsGame() {
        map.playerCollisionWithEnemy();
        assertTrue(map.gameEnded(), "Game should end when player collides with enemy");
        assertFalse(map.isGameWin(), "Game should not be won when player collides with enemy");
    }

    /**
     * Verifies that collecting a bonus increases the score by 100 points.
     */
    @Test
    void collectedBonusIncreasesScore() {
        Bonus bonus = new Bonus(0, 10);
        bonus.updateState(0);
        map.collectedBonus(bonus);
        assertEquals(100, map.getScore(), "Score should increase by 100 when a bonus is collected");
    }

    /**
     * Ensures that hitting a trap deducts 50 points from the score.
     */
    @Test
    void trapHitDeductsPoints() {
        int initialScore = map.getScore();
        map.trapHit();
        assertEquals(initialScore - 50, map.getScore(), "Score should decrease by 50 when a trap is hit");
    }

    /**
     * Verifies that the game ends when the score becomes negative after hitting a trap.
     */
    @Test
    void negativePointsEndGame() {
        map.trapHit();
        assertTrue(map.getScore() < 0, "Score should decrease by 50 when a trap is hit");
        assertTrue(map.gameEnded());
        assertFalse(map.isGameWin());
    }

    /**
     * Ensures that hitting a trap again during the cooldown period does not affect the score.
     */
    @Test
    void trapCoolDown() {
        Bonus bonus = new Bonus(0, 10);
        map.collectedBonus(bonus);
        map.trapHit();
        int firstHit = map.getScore();
        map.trapHit();
        assertEquals(firstHit, map.getScore());
    }

    /**
     * Verifies that hitting a trap after the cooldown period deducts points again.
     */
    @Test
    void trapCoolDownFinished(){
        int initialScore = map.getScore();
        map.trapHit();
        for (int i = 0; i < 120; i++){
            map.updateTimer();
        }
        map.trapHit();
        assertEquals(initialScore - 100, map.getScore());
    }

    /**
     * Ensures that the update method increments the time and affects the score accordingly.
     */
    @Test
    void updateIncrementsTime() {
        Bonus bonus = new Bonus(1, 10);
        map.collectedBonus(bonus);
        assertNotEquals(100, map.getScore());
        for (int i = 0; i < 60; i++){
            map.updateTimer();
        }
        map.collectedBonus(bonus);
        assertEquals(100, map.getScore(), "Score should increase by 100 when a bonus is collected at time 1");
    }

}
