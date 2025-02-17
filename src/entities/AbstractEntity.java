package entities;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class AbstractEntity {
    protected float x, y;
    protected int width, height;
    protected Rectangle2D.Float hitbox;

    public AbstractEntity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    protected void drawHitbox(Graphics g){
        // For debugging hitbox
        g.setColor(Color.PINK);
        g.drawRect((int) hitbox.x, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }

//    protected void updateHitbox() {
//        hitbox.x = (int)x;
//        hitbox.y = (int)y;
//    }

    protected void initHitbox(float x, float y, float width, float height) {
        hitbox = new Rectangle2D.Float(x, y, width, height);
    }
}
