package SF;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.knowm.xchart.*;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Map;

public class ButtonHandler
{
    public void handleDraw(ChoiceBox<String> signalChoice, TextField amplitudeField, TextField startingTimeField,
                                  TextField durationTimeField, TextField periodField, TextField fullfilmentField,
                                  TextField samplingField) throws Exception
    {

        String choiceValue = signalChoice.getValue();
        Double amplitude = Double.parseDouble(amplitudeField.getText());
        Double startingTime = Double.parseDouble(startingTimeField.getText());
        Double durationTime = Double.parseDouble(durationTimeField.getText());
        Double period = Double.parseDouble(periodField.getText());
        Double fullfilment = Double.parseDouble(fullfilmentField.getText());
        Integer sampling = Integer.parseInt(samplingField.getText());

        Generator generator = new Generator();
        Signal signal;


        switch (choiceValue) {
            case "Noise":
                signal = generator.UniformNoise(amplitude, startingTime, durationTime, sampling);
                Draw(signal);
                break;

            case "Gausian Noise":
                signal = generator.GaussianNoise(amplitude, startingTime, durationTime, sampling);
                Draw(signal);
                break;

            case "Sinusoida":
                signal = generator.Sinusoidal(amplitude, startingTime, durationTime, period, sampling);
                Draw(signal);
                break;

            case "Erected Sidusoida":
                signal = generator.ErectedSinusoidal(amplitude, startingTime, durationTime, period, sampling);
                Draw(signal);
                break;

            case "Two Said Erected Sinusoida":
                signal = generator.ErectedSinusoidalTwoParts(amplitude, startingTime, durationTime, period, sampling);
                Draw(signal);
                break;

            case "Rectangle signal":
                signal = generator.Rectangular(amplitude, startingTime, durationTime, period, fullfilment, sampling);
                Draw(signal);
                break;

            case "Simetrical Rectangular signal":
                signal = generator.RectangularSimetrical(amplitude, startingTime, durationTime, period, fullfilment, sampling);
                Draw(signal);
                break;

            case "Trangular signal":
                signal = generator.Triangular(amplitude, startingTime, durationTime, period, fullfilment, sampling);
                Draw(signal);
                break;

            case "Unit Jump":
                signal = generator.UnitJump(amplitude, startingTime, durationTime, sampling);
                Draw(signal);
                break;
        }

    }

    public void handleSave(ChoiceBox<String> signalChoice, TextField amplitudeField, TextField startingTimeField,
                           TextField durationTimeField, TextField periodField, TextField fullfilmentField,
                           TextField samplingField) throws Exception
    {

        String choiceValue = signalChoice.getValue();
        Double amplitude = Double.parseDouble(amplitudeField.getText());
        Double startingTime = Double.parseDouble(startingTimeField.getText());
        Double durationTime = Double.parseDouble(durationTimeField.getText());
        Double period = Double.parseDouble(periodField.getText());
        Double fullfilment = Double.parseDouble(fullfilmentField.getText());
        Integer sampling = Integer.parseInt(samplingField.getText());

        Generator generator = new Generator();
        Signal signal;


        switch (choiceValue) {
            case "Noise":
                signal = generator.UniformNoise(amplitude, startingTime, durationTime, sampling);
                Save(signal);
                break;

            case "Gausian Noise":
                signal = generator.GaussianNoise(amplitude, startingTime, durationTime, sampling);
                Save(signal);
                break;

            case "Sinusoida":
                signal = generator.Sinusoidal(amplitude, startingTime, durationTime, period, sampling);
                Save(signal);
                break;

            case "Erected Sidusoida":
                signal = generator.ErectedSinusoidal(amplitude, startingTime, durationTime, period, sampling);
                Save(signal);
                break;

            case "Two Said Erected Sinusoida":
                signal = generator.ErectedSinusoidalTwoParts(amplitude, startingTime, durationTime, period, sampling);
                Save(signal);
                break;

            case "Rectangle signal":
                signal = generator.Rectangular(amplitude, startingTime, durationTime, period, fullfilment, sampling);
                Save(signal);
                break;

            case "Simetrical Rectangular signal":
                signal = generator.RectangularSimetrical(amplitude, startingTime, durationTime, period, fullfilment, sampling);
                Save(signal);
                break;

            case "Trangular signal":
                signal = generator.Triangular(amplitude, startingTime, durationTime, period, fullfilment, sampling);
                Save(signal);
                break;

            case "Unit Jump":
                signal = generator.UnitJump(amplitude, startingTime, durationTime, sampling);
                Save(signal);
                break;
        }

    }

    private void Draw(Signal signal)
    {
        Map<Double, Double> values;
        double[] xs, ys;
        int i;

        values = signal.getValues();
        xs = new double[values.size()];
        ys = new double[values.size()];

        i=0;
        for (Map.Entry<Double,Double> entry: values.entrySet())
        {
            xs[i] = entry.getKey();
            ys[i] = entry.getValue();
            i++;
        }
        XYChart chart = QuickChart.getChart(signal.getName(), "X", "Y", "y(x)", xs, ys);
        new SwingWrapper(chart).displayChart().setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        CategoryChart histogram = new CategoryChartBuilder().width(1600).height(800).title("Histogram").xAxisTitle("X").yAxisTitle("Y").build();

        Integer [] counter = countHistogram(ys, signal.getAmplitude());
        histogram.addSeries("test", Arrays.asList(countXAxis(signal.getAmplitude())), Arrays.asList(counter));
        new SwingWrapper<CategoryChart>(histogram).displayChart().setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

    }

