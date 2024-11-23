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

    public static String sendMessage(int type, int id, int x, int y, int mov){

        // Create the message to send
        String message = type + " " + id + " " + x + " " + y + " " + mov+" ";
        try (Socket socket = new Socket("localhost", 1234)) {
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
            System.err.println("Error occurred while sending message: " + e.getMessage());
            e.printStackTrace();  // Optionally print stack trace for debugging
        }

        return "";
    }

}
