package com.mewebstudio.javaspringbootboilerplate.waitnotify;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Time;
import java.util.Random;
import java.util.StringJoiner;

public class MatrixGenerator {
    private static final int NUM_OF_MATRICES = 1000;
    private static final int N = 10;
    private static final String outputFile = "./out/matrix";

    public static void main(String[] args) throws IOException{
        File file = new File(outputFile);
        FileWriter fileWriter = new FileWriter(file);
        long StartTime = System.currentTimeMillis();
        createMatrices(fileWriter);
        fileWriter.flush();
        fileWriter.close();
        long endTime = System.currentTimeMillis();
        System.out.println(String.format("Reading took %d ms", endTime - StartTime));

    }

    private static void createMatrices(FileWriter fileWriter) throws IOException{
        Random random = new Random();
        for (int numOfMatrices = 0; numOfMatrices <=NUM_OF_MATRICES*2 ; numOfMatrices ++){

            float[][] matrix = createMatrix(random);
            saveMatrixToFile(matrix , fileWriter);

        }
    }

    private static void saveMatrixToFile(float[][] matrix , FileWriter fileWriter) throws IOException{

        for (int i = 0; i <N; i++){
            StringJoiner stringJoiner = new StringJoiner(", ");
            for (int j = 0 ;  j <= N ; j ++){
                stringJoiner.add(String.format("%.2f" , matrix[i][j] ));
            }
                fileWriter.write(stringJoiner.toString());
                fileWriter.write("\n");

        }
        fileWriter.write("\n");
    }

    private static float[][] createMatrix(Random random){
        float[][] matrix = new float[NUM_OF_MATRICES][NUM_OF_MATRICES];
        for (int i = 0; i < N; i ++){
            float[] row = createRow(random);
            matrix[i] = row;
        }
        return matrix;

    }

    private static float[] createRow(Random random){
        float[] row = new float[NUM_OF_MATRICES];
        for (int i =0 ; i < N; i ++){
            row[i] = random.nextFloat()*random.nextInt(100);
        }
        return row;
    }
}
