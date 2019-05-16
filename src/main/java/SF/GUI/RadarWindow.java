package SF.GUI;

import SF.Radar.RadarSimulator;
import SF.Signal;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.List;

public class RadarWindow
{

    private Signal probingSignal = new Signal();
    private RadarSimulator radarSimulator = null;

    public void display()
    {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Radar");

        final FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("./signals"));
        Button openFileButton = new Button("Open file");
        Button startSimulationButton = new Button("Start simulation");
        Button stopSimulationButton = new Button("Stop simulation");

        TextField objectSpeedField = new TextField("1.0");
        TextField signalSpeedField = new TextField("1000.0");
        TextField startingObjectDistanceField = new TextField("0.0");
        TextField simulationTimeStepField = new TextField("100");
        Label fileLabel = new Label("File");
        Label objectSpeedLabel = new Label(" Object speed");
        Label signalSpeedLabel = new Label(" Signal speed");
        Label startingObjectDistanceLabel = new Label("Starting distance");
        Label simulationTimeStepLabel = new Label("Simulation time step (milisec)");
        Label objectActualDistanceLabel = new Label("0");
        Label objectCalculatedDistanceLabel = new Label("0");
        Label objectActualDistanceLabel2 = new Label("Actual distance");
        Label objectCalculatedDistanceLabel2 = new Label("Calculated distance");

        openFileButton.setMinWidth(100);
        startSimulationButton.setMinWidth(100);
        stopSimulationButton.setMinWidth(100);

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
                    probingSignal.copy((Signal)in.readObject());
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

                fileLabel.setText(probingSignal.getName());
            }
        });


        startSimulationButton.setOnAction(e ->
        {
            try
            {
                radarSimulator = new RadarSimulator(Double.parseDouble(objectSpeedField.getText()),
                        Double.parseDouble(signalSpeedField.getText()),
                        probingSignal,
                        Double.parseDouble(startingObjectDistanceField.getText()),
                        objectActualDistanceLabel,
                        objectCalculatedDistanceLabel,
                        Integer.parseInt(simulationTimeStepField.getText()));
            }
            catch (NumberFormatException e1)
            {
                e1.printStackTrace();
                AlertBox.display("Wrong format", "Wrong format of numbers.");
            }
            catch (NullPointerException e1)
            {
                e1.printStackTrace();
                AlertBox.display("Choose file", "Please choose file to load.");
            }

            radarSimulator.getThread().setDaemon(true);
            radarSimulator.getThread().start();

        });

        stopSimulationButton.setOnAction(e ->
        {
            radarSimulator.stopSimulation();
        });

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(12);
        grid.setHgap(12);

        GridPane.setConstraints(openFileButton, 0,0);
        GridPane.setConstraints(startSimulationButton, 0, 1);
        GridPane.setConstraints(stopSimulationButton, 0, 2);
        GridPane.setConstraints(fileLabel, 1, 0);
        GridPane.setConstraints(simulationTimeStepField, 0, 6);
        GridPane.setConstraints(simulationTimeStepLabel, 1, 6);
        GridPane.setConstraints(objectSpeedLabel, 1, 7);
        GridPane.setConstraints(objectSpeedField, 0, 7);
        GridPane.setConstraints(signalSpeedLabel, 1, 8);
        GridPane.setConstraints(signalSpeedField, 0, 8);
        GridPane.setConstraints(startingObjectDistanceField, 0, 9);
        GridPane.setConstraints(startingObjectDistanceLabel, 1, 9);
        GridPane.setConstraints(objectActualDistanceLabel, 1, 10);
        GridPane.setConstraints(objectCalculatedDistanceLabel, 1, 11);
        GridPane.setConstraints(objectActualDistanceLabel2, 0, 10);
        GridPane.setConstraints(objectCalculatedDistanceLabel2, 0 , 11);


        grid.getChildren().addAll(openFileButton, startSimulationButton, stopSimulationButton,
                fileLabel, objectSpeedField, objectSpeedLabel, signalSpeedField,
                signalSpeedLabel, objectActualDistanceLabel, objectCalculatedDistanceLabel,
                startingObjectDistanceField, startingObjectDistanceLabel, objectActualDistanceLabel2,
                objectCalculatedDistanceLabel2, simulationTimeStepField, simulationTimeStepLabel);

        window.setScene(new Scene(grid, 350, 400));
        window.show();
    }
}
