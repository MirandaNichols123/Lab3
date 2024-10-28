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
    public static double calculateAverageFriction(List<DataItem> dataItems)
    {
        //create stream from list of DataItem objects
        return dataItems.stream()
                //mapper function to each DataItem to get a stream of doubles
                .mapToDouble(DataItem::coefficientFriction) // This is correct
                .average()//calc avg of double values in stream
                //return avg value, or 0 if stream is empty
                .orElse(0);
    }
    public static double calculateAverageVerticalDisp(List<DataItem> dataItems)
    {
        return dataItems.stream()
                .mapToDouble(DataItem::verticalDispMicrons) // Ensure you have this accessor in DataItem
                .average()
                .orElse(0);
    }
    public static double calculateAverageConfiningPressure(List<DataItem> dataItems)
    {
        return dataItems.stream()
                .mapToDouble(DataItem::confiningPressureMPa)
                .average()
                .orElse(0);
    }
    public static double calculateAverageDisplacement(List<DataItem> dataItems)
    {
        return dataItems.stream()
                .mapToDouble(DataItem::displacementMM)
                .average()
                .orElse(0);
    }

    public static double calculateAverageNormalStress(List<DataItem> dataItems)
    {
        return dataItems.stream()
                .mapToDouble(DataItem::normalStressMPa)
                .average()
                .orElse(0);
    }

    public static double calculateAverageShearStress(List<DataItem> dataItems)
    {
        return dataItems.stream()
                .mapToDouble(DataItem::shearStressMPa)
                .average()
                .orElse(0);
    }
    public static double calculateAverageTime(List<DataItem> dataItems)
    {
        return dataItems.stream()
                .mapToDouble(DataItem::timeS)
                .average()
                .orElse(0);
    }
    public static double calculateAverageVelocity(List<DataItem> dataItems)
    {
        return dataItems.stream()
                .mapToDouble(DataItem::velocityMMPerS)
                .average()
                .orElse(0);
    }
    public static double calcMeanStress(List<DataItem> dataItems)
    {
        double totalStress = dataItems.stream()
                .mapToDouble(DataItem::shearStressMPa)
                .sum();//find sum
        return totalStress / dataItems.size();//divides to find mean
    }
    public static double calcStdDevStress(List<DataItem> dataItems, double meanStress)
    {
        //calculate the variance
        double variance = dataItems.stream()
                //maps double to find std
                .mapToDouble(item -> Math.pow(item.shearStressMPa() - meanStress, 2))
                .sum() / dataItems.size();
        return Math.sqrt(variance);
    }
    public static double calcMinVerticalDisp(List<DataItem> dataItems)
    {
        return dataItems.stream()
                .mapToDouble(DataItem::verticalDispMicrons)
                .min()//find minimum
                .orElse(0);
    }
    public static double calcMinConfiningPressure(List<DataItem> dataItems)
    {
        return dataItems.stream()
                .mapToDouble(DataItem::confiningPressureMPa)
                .min()
                .orElse(0);
    }
    public static double calcMinDisplacement(List<DataItem> dataItems)
    {
        return dataItems.stream()
                .mapToDouble(DataItem::displacementMM)
                .min()
                .orElse(0);
    }
    public static double calcMinNormalStress(List<DataItem> dataItems)
    {
        return dataItems.stream()
                .mapToDouble(DataItem::normalStressMPa)
                .min()
                .orElse(0);
    }
    public static double calcMinShearStress(List<DataItem> dataItems)
    {
        return dataItems.stream()
                .mapToDouble(DataItem::shearStressMPa)
                .min()
                .orElse(0);
    }
    public static double calcMinTime(List<DataItem> dataItems)
    {
        return dataItems.stream()
                .mapToDouble(DataItem::timeS)
                .min()
                .orElse(0);
    }
    public static double calcMinVelocity(List<DataItem> dataItems)
    {
        return dataItems.stream()
                .mapToDouble(DataItem::velocityMMPerS)
                .min()
                .orElse(0);
    }
    public static double calcMinFriction(List<DataItem> dataItems)
    {
        return dataItems.stream()
                .mapToDouble(DataItem::coefficientFriction)
                .min()
                .orElse(0);
    }
    public static double calcMaxFriction(List<DataItem> dataItems)
    {
        return dataItems.stream()
                .mapToDouble(DataItem::coefficientFriction)
                .max()//finds max
                .orElse(0);
    }
    public static double calcMaxVerticalDisp(List<DataItem> dataItems)
    {
        return dataItems.stream()
                .mapToDouble(DataItem::verticalDispMicrons)
                .max()
                .orElse(0);
    }
    public static double calcMaxConfiningPressure(List<DataItem> dataItems)
    {
        return dataItems.stream()
                .mapToDouble(DataItem::confiningPressureMPa)
                .max()
                .orElse(0);
    }
    public static double calcMaxDisplacement(List<DataItem> dataItems)
    {
        return dataItems.stream()
                .mapToDouble(DataItem::displacementMM)
                .max()
                .orElse(0);
    }
    public static double calcMaxNormalStress(List<DataItem> dataItems)
    {
        return dataItems.stream()
                .mapToDouble(DataItem::normalStressMPa)
                .max()
                .orElse(0);
    }

    public static double calcMaxShearStress(List<DataItem> dataItems)
    {
        return dataItems.stream()
                .mapToDouble(DataItem::shearStressMPa)
                .max()
                .orElse(0);
    }

    public static double calcMaxTime(List<DataItem> dataItems)
    {
        return dataItems.stream()
                .mapToDouble(DataItem::timeS)
                .max()
                .orElse(0);
    }
    //calculates max velocity for statsPanel using stream
    public static double calcMaxVelocity(List<DataItem> dataItems)
    {
        return dataItems.stream()
                .mapToDouble(DataItem::velocityMMPerS)
                .max()
                .orElse(0);
    }
}
