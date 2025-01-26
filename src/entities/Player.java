package entities;

import utils.Constants;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Player extends AbstractEntity {
    // player animations
    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 20;

    // controlling player
    private Constants.PlayerAction playerAction = Constants.PlayerAction.IDLE;
    private Constants.PlayerDirection playerDirection = Constants.PlayerDirection.RIGHT;
    private boolean playerMoving = false;

    public Player(float x, float y){
        super(x, y);
        loadAnimationFrames();
    }

    public void update() {
        updateAnimationTick();
        setAnimation();
        updatePlayerPos();
    }

    public void render(Graphics g) {
        g.drawImage(animations[playerAction.getAtlasIndex()][aniIndex],(int)x, (int)y, 128, 80, null);
    }

    public void setPlayerDirection(Constants.PlayerDirection direction) {
        this.playerDirection = direction;
        setPlayerMoving(true);
    }

    public void setPlayerMoving(boolean moving){
        this.playerMoving = moving;
    }
    private void updatePlayerPos() {
        if(!playerMoving) {
            return;
        }

        switch(playerDirection) {
            case Constants.PlayerDirection.LEFT:
                x -= 1;
                break;
            case Constants.PlayerDirection.UP:
                y -= 1;
                break;
            case Constants.PlayerDirection.RIGHT:
                x += 1;
                break;
            case Constants.PlayerDirection.DOWN:
                y += 1;
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

    private void updateAnimationTick(){
        aniTick++;
        if(aniTick >= aniSpeed){
            aniTick = 0;
            aniIndex++;
            if(aniIndex >= playerAction.getTotalFrames()) aniIndex = 0;
        }
    }
    private void loadAnimationFrames() {
        animations = new BufferedImage[9][6];
        BufferedImage spriteAtlas = importImage();
        if (spriteAtlas == null) {
            System.err.println("Critical error: Player image not found");
            System.exit(1);
        }

        for (int row = 0; row < animations.length; row++){
            for(int col = 0; col < animations[row].length; col++){
                animations[row][col] = spriteAtlas.getSubimage(col*64, row*40, 64, 40);
            }
        }

    }

    private BufferedImage importImage() {
        BufferedImage spriteAtlas = null;
        // try-with-resources will auto call is.close() for us
        try (InputStream is = getClass().getResourceAsStream("/player_sprites.png"))
        {
            spriteAtlas = ImageIO.read(is);
        } catch (IOException e){
            e.printStackTrace();
        }

        return spriteAtlas;
    }
}
