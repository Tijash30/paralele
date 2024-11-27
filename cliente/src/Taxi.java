import java.awt.*;
import java.util.concurrent.locks.Lock;

public class Taxi extends Car {
    public boolean loaded = false;
    public boolean loading = false;
    private long startTime;
    private People passager;
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

    public void setPassager(People person){

        this.loaded =true;

        startTime = System.currentTimeMillis();
        this.passager = person;
    }
    public void unloadPassager(){
        if(this.passager==null)return;
        People person = this.passager;
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
        this.loaded =false;
    }

     private boolean allowedMovement(){
        if(loading)return false;
         String respuestaServidor = MessageSender.sendMessage(4,
                                                                this.getId(),
                                                             this.getX()+this.getDx(),
                                                             this.getY()+this.getDy(),
                                                                this.getMoveType());
         if(respuestaServidor.equals("Ok")){
             return true;
         }
         return false;
     }

    public boolean isLoaded() {
        return loaded;
    }

    public void move() {
        if(allowedMovement()&&fs){
            this.trafico=false;
            x += dx;
            y += dy;
            if((this.moveType==0||this.moveType==2)&&(this.x%100==65||this.x%100==85||this.x%100==66||this.x%100==86)){
                if(random.nextBoolean()){
                    if(this.x%100==65||this.x%100==66){
                        if(allowedRotation(3))
                            moveType=3;
                    }else{
                        if(allowedRotation(1))
                            moveType=1;
                    }
                    updateMove();
                }
            }else{
                if((this.moveType==1||this.moveType==3)&&(this.y%100==65||this.y%100==85||this.y%100==66||this.y%100==86)){
                    if(random.nextBoolean()){
                        if(this.y%100==65||this.y%100==66){
                            if(allowedRotation(2))
                                moveType=2;
                        }else{
                            if(allowedRotation(0))
                                moveType=0;
                        }
                        updateMove();
                    }
                }

            }
        }else{
            this.trafico=true;
        }
        if(!running&&fs){
            String ans =MessageSender.sendMessage(6,this.getId(),x,y,moveType);
            System.out.println("Deletion: "+ans);
            fs=false;
        }
    }

    public void run() {
        this.setThread(Thread.currentThread());
        while (this.isRunning()||fs) {
            if(!loading)
                move();
            if(loaded){
                long elapsedTime = System.currentTimeMillis() - startTime;
                if(elapsedTime>10000){
                    unloadPassager();
                }
            }
            wrapAround(CityMap.getWidth(), CityMap.getHeight()); // Hardcoded dimensions; ideally, pass as arguments.
            try {
                Thread.sleep(50); // Control movement speed
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
