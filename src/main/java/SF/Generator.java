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
        Signal signal = new Signal("UniformNoise");

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
}
