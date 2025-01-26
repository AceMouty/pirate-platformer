package main;

import javax.swing.JFrame;

public class GameWindow extends JFrame {
    public GameWindow(GamePanel gamePanel) {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(gamePanel);
        setLocationRelativeTo(null); // open center screen
        setResizable(false);
        pack(); // use the preferred size defined in the panel
        // this needs to be the last thing when setting up the frame
        setVisible(true);
    }
}
