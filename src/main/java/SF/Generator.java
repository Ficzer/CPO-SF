package SF;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class Generator
{
    public Signal UniformNoise(Double amplitude, Double startingTime, Double durationTime, int sampling)
    {
        Signal signal = new Signal("Noise");

        signal.setAmplitude(amplitude);
        signal.setStartingTime(startingTime);
        signal.setDurationTime(durationTime);
        signal.setSampling(sampling);

        Random rng = new Random();
        Double value;

        for(Double i=startingTime; i<=durationTime+startingTime; i+=durationTime/(double)sampling)
        {
            value = 2 * (rng.nextDouble() - 0.5) * amplitude;
            signal.getValues().put(i, value);
        }

        signal.setValues(new TreeMap<Double, Double>(signal.getValues()));

        return signal;
    }

    public Signal GaussianNoise(Double amplitude, Double startingTime, Double durationTime, int sampling)
    {
        Signal signal = new Signal("GaussianNoise");

        signal.setAmplitude(amplitude);
        signal.setStartingTime(startingTime);
        signal.setDurationTime(durationTime);
        signal.setSampling(sampling);

        Random rng = new Random();
        Double value;

        for(Double i=startingTime; i<=durationTime+startingTime; i+=durationTime/(double)sampling)
        {
            value = rng.nextGaussian() * amplitude * 0.4;
            signal.getValues().put(i, value);
        }

        signal.setValues(new TreeMap<Double, Double>(signal.getValues()));

        return signal;
    }

    public Signal Sinusoidal(Double amplitude, Double startingTime, Double durationTime, Double period, int sampling)
    {
        Signal signal = new Signal("Sine");

        signal.setAmplitude(amplitude);
        signal.setStartingTime(startingTime);
        signal.setDurationTime(durationTime);
        signal.setPeriod(period);
        signal.setSampling(sampling);
		sampling = normalizeSampling(sampling, durationTime, period);
        Double value;

        for(Double i=startingTime; i<=durationTime+startingTime; i+=durationTime/(double)sampling)
        {
            value = amplitude * Math.sin(2*Math.PI/period*(i-startingTime));
            signal.getValues().put(i, value);
        }

        signal.setValues(new TreeMap<Double, Double>(signal.getValues()));

        return signal;
    }

    public Signal ErectedSinusoidal(Double amplitude, Double startingTime, Double durationTime, Double period, int sampling)
    {
		//sampling = normalizeSampling(sampling, durationTime, period);
        Signal signal = this.Sinusoidal(amplitude, startingTime, durationTime, period, sampling);

        signal.setName("Half-waveRectifiedSine");

        for (Map.Entry<Double,Double> entry: signal.getValues().entrySet())
        {
            if(entry.getValue() < 0)
            {
                entry.setValue(0.0);
            }
        }

        signal.setValues(new TreeMap<Double, Double>(signal.getValues()));

        return signal;
    }

    public Signal ErectedSinusoidalTwoParts(Double amplitude, Double startingTime, Double durationTime, Double period, int sampling)
    {
		//sampling = normalizeSampling(sampling, durationTime, period);
        Signal signal = this.Sinusoidal(amplitude, startingTime, durationTime, period, sampling);

        signal.setName("Full-waveRectifiedSine");

        for (Map.Entry<Double,Double> entry: signal.getValues().entrySet())
        {
            if(entry.getValue() < 0)
            {
                entry.setValue(Math.abs(entry.getValue()));
            }
        }

        signal.setValues(new TreeMap<Double, Double>(signal.getValues()));

        return signal;
    }

    public Signal Rectangular(Double amplitude, Double startingTime, Double durationTime, Double period, Double fullfilment, int sampling)
    {
		//sampling = normalizeSampling(sampling, durationTime, period);
        Signal signal = new Signal("SquareWave");

        signal.setAmplitude(amplitude);
        signal.setStartingTime(startingTime);
        signal.setDurationTime(durationTime);
        signal.setPeriod(period);
        signal.setFulfillment(fullfilment);
        signal.setSampling(sampling);
        Double value, pom = 0.0;
        int k = 0;
        boolean flag = true;

        for(Double i=startingTime; i<=durationTime+startingTime; i+=durationTime/(double)sampling)
        {
            pom = i - period;
            flag = true;
            while(flag)
            {
                if(pom >= 0.0)
                {
                    k++;
                    pom -= period;
                }
                else
                {
                    flag = false;
                }
            }

            if(i >= k*period + startingTime && i < fullfilment*period + k*period + startingTime)
            {
                value = amplitude;
            }
            else
            {
                value = 0.0;
            }
            signal.getValues().put(i, value);
            k = 0;
        }

        signal.setValues(new TreeMap<Double, Double>(signal.getValues()));

        return signal;
    }

    public Signal RectangularSimetrical(Double amplitude, Double startingTime, Double durationTime, Double period, Double fullfilment, int sampling)
    {
		//sampling = normalizeSampling(sampling, durationTime, period);
        Signal signal = new Signal("SymmetricalRectangularSignal");

        signal.setAmplitude(amplitude);
        signal.setStartingTime(startingTime);
        signal.setDurationTime(durationTime);
        signal.setPeriod(period);
        signal.setFulfillment(fullfilment);
        signal.setSampling(sampling);

		sampling = normalizeSampling(sampling, durationTime, period);
        Double value, pom = 0.0;
        int k = 0;
        boolean flag = true;

        for(Double i=startingTime; i<=durationTime+startingTime; i+=durationTime/(double)sampling)
        {
            pom = i - period;
            flag = true;
            while(flag)
            {
                if(pom >= 0.0)
                {
                    k++;
                    pom -= period;
                }
                else
                {
                    flag = false;
                }
            }

            if(i >= k*period + startingTime && i < fullfilment*period + k*period + startingTime)
            {
                value = amplitude;
            }
            else
            {
                value = -amplitude;
            }
            signal.getValues().put(i, value);
            k = 0;
        }

        signal.setValues(new TreeMap<Double, Double>(signal.getValues()));

        return signal;
    }

    public Signal Triangular(Double amplitude, Double startingTime, Double durationTime, Double period, Double fullfilment, int sampling)
    {

        Signal signal = new Signal("Triangular");

        signal.setAmplitude(amplitude);
        signal.setStartingTime(startingTime);
        signal.setDurationTime(durationTime);
        signal.setPeriod(period);
        signal.setFulfillment(fullfilment);
        signal.setSampling(sampling);
        Double value, pom = 0.0;
        int k = 0;
        boolean flag = true;
		sampling = normalizeSampling(sampling, durationTime, period);
        for(Double i=startingTime; i<=durationTime+startingTime; i+=durationTime/(double)sampling)
        {
            pom = i - period;
            flag = true;
            while(flag)
            {
                if(pom >= 0.0)
                {
                    k++;
                    pom -= period;
                }
                else
                {
                    flag = false;
                }
            }

            if(i >= k*period + startingTime && i < fullfilment*period + k*period + startingTime)
            {
                value = amplitude/(fullfilment*period)*(i-k*period-startingTime);
            }
            else
            {
                value = (-amplitude/(period*(1.0-fullfilment))*(i-k*period-startingTime))+amplitude/(1-fullfilment);
            }

            if(value > amplitude + 0.0001)
                value = 0.0;

            signal.getValues().put(i, value);
            k = 0;
        }

        signal.setValues(new TreeMap<Double, Double>(signal.getValues()));

        return signal;
    }

    public Signal UnitJump(Double amplitude, Double startingTime, Double durationTime, int sampling)
    {
        Signal signal = new Signal("Unit jump");

        signal.setAmplitude(amplitude);
        signal.setStartingTime(startingTime);
        signal.setDurationTime(durationTime);
        signal.setSampling(sampling);

        Double value = 0.0;
        Double ts = (durationTime - startingTime) / 2.0;

        for(Double i=startingTime; i<=durationTime+startingTime; i+=durationTime/(double)sampling)
        {
            if(i < ts)
            {
                value = 0.0;
            }
            else if(i == ts)
            {
                value = amplitude/2.0;
            }
            else if(i > ts)
            {
                value = amplitude;
            }

            signal.getValues().put(i, value);

        }

        signal.setValues(new TreeMap<Double, Double>(signal.getValues()));

        return signal;
    }

    public Signal UnitImpulse (Double amplitude, Double startingTime, Double durationTime, int impuls, int sampling)
    {
        Signal signal = new Signal("Unit impulse");
        signal.setAmplitude(amplitude);
        signal.setStartingTime(startingTime);
        signal.setDurationTime(durationTime);
        signal.setSampling(sampling);

        int iterator = 0;

        for(Double i=startingTime; i<=durationTime+startingTime; i+=durationTime/(double)sampling, iterator++)
        {
            if(iterator == impuls)
                signal.getValues().put(i, amplitude);
            else
                signal.getValues().put(i, 0.0);
        }

        signal.setValues(new TreeMap<Double, Double>(signal.getValues()));

        return signal;
    }

    public Signal ImpulseNoise(Double amplitude, Double startingTime, Double durationTime, Double probability, int sampling) throws WrongProbabilityFormatException {
        Signal signal = new Signal("Impulse noise");
        signal.setAmplitude(amplitude);
        signal.setStartingTime(startingTime);
        signal.setDurationTime(durationTime);
        signal.setSampling(sampling);

        int iterator = 0;
        Random random = new Random();

        if(probability < 0.0 || probability > 1.0)
        {
            throw new WrongProbabilityFormatException();
        }

        for(Double i=startingTime; i<=durationTime+startingTime; i+=durationTime/(double)sampling, iterator++)
        {
            if(random.nextDouble() < probability)
            {
                signal.getValues().put(i, amplitude);
            }
            else
            {
                signal.getValues().put(i, 0.0);
            }
        }

        signal.setValues(new TreeMap<Double, Double>(signal.getValues()));

        return signal;
    }

    private int normalizeSampling(int sampling, Double durationTime, Double period)
    {
        double pom = durationTime / period;
        return (int)(sampling * pom);
    }
}
