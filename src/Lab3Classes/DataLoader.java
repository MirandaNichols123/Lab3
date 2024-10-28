import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataLoader
{
    public static List<DataItem> loadData(String filePath) throws IOException {
        List<DataItem> dataItems = new ArrayList<>();

        // Open the file
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Skip the header row
            reader.readLine(); // This reads and skips the first line (header)

            // Read each line of the CSV
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");

                // Parse the values from the CSV to create a DataItem
                double verticalDispMicrons = Double.parseDouble(values[0]);
                double confiningPressureMPa = Double.parseDouble(values[1]);
                double displacementMM = Double.parseDouble(values[2]);
                double coefficientFriction = Double.parseDouble(values[3]);
                double normalStressMPa = Double.parseDouble(values[4]);
                double shearStressMPa = Double.parseDouble(values[5]);
                double timeS = Double.parseDouble(values[6]);
                double velocityMMPerS = Double.parseDouble(values[7]);

                // Create a new DataItem with parsed values and add it to the list
                dataItems.add(new DataItem(verticalDispMicrons, confiningPressureMPa, displacementMM,
                        coefficientFriction, normalStressMPa, shearStressMPa, timeS, velocityMMPerS));
            }
        }

        return dataItems;
    }
    public static double calculateAverageFriction(List<DataItem> dataItems) {
        return dataItems.stream()
                .mapToDouble(DataItem::coefficientFriction) // This is correct
                .average()
                .orElse(0);
    }

    public static double calculateAverageVerticalDisp(List<DataItem> dataItems) {
        return dataItems.stream()
                .mapToDouble(DataItem::verticalDispMicrons) // Ensure you have this accessor in DataItem
                .average()
                .orElse(0);
    }

    public static double calculateAverageConfiningPressure(List<DataItem> dataItems) {
        return dataItems.stream()
                .mapToDouble(DataItem::confiningPressureMPa) // Ensure you have this accessor in DataItem
                .average()
                .orElse(0);
    }

    public static double calculateAverageDisplacement(List<DataItem> dataItems) {
        return dataItems.stream()
                .mapToDouble(DataItem::displacementMM) // Ensure you have this accessor in DataItem
                .average()
                .orElse(0);
    }

    public static double calculateAverageNormalStress(List<DataItem> dataItems) {
        return dataItems.stream()
                .mapToDouble(DataItem::normalStressMPa) // Ensure you have this accessor in DataItem
                .average()
                .orElse(0);
    }

    public static double calculateAverageShearStress(List<DataItem> dataItems) {
        return dataItems.stream()
                .mapToDouble(DataItem::shearStressMPa) // Ensure you have this accessor in DataItem
                .average()
                .orElse(0);
    }

    public static double calculateAverageTime(List<DataItem> dataItems) {
        return dataItems.stream()
                .mapToDouble(DataItem::timeS) // Ensure you have this accessor in DataItem
                .average()
                .orElse(0);
    }

    public static double calculateAverageVelocity(List<DataItem> dataItems) {
        return dataItems.stream()
                .mapToDouble(DataItem::velocityMMPerS) // Ensure you have this accessor in DataItem
                .average()
                .orElse(0);
    }

    public static double calcMeanFriction(List<DataItem> dataItems) {
        double totalFriction = dataItems.stream()
                .mapToDouble(DataItem::coefficientFriction) // This is correct
                .sum();
        return totalFriction / dataItems.size();
    }

    public static double calcStdDevFriction(List<DataItem> dataItems, double meanFriction) {
        if (dataItems.isEmpty()) {
            return 0;
        }
        // Calculate the variance
        double variance = dataItems.stream()
                .mapToDouble(item -> Math.pow(item.coefficientFriction() - meanFriction, 2)) // Make sure you call the method
                .sum() / dataItems.size();
        return Math.sqrt(variance);
    }

    public static double calcMinVerticalDisp(List<DataItem> dataItems) {
        return dataItems.stream()
                .mapToDouble(DataItem::verticalDispMicrons) // Ensure you have this accessor in DataItem
                .min()
                .orElse(0);
    }

    public static double calcMinConfiningPressure(List<DataItem> dataItems) {
        return dataItems.stream()
                .mapToDouble(DataItem::confiningPressureMPa) // Ensure you have this accessor in DataItem
                .min()
                .orElse(0);
    }

    public static double calcMinDisplacement(List<DataItem> dataItems) {
        return dataItems.stream()
                .mapToDouble(DataItem::displacementMM) // Ensure you have this accessor in DataItem
                .min()
                .orElse(0);
    }

    public static double calcMinNormalStress(List<DataItem> dataItems) {
        return dataItems.stream()
                .mapToDouble(DataItem::normalStressMPa) // Ensure you have this accessor in DataItem
                .min()
                .orElse(0);
    }

    public static double calcMinShearStress(List<DataItem> dataItems) {
        return dataItems.stream()
                .mapToDouble(DataItem::shearStressMPa) // Ensure you have this accessor in DataItem
                .min()
                .orElse(0);
    }

    public static double calcMinTime(List<DataItem> dataItems) {
        return dataItems.stream()
                .mapToDouble(DataItem::timeS) // Ensure you have this accessor in DataItem
                .min()
                .orElse(0);
    }

    public static double calcMinVelocity(List<DataItem> dataItems) {
        return dataItems.stream()
                .mapToDouble(DataItem::velocityMMPerS) // Ensure you have this accessor in DataItem
                .min()
                .orElse(0);
    }

    public static double calcMinFriction(List<DataItem> dataItems) {
        return dataItems.stream()
                .mapToDouble(DataItem::coefficientFriction) // This is correct
                .min()
                .orElse(0);
    }

    public static double calcMaxFriction(List<DataItem> dataItems) {
        return dataItems.stream()
                .mapToDouble(DataItem::coefficientFriction) // Ensure you have this accessor in DataItem
                .max()
                .orElse(0);
    }

    public static double calcMaxVerticalDisp(List<DataItem> dataItems) {
        return dataItems.stream()
                .mapToDouble(DataItem::verticalDispMicrons) // Ensure you have this accessor in DataItem
                .max()
                .orElse(0);
    }

    public static double calcMaxConfiningPressure(List<DataItem> dataItems) {
        return dataItems.stream()
                .mapToDouble(DataItem::confiningPressureMPa) // Ensure you have this accessor in DataItem
                .max()
                .orElse(0);
    }

    public static double calcMaxDisplacement(List<DataItem> dataItems) {
        return dataItems.stream()
                .mapToDouble(DataItem::displacementMM) // Ensure you have this accessor in DataItem
                .max()
                .orElse(0);
    }

    public static double calcMaxNormalStress(List<DataItem> dataItems) {
        return dataItems.stream()
                .mapToDouble(DataItem::normalStressMPa) // Ensure you have this accessor in DataItem
                .max()
                .orElse(0);
    }

    public static double calcMaxShearStress(List<DataItem> dataItems) {
        return dataItems.stream()
                .mapToDouble(DataItem::shearStressMPa) // Ensure you have this accessor in DataItem
                .max()
                .orElse(0);
    }

    public static double calcMaxTime(List<DataItem> dataItems) {
        return dataItems.stream()
                .mapToDouble(DataItem::timeS) // Ensure you have this accessor in DataItem
                .max()
                .orElse(0);
    }

    public static double calcMaxVelocity(List<DataItem> dataItems) {
        return dataItems.stream()
                .mapToDouble(DataItem::velocityMMPerS)
                .max()
                .orElse(0);
    }
}
