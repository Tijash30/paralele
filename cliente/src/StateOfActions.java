import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;


public class StateOfActions extends JFrame {
    private final JTabbedPane tabbedPane;
    private final Map<String, JTable> tabTables; // Map to store tables

    public StateOfActions() {
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
        String difStatesCars[]={"Creando", "Esperando", "Esperando", "Esperando", "Esperando", "Manejando", "Se fue"};
        String difStatesPeople[]={"Creando", "Esperando para Bajar", "Esperando", "Esperando", "Esperando", "Caminando", "Se fue"};

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
            for(int i=0; i<7; i++){
                if(contStates[i]!=0){
                    if(tabTitle=="People")
                        model.addRow(new Object[]{difStatesPeople[i], contStates[i]});
                    else 
                        model.addRow(new Object[]{difStatesCars[i], contStates[i]});
                }
            }
            table.setModel(model);
        }
    }
}
