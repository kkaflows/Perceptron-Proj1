package view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class NeuralCell {

    final double learningRate = 0.001;
    double[] weights;
    double error;
    Random random;
    Scanner scanner;
    int input[][];
    double target[];

    public int rowCount;
    public int columnCount;
    double globalError;

    @FXML
    public TextField X1;
    @FXML
    public TextField X2;
    @FXML
    public TextField result;
    @FXML
    public TextField weight1TextField;
    @FXML
    public TextField weight2TextField;
    @FXML
    public TextField sigmoidTextField;


    public NeuralCell() throws FileNotFoundException {

        scanner = new Scanner(new File("data.txt"));

        rowCount = scanner.nextInt();
        columnCount = scanner.nextInt();
        random = new Random();
        weights = new double[columnCount-1];
        input = new int[rowCount][columnCount - 1];
        target = new double[rowCount];

        weights[0] = random.nextDouble()*2;
        weights[1] = random.nextDouble()*2;


    }

    public void loadData() {
        int row = 0;
        while (scanner.hasNextInt()) {
            for (int i = 0; i < columnCount - 1; i++) {
                input[row][i] = scanner.nextInt();
            }
            target[row] = scanner.nextInt();
            row++;
        }

    }

    public int weightSum(double[] tmp) {
        double weightSum = 0;
        for (int i = 0; i < columnCount - 1; i++) {
            weightSum += weights[i] * tmp[i];
        }
        if (weightSum > 10) {
            return 1;
        } else {
            return 0;
        }

    }
    public double weightSum2(double[] tmp) {
        double weightSum = 0;
        for (int i = 0; i < columnCount - 1; i++) {
            weightSum += weights[i] * tmp[i];
        }

        return 1/(1 + Math.exp(-weightSum));


    }

    public void learn() {
        double checkTmp = 0;
        loadData();
        do {
            for (int i = 0; i < rowCount; i++) {
                double tmp[] = new double[columnCount - 1];
                for (int j = 0; j < columnCount-1; j++) {

                        tmp[j] = input[i][j];
                }
                checkTmp = weightSum(tmp);
                error = target[i]-checkTmp;
                System.out.println("error = " + error);
                adjustWeights(tmp);
            }


        } while (error > 0.01);

    }

    public void learn2() {
        double checkTmp = 0;
        loadData();

            for (int i = 0; i < rowCount; i++) {
                double tmp[] = new double[columnCount - 1];
                for (int j = 0; j < columnCount-1; j++) {

                    tmp[j] = input[i][j];
                }

                checkTmp = weightSum2(tmp);
                error = target[i]-checkTmp;

                adjustWeights(tmp);
                System.out.println("error = "+error);
            }




    }

    private void adjustWeights(double[] tmp) {

        for (int i = 0; i < columnCount-1; i++) {
            weights[i] +=  error * learningRate * tmp[i];
        }
    }


    public void handleCheckNumbersButton(){
        double weightSum = 0;
        double[] tmp = new double[2];
        tmp[0] = Double.parseDouble(X1.getText());
        tmp[1] = Double.parseDouble(X2.getText());


        for (int i = 0; i < columnCount - 1; i++) {
            weightSum += weights[i] * tmp[i];
        }
        if (weightSum > 10) {
            result.setText("1");
        } else {
            result.setText("0");
        }

    }

    public void handleShowWeightButton(){
        weight1TextField.setText(String.valueOf(weights[0]));
        weight2TextField.setText(String.valueOf(weights[1]));
//        weight2TextField.setText(String.valueOf(weights[1]));
    }

    public double sigmoid(double weightSum){
        return  1/(1 + Math.exp(-weightSum));
    }

    public void handleSigmoidButton(){
        double weightSum = 0;
        double[] tmp = new double[2];
        tmp[0] = Double.parseDouble(X1.getText());
        tmp[1] = Double.parseDouble(X2.getText());


        for (int i = 0; i < columnCount - 1; i++) {
            weightSum += weights[i] * tmp[i];
        }
        double sigmoidValue = 1/(1 + Math.exp(-weightSum));

        sigmoidTextField.setText(Double.toString(sigmoidValue));


    }


}
