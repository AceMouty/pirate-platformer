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
    private boolean up, right, down, left, jump;
    private float playerSpeed = 2.0f;

    // Level information
    private Level level;

    // hitbox offsets (make hitbox close to player body)
    private float xDrawOffset = 21 * Game.SCALE;
    private float yDrawOffset = 4 * Game.SCALE;

    // jump / gravity
    private float airSpeed = 0f;
    private float gravity = 0.04f * Game.SCALE;
    private float jumpSpeed = -2.25f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private boolean inAir = false;

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

    public void setJump(boolean jump) {
        this.jump = jump;
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
        float xSpeed = 0;

        if(jump) {
            jump();
        }
        // if not pressing any button, exit, nothing to do
        if(!left && !right && !inAir){
            return;
        }

        // check if moving left or right
        if(left) xSpeed -= playerSpeed;
        if(right) xSpeed += playerSpeed;

        // Apply changes based on if we are in the air or not
        if(inAir) {
            // if still jumping else landed...
            if(HelperMethods.IsValidMove(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, level.getLevelData())){
                hitbox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            } else {
                hitbox.y = HelperMethods.GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
                if(airSpeed > 0){
                    resetInAir();
                } else {
                    airSpeed = fallSpeedAfterCollision;
                }
                updateXPos(xSpeed);
            }
        } else
            updateXPos(xSpeed);

        playerMoving = true;
    }

    private void jump() {
        if(inAir) {
            return;
        }

        inAir = true;
        airSpeed = jumpSpeed;
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    private void updateXPos(float xSpeed) {
        if(!HelperMethods.IsValidMove(hitbox.x+xSpeed, hitbox.y, hitbox.width, hitbox.height, level.getLevelData())){
            hitbox.x = HelperMethods.GetEntityXPosNextToWall(hitbox, xSpeed);
            return;
        }

        hitbox.x += xSpeed;
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
