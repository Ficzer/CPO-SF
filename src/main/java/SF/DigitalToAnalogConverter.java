package SF;

import java.util.*;

public class DigitalToAnalogConverter
{
    public Signal interpolate(final Signal signal, int newSampling)
    {
        Map<Double, Double> values = signal.getValues();
        Map<Double, Double> newValues = new HashMap<Double, Double>();
        Signal newSignal = new Signal();

        List<Double> valuesList = new ArrayList<>(values.values());
        List<Double> timeList = new ArrayList<>(values.keySet());

        Double oldTimeStep = signal.getDurationTime() / signal.getSampling();
        Double newTimeStep = signal.getDurationTime() / newSampling;
        Double time = signal.getStartingTime();

        for(int i=0; i<newSampling; i++)
        {
            int j = (int) ((time - signal.getStartingTime()) / oldTimeStep); // where is between old samplings
            if(j == valuesList.size()-1)
            {
                --j;
            }

            Double a = (valuesList.get(j+1) - valuesList.get(j)) / oldTimeStep;
            Double b = valuesList.get(j+1) - a * ((j+1) * 1.0 * oldTimeStep);
            newValues.put(time, a * time + b);
            time += newTimeStep;
        }

        newSignal.setName(signal.getName() + "(Interpolate)");
        newSignal.setStartingTime(signal.getStartingTime());
        newSignal.setDurationTime(signal.getDurationTime());
        newSignal.setAmplitude(signal.getAmplitude());
        newSignal.setPeriod(signal.getPeriod());
        newSignal.setFulfillment(signal.getFulfillment());
        newSignal.setSampling(newSampling);
        newSignal.setValues(new TreeMap<Double, Double>(newValues));

        return newSignal;
    }

    public Signal reconstructWithSinc(final Signal signal, int newSampling, int maxProbes)
    {
        Map<Double, Double> values = signal.getValues();
        Map<Double, Double> newValues = new HashMap<Double, Double>();
        Signal newSignal = new Signal();

        List<Double> valuesList = new ArrayList<>(values.values());
        List<Double> timeList = new ArrayList<>(values.keySet());

        Double oldTimeStep = signal.getDurationTime() / signal.getSampling();
        Double newTimeStep = signal.getDurationTime() / newSampling;
        Double time = 0.0;

        maxProbes = Math.min(maxProbes, signal.getSampling());

        for(int i=0; i<newSampling; i++)
        {
            Double sum = 0.0;
            /*int k = (int) (Math.max(time / oldTimeStep - maxProbes, signal.getStartingTime() / oldTimeStep));
            k = (int) (Math.max(k, (time / oldTimeStep) - maxProbes / 2));
            k = (int) (Math.min(k, ((signal.getStartingTime() + signal.getDurationTime()) / oldTimeStep) - maxProbes));*/

            int k = (int)(time/oldTimeStep);
            k = k - maxProbes + 1;
            int n = k;

            if(k < 0)
            {
                k = 0;
            }

            int maxK = n + 2 * maxProbes - 1;

            if(maxK >= valuesList.size())
            {
                maxK = valuesList.size() - 1;
            }

            while(k < maxK)
            {
                sum += valuesList.get(k) * sinc(time / oldTimeStep - k);
                k++;
                double a = sinc(time / oldTimeStep - k);
            }

            newValues.put(time, sum);
            time += newTimeStep;
        }

        newSignal.setName(signal.getName() + "(Sinc)");
        newSignal.setStartingTime(signal.getStartingTime());
        newSignal.setDurationTime(signal.getDurationTime());
        newSignal.setAmplitude(signal.getAmplitude());
        newSignal.setPeriod(signal.getPeriod());
        newSignal.setFulfillment(signal.getFulfillment());
        newSignal.setSampling(newSampling);
        newSignal.setValues(new TreeMap<Double, Double>(newValues));

        return newSignal;
    }


    private double sinc(double x) {
        if (Math.abs(x) < 0.0000001) {
            return 1.0;
        } else {
            return (Math.sin(Math.PI * x) / (Math.PI * x));
        }
    }
}
