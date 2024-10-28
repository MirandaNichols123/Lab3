public record DataItem(double verticalDispMicrons, double confiningPressureMPa, double displacementMM,
                       double coefficientFriction, double normalStressMPa, double shearStressMPa, double timeS,
                       double velocityMMPerS)
{

    public String toString()
    {
        return "DataItem " +
                "verticalDispMicrons=" + verticalDispMicrons +
                ", confiningPressureMPa=" + confiningPressureMPa +
                ", displacementMm=" + displacementMM +
                ", coefficientFriction=" + coefficientFriction +
                ", normalStressMPa=" + normalStressMPa +
                ", shearStressMPa=" + shearStressMPa +
                ", timeSeconds=" + timeS +
                ", velocityMmPerS=" + velocityMMPerS +
                '}';
    }
    public double getCoefficientFriction() {
        return coefficientFriction;
    }


}
