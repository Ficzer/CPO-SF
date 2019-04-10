package SF;

import java.util.*;

public class AnalogToDigitalConverter
{
    public Signal sample(final Signal signal, int newSampling)
    {
        Map<Double, Double> values = signal.getValues();
        Map<Double, Double> newValues = new HashMap<Double, Double>();
        Signal newSignal = new Signal();

        List<Double> valuesList = new ArrayList<>(values.values());
        List<Double> timeList = new ArrayList<>(values.keySet());

        int oldSampling = signal.getSampling();

        if(oldSampling % newSampling > 0.000001)
        {
            throw new IllegalArgumentException("Sampling frequency of base signal should be multiplication of probing frequency");
        }

        int pom = newSampling * (int)(signal.getDurationTime() / signal.getPeriod());
        int k = oldSampling / pom;

        for(int i=0; i< (((values.size() * 1.0) * pom / oldSampling) + 1) - 1; i++)
        {
            newValues.put(timeList.get(i * k), valuesList.get(i * k));
        }

        newValues.put(timeList.get(timeList.size()-1), valuesList.get(valuesList.size()-1));

        newSignal.setName(signal.getName() + "(Digital)");
        newSignal.setStartingTime(signal.getStartingTime());
        newSignal.setDurationTime(signal.getDurationTime());
        newSignal.setAmplitude(signal.getAmplitude());
        newSignal.setPeriod(signal.getPeriod());
        newSignal.setFulfillment(signal.getFulfillment());
        newSignal.setSampling(newSampling);
        newSignal.setValues(new TreeMap<Double, Double>(newValues));

        return newSignal;
    }

    public Signal quantize(final Signal signal, final int bits)
    {
        Map<Double, Double> values = signal.getValues();
        Map<Double, Double> newValues = new HashMap<Double, Double>();
        Signal newSignal = new Signal();

        List<Double> valuesList = new ArrayList<>(values.values());
        List<Double> timeList = new ArrayList<>(values.keySet());

        int levels = (int) (Math.pow(2, bits)) - 1;

        Double minValue = valuesList.get(0);

        for(Double value : valuesList)
        {
            if(value < minValue)
                minValue = value;
        }

        Double maxValue = valuesList.get(0);

        for(Double value : valuesList)
        {
            if(value > maxValue)
                maxValue = value;
        }

        Double span = (maxValue - minValue) / levels;

        for(int i=0; i<valuesList.size(); i++)
        {
            newValues.put(timeList.get(i), minValue + Math.floor((valuesList.get(i) - minValue) / span) * span);
        }

        newSignal.setName(signal.getName() + "(Quantized)");
        newSignal.setStartingTime(signal.getStartingTime());
        newSignal.setDurationTime(signal.getDurationTime());
        newSignal.setAmplitude(signal.getAmplitude());
        newSignal.setPeriod(signal.getPeriod());
        newSignal.setFulfillment(signal.getFulfillment());
        newSignal.setSampling(signal.getSampling());
        newSignal.setValues(new TreeMap<Double, Double>(newValues));

        return newSignal;
    }
}
