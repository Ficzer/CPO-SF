package SF;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import java.util.List;

public class LoadSignalWindow
{
    public void display()
    {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Load Signal");

        final FileChooser fileChooser = new FileChooser();
        Button openFileButton = new Button("Open file");
        Button drawButton = new Button("Draw");
        Button showDataButton = new Button("Show Data");
        TextField fileNameField = new TextField();
        TextField intervalsField = new TextField("10");
        Label fileLabel = new Label("File");
        Label intervalLabel = new Label("Intervals");

        AdvancedButtonHandler advancedButtonHandler = new AdvancedButtonHandler();
        ButtonHandler buttonHandler = new ButtonHandler();
        Signal signal = new Signal();

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
                        signal.copy((Signal)in.readObject());
                    }
                    catch (FileNotFoundException e1)
                    {
                        e1.printStackTrace();
                    }
                    catch (IOException e1)
                    {
                        e1.printStackTrace();
                    }
                    catch (ClassNotFoundException e1)
                    {
                        e1.printStackTrace();
                        AlertBox.display("Wrong file", "Wrong file Format");
                    }
                    fileNameField.setText(signal.getName());
                }
            });

        drawButton.setOnAction(e ->
        {
            try
            {
                buttonHandler.draw(signal, Integer.valueOf(intervalsField.getText()));
            }
            catch (NumberFormatException e1)
            {
                e1.printStackTrace();
                AlertBox.display("Wrong format", "Wrong format of intervals");
            }
        });

        showDataButton.setOnAction(e ->
        {
            AlertBox.display("Data", buttonHandler.generateData(signal));
        });




        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(12);
        grid.setHgap(12);

        GridPane.setConstraints(openFileButton, 0,0);
        GridPane.setConstraints(drawButton, 0, 1);
        GridPane.setConstraints(showDataButton, 0 , 2);
        GridPane.setConstraints(fileLabel, 1, 0);
        GridPane.setConstraints(intervalLabel, 1, 1);
        GridPane.setConstraints(fileNameField, 2, 0);
        GridPane.setConstraints(intervalsField, 2, 1);


        grid.getChildren().addAll(openFileButton, drawButton, showDataButton, fileNameField, intervalsField,
                fileLabel, intervalLabel);

        window.setScene(new Scene(grid, 320, 300));
        window.show();
    }

    private Signal getFile(Signal signal, Stage window, FileChooser fileChooser)
    {

        return signal;
    }
}
