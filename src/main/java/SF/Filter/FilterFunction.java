package SF.Filter;

public interface FilterFunction
{
    Double getValue(final int i);
    int getK(final Double samplingFrequency, final Double frequency);
}
