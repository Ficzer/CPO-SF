package SF.GUI;

import SF.AnalogToDigitalConverter;
import SF.DigitalToAnalogConverter;
import SF.EmptyFileNameException;
import SF.Signal;
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

public class CAConverterWindow
{

    private Signal originalAnalogSignal  = new Signal();
    private Signal originalSignal = new Signal();
    private Signal newSignal = null;

    public void display()
    {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Convert to Analog Signal");

        final FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("./signals"));
        Button openFileButton = new Button("Open file");
        Button drawButton = new Button("Draw");
        Button showDataButton = new Button("Show Data");
        Button interpolateButton = new Button("Interpolate");
        Button sincButton = new Button("Sinc");
        Button saveButton = new Button("Save new signal");

        TextField samplingField = new TextField("100");
        TextField probesField = new TextField("4");
        Label fileLabel = new Label("File");
        Label samplingLabel = new Label(" New sampling");
        Label probesLabel = new Label(" Max probes");


        openFileButton.setMinWidth(100);
        drawButton.setMinWidth(100);
        showDataButton.setMinWidth(100);

        AdvancedButtonHandler advancedButtonHandler = new AdvancedButtonHandler();
        ButtonHandler buttonHandler = new ButtonHandler();
        DigitalToAnalogConverter digitalToAnalogConverter = new DigitalToAnalogConverter();

        openFileButton.setOnAction(e ->
        {
            File file = fileChooser.showOpenDialog(window);
            newSignal = null;

            if(file != null)
            {
                FileInputStream fileInputStream = null;
                try
                {
                    fileInputStream = new FileInputStream(file);
                    ObjectInputStream in = new ObjectInputStream(fileInputStream);
                    originalSignal.copy((Signal)in.readObject());
                    String analogName =  originalSignal.getName();

                    int index = analogName.indexOf("(");
                    if(index != -1)
                    {
                        analogName = analogName.substring(0, index);
                    }

                    fileInputStream = new FileInputStream(new File("./signals/" + analogName + ".bin"));
                    in = new ObjectInputStream(fileInputStream);
                    originalAnalogSignal.copy((Signal)in.readObject());
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


                fileLabel.setText(originalSignal.getName());
            }
        });

        drawButton.setOnAction(e ->
        {
            try
            {
                if(newSignal != null)
                {
                    buttonHandler.draw(newSignal, originalSignal, originalAnalogSignal);
                }
                else
                {
                    buttonHandler.draw(originalSignal, 10, false, true);
                }

            }
            catch (NumberFormatException e1)
            {
                e1.printStackTrace();
                AlertBox.display("Wrong format", "Wrong format of intervals.");
            }
            catch (NullPointerException e1)
            {
                e1.printStackTrace();
                AlertBox.display("Choose file", "Please choose file to load.");
            }
        });

        showDataButton.setOnAction(e ->
        {
            try
            {
                AlertBox.display("Data", buttonHandler.generateData(newSignal));
            }
            catch (NullPointerException e1)
            {
                e1.printStackTrace();
                AlertBox.display("Choose file", "Please choose file to load.");
            }
        });

        interpolateButton.setOnAction(e ->
        {
            try
            {
                newSignal = digitalToAnalogConverter.interpolate(originalSignal, Integer.parseInt(samplingField.getText()));
                AlertBox.display("Success", "The signal has been interpolated");
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
            catch (IllegalArgumentException e1)
            {
                e1.printStackTrace();
                AlertBox.display("Sampling error", e1.getMessage());
            }
        });

        sincButton.setOnAction(e ->
        {
            try
            {
                newSignal = digitalToAnalogConverter.reconstructWithSinc(originalSignal,
                        Integer.parseInt(samplingField.getText()), Integer.parseInt(probesField.getText()));

                AlertBox.display("Success", "The signal has been sinced");
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
        });

        saveButton.setOnAction(e ->
        {
            try
            {
                buttonHandler.save(newSignal);
                AlertBox.display("Success", "The file has been saved");
            }
            catch (EmptyFileNameException e1)
            {
                e1.printStackTrace();
            }
            catch (NullPointerException e1)
            {
                e1.printStackTrace();
                AlertBox.display("Generate file", "Please generate file to save.");
            }

        });




        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(12);
        grid.setHgap(12);

        GridPane.setConstraints(openFileButton, 0,0);
        GridPane.setConstraints(drawButton, 0, 1);
        GridPane.setConstraints(showDataButton, 0 , 2);
        GridPane.setConstraints(interpolateButton, 0, 3);
        GridPane.setConstraints(sincButton, 0, 4);
        GridPane.setConstraints(saveButton, 0, 5);
        GridPane.setConstraints(fileLabel, 1, 0);
        GridPane.setConstraints(samplingLabel, 1, 6);
        GridPane.setConstraints(samplingField, 0, 6);
        GridPane.setConstraints(probesLabel, 1, 7);
        GridPane.setConstraints(probesField, 0, 7);


        grid.getChildren().addAll(openFileButton, drawButton, showDataButton,
                fileLabel, interpolateButton, sincButton, samplingField,
                samplingLabel, probesField, probesLabel, saveButton);

        window.setScene(new Scene(grid, 300, 400));
        window.show();
    }
}
