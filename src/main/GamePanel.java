package main;

import inputs.KeyHandler;
import inputs.MouseHandler;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class GamePanel extends JPanel {
    private final MouseHandler mouseHandler;
    private float xDelta = 100, yDelta = 100;
    private BufferedImage image, subImage;

    public GamePanel() {
        mouseHandler = new MouseHandler(this);

        importImage();

        setPanelSize();
        addKeyListener(new KeyHandler(this));
        addMouseListener(mouseHandler); // mouse clicks
        addMouseMotionListener(mouseHandler); // mouse movements

    }

    public void updateXDelta(int value) {
        this.xDelta += value;
    }

    public void updateYDelta(int value){
        this.yDelta += value;
    }

    // magic-function: gets called internally by Java Swing
    // Graphics g: The thing that actually handles drawing; the "Paint brush"
    public void paintComponent(Graphics g){
        super.paintComponent(g); // stages the Panel to paint next frame
        // custom area ready to paint...

        // grab a sub section of the png file, this is why we used BufferedImage
        subImage = image.getSubimage(1*64,8*40, 64, 40);
        g.drawImage(subImage,(int)xDelta, (int)yDelta, 128, 80, null);
    }

    private void setPanelSize(){
        Dimension dimension = new Dimension(1280, 800);
        setMinimumSize(dimension);
        setPreferredSize(dimension);
        setMaximumSize(dimension);
    }

    private void importImage() {
        InputStream is = getClass().getResourceAsStream("/player_sprites.png");
        try{
            image = ImageIO.read(is);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
