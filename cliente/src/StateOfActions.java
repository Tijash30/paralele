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
        updateCarContent("Cars", cars);
        updateTaxiContent("Taxis", taxis);
        updatePeopleContent("People", people);

        tabbedPane.revalidate();
        tabbedPane.repaint();
    }

    private <T extends Runnable> void updatePeopleContent(String tabTitle, List<People> threads) {
        JTable table = tabTables.get(tabTitle);
        int contStates[]={0,0,0,0,0,0,0};
        String difStatesPeople[]={"Creando", "Esperando para Bajar",  "Esperando para subir", "Caminando", "Se fue"};

        if (table != null) {
            // Create a DefaultTableModel with column headers
            String[] columns = {"States", "count"};
            DefaultTableModel model = new DefaultTableModel(columns, 0);


            // Populate the table with thread information
            for (People person: threads) {
                    Thread thread = person.getThread();
                    String state = thread.getState().toString();
                    if(state =="NEW")
                        contStates[0]+=1;
                    else {
                        if(!person.isRunning()){
                            contStates[4]+=1;
                            continue;
                        }
                        if(person.isOnRide){
                            contStates[1]+=1;
                        }else{
                            if(!person.isAllowMovement()){
                                contStates[2]+=1;
                            }else{
                                contStates[3]+=1;
                            }
                        }
                    }
                    //model.addRow(new Object[]{state, 0});

            }
            for(int i=0; i<5; i++){
                model.addRow(new Object[]{difStatesPeople[i], contStates[i]});
            }
            table.setModel(model);
        }
    }

    private <T extends Runnable> void updateCarContent(String tabTitle, List<Car> cars) {
        JTable table = tabTables.get(tabTitle);
        int contStates[]={0,0,0,0,0,0,0};
        String difStatesCars[]={"Creando", "Esperando Trafico", "Manejando", "Desparaeciendo"};

        if (table != null) {
            // Create a DefaultTableModel with column headers
            String[] columns = {"States", "count"};
            DefaultTableModel model = new DefaultTableModel(columns, 0);
            // Populate the table with thread information
            for (Car car : cars) {
                Thread thread = car.getThread();
                String state = thread.getState().toString();
                if(state =="NEW")
                    contStates[0]+=1;
                else {
                    if(car.running){
                        if(car.trafico){
                            contStates[1]+=1;
                        }else{
                            contStates[2]+=1;
                        }
                    }else{
                        contStates[3]+=1;
                    }
                }
                //model.addRow(new Object[]{state, 0});

            }
            for(int i=0; i<4; i++){
                model.addRow(new Object[]{difStatesCars[i], contStates[i]});
            }
            table.setModel(model);
        }
    }

    private <T extends Runnable> void updateTaxiContent(String tabTitle, List<Taxi> cars) {
        JTable table = tabTables.get(tabTitle);
        int contStates[]={0,0,0,0,0,0,0};
        String difStatesCars[]={"Creando","Esperando Trafico", "Manejando","Recogiendo Pasajero", "Dejando Pasajero", "Desparaeciendo"};

        if (table != null) {
            // Create a DefaultTableModel with column headers
            String[] columns = {"States", "count"};
            DefaultTableModel model = new DefaultTableModel(columns, 0);
            // Populate the table with thread information
            for (Taxi car : cars) {
                Thread thread = car.getThread();
                String state = thread.getState().toString();
                if(state =="NEW")
                    contStates[0]+=1;
                else {
                    if(car.running){
                        if(car.trafico){
                            contStates[1]+=1;
                        }else{
                            if(car.loading)
                                contStates[3]+=1;
                            else{
                                if(car.loaded)
                                    contStates[4]+=1;
                                else
                                    contStates[2]+=1;
                            }
                        }
                    }else{
                        contStates[6]+=1;
                    }
                }
                //model.addRow(new Object[]{state, 0});

            }
            for(int i=0; i<6; i++){
                model.addRow(new Object[]{difStatesCars[i], contStates[i]});
            }
            table.setModel(model);
        }
    }


}
