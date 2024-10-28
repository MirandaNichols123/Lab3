
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PieChart extends JPanel {
    private final Map<String, Double> data; // Data for the pie chart

    public PieChart(List<DataItem> dataItems, String title) {
        this.data = calculateData(dataItems);
        // Title for the pie chart
        setLayout(new BorderLayout()); // Set layout to BorderLayout
        add(new JLabel(title, SwingConstants.CENTER), BorderLayout.NORTH); // Add title label at the top
    }

    private Map<String, Double> calculateData(List<DataItem> dataItems) {
        Map<String, Double> dataMap = new HashMap<>();

        // Aggregate data based on some criteria; this example uses friction coefficients.
        for (DataItem item : dataItems) {
            String category = item.coefficientFriction() > 0.5 ? "High Friction" : "Low Friction";
            dataMap.put(category, dataMap.getOrDefault(category, 0.0) + item.coefficientFriction());
        }
        return dataMap;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawPieChart(g);
        drawLegend(g);
    }

    private void drawPieChart(Graphics g) {
        int total = data.values().stream().mapToInt(Double::intValue).sum(); // Sum all values
        int startAngle = 0; // Starting angle for pie slices
        int width = getWidth();
        int height = getHeight();
        int diameter = Math.min(width, height) - 75; // Diameter of the pie chart
        int x = (width - diameter) / 2; // X position
        int y = (height - diameter) / 2; // Y position

        for (Map.Entry<String, Double> entry : data.entrySet()) {
            String category = entry.getKey();
            double value = entry.getValue();

            // Calculate angle
            int angle = (int) Math.round(value / total * 360);
            g.setColor(getColor(category)); // Set color based on category
            g.fillArc(x, y, diameter, diameter, startAngle, angle); // Draw slice
            startAngle += angle; // Move to the next slice
        }
    }

    private Color getColor(String category) {
        switch (category) {
            case "High Friction": return Color.RED;
            case "Low Friction": return Color.BLUE;
            default: return Color.GRAY; // Default color
        }
    }
    // Draws a legend below the pie chart
    private void drawLegend(Graphics g) {
        int legendX = 10; // Starting X position for the legend
        int legendY = getHeight() - 100; // Y position for the legend
        int squareSize = 15; // Size of the square for colors

        g.setColor(Color.BLACK); // Color for the legend text
        int index = 0; // Legend item index

        // Iterate through the data categories to draw the legend
        for (Map.Entry<String, Double> entry : data.entrySet()) {
            g.setColor(getColor(entry.getKey())); // Set color for the category
            g.fillRect(legendX, legendY + index * (squareSize + 5), squareSize, squareSize); // Draw colored square

            g.setColor(Color.BLACK); // Set color for text
            g.drawString(entry.getKey(), legendX + squareSize + 5, legendY + index * (squareSize + 5) + squareSize); // Draw label
            index++;
        }
    }
    public void updateData(List<DataItem> dataItems) {
        this.data.clear(); // Clear existing data
        this.data.putAll(calculateData(dataItems)); // Recalculate data based on new input
        repaint(); // Repaint the chart to reflect the new data
    }
}
