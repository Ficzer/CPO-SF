package SF.Filter;

public interface FilterFunction
{
    Double getValue(final int i);
    Double getK(final Double samplingFrequency, final Double frequency);
}
