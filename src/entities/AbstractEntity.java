package entities;

public abstract class AbstractEntity {
    protected float x, y;
    protected int width, height;

    public AbstractEntity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}
