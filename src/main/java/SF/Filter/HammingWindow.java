package SF.Filter;

public class HammingWindow implements WindowFunction
{

    @Override
    public Double getValue(int i, int M)
    {
        return 0.53836 - 0.46164 * Math.cos(2 * Math.PI * i / M);
    }
}
