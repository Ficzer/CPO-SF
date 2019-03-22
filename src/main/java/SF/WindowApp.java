package SF;

import javafx.application.Application;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class WindowApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Signal generator");
        primaryStage.setOnCloseRequest(e -> closeProgram(primaryStage));

        Button drawButton = new Button();
        Button saveButton = new Button();
        Button showDataButton = new Button();
        Button advancedOptionsButton = new Button();
        drawButton.setText("Draw");
        saveButton.setText("Save signal to file");
        showDataButton.setText("Show details");
        advancedOptionsButton.setText("Signal operations");

        ChoiceBox<String> signalChoice = new ChoiceBox<>();
        signalChoice.getItems().addAll("Noise", "Gaussian noise", "Sine Wave", "Half-wave rectified sine"
                , "Full-wave rectified sine", "Square wave", "Symmetrical Rectangular signal"
                , "Triangular wave", "Step function");
        signalChoice.setValue("Noise");

        TextField nameField = new TextField();
        TextField amplitudeField = new TextField("1");
        TextField startingTimeField = new TextField("0");
        TextField durationTimeField = new TextField("10");
        TextField periodField = new TextField("5");
        TextField FillFactorField = new TextField("0.5");
        TextField samplingField = new TextField("300");
        TextField histogramElements = new TextField("10");

        nameField.setPromptText("Name");
        amplitudeField.setPromptText("Amplitude");
        startingTimeField.setPromptText("Starting time");
        durationTimeField.setPromptText("Duration time");
        periodField.setPromptText("Period");
        FillFactorField.setPromptText("Fill factor");
        samplingField.setPromptText("Sampling");

        Label nameLabel = new Label("File name");
        Label amplitudeLabel = new Label("Amplitude");
        Label startingTimeLabel = new Label("Start time");
        Label durationTimeLabel = new Label("Duration time");
        Label periodLabel = new Label("Period");
        Label FillFactorLabel = new Label("Fill factor");
        Label SamplingLabel = new Label("Sampling");
		Label histogramElementsLabel = new Label("Histogram intervals");



        ButtonHandler buttonHandler = new ButtonHandler();
        AdvancedOptionsWindow advancedOptionsWindow = new AdvancedOptionsWindow();


        drawButton.setOnAction(e -> {
            try
            {
                buttonHandler.handleDraw(signalChoice, amplitudeField, startingTimeField, durationTimeField
                            , periodField, FillFactorField, samplingField, histogramElements);
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
                buttonHandler.handleSave(signalChoice, amplitudeField, startingTimeField, durationTimeField
                        , periodField, FillFactorField, samplingField, nameField);
                AlertBox.display("Success", "File has been saved.");
            }
            catch (Exception e1)
            {
                AlertBox.display("Wrong Format", "Format of given data is wrong or text fields are empty.");
               // e1.printStackTrace();
            }
        });

        showDataButton.setOnAction(e -> {
            try
            {
                buttonHandler.handleShowData(signalChoice, amplitudeField, startingTimeField, durationTimeField
                        , periodField, FillFactorField, samplingField);
            }
            catch (Exception e1)
            {
                AlertBox.display("Wrong Format", "Format of given data is wrong or text fields are empty");
                e1.printStackTrace();
            }
        });

        advancedOptionsButton.setOnAction(e -> {advancedOptionsWindow.display(histogramElements);});



        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(12);
        grid.setHgap(12);

        GridPane.setConstraints(signalChoice, 0, 0);
        GridPane.setConstraints(drawButton, 0, 2);
        GridPane.setConstraints(saveButton, 0,3);
        GridPane.setConstraints(showDataButton, 0,4);
        GridPane.setConstraints(advancedOptionsButton, 0, 5);
        GridPane.setConstraints(amplitudeField, 3,0);
        GridPane.setConstraints(startingTimeField, 3,1);
        GridPane.setConstraints(durationTimeField, 3,2);
        GridPane.setConstraints(periodField, 3,3);
        GridPane.setConstraints(FillFactorField, 3,4);
        GridPane.setConstraints(samplingField, 3,5);
        GridPane.setConstraints(nameField, 3,6);
        GridPane.setConstraints(histogramElements, 3, 7);
        GridPane.setConstraints(amplitudeLabel, 2,0);
        GridPane.setConstraints(startingTimeLabel, 2,1);
        GridPane.setConstraints(durationTimeLabel, 2,2);
        GridPane.setConstraints(periodLabel, 2,3);
        GridPane.setConstraints(FillFactorLabel, 2,4);
        GridPane.setConstraints(SamplingLabel, 2,5);
        GridPane.setConstraints(nameLabel, 2,6);
        GridPane.setConstraints(histogramElementsLabel, 2, 7);


        grid.getChildren().addAll(signalChoice, drawButton, saveButton, showDataButton, amplitudeField, startingTimeField, durationTimeField,
                                    periodField, FillFactorField, samplingField, amplitudeLabel, startingTimeLabel,
                                    durationTimeLabel, periodLabel, FillFactorLabel, SamplingLabel, advancedOptionsButton,
                                    nameField, nameLabel, histogramElementsLabel, histogramElements);

        primaryStage.setScene(new Scene(grid, 530, 330));
        primaryStage.show();
    }

    private void closeProgram(Stage primaryStage)
    {
        primaryStage.close();
    }


}