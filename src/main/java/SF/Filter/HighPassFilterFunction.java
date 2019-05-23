package SF.Filter;

public class HighPassFilterFunction implements FilterFunction
{

    @Override
    public Double getValue(int i) {
        return Math.pow(-1, i);
    }

    @Override
    public int getK(Double samplingFrequency, Double frequency) {
        return (int) (samplingFrequency / frequency);
        //return (int) (samplingFrequency / (samplingFrequency / 2.0 - frequency));
    }
}
