import java.awt.*;
import java.util.Random;

public class People implements Runnable, ThreadedAgent {
    private int x, y;
    private final int radius = 5;
    private int dx, dy;
    private int id;
    private boolean running = true;
    private Thread thread;

    public People(int x, int y) {
        this.x = x;
        this.y = y;
        Random random = new Random();
        this.dx = random.nextBoolean() ? 1 : -1;
        this.dy = random.nextBoolean() ? 1 : -1;
        thread = new Thread(this);
    }

    public void move(int width, int height) {
        x += dx;
        y += dy;

        // Bounce off boundaries
        if (x < 50 || x > width - 50) dx *= -1;
        if (y < 50 || y > height - 50) dy *= -1;
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
    }

    @Override
    public Thread getThread() {
        return thread;
    }

    @Override
    public void run() {
        while (running) {
            move(800, 600);
            try {
                Thread.sleep(100); // Slower than cars/taxis
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void setId(){
        /*
        if(id==-1){
            String respuestaServidor="Ok 1";
            if(respuestaServidor.contains("Ok")){
                id=Integer.parseInt(respuestaServidor.substring(3));
            }
        }*/
    }

    public void stop() {
        running = false;
    }

}
