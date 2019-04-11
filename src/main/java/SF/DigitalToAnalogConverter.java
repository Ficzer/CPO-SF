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

        Double oldTimeStep = signal.getPeriod() / signal.getSampling();
        Double newTimeStep = signal.getPeriod() / newSampling;
        Double time = signal.getStartingTime();

        int samplingPerWindow = newSampling * (int)(signal.getDurationTime() / signal.getPeriod());

        for(int i=0; i<samplingPerWindow; i++)
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

        Double oldTimeStep = signal.getPeriod() / signal.getSampling();
        Double newTimeStep = signal.getPeriod() / newSampling;
        Double time = 0.0;

        int samplingPerWindow = newSampling * (int)(signal.getDurationTime() / signal.getPeriod());
        maxProbes = Math.min(maxProbes, signal.getSampling());

        for(int i=0; i<samplingPerWindow; i++)
        {
            Double sum = 0.0;
            int k = (int) (Math.max(time / oldTimeStep - maxProbes, signal.getStartingTime() / oldTimeStep));
            k = (int) (Math.max(k, (time / oldTimeStep) - maxProbes / 2));
            k = (int) (Math.min(k, ((signal.getStartingTime() + signal.getDurationTime()) / oldTimeStep) - maxProbes));
            int maxK = k + maxProbes;

            while(k < maxK)
            {
                sum += valuesList.get(k) * sinc(time / oldTimeStep - k);
                k++;
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
