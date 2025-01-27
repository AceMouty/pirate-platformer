package entities;

import main.Game;
import utils.Constants;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends AbstractEntity {
    // player animations
    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 20;

    // controlling player
    private Constants.PlayerAction playerAction = Constants.PlayerAction.IDLE;
    private Constants.PlayerDirection playerDirection = Constants.PlayerDirection.RIGHT;
    private boolean playerMoving = false, playerAttacking = false;
    private boolean up, right, down, left;
    private float playerSpeed = 2.0f;

    public Player(float x, float y, int width, int height){
        super(x, y, width, height);
        loadAnimationFrames();
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setPlayerAttacking(boolean playerAttacking) {
        this.playerAttacking = playerAttacking;
    }

    public void update() {
        updatePlayerPos();
        updateAnimationTick();
        setAnimation();
    }

    public void render(Graphics g) {
        g.drawImage(animations[playerAction.getAtlasIndex()][aniIndex],(int)x, (int)y, width, height, null);
    }

    public void resetDirectionBooleans() {
        left = false;
        right = false;
        up = false;
        down = false;
    }

//    public void setPlayerDirection(Constants.PlayerDirection direction) {
//        this.playerDirection = direction;
//        setPlayerMoving(true);
//    }

//    public void setPlayerMoving(boolean moving){
//        this.playerMoving = moving;
//    }
    private void updatePlayerPos() {
        playerMoving = false;
        if(left && !right) {
            x -= playerSpeed;
            playerMoving = true;
        } else if(right && !left) {
            x += playerSpeed;
            playerMoving = true;
        }

        if(up && !down) {
            y -= playerSpeed;
            playerMoving = true;
        } else if (down && !up) {
            y += playerSpeed;
            playerMoving = true;
        }
    }

    private void setAnimation() {
        int startAni = playerAction.getAtlasIndex();

        if(playerMoving) {
            playerAction = Constants.PlayerAction.RUNNING;
        } else {
            playerAction = Constants.PlayerAction.IDLE;
        }

        if(playerAttacking){
            playerAction = Constants.PlayerAction.ATTACK_1;
        }

        // did we change our animation?
        if(startAni != playerAction.getAtlasIndex()) {
            resetAnimationTick();
        }
    }

    private void updateAnimationTick(){
        aniTick++;
        if(aniTick >= aniSpeed){
            aniTick = 0;
            aniIndex++;
            if(aniIndex >= playerAction.getTotalFrames()) {
                aniIndex = 0;
                setPlayerAttacking(false);
            }
        }
    }

    private void resetAnimationTick() {
        aniTick = 0;
        aniIndex = 0;
    }

    private void loadAnimationFrames() {
        animations = new BufferedImage[9][6];
        BufferedImage spriteAtlas = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
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
}
