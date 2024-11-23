import java.awt.*;
import java.util.Random;

public class Taxi extends Car {
    private boolean loaded;
    public Taxi(int x, int y) {
        super(x, y);
    }

    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        if(this.getMoveType() %2==0)
            g.fillRect(getX(), getY(), getWidth(), getHeight());
        else
            g.fillRect(getX(), getY(), getHeight(), getWidth());
    }
}
