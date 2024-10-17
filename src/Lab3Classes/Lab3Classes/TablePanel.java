package Lab3Classes;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;

public class TablePanel extends JPanel {
    private final JTable table;
    private TableRowSorter<TableModel> sorter;
    private List<DataItem> allItems;
    private final DefaultTableModel tableModel;
    private final JCheckBox filter1, filter2, filter3;

    public TablePanel(List<DataItem> dataItems) {
        setLayout(new BorderLayout());
        this.allItems = dataItems;

        // Create table model with columns
        String[] columnNames = {"Vertical Disp. (microns)", "Confining Pressure (MPa)",
                "Displacement (mm)", "Coefficient of Friction",
                "Normal Stress (MPa)", "Shear Stress (MPa)",
                "Time (s)", "Velocity (mm/s)"};

        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);

        // Set up the table sorter
        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        // Populate table with data
        populateTable(dataItems);

        // Add table to JScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Add filters (checkboxes)
        JPanel filterPanel = new JPanel();
        filter1 = new JCheckBox("Filter 1");
        filter2 = new JCheckBox("Filter 2");
        filter3 = new JCheckBox("Filter 3");
        filterPanel.add(filter1);
        filterPanel.add(filter2);
        filterPanel.add(filter3);

        // Add action listeners for filters
        filter1.addActionListener(e -> updateTable(applyFilters()));
        filter2.addActionListener(e -> updateTable(applyFilters()));
        filter3.addActionListener(e -> updateTable(applyFilters()));

        add(filterPanel, BorderLayout.NORTH);

        // Add selection listener to notify DetailsPanel when a row is selected
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        DataItem selectedItem = allItems.get(table.convertRowIndexToModel(selectedRow));
                        // Notify the DetailsPanel of the selected item
                        // (Assume you have a method in DetailsPanel to update details)
                        // detailsPanel.updateDetails(selectedItem);
                    }
                }
            }
        });
    }

    // Method to populate the table with data
    private void populateTable(List<DataItem> dataItems) {
        tableModel.setRowCount(0); // Clear existing data
        for (DataItem item : dataItems) {
            Object[] row = {
                    item.getVerticalDispMicrons(),
                    item.getConfiningPressureMPa(),
                    item.getDisplacementMM(),
                    item.getCoefficientFriction(),
                    item.getNormalStressMPa(),
                    item.getShearStressMPa(),
                    item.getTimeS(),
                    item.getVelocityMMPerS()
            };
            tableModel.addRow(row);
        }
    }

    // Method to update the table with new data after filtering
    public void updateTable(List<DataItem> dataItems) {
        populateTable(dataItems);
        sorter.setModel(tableModel); // Reapply sorter after updating the model
    }

    // Method to apply filters based on checkbox selections
    private List<DataItem> applyFilters() {
        return allItems.stream()
                .filter(item -> filter1.isSelected() || filter2.isSelected() || filter3.isSelected()) // Example filter logic
                .toList();
    }

    public JTable getTable() {
        return table;
    }
}