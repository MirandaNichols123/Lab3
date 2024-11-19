import java.util.List;

public class AverageStatsStrategy implements StatStrategy
{
    @Override
    public double[] calculate(List<DataItem> dataItems)
    {
        return new double[]
                {
                        DataStatistics.calculateAverageFriction(dataItems),
                        DataStatistics.calculateAverageVerticalDisp(dataItems),
                        DataStatistics.calculateAverageConfiningPressure(dataItems),
                        DataStatistics.calculateAverageDisplacement(dataItems),
                        DataStatistics.calculateAverageNormalStress(dataItems),
                        DataStatistics.calculateAverageShearStress(dataItems),
                        DataStatistics.calculateAverageTime(dataItems),
                        DataStatistics.calculateAverageVelocity(dataItems)
                };
    }
}
