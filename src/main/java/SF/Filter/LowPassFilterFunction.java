package SF.Filter;

public class LowPassFilterFunction implements FilterFunction
{

    @Override
    public Double getValue(int i) {
        return 1.0;
    }

    @Override
    public Double getK(Double samplingFrequency, Double frequency)
    {
        return (samplingFrequency / frequency); //200 25
    }
}
