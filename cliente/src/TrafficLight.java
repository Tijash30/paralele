import java.awt.*;

public class TrafficLight {
    public enum State {
        RED, GREEN
    }

    private final int x, y;
    private State state;
    private static final int LongSide = 5;// Smaller traffic light size
    private static final int ShortSide = 10; // Smaller traffic light size
    private boolean Horizontal; // Smaller traffic light size

    public TrafficLight(int x, int y, boolean horizontal) {
        this.x = x;
        this.y = y;
        this.state = State.GREEN;
        this.Horizontal = horizontal;
    }

    public void toggle() {
        state = (state == State.GREEN) ? State.RED : State.GREEN;
    }

    public boolean isNear(int carX, int carY) {
        return Math.abs(carX - x) < 20 && Math.abs(carY - y) < 20; // Smaller detection radius
    }

    public void draw(Graphics g) {
        g.setColor(state == State.GREEN ? Color.GREEN : Color.RED);
        if(!this.Horizontal)
            g.fillRect(x, y, LongSide, ShortSide);
        else
            g.fillRect(x, y, ShortSide, LongSide);

    }

    public State getState() {
        return state;
    }

    private void setState(State state) {
        this.state = state;
    }
    public void setGreen(boolean green){
        if(green)
            this.setState(State.GREEN);
        else
            this.setState(State.RED);
    }
}
