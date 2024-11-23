import java.awt.*;

public class CarInfo {
    private int x, y;
    private final int width = 20, height = 10;
    private boolean active = true;
    private int moveType;

    public CarInfo(int x, int y, int moveType) {
        this.x = x;
        this.y = y;
        this.moveType = moveType;
    }
    public void update(int x, int y, int moveType) {
        this.x = x;
        this.y = y;
        this.moveType = moveType;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMoveType() {
        return moveType;
    }

    public void setMoveType(int moveType) {
        this.moveType = moveType;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Rectangle getBounds() {
        if(moveType %2==0)
            return new Rectangle(x, y, width, height);
        else
            return new Rectangle(x, y, height, width);

    }

    public Rectangle getBigBounds() {
        if(moveType %2==0)
            return new Rectangle(x-1, y-1, width+2, height+2);
        else
            return new Rectangle(x-1, y-1, height+2, width+2);

    }
}
