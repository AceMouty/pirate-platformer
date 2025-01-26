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
    private float xDelta = 100, yDelta = 100;

    // sprites and animations
    private BufferedImage image, subImage;
    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 20;

    // player items
    private Constants.PlayerAction playerAction = Constants.PlayerAction.IDLE;
    private Constants.PlayerDirection playerDirection = Constants.PlayerDirection.RIGHT;
    private boolean playerMoving = false;

    public GamePanel() {
        mouseHandler = new MouseHandler(this);

        importImage();
        loadAnimationFrames();

        setPanelSize();
        addKeyListener(new KeyHandler(this));
        addMouseListener(mouseHandler); // mouse clicks
        addMouseMotionListener(mouseHandler); // mouse movements

    }

//    public void updateXDelta(int value) {
//        this.xDelta += value;
//    }
//
//    public void updateYDelta(int value){
//        this.yDelta += value;
//    }

    public void setPlayerDirection(Constants.PlayerDirection direction) {
        this.playerDirection = direction;
        setPlayerMoving(true);
    }

    public void setPlayerMoving(boolean moving){
        this.playerMoving = moving;
    }

    public void updateGame() {
        updateAnimationTick();
        setAnimation();
        updatePlayerPos();
    }

    // magic-function: gets called internally by Java Swing
    // Graphics g: The thing that actually handles drawing; the "Paint brush"
    public void paintComponent(Graphics g){
        super.paintComponent(g); // stages the Panel to paint next frame
        // custom area ready to paint...

        g.drawImage(animations[playerAction.getAtlasIndex()][aniIndex],(int)xDelta, (int)yDelta, 128, 80, null);
    }

    private void updatePlayerPos() {
        if(!playerMoving) {
            return;
        }

        switch(playerDirection) {
            case Constants.PlayerDirection.LEFT:
                xDelta -= 3;
                break;
            case Constants.PlayerDirection.UP:
                yDelta -= 3;
                break;
            case Constants.PlayerDirection.RIGHT:
                xDelta += 3;
                break;
            case Constants.PlayerDirection.DOWN:
                yDelta += 3;
                break;
        }
    }

    private void setAnimation() {
        if(playerMoving) {
            playerAction = Constants.PlayerAction.RUNNING;
        } else {
            playerAction = Constants.PlayerAction.IDLE;
        }
    }

    private void setPanelSize(){
        Dimension dimension = new Dimension(1280, 800);
        setMinimumSize(dimension);
        setPreferredSize(dimension);
        setMaximumSize(dimension);
    }

    private void importImage() {
        // try-with-resources will auto call is.close() for us
        try (InputStream is = getClass().getResourceAsStream("/player_sprites.png"))
        {
            image = ImageIO.read(is);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void loadAnimationFrames() {
        animations = new BufferedImage[9][6];

        for (int row = 0; row < animations.length; row++){
            for(int col = 0; col < animations[row].length; col++){
                animations[row][col] = image.getSubimage(col*64, row*40, 64, 40);
            }
        }

    }

    private void updateAnimationTick(){
        aniTick++;
        if(aniTick >= aniSpeed){
            aniTick = 0;
            aniIndex++;
            if(aniIndex >= playerAction.getTotalFrames()) aniIndex = 0;
        }
    }
}
