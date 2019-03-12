package SF;

import java.util.*;


public class App
{
    public static void main(String[] args) {
        System.out.println("XDD");
        Generator generator = new Generator();
        System.out.println(generator.Sinusoidal(1.0, 0.0, 20.0, 10.0, 100));

        // Create Chart
        HashMap<Double, Double> values = generator.Rectangular(1.0, 0.0, 40.0, 10.0, 0.5,  300).getValues();
        double[] xs = new double[values.size()];
        double[] list = new double[values.size()];

        // As i see it we have to sort hash map by key because it's not sortet idk why xd, i tried but i could not do it

        int i=0;
        for (Map.Entry<Double,Double> entry: values.entrySet())
        {
            xs[i] = entry.getKey();
            list[i] = entry.getValue();
            i++;
        } // TODO fix god damn ploter xd, in hashmap key is time and value is value

        Draw draw = new Draw();
        draw.draw(xs, list);
       // WindowApp.main(args);

    }
}
