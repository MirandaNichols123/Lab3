import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataVisualization
{
    private TablePanel tablePanel;
    private Filters filters;
    private StatsPanel statsPanel;
    private PieChart pieChart;
    private DetailsPanel detailsPanel;
    private static final Logger logger= Logger.getLogger(DataVisualization.class.getName());//logger for error logging

    //public static void main(args[])
    public static void main(String[] args)
    {
        //GUI runs on Event Dispatch Thread
        SwingUtilities.invokeLater(() ->
        {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());//sets OS-specific looks and feel
            } catch (Exception e) {
                //uses the logger to log the exception
                logger.log(Level.SEVERE, "Failed to set Look and Feel", e);
            }
            DataVisualization app = new DataVisualization();//create application instance
            app.createAndShowGUI();//initializes and display GUI
        });
    }

    public void createAndShowGUI()
    {
        JFrame frame = new JFrame("Data Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        //load data
        List<DataItem> dataItems = loadData(frame);
        if (dataItems == null) return;//exit if loading failed

        //initialize GUI components
        initializeComponents(frame, dataItems);

        setupLayout(frame);

        //set frame properties
        frame.setSize(1500, 760);
        frame.setVisible(true);
    }

    //load data from file and returns it as a List of DataItems
    private List<DataItem> loadData(JFrame frame)
    {
        String filePath = "src/Experiment_404_mechanical_data.csv";//filepath
        try {
            List<DataItem> dataItems = DataLoader.loadData(filePath);//load data using DataLoader
            System.out.printf("Data loaded successfully from: %s%n", filePath);//print success message
            return dataItems;
        } catch (Exception e)
        {
            //show error dialog if data loading fails
            JOptionPane.showMessageDialog(frame, "Error loading data: %s".formatted(e.getMessage()),
                    "File Load Error", JOptionPane.ERROR_MESSAGE);
            return null;//return null if loading fails
        }
    }
    //initializes main components of GUI with data items
    private void initializeComponents(JFrame frame, List<DataItem> dataItems)
    {
        statsPanel= new StatsPanel();
        pieChart = new PieChart(dataItems);
        tablePanel = new TablePanel(dataItems, statsPanel, pieChart);
        filters = new Filters(dataItems, tablePanel);
        detailsPanel = new DetailsPanel();

        //update stats with initial data
        statsPanel.update(dataItems);

        //average, min, and max strategy
        StatStrategy averageStrategy = new AverageStatsStrategy();
        StatStrategy minStrategy = new MinStatsStrategy();
        StatStrategy maxStrategy = new MaxStatsStrategy();

        statsPanel.setStrategy(averageStrategy);
        statsPanel.update(dataItems);
        tablePanel.addObserver(statsPanel);
        tablePanel.addObserver(pieChart);

        //set up table selection listener
        tablePanel.getTable().getSelectionModel().addListSelectionListener(_ ->
        {
            int selectedRow = tablePanel.getTable().getSelectedRow();
            if (selectedRow != -1) //proceed if row is selected
            {
                int modelRow = tablePanel.getTable().convertRowIndexToModel(selectedRow);//convert row index to model index
                DataItem selectedItem = dataItems.get(modelRow);//get selected DataItem
                detailsPanel.showDetails(selectedItem);//show details of selected item in details panel
            }
        });
        //strategy selection dropdown (JComboBox)
        String[] strategies = {"Average", "Minimum", "Maximum"};
        JComboBox<String> strategyComboBox = new JComboBox<>(strategies);
        strategyComboBox.addActionListener(_ -> {
            String selectedStrategy = (String) strategyComboBox.getSelectedItem();
            //check if selectedStrategy is not null
            if (selectedStrategy != null) {
                switch (selectedStrategy) {
                    case "Average":
                        statsPanel.setStrategy(averageStrategy);
                        break;
                    case "Minimum":
                        statsPanel.setStrategy(minStrategy);
                        break;
                    case "Maximum":
                        statsPanel.setStrategy(maxStrategy);
                        break;
                }
                //updates stats after strategy change
                statsPanel.update(dataItems);
            }
        });

        //adds the JComboBox to the frame or panel
        JPanel controlPanel = new JPanel();
        controlPanel.add(new JLabel("Select Strategy:"));
        controlPanel.add(strategyComboBox);
        //adds to the bottom of the frame
        frame.add(controlPanel, BorderLayout.SOUTH);
    }
    //sets layout structures
    private void setupLayout(JFrame frame)
    {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());//use BorderLayout for vertical stacking
        leftPanel.add(filters, BorderLayout.NORTH);//place filters at the top
        leftPanel.add(tablePanel, BorderLayout.CENTER);//place table panel below filters

        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);//splits horizontally
        mainSplitPane.setLeftComponent(leftPanel);//set left panel with filters and table panel

        JSplitPane rightSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);//right split pane, divides vertically
        rightSplitPane.setTopComponent(detailsPanel);//set details at top of right split
        rightSplitPane.setBottomComponent(pieChart);//set pie chart at bottom of right split
        rightSplitPane.setDividerLocation(200);//divider location for right split pane

        mainSplitPane.setRightComponent(rightSplitPane);//set right split pane on right side of main split
        mainSplitPane.setDividerLocation(750);//set initial divider location for main split pane

        frame.add(statsPanel, BorderLayout.NORTH);//stats panel at top of frame
        frame.add(mainSplitPane, BorderLayout.CENTER);//main split pane in center of frame
    }
}