package main;

import inputs.KeyHandler;
import inputs.MouseHandler;
import utils.Constants;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class GamePanel extends JPanel {
    private final MouseHandler mouseHandler;
    private final Game game;

    public GamePanel(Game game) {
        this.game = game;
        mouseHandler = new MouseHandler(this);

        setPanelSize();
        addKeyListener(new KeyHandler(this));
        addMouseListener(mouseHandler); // mouse clicks
        addMouseMotionListener(mouseHandler); // mouse movements

    }

    public Game getGame() {
        return game;
    }

    public void updateGame() {

    }

    // magic-function: gets called internally by Java Swing
    // Graphics g: The thing that actually handles drawing; the "Paint brush"
    public void paintComponent(Graphics g){
        super.paintComponent(g); // stages the Panel to paint next frame
        // custom area ready to paint...

        game.render(g);
    }

    private void setPanelSize(){
        Dimension dimension = new Dimension(1280, 800);
        setMinimumSize(dimension);
        setPreferredSize(dimension);
        setMaximumSize(dimension);
    }
}
