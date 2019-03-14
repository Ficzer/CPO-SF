package SF;

import java.io.IOException;
import java.util.*;


public class App
{
    public static void main(String[] args)
    {

        Generator generator = new Generator();
        Signal signalOne = generator.Sinusoidal(1.0, 0.0, 10.0, 3.0, 300);
        Signal signalTwo = generator.ErectedSinusoidal(1.5, 0.0, 10.0, 1.0, 300);
        Signal result = null;

        CalculationHelper calculationHelper = new CalculationHelper();

        try {
            result = calculationHelper.subtractSignals(signalOne, signalTwo);
        } catch (WrongSamplingException e) {
            e.printStackTrace();
        }
        // Create Chart
        Map<Double, Double> values = result.getValues();
        double[] xs = new double[values.size()];
        double[] list = new double[values.size()];

        int i=0;
        for (Map.Entry<Double,Double> entry: values.entrySet())
        {
            xs[i] = entry.getKey();
            list[i] = entry.getValue();
            i++;
        }

        Draw draw = new Draw();
        draw.draw(xs, list);



        // WindowApp.main(args);

    }
}
