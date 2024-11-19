import java.util.List;

public class MaxStatsStrategy implements StatStrategy {
    @Override
    public double[] calculate(List<DataItem> dataItems) {
        return new double[]
                {
                        DataStatistics.calcMaxFriction(dataItems),
                        DataStatistics.calcMaxVerticalDisp(dataItems),
                        DataStatistics.calcMaxConfiningPressure(dataItems),
                        DataStatistics.calcMaxDisplacement(dataItems),
                        DataStatistics.calcMaxNormalStress(dataItems),
                        DataStatistics.calcMaxShearStress(dataItems),
                        DataStatistics.calcMaxTime(dataItems),
                        DataStatistics.calcMaxVelocity(dataItems)
                };
    }
}
