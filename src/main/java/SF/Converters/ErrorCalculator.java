package SF.Converters;

import SF.Signal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ErrorCalculator
{
    public Double calculateMSE(final Signal signal, final Signal originalSignal)
    {
        if(Math.abs(signal.getSampling() - originalSignal.getSampling()) > 0.000001)
        {
            throw new IllegalArgumentException("Signals samplings must be the same !!!");
        }

        Map<Double, Double> values = signal.getValues();
        Map<Double, Double> originalValues = originalSignal.getValues();
        List<Double> valuesList = new ArrayList<>(values.values());
        List<Double> originalValuesList = new ArrayList<>(originalValues.values());

        int len = Math.min(signal.getValues().size(), originalSignal.getValues().size());

        Double sum = 0.0;
        Double a = 0.0;

        for(int i=0; i<len; i++)
        {
            a = Math.pow(valuesList.get(i) - originalValuesList.get(i), 2.0);
            sum = sum + a;
        }

        Double result = sum/(len * 1.0);

        return result;
    }

    public Double calculateSNR(final Signal signal, final Signal originalSignal)
    {
        if(Math.abs(signal.getSampling() - originalSignal.getSampling()) > 0.000001)
        {
            throw new IllegalArgumentException("Signals samplings must be the same !!!");
        }

        Map<Double, Double> values = signal.getValues();
        Map<Double, Double> originalValues = originalSignal.getValues();
        List<Double> valuesList = new ArrayList<>(values.values());
        List<Double> originalValuesList = new ArrayList<>(originalValues.values());

        int len = Math.min(signal.getValues().size(), originalSignal.getValues().size());

        Double squaredSum = 0.0;
        Double noiseSquaredSum = 0.0;

        for(int i=0; i<len; i++)
        {
            squaredSum = squaredSum + valuesList.get(i) * valuesList.get(i);
            noiseSquaredSum = noiseSquaredSum + Math.pow(Math.abs(valuesList.get(i) - originalValuesList.get(i)), 2.0);
        }

        Double result = 10.0 * Math.log10(squaredSum/noiseSquaredSum);

        return result;
    }

    public Double calculatePSNR(final Signal signal, final Signal originalSignal)
    {
        if(Math.abs(signal.getSampling() - originalSignal.getSampling()) > 0.000001)
        {
            throw new IllegalArgumentException("Signals samplings must be the same !!!");
        }

        Map<Double, Double> values = signal.getValues();
        List<Double> valuesList = new ArrayList<>(values.values());

        Double maxValue = valuesList.get(0);

        for(int i=1; i<valuesList.size(); i++)
        {
            if(valuesList.get(i) > maxValue)
            {
                maxValue = valuesList.get(i);
            }
        }

        Double result = 10.0 * Math.log10(maxValue/calculateMSE(signal, originalSignal));

        return result;
    }

    public Double calculateMD(final Signal signal, final Signal originalSignal)
    {
        if(Math.abs(signal.getSampling() - originalSignal.getSampling()) > 0.000001)
        {
            throw new IllegalArgumentException("Signals samplings must be the same !!!");
        }

        Map<Double, Double> values = signal.getValues();
        Map<Double, Double> originalValues = originalSignal.getValues();
        List<Double> valuesList = new ArrayList<>(values.values());
        List<Double> originalValuesList = new ArrayList<>(originalValues.values());

        Double diff = Math.abs(valuesList.get(0) - originalValuesList.get(0));

        for(int i = 1; i<valuesList.size(); i++)
        {
            if(Math.abs(valuesList.get(i) - originalValuesList.get(i)) > diff)
            {
                diff = Math.abs(valuesList.get(i) - originalValuesList.get(i));
            }
        }

        return diff;

    }

    public Double calculateENOB(Double snr)
    {
        return (snr - 1.76)/6.02;
    }
}
