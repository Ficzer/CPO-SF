package SF;

import java.util.Map;
import java.util.Random;

public class Generator
{
    public Signal UniformNoise(Double amplitude, Double startingTime, Double durationTime, int sampling)
    {
        Signal signal = new Signal("UniformNoise");

        signal.setAmplitude(amplitude);
        signal.setStartingTime(startingTime);
        signal.setDurationTime(durationTime);

        Random rng = new Random();
        Double value;

        for(Double i=startingTime; i<durationTime+startingTime; i+=durationTime/(double)sampling)
        {
            value = (rng.nextDouble() - 0.5) * amplitude;
            signal.getValues().put(i, value);
        }

        return signal;
    }

    public Signal GaussianNoise(Double amplitude, Double startingTime, Double durationTime, int sampling)
    {
        Signal signal = new Signal("GaussianNoise");

        signal.setAmplitude(amplitude);
        signal.setStartingTime(startingTime);
        signal.setDurationTime(durationTime);

        Random rng = new Random();
        Double value;

        for(Double i=startingTime; i<durationTime+startingTime; i+=durationTime/(double)sampling)
        {
            value = (rng.nextGaussian() - 0.5) * amplitude;
            signal.getValues().put(i, value);
        }

        return signal;
    }

    public Signal Sinusoidal(Double amplitude, Double startingTime, Double durationTime, Double period, int sampling)
    {
        Signal signal = new Signal("Sinusoidal");

        signal.setAmplitude(amplitude);
        signal.setStartingTime(startingTime);
        signal.setDurationTime(durationTime);
        signal.setPeriod(period);

        Double value;
        System.out.println(durationTime/(double)sampling);

        for(Double i=startingTime; i<durationTime+startingTime; i+=durationTime/(double)sampling)
        {
            value = amplitude * Math.sin(2*Math.PI/period*(i-startingTime));
            System.out.println(value);
            signal.getValues().put(i, value);
        }

        return signal;
    }

    public Signal ErectedSinusoidal(Double amplitude, Double startingTime, Double durationTime, Double period, int sampling)
    {
        Signal signal = this.Sinusoidal(amplitude, startingTime, durationTime, period, sampling);

        for (Map.Entry<Double,Double> entry: signal.getValues().entrySet())
        {
            if(entry.getValue() < 0)
            {
                entry.setValue(0.0);
            }
        }
        return signal;
    }

    public Signal ErectedSinusoidalTwoParts(Double amplitude, Double startingTime, Double durationTime, Double period, int sampling)
    {
        Signal signal = this.Sinusoidal(amplitude, startingTime, durationTime, period, sampling);

        for (Map.Entry<Double,Double> entry: signal.getValues().entrySet())
        {
            if(entry.getValue() < 0)
            {
                entry.setValue(Math.abs(entry.getValue()));
            }
        }
        return signal;
    }

    public Signal Rectangular(Double amplitude, Double startingTime, Double durationTime, Double period, Double fullfilment, int sampling)
    {
        Signal signal = new Signal("Rectangular");

        signal.setAmplitude(amplitude);
        signal.setStartingTime(startingTime);
        signal.setDurationTime(durationTime);
        signal.setPeriod(period);
        signal.setFulfillment(fullfilment);

        Double value;

        return null;
    }
}
