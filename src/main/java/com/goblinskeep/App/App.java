package com.goblinskeep.App;

import javax.swing.JFrame;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        JFrame window = new JFrame(); //Create a new window
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Goblin's Keep");
        
        GamePanel gamepanel = new GamePanel();
        window.add(gamepanel);
        
        /*
        * Causes this Window to be sized to fit the preferred size
        * and layouts its subcoomponents (our gamepanel)
        */
        window.pack();
        
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        gamepanel.startGameThread();
    }
}
