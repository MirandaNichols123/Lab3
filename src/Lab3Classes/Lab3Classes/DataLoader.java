package Lab3Classes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {

    // This method reads data from a CSV file and returns a List of DataItem objects
    public static List<DataItem> loadData(String filePath) {
        List<DataItem> dataItems = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.readLine();
            String line;

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");

                double verticalDispMicrons = Double.parseDouble(fields[0]);
                double confiningPressureMPa = Double.parseDouble(fields[1]);
                double displacementMM = Double.parseDouble(fields[2]);
                double coefficientFriction = Double.parseDouble(fields[3]);
                double normalStressMPa = Double.parseDouble(fields[4]);
                double shearStressMPa = Double.parseDouble(fields[5]);
                double timeS = Double.parseDouble(fields[6]);
                double velocityMMPerS = Double.parseDouble(fields[7]);

                DataItem item = new DataItem(verticalDispMicrons, confiningPressureMPa, displacementMM,
                        coefficientFriction, normalStressMPa, shearStressMPa, timeS, velocityMMPerS);
                dataItems.add(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataItems;
    }
    public static double calculateAverageFriction(List<DataItem> dataItems) {
        return dataItems.stream()
                .mapToDouble(DataItem::getCoefficientFriction)
                .average()
                .orElse(0); // Handle empty case
    }
    public static void main(String[] args) {
        DataLoader csvReader = new DataLoader();
        String filePath = "src/Lab3Classes/Experiment_404_mechanical_data.csv";
        List<DataItem> dataItems = csvReader.loadData(filePath);

        // Print the data to verify
        for (DataItem item : dataItems) {
            System.out.println(item.getVerticalDispMicrons() + " " + item.getConfiningPressureMPa());
        }
    }
}
