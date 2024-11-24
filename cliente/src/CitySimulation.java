import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class CitySimulation extends JPanel {
    private final ArrayList<Car> cars = new ArrayList<>();
    private final ArrayList<Taxi> taxis = new ArrayList<>();
    private final ArrayList<People> people = new ArrayList<>();
    private final ArrayList<Intersection> intersections = new ArrayList<>();
    private final CityMap cityMap;
    private final static int width = 860;
    private final static int height = 660;
    public static Random random;

    public CitySimulation() {
        random = new Random();
        cityMap = new CityMap(width, height);
        setPreferredSize(new Dimension(width, height));
        // Initialize intersections
        initializeIntersections();
    }

    private void initializeIntersections() {

        for (int i = 80; i < width; i += 100) {
            for (int j = 80; j < height; j += 100) {
                Intersection intersection = new Intersection(i, j);
                intersections.add(intersection);
                new Thread(intersection).start();
            }
        }
    }

    public void addCar() {
        Car car = new Car(random.nextInt(width/100) * 100 + 85,
                          random.nextInt(height/100)* 100 + 85);
        cars.add(car);
        new Thread(car).start();
    }

    public void removeCar() {
        if (!cars.isEmpty()) {
            Car car = cars.remove(cars.size() - 1);
            car.stop();
        }
    }

    public void addTaxi() {
        Taxi taxi = new Taxi(random.nextInt(width/100) * 100 + 85,
                            random.nextInt(height/100)* 100 + 85);
        taxis.add(taxi);
        new Thread(taxi).start();
    }

    public void removeTaxi() {
        if (!taxis.isEmpty()) {
            Taxi taxi = taxis.remove(taxis.size() - 1);
            taxi.stop();
        }
    }

    public void addPerson() {
        People person = new People(random.nextInt((width/100)-1) * 100 + 105,
                                    random.nextInt((height/100)-1) * 100 + 105, taxis);
        people.add(person);
        new Thread(person).start();
    }

    public void removePerson() {
        if (!people.isEmpty()) {
            People person = people.remove(people.size() - 1);
            person.stop();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        cityMap.draw(g);
        for (Car car : cars) {
            car.draw(g);
        }
        for (Taxi taxi : taxis) {
            taxi.draw(g);
        }
        for (People person : people) {
            if(!person.isOnRide)
                person.draw(g);
        }
        for (Intersection intersection : intersections) {
            intersection.draw(g);
        }
    }

    public ArrayList<Intersection> getIntersections() {
        return intersections;
    }

    public ArrayList<People> getPeople() {
        return people;
    }

    public ArrayList<Taxi> getTaxis() {
        return taxis;
    }

    public ArrayList<Car> getCars() {
        return cars;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("City Simulation with Controls");
        CitySimulation city = new CitySimulation();
        ThreadStateWindow threadStateWindow = new ThreadStateWindow();
        threadStateWindow.setVisible(true);
        StatesOfThreads statesofthreads = new StatesOfThreads();
        statesofthreads.setVisible(true);
        StateOfActions stateofactions = new StateOfActions();
        stateofactions.setVisible(true);

        // Tabbed Pane for Controls (Tabs on the LEFT)
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);

        // Control Panel
        JPanel controlPanel = new JPanel(new GridLayout(3, 1, 10, 10));

        // Car Controls
        JPanel carPanel = new JPanel(new FlowLayout());
        JLabel carLabel = new JLabel("Cars:");
        JButton addCarButton = new JButton("Add Car");
        JButton removeCarButton = new JButton("Remove Car");
        addCarButton.addActionListener(e -> city.addCar());
        removeCarButton.addActionListener(e -> city.removeCar());
        carPanel.add(carLabel);
        carPanel.add(addCarButton);
        carPanel.add(removeCarButton);
        controlPanel.add(carPanel);

        // Taxi Controls
        JPanel taxiPanel = new JPanel(new FlowLayout());
        JLabel taxiLabel = new JLabel("Taxis:");
        JButton addTaxiButton = new JButton("Add Taxi");
        JButton removeTaxiButton = new JButton("Remove Taxi");
        addTaxiButton.addActionListener(e -> city.addTaxi());
        removeTaxiButton.addActionListener(e -> city.removeTaxi());
        taxiPanel.add(taxiLabel);
        taxiPanel.add(addTaxiButton);
        taxiPanel.add(removeTaxiButton);
        controlPanel.add(taxiPanel);

        // People Controls
        JPanel peoplePanel = new JPanel(new FlowLayout());
        JLabel peopleLabel = new JLabel("People:");
        JButton addPeopleButton = new JButton("Add Person");
        JButton removePeopleButton = new JButton("Remove Person");
        addPeopleButton.addActionListener(e -> city.addPerson());
        removePeopleButton.addActionListener(e -> city.removePerson());
        peoplePanel.add(peopleLabel);
        peoplePanel.add(addPeopleButton);
        peoplePanel.add(removePeopleButton);
        controlPanel.add(peoplePanel);

        tabbedPane.add("Controls", controlPanel);

        // Add City Simulation and Controls to Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(city, BorderLayout.CENTER);
        mainPanel.add(tabbedPane, BorderLayout.WEST);

        // Setup Frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(mainPanel);
        frame.pack();
        frame.setVisible(true);

        // Repaint Continuously
        new Timer(16, e ->{
            city.repaint();
            threadStateWindow.updateThreadStates(
                    city.getCars(),
                    city.getTaxis(),
                    city.getPeople(),
                    city.getIntersections()
            );
            statesofthreads.updateThreadStates(
                    city.getCars(),
                    city.getTaxis(),
                    city.getPeople()
            );
            stateofactions.updateThreadStates(
                    city.getCars(),
                    city.getTaxis(),
                    city.getPeople()
            );
        } ).start(); // ~60 FPS
    }
}
