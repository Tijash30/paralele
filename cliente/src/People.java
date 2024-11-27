import java.awt.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class People implements Runnable, ThreadedAgent {
    private int x, y;
    private final int radius = 5;
    public int dx, dy;
    private int previousTaxi;
    private int id;
    private boolean running = true;
    private Thread thread;
    private ArrayList<Taxi> taxis;
    private boolean allowMovement= true;
    public boolean isOnRide=false;

    private final Lock lock = new ReentrantLock();

    public People(int x, int y, ArrayList<Taxi> taxis) {
        this.x = x;
        this.y = y;
        Random random = new Random();
        //si es true entonces que solo avance en x
        if(random.nextBoolean()){
            this.dx = random.nextBoolean() ? 1 : -1;
            this.dy=0;
        }
        else{
            this.dy = random.nextBoolean() ? 1 : -1;
            this.dx=0;
        }
        thread = new Thread(this);
        this.taxis= taxis;
        this.previousTaxi =-1;
        this.id=-1;
    }

    public void move(int width, int height) {
        if(allowMovement){
            x += dx;
            y += dy;

            // Bounce off boundaries
            if(x<=0)
                dx=1;
            else if(x>=width+55)
                dx=-1;
            else if(y<=0)
                dy=1;
            else if(y>=height+55)
                dy=-1;
            //if (x <=0 || x >=width+50 ) dx *= -1;
            //if (y <=0 || y >=height+50) dy *= -1;
            updateMovement();
        }

        if(!taxis.isEmpty()){
            for(Taxi taxi : taxis){
                //si el taxi ya tiene un pasagero 
                if(!taxi.isLoaded()&&taxi.getId()!=this.previousTaxi){
                    //si el taxi está cerca
                    if((this.x<taxi.getX()+50&&this.x>taxi.getX()-50) && (this.y<taxi.getY()+50&&this.y>taxi.getY()-50)){
                        //si el taxi está muy cerca
                        if((this.x<taxi.getX()+21&&this.x>taxi.getX()-21) && (this.y<taxi.getY()+21&&this.y>taxi.getY()-21)){
                            taxi.setPassager(this);
                            taxi.loading= true;
                            int deltaX = (taxi.getX() - this.x) / 7;
                            int deltaY = (taxi.getY() - this.y) / 7;
                            for(int k=0; k<7; k++){
                                x += deltaX;
                                y += deltaY;
                                try {
                                    Thread.sleep(100); 
                                } catch (InterruptedException e) {
                                    Thread.currentThread().interrupt();
                                }
                            }
                            isOnRide= true;
                            taxi.loading= false;
                            previousTaxi = taxi.getId();
                            break;
                        }
                        allowMovement=false;
                    }else
                        allowMovement=true;
                }
            }
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.ORANGE);
        g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
    }

    @Override
    public Thread getThread() {
        return thread;
    }

    @Override
    public void run() {
        thread= Thread.currentThread();
        if(id==-1)
            setId();
        while (running) {
            if(!isOnRide)
                move(800, 600);
            try {
                Thread.sleep(100); // Slower than cars/taxis
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        MessageSender.sendMessage(9,id,x,y,2);
    }

    private void updateMovement(){
        String respuestaServidor = MessageSender.sendMessage(8,id,x,y,2);
        if(respuestaServidor.equals("Ok")){
            return;
        }

    }

    public void setId(){
        if(id==-1){
            String respuestaServidor=MessageSender.sendMessage(7,id,x,y,0);
            if(respuestaServidor.contains("Ok")){
                id=Integer.parseInt(respuestaServidor.substring(3));
            }
        }
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void stop() {
        running = false;
    }

    public boolean isRunning() {
        return running;
    }

    public boolean isAllowMovement() {
        return allowMovement;
    }
}
