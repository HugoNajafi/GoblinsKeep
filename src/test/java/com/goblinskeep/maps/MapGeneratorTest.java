
package com.goblinskeep.maps;

import com.goblinskeep.app.GamePanel;
import com.goblinskeep.app.MapGenerator;
import com.goblinskeep.entity.Goblin;
import com.goblinskeep.objects.MainObject;
import com.goblinskeep.tile.TileManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit and integration tests for validating the functionality of the MapGenerator class.
 * */
public class MapGeneratorTest {

    private GamePanel gp;
    private MapGenerator map;

    /**
     * Initializes the GamePanel and gets its internal MapGenerator before each test.
     */
    @BeforeEach
    void setup() {
        gp = new GamePanel();
        map = gp.map;
    }


    // === Map Parsing ===

    /**
     * Ensures that each tile number is correctly mapped to a tile with the expected collision behavior.
     * For example, tile 1 (wall) should have collision enabled, while tiles like 0 or 8 should not.
     */
    @Test
    void tileMappingIsCorrect() {
        TileManager tileM = map.getTileM();
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

    /**
     * Verifies that the parsed tile map matches the dimensions defined by the world size constants.
     */
    @Test
    void mapDimensionsMatchGameWorld() {
        assertEquals(gp.maxWorldCol, map.getTileM().mapTileNum.length, "Column size mismatch");
        assertEquals(gp.maxWorldRow, map.getTileM().mapTileNum[0].length, "Row size mismatch");
    }


    // === Map Generation ===

    /**
     * Confirms that all objects managed by the ObjectManager:
     * 1. Have keys that correspond to their world position.
     * 2. Are placed on tiles that originally contained object codes in the raw map data.
     */
    @Test
    void allObjectsHaveCorrectWorldCoordinates() {
        Map<String, MainObject> objects = map.getObj().anObject;
        int[][] rawMap = map.rawMapData;

        for (Map.Entry<String, MainObject> entry : objects.entrySet()) {
            MainObject obj = entry.getValue();
            int tileX = obj.worldX / gp.tileSize;
            int tileY = obj.worldY / gp.tileSize;
            String expectedKey = tileX + "," + tileY;

            // Check that key matches the world coordinates
            assertEquals(expectedKey, entry.getKey(), "Object key does not match its world coordinates");

            // Confirm the tile originally held an object-placing tile code
            int tileCode = rawMap[tileX][tileY];
            boolean validCode = tileCode == 2 || tileCode == 3 || tileCode == 4 || tileCode == 5 ||
                    tileCode == 7 || tileCode == 12 || tileCode == 13 || tileCode == 15 ||
                    tileCode == 16 || tileCode == 22;

            assertTrue(validCode,
                    String.format("Unexpected object at tile (%d,%d) with map code %d", tileX, tileY, tileCode));
        }
    }

    /**
     * Validates that the player’s world coordinates match a tile coded as 6
     * in the unmodified raw map data.
     */
    @Test
    void playerSpawnMatchesMap() {
        int playerX = map.getPlayer().WorldX / gp.tileSize;
        int playerY = map.getPlayer().WorldY / gp.tileSize;

        boolean found = (map.rawMapData[playerX][playerY] == 6);
        assertTrue(found, "Player spawn position does not match the map data (tile 6 expected)");
    }

    /**
     * Ensures that all goblins are spawned on tiles that are walkable,
     * i.e., tile code 0 after map processing.
     */
    @Test
    void goblinSpawnPointsAreSetCorrectly() {
        List<Goblin> goblins = map.getGoblins();
        for (Goblin goblin : goblins) {
            int x = goblin.getX() / gp.tileSize;
            int y = goblin.getY() / gp.tileSize;
            int tileNum = map.getTileM().mapTileNum[x][y];
            assertEquals(0, tileNum, "Goblin spawn tile should be walkable (non-wall)");
        }
    }

    /**
     * Validates that all core components required by the map generator
     * are initialized and not null.
     */
    @Test
    void tileManagerAndObjectManagerAreInitialized() {
        assertNotNull(map.getTileM(), "TileManager is not initialized");
        assertNotNull(map.getObj(), "ObjectManager is not initialized");
        assertNotNull(map.getPlayer(), "Player is not initialized");
    }
}
