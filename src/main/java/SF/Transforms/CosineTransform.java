package SF.Transforms;

import SF.Signal;

import java.util.*;

public class CosineTransform implements Transform {
    @Override
    public Signal transform(Signal signal) {

        List<Double> values = new ArrayList<>(signal.getValues().values());
        List<Double> timeValues = new ArrayList<>(signal.getValues().keySet());
        Map<Double, Double> newValues = new HashMap<>();
        int N = values.size();

        for(int m=0; m<N; m++)
        {
            Double tempVal = 0.0;
            for(int n=0; n<N; n++)
            {
                tempVal += values.get(n) * Math.cos(Math.PI * (2 * n + 1) * m / (2 * N));
            }

            if(m == 0)
                tempVal = tempVal * Math.sqrt(1/N);
            else
                tempVal = tempVal * Math.sqrt(2/N);

            newValues.put(timeValues.get(m), tempVal);
        }

        Signal newSignal = new Signal();
        newSignal.setName(signal.getName() + "(CosTransformed)");
        newSignal.setAmplitude(signal.getAmplitude());
        newSignal.setStartingTime(signal.getStartingTime());
        newSignal.setDurationTime(signal.getDurationTime());
        newSignal.setSampling(newSignal.getSampling());
        newSignal.setValues(new TreeMap<>(newValues));

        return newSignal;
    }

    @Override
    public Signal inverseTransform(Signal signal) {
        List<Double> values = new ArrayList<>(signal.getValues().values());
        List<Double> timeValues = new ArrayList<>(signal.getValues().keySet());
        Map<Double, Double> newValues = new HashMap<>();
        int N = values.size();

        for(int n=0; n<N; n++)
        {
            Double tempVal = 0.0;
            for(int m=0; m<N; m++)
            {
                tempVal += values.get(n) * Math.cos(Math.PI * (2 * n + 1) * m / (2 * N));
                if(m == 0)
                    tempVal = tempVal * Math.sqrt(1/N);
                else
                    tempVal = tempVal * Math.sqrt(2/N);
            }

            newValues.put(timeValues.get(n), tempVal);
        }

        Signal newSignal = new Signal();
        newSignal.setName(signal.getName() + "(CosTransformed)");
        newSignal.setAmplitude(signal.getAmplitude());
        newSignal.setStartingTime(signal.getStartingTime());
        newSignal.setDurationTime(signal.getDurationTime());
        newSignal.setSampling(newSignal.getSampling());
        newSignal.setValues(new TreeMap<>(newValues));

        return newSignal;
    }
}
