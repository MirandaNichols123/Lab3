import javax.swing.*;
import java.awt.*;

public class DetailsPanel extends JPanel
{
    private final JTextArea detailsArea;
    public DetailsPanel()
    {
        setLayout(new BorderLayout());
        //create a header label for the detail section
        JLabel headerLabel = new JLabel("Details", SwingConstants.LEFT);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 14));//font size, style
        headerLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));//add padding
        add(headerLabel, BorderLayout.NORTH);//header at the top

        //initialize and configure the text area
        detailsArea = new JTextArea();
        detailsArea.setEditable(false);//disable editing
        detailsArea.setLineWrap(true);//enable word wrap
        detailsArea.setWrapStyleWord(true);//wrap on word boundaries
        detailsArea.setMargin(new Insets(10, 10, 10, 10));

        //add scroll for overflow
        add(new JScrollPane(detailsArea), BorderLayout.CENTER);
    }
    //method to display details of the selected dataItem
    public void showDetails(DataItem item)
    {
        if (item != null)
        {
            //text content for the details area
            detailsArea.setText(String.format(
                    """
                            Vertical Disp. (µm): %.4f
                            Confining Pressure (MPa): %.4f
                            Displacement (mm): %.4f
                            Friction Coefficient: %.4f
                            Normal Stress (MPa): %.4f
                            Shear Stress (MPa): %.4f
                            Time (s): %.4f
                            Velocity (mm/s): %.4f""",
                    item.verticalDispMicrons(),
                    item.confiningPressureMPa(),
                    item.displacementMM(),
                    item.coefficientFriction(),
                    item.normalStressMPa(),
                    item.shearStressMPa(),
                    item.timeS(),
                    item.velocityMMPerS()
            ));
        } else
        {
            //clear the details area if no item is selected or available
            detailsArea.setText("No data selected");
        }
    }
}
