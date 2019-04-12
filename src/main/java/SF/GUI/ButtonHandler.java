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

public class ButtonHandler {
	public void handleDraw(ChoiceBox<String> signalChoice, TextField amplitudeField, TextField startingTimeField,
						   TextField durationTimeField, TextField periodField, TextField fullfilmentField,
						   TextField samplingField, TextField histogramElements) throws Exception {

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
				draw(signal, intervals, false, false);
				break;

			case "Gaussian noise":
				signal = generator.GaussianNoise(amplitude, startingTime, durationTime, sampling);
				draw(signal, intervals, false, false);
				break;

			case "Sine Wave":
				signal = generator.Sinusoidal(amplitude, startingTime, durationTime, period, sampling, true);
				draw(signal, intervals, false, true);
				break;

			case "Half-wave rectified sine":
				signal = generator.ErectedSinusoidal(amplitude, startingTime, durationTime, period, sampling, true);
				draw(signal, intervals, false, true);
				break;

			case "Full-wave rectified sine":
				signal = generator.ErectedSinusoidalTwoParts(amplitude, startingTime, durationTime, period, sampling, true);
				draw(signal, intervals, false, true);
				break;

			case "Square wave":
				signal = generator.Rectangular(amplitude, startingTime, durationTime, period, fillFactor, sampling, true);
				draw(signal, intervals, false, true);
				break;

			case "Symmetrical Rectangular signal":
				signal = generator.RectangularSimetrical(amplitude, startingTime, durationTime, period, fillFactor, sampling, true);
				draw(signal, intervals, false, true);
				break;

			case "Triangular wave":
				signal = generator.Triangular(amplitude, startingTime, durationTime, period, fillFactor, sampling, true);
				draw(signal, intervals, false, true);
				break;

			case "Step function":
				signal = generator.UnitJump(amplitude, startingTime, durationTime, sampling);
				draw(signal, intervals, false, true);
				break;
		}

	}

	public void handleDiscreteDraw(ChoiceBox<String> signalChoice, TextField amplitudeField, TextField startingTimeField,
								   TextField durationTimeField, TextField impulsePositionField, TextField probabilityField,
								   TextField samplingField, TextField nameField, TextField intervalsField) {
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
				draw(signal, intervals, true, true);
				break;

			case "Impulse Noise":
				try {
					signal = generator.ImpulseNoise(amplitude, startingTime, durationTime, probability, sampling);
				} catch (WrongProbabilityFormatException e) {
					AlertBox.display("Wrong Format", "Wrong probability format. /r/n Please use value between 0 and 1");
					e.printStackTrace();
				}
				signal.setName(nameField.getText());
				draw(signal, intervals, true, true);
				break;
		}
	}

	public void handleSave(ChoiceBox<String> signalChoice, TextField amplitudeField, TextField startingTimeField,
						   TextField durationTimeField, TextField periodField, TextField fullfilmentField,
						   TextField samplingField, TextField nameField) throws Exception {

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
				signal = generator.Sinusoidal(amplitude, startingTime, durationTime, period, sampling, true);
				signal.setName(nameField.getText());
				save(signal);
				break;

			case "Half-wave rectified sine":
				signal = generator.ErectedSinusoidal(amplitude, startingTime, durationTime, period, sampling, true);
				signal.setName(nameField.getText());
				save(signal);
				break;

			case "Full-wave rectified sine":
				signal = generator.ErectedSinusoidalTwoParts(amplitude, startingTime, durationTime, period, sampling, true);
				signal.setName(nameField.getText());
				save(signal);
				break;

			case "Square wave":
				signal = generator.Rectangular(amplitude, startingTime, durationTime, period, fullfilment, sampling, true);
				signal.setName(nameField.getText());
				save(signal);
				break;

			case "Symmetrical Rectangular signal":
				signal = generator.RectangularSimetrical(amplitude, startingTime, durationTime, period, fullfilment, sampling, true);
				signal.setName(nameField.getText());
				save(signal);
				break;

			case "Triangular wave":
				signal = generator.Triangular(amplitude, startingTime, durationTime, period, fullfilment, sampling, true);
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
				try {
					signal = generator.ImpulseNoise(amplitude, startingTime, durationTime, probability, sampling);
				} catch (WrongProbabilityFormatException e) {
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
							   TextField samplingField) throws Exception {

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
				signal = generator.Sinusoidal(amplitude, startingTime, durationTime, period, sampling, true);
				AlertBox.display("Data", generateData(signal));
				break;

			case "Half-wave rectified sine":
				signal = generator.ErectedSinusoidal(amplitude, startingTime, durationTime, period, sampling, true);
				AlertBox.display("Data", generateData(signal));
				break;

			case "Full-wave rectified sine":
				signal = generator.ErectedSinusoidalTwoParts(amplitude, startingTime, durationTime, period, sampling, true);
				AlertBox.display("Data", generateData(signal));
				break;

			case "Square wave":
				signal = generator.Rectangular(amplitude, startingTime, durationTime, period, fullfilment, sampling, true);
				AlertBox.display("Data", generateData(signal));
				break;

			case "Symmetrical Rectangular signal":
				signal = generator.RectangularSimetrical(amplitude, startingTime, durationTime, period, fullfilment, sampling, true);
				AlertBox.display("Data", generateData(signal));
				break;

			case "Triangular wave":
				signal = generator.Triangular(amplitude, startingTime, durationTime, period, fullfilment, sampling, true);
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
				try {
					signal = generator.ImpulseNoise(amplitude, startingTime, durationTime, probability, sampling);
				} catch (WrongProbabilityFormatException e) {
					AlertBox.display("Wrong Format", "Wrong probability format. /r/n Please use value between 0 and 1");
					e.printStackTrace();
				}
				signal.setName(nameField.getText());
				AlertBox.display("Data", generateData(signal));
				break;
		}
	}

	public void draw(Signal signal, int intervals, boolean isDiscrete, boolean perWindow) {
		Map<Double, Double> values;
		double[] xs, ys;
		int i = 0;
		values = signal.getValues();

		xs = new double[values.size()];
		ys = new double[values.size()];

		i = 0;
		for (Map.Entry<Double, Double> entry : values.entrySet()) {
			xs[i] = entry.getKey();
			ys[i] = entry.getValue();
			i++;
		}

		double min;
		double max;
		CategoryChart histogram = new CategoryChartBuilder().width(1200).title("Histogram").xAxisTitle("X").yAxisTitle("Y").build();
		if (signal.getPeriod() != null && !perWindow) {
			double k = signal.getDurationTime() / signal.getPeriod();
			int size = (int)k * signal.getSampling();
			double[] histogramY = new double[size];
			i = 0;

			System.out.println(size + " " + ys.length);
			histogramY = Arrays.copyOfRange(ys, 0, size + 1);

			min = Arrays.stream(histogramY).min().getAsDouble();
			max = Arrays.stream(histogramY).max().getAsDouble();
			Integer[] counter = countHistogram(histogramY, signal.getAmplitude(), intervals, min, max);
			histogram.addSeries("amount", Arrays.asList(countXAxis(intervals, signal.getAmplitude(), min, max)), Arrays.asList(counter));

		} else {
			min = Arrays.stream(ys).min().getAsDouble();
			max = Arrays.stream(ys).max().getAsDouble();
			Integer[] counter = countHistogram(ys, signal.getAmplitude(), intervals, min, max);
			histogram.addSeries("amount", Arrays.asList(countXAxis(intervals, signal.getAmplitude(), min, max)), Arrays.asList(counter));
		}

		new SwingWrapper<CategoryChart>(histogram).displayChart().setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		if (isDiscrete) {
			XYChart chart = new XYChartBuilder().width(800).height(600).title("").xAxisTitle("X").yAxisTitle("Y").build();
			chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);
			chart.getStyler().setMarkerSize(5);
			chart.addSeries("X", xs, ys);
			new SwingWrapper(chart).displayChart().setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		} else {
			XYChart chart = QuickChart.getChart(signal.getName(), "X", "Y", "y(x)", xs, ys);
			new SwingWrapper(chart).displayChart().setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		}

	}

	public void draw(Signal signal, Signal scateredSignal) {
		Map<Double, Double> values, values2;
		double[] xs, ys, xss, yss;
		int i = 0;
		values = signal.getValues();
		values2 = scateredSignal.getValues();

		xs = new double[values.size()];
		ys = new double[values.size()];
		xss = new double[values2.size()];
		yss = new double[values2.size()];

		i = 0;
		for (Map.Entry<Double, Double> entry : values.entrySet()) {
			xs[i] = entry.getKey();
			ys[i] = entry.getValue();
			i++;
		}

		i = 0;
		for (Map.Entry<Double, Double> entry : values2.entrySet()) {
			xss[i] = entry.getKey();
			yss[i] = entry.getValue();
			i++;
		}

		XYChart chart = QuickChart.getChart(signal.getName(), "X", "Y", signal.getName(), xs, ys);
		chart.addSeries(scateredSignal.getName(), xss, yss).setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);
		new SwingWrapper(chart).displayChart().setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

	}

	public void draw(Signal signal, Signal scateredSignal, Signal analogOriginalSignal) {
		Map<Double, Double> values, values2, values3;
		double[] xs, ys, xss, yss, xsss, ysss;
		int i = 0;
		values = signal.getValues();
		values2 = scateredSignal.getValues();
		values3 = analogOriginalSignal.getValues();

		xs = new double[values.size()];
		ys = new double[values.size()];
		xss = new double[values2.size()];
		yss = new double[values2.size()];
		xsss = new double[values3.size()];
		ysss = new double[values3.size()];

		i = 0;
		for (Map.Entry<Double, Double> entry : values.entrySet()) {
			xs[i] = entry.getKey();
			ys[i] = entry.getValue();
			i++;
		}

		i = 0;
		for (Map.Entry<Double, Double> entry : values2.entrySet()) {
			xss[i] = entry.getKey();
			yss[i] = entry.getValue();
			i++;
		}

		i = 0;
		for (Map.Entry<Double, Double> entry : values3.entrySet()) {
			xsss[i] = entry.getKey();
			ysss[i] = entry.getValue();
			i++;
		}

		XYChart chart = QuickChart.getChart(analogOriginalSignal.getName(), "X", "Y", analogOriginalSignal.getName(), xsss, ysss);
		chart.addSeries(signal.getName(), xs, ys);
		chart.addSeries(scateredSignal.getName(), xss, yss).setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);
		new SwingWrapper(chart).displayChart().setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

	}

	public void save(Signal signal) throws EmptyFileNameException {
		XMLConverter xmlConverter = new XMLConverter();

		try {
			if (signal.getName().equals("") || signal.getName() == null) {
				throw new EmptyFileNameException("File name cannot be empty");
			}
			xmlConverter.Serialize("./signals/" + signal.getName() + ".xml", signal);

			FileOutputStream file = new FileOutputStream("./signals/" + signal.getName() + ".bin");
			ObjectOutputStream out = new ObjectOutputStream(file);

			out.writeObject(signal);

			out.close();
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Integer[] countHistogram(double[] ys, double amplitude, int elements, double min, double max) {
		Integer[] result = new Integer[elements];
		for (int i = 0; i < result.length; i++)
			result[i] = 0;

		double k = (max - min) / elements;
		double it = 0;
		double epsilon = 0.001;
		for (int i = 0; i < ys.length; i++) {
			for (int j = 0; j < elements; j++) {
				if (ys[i] >= min + it && ys[i] < min + it + k + epsilon) {
					result[j]++;
				}

				it += k;
			}

			it = 0;
		}

		return result;
	}

	private String[] countXAxis(int elements, double amplitude, double min, double max) {
		double k = (max - min) / elements;
		double it = 0;
		String[] arr = new String[elements];
		DecimalFormat format = new DecimalFormat("0.0");
		for (int i = 0; i < elements; i++) {
			double start = min + it;
			double stop = min + it + k;
			arr[i] = format.format(start) + "-" + format.format(stop);
			it += k;
		}
		return arr;
	}

	public String generateData(Signal signal) {
		CalculationHelper calculationHelper = new CalculationHelper();
		String result = new String();

		result += "Average: " + calculationHelper.Average(signal) + "\r\n";
		result += "Absolute Average: " + calculationHelper.AbsoluteAverage(signal) + "\r\n";
		result += "Mean power: " + calculationHelper.Strength(signal) + "\r\n";
		result += "Variance: " + calculationHelper.Variance(signal) + "\r\n";
		result += "Root Mean Square: " + calculationHelper.RootMeanSquare(signal) + "\r\n";

		return result;
	}

	public String generateErrorsData(Signal signal, Signal originalSignal)
	{
		ErrorCalculator errorCalculator = new ErrorCalculator();
		String result = new String();

		Double mse, snr, psnr, md;
		mse = errorCalculator.calculateMSE(signal, originalSignal);
		snr = errorCalculator.calculateSNR(signal, originalSignal);
		psnr = errorCalculator.calculatePSNR(signal, originalSignal);
		md = errorCalculator.calculateMD(signal, originalSignal);

		mse = roundError(mse);
		snr = roundError(snr);
		psnr = roundError(psnr);
		md = roundError(md);

		result += "MSE: " + mse + "\r\n";
		result += "SNR: " + snr + "\r\n";
		result += "PSNR: " + psnr + "\r\n";
		result += "MD: " + md + "\r\n";

		return result;
	}

	private Double roundError(Double result)
	{
		result *= 1000;
		result = (double) Math.round(result);
		result /= 1000;

		return result;
	}


}

