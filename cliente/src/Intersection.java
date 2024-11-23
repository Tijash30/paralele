import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Intersection implements Runnable, ThreadedAgent {
    private final int x, y; // Position of the intersection
    private int activeLight;
    private int id=-1;
    private final TrafficLight[] lights = new TrafficLight[4]; // Four traffic lights (one per direction)
    private final AtomicBoolean running = new AtomicBoolean(true); // To stop the thread gracefully
    private Thread thread;

    public Intersection(int x, int y) {
        this.x = x;
        this.y = y;

        // Initialize the traffic lights for each direction
        lights[0] = new TrafficLight(x - CityMap.streetWidth/2, y - CityMap.streetWidth/2,true); // Top
        lights[1] = new TrafficLight(x - 5 + CityMap.streetWidth/2, y - CityMap.streetWidth/2,false); // Right
        lights[3] = new TrafficLight(x - CityMap.streetWidth/2, y - 10 + CityMap.streetWidth/2,false); // Bottom
        lights[2] = new TrafficLight(x - 10 + CityMap.streetWidth/2, y - 5 + CityMap.streetWidth/2,true); // Left
        thread = new Thread(this);
        setId();
    }

    public void draw(Graphics g) {
        for (TrafficLight light : lights) {
            light.draw(g);
        }
    }

    public void stop() {
        running.set(false);
    }

    @Override
    public Thread getThread() {
        return thread;
    }

    public void setId(){

        if (id == -1) {
            String respuestaServidor = MessageSender.sendMessage(1,-1,x,y,activeLight);
            if(respuestaServidor.contains("Ok")){
                id=Integer.parseInt(respuestaServidor.substring(3));
            }
        }

    }

    @Override
    public void run() {
        thread= Thread.currentThread();
        activeLight = 0;
        while (running.get()) {
            // Cycle to the next light
            activeLight = (activeLight + 1) % 8;
            MessageSender.sendMessage(3,id,x,y,activeLight);
            // Activate one light at a time
            for (int i = 0; i < lights.length; i++) {
                lights[i].setGreen(i%2 == (activeLight/4)%2);
            }
            // Simulate traffic light delay
            try {
                Thread.sleep(750); // 3 seconds per light
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
