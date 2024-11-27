import javax.swing.*;
import java.awt.*;

public class Portada {
    public static void main(String[] args) {
        JFrame frame = new JFrame("City Simulation");

        // Main Panel
        JPanel splashPanel = new JPanel(new BorderLayout());

        // Titles Panel
        JPanel titlesPanel = new JPanel(new GridLayout(8, 1)); // Grid layout with 6 rows and 1 column

        // Add multiple title labels to the titles panel
        JLabel titleLabel1 = new JLabel("Proyecto Final", SwingConstants.CENTER);
        titleLabel1.setFont(new Font("Arial", Font.BOLD, 34));
        titlesPanel.add(titleLabel1);

        JLabel titleLabel2 = new JLabel("Ingeniería en sistemas y gráficas computacionales", SwingConstants.CENTER);
        titleLabel2.setFont(new Font("Arial", Font.PLAIN, 24));
        titlesPanel.add(titleLabel2);

        JLabel titleLabel3 = new JLabel("Fundamentos de programación en paralelo\n\n", SwingConstants.CENTER);
        titleLabel3.setFont(new Font("Arial", Font.PLAIN, 28));
        titlesPanel.add(titleLabel3);

        JLabel titleLabel8 = new JLabel("Juan Carlos López Pimentel\n\n", SwingConstants.CENTER);
        titleLabel8.setFont(new Font("Arial", Font.PLAIN, 28));
        titlesPanel.add(titleLabel8);

        JLabel titleLabel4 = new JLabel("Héctor Manuel Eguiarte Carlos", SwingConstants.CENTER);
        titleLabel4.setFont(new Font("Arial", Font.PLAIN, 18));
        titlesPanel.add(titleLabel4);

        JLabel titleLabel5 = new JLabel("Isaac Humberto Preciado Bazavilvazo", SwingConstants.CENTER);
        titleLabel5.setFont(new Font("Arial", Font.PLAIN, 18));
        titlesPanel.add(titleLabel5);

        JLabel titleLabel6 = new JLabel("Sinuhé Tijash Salamanca Ramos", SwingConstants.CENTER);
        titleLabel6.setFont(new Font("Arial", Font.PLAIN, 18));
        titlesPanel.add(titleLabel6);

        JLabel titleLabel7 = new JLabel("26 de noviembre del 2024", SwingConstants.CENTER);
        titleLabel7.setFont(new Font("Arial", Font.PLAIN, 18));
        titlesPanel.add(titleLabel7);
        

        splashPanel.add(titlesPanel, BorderLayout.NORTH); // Add titlesPanel to the top

        // Image Label
        JLabel imageLabel = new JLabel(new ImageIcon("uplogo.jpeg"));
        splashPanel.add(imageLabel, BorderLayout.CENTER); // Add image in the center

        // Bottom Panel for Button
        JPanel buttonPanel = new JPanel(new FlowLayout()); // A panel for buttons
        JButton startButton = new JButton("Empezar Simulación");
        startButton.setFont(new Font("Arial", Font.PLAIN, 16));
        buttonPanel.add(startButton); // Add button to buttonPanel
        splashPanel.add(buttonPanel, BorderLayout.SOUTH); // Add buttonPanel at the bottom

        // Add action listener for the button
        CitySimulation cete = new CitySimulation(); 
        startButton.addActionListener(e -> cete.maino());

        // Setup Frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600); // Set size of the frame
        frame.setLocationRelativeTo(null); // Center frame on screen
        frame.add(splashPanel); // Add splashPanel to the frame
        frame.setVisible(true); // Make the frame visible
    }
}
