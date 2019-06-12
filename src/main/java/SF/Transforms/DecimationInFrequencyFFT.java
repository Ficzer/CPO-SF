package SF.Transforms;

import SF.Signal;
import org.apache.commons.math3.complex.Complex;

import java.util.*;

public class DecimationInFrequencyFFT implements Transform {

    private Complex[] FFT(Complex[] values) {
        int N = values.length;
        if (N == 1) {
            return values;
        }
        int M = N / 2;

        Complex[] firstHalf = new Complex[M];
        Complex[] secondHalf = new Complex[M];

        for (int k = 0; k < M; k++) {
            double angle = -Math.PI * k * 2.0 / (N * 1.0);
            Complex exp = new Complex(Math.cos(angle), Math.sin(angle));
            firstHalf[k] = values[k].add(values[k + M]);
            secondHalf[k] = (values[k].subtract(values[k + M])).multiply(exp);
        }

        Complex[] FfirstHalf = FFT(firstHalf);
        Complex[] FsecondHalf = FFT(secondHalf);

        for (int i = 0; i < M; i++) {
            values[2 * i] = FfirstHalf[i];
            values[2 * i + 1] = FsecondHalf[i];
        }

        return values;
    }

    private Complex[] iFFT(Complex[] values) {
        int N = values.length;
        if (N == 1) {
            return values;
        }
        int M = N / 2;

        Complex[] firstHalf = new Complex[M];
        Complex[] secondHalf = new Complex[M];

        for (int k = 0; k < M; k++) {
            double angle = Math.PI * k * 2.0 / (N * 1.0);
            Complex exp = new Complex(Math.cos(angle), Math.sin(angle));
            firstHalf[k] = values[k].add(values[k + M]);
            secondHalf[k] = (values[k].subtract(values[k + M])).multiply(exp);
        }

        Complex[] FfirstHalf = iFFT(firstHalf);
        Complex[] FsecondHalf = iFFT(secondHalf);

        for (int i = 0; i < M; i++) {
            values[2 * i] = FfirstHalf[i];
            values[2 * i + 1] = FsecondHalf[i];
        }

        return values;
    }

    @Override
    public Signal transform(Signal signal) {

        int length = signal.getValues().size();
        List<Double> timeValues = new ArrayList<>(signal.getValues().keySet());
        Map<Double, Double> realValues = new HashMap<>();
        Map<Double, Double> complexValues = new HashMap<>();

        double[] oldValues = new double[length];

        System.arraycopy(signal.getValues().values(), 0, oldValues, 0, length);

        int bits = (int) Math.ceil((Math.log(length) / Math.log(2)));

        length = (int) Math.pow(2, (bits));
        Complex[] values = new Complex[length];

        for (int i = 0; i < oldValues.length; i++) {
            values[i] = new Complex(oldValues[i], 0.0);
        }

        for (int i = oldValues.length; i < length; i++) {
            values[i] = new Complex(0.0, 0.0);
        }

        values = FFT(values);

        for (int i = 0; i < length; i++) {
            realValues.put(timeValues.get(i), values[i].getReal() / (length * 1.0));
            complexValues.put(timeValues.get(i), values[i].getImaginary() / (length * 1.0));
        }

        Double frequency = signal.getDurationTime() / signal.getSampling();

        Signal newSignal = new Signal();
        newSignal.setName(signal.getName() + "(Transformed)");
        newSignal.setAmplitude(signal.getAmplitude());
        newSignal.setStartingTime(signal.getStartingTime());
        newSignal.setDurationTime(frequency);
        newSignal.setSampling(newSignal.getSampling());
        newSignal.setValues(new TreeMap<>(realValues));
        newSignal.setImaginaryValues(new TreeMap<>(complexValues));

        return newSignal;
    }

    @Override
    public Signal inverseTransform(Signal signal) {
        List<Double> imaginaryValues = new ArrayList<>(signal.getImaginaryValues().values());
        List<Double> timeValues = new ArrayList<>(signal.getValues().keySet());
        Map<Double, Double> realValues = new HashMap<>();

        int length = signal.getValues().size();
        double[] oldValues = new double[length];
        System.arraycopy(signal.getValues().values(), 0, oldValues, 0, length);
        int bits = (int) Math.ceil((Math.log(length) / Math.log(2)));
        length = (int) Math.pow(2, (bits));
        Complex[] values = new Complex[length];
        for (int i = 0; i < oldValues.length; i++) {
            values[i] = new Complex(oldValues[i], imaginaryValues.get(i));
        }
        for (int i = oldValues.length; i < length; i++) {
            values[i] = new Complex(0.0, 0.0);
        }

        values = iFFT(values);

        for (int i = 0; i < length; i++) {
            realValues.put(timeValues.get(i), values[i].getReal());
        }

        Double frequency = signal.getDurationTime() / signal.getSampling() * length;

        Signal newSignal = new Signal();
        newSignal.setName(signal.getName() + "(Transformed)");
        newSignal.setAmplitude(signal.getAmplitude());
        newSignal.setStartingTime(signal.getStartingTime());
        newSignal.setDurationTime(1.0 / frequency * length);
        newSignal.setSampling(newSignal.getSampling());
        newSignal.setValues(new TreeMap<>(realValues));

        return newSignal;
    }

}
