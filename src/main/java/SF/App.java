package SF;

import java.io.IOException;
import java.util.*;


public class App
{
    public static void main(String[] args)
    {

        Generator generator = new Generator();
        // Create Chart
        Map<Double, Double> values = generator.Triangular(1.0, 0.0, 20.0, 10.0, 0.1, 500).getValues();
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

        XMLConverter xmlConverter = new XMLConverter();

        Signal signal = generator.Sinusoidal(1.0, 0.0, 20.0, 10.0, 100);
        try {
            xmlConverter.Serialize("testSin.xml", signal);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Signal signal2 = xmlConverter.Deserialize("testSin.xml");
            System.out.println(signal2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // WindowApp.main(args);

    }
}
