import javax.swing.*;
import java.awt.*;

public class DetailsPanel extends JPanel
{
    private final JTextArea detailsArea;

    public DetailsPanel()
    {
        setLayout(new BorderLayout());

        // Create a header label for the detail section
        JLabel headerLabel = new JLabel("Details", SwingConstants.LEFT);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 14));//font size, style
        headerLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));//add some padding
        add(headerLabel, BorderLayout.NORTH);//header at the top


        //initialize and configure the text area
        detailsArea = new JTextArea();
        detailsArea.setEditable(false);//disable editing
        detailsArea.setLineWrap(true);//enable word wrap
        detailsArea.setWrapStyleWord(true);//wrap on word boundaries
        detailsArea.setMargin(new Insets(10, 10, 10, 10));//add padding

        //add the text area to a scroll pane in case of overflow
        add(new JScrollPane(detailsArea), BorderLayout.CENTER);
    }

    //method to display details of the selected dataItem
    public void showDetails(DataItem item)
    {
        if (item != null)
        {
            //set the text content for the details area
            detailsArea.setText(
                    "Vertical Disp. (µm): " + item.verticalDispMicrons() + "\n" +
                            "Confining Pressure (MPa): " + item.confiningPressureMPa() + "\n" +
                            "Displacement (mm): " + item.displacementMM() + "\n" +
                            "Friction Coefficient: " + item.coefficientFriction() + "\n" +
                            "Normal Stress (MPa): " + item.normalStressMPa() + "\n" +
                            "Shear Stress (MPa): " + item.shearStressMPa() + "\n" +
                            "Time (s): " + item.timeS() + "\n" +
                            "Velocity (mm/s): " + item.velocityMMPerS()
            );
        } else
        {
            //clear the details area if no item is selected or available
            detailsArea.setText("No data available.");
        }
    }
}
