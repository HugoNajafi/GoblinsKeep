// package com.goblinskeep.entity;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import static org.junit.jupiter.api.Assertions.fail;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.anyInt;
// import static org.mockito.ArgumentMatchers.eq;
// import static org.mockito.Mockito.*;
// import java.awt.Graphics2D;
// import java.awt.Image;
// import java.awt.image.BufferedImage;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import com.goblinskeep.app.GamePanel;

// public class testGoblin {
//     // Mocked dependencies
//     private GamePanel gp;
//     private Goblin regularGoblin;

//     @BeforeEach
//     void setUp(){
//         gp = new GamePanel();
//         regularGoblin =  new RegularGoblin(gp, gp.Player);

//     }

//     //AI moves left since it is running with pathfinding. It goes straight to the player, and stops 1 tile away from the player.
//     // The player is at (0,0) and the goblin is at (12,0). The goblin moves left to (1,0) and stops there.
//     private void moveleft(){
//         gp.Player.WorldX = 0;
//         gp.Player.WorldY = 0;
//         regularGoblin.WorldX = 12;
//         regularGoblin.WorldY = 0;
//         System.out.println("Before GoblinX: " + regularGoblin.WorldX);
//         System.out.println("Before GoblinY: " + regularGoblin.WorldY);
//         regularGoblin.update();
//         System.out.println("After GoblinX: " + regularGoblin.WorldX);
//         System.out.println("After GoblinY: " + regularGoblin.WorldY);

//     }
//     private void moveright(){
//         gp.Player.WorldX = 12;
//         gp.Player.WorldY = 0;
//         regularGoblin.WorldX = 0;
//         regularGoblin.WorldY = 0;
//         System.out.println("Before GoblinX: " + regularGoblin.WorldX);
//         System.out.println("Before GoblinY: " + regularGoblin.WorldY);
//         regularGoblin.update();
//         System.out.println("After GoblinX: " + regularGoblin.WorldX);
//         System.out.println("After GoblinY: " + regularGoblin.WorldY);
//     }
//     private void moveup(){
//         gp.Player.WorldX = 0;
//         gp.Player.WorldY = 12;
//         regularGoblin.WorldX = 0;
//         regularGoblin.WorldY = 0;
//         System.out.println("Before GoblinX: " + regularGoblin.WorldX);
//         System.out.println("Before GoblinY: " + regularGoblin.WorldY);
//         regularGoblin.update();
//         System.out.println("After GoblinX: " + regularGoblin.WorldX);
//         System.out.println("After GoblinY: " + regularGoblin.WorldY);
//     }
//     private void movedown(){
//         gp.Player.WorldX = 0;
//         gp.Player.WorldY = 0;
//         regularGoblin.WorldX = 0;
//         regularGoblin.WorldY = 12;
//         System.out.println("Before GoblinX: " + regularGoblin.WorldX);
//         System.out.println("Before GoblinY: " + regularGoblin.WorldY);
//         regularGoblin.update();
//         System.out.println("After GoblinX: " + regularGoblin.WorldX);
//         System.out.println("After GoblinY: " + regularGoblin.WorldY);
//     }

//     @Test
//     public void testMovement(){
//         // Move the goblin left and check its position
//         moveleft();
//         assertTrue(regularGoblin.WorldX == 11);
//         assertTrue(regularGoblin.WorldY == 0);

//         // Move the goblin right and check its position
//         moveright();
//         assertTrue(regularGoblin.WorldX == 1);
//         assertTrue(regularGoblin.WorldY == 0);

//         // Move the goblin up and check its position
//         moveup();
//         assertTrue(regularGoblin.WorldX == 0);
//         assertTrue(regularGoblin.WorldY == 11);

//         // Move the goblin down and check its position
//         movedown();
//         assertTrue(regularGoblin.WorldX == 0);
//         assertTrue(regularGoblin.WorldY == 1);
//     }

//     @Test
//     public void checkSpritechange(){
//         gp.Player.WorldX = 0;
//         gp.Player.WorldY = 0;

//         regularGoblin.WorldX = 0;
//         regularGoblin.WorldY = 23;
//         //Test UP movement
//         regularGoblin.update();
//         assertEquals(1, regularGoblin.SpriteNum); // Assert SpriteNum is 1
//         assertEquals(regularGoblin.up1, regularGoblin.getSpriteForDirection()); // Assert image is up2
//         for (int i = 0; i < 10; i++) {
//             regularGoblin.update();
//         }
//         assertEquals(2, regularGoblin.SpriteNum); // Assert SpriteNum is 2
//         assertEquals(regularGoblin.up2, regularGoblin.getSpriteForDirection()); // Assert image is up2

//         gp.Player.WorldX = 0;
//         gp.Player.WorldY = 23;

//         regularGoblin.WorldX = 0;
//         regularGoblin.WorldY = 0;
//         //Test DOWN movement
//         for (int i = 0; i < 11; i++) {
//             regularGoblin.update();
//         }
//         assertEquals(1, regularGoblin.SpriteNum); // Assert SpriteNum is 1
//         assertEquals(regularGoblin.down1, regularGoblin.getSpriteForDirection()); // Assert image is down1

//         for (int i = 0; i < 11; i++) {
//             regularGoblin.update();
//         }
//         assertEquals(2, regularGoblin.SpriteNum); // Assert SpriteNum is 2
//         assertEquals(regularGoblin.down2, regularGoblin.getSpriteForDirection()); // Assert image is down2

