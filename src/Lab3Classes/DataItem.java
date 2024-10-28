//dataItem is a record that holds various measurements related to an experiment or dataset
public record DataItem(double verticalDispMicrons, double confiningPressureMPa, double displacementMM,
                       double coefficientFriction, double normalStressMPa, double shearStressMPa, double timeS,
                       double velocityMMPerS)
{
    //display DataItem information in logs or console output
    public String toString()
    {
        return ("DataItem verticalDispMicrons=%s, confiningPressureMPa=%s, displacementMm=%s, " +
                "coefficientFriction=%s, normalStressMPa=%s, shearStressMPa=%s, timeSeconds=%s, " +
                "velocityMmPerS=%s}").formatted(verticalDispMicrons, confiningPressureMPa, displacementMM,
                coefficientFriction, normalStressMPa, shearStressMPa, timeS, velocityMMPerS);
    }
}
