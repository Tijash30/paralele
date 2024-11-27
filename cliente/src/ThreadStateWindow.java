import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThreadStateWindow extends JFrame {
    private final JTabbedPane tabbedPane;
    private final Map<String, JPanel> tabPanels; // Map to store tabs

    public ThreadStateWindow() {
        setTitle("Thread State Monitor");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        tabbedPane = new JTabbedPane();
        tabPanels = new HashMap<>();
        add(tabbedPane, BorderLayout.CENTER);

        // Initialize tabs
        tabPanels.put("Cars", new JPanel());
        tabPanels.put("Taxis", new JPanel());
        tabPanels.put("People", new JPanel());
        tabPanels.put("Intersections", new JPanel());

        tabPanels.forEach((title, panel) -> tabbedPane.add(title, panel));
    }

    public void updateThreadStates(
            List<Car> cars,
            List<Taxi> taxis,
            List<People> people,
            List<Intersection> intersections
    ) {
        // Update each tab panel
        updateCarContent("Cars", cars);
        updateTaxiContent("Taxis", taxis);
        updatePeopleContent("People", people);
        updateTabContent("Intersections", intersections);

        tabbedPane.revalidate();
        tabbedPane.repaint();
    }

    private <T extends Runnable> void updateTabContent(String tabTitle, List<T> threads) {
        JPanel panel = tabPanels.get(tabTitle);

        if (panel != null) {
            panel.removeAll(); // Clear previous content

            DefaultListModel<String> listModel = new DefaultListModel<>();
            JList<String> threadList = new JList<>(listModel);

            for (T runnable : threads) {
                if (runnable instanceof ThreadedAgent) {
                    Thread thread = ((ThreadedAgent) runnable).getThread();
                    String stateInfo = String.format("Thread ID: %d | State: %s",
                            thread.getId(), thread.getState());
                    listModel.addElement(stateInfo);
                }
            }

            panel.setLayout(new BorderLayout());
            panel.add(new JScrollPane(threadList), BorderLayout.CENTER);
        }
    }

    private <T extends Runnable> void updatePeopleContent(String tabTitle, List<People> threads) {
        JPanel panel = tabPanels.get(tabTitle);

        if (panel != null) {
            panel.removeAll(); // Clear previous content

            DefaultListModel<String> listModel = new DefaultListModel<>();
            JList<String> threadList = new JList<>(listModel);

            for (People person : threads) {
                    Thread thread = person.getThread();
                    String stateInfo = String.format("Thread ID: %d | State: %s",
                            thread.getId(), thread.getState());
                    String state = thread.getState().toString();
                            if(state =="NEW")
                        stateInfo+="| Creando";
                    else {
                        if(!person.isRunning()){
                            stateInfo+="| Se fue";
                            continue;
                        }
                        if(person.isOnRide){
                        stateInfo+="| Esperando para bajar";
                        }else{
                            if(!person.isAllowMovement()){
                                
                                stateInfo+="| Esperando para subir";
                            }else{
                                
                                stateInfo+="| Caminado";
                            }
                        }
                    }
                    listModel.addElement(stateInfo);
                
            }

            panel.setLayout(new BorderLayout());
            panel.add(new JScrollPane(threadList), BorderLayout.CENTER);
        }
    }

    private <T extends Runnable> void updateCarContent(String tabTitle, List<Car> cars) {
        JPanel panel = tabPanels.get(tabTitle);

        if (panel != null) {
            panel.removeAll(); // Clear previous content

            DefaultListModel<String> listModel = new DefaultListModel<>();
            JList<String> threadList = new JList<>(listModel);

            for (Car car : cars) {
                    Thread thread = car.getThread();
                    String stateInfo = String.format("Thread ID: %d | State: %s",
                            thread.getId(), thread.getState());
                    String state = thread.getState().toString();
                    if(state =="NEW")
                        stateInfo+="| Creando";
                        
                    else {
                        if(car.running){
                            if(car.trafico){
                                stateInfo+="| Esperando Trafico";
                            }else{
                                stateInfo+="| Manejando";
                            }
                        }else{
                            stateInfo+="| Desapareciendo";
                        }
                    }
                    listModel.addElement(stateInfo);
                
            }

            panel.setLayout(new BorderLayout());
            panel.add(new JScrollPane(threadList), BorderLayout.CENTER);
        }
    }

    private <T extends Runnable> void updateTaxiContent(String tabTitle, List<Taxi> cars) {
        JPanel panel = tabPanels.get(tabTitle);

        if (panel != null) {
            panel.removeAll(); // Clear previous content

            DefaultListModel<String> listModel = new DefaultListModel<>();
            JList<String> threadList = new JList<>(listModel);

            for (Taxi car: cars) {
                    Thread thread = car.getThread();
                    String stateInfo = String.format("Thread ID: %d | State: %s",
                            thread.getId(), thread.getState());
                    String state = thread.getState().toString();
                if(state =="NEW")
                    stateInfo+="| Creando";
                else {
                    if(car.running){
                        if(car.trafico){
                            stateInfo+="| Esperando Trafico";
                        }else{
                            if(car.loading)
                                stateInfo+="| Recojiendo Pasajero";
                            else{
                                if(car.loaded)
                                    stateInfo+="| Dejando Pasajero";
                                else
                                    stateInfo+="| Manejando";
                            }
                        }
                    }else{
                        stateInfo+="| Desapareciendo";
                    }
                }
                    listModel.addElement(stateInfo);
                
            }

            panel.setLayout(new BorderLayout());
            panel.add(new JScrollPane(threadList), BorderLayout.CENTER);
        }
    }
}
