import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataLoader
{
    //load CSV file and returns list of DataItem
    public static List<DataItem> loadData(String filePath) throws IOException
    {
        List<DataItem> dataItems = new ArrayList<>();
        //open the file
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath)))
        {
            String line;
            //skip the header row
            reader.readLine();
            //read each line of the CSV
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");

                //parse the values from the CSV to create a DataItem
                double verticalDispMicrons = Double.parseDouble(values[0]);
                double confiningPressureMPa = Double.parseDouble(values[1]);
                double displacementMM = Double.parseDouble(values[2]);
                double coefficientFriction = Double.parseDouble(values[3]);
                double normalStressMPa = Double.parseDouble(values[4]);
                double shearStressMPa = Double.parseDouble(values[5]);
                double timeS = Double.parseDouble(values[6]);
                double velocityMMPerS = Double.parseDouble(values[7]);

                //create a new DataItem with parsed values and add it to the list
                dataItems.add(new DataItem(verticalDispMicrons, confiningPressureMPa, displacementMM,
                        coefficientFriction, normalStressMPa, shearStressMPa, timeS, velocityMMPerS));
            }
        }
        return dataItems;
    }
}
