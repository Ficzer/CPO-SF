package SF;

import java.util.List;

public class App
{

    public static void main(String[] args) {
        System.out.println("XDD");
        Generator generator = new Generator();
        System.out.println(generator.ErectedSinusoidal(1.0, 0.0, 10.0, 2.0, 100));

        // Create Chart
        List<Double> values = generator.Sinusoidal(5.0, 0.0, 10.0, 3.0, 100).getValues();
        double[] xs = new double[values.size()];
        double[] list = new double[values.size()];
        for(int i = 0; i<list.length; i++){
            list[i] = values.get(i);
            xs[i] = i;
        }

        Draw draw = new Draw();
        draw.draw(xs, list);
       // WindowApp.main(args);

    }
}
