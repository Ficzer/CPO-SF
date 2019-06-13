package SF.GUI;

import SF.EmptyFileNameException;
import SF.Signal;
import SF.Transforms.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;

public class TransformWindow {

    Signal signalToTransform = new Signal();

    public void display()
    {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Transform");

        final FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("./signals"));
        Button openFileButton = new Button("Open file");
        Button generateSignalButton = new Button("Generate signal");
        Button drawSignal = new Button("Draw Signal");
        Button saveSignal = new Button("Save signal");

        ChoiceBox<String> transformChoice = new ChoiceBox<>();
        transformChoice.getItems().addAll("Fourier Transform", "Fast Fourier Transform", "Cosine Transform", "Fast Cosine Transform");
        transformChoice.setValue("Fourier Transform");
        ChoiceBox<String> chartChoice = new ChoiceBox<>();
        chartChoice.getItems().addAll("W1 charts", "W2 charts");
        chartChoice.setValue("W1 charts");

        Label fileLabel = new Label("File");
        Label transformLabel = new Label("Transform");
        Label chartModeLabel = new Label("Chart Mode");
        Label timeLabel = new Label("Generation time");
        Label elapsedTimeLabel = new Label();


        openFileButton.setMinWidth(100);

        openFileButton.setOnAction(e ->
        {
            File file = fileChooser.showOpenDialog(window);

            if(file != null)
            {
                FileInputStream fileInputStream = null;
                try
                {
                    fileInputStream = new FileInputStream(file);
                    ObjectInputStream in = new ObjectInputStream(fileInputStream);
                    signalToTransform.copy((Signal)in.readObject());
                }
                catch (FileNotFoundException e1)
                {
                    e1.printStackTrace();
                }
                catch (StreamCorruptedException e1)
                {
                    e1.printStackTrace();
                    AlertBox.display("Wrong file", "Wrong file Format");
                }
                catch (ClassNotFoundException e1)
                {
                    e1.printStackTrace();
                    AlertBox.display("Wrong file", "Wrong file Format");
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }

                fileLabel.setText(signalToTransform.getName());
            }
        });

        generateSignalButton.setOnAction(e -> {
            Transform transform;

            long startingTime = System.nanoTime();

            switch (transformChoice.getValue())
            {
                case "Fourier Transform":
                    transform = new DiscreteFourierTransform();
                    signalToTransform = transform.transform(signalToTransform);
                    break;
                case "Fast Fourier Transform":
                    transform = new DecimationInFrequencyFFT();
                    signalToTransform = transform.transform(signalToTransform);
                    break;
                case "Cosine Transform":
                    transform = new CosineTransform();
                    signalToTransform = transform.transform(signalToTransform);
                    break;
                case "Fast Cosine Transform":
                    transform = new FastCosineTransform();
                    signalToTransform = transform.transform(signalToTransform);
                    break;
            }

            elapsedTimeLabel.setText(String.valueOf((System.nanoTime() - startingTime)/ 1000000) + " ms");

        });

        drawSignal.setOnAction(e -> {

            ComplexChartsGenerator complexChartsGenerator = new ComplexChartsGenerator();


            switch (chartChoice.getValue())
            {
                case "W1 charts":
                    try {
                        complexChartsGenerator.drawW1(signalToTransform);

                    } catch (NotAComplexSignalException e1) {
                        AlertBox.display("Not complex", "Signal is not complex");
                        e1.printStackTrace();
                    }
                    break;
                case "W2 charts":
                    try {
                        complexChartsGenerator.drawW2(signalToTransform);
                    } catch (NotAComplexSignalException e1) {
                        AlertBox.display("Not complex", "Signal is not complex");
                        e1.printStackTrace();
                    }
                    break;
            }
        });

        saveSignal.setOnAction(e -> {
            ButtonHandler buttonHandler = new ButtonHandler();
            try {
                buttonHandler.save(signalToTransform);
            } catch (EmptyFileNameException e1) {
                e1.printStackTrace();
            }
        });





        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(12);
        grid.setHgap(12);

        GridPane.setConstraints(openFileButton, 0, 0);
        GridPane.setConstraints(fileLabel, 1, 0);
        GridPane.setConstraints(transformChoice, 1, 1);
        GridPane.setConstraints(chartChoice, 1 ,2);
        GridPane.setConstraints(generateSignalButton, 0, 3);
        GridPane.setConstraints(drawSignal, 0, 4);
        GridPane.setConstraints(saveSignal, 0 ,5);
        GridPane.setConstraints(transformLabel, 0, 1);
        GridPane.setConstraints(chartModeLabel, 0 ,2);
        GridPane.setConstraints(timeLabel, 0 , 6);
        GridPane.setConstraints(elapsedTimeLabel, 1, 6);

        grid.getChildren().addAll(openFileButton, fileLabel, generateSignalButton, drawSignal, saveSignal,
                transformChoice, chartChoice, transformLabel, chartModeLabel, timeLabel, elapsedTimeLabel);

        window.setScene(new Scene(grid, 350, 450));
        window.show();
    }
}
