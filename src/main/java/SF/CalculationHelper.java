package SF;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CalculationHelper
{
    public Double Average(Signal signal)
    {
        Double result = 0.0;

        for (Map.Entry<Double,Double> entry: signal.getValues().entrySet())
        {
            result += entry.getValue();
        }

        result = result / (double)(signal.getValues().size() + 1);

        result *= 100;
        result = (double)Math.round(result);
        result /= 100;

        return result;
    }

    public Double AbsoluteAvarage(Signal signal)
    {
        Double result = 0.0;

        for (Map.Entry<Double,Double> entry: signal.getValues().entrySet())
        {
            result += Math.abs(entry.getValue());
        }

        result = result / (double)(signal.getValues().size() + 1);

        result *= 100;
        result = (double)Math.round(result);
        result /= 100;

        return result;
    }

    public Double Strenght(Signal signal)
    {
        Double result = 0.0;

        for (Map.Entry<Double,Double> entry: signal.getValues().entrySet())
        {
            result += entry.getValue() * entry.getValue();
        }

        result = result / (double)(signal.getValues().size() + 1);

        result *= 100;
        result = (double)Math.round(result);
        result /= 100;

        return result;
    }

    public Double Variance(Signal signal)
    {
        Double result = 0.0;
        Double average = this.Average(signal);

        for (Map.Entry<Double,Double> entry: signal.getValues().entrySet())
        {
            result += entry.getValue() - average;
        }

        result = result / (double)(signal.getValues().size() + 1);

        result *= 100;
        result = (double)Math.round(result);
        result /= 100;

        return result;
    }

    public Double RootMeanSquare(Signal signal)
    {
        Double result = 0.0;
        result = Math.sqrt(this.Strenght(signal));

        result *= 100;
        result = (double)Math.round(result);
        result /= 100;

        return result;
    }

    public Signal addSignals(Signal signalOne, Signal signalTwo) throws WrongSamplingException
    {
        if(!signalOne.getSampling().equals(signalTwo.getSampling()))
        {
            throw new WrongSamplingException("Sampling of two signals does't match");
        }

        List<Double> tempList = new ArrayList<Double>();

        for (Map.Entry<Double,Double> entry: signalOne.getValues().entrySet())
        {
            tempList.add(entry.getValue());
        }

        int i = 0;
        for (Map.Entry<Double,Double> entry: signalTwo.getValues().entrySet())
        {
            entry.setValue(entry.getValue() + tempList.get(i));
            i++;
        }

        Signal resultSignal = new Signal();
        resultSignal.setName(signalOne.getName() + " + " + signalTwo.getName());
        resultSignal.setSampling(signalTwo.getSampling());
        resultSignal.setValues(signalTwo.getValues());
        resultSignal.setFulfillment(signalTwo.getFulfillment());
        resultSignal.setPeriod(signalTwo.getPeriod());
        resultSignal.setStartingTime(signalTwo.getStartingTime());
        resultSignal.setDurationTime(signalTwo.getDurationTime());

        return resultSignal;
    }

    public Signal subtractSignals(Signal signalOne, Signal signalTwo) throws WrongSamplingException
    {
        if(!signalOne.getSampling().equals(signalTwo.getSampling()))
        {
            throw new WrongSamplingException("Sampling of two signals does't match");
        }

        List<Double> tempList = new ArrayList<Double>();

        for (Map.Entry<Double,Double> entry: signalOne.getValues().entrySet())
        {
            tempList.add(entry.getValue());
        }

        int i = 0;
        for (Map.Entry<Double,Double> entry: signalTwo.getValues().entrySet())
        {
            entry.setValue(entry.getValue() - tempList.get(i));
            i++;
        }

        Signal resultSignal = new Signal();
        resultSignal.setName(signalOne.getName() + " + " + signalTwo.getName());
        resultSignal.setSampling(signalTwo.getSampling());
        resultSignal.setValues(signalTwo.getValues());
        resultSignal.setFulfillment(signalTwo.getFulfillment());
        resultSignal.setPeriod(signalTwo.getPeriod());
        resultSignal.setStartingTime(signalTwo.getStartingTime());
        resultSignal.setDurationTime(signalTwo.getDurationTime());

        return resultSignal;
    }

    public Signal multiplySignals(Signal signalOne, Signal signalTwo) throws WrongSamplingException
    {
        if(!signalOne.getSampling().equals(signalTwo.getSampling()))
        {
            throw new WrongSamplingException("Sampling of two signals does't match");
        }

        List<Double> tempList = new ArrayList<Double>();

        for (Map.Entry<Double,Double> entry: signalOne.getValues().entrySet())
        {
            tempList.add(entry.getValue());
        }

        int i = 0;
        for (Map.Entry<Double,Double> entry: signalTwo.getValues().entrySet())
        {
            entry.setValue(entry.getValue() * tempList.get(i));
            i++;
        }

        Signal resultSignal = new Signal();
        resultSignal.setName(signalOne.getName() + " + " + signalTwo.getName());
        resultSignal.setSampling(signalTwo.getSampling());
        resultSignal.setValues(signalTwo.getValues());
        resultSignal.setFulfillment(signalTwo.getFulfillment());
        resultSignal.setPeriod(signalTwo.getPeriod());
        resultSignal.setStartingTime(signalTwo.getStartingTime());
        resultSignal.setDurationTime(signalTwo.getDurationTime());

        return resultSignal;
    }

    public Signal divideSignals(Signal signalOne, Signal signalTwo) throws WrongSamplingException
    {
        if(!signalOne.getSampling().equals(signalTwo.getSampling()))
        {
            throw new WrongSamplingException("Sampling of two signals does't match");
        }

        List<Double> tempList = new ArrayList<Double>();

        for (Map.Entry<Double,Double> entry: signalOne.getValues().entrySet())
        {
            tempList.add(entry.getValue());
        }

        int i = 0;
        for (Map.Entry<Double,Double> entry: signalTwo.getValues().entrySet())
        {
            entry.setValue(entry.getValue() / tempList.get(i));
            i++;
        }

        Signal resultSignal = new Signal();
        resultSignal.setName(signalOne.getName() + " + " + signalTwo.getName());
        resultSignal.setSampling(signalTwo.getSampling());
        resultSignal.setValues(signalTwo.getValues());
        resultSignal.setFulfillment(signalTwo.getFulfillment());
        resultSignal.setPeriod(signalTwo.getPeriod());
        resultSignal.setStartingTime(signalTwo.getStartingTime());
        resultSignal.setDurationTime(signalTwo.getDurationTime());

        return resultSignal;
    }
}
