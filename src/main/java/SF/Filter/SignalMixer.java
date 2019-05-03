package SF.Filter;

import SF.Signal;

import java.util.*;

public class SignalMixer
{
    public Signal convolution(Signal signal1, Signal signal2)
    {
        Double samplingFrequency1 = signal1.getSampling()/signal1.getDurationTime();
        Double samplingFrequency2 = signal2.getSampling()/signal2.getDurationTime();

        if (samplingFrequency1 - samplingFrequency2 > 0.000001)
        {
            throw new IllegalArgumentException("Signals should have same sampling frequency");
        }

        ArrayList<Double> values1 = (ArrayList<Double>) signal1.getValues().values();
        ArrayList<Double> values2 = (ArrayList<Double>) signal2.getValues().values();
        Map<Double, Double> newValues = new HashMap<>();
        int newSize = values1.size() + values2.size() - 1;
        Double newDurationTime = signal1.getStartingTime() + 1.0 / samplingFrequency1 * newSize;

        for(int i=0; i<newSize; i++)
        {
            Double tempVal = 0.0;
            for(int k=0; k<values1.size(); k++)
            {
                if(i-k >= 0 && i-k < values2.size())
                {
                    tempVal += values1.get(k) * values2.get(i - k);
                }
            }
            newValues.put(i * samplingFrequency1 / newDurationTime, tempVal);
        }

        signal1.setValues(new TreeMap<>(newValues));
        signal1.setDurationTime(newDurationTime);

        return signal1;
    }


    /// To DO
    public Signal correlation(Signal signal1, Signal signal2)
    {
       /* Double samplingFrequency1 = signal1.getSampling()/signal1.getDurationTime();
        Double samplingFrequency2 = signal2.getSampling()/signal2.getDurationTime();

        if (samplingFrequency1 - samplingFrequency2 > 0.000001)
        {
            throw new IllegalArgumentException("Signals should have same sampling frequency");
        }

        ArrayList<Double> values1 = (ArrayList<Double>) signal1.getValues().values();
        ArrayList<Double> values2 = (ArrayList<Double>) signal2.getValues().values();
        Map<Double, Double> newValues = new HashMap<>();
        int newSize = values1.size() + values2.size() - 1;
        Double newDurationTime = signal1.getStartingTime() + 1.0 / samplingFrequency1 * newSize;

        for(int i= -newSize/2; i < newSize/2; i++)
        {
            Double tempVal = 0.0;
            for(int k=0; k<values1.size(); k++)
            {
                if(i-k >= 0 && i-k < values2.size())
                {
                    tempVal += values1.get(k) * values2.get(i - k);
                }
            }
            newValues.put(i * samplingFrequency1 / newDurationTime, tempVal);
        }

        signal1.setValues(new TreeMap<>(newValues));
        signal1.setDurationTime(newDurationTime);
*/
        return signal1;
    }
}
