package SF;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
    }

    public Signal(String name, Double amplitude, Double period, Double startingTime, Double durationTime, Double fulfillment)
    {
        this.name = name;
        this.amplitude = amplitude;
        this.period = period;
        this.startingTime = startingTime;
        this.durationTime = durationTime;
        this.fulfillment = fulfillment;
    }

    //TODO: Signal class for discreet signal

}
