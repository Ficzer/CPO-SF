package SF.Transforms;

import SF.Signal;

import java.util.*;

public class DiscreteFourierTransform implements Transform {

    @Override
    public Signal transform(Signal signal) {

        List<Double> values = new ArrayList<>(signal.getValues().values());
        List<Double> timeValues = new ArrayList<>(signal.getValues().keySet());
        int N = signal.getValues().size() - 1;
        Double frequency = signal.getDurationTime() / signal.getSampling() * 1.0;

        Map<Double, Double> realValues = new HashMap<>();
        Map<Double, Double> complexValues = new HashMap<>();


        Double transformedValueReal;
        Double transformedValueComplex;

        for(int m=0; m<N; m++)
        {
            transformedValueReal = 0.0;
            transformedValueComplex = 0.0;
            for(int n=0; n<N; n++)
            {
                Double angle = -2 * Math.PI * n * m / N;
                transformedValueReal += values.get(n) * Math.cos(angle);
                transformedValueComplex += values.get(n) * Math.sin(angle);
            }
            transformedValueReal /= N;
            transformedValueComplex /= N;

            realValues.put(1 / frequency / signal.getSampling() * m, transformedValueReal);
            complexValues.put(1 / frequency / signal.getSampling() * m, transformedValueComplex);
        }

        Signal newSignal = new Signal();
        newSignal.setName(signal.getName() + "(Transformed)");
        newSignal.setAmplitude(signal.getAmplitude());
        newSignal.setStartingTime(0.0);
        newSignal.setDurationTime(frequency);
        newSignal.setSampling(newSignal.getSampling());
        newSignal.setValues(new TreeMap<>(realValues));
        newSignal.setImaginaryValues(new TreeMap<>(complexValues));

        return newSignal;
    }

    @Override
    public Signal inverseTransform(Signal signal) {
        List<Double> values = new ArrayList<>(signal.getValues().values());
        List<Double> imaginaryValues = new ArrayList<>(signal.getImaginaryValues().values());
        List<Double> timeValues = new ArrayList<>(signal.getValues().keySet());
        int N = signal.getValues().size();

        Map<Double, Double> realValues = new HashMap<>();
        Map<Double, Double> complexValues = new HashMap<>();

        Double transformedValueReal;
        Double transformedValueComplex;

        for(int m=0; m<N; m++)
        {
            transformedValueReal = 0.0;
            transformedValueComplex = 0.0;
            for(int n=0; n<N; n++)
            {
                Double angle = 2 * Math.PI * n * m / N;
                transformedValueReal += values.get(n) * Math.cos(angle) - imaginaryValues.get(n) * Math.sin(angle);
                transformedValueComplex += values.get(n) * Math.cos(angle) + imaginaryValues.get(n) * Math.sin(angle);
            }
            realValues.put(timeValues.get(m), transformedValueReal);
        }

        Double frequency = signal.getDurationTime() / signal.getSampling() * N;

        Signal newSignal = new Signal();
        newSignal.setName(signal.getName() + "(Detransformed)");
        newSignal.setAmplitude(signal.getAmplitude());
        newSignal.setStartingTime(signal.getStartingTime());
        newSignal.setDurationTime(1.0 / frequency * N);
        newSignal.setSampling(newSignal.getSampling());
        newSignal.setValues(new TreeMap<>(realValues));

        return newSignal;
    }
}
