package SF;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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


}

