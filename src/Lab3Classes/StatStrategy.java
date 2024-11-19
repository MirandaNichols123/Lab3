//use of strategy pattern for mathematics equations, uses drop down menu to pick

import java.util.List;

public interface StatStrategy
{
    double[] calculate(List<DataItem> dataItems);
}
