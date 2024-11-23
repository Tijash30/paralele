import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        int puerto = 1234;
        TrafficManager.positionManager = new PositionManager();
        try (ServerSocket serverSocket = new ServerSocket(puerto)) {
            System.out.println("Servidor iniciado. Esperando conexiones en el puerto " + puerto + "...");

            while (true) {
                // Aceptar una nueva conexión de cliente
                Socket clientSocket = serverSocket.accept();
                //System.out.println("Cliente conectado desde " + clientSocket.getInetAddress());
                // Crear un nuevo hilo para manejar la conexión del cliente
                new Thread(new TrafficManager(clientSocket)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

