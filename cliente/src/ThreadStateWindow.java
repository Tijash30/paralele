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
        updateTabContent("Cars", cars);
        updateTabContent("Taxis", taxis);
        updateTabContent("People", people);
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
}
