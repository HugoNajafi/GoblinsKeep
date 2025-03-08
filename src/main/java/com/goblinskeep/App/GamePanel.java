package com.goblinskeep.App;

//import Tile.TileManager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;

import com.goblinskeep.Keyboard.MenuInputHandler;
import com.goblinskeep.UI.EndUI;
import com.goblinskeep.UI.MenuUI;
import com.goblinskeep.UI.PauseUI;
import com.goblinskeep.UI.UI;
import com.goblinskeep.entity.CollisionChecker;
import com.goblinskeep.entity.Entity;
import com.goblinskeep.entity.Player;
import com.goblinskeep.entity.SmartGoblin;

import javax.swing.JPanel;

import com.goblinskeep.Keyboard.PlayerInputHandler;
import com.goblinskeep.Tile.TileManager;
import com.goblinskeep.objects.MainObject;


//Game Panel works as game Screen
public class GamePanel extends JPanel implements Runnable
{
    private  final int originaltileSize = 16; //Tile size during design
    private  final int scale = 3;//Scale for tiles and characters to fit monitor resolution

    public  final int tileSize = originaltileSize * scale; //Scalling
    public  final int maxScreenCol = 16; //Width of game
    public  final int maxScreenRow = 12; //Height of game
    public  final int screenWidth = tileSize * maxScreenCol; //Scalling
    public  final int screenHeight = tileSize * maxScreenRow; //Scalling
    public  final int maxWorldCol = 85;
    public  final int maxWorldRow = 82;
    
    //FPS
    private int FPS = 60;
    
    //TileManager tileM;
    public TileManager tileM;
    public Player Player;
    public CollisionChecker collisionChecker;
    PlayerInputHandler PlayerInput;
    public Thread gameThread;

    // Game state
    public Gamestate gamestate;
    private ArrayList<SmartGoblin> goblins;

    //temporary public stuff
    public MainObject[] obj;
    public UI ui = new UI(this);
    public EndUI endUI = new EndUI(this);
    public GameStatus status;
    public Map1 map;
    private MenuUI menuUI = new MenuUI(this);
    private MenuInputHandler keyboard = new MenuInputHandler(this);
    
    //Set Player's default position
    // int playerX = 100;
    // int playerY = 100;
    // @Override
    // Player.setX(100);
    // @Override
    // Player.setY(100);
    
    
    /**
     * Constructor
     */
    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.tileM = new TileManager(this);
        this.PlayerInput = new PlayerInputHandler();
        this.Player = new Player(100, 100, this, PlayerInput);
        this.Player.speed = 5;
        
        this.collisionChecker = new CollisionChecker(this);
        
        // Initialize game state
        //should be maxWorldCol and Row
        this.gamestate = new Gamestate(maxScreenCol, maxScreenRow, this.Player);
        this.map = new Map1(this);
        
        // Create and load map
//        String mapStr = mapReader.createSampleMap();
//        mapReader.loadMap(mapStr, gamestate, this);

        tileM = map.getTileManager();
        goblins = map.getGoblins();
        
        // Update tile map for collision checking
//        tileM.updateMapTileNum(gamestate);
        
        // Initialize goblins
//        goblins = new ArrayList<>();
//        for (Point pos : gamestate.getInitialGoblinPositions()) {
//            SmartGoblin goblin = new SmartGoblin(this, Player, gamestate);
//            goblin.setX(pos.x * tileSize);
//            goblin.setY(pos.y * tileSize);
//            goblin.collisionArea = new Rectangle(8, 16, 32, 32); // Set collision area
//            goblin.hitboxDefaultX = 8;
//            goblin.hitboxDefaultY = 16;
//            goblins.add(goblin);
//            gamestate.addEnemy(goblin);
//        }

        //place objects
        setUpGame();

        //Double buffer improves rendering
        this.setDoubleBuffered(true);
        this.addKeyListener(PlayerInput);
        this.addKeyListener(keyboard);
        this.setFocusable(true);
        this.repaint();
    }
    
    public GamePanel(int PlayerX, int PlayerY){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.tileM = new TileManager(this);
        this.PlayerInput = new PlayerInputHandler();
        this.Player = new Player(PlayerX, PlayerY, this, PlayerInput);
        //Double buffer improves rendering
        this.setDoubleBuffered(true);
        this.addKeyListener(PlayerInput);
        this.setFocusable(true);
        this.repaint();
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void setUpGame(){
        obj = map.getObjects();
        status = GameStatus.MENU; //in the future change this to MENU
    }
    
    @Override
    /**
     * GAME LOOP
     * We implement Runnable on our game panel to implement a thread.
     * So when we start the thread this run method will be called, we will use it as our main game loop
     */
    //Sleep Method
    // public void run(){
    //     double drawInterval = 1000000000/FPS; //Draw the screen 60 times per second. We use 1 billion -> 1 nano sec = 1 sec
    //     double nextDrawTime = System.nanoTime() + drawInterval;
    //     long timer = 0;
    //     int drawCount = 0;
    //     long LastTime = System.nanoTime();
    //     long CurrentTime;
        
    //     while (gameThread != null){

    //         CurrentTime = System.nanoTime();
    //         timer += (CurrentTime - LastTime);
    //         LastTime = CurrentTime;

            
    //         //System.out.println("Game is Running");
    //         //Use nano secondnds to get a more precise time
    //         /*
    //         */
            
    //         //1. Update information like character/enemy positions
    //         update();
            
    //         //2. Draw the screen with updated information
    //         repaint();
    //         drawCount ++;
            
    //         try{
    //             double remainingTime = nextDrawTime - System.nanoTime();
    //             remainingTime /= 1000000; //Convert to milli, since sleep wants time in milli seconds
                
    //             //If Update and repaint takes more time than the draw interval, then we set remaining time to 0
    //             //Draw 
    //             if(remainingTime < 0){
    //                 remainingTime = 0;
    //             }
                
    //             Thread.sleep((long)remainingTime);
    //             nextDrawTime += drawInterval;
    //             if(timer >= 1000000000){
    //                 System.out.println("FPS " + drawCount);
    //                 drawCount = 0;
    //                 timer = 0;
    //             }
    //         } catch (InterruptedException e){
    //             e.printStackTrace();
    //         }
    //     }
        
    // }

    public void run(){
        double drawInterval = 1000000000/FPS; //Draw the screen 60 times per second. We use 1 billion -> 1 nano sec = 1 sec
        double delta = 0;
        long timer = 0;
        int drawCount = 0;
        long LastTime = System.nanoTime();
        long CurrentTime;

        while(gameThread != null){
            CurrentTime = System.nanoTime();
            delta += (CurrentTime - LastTime) / drawInterval;
            timer += (CurrentTime - LastTime);
            LastTime = CurrentTime;

            if(delta >= 1){
                update();
                repaint();
                delta--;
                drawCount++;
            }

            //Check FPS
            if(timer >= 1000000000){
                System.out.println("FPS " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
        
    }
    public void update(){
        // System.out.println("Updating");
        /*
         * In Java, X:0, Y:0 is top right corner
         * X values increase to the right
         * Y values increase going down. i.e the closer you are to 0, the higher up the screen you are
         * (So we need to perform this operation inversely)
         * 
         */
        // Player.getAction();
        if (status == GameStatus.PLAYING) {
            if (map.gameEnded()){ //this doesnt change so game bricks at the menu
                status = GameStatus.END; //change later to WIN/LOSE
            } else {
                Player.getAction();
                for (SmartGoblin goblin : goblins) {
                    goblin.getAction();
                }
            }
        } else if (status == GameStatus.PAUSED) {
            //do nothing if paused
        } else if (status == GameStatus.RESTART){
            restartGame();
            status = GameStatus.PLAYING;
        }

        // if(Player.up){
        //     // System.out.println("Pressing Up");
        //     // System.out.println(playerY);
        //     playerY -= Direction.UP.getDy();
        // }
        // else if(Player.down){
        //     // System.out.println("Pressing Down");
        //     // System.out.println(playerY);
        //     playerY -= Direction.DOWN.getDy();
        // }
        // else if(Player.left){
        //     // System.out.println("Pressing Left");
        //     // System.out.println(playerX);
        //     playerX += Direction.LEFT.getDx();
        // }
        // else if(Player.right){
        //     // System.out.println("Pressing Right");
        //     // System.out.println(playerX);
        //     playerX += Direction.RIGHT.getDx();
        // }
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        if (status == GameStatus.MENU){
            menuUI.draw(g2);
        } else if (status == GameStatus.END) {
            endUI.draw(g2);
        } else {
            tileM.draw(g2, gamestate);
            for (MainObject mainObject : obj) {
                if (mainObject != null) {
                    mainObject.draw(g2, this);
                }
            }
            Player.draw(g2);
            for (SmartGoblin goblin : goblins) {
                goblin.draw(g2);
            }
            ui.draw(g2);
        }
        /*
        * Graphics2D extends Graphics and provides more methods
        * coordinate transformations, color management etc
        */

        
        g2.dispose();
        
    }

    public Iterator<SmartGoblin> getSmartGoblinIterator(){
        return goblins.iterator();
    }

    public MenuUI getMenuUI(){
        return menuUI;
    }

    public void restartGame() {

    }

}




