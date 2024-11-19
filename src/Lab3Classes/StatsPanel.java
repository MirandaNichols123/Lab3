import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StatsPanel extends JPanel implements TableObserver {
    //private final JLabel[] avgLabels;
    //private final JLabel[] minLabels;
    //private final JLabel[] maxLabels;
    private final JLabel[] statLabels;
    private StatStrategy strategy;


    //sets up the panel layout and initializes labels
    public StatsPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));//set vertical layout for panel
        //create a header label for the stats section
        JLabel headerLabel = new JLabel("Statistics", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 14));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        //create labels for averages and means
        String[] Texts = {
                "Friction: ", "Vertical Displacement: ", "Confining Pressure: ",
                "Displacement: ", "Normal Stress: ", "Shear Stress: ",
                "Time: ", "Velocity: "
        };
        //initialized labels for avg, min, and max stats
        //avgLabels = createLabels(Texts);
        //minLabels = createLabels(Texts);
        //maxLabels = createLabels(Texts);
        //add components to the panel
        //add(headerLabel); // Header at the top
        //add(createSection("Average", avgLabels));
        //add(createSection("Minimum", minLabels));
        //add(createSection("Maximum", maxLabels));
        statLabels = createLabels(Texts);
        add(headerLabel);
        add(createSection(statLabels));
    }

    //update statistics dynamically using the strategy pattern
    @Override
    public void update(List<DataItem> dataItems) {
        if (strategy != null) {
            double[] results = strategy.calculate(dataItems); // Use strategy to calculate stats
            updateLabels(statLabels, results); // Update the display with calculated values
        }
    }

//helper method to create jLabel array with default values
private JLabel[] createLabels(String[] texts)
{
    JLabel[] labels = new JLabel[texts.length];
    for (int i = 0; i < texts.length; i++) {
        labels[i] = new JLabel("%s0.00".formatted(texts[i]));//initialize with default value
    }
    return labels;
}
//create JPanel for each stats category(avg, min, max)
private JPanel createSection(JLabel[] labels)
{
    JPanel sectionPanel = new JPanel();
    sectionPanel.setLayout(new FlowLayout(FlowLayout.LEFT));//use FlowLayout for horizontal
    //create and add section title labels
    JLabel sectionLabel = new JLabel("%s: ".formatted("Statistics"), SwingConstants.LEFT);
    sectionLabel.setFont(new Font("Arial", Font.BOLD, 12));
    sectionPanel.add(sectionLabel);

    //add each label to section panel
    for (JLabel label : labels)
    {
        sectionPanel.add(label);
    }
    return sectionPanel;//returns section panel
}

// Helper method to update JLabel text with calculated values
private void updateLabels(JLabel[] labels, double[] values) {
    for (int i = 0; i < labels.length; i++) {
        // Update label with formatted text and values
        labels[i].setText(String.format("%s%.4f", "%s: ".formatted(labels[i].getText().split(":")[0]), values[i]));
    }
}

// Set the current strategy for statistics calculation
public void setStrategy(StatStrategy strategy) {
    this.strategy = strategy;
}
}
