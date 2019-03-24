package SF.GUI;

import SF.*;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.knowm.xchart.*;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Map;

public class ButtonHandler
{
    public void handleDraw(ChoiceBox<String> signalChoice, TextField amplitudeField, TextField startingTimeField,
						   TextField durationTimeField, TextField periodField, TextField fullfilmentField,
						   TextField samplingField, TextField histogramElements) throws Exception
    {

        String choiceValue = signalChoice.getValue();
        Double amplitude = Double.parseDouble(amplitudeField.getText());
        Double startingTime = Double.parseDouble(startingTimeField.getText());
        Double durationTime = Double.parseDouble(durationTimeField.getText());
        Double period = Double.parseDouble(periodField.getText());
        Double fillFactor = Double.parseDouble(fullfilmentField.getText());
        Integer sampling = Integer.parseInt(samplingField.getText());
		Integer intervals = Integer.parseInt(histogramElements.getText());

		Generator generator = new Generator();
        Signal signal;


        switch (choiceValue) {
            case "Noise":
                signal = generator.UniformNoise(amplitude, startingTime, durationTime, sampling);
                draw(signal, intervals, false);
                break;

            case "Gaussian noise":
                signal = generator.GaussianNoise(amplitude, startingTime, durationTime, sampling);
				draw(signal, intervals, false);

            case "Sine Wave":
                signal = generator.Sinusoidal(amplitude, startingTime, durationTime, period, sampling);
				draw(signal, intervals, false);
                break;

            case "Half-wave rectified sine":
                signal = generator.ErectedSinusoidal(amplitude, startingTime, durationTime, period, sampling);
				draw(signal, intervals, false);
                break;

            case "Full-wave rectified sine":
                signal = generator.ErectedSinusoidalTwoParts(amplitude, startingTime, durationTime, period, sampling);
				draw(signal, intervals, false);
                break;

            case "Square wave":
                signal = generator.Rectangular(amplitude, startingTime, durationTime, period, fillFactor, sampling);
				draw(signal, intervals, false);
                break;

            case "Symmetrical Rectangular signal":
                signal = generator.RectangularSimetrical(amplitude, startingTime, durationTime, period, fillFactor, sampling);
				draw(signal, intervals, false);
                break;

            case "Triangular wave":
                signal = generator.Triangular(amplitude, startingTime, durationTime, period, fillFactor, sampling);
				draw(signal, intervals, false);
                break;

            case "Step function":
                signal = generator.UnitJump(amplitude, startingTime, durationTime, sampling);
				draw(signal, intervals, false);
                break;
        }

    }

    public void handleDiscreteDraw(ChoiceBox<String> signalChoice, TextField amplitudeField, TextField startingTimeField,
                                   TextField durationTimeField, TextField impulsePositionField, TextField probabilityField,
                                   TextField samplingField, TextField nameField, TextField intervalsField)
    {
        String choiceValue = signalChoice.getValue();
        Double amplitude = Double.parseDouble(amplitudeField.getText());
        Double startingTime = Double.parseDouble(startingTimeField.getText());
        Double durationTime = Double.parseDouble(durationTimeField.getText());
        Integer impulsePosition = Integer.parseInt(impulsePositionField.getText());
        Double probability = Double.parseDouble(probabilityField.getText());
        Integer sampling = Integer.parseInt(samplingField.getText());
        Integer intervals = Integer.parseInt(intervalsField.getText());

        Generator generator = new Generator();
        Signal signal = null;

        switch (choiceValue) {
            case "Unit Impulse":
                signal = generator.UnitImpulse(amplitude, startingTime, durationTime, impulsePosition, sampling);
                signal.setName(nameField.getText());
                draw(signal, intervals, true);
                break;

            case "Impulse Noise":
                try
                {
                    signal = generator.ImpulseNoise(amplitude, startingTime, durationTime, probability, sampling);
                } catch (WrongProbabilityFormatException e)
                {
                    AlertBox.display("Wrong Format", "Wrong probability format. /r/n Please use value between 0 and 1");
                    e.printStackTrace();
                }
                signal.setName(nameField.getText());
                draw(signal, intervals, true);
                break;
        }
    }

    public void handleSave(ChoiceBox<String> signalChoice, TextField amplitudeField, TextField startingTimeField,
                           TextField durationTimeField, TextField periodField, TextField fullfilmentField,
                           TextField samplingField, TextField nameField) throws Exception
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
                signal.setName(nameField.getText());
                save(signal);
                break;

            case "Gaussian noise":
                signal = generator.GaussianNoise(amplitude, startingTime, durationTime, sampling);
                signal.setName(nameField.getText());
                save(signal);
                break;

            case "Sine Wave":
                signal = generator.Sinusoidal(amplitude, startingTime, durationTime, period, sampling);
                signal.setName(nameField.getText());
                save(signal);
                break;

