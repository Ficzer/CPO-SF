package SF.GUI;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AdvancedOptionsWindow
{
    public void display(TextField histogramElements)
    {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Advanced Options");

        AdvancedButtonHandler advancedButtonHandler = new AdvancedButtonHandler();

        Label fileALabel = new Label("File A");
        Label fileBLabel = new Label("File B");
        Label operationLabel = new Label("Operation");

        Button drawButton = new Button();
        drawButton.setText("Draw result");
        Button saveButton = new Button();
        saveButton.setText("Save result");
        Button showDataButton = new Button();
        showDataButton.setText("Show Data");
       // Button cleanButton = new Button();
       // cleanButton.setText("Clear All Data");

        ChoiceBox<String> fileChoiceA = new ChoiceBox<>();
        fileChoiceA.getItems().addAll(this.getFiles(new File("./signals/"), ".bin"));

        ChoiceBox<String> fileChoiceB = new ChoiceBox<>();
        fileChoiceB.getItems().addAll(this.getFiles(new File("./signals/"), ".bin"));

        ChoiceBox<String> operationChoice = new ChoiceBox<>();
        operationChoice.getItems().addAll("Add", "Subtract", "Multiply", "Divide");
        operationChoice.setValue("Add");

        drawButton.setOnAction(e -> advancedButtonHandler.handleDraw(fileChoiceA, fileChoiceB, operationChoice, histogramElements));
        saveButton.setOnAction(e -> {
        	advancedButtonHandler.handleSave(fileChoiceA, fileChoiceB, operationChoice);
			AlertBox.display("Success", "File has been saved.");
		});
        showDataButton.setOnAction(e -> advancedButtonHandler.showDataHandler(fileChoiceA, fileChoiceB, operationChoice));
        List<String> files = getFiles(new File("./signals/"), ".bin");
        files.addAll(getFiles(new File("./signals/"), ".xml"));
        //cleanButton.setOnAction(e -> {advancedButtonHandler.handleClean(files);});

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(12);
        grid.setHgap(12);

        GridPane.setConstraints(fileALabel, 0, 0);
        GridPane.setConstraints(fileBLabel, 0, 1);
        GridPane.setConstraints(operationLabel, 0, 2);
        GridPane.setConstraints(fileChoiceA, 1, 0);
        GridPane.setConstraints(fileChoiceB, 1, 1);
        GridPane.setConstraints(operationChoice, 1, 2);
        GridPane.setConstraints(drawButton, 0, 4);
        GridPane.setConstraints(saveButton, 0, 5);
        GridPane.setConstraints(showDataButton, 0, 6);
      //  GridPane.setConstraints(cleanButton, 0 , 7);


        grid.getChildren().addAll(fileChoiceA, fileChoiceB, fileALabel, fileBLabel, operationLabel, operationChoice,
                drawButton, saveButton, showDataButton/*, cleanButton */);

        window.setScene(new Scene(grid, 220, 300));
        window.show();
    }

    private List<String> getFiles(File folder, String suffix)
    {
        List<String> result = new ArrayList<String>();
        for (final File fileEntry : folder.listFiles())
        {
            if(fileEntry.getName().endsWith(suffix))
            {
                result.add(fileEntry.getName());
            }
        }
        return result;
    }
}
