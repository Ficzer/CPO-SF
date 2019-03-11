package SF;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Signal
{
    private String name;
    private Double amplitude;
    private Double period;
    private Double startingTime;
    private Double durationTime;
    private Double fulfillment;
    private List<Double> values;
    private List<Double> imaginaryValues;

    public Signal(String name)
    {
        this.name = name;
        this.values = new ArrayList<Double>();
        this.imaginaryValues = new ArrayList<Double>();
    }

    public Signal(String name, Double amplitude, Double period, Double startingTime, Double durationTime, Double fulfillment)
    {
        this.name = name;
        this.amplitude = amplitude;
        this.period = period;
        this.startingTime = startingTime;
        this.durationTime = durationTime;
        this.fulfillment = fulfillment;
        this.values = new ArrayList<Double>();
        this.imaginaryValues = new ArrayList<Double>();
    }

}
