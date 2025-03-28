package com.goblinskeep.pathFinder;

import com.goblinskeep.app.GamePanel;
import com.goblinskeep.app.GameStatus;
import com.goblinskeep.entity.Goblin;
import com.goblinskeep.entity.RegularGoblin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class pathFinderTest {
    private GamePanel gp;
    private pathFinder pf;

    @BeforeEach
    void setUp() {
        gp = new GamePanel();
        pf = new pathFinder(gp);
    }

    @Test
    void testCreateNodes() {
        pf.createNodes();
        assertNotNull(pf.node);
        assertEquals(gp.maxWorldCol, pf.node.length);
        assertEquals(gp.maxWorldRow, pf.node[0].length);
    }

    @Test
    void testResetNodes() {
        pf.createNodes();
        pf.node[0][0].open = true;
        pf.node[0][0].explored = true;
        pf.node[0][0].solid = true;

        pf.resetNodes();
        assertFalse(pf.node[0][0].open);
        assertFalse(pf.node[0][0].explored);
        assertFalse(pf.node[0][0].solid);
    }

    @Test
    void testSetNodes() {
        pf.createNodes();
        pf.setNodes(0, 0, gp.maxWorldCol - 1, gp.maxWorldRow - 1);
        assertNotNull(pf.startNode);
        assertNotNull(pf.goalNode);
        assertEquals(0, pf.startNode.col);
        assertEquals(0, pf.startNode.row);
        assertEquals(gp.maxWorldCol - 1, pf.goalNode.col);
        assertEquals(gp.maxWorldRow - 1, pf.goalNode.row);
    }

    @Test
    void testSearch() {
        pf.createNodes();
        pf.setNodes(0, 0, gp.maxWorldCol - 1, gp.maxWorldRow - 1);
        boolean result = pf.search();
        assertTrue(result);
        assertTrue(pf.goalReached);
    }


    @Test
    void test1SearchPath() {
       pathFinder pf = gp.pathFinder;
       RegularGoblin goblin = new RegularGoblin(gp, gp.Player);
        int goalCol = (gp.Player.WorldX + gp.Player.hitboxDefaultX +
                (gp.Player.collisionArea.width / 2)) / gp.tileSize;
        int goalRow = (gp.Player.WorldY + gp.Player.hitboxDefaultY +
                (gp.Player.collisionArea.height / 2)) / gp.tileSize;

        // Set goblin position
        goblin.onPath = true;
        int startCol = 43;
        int startRow = 18;
        startCol = 130/2;
        startRow = 45;
        goblin.WorldX = startCol * gp.tileSize;
        goblin.WorldY = startRow * gp.tileSize;
        goblin.myPath.clear();
        gp.pathFinder.searchPath(goalCol, goalRow, goblin);
        assertFalse(goblin.myPath.isEmpty(),
                "The goblin's path should contain nodes after searchPath is called successfully");
        for (int i = 0; i < 3000; i++){
            goblin.update();
        }
    }

    @Test
    void testing(){
        gp.status = GameStatus.PLAYING;
        for (int i = 0; i < 2000; i++) {
            gp.update();
        }
//        Goblin goblin = gp.getGoblinIterator().next();
//        List<Goblin> goblinList = new ArrayList<>();
//        for (Iterator<Goblin> it = gp.getGoblinIterator(); it.hasNext(); ) {
//            Goblin g = it.next();
//            goblinList.add(g);
//        }
//        for (int i = 0; i < 2000; i++){
//
//            goblin.onPath =true;
//            goblin.inSight = true;
//            goblin.update();
//        }
    }

    @Test
    void testMotion(){
        gp.startGameThread();

    }

    @Test
    void testGameRunningWithPathfinder() throws InterruptedException {
        // Start the game in a separate thread to avoid blocking the test
        Thread gameThread = new Thread(() -> {
            gp.startGameThread();
        });
        gameThread.start();

        // Create and set up the goblin
        RegularGoblin goblin = new RegularGoblin(gp, gp.Player);
        goblin.onPath = true;

        // Position the goblin
        int startCol = 43;
        int startRow = 18;
        goblin.WorldX = startCol * gp.tileSize;
        goblin.WorldY = startRow * gp.tileSize;
        goblin.hitboxDefaultX = 8;
        goblin.hitboxDefaultY = 16;
        goblin.collisionArea = new Rectangle(goblin.hitboxDefaultX, goblin.hitboxDefaultY, 23, 23);

        // Set goal position
        int goalCol = gp.Player.WorldX/gp.tileSize;
        int goalRow = gp.Player.WorldY/gp.tileSize;

        // Call searchPath
        gp.pathFinder.searchPath(goalCol, goalRow, goblin);

        // Allow the game to run for a short time
        Thread.sleep(5000);

        // Stop the game thread
        gp.gameThread = null;
        gameThread.interrupt();

        // Verify path was created
        assertFalse(goblin.myPath.isEmpty(),
                "The goblin's path should contain nodes after searchPath is called successfully");

        // Additional assertions as needed
        assertNotNull(goblin.direction);
    }
}