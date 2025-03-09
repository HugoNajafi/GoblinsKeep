package com.goblinskeep.app;

//import Tile.TileManager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;


import com.goblinskeep.keyboard.MenuInputHandler;
import com.goblinskeep.UI.EndUI;
import com.goblinskeep.UI.MenuUI;
import com.goblinskeep.UI.UI;
import com.goblinskeep.entity.CollisionChecker;
import com.goblinskeep.entity.Player;
import com.goblinskeep.entity.SmartGoblin;

import javax.swing.JPanel;

import com.goblinskeep.keyboard.PlayerInputHandler;
import com.goblinskeep.objects.ObjectManager;
import com.goblinskeep.tile.TileManager;


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
    public  final int maxWorldCol = 50;
    public  final int maxWorldRow = 50;
    
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
    public ObjectManager obj;

    public UI ui = new UI(this);
    public EndUI endUI = new EndUI(this);
    public GameStatus status;
    public MapGenerator map;
    private MenuUI menuUI = new MenuUI(this);
    private MenuInputHandler keyboard = new MenuInputHandler(this);
    

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
        this.obj = new ObjectManager(this);
        
        this.collisionChecker = new CollisionChecker(this);
        
        // Initialize game state
        //should be maxWorldCol and Row
        this.gamestate = new Gamestate(maxScreenCol, maxScreenRow, this.Player);
        this.map = new MapGenerator(this);

        goblins = map.getGoblins();
        

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
        status = GameStatus.MENU; //in the future change this to MENU
    }
    
    @Override
    /**
     * GAME LOOP
     * We implement Runnable on our game panel to implement a thread.
     * So when we start the thread this run method will be called, we will use it as our main game loop
     */
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
                Player.update();
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
            obj.draw(g2,this);
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




