import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TablePanel extends JPanel
{
    private final JTable table;
    private final TableRowSorter<TableModel> sorter;
    private final DefaultTableModel tableModel;
    private final List<TableObserver> observers = new ArrayList<>();

    public TablePanel(List<DataItem> dataItems, StatsPanel statsPanel, PieChart pieChart)
    {
        setLayout(new BorderLayout());
        addObserver(statsPanel);
        addObserver(pieChart);

        //create table model with column names
        String[] columnNames =
                {
                "Vertical Disp. (microns)",
                "Confining Pressure (MPa)",
                "Displacement (mm)",
                "Coefficient of Friction",
                "Normal Stress (MPa)",
                "Shear Stress (MPa)",
                "Time (s)",
                "Velocity (mm/s)"
                };
        //creating table
        tableModel = new DefaultTableModel(columnNames, 0);//empty table model
        table = new JTable(tableModel);//initializes JTable with model
        //set up the table sorter
        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
        //populate table with data
        populateTable(dataItems);
        //add table to JScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }
    //method to populate the table with data
    private void populateTable(List<DataItem> dataItems)
    {
        tableModel.setRowCount(0);//clear existing data
        for (DataItem item : dataItems)
        //create row for each DataItem and add it to table model
        {
            Object[] row =
                    {
                    item.verticalDispMicrons(),
                    item.confiningPressureMPa(),
                    item.displacementMM(),
                    item.coefficientFriction(),
                    item.normalStressMPa(),
                    item.shearStressMPa(),
                    item.timeS(),
                    item.velocityMMPerS()
                    };
            tableModel.addRow(row);//add row to table model
        }
    }
    //update the table with new data after filtering
    public void updateTable(List<DataItem> dataItems)
    {
        populateTable(dataItems);//populate table with filtered data
        sorter.setModel(tableModel);//update sorter with new model
        notifyObservers(dataItems);
    }

    //add observe so panels can use whenever tablePanel is used by filters
    public void addObserver(TableObserver observer)
    {
        observers.add(observer);
    }
    //notifies and updates the observer
    public void notifyObservers(List<DataItem> dataItems)
    {
        for (TableObserver observer : observers)
        {
            observer.update(dataItems);
        }
    }
    public JTable getTable()
    {
        return table;//return table instance
    }
}