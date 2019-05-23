package SF.GUI;

import SF.Converters.AnalogToDigitalConverter;
import SF.EmptyFileNameException;
import SF.Filter.Filter;
import SF.Signal;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FilterWindow
{
    private Signal originalSignal = new Signal();
    private Signal filteredSignal = null;

    public void display()
    {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Filter Signal");

        final FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("./signals"));
        Button openFileButton = new Button("Open file");
        Button drawButton = new Button("Filter");
        Button saveButton = new Button("Save new signal");

        TextField mField = new TextField("63");
        TextField frequencyField = new TextField("100.0");
        Label fileLabel = new Label("File");
        Label mLabel = new Label(" Filter order");
        Label frequencyLabel = new Label(" Filter frequency");

        ChoiceBox<String> windowFunctionChoice = new ChoiceBox<>();
        windowFunctionChoice.getItems().addAll("Blackman", "Hamming", "Hanning", "Ractangular");
        windowFunctionChoice.setValue("Rectangular");

        ChoiceBox<String> filterFunctionChoice = new ChoiceBox<>();
        filterFunctionChoice.getItems().addAll("Low pass", "Band pass", "High pass");
        filterFunctionChoice.setValue("Low pass");

        openFileButton.setMinWidth(100);
        drawButton.setMinWidth(100);

        ButtonHandler buttonHandler = new ButtonHandler();


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
                    originalSignal.copy((Signal)in.readObject());
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
                List<Signal> resList = buttonHandler.applyFilter(windowFunctionChoice, filterFunctionChoice, mField,
                        frequencyField, originalSignal);

                buttonHandler.draw(resList.get(1), 10, false, true);
                buttonHandler.draw(resList.get(0), 10, false, true);

                filteredSignal = resList.get(1);

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
                buttonHandler.save(filteredSignal);
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
        GridPane.setConstraints(saveButton, 0, 2);
        GridPane.setConstraints(fileLabel, 1, 0);
        GridPane.setConstraints(mLabel, 1, 7);
        GridPane.setConstraints(mField, 0, 7);
        GridPane.setConstraints(frequencyLabel, 1, 8);
        GridPane.setConstraints(frequencyField, 0, 8);
        GridPane.setConstraints(windowFunctionChoice, 0, 5);
        GridPane.setConstraints(filterFunctionChoice, 0, 6);


        grid.getChildren().addAll(openFileButton, drawButton,
                fileLabel, mField, mLabel, frequencyField,
                frequencyLabel, saveButton, windowFunctionChoice,
                filterFunctionChoice);

        window.setScene(new Scene(grid, 300, 400));
        window.show();
    }
}
