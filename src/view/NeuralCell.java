package view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class NeuralCell {

    final double learningRate = 0.01;
    double[] weights;
    double error;
    Random random;
    Scanner scanner;
    Scanner scannerTest;
    int input[][];
    int inputTest[][];
    double target[];
    double targetTest[];

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
    @FXML
    public TextField testResult;


    public NeuralCell() throws FileNotFoundException {

        scanner = new Scanner(new File("data.txt"));

        rowCount = scanner.nextInt() ;
        columnCount = scanner.nextInt();
        random = new Random();
        weights = new double[columnCount - 1];
        input = new int[rowCount][columnCount - 1];
        target = new double[rowCount];

        weights[0] = random.nextDouble();
        weights[1] = random.nextDouble();


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

    public double weightSum2(double[] tmp) {
        double weightSum = 0;
        for (int i = 0; i < columnCount - 1; i++) {
            weightSum += weights[i] * tmp[i];
        }

//        System.out.println("weightsum = " + weightSum);

        return sigmoid(weightSum);


    }

    public void learn2() throws FileNotFoundException {
        double checkTmp = 0;
        loadData();

        for (int i = 0; i < rowCount; i++) {
            double tmp[] = new double[columnCount - 1];
            for (int j = 0; j < columnCount - 1; j++) {

                tmp[j] = input[i][j];
            }
            System.out.println(tmp[0]);
            System.out.println(tmp[1]);
            checkTmp = weightSum2(tmp);
            error = target[i] - checkTmp;

                if(error*error>0.25)
            adjustWeights(tmp);
            System.out.println("error = " + error);
        }


    }

    private void adjustWeights(double[] tmp) {

        for (int i = 0; i < columnCount - 1; i++) {
            weights[i] += error * learningRate * tmp[i];
        }
    }

    public void handleShowWeightButton() {
        weight1TextField.setText(String.valueOf(weights[0]));
        weight2TextField.setText(String.valueOf(weights[1]));
//        weight2TextField.setText(String.valueOf(weights[1]));
    }

    public double sigmoid(double weightSum) {
        return 1 / (1 + Math.exp(-weightSum + 10));
    }

    public void handleCheckNumbersButton() {
        double weightSum = 0;
        double[] tmp = new double[2];
        tmp[0] = Double.parseDouble(X1.getText());
        tmp[1] = Double.parseDouble(X2.getText());


        for (int i = 0; i < columnCount - 1; i++) {
            weightSum += weights[i] * tmp[i];
        }
        double sigmoidValue = sigmoid(weightSum);

        sigmoidTextField.setText(Double.toString(sigmoidValue));
        if(sigmoidValue>0.5)
            result.setText("1");
        else
            result.setText("0");

    }

    public void test() throws FileNotFoundException {
        scannerTest = new Scanner(new File("test2.txt"));
        double rowCountTest = scannerTest.nextDouble();
        double columnCountTest = scannerTest.nextDouble();
        inputTest = new int[(int) rowCountTest][(int) columnCountTest - 1];
        targetTest = new double[(int) rowCountTest];
        double weightsumTest;
        double tmp[] = new double[2];
        double success = 0;
        double errorTest;
        double prediction = 0;

        for (int i = 0; i < rowCountTest; i++) {
            inputTest[i][0] = scannerTest.nextInt();
            inputTest[i][1] = scannerTest.nextInt();
            targetTest[i] = scannerTest.nextInt();
            tmp[0] = inputTest[i][0];
            tmp[1] = inputTest[i][1];

            weightsumTest = weightSum2(tmp);
            prediction = sigmoid(weightsumTest);
            errorTest = targetTest[i] - prediction;
            //System.out.println();
            if (errorTest*errorTest < 0.25) {
                success += 1;
            }
        }
        double average = (success/rowCountTest)*100;
        testResult.setText(String.valueOf(average));



    }


}
