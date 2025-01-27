package main;

import javax.swing.JFrame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class GameWindow extends JFrame {
    public GameWindow(GamePanel gamePanel) {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(gamePanel);
        //setLocationRelativeTo(null); // open center screen
        setResizable(false);
        pack(); // use the preferred size defined in the panel
        // this needs to be the last thing when setting up the frame
        setVisible(true);

        // detect loss of focus
        addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {

            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                System.out.println("Byeee!");
                gamePanel.getGame().windowFocusLost();
            }
        });
    }
}
