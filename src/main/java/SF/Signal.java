package SF;


import lombok.Data;

import java.util.HashMap;

@Data
public class Signal
{
    private String name;
    private Double amplitude;
    private Double period;
    private Double startingTime;
    private Double durationTime;
    private Double fulfillment;
    private HashMap<Double, Double> values;
    private HashMap<Double, Double> imaginaryValues;

    public Signal(String name)
    {
        this.name = name;
        this.values = new HashMap<Double, Double>();
        this.imaginaryValues = new HashMap<Double, Double>();
    }

    public Signal(String name, Double amplitude, Double period, Double startingTime, Double durationTime, Double fulfillment)
    {
        this.name = name;
        this.amplitude = amplitude;
        this.period = period;
        this.startingTime = startingTime;
        this.durationTime = durationTime;
        this.fulfillment = fulfillment;
        this.values = new HashMap<Double, Double>();
        this.imaginaryValues = new HashMap<Double, Double>();
    }

}
