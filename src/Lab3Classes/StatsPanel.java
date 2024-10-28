import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StatsPanel extends JPanel {
    private final JLabel[] avgLabels;
    private final JLabel[] minLabels;
    private final JLabel[] maxLabels;

    public StatsPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Set layout for this panel

        // Create a header label for the stats section
        JLabel headerLabel = new JLabel("Statistics", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Create labels for averages and means
        String[] Texts = {
                "Friction: ", "Vertical Displacement: ", "Confining Pressure: ",
                "Displacement: ", "Normal Stress: ", "Shear Stress: ",
                "Time: ", "Velocity: "
        };
        avgLabels = createLabels(Texts);
        minLabels = createLabels(Texts);
        maxLabels = createLabels(Texts);

        // Add components to the panel
        add(headerLabel); // Header at the top
        add(createSection("Average", avgLabels));
        add(createSection("Minimum", minLabels));
        add(createSection("Maximum", maxLabels));
    }

    private JLabel[] createLabels(String[] texts) {
        JLabel[] labels = new JLabel[texts.length];
        for (int i = 0; i < texts.length; i++) {
            labels[i] = new JLabel(texts[i] + "0.00"); // Initialize with default value
        }
        return labels;
    }

    private JPanel createSection(String title, JLabel[] labels) {
        JPanel sectionPanel = new JPanel();
        sectionPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Use FlowLayout for horizontal arrangement
        JLabel sectionLabel = new JLabel(title + ": ", SwingConstants.LEFT);
        sectionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        sectionPanel.add(sectionLabel);
        for (JLabel label : labels) {
            sectionPanel.add(label);
        }
        return sectionPanel;
    }

    // Method to update stats in the panel
    public void updateStats(List<DataItem> dataItems) {
        // Update averages
        updateLabels(avgLabels, new double[]{
                DataLoader.calculateAverageFriction(dataItems),
                DataLoader.calculateAverageVerticalDisp(dataItems),
                DataLoader.calculateAverageConfiningPressure(dataItems),
                DataLoader.calculateAverageDisplacement(dataItems),
                DataLoader.calculateAverageNormalStress(dataItems),
                DataLoader.calculateAverageShearStress(dataItems),
                DataLoader.calculateAverageTime(dataItems),
                DataLoader.calculateAverageVelocity(dataItems)
        });

        // Update means
        updateLabels(minLabels, new double[]{
                DataLoader.calcMinFriction(dataItems),
                DataLoader.calcMinVerticalDisp(dataItems),
                DataLoader.calcMinConfiningPressure(dataItems),
                DataLoader.calcMinDisplacement(dataItems),
                DataLoader.calcMinNormalStress(dataItems),
                DataLoader.calcMinShearStress(dataItems),
                DataLoader.calcMinTime(dataItems),
                DataLoader.calcMinVelocity(dataItems)
        });
        // Update means
        updateLabels(maxLabels, new double[]{
                DataLoader.calcMaxFriction(dataItems),
                DataLoader.calcMaxVerticalDisp(dataItems),
                DataLoader.calcMaxConfiningPressure(dataItems),
                DataLoader.calcMaxDisplacement(dataItems),
                DataLoader.calcMaxNormalStress(dataItems),
                DataLoader.calcMaxShearStress(dataItems),
                DataLoader.calcMaxTime(dataItems),
                DataLoader.calcMaxVelocity(dataItems)
        });
    }

    private void updateLabels(JLabel[] labels, double[] values) {
        for (int i = 0; i < labels.length; i++) {
            labels[i].setText(String.format("%s%.4f", labels[i].getText().split(":")[0] + ": ", values[i]));
        }
    }
}