            case "Half-wave rectified sine":
                signal = generator.ErectedSinusoidal(amplitude, startingTime, durationTime, period, sampling);
                signal.setName(nameField.getText());
                save(signal);
                break;

            case "Full-wave rectified sine":
                signal = generator.ErectedSinusoidalTwoParts(amplitude, startingTime, durationTime, period, sampling);
                signal.setName(nameField.getText());
                save(signal);
                break;

            case "Square wave":
                signal = generator.Rectangular(amplitude, startingTime, durationTime, period, fullfilment, sampling);
                signal.setName(nameField.getText());
                save(signal);
                break;

            case "Symmetrical Rectangular signal":
                signal = generator.RectangularSimetrical(amplitude, startingTime, durationTime, period, fullfilment, sampling);
                signal.setName(nameField.getText());
                save(signal);
                break;

            case "Triangular wave":
                signal = generator.Triangular(amplitude, startingTime, durationTime, period, fullfilment, sampling);
                signal.setName(nameField.getText());
                save(signal);
                break;

            case "Step function":
                signal = generator.UnitJump(amplitude, startingTime, durationTime, sampling);
                signal.setName(nameField.getText());
                save(signal);
                break;
        }

    }

    public void handleDiscreteSave(ChoiceBox<String> signalChoice, TextField amplitudeField, TextField startingTimeField,
                                   TextField durationTimeField, TextField impulsePositionField, TextField probabilityField,
                                   TextField samplingField, TextField nameField, TextField intervalsField) throws Exception {
        String choiceValue = signalChoice.getValue();
        Double amplitude = Double.parseDouble(amplitudeField.getText());
        Double startingTime = Double.parseDouble(startingTimeField.getText());
        Double durationTime = Double.parseDouble(durationTimeField.getText());
        Integer impulsePosition = Integer.parseInt(impulsePositionField.getText());
        Double probability = Double.parseDouble(probabilityField.getText());
        Integer sampling = Integer.parseInt(samplingField.getText());
        Integer intervals = Integer.parseInt(intervalsField.getText());

        Generator generator = new Generator();
        Signal signal = null;

        switch (choiceValue) {
            case "Unit Impulse":
                signal = generator.UnitImpulse(amplitude, startingTime, durationTime, impulsePosition, sampling);
                signal.setName(nameField.getText());
                save(signal);
                break;

            case "Impulse Noise":
                try
                {
                    signal = generator.ImpulseNoise(amplitude, startingTime, durationTime, probability, sampling);
                } catch (WrongProbabilityFormatException e)
                {
                    AlertBox.display("Wrong Format", "Wrong probability format. /r/n Please use value between 0 and 1");
                    e.printStackTrace();
                }
                signal.setName(nameField.getText());
                save(signal);
                break;
        }
    }

    public void handleShowData(ChoiceBox<String> signalChoice, TextField amplitudeField, TextField startingTimeField,
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
                AlertBox.display("Data", generateData(signal));
                break;

            case "Gaussian noise":
                signal = generator.GaussianNoise(amplitude, startingTime, durationTime, sampling);
                AlertBox.display("Data", generateData(signal));
                break;

            case "Sine Wave":
                signal = generator.Sinusoidal(amplitude, startingTime, durationTime, period, sampling);
                AlertBox.display("Data", generateData(signal));
                break;

            case "Half-wave rectified sine":
                signal = generator.ErectedSinusoidal(amplitude, startingTime, durationTime, period, sampling);
                AlertBox.display("Data", generateData(signal));
                break;

            case "Full-wave rectified sine":
                signal = generator.ErectedSinusoidalTwoParts(amplitude, startingTime, durationTime, period, sampling);
                AlertBox.display("Data", generateData(signal));
                break;

            case "Square wave":
                signal = generator.Rectangular(amplitude, startingTime, durationTime, period, fullfilment, sampling);
                AlertBox.display("Data", generateData(signal));
                break;

            case "Symmetrical Rectangular signal":
                signal = generator.RectangularSimetrical(amplitude, startingTime, durationTime, period, fullfilment, sampling);
                AlertBox.display("Data", generateData(signal));
                break;

            case "Triangular wave":
                signal = generator.Triangular(amplitude, startingTime, durationTime, period, fullfilment, sampling);
                AlertBox.display("Data", generateData(signal));
                break;

            case "Step function":
                signal = generator.UnitJump(amplitude, startingTime, durationTime, sampling);
                AlertBox.display("Data", generateData(signal));
                break;
        }

    }

    public void handleDiscreteShowData(ChoiceBox<String> signalChoice, TextField amplitudeField, TextField startingTimeField,
                                   TextField durationTimeField, TextField impulsePositionField, TextField probabilityField,
                                   TextField samplingField, TextField nameField, TextField intervalsField) throws Exception {
        String choiceValue = signalChoice.getValue();
        Double amplitude = Double.parseDouble(amplitudeField.getText());
        Double startingTime = Double.parseDouble(startingTimeField.getText());
        Double durationTime = Double.parseDouble(durationTimeField.getText());
        Integer impulsePosition = Integer.parseInt(impulsePositionField.getText());
        Double probability = Double.parseDouble(probabilityField.getText());
        Integer sampling = Integer.parseInt(samplingField.getText());
        Integer intervals = Integer.parseInt(intervalsField.getText());

        Generator generator = new Generator();
        Signal signal = null;

        switch (choiceValue) {
            case "Unit Impulse":
                signal = generator.UnitImpulse(amplitude, startingTime, durationTime, impulsePosition, sampling);
                signal.setName(nameField.getText());
                AlertBox.display("Data", generateData(signal));
                break;

            case "Impulse Noise":
                try
                {
                    signal = generator.ImpulseNoise(amplitude, startingTime, durationTime, probability, sampling);
                } catch (WrongProbabilityFormatException e)
                {
                    AlertBox.display("Wrong Format", "Wrong probability format. /r/n Please use value between 0 and 1");
                    e.printStackTrace();
                }
                signal.setName(nameField.getText());
                AlertBox.display("Data", generateData(signal));
                break;
        }
    }

    public void draw(Signal signal, int intervals, boolean isDiscreet)
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
        CategoryChart histogram = new CategoryChartBuilder().width(1200).title("Histogram").xAxisTitle("X").yAxisTitle("Y").build();

		double min = Arrays.stream(ys).min().getAsDouble();
		double max = Arrays.stream(ys).max().getAsDouble();

        Integer [] counter = countHistogram(ys, signal.getAmplitude(), intervals, min, max);
        histogram.addSeries("test", Arrays.asList(countXAxis(intervals, signal.getAmplitude(), min, max)), Arrays.asList(counter));
        new SwingWrapper<CategoryChart>(histogram).displayChart().setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        if(isDiscreet)
        {
            XYChart chart = new XYChartBuilder().width(800).height(600).title("test").xAxisTitle("X").yAxisTitle("Y").build();
            chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);
            chart.getStyler().setMarkerSize(5);
            chart.addSeries("X", xs, ys);
            new SwingWrapper(chart).displayChart().setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        }
        else
            {
            XYChart chart = QuickChart.getChart(signal.getName(), "X", "Y", "y(x)", xs, ys);
            new SwingWrapper(chart).displayChart().setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        }

    }

    public void save(Signal signal) throws EmptyFileNameException
    {
        XMLConverter xmlConverter = new XMLConverter();

        try
        {
        	if(signal.getName().equals("") || signal.getName() == null){
        		throw new EmptyFileNameException("File name cannot be empty");
			}
            xmlConverter.Serialize("./signals/" + signal.getName() + ".xml", signal);

            FileOutputStream file = new FileOutputStream("./signals/" + signal.getName() + ".bin");
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

    private Integer[] countHistogram(double [] ys, double amplitude, int elements, double min, double max)
    {
        Integer[] result = new Integer[elements];
        for(int i=0;i<result.length;i++)
            result[i] = 0;

		double k = (max - min)/elements;
       // double k =  2 * (amplitude / elements);
        double it = 0;
        double epsilon = 0.001;
        for(int i = 0; i<ys.length; i++){

        	for(int j = 0; j<elements; j++){
        		if(ys[i] >=  min + it && ys[i] < min + it + k + epsilon){
					//System.out.println(ys[i] + " " + (temp + it) + " " + (temp + k + it) + " " + amplitude);
        			result[j]++;
				}

				it += k;
			}

			it = 0;
		}



        for(int i=0; i<ys.length; i++)
        {
         /*   if(ys[i] >= -amplitude && ys[i] < -9*amplitude/10.0)
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
                result[19]++; */
            }

        return result;
    }

    private String[] countXAxis(int elements, double amplitude, double min, double max)
    {
		double k = (max - min)/elements;
    	double it = 0;
    	String[] arr = new String[elements];
		DecimalFormat format = new DecimalFormat("0.0");
    	for(int i = 0; i<elements; i++){
    		double start = min + it;
    		double stop = min + it + k;
    		arr[i] = format.format(start) + "-" + format.format(stop);
    		it += k;
		}
		return arr;
       /* return new String[] {String.valueOf(-amplitude) + " - " + String.valueOf(-0.9*amplitude),
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
                }; */
    }

    public String generateData(Signal signal)
    {
        CalculationHelper calculationHelper = new CalculationHelper();
        String result = new String();

        result += "Average: " + calculationHelper.Average(signal) + "\r\n";
        result += "Absolute Average: " + calculationHelper.AbsoluteAverage(signal) + "\r\n";
        result += "Mean power: " + calculationHelper.Strength(signal) + "\r\n";
        result += "Variance: " + calculationHelper.Variance(signal) + "\r\n";
        result += "Root Mean Square: " + calculationHelper.RootMeanSquare(signal) + "\r\n";

        return result;
    }


}

