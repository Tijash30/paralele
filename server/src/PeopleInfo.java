import java.awt.*;

public class PeopleInfo {
    private int x, y;
    private final int radius = 5;
    public PeopleInfo(int x, int y){
        this.x =x;
        this.y = y;
    }
    public Rectangle getBounds() {
            return new Rectangle(x-2, y-2, radius, radius);
    }
    public void update(int x, int y){
        this.x =x;
        this.y = y;
    }
}
