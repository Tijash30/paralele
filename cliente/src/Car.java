import java.awt.*;
import java.util.Random;

public class Car implements Runnable, ThreadedAgent {
    protected int x, y;
    private final int width = 20, height = 10;
    public int moveType;
    protected int dx, dy;
    private int id=-1;
    protected boolean running = true;
    public boolean fs=true;
    private Thread thread;
    protected static Random random;
    protected boolean trafico;

    public Car(int x, int y) {
        this.x = x;
        this.y = y;
        if(random==null)
            random = new Random();
        moveType = random.nextInt(4);
        updateMove();
        if(moveType /2 >0){
            this.y -= 20;
        }
        if(moveType /2 >0){
            this.x-=20;
        }
        setId();
        this.thread= new Thread();
    }

    public void move() {
        if(allowedMovement()&&fs){
            trafico = false;
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
            trafico=true;
        }
        if(!running&&fs){
            String ans =MessageSender.sendMessage(6,id,x,y,moveType);
            System.out.println("Deletion: "+ans);
            fs=false;
        }
    }

    private boolean allowedMovement(){
        String respuestaServidor = MessageSender.sendMessage(4,id,x+dx,y+dy,moveType);
        if(respuestaServidor.equals("Ok")){
            return true;
        }
        return false;
    }

    protected boolean allowedRotation(int nxtRotation){
        String respuestaServidor = MessageSender.sendMessage(5,id,x,y,nxtRotation);
        if(respuestaServidor.equals("Ok")){
            return true;
        }
        return false;
    }

    protected void updateMove(){
        this.dx = moveType %2==0 ? 2 : 0;
        if(moveType /2 >0){
            this.dx *=-1;
        }
        this.dy = moveType %2==1 ? -2 : 0;
        if(moveType /2 >0){
            this.dy *=-1;
        }
    }
    public void wrapAround(int width, int height) {
        if (x < -5) x = width + 5;
        if (x > width+5) x = -5;
        if (y < -5) y = height + 5;
        if (y > height+5) y = -5;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        if(moveType %2==0)
            g.fillRect(x, y, width, height);
        else
            g.fillRect(x,y,height,width);
    }

    @Override
    public Thread getThread() {
        return thread;
    }

    @Override
    public void run() {
        thread= Thread.currentThread();
        while (running||fs) {
            move();
            wrapAround(CityMap.getWidth(), CityMap.getHeight()); // Hardcoded dimensions; ideally, pass as arguments.
            try {
                Thread.sleep(50); // Control movement speed
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    public void setId(){
        if(id==-1){
            String respuestaServidor = MessageSender.sendMessage(0,-1,x,y,moveType);
            if(respuestaServidor.contains("Ok")){
                id=Integer.parseInt(respuestaServidor.substring(3));
            }
        }
    }

    public void stop() {
        running = false;
    }

    public void setX(int x) {
        this.x = x;
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

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isFs() {
        return fs;
    }

    public void setFs(boolean fs) {
        this.fs = fs;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public static Random getRandom() {
        return random;
    }

    public static void setRandom(Random random) {
        Car.random = random;
    }
}

