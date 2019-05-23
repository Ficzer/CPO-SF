package SF.Radar;

import SF.Filter.SignalMixer;
import SF.Signal;
import javafx.application.Platform;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class RadarSimulator implements Runnable
{
    private Double objectSpeed;
    private Double signalSpeed;
    private Signal probingSignal;
    private int buforSize;
    private int simulationTimeStep; // milisec
    private boolean isRunning = true;
    private Double realObjectDistance;
    private Double calculatedObjectDistance;
    private SignalMixer signalMixer = new SignalMixer();
    Thread thread = new Thread(this);
    private Label objectCalculatedDistanceLabel;
    private Label objectActualDistanceLabel;
    private Double signalFrequency;
    private Signal correlation;

    public RadarSimulator(Double objectSpeed, Double signalSpeed, Signal probingSignal, Double startingObjectDistance,
                          Label objectActualDistanceLabel, Label objectCalculatedDistanceLabel, int simulationTimeStep)
    {
        this.objectSpeed = objectSpeed;
        this.signalSpeed = signalSpeed;
        this.probingSignal = probingSignal;
        this.realObjectDistance = startingObjectDistance;
        this.objectCalculatedDistanceLabel = objectCalculatedDistanceLabel;
        this.objectActualDistanceLabel = objectActualDistanceLabel;
        this.simulationTimeStep = simulationTimeStep;
        signalFrequency = probingSignal.getSampling() / probingSignal.getDurationTime();
    }

    @Override
    public void run()
    {
        while(true)
        {
            if(isRunning)
            {
                realObjectDistance += (objectSpeed / 10);
                Double time = realObjectDistance / signalSpeed;
                int n = (int)(probingSignal.getSampling() * 2 * time / probingSignal.getDurationTime());
                Signal delayedSignal = getDelayedSignal(probingSignal, n);
                correlation = signalMixer.correlation(probingSignal, delayedSignal);
                Double calculatedTime = ((double)findMax(correlation, probingSignal.getSampling()) - probingSignal.getSampling()+1)/signalFrequency;
                calculatedObjectDistance = calculatedTime / 2  * signalSpeed;

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        objectActualDistanceLabel.setText(realObjectDistance.toString());
                        objectCalculatedDistanceLabel.setText(calculatedObjectDistance.toString());
                    }
                });

            }

            try {
                Thread.sleep(simulationTimeStep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopSimulation()
    {
        isRunning = false;
    }

    public void restartSimulation()
    {
        isRunning = true;
    }

    public Signal getCorrelation()
    {
        return correlation;
    }

    public Thread getThread()
    {
        return thread;
    }

    private Signal getDelayedSignal(Signal signal, int delayCount)
    {
        Double signalProbingFrequency = signal.getSampling() / signal.getDurationTime();
        ArrayList<Double> values = new ArrayList<>(signal.getValues().values());
        ArrayList<Double> newValues = new ArrayList<>();
        Map<Double, Double> map = new HashMap<>();

        for (int i=0; i<values.size(); i++)
        {
            if(i+delayCount < values.size())
            {
                newValues.add(values.get(i+delayCount));
            }
            else
            {
                newValues.add(values.get(Math.abs(values.size() - i - delayCount)));
            }
        }

        for (int i=0; i<newValues.size(); i++)
        {
            map.put(i / signalProbingFrequency, newValues.get(i));
        }

        Signal newSignal = new Signal(signal.getName(), signal.getAmplitude(), signal.getPeriod(),
                signal.getStartingTime(), signal.getDurationTime(), signal.getFulfillment(), signal.getSampling());
        newSignal.setValues(new TreeMap<>(map));

        return newSignal;
    }

    private int findMax(Signal signal, int startingPoint)
    {
        ArrayList<Double> values = new ArrayList<>(signal.getValues().values());
        Double maxValue = 0.0;
        int indexOfMaxValue = startingPoint;

        for (int i=startingPoint; i<values.size(); i++)
        {
            if(values.get(i) > maxValue)
            {
                maxValue = values.get(i);
                indexOfMaxValue = i;
            }
        }

        return indexOfMaxValue;
    }
}
