package SF.Filter;

import SF.Signal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Filter
{
    private WindowFunction windowFunction;
    private FilterFunction filterFunction;
    private int M;
    private Double frequency;

    Filter(WindowFunction windowFunction, FilterFunction filterFunction, int M, Double frequency)
    {
        this.windowFunction = windowFunction;
        this.filterFunction = filterFunction;
        this.M = M;
        this.frequency = frequency;
    }

    // return h and filtered signal
    public List<Signal> apply(Signal signal)
    {
        // Calculating h

        final int K = filterFunction.getK(signal.getSampling() / signal.getDurationTime(), frequency);

        double[] newValues = new double[M];

        for(int i=0; i<M; i++)
        {
            if(i == (M-1)/2)
            {
                newValues[i] = 2.0 / K;
            }
            else
            {
                newValues[i] = Math.sin((2.0 * Math.PI * (i - (M - 1) / 2)) / K) / (Math.PI * (i - (M - 1) / 2));
            }

            newValues[i] *= windowFunction.getValue(i, M);
            newValues[i] *= filterFunction.getValue(i - (M - 1) / 2);
        }

        Map<Double, Double> newValuesMap = new HashMap<>();

        for(int i=0; i<newValues.length; i++)
        {
            newValuesMap.put(i * signal.getSampling() / signal.getDurationTime(), newValues[i]);
        }

        Signal h = new Signal("h", signal.getAmplitude(), 0.0, signal.getStartingTime(),
                signal.getStartingTime() + 1.0 / signal.getSampling() / signal.getDurationTime() * M, 0.0, signal.getSampling());

        List<Signal> res = new ArrayList<>();
        res.add(h);

        // Convultion

        SignalMixer signalMixer = new SignalMixer();

        Signal convolutionedSignal = signalMixer.convolution(h, signal);

        // Shrinking

        ArrayList<Double> oldConvolutionValues = (ArrayList<Double>) convolutionedSignal.getValues().values();
        Map<Double, Double> newConvolutionValues = new HashMap<>();
        Double samplingFrequency = convolutionedSignal.getSampling() / convolutionedSignal.getDurationTime();
        Double newDurationTime = convolutionedSignal.getDurationTime() - (M-1) / samplingFrequency;

        for(int i=0; i<oldConvolutionValues.size() - M + 1; i++)
        {
            newConvolutionValues.put(i * samplingFrequency / newDurationTime, oldConvolutionValues.get(i + (M - 1) / 2));
        }

        Signal filteredSignal = new Signal(convolutionedSignal.getName() + "(filterd)", convolutionedSignal.getAmplitude(),
            0.0, convolutionedSignal.getStartingTime(), newDurationTime, 0.0, convolutionedSignal.getSampling());

        res.add(filteredSignal);

        return res;
    }
}
