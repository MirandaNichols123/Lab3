import java.util.List;

public class MinStatsStrategy implements StatStrategy {
    @Override
    public double[] calculate(List<DataItem> dataItems) {
        return new double[]
                {
                DataStatistics.calcMinFriction(dataItems),
                DataStatistics.calcMinVerticalDisp(dataItems),
                DataStatistics.calcMinConfiningPressure(dataItems),
                DataStatistics.calcMinDisplacement(dataItems),
                DataStatistics.calcMinNormalStress(dataItems),
                DataStatistics.calcMinShearStress(dataItems),
                DataStatistics.calcMinTime(dataItems),
                DataStatistics.calcMinVelocity(dataItems)
        };
    }
}
