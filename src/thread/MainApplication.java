package com.mewebstudio.javaspringbootboilerplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainApplication {
    public static final int MAX_PASSWORD = 9999;
    public static class Vault{
        private int password;
        public Vault(int password){
            this.password = password;
        }

        public boolean isPwdCorrect(Integer  pwd){
            try{
                Thread.sleep(5);

            }catch(InterruptedException e){

            }
            return this.password == pwd;
        }

    }

    private static abstract class HackerThread extends Thread {
        protected Vault vault;
        public HackerThread(Vault vault){
            this.vault = vault;
            this.setName(this.getClass().getSimpleName());
            this.setPriority(Thread.MAX_PRIORITY);
        }
        @Override
        public void start(){
            System.out.println("Started the thread" + this.getName());
            super.start();
        }
    }

    private static class AscendHackerThread extends HackerThread{
        public AscendHackerThread(Vault vault){
            super(vault);
        }

        @Override
        public void run(){
            for (int guess = 0; guess< MAX_PASSWORD; guess++){
                if(vault.isPwdCorrect(guess)){
                    System.out.println(this.getName() + "guessed the pwd" + guess);
                    System.exit(0);
                }
            }
        }
    }
    private static class DescendHackerThread extends HackerThread{
        public DescendHackerThread(Vault vault){
            super(vault);
        }

        @Override
        public void run(){
            for (int guess = MAX_PASSWORD; guess> 0; guess--){
                if(vault.isPwdCorrect(guess)){
                    System.out.println(this.getName() + "guessed the pwd" + guess);
                    System.exit(0);
                }
            }
        }
    }

    private static class PoliceThread extends Thread{
        @Override
        public void run(){
            for(int i = 0; i<10 ;i ++){
                try {
                    Thread.sleep(1000);
                    System.out.println(i);
                }catch(InterruptedException e){
                    System.out.println(e);
                }
            }
            System.out.println("Time over");
            System.exit(0);

        }
    }

    public static void main(String[] args) {
        Random random = new Random();
        Vault vault = new Vault(random.nextInt(MAX_PASSWORD));
        List<Thread> threads = new ArrayList<>();
        threads.add(new PoliceThread());
        threads.add(new DescendHackerThread(vault));
        threads.add(new AscendHackerThread(vault));

        for(Thread thread : threads){
            thread.start();
        }


    }

}
