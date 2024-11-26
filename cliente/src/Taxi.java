import java.awt.*;
import java.util.concurrent.locks.Lock;

public class Taxi extends Car {
    private boolean loaded;
    public boolean hasPassa= false;
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

    public void getPassa(People person){

        this.hasPassa=true;

        long startTime = System.currentTimeMillis();
        long elapsedTime = 0;

        while (elapsedTime < 10000) { 
            elapsedTime = System.currentTimeMillis() - startTime;
        }
        Lock lock = person.getLock();
        if (lock.tryLock()) {
            try {
                System.out.println("bajó del taxi sin problema en       x: "+this.getX()+" y: "+this.getY());
            } finally {
                lock.unlock(); 
                person.isOnRide=false;
                if(this.moveType==0 ){
                    person.dx=1;
                    person.dy=0;
                    person.setY(this.getY()+20);
                    person.setX(this.getX());
                }else if(this.moveType==2){
                    person.dx=1;
                    person.dy=0;
                    person.setY(this.getY()-10);
                    person.setX(this.getX());
                }else if(this.moveType==3){
                    person.dx=0;
                    person.dy=1;
                    person.setX(this.getX()-10);
                    person.setY(this.getY());
                }
                else if(this.moveType==1){
                    person.dx=0;
                    person.dy=1;
                    person.setX(this.getX()+20);
                    person.setY(this.getY());
                }
                try {
                    person.getThread().sleep(1500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                
            }
        }
        this.hasPassa=false;
    }


}
