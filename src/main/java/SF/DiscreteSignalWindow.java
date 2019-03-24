package SF;

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

public class DiscreteSignalWindow
{
    public void display()
    {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Discrete signal");
        ButtonHandler buttonHandler = new ButtonHandler();

        Button drawButton = new Button();
        Button saveButton = new Button();
        Button showDataButton = new Button();
        drawButton.setText("Draw");
        saveButton.setText("Save signal to file");
        showDataButton.setText("Show details");

        TextField nameField = new TextField();
        TextField amplitudeField = new TextField("1");
        TextField startingTimeField = new TextField("0");
        TextField durationTimeField = new TextField("10");
        TextField impulsePositionField = new TextField("150");
        TextField probabilityField = new TextField("0.5");
        TextField samplingField = new TextField("300");
        TextField intervalsField = new TextField("10");

        Label nameLabel = new Label("File name");
        Label amplitudeLabel = new Label("Amplitude");
        Label startingTimeLabel = new Label("Start time");
        Label durationTimeLabel = new Label("Duration time");
        Label impulsePositionLabel = new Label("Impulse Position");
        Label probabilityLabel = new Label("Probability");
        Label samplingLabel = new Label("Sampling");
        Label intervalsLabel = new Label("Intervals");

        ChoiceBox<String> signalChoice = new ChoiceBox<>();
        signalChoice.getItems().addAll("Unit Impulse", "Impulse Noise");
        signalChoice.setValue("Unit Impulse");

        drawButton.setOnAction(e -> {
            try
            {
                buttonHandler.handleDiscreteDraw(signalChoice, amplitudeField, startingTimeField,
                        durationTimeField, impulsePositionField, probabilityField, samplingField, nameField, intervalsField);
            }
            catch (Exception e1)
            {
                AlertBox.display("Wrong Format", "Format of given data is wrong or text fields are empty.");
                e1.printStackTrace();
            }
        });

        saveButton.setOnAction(e -> {
            try
            {
                buttonHandler.handleDiscreteSave(signalChoice, amplitudeField, startingTimeField,
                        durationTimeField, impulsePositionField, probabilityField, samplingField, nameField, intervalsField);
            }
            catch (Exception e1)
            {
                AlertBox.display("Wrong Format", "Format of given data is wrong or text fields are empty.");
                e1.printStackTrace();
            }
            AlertBox.display("Success", "File has been saved.");
        });

        showDataButton.setOnAction(e -> {
            try
            {
                buttonHandler.handleDiscreteShowData(signalChoice, amplitudeField, startingTimeField,
                        durationTimeField, impulsePositionField, probabilityField, samplingField, nameField, intervalsField);
            }
            catch (Exception e1)
            {
                AlertBox.display("Wrong Format", "Format of given data is wrong or text fields are empty.");
                e1.printStackTrace();
            }
        });


        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(12);
        grid.setHgap(12);

        GridPane.setConstraints(signalChoice, 0, 0);
        GridPane.setConstraints(drawButton, 0, 2);
        GridPane.setConstraints(saveButton, 0, 3);
        GridPane.setConstraints(showDataButton, 0, 4);
        GridPane.setConstraints(nameLabel, 1,0);
        GridPane.setConstraints(amplitudeLabel, 1, 1);
        GridPane.setConstraints(startingTimeLabel, 1, 2);
        GridPane.setConstraints(durationTimeLabel, 1, 3);
        GridPane.setConstraints(impulsePositionLabel, 1, 4);
        GridPane.setConstraints(probabilityLabel, 1, 5);
        GridPane.setConstraints(samplingLabel, 1, 6);
        GridPane.setConstraints(nameField, 2, 0);
        GridPane.setConstraints(amplitudeField, 2, 1);
        GridPane.setConstraints(startingTimeField, 2, 2);
        GridPane.setConstraints(durationTimeField, 2,3);
        GridPane.setConstraints(impulsePositionField, 2, 4);
        GridPane.setConstraints(probabilityField, 2, 5);
        GridPane.setConstraints(samplingField, 2, 6);
        GridPane.setConstraints(intervalsLabel, 1, 7);
        GridPane.setConstraints(intervalsField, 2, 7);


        grid.getChildren().addAll(signalChoice, drawButton, saveButton, showDataButton, nameField, startingTimeField, durationTimeField,
                impulsePositionField, probabilityField, samplingField, nameLabel, startingTimeLabel, durationTimeLabel,
                impulsePositionLabel, probabilityLabel, samplingLabel, intervalsField, intervalsLabel);

        window.setScene(new Scene(grid, 400, 340));
        window.show();
    }

}
