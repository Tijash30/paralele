public class IntersectionInfo {
    private int x, y; // Position of the intersection
    private int activeLight;

    public IntersectionInfo(int x, int y, int activeLight) {
        this.x = x;
        this.y = y;
        this.activeLight = activeLight;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getActiveLight() {
        return activeLight;
    }

    public void setActiveLight(int activeLight) {
        this.activeLight = activeLight;
    }

    public void update(int x, int y, int activeLight){
        this.x = x;
        this.y = y;
        this.activeLight = activeLight;

    }
}
