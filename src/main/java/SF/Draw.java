package SF;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import javax.swing.*;

public class Draw {

    public JFrame draw(double[] x, double[] y){
        XYChart chart = QuickChart.getChart("Sample Chart", "X", "Y", "y(x)", x, y);
        return new SwingWrapper(chart).displayChart();
    }
}
