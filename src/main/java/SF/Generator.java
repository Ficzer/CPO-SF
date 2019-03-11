package SF;

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

        for(int i=0; i<sampling; i++)
        {
            value = (rng.nextDouble() - 0.5) * amplitude;
            signal.getValues().add(value);
        }

        return signal;
    }

    public Signal GuasianNoise(Double amplitude, Double startingTime, Double durationTime, int sampling)
    {
        Signal signal = new Signal("GuasianNoise");

        signal.setAmplitude(amplitude);
        signal.setStartingTime(startingTime);
        signal.setDurationTime(durationTime);

        Random rng = new Random();
        Double value;

        for(int i=0; i<sampling; i++)
        {
            value = (rng.nextGaussian() - 0.5) * amplitude;
            signal.getValues().add(value);
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

        for(int i=0; i<sampling; i++)
        {
            value = amplitude * Math.sin(2*Math.PI*period*(double)i/(double)sampling-startingTime);
            signal.getValues().add(value);
        }

        return signal;
    }

    public Signal ErectedSinusoidal(Double amplitude, Double startingTime, Double durationTime, Double period, int sampling)
    {
        Signal signal = this.Sinusoidal(amplitude, startingTime, durationTime, period, sampling);

        for(int i=0; i<signal.getValues().size(); i++)
        {
            if(signal.getValues().get(i) < 0)
            {
                signal.getValues().set(i, 0.0);
            }
        }
        return signal;
    }

    public Signal ErectedSinusoidalTwoParts(Double amplitude, Double startingTime, Double durationTime, Double period, int sampling)
    {
        Signal signal = this.Sinusoidal(amplitude, startingTime, durationTime, period, sampling);

        for(int i=0; i<signal.getValues().size(); i++)
        {
            if(signal.getValues().get(i) < 0)
            {
                signal.getValues().set(i, Math.abs(signal.getValues().get(i)));
            }
        }
        return signal;
    }
}
