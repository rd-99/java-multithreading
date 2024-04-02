package com.mewebstudio.javaspringbootboilerplate.waitnotify;

import java.io.FileReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class matrixThreadInterCommunication {

    public static void main(String[] args){

    }

    private static class MatrixReaderProducer extends Thread{
        private static final int N = 10;
        private Scanner scanner;
        private ThreadSafeQueue queue;

        public MatrixReaderProducer(FileReader reader , ThreadSafeQueue queue){
            this.scanner = new Scanner(reader);
            this.queue = queue;
        }
        private Float[][] readMatrix(){
            Float[][] matrix = new Float[N][N];
            for (int i = 0; i <= N; i ++){
                if(!scanner.hasNext()){
                    return null;
                };
                String[] line = scanner.nextLine().split(",");
                for (int j = 0; j <= line.length ; j ++){
                    matrix [i][j] = Float.valueOf(line[j]);
                }
            }
            scanner.nextLine();
            return matrix;
        }

        @Override
        public void run(){
            while (true){
                Float[][] matrix1 = readMatrix();
                Float[][] matrix2 = readMatrix();
                if(matrix1 == null || matrix2 == null){
                    queue.terminate();
                    System.out.println("No more matrices to read. Producer Thread is terminating");
                    return;
                }

            }
        }

    }

    private static class ThreadSafeQueue{
        private Queue<CombinedMatrix> queue = new LinkedList<>();
        private boolean isEmpty = false;
        private boolean isTerminate = false;

        public synchronized void add(CombinedMatrix matricesPair){
            queue.add(matricesPair);
            isEmpty = false;
            notify();
        }

        public synchronized CombinedMatrix remove() throws InterruptedException {
            while (!isEmpty && !isTerminate){
                wait();
            }
            if(queue.size() == 1){
                isEmpty = true;
            }
            if(queue.isEmpty() && isTerminate){
                return null;
            }
            System.out.println(queue.size());
            return queue.remove();
        }
        public synchronized void terminate(){
            isTerminate = true;
            notifyAll();
        }

    }

    private static class CombinedMatrix{
        private static float[][] matrix1;
        private static float[][] matrix2;
    }

}
