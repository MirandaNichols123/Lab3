import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PieChart extends JPanel
{
    private final Map<String, Double> data;//data for the pie chart
    public PieChart(List<DataItem> dataItems)
    {
        this.data = calculateData(dataItems);//calc and store data based on DataItem list
        setLayout(new BorderLayout());
        //title for the pie chart
        JLabel headerLabel = new JLabel("Pie Chart", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(headerLabel, BorderLayout.NORTH);
    }

    private Map<String, Double> calculateData(List<DataItem> dataItems)
    {
        //aggregate data based on some criteria
        Map<String, Double> dataMap = new HashMap<>();
        //loop through DataItem list to categorize and sum values
        for (DataItem item : dataItems)
        {
            String category = item.coefficientFriction() > 0.5 ? "High Friction" : "Low Friction";
            //update category total
            dataMap.put(category, dataMap.getOrDefault(category, 0.0) + item.coefficientFriction());
        }
        return dataMap;//returns aggregated data map
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        drawPieChart(g);//draw pie chart
        drawLegend(g);//indicated category colors
    }
    private void drawPieChart(Graphics g)
    {
        int total = data.values().stream().mapToInt(Double::intValue).sum();//sum all values
        int startAngle = 0;//starting angle for pie slices
        int width = getWidth();
        int height = getHeight();
        int diameter = Math.min(width, height) - 75;//diameter of the pie chart
        int x = (width - diameter) / 2;//X position
        int y = (height - diameter) / 2;//Y position

        //loop through data entries to draw pie slice
        for (Map.Entry<String, Double> entry : data.entrySet())
        {
            String category = entry.getKey();//get name
            double value = entry.getValue();//get value

            //calculate angle
            int angle = (int) Math.round(value / total * 360);
            g.setColor(getColor(category));//set color based on category
            g.fillArc(x, y, diameter, diameter, startAngle, angle);//draw slice
            startAngle += angle;//move to the next slice
        }
    }
    //returns color associated with category
    private Color getColor(String category)
    {
        return switch (category)
        {
            case "High Friction" -> Color.RED;//high friction represented in red
            case "Low Friction" -> Color.BLUE;//low friction represented in blue
            default -> Color.GRAY;//default color
        };
    }
    //draws a legend below the pie chart
    private void drawLegend(Graphics g)
    {
        int legendX = 10;//starting X position for the legend
        int legendY = getHeight() - 100;//Y position for the legend
        int squareSize = 15;//size of the square for colors

        g.setColor(Color.BLACK);//color for the legend text
        int index = 0;//legend item index

        //loop through the data categories to draw the legend
        for (Map.Entry<String, Double> entry : data.entrySet())
        {
            g.setColor(getColor(entry.getKey()));//set color for the category
            g.fillRect(legendX, legendY + index * (squareSize + 5), squareSize, squareSize);//draw colored square

            g.setColor(Color.BLACK);//set color for text
            g.drawString(entry.getKey(), legendX + squareSize + 5, legendY + index * (squareSize + 5) + squareSize);//draw label
            index++;//move to next entry
        }
    }
    public void updateData(List<DataItem> dataItems)
    {
        this.data.clear();//clear existing data
        this.data.putAll(calculateData(dataItems));//recalculate data based on new input
        repaint();//repaint the chart to reflect the new data
    }
}
