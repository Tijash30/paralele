import org.w3c.dom.css.Rect;

import java.awt.*;
import java.util.ArrayList;

public class IntersectionInfo {
    private int x, y; // Position of the intersection
    private int activeLight;

    public IntersectionInfo(int x, int y, int activeLight) {
        this.x = x;
        this.y = y;
        this.activeLight = activeLight;
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

    public int getActiveLight() {
        return activeLight;
    }

    public void setActiveLight(int activeLight) {
        this.activeLight = activeLight;
    }

    public void update(int x, int y, int activeLight){
        this.x = x;
        this.y = y;
        this.activeLight = activeLight;

    }
    public Rectangle getBounds() {
        return new Rectangle(x-20, y-20, 40, 40);
    }
    public boolean check( Rectangle car){
        Rectangle[] lights = new Rectangle[4];
        lights[0] = new Rectangle(x - 20, y - 25, 10,5);// Top
        lights[1] = new Rectangle(x  + 20, y - 20,5,10); // Right
        lights[3] = new Rectangle(x - 25, y  + 10,5,10); // Bottom
        lights[2] = new Rectangle(x  + 10, y  + 20,10,5); // Left
        for( int i=0; i<4; i++){
            if(activeLight %2 ==i%2)
                continue;
            else if(car.intersects(lights[i])){
                System.out.println(activeLight);
                System.out.println(i);
                return false;
            }
        }
        return true;
    }
}
