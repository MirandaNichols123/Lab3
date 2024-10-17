package Lab3Classes;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class DataTableModel extends AbstractTableModel {
    private List<DataItem> dataItems; // List holding the data
    private final String[] columnNames = {
            "Vertical Disp (microns)",
            "Confining Pressure (MPa)",
            "Displacement (mm)",
            "Friction Coefficient",
            "Normal Stress (MPa)",
            "Shear Stress (MPa)",
            "Time (s)",
            "Velocity (mm/s)"
    };

    // Constructor to initialize with a list of DataItems
    public DataTableModel(List<DataItem> dataItems) {
        this.dataItems = dataItems;
    }

    @Override
    public int getRowCount() {
        return dataItems.size(); // Number of rows equals number of items
    }

    @Override
    public int getColumnCount() {
        return columnNames.length; // Number of columns equals number of column names
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col]; // Return column name based on the index
    }

    @Override
    public Object getValueAt(int row, int col) {
        DataItem item = dataItems.get(row); // Get the data item for the specific row
        // Return the appropriate data based on the column index
        switch (col) {
            case 0: return item.getVerticalDispMicrons();
            case 1: return item.getConfiningPressureMPa();
            case 2: return item.getDisplacementMM();
            case 3: return item.getCoefficientFriction();
            case 4: return item.getNormalStressMPa();
            case 5: return item.getShearStressMPa();
            case 6: return item.getTimeS();
            case 7: return item.getVelocityMMPerS();
            default: return null;
        }
    }

    // Method to add a new row of data
    public void addRow(DataItem newItem) {
        dataItems.add(newItem); // Add the new item to the list
        fireTableRowsInserted(dataItems.size() - 1, dataItems.size() - 1); // Notify JTable that a new row has been added
    }

    // Optional: Method to remove a row by index
    public void removeRow(int rowIndex) {
        dataItems.remove(rowIndex); // Remove the item from the list
        fireTableRowsDeleted(rowIndex, rowIndex); // Notify JTable that a row has been deleted
    }

    // Method to update the entire table data
    public void updateData(List<DataItem> newDataItems) {
        this.dataItems = newDataItems; // Replace the current data with new data
        fireTableDataChanged(); // Notify JTable that the data has been updated
    }

    // Optional: Make certain cells editable
    @Override
    public boolean isCellEditable(int row, int col) {
        // Example: Only make the first column editable
        return col == 0;
    }
}
