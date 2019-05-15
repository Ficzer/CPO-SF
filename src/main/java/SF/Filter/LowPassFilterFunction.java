package SF.Filter;

public class LowPassFilterFunction implements FilterFunction
{

    @Override
    public Double getValue(int i) {
        return 1.0;
    }

    @Override
    public int getK(Double samplingFrequency, Double frequency)
    {
        return (int) (samplingFrequency / frequency); //200 25
    }
}
