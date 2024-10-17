package Lab3Classes;

import javax.swing.*;
import java.awt.*;

public class DetailsPanel extends JPanel {
    private JTextArea detailsArea;

    public DetailsPanel() {
        setLayout(new BorderLayout());

        // Initialize and configure the text area
        detailsArea = new JTextArea();
        detailsArea.setEditable(false);  // Disable editing
        detailsArea.setLineWrap(true);   // Enable word wrap
        detailsArea.setWrapStyleWord(true);  // Wrap on word boundaries
        detailsArea.setMargin(new Insets(10, 10, 10, 10));  // Add padding

        // Add the text area to a scroll pane in case of overflow
        add(new JScrollPane(detailsArea), BorderLayout.CENTER);
    }

    // Method to display details of the selected DataItem
    public void showDetails(DataItem item) {
        if (item != null) {
            // Set the text content for the details area
            detailsArea.setText(
                    "Vertical Disp. (µm): " + item.getVerticalDispMicrons() + "\n\n" +
                            "Confining Pressure (MPa): " + item.getConfiningPressureMPa() + "\n\n" +
                            "Displacement (mm): " + item.getDisplacementMM() + "\n\n" +
                            "Friction Coefficient: " + item.getCoefficientFriction() + "\n\n" +
                            "Normal Stress (MPa): " + item.getNormalStressMPa() + "\n\n" +
                            "Shear Stress (MPa): " + item.getShearStressMPa() + "\n\n" +
                            "Time (s): " + item.getTimeS() + "\n\n" +
                            "Velocity (mm/s): " + item.getVelocityMMPerS() + "\n"
            );
        } else {
            // Clear the details area if no item is selected or available
            detailsArea.setText("No data available.");
        }
    }
}