//         gp.Player.WorldX = 0;
//         gp.Player.WorldY = 0;

//         regularGoblin.WorldX = 23;
//         regularGoblin.WorldY = 0;
//         //Test LEFT movment
//         for (int i = 0; i < 11; i++) {
//             regularGoblin.update();
//         }
//         assertEquals(1, regularGoblin.SpriteNum); // Assert SpriteNum is 1
//         assertEquals(regularGoblin.left1, regularGoblin.getSpriteForDirection()); // Assert image is left1

//         for (int i = 0; i < 11; i++) {
//             regularGoblin.update();
//         }
//         assertEquals(2, regularGoblin.SpriteNum); // Assert SpriteNum is 2
//         assertEquals(regularGoblin.left2, regularGoblin.getSpriteForDirection()); // Assert image is left2

//         gp.Player.WorldX = 23;
//         gp.Player.WorldY = 0;

//         regularGoblin.WorldX = 0;
//         regularGoblin.WorldY = 0;
//         //Test RIGHT movement
//         for (int i = 0; i < 11; i++) {
//             regularGoblin.update();
//         }
//         assertEquals(1, regularGoblin.SpriteNum); // Assert SpriteNum is 1
//         assertEquals(regularGoblin.right1, regularGoblin.getSpriteForDirection()); // Assert image is left1

//         for (int i = 0; i < 11; i++) {
//             regularGoblin.update();
//         }
//         assertEquals(2, regularGoblin.SpriteNum); // Assert SpriteNum is 2
//         assertEquals(regularGoblin.right2, regularGoblin.getSpriteForDirection()); // Assert image is left2

//         System.out.println("Goblin SpriteCounter passed. Success.");

//     }
//     public void testUpdate(){
//         gp.Player.WorldX = 0;
//         gp.Player.WorldY = 0;
//         regularGoblin.WorldX = 12;
//         regularGoblin.WorldY = 0;
//         regularGoblin.update();
//         assertTrue(regularGoblin.onPath);
//         assertTrue(regularGoblin.inSight);
//     }


//     //Goblin SpriteChange

//     //Goblin Collision with Player

//     //Goblin Collision with Objects

//     //Goblin Collision with Tiles

//     //Goblin Collision with other Goblins


//     @Test
//     void testDrawForGoblin(){
//         Goblin goblin = new RegularGoblin(gp, gp.Player);
//         goblin.draw(mock(Graphics2D.class));
//         Player player = gp.Player;
//         player.WorldX = 100;
//         player.WorldY = 100;
//         goblin.WorldX = 120;
//         goblin.WorldY = 120;
//         Graphics2D g2 = mock(Graphics2D.class);
//         goblin.draw(g2);
//         verify(g2, atLeastOnce()).drawImage(any(BufferedImage.class), anyInt(), anyInt(), anyInt(), anyInt(), any());
//     }

//     /**
//      * Tests the draw method of goblin when the goblin is not near the player.
//      */
//     @Test
//     void testDrawForGoblinNotNearPlayer() {
//         Goblin goblin = new RegularGoblin(gp, gp.Player);
//         Graphics2D g2 = mock(Graphics2D.class);
//         goblin.draw(g2);
//         verifyNoInteractions(g2);
//     }

//     /**
//      * Tests the draw method of goblin when the goblin is too far to the left of the player.
//      */
//     @Test
//     public void testDrawTooFarLeft() {
//         Graphics2D g2 = mock(Graphics2D.class);

//         gp.Player.WorldX = 440;
//         gp.Player.WorldY = 440;

//         // goblin object too far left
//         Goblin goblin = new RegularGoblin(gp, gp.Player);
//         goblin.WorldX = 10;  // Left of visible area
//         goblin.WorldY = 410;
//         goblin.draw(g2);
//         verifyNoInteractions(g2);
//     }

//     /**
//      * Tests the draw method of goblin when the goblin is too far to the right of the player.
//      */
//     @Test
//     public void testDrawTooFarRight() {
//         Graphics2D g2 = mock(Graphics2D.class);

//         gp.Player.WorldX = 400;
//         gp.Player.WorldY = 400;

//         // goblin object too far right
//         Goblin goblin = new RegularGoblin(gp, gp.Player);
//         goblin.WorldX = 900;  // Right of visible area
//         goblin.WorldY = 410;
//         goblin.draw(g2);
//         verifyNoInteractions(g2);
//     }

//     /**
//      * Tests the draw method of goblin when the goblin is too far up from the player.
//      */
//     @Test
//     public void testDrawTooFarUp() {
//         Graphics2D g2 = mock(Graphics2D.class);

//         gp.Player.WorldX = 400;
//         gp.Player.WorldY = 400;

//         // goblin object too far up
//         Goblin goblin = new RegularGoblin(gp, gp.Player);
//         goblin.WorldX = 400;  // Above visible area
//         goblin.WorldY = 10;
//         goblin.draw(g2);
//         verifyNoInteractions(g2);
//     }

//     /**
//      * Tests the draw method of goblin when the goblin is too far down from the player.
//      */
//     @Test
//     public void testDrawTooFarDown() {
//         Graphics2D g2 = mock(Graphics2D.class);

//         gp.Player.WorldX = 400;
//         gp.Player.WorldY = 400;

//         // goblin object too far down
//         Goblin goblin = new RegularGoblin(gp, gp.Player);
//         goblin.WorldX = 400;  // Below visible area
//         goblin.WorldY = 800;
//         goblin.draw(g2);
//         verifyNoInteractions(g2);
//     }

    
// }
