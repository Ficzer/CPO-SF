package SF;

import java.util.*;


public class App
{
    public static void main(String[] args) {
        System.out.println("XDD");
        Generator generator = new Generator();
        System.out.println(generator.Sinusoidal(1.0, 0.0, 20.0, 10.0, 100));

        // Create Chart
        HashMap<Double, Double> values = generator.Sinusoidal(1.0, 0.0, 20.0, 10.0, 100).getValues();
        double[] xs = new double[values.size()];
        double[] list = new double[values.size()];

        TreeMap<Double, Double> map = new TreeMap<Double, Double>(values);

        int i=0;
        for (Map.Entry<Double,Double> entry: map.entrySet())
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
