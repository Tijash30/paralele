import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;


public class StatesOfThreads extends JFrame {
    private final JTabbedPane tabbedPane;
    private final Map<String, JTable> tabTables; // Map to store tables

    public StatesOfThreads() {
        setTitle("States of Threads");
        setSize(300, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        tabbedPane = new JTabbedPane();
        tabTables = new HashMap<>();
        add(tabbedPane, BorderLayout.CENTER);

        // Initialize tables for each thread type
        tabTables.put("Cars", new JTable());
        tabTables.put("Taxis", new JTable());
        tabTables.put("People", new JTable());

        // Add tables to each tab
        tabTables.forEach((title, table) -> tabbedPane.add(title, new JScrollPane(table)));
    }

    public void updateThreadStates(
            List<Car> cars,
            List<Taxi> taxis,
            List<People> people
    ) {
        // Update each table with thread state information
        updateTableContent("Cars", cars);
        updateTableContent("Taxis", taxis);
        updateTableContent("People", people);

        tabbedPane.revalidate();
        tabbedPane.repaint();
    }

    private <T extends Runnable> void updateTableContent(String tabTitle, List<T> threads) {
        JTable table = tabTables.get(tabTitle);
        int contStates[]={0,0,0,0,0,0,0};

        if (table != null) {
            // Create a DefaultTableModel with column headers
            String[] columns = {"States", "count"};
            DefaultTableModel model = new DefaultTableModel(columns, 0);
            

            // Populate the table with thread information
            for (T runnable : threads) {
                if (runnable instanceof ThreadedAgent) {
                    Thread thread = ((ThreadedAgent) runnable).getThread();
                    String state = thread.getState().toString();
                    if(state =="NEW")
                        contStates[0]+=1;
                    else if(state=="RUNNABLE")
                        contStates[1]+=1;
                    else if(state=="RUNNING")
                        contStates[2]+=1;
                    else if(state=="BLOCKED")
                        contStates[3]+=1;
                    else if(state=="WAITING")
                        contStates[4]+=1;
                    else if(state=="TIMED_WAITING")
                        contStates[5]+=1;
                    else if(state=="TERMINATED")
                        contStates[6]+=1;
                    //model.addRow(new Object[]{state, 0});
                }
            }
            model.addRow(new Object[]{"NEW", contStates[0]});
            model.addRow(new Object[]{"RUNNABLE", contStates[1]});
            model.addRow(new Object[]{"RUNNING", contStates[2]});
            model.addRow(new Object[]{"BLOCKED", contStates[3]});
            model.addRow(new Object[]{"WAITING", contStates[4]});
            model.addRow(new Object[]{"TIMED_WAITING", contStates[5]});
            model.addRow(new Object[]{"TERMINATED", contStates[6]});

            table.setModel(model);
        }
    }
}
