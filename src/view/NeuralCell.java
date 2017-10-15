package view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class NeuralCell {

    final double learningRate = 0.1;
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
    public NeuralCell() throws FileNotFoundException {

        scanner = new Scanner(new File("data.txt"));

        rowCount = scanner.nextInt();
        columnCount = scanner.nextInt();
        random = new Random();
        weights = new double[columnCount-1];
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
                adjustWeights(tmp);
            }


        } while (error > 0.01);

    }

    private void adjustWeights(double[] tmp) {

        for (int i = 0; i < columnCount-1; i++) {
            weights[i] +=  error * learningRate * tmp[i];
        }
    }


}
