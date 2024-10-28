import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class Filters extends JPanel
{
    private final JCheckBox filter1;
    private final JCheckBox filter2;
    private final JCheckBox filter3;
    private final List<DataItem> allItems;
    private final TablePanel tablePanel;

    public Filters(List<DataItem> allItems, TablePanel tablePanel)
    {
        //store references
        this.allItems = allItems;
        this.tablePanel = tablePanel;

        setLayout(new BorderLayout());
        //text fields for JCheckBox
        filter1 = new JCheckBox("Display Friction higher than 0.5");
        filter2 = new JCheckBox("Display pressure less than 25");
        filter3 = new JCheckBox("Display within 1 std of mean Stress");
        //add GUI filters
        JPanel filterPanel = new JPanel();
        filterPanel.add(filter1);
        filterPanel.add(filter2);
        filterPanel.add(filter3);

        add(filterPanel, BorderLayout.NORTH);

        //action listeners
        filter1.addActionListener(_ -> updateTableWithFilters());
        filter2.addActionListener(_ -> updateTableWithFilters());
        filter3.addActionListener(_ -> updateTableWithFilters());
    }
    //updates stats and chart with filters
    private void updateTableWithFilters()
    {
        List<DataItem> filteredItems = applyFilters();
        tablePanel.updateTable(filteredItems);
    }
    //filters with streams
    private List<DataItem> applyFilters()
    {
        List<DataItem> filteredItems = allItems;
        if (filter1.isSelected())
        {
            filteredItems = filteredItems.stream()//filters data through streams
                    .filter(item -> item.coefficientFriction() > 0.5)//filters to find coefficientFriction is > than 0.5
                    .collect(Collectors.toList());//collect data
        }
        if (filter2.isSelected())
        {
            filteredItems = filteredItems.stream()
                    .filter(item -> item.confiningPressureMPa() < 25)
                    .collect(Collectors.toList());
        }
        if (filter3.isSelected())
        {
            double meanStress = DataStatistics.calcMeanStress(allItems);//gets mean of stress
            double stdDevStress = DataStatistics.calcStdDevStress(allItems, meanStress);//gets stdDev of stress
            filteredItems = filteredItems.stream()
                    .filter(item -> Math.abs(item.shearStressMPa() - meanStress) <= stdDevStress)//uses math.abs to make sure there is a positive value.
                    .collect(Collectors.toList());
        }

        return filteredItems;
    }
}
