package SF;

import javafx.scene.control.ChoiceBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class AdvancedButtonHandler
{
    public void handleDraw(ChoiceBox<String> signalChoiceA, ChoiceBox<String> signalChoiceB, ChoiceBox<String> operation)
    {
        Signal signalA = null;
        Signal signalB = null;
        CalculationHelper calculationHelper = new CalculationHelper();
        ButtonHandler buttonHandler = new ButtonHandler();

        try
        {
            FileInputStream file = new FileInputStream("./signals/" + signalChoiceA.getValue());
            ObjectInputStream in = new ObjectInputStream(file);
            signalA = (Signal)in.readObject();

            file = new FileInputStream("./signals/" + signalChoiceB.getValue());
            in = new ObjectInputStream(file);
            signalB = (Signal)in.readObject();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        switch (operation.getValue())
        {
            case "Add":
                try
                {
                    buttonHandler.Draw(calculationHelper.addSignals(signalA, signalB));
                }
                catch (WrongSamplingException e)
                {
                    AlertBox.display("Wrong sampling", "Samplings of two singals don't match");
                    e.printStackTrace();
                }
                break;
            case "Subtract":
                try
                {
                    buttonHandler.Draw(calculationHelper.subtractSignals(signalA, signalB));
                }
                catch (WrongSamplingException e)
                {
                    AlertBox.display("Wrong sampling", "Samplings of two singals don't match");
                    e.printStackTrace();
                }
                break;
            case "Multiply":
                try
                {
                    buttonHandler.Draw(calculationHelper.multiplySignals(signalA, signalB));
                }
                catch (WrongSamplingException e)
                {
                    AlertBox.display("Wrong sampling", "Samplings of two singals don't match");
                    e.printStackTrace();
                }
                break;
            case "Divide":
                try
                {
                    buttonHandler.Draw(calculationHelper.divideSignals(signalA, signalB));
                }
                catch (WrongSamplingException e)
                {
                    AlertBox.display("Wrong sampling", "Samplings of two singals don't match");
                    e.printStackTrace();
                }
                break;

        }

    }

    public void handleSave(ChoiceBox<String> signalChoiceA, ChoiceBox<String> signalChoiceB, ChoiceBox<String> operation)
    {
        Signal signalA = null;
        Signal signalB = null;
        CalculationHelper calculationHelper = new CalculationHelper();
        ButtonHandler buttonHandler = new ButtonHandler();

        try
        {
            FileInputStream file = new FileInputStream("./signals/" + signalChoiceA.getValue());
            ObjectInputStream in = new ObjectInputStream(file);
            signalA = (Signal)in.readObject();

            file = new FileInputStream("./signals/" + signalChoiceB.getValue());
            in = new ObjectInputStream(file);
            signalB = (Signal)in.readObject();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        switch (operation.getValue())
        {
            case "Add":
                try
                {
                    buttonHandler.Save(calculationHelper.addSignals(signalA, signalB));
                }
                catch (WrongSamplingException e)
                {
                    AlertBox.display("Wrong sampling", "Samplings of two singals don't match");
                    e.printStackTrace();
                }
                break;
            case "Subtract":
                try
                {
                    buttonHandler.Save(calculationHelper.subtractSignals(signalA, signalB));
                }
                catch (WrongSamplingException e)
                {
                    AlertBox.display("Wrong sampling", "Samplings of two singals don't match");
                    e.printStackTrace();
                }
                break;
            case "Multiply":
                try
                {
                    buttonHandler.Save(calculationHelper.multiplySignals(signalA, signalB));
                }
                catch (WrongSamplingException e)
                {
                    AlertBox.display("Wrong sampling", "Samplings of two singals don't match");
                    e.printStackTrace();
                }
                break;
            case "Divide":
                try
                {
                    buttonHandler.Save(calculationHelper.divideSignals(signalA, signalB));
                }
                catch (WrongSamplingException e)
                {
                    AlertBox.display("Wrong sampling", "Samplings of two singals don't match");
                    e.printStackTrace();
                }
                break;

        }
    }

    public void showDataHandler(ChoiceBox<String> signalChoiceA, ChoiceBox<String> signalChoiceB, ChoiceBox<String> operation)
    {
        Signal signalA = null;
        Signal signalB = null;
        CalculationHelper calculationHelper = new CalculationHelper();
        ButtonHandler buttonHandler = new ButtonHandler();

        try
        {
            FileInputStream file = new FileInputStream("./signals/" + signalChoiceA.getValue());
            ObjectInputStream in = new ObjectInputStream(file);
            signalA = (Signal)in.readObject();

            file = new FileInputStream("./signals/" + signalChoiceB.getValue());
            in = new ObjectInputStream(file);
            signalB = (Signal)in.readObject();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        switch (operation.getValue())
        {
            case "Add":
                try
                {
                    AlertBox.display("Data", buttonHandler.generateData(calculationHelper.addSignals(signalA, signalB)));
                }
                catch (WrongSamplingException e)
                {
                    AlertBox.display("Wrong sampling", "Samplings of two singals don't match");
                    e.printStackTrace();
                }
                break;
            case "Subtract":
                try
                {
                    AlertBox.display("Data", buttonHandler.generateData(calculationHelper.addSignals(signalA, signalB)));
                }
                catch (WrongSamplingException e)
                {
                    AlertBox.display("Wrong sampling", "Samplings of two singals don't match");
                    e.printStackTrace();
                }
                break;
            case "Multiply":
                try
                {
                    AlertBox.display("Data", buttonHandler.generateData(calculationHelper.addSignals(signalA, signalB)));
                }
                catch (WrongSamplingException e)
                {
                    AlertBox.display("Wrong sampling", "Samplings of two singals don't match");
                    e.printStackTrace();
                }
                break;
            case "Divide":
                try
                {
                    AlertBox.display("Data", buttonHandler.generateData(calculationHelper.addSignals(signalA, signalB)));
                }
                catch (WrongSamplingException e)
                {
                    AlertBox.display("Wrong sampling", "Samplings of two singals don't match");
                    e.printStackTrace();
                }
                break;

        }
    }

    public void handleClean(List<String> files)
    {
        for (int i=0; i<files.size(); i++)
        {
            try {
                Files.deleteIfExists(Paths.get("./signals/" + files.get(i)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
