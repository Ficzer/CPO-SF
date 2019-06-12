package SF.Transforms;

import SF.Signal;

import java.util.*;

public class FastCosineTransform implements Transform {
    @Override
    public Signal transform(Signal signal) {

        Transform fttTransorm = new DecimationInFrequencyFFT();
        List<Double> values = new ArrayList<>(signal.getValues().values());
        List<Double> timeValues = new ArrayList<>(signal.getValues().keySet());
        Map<Double, Double> newValues = new HashMap<>();
        int N = values.size();
        int halfN = N / 2;

        List<Double> real = new ArrayList<>(N);

        for (int i = 0; i < halfN; i++) {
            real.set(i, values.get(i * 2));
            real.set(N - 1 - i, values.get(i * 2 + 1));
        }

        if(N % 2 == 1)
            real.set(halfN, values.get(N-1));

        Map<Double, Double> valuesToTransForm = new HashMap<Double, Double>();

        for(int i=0; i<N; i++)
        {
            valuesToTransForm.put(timeValues.get(i), real.get(i));
        }

        signal.setValues(new TreeMap<>(valuesToTransForm));

        // FTT Transform
        signal = fttTransorm.transform(signal);

        values = new ArrayList<>(signal.getValues().values());
        List<Double> imaginaryValues = new ArrayList<>(signal.getValues().keySet());
        timeValues = new ArrayList<>(signal.getValues().keySet());

        List<Double> retValues = new ArrayList<>(N);

        for(int i=0; i<N; i++)
        {
            Double temp = i * Math.PI / (N/2);
            retValues.set(i, values.get(i) * Math.cos(temp) + imaginaryValues.get(i) * Math.sin(temp));
        }

        for(int i=0; i<N; i++)
        {
            newValues.put(timeValues.get(i), retValues.get(i));
        }

        Signal newSignal = new Signal();
        newSignal.setName(signal.getName() + "(FCT)");
        newSignal.setAmplitude(signal.getAmplitude());
        newSignal.setStartingTime(signal.getStartingTime());
        newSignal.setDurationTime(signal.getDurationTime());
        newSignal.setSampling(newSignal.getSampling());
        newSignal.setValues(new TreeMap<>(newValues));

        return newSignal;

    }

    @Override
    public Signal inverseTransform(Signal signal) {
        return null;
    }
}
