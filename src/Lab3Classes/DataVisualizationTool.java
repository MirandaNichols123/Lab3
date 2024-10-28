import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DataVisualizationTool {
    private TablePanel tablePanel;
    private StatsPanel statsPanel;
    private PieChart pieChart;
    private DetailsPanel detailsPanel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            DataVisualizationTool app = new DataVisualizationTool();
            app.createAndShowGUI();
        });
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Data Visualization Tool");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Load data
        List<DataItem> dataItems = loadData(frame);
        if (dataItems == null) return; // Exit if loading failed

        // Initialize GUI components
        initializeComponents(dataItems);

        // Set up layout
        setupLayout(frame);

        // Set frame properties
        frame.setSize(1500, 760);
        frame.setVisible(true);
    }

    private List<DataItem> loadData(JFrame frame) {
        String filePath = "src/Experiment_404_mechanical_data.csv"; // Adjust file path as necessary
        try {
            List<DataItem> dataItems = DataLoader.loadData(filePath);
            System.out.println("Data loaded successfully from: " + filePath);
            return dataItems;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error loading data: " + e.getMessage(),
                    "File Load Error", JOptionPane.ERROR_MESSAGE);
            return null; // Return null if loading fails
        }
    }

    private void initializeComponents(List<DataItem> dataItems) {
        statsPanel = new StatsPanel();
        pieChart = new PieChart(dataItems, "Friction Coefficient Distribution"); // Create pie chart first
        tablePanel = new TablePanel(dataItems, statsPanel, pieChart); // Pass pie chart to the table panel
        detailsPanel = new DetailsPanel();

        // Update stats with initial data
        statsPanel.updateStats(dataItems);

        // Set up table selection listener
        tablePanel.getTable().getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = tablePanel.getTable().getSelectedRow();
            if (selectedRow != -1) {
                int modelRow = tablePanel.getTable().convertRowIndexToModel(selectedRow);
                DataItem selectedItem = dataItems.get(modelRow);
                detailsPanel.showDetails(selectedItem);
            }
        });
    }

    private void setupLayout(JFrame frame) {
        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        mainSplitPane.setLeftComponent(tablePanel);

        JSplitPane rightSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        rightSplitPane.setTopComponent(detailsPanel);
        rightSplitPane.setBottomComponent(pieChart);
        rightSplitPane.setDividerLocation(200);

        mainSplitPane.setRightComponent(rightSplitPane);
        mainSplitPane.setDividerLocation(750);

        frame.add(statsPanel, BorderLayout.NORTH);
        frame.add(mainSplitPane, BorderLayout.CENTER);
    }
}