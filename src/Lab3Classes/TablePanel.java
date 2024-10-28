import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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

    public TablePanel(List<DataItem> dataItems, StatsPanel statsPanel, PieChart pieChart) {
        setLayout(new BorderLayout());
        this.allItems = dataItems;
        this.statsPanel = statsPanel;
        this.pieChart = pieChart;

        //create table model with columns
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
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);

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
        filter1 = new JCheckBox("Display Friction higher than 0.5");
        filter2 = new JCheckBox("Display pressure less than 25");
        filter3 = new JCheckBox("Display within 1 std of mean Friction");
        filterPanel.add(filter1);
        filterPanel.add(filter2);
        filterPanel.add(filter3);

        //add action listeners for filters
        filter1.addActionListener(e -> updateTable(applyFilters()));
        filter2.addActionListener(e -> updateTable(applyFilters()));
        filter3.addActionListener(e -> updateTable(applyFilters()));

        add(filterPanel, BorderLayout.NORTH);
    }
    //method to populate the table with data
    private void populateTable(List<DataItem> dataItems) {
        tableModel.setRowCount(0); // Clear existing data
        for (DataItem item : dataItems) {
            Object[] row = {
                    item.verticalDispMicrons(),
                    item.confiningPressureMPa(),
                    item.displacementMM(),
                    item.coefficientFriction(),
                    item.normalStressMPa(),
                    item.shearStressMPa(),
                    item.timeS(),
                    item.velocityMMPerS()
            };
            tableModel.addRow(row);
        }
    }

    // Method to update the table with new data after filtering
    public void updateTable(List<DataItem> dataItems) {
        populateTable(dataItems);
        sorter.setModel(tableModel);
        //no need to reapply sorter as it's already set
        statsPanel.updateStats(dataItems); // Update stats based on filtered data
        pieChart.updateData(dataItems);
    }

    // Method to apply filters based on checkbox selections
    private List<DataItem> applyFilters() {
        // Start with all items
        List<DataItem> filteredItems = allItems;

        // Apply filter 1
        if (filter1.isSelected()) {
            filteredItems = filteredItems.stream()
                    .filter(item -> item.coefficientFriction() > 0.5)
                    .collect(Collectors.toList());
        }

        // Apply filter 2
        if (filter2.isSelected()) {
            filteredItems = filteredItems.stream()
                    .filter(item -> item.confiningPressureMPa() < 25)
                    .collect(Collectors.toList());
        }

        // Apply filter 3
        if (filter3.isSelected()) {
            double meanFriction = DataLoader.calcMeanFriction(allItems);
            double stdDevFriction = DataLoader.calcStdDevFriction(allItems, meanFriction);
            filteredItems = filteredItems.stream()
                    .filter(item -> Math.abs(item.coefficientFriction() - meanFriction) <= stdDevFriction)
                    .collect(Collectors.toList());
        }

        // Update the table with filtered data
        updateTable(filteredItems);

        return filteredItems;
    }

    public JTable getTable() {
        return table;
    }
}