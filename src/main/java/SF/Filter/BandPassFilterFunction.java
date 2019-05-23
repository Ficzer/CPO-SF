package SF.Filter;

public class BandPassFilterFunction implements FilterFunction
{

    @Override
    public Double getValue(int i) {
        return 2.0 * Math.sin(Math.PI * i / 2.0);
    }

    @Override
    public int getK(Double samplingFrequency, Double frequency) {
        return (int) (samplingFrequency / frequency);
        //return (int) ((4 * samplingFrequency) / (samplingFrequency - 4 * frequency));
    }
}
