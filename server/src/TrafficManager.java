import java.io.*;
import java.net.Socket;

class TrafficManager implements Runnable {
    private Socket clientSocket;
    public static PositionManager positionManager;


    /*
    0.- Register Car
    1.- Register Traffic Light
    2.- Update Car
    3.- Update Traffic Light
    4.- Ask for movement
    5.- Ask for turn
    6.- Remove Car
     */

    public TrafficManager(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            // Leer el mensaje del cliente
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String line = input.readLine();
            String[] parts = line.split(" ");

            int type = Integer.parseInt(parts[0]);
            int id = Integer.parseInt(parts[1]);
            int x = Integer.parseInt(parts[2]);
            int y = Integer.parseInt(parts[3]);
            int dir = Integer.parseInt(parts[4]);

            boolean ans = true;
            // Switch statement to handle different message types
            switch (type) {
                case 0:
                    // Register new car
                    //System.out.println("Intersection Registered");
                    while (id==-1){
                        id = positionManager.registerCar(x, y, dir);
                        try {
                            Thread.sleep(40);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;  // Added break to prevent fall-through to the next case
                case 1:
                    // Register new Intersection
                    //System.out.println("Intersection Registered");
                    while (id==-1){
                        id = positionManager.registerIntersection(x, y, dir);
                        try {
                            Thread.sleep(40);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 3:
                    // Check for collision and update car
                    positionManager.updateIntersection(id,x,y,dir);
                    break;   // Added break to prevent fall-through to the next case

                case 4:
                case 5:
                    // Check for collision and update car
                    if (positionManager.isCollisionFree(id, x, y, dir)) {
                        positionManager.updateCar(id, x, y, dir);
                    } else {
                        ans = false;  // Set answer to false if no collision
                    }
                    break;   // Added break to prevent fall-through to the next case

                case 6:
                    //System.out.println("Deletion petition");
                    positionManager.deleteCar(id);
                    break;
            }

            // Enviar respuesta al cliente
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

            if (ans) {
                // Send 'Ok' message, include id if type is 0
                String message="Ok";
                if(type==0||type==1){
                    message+=" "+id;
                }
                output.println(message);
            } else {
                output.println("No");
            }

            // Cerrar las conexiones
            input.close();
            output.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}