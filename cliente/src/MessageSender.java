import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MessageSender {
    /*
    0.- Register Car
    1.- Register Traffic Light
    2.- Update Car
    3.- Update Traffic Light
    4.- Ask for movement
    5.- Ask for turn
    6.- Remove Car
     */

    public static String sendMessage(int type, int id, int x, int y, int mov) {
        // Create the message to send
        String message = type + " " + id + " " + x + " " + y + " " + mov + " ";
        int basePort = 1234;
        int maxRetries = 5;
        for (int i = 0; i < maxRetries; i++) {
            int port = basePort + i;
            try (Socket socket = new Socket("localhost", port)) {
                // Send message to server
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
                output.println(message);  // Sends the message

                // Read the server's response
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String respuestaServidor = input.readLine();
                input.close();
                output.close();
                return respuestaServidor;
            } catch (IOException e) {
                System.err.println("Port " + port + " is unavailable. Trying the next port...");
            }
        }

        System.err.println("Failed to connect to any of the ports after " + maxRetries + " attempts.");
        return "";
    }

}