    private void Save(Signal signal)
    {
        XMLConverter xmlConverter = new XMLConverter();

        try
        {
            xmlConverter.Serialize(signal.getName() + ".xml", signal);

            FileOutputStream file = new FileOutputStream(signal.getName() + ".bin");
            ObjectOutputStream out = new ObjectOutputStream(file);

            out.writeObject(signal);

            out.close();
            file.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private Integer[] countHistogram(double [] ys, double amplitude)
    {
        Integer[] result = new Integer[20];
        for(int i=0;i<result.length;i++)
            result[i] = 0;
        boolean isNegative = false;

        for(int i=0; i<ys.length; i++)
        {
            if(ys[i] >= -amplitude && ys[i] < -9*amplitude/10.0)
                result[0]++;
            if(ys[i] >= -9*amplitude/10.0 && ys[i] < -8*amplitude/10.0)
                result[1]++;
            if(ys[i] >= -8*amplitude/10.0 && ys[i] < -7*amplitude/10.0)
                result[2]++;
            if(ys[i] >= -7*amplitude/10.0 && ys[i] < -6*amplitude/10.0)
                result[3]++;
            if(ys[i] >= -6*amplitude/10.0 && ys[i] < -5*amplitude/10.0)
                result[4]++;
            if(ys[i] >= -5*amplitude/10.0 && ys[i] < -4*amplitude/10.0)
                result[5]++;
            if(ys[i] >= -4*amplitude/10.0 && ys[i] < -3*amplitude/10.0)
                result[6]++;
            if(ys[i] >= -3*amplitude/10.0 && ys[i] < -2*amplitude/10.0)
                result[7]++;
            if(ys[i] >= -2*amplitude/10.0 && ys[i] < -1*amplitude/10.0)
                result[8]++;
            if(ys[i] >= -1*amplitude/10.0 && ys[i] < 0*amplitude/10.0)
                result[9]++;
            if(ys[i] >= 0.0 && ys[i] < amplitude/10.0)
                result[10]++;
            if(ys[i] >= amplitude/10.0 && ys[i] < 2*amplitude/10.0)
                result[11]++;
            if(ys[i] >= 2*amplitude/10.0 && ys[i] < 3*amplitude/10.0)
                result[12]++;
            if(ys[i] >= 3*amplitude/10.0 && ys[i] < 4*amplitude/10.0)
                result[13]++;
            if(ys[i] >= 4*amplitude/10.0 && ys[i] < 5*amplitude/10.0)
                result[14]++;
            if(ys[i] >= 5*amplitude/10.0 && ys[i] < 6*amplitude/10.0)
                result[15]++;
            if(ys[i] >= 6*amplitude/10.0 && ys[i] < 7*amplitude/10.0)
                result[16]++;
            if(ys[i] >= 7*amplitude/10.0 && ys[i] < 8*amplitude/10.0)
                result[17]++;
            if(ys[i] >= 8*amplitude/10.0 && ys[i] < 9*amplitude/10.0)
                result[18]++;
            if(ys[i] >= 9*amplitude/10.0 && ys[i] <= 10*amplitude/10.0)
                result[19]++;
            }

        return result;
    }

    private String[] countXAxis(double amplitude)
    {
        return new String[] {String.valueOf(-amplitude) + " - " + String.valueOf(-0.9*amplitude),
                String.valueOf(-0.9*amplitude) + " - " + String.valueOf(-0.8*amplitude),
                String.valueOf(-0.8*amplitude) + " - " + String.valueOf(-0.7*amplitude),
                String.valueOf(-0.7*amplitude) + " - " + String.valueOf(-0.6*amplitude),
                String.valueOf(-0.6*amplitude) + " - " + String.valueOf(-0.5*amplitude),
                String.valueOf(-0.5*amplitude) + " - " + String.valueOf(-0.4*amplitude),
                String.valueOf(-0.4*amplitude) + " - " + String.valueOf(-0.3*amplitude),
                String.valueOf(-0.3*amplitude) + " - " + String.valueOf(-0.2*amplitude),
                String.valueOf(-0.2*amplitude) + " - " + String.valueOf(-0.1*amplitude),
                String.valueOf(-0.1*amplitude) + " - " + String.valueOf(-0.0*amplitude),
                String.valueOf(-0.0*amplitude) + " - " + String.valueOf(0.1*amplitude),
                String.valueOf(0.1*amplitude) + " - " + String.valueOf(0.2*amplitude),
                String.valueOf(0.2*amplitude) + " - " + String.valueOf(0.3*amplitude),
                String.valueOf(0.3*amplitude) + " - " + String.valueOf(0.4*amplitude),
                String.valueOf(0.4*amplitude) + " - " + String.valueOf(0.5*amplitude),
                String.valueOf(0.5*amplitude) + " - " + String.valueOf(0.6*amplitude),
                String.valueOf(0.6*amplitude) + " - " + String.valueOf(0.7*amplitude),
                String.valueOf(0.7*amplitude) + " - " + String.valueOf(0.8*amplitude),
                String.valueOf(0.8*amplitude) + " - " + String.valueOf(0.9*amplitude),
                String.valueOf(0.9*amplitude) + " - " + String.valueOf(amplitude),
                };
    }


}

