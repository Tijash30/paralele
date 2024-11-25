import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        int basePort = 1234;
        int maxPorts = 5;
        TrafficManager.positionManager = new PositionManager();

        // Start a thread for each port
        for (int i = 0; i < maxPorts; i++) {
            int port = basePort + i;
            new Thread(() -> startServer(port)).start();
        }
    }

    private static void startServer(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Servidor iniciado en el puerto " + port + ". Esperando conexiones...");
            while (true) {
                // Accept a new client connection
                Socket clientSocket = serverSocket.accept();
                // Create a new thread to handle the client connection
                new Thread(new TrafficManager(clientSocket)).start();
            }
        } catch (IOException e) {
            System.err.println("Error en el puerto " + port + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}

