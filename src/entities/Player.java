package entities;

import levels.Level;
import main.Game;
import utils.Constants;
import utils.HelperMethods;
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

    // Level information
    private Level level;

    // hitbox offsets (make hitbox close to player body)
    private float xDrawOffset = 21 * Game.SCALE;
    private float yDrawOffset = 4 * Game.SCALE;

    public Player(float x, float y, int width, int height){
        super(x, y, width, height);
        loadAnimationFrames();
        initHitbox(x, y, 20 * Game.SCALE, 28 * Game.SCALE);
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

    public void setLevel(Level level) {
        this.level = level;
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
        g.drawImage(animations[playerAction.getAtlasIndex()][aniIndex],
          (int)(hitbox.x - xDrawOffset), (int)(hitbox.y - yDrawOffset), width, height, null);

        drawHitbox(g);
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
        float xSpeed = 0, ySpeed = 0;

        // if not pressing any button, exit, nothing to do
        if(!left && !right && !up && !down){
            return;
        }

        if(left && !right) {
            xSpeed = -playerSpeed;
        } else if(right && !left) {
            xSpeed = playerSpeed;
        }

        if(up && !down) {
            ySpeed = -playerSpeed;
        } else if (down && !up) {
            ySpeed = playerSpeed;
        }

//        if(!HelperMethods.IsValidMove(x+xSpeed, y+ySpeed, width, height, level.getLevelData())){
//            return;
//        }
        if(!HelperMethods.IsValidMove(hitbox.x+xSpeed, hitbox.y+ySpeed, hitbox.width, hitbox.height, level.getLevelData())){
            return;
        }

        hitbox.x += xSpeed;
        hitbox.y += ySpeed;
        playerMoving = true;
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
