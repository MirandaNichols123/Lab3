import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class TablePanel extends JPanel
{
    private final JTable table;
    private final TableRowSorter<TableModel> sorter;
    private final List<DataItem> allItems;
    private final DefaultTableModel tableModel;
    private final JCheckBox filter1, filter2, filter3;//filter checkboxes
    private final StatsPanel statsPanel;
    private final PieChart pieChart;

    public TablePanel(List<DataItem> dataItems, StatsPanel statsPanel, PieChart pieChart)
    {
        setLayout(new BorderLayout());
        //store original data
        this.allItems = dataItems;
        //store refernces to panel/chart
        this.statsPanel = statsPanel;
        this.pieChart = pieChart;

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

        //add checkboxes for filter
        JPanel filterPanel = new JPanel();
        //check boxes
        filter1 = new JCheckBox("Display Friction higher than 0.5");
        filter2 = new JCheckBox("Display pressure less than 25");
        filter3 = new JCheckBox("Display within 1 std of mean Stress");
        filterPanel.add(filter1);
        filterPanel.add(filter2);
        filterPanel.add(filter3);

        //add action listeners for filters when updated
        filter1.addActionListener(_ -> updateTable(applyFilters()));
        filter2.addActionListener(_ -> updateTable(applyFilters()));
        filter3.addActionListener(_ -> updateTable(applyFilters()));

        add(filterPanel, BorderLayout.NORTH);
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
    //method to update the table with new data after filtering
    public void updateTable(List<DataItem> dataItems)
    {
        populateTable(dataItems);//populate table with filtered data
        sorter.setModel(tableModel);//update sorter with new model
        statsPanel.updateStats(dataItems);//update stats based on filtered data
        pieChart.updateData(dataItems);//update pie chart based on filtered data
    }
    //method to apply filters based on checkbox selections
    private List<DataItem> applyFilters()
    {
        //start with all items
        List<DataItem> filteredItems = allItems;
        //apply filter 1
        if (filter1.isSelected())
        {
            //create stream from filteredItems to process items
            filteredItems = filteredItems.stream()
                    //filter items included those where the coefficient of friction greater than 0.5
                    .filter(item -> item.coefficientFriction() > 0.5)
                    //collect filtered results back into List<DataItem>
                    .collect(Collectors.toList());
        }
        //apply filter 2
        if (filter2.isSelected())
        {
            filteredItems = filteredItems.stream()
                    .filter(item -> item.confiningPressureMPa() < 25)
                    .collect(Collectors.toList());
        }
        //apply filter 3
        if (filter3.isSelected())
        {
            //calculate mean and std stress across all items using method and mean from DataLoader
            double meanStress = DataLoader.calcMeanStress(allItems);
            double stdDevStress = DataLoader.calcStdDevStress(allItems, meanStress);
            filteredItems = filteredItems.stream()
                    //filter items whose stress is within 1 std of mean stress
                    .filter(item -> Math.abs(item.shearStressMPa() - meanStress) <= stdDevStress)
                    .collect(Collectors.toList());
        }
        updateTable(filteredItems);//update displayed table with filtered items
        return filteredItems;//return filtered list
    }
    public JTable getTable()
    {
        return table;//return table instance
    }
}