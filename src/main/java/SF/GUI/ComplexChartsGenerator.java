package SF.GUI;

import SF.Signal;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYSeries;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ComplexChartsGenerator {

    public void drawW1(Signal complexSignal) throws NotAComplexSignalException {

        if(complexSignal.getImaginaryValues().size() == 0)
            throw new NotAComplexSignalException("Signal is not complex");

        Map<Double, Double> realValues, imaginaryValues;
        double[] xs, ys, xss, yss;
        int i = 0;
        realValues = complexSignal.getValues();
        imaginaryValues = complexSignal.getImaginaryValues();

        xs = new double[realValues.size()];
        ys = new double[realValues.size()];
        xss = new double[imaginaryValues.size()];
        yss = new double[imaginaryValues.size()];

        i = 0;
        for (Map.Entry<Double, Double> entry : realValues.entrySet()) {
            xs[i] = entry.getKey();
            ys[i] = entry.getValue();
            i++;
        }

        i = 0;
        for (Map.Entry<Double, Double> entry : imaginaryValues.entrySet()) {
            xss[i] = entry.getKey();
            yss[i] = entry.getValue();
            i++;
        }

        XYChart realChart = new XYChart(9000, 9000);
        XYChart imaginaryChart = new XYChart(9000, 9000);
        realChart.addSeries("Real", xs, ys).setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);
        imaginaryChart.addSeries("Imaginary", xss, yss).setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);
        new SwingWrapper(realChart).displayChart().setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        new SwingWrapper(imaginaryChart).displayChart().setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    public void drawW2(Signal complexSignal) throws NotAComplexSignalException {

        if(complexSignal.getImaginaryValues().size() == 0)
            throw new NotAComplexSignalException("Signal is not complex");

        Map<Double, Double> realValues, imaginaryValues;
        double[] xs, ys, xss, yss;
        realValues = complexSignal.getValues();
        imaginaryValues = complexSignal.getImaginaryValues();

        List<Double> realValuesList = new ArrayList<>(realValues.values());
        List<Double> imaginaryValuesList = new ArrayList<>(imaginaryValues.values());
        List<Double> timeValues = new ArrayList<>(realValues.keySet());

        xs = new double[realValues.size()];
        ys = new double[realValues.size()];
        xss = new double[imaginaryValues.size()];
        yss = new double[imaginaryValues.size()];

        for(int i=0; i<realValues.size(); i++)
        {
            ys[i] = Math.sqrt(realValuesList.get(i) * realValuesList.get(i) + imaginaryValuesList.get(i) * imaginaryValuesList.get(i));

            if(realValuesList.get(i) > 0)
                yss[i] = Math.atan(imaginaryValuesList.get(i) / realValuesList.get(i));
            else
                yss[i] = Math.atan(imaginaryValuesList.get(i) / realValuesList.get(i)) + Math.PI;

            xs[i] = timeValues.get(i);
            xss[i] = timeValues.get(i);
        }

        XYChart realChart = new XYChart(9000, 9000);
        XYChart imaginaryChart = new XYChart(9000, 9000);
        realChart.addSeries("Modulus", xs, ys).setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);
        imaginaryChart.addSeries("Arg", xss, yss).setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);
        new SwingWrapper(realChart).displayChart().setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        new SwingWrapper(imaginaryChart).displayChart().setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
}
