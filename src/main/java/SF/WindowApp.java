package SF;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WindowApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Signal generator");
        primaryStage.setOnCloseRequest(e -> closeProgram());

        Button drawButton = new Button();
        Button saveButton = new Button();
        drawButton.setText("Draw");
        saveButton.setText("Save to XML");

        ChoiceBox<String> signalChoice = new ChoiceBox<>();
        signalChoice.getItems().addAll("Noise", "Gausian Noise", "Sinusoida", "Erected Sidusoida"
                , "Two Said Erected Sinusoida", "Rectangle signal", "Simetrical Rectangular signal"
                , "Trangular signal", "Unit Jump");
        signalChoice.setValue("Noise");

        TextField amplitudeField = new TextField();
        TextField startingTimeField = new TextField();
        TextField durationTimeField = new TextField();
        TextField periodField = new TextField();
        TextField FillFactorField = new TextField();
        TextField samplingField = new TextField();
        amplitudeField.setPromptText("Amplitude");
        startingTimeField.setPromptText("Starting time");
        durationTimeField.setPromptText("Duration time");
        periodField.setPromptText("Period");
        FillFactorField.setPromptText("Fill factor");
        samplingField.setPromptText("Sampling");

        Label amplitudeLabel = new Label("Amplitude");
        Label startingTimeLabel = new Label("Starting time");
        Label durationTimeLabel = new Label("Duration time");
        Label periodLabel = new Label("Period");
        Label FillFactorLabel = new Label("Fill factor");
        Label SamplingLabel = new Label("Sampling");




        ButtonHandler buttonHandler = new ButtonHandler();


        drawButton.setOnAction(e -> {
            try
            {
                buttonHandler.handleDraw(signalChoice, amplitudeField, startingTimeField, durationTimeField
                            , periodField, FillFactorField, samplingField);
            }
            catch (Exception e1)
            {
                AlertBox.display("Wrong Format", "Format of given data is wrong or text fields are empty");
            }
        });
        saveButton.setOnAction(e -> {
            try
            {
                buttonHandler.handleSave(signalChoice, amplitudeField, startingTimeField, durationTimeField
                        , periodField, FillFactorField, samplingField);
            }
            catch (Exception e1)
            {
                AlertBox.display("Wrong Format", "Format of given data is wrong or text fields are empty");
            }
        });



        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(12);
        grid.setHgap(12);

        GridPane.setConstraints(signalChoice, 0, 0);
        GridPane.setConstraints(drawButton, 0, 2);
        GridPane.setConstraints(saveButton, 0,3);
        GridPane.setConstraints(amplitudeField, 3,0);
        GridPane.setConstraints(startingTimeField, 3,1);
        GridPane.setConstraints(durationTimeField, 3,2);
        GridPane.setConstraints(periodField, 3,3);
        GridPane.setConstraints(FillFactorField, 3,4);
        GridPane.setConstraints(samplingField, 3,5);
        GridPane.setConstraints(amplitudeLabel, 2,0);
        GridPane.setConstraints(startingTimeLabel, 2,1);
        GridPane.setConstraints(durationTimeLabel, 2,2);
        GridPane.setConstraints(periodLabel, 2,3);
        GridPane.setConstraints(FillFactorLabel, 2,4);
        GridPane.setConstraints(SamplingLabel, 2,5);


        grid.getChildren().addAll(signalChoice, drawButton, saveButton, amplitudeField, startingTimeField, durationTimeField,
                                    periodField, FillFactorField, samplingField, amplitudeLabel, startingTimeLabel,
                                    durationTimeLabel, periodLabel, FillFactorLabel, SamplingLabel);

        primaryStage.setScene(new Scene(grid, 800, 600));
        primaryStage.show();
    }

    private void closeProgram()
    {

    }


}