import java.awt.*;

public class CityMap {
    private static int width;
    private static int height;
    public static int streetWidth; // Width of streets

    public CityMap(int width, int height) {
        this.width = width;
        this.height = height;
        streetWidth = 40;
    }

    public void draw(Graphics g) {
        int lineThickness = 2; // Thickness of the white line

        // Draw sidewalks vertically
        g.setColor(Color.DARK_GRAY);
        for (int i = 50; i < width; i += 100) {
            g.fillRect(i, 0, streetWidth+20, height);
        }

        // Draw horizontal streets
        for (int i = 50; i < height; i += 100) {
            g.fillRect(0, i, width, streetWidth+20);
        }


        g.setColor(Color.BLACK);
        for (int i = 60; i < width; i += 100) {
            g.fillRect(i, 0, streetWidth, height);

            g.setColor(Color.WHITE);
            g.fillRect(i + streetWidth / 2 - lineThickness / 2, 0, lineThickness, height);
            g.setColor(Color.BLACK);
        }

        // Draw horizontal streets
        for (int i = 60; i < height; i += 100) {
            g.fillRect(0, i, width, streetWidth);

            g.setColor(Color.WHITE);
            g.fillRect(0, i + streetWidth / 2 - lineThickness / 2, width, lineThickness);
            g.setColor(Color.BLACK);
        }
        // Draw horizontal streets
        for (int i = 80; i < width; i += 100) {
            for (int j = 80; j < height; j += 100) {
                g.fillRect(i - streetWidth/ 2 , j - streetWidth/ 2 , streetWidth, streetWidth);
            }
        }
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }
}
