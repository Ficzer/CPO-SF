package SF;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class App
{
    public static void main(String[] args) {
        System.out.println("XDD");
        Generator generator = new Generator();
        System.out.println(generator.Sinusoidal(1.0, 0.0, 10.0, 10.0, 100));

        // Create Chart
        HashMap<Double, Double> values = generator.ErectedSinusoidalTwoParts(1.0, 0.0, 30.0,10.0, 500).getValues();
        double[] xs = new double[values.size()];
        double[] list = new double[values.size()];

        int i=0;
        for (Map.Entry<Double,Double> entry: values.entrySet())
        {
            xs[i] = entry.getKey();
            list[i] = entry.getValue();
            i++;
        } // TODO fix god damn ploter xd

        Draw draw = new Draw();
        draw.draw(xs, list);
       // WindowApp.main(args);

    }
}
