package com.mewebstudio.javaspringbootboilerplate;

import java.util.concurrent.locks.Lock;
import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLocks {

    public static class ReadAndWriteFromVar{
        public static int globalCount = 0;
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        Lock readLock = lock.readLock();
        Lock writeLock = lock.writeLock();

        public void changeGlobalCount(int change){
            writeLock.lock();
            try {
                globalCount += change;

            } finally {
                writeLock.unlock();
            }

        }
        public int readGlobalCount(){
            readLock.lock();
            try {
                return globalCount;
            } finally {
                readLock.unlock();
            }
        }
    }



  public static void main(String[] args) throws InterruptedException {

      ReadAndWriteFromVar loc = new ReadAndWriteFromVar();
      Thread writer = new Thread(() -> {
          Random random = new Random();
          for(int i = 0; i <= 5; i ++){
              int r = random.nextInt(25);
              try {
                  Thread.sleep(150);
                  loc.changeGlobalCount(r);
              } catch (InterruptedException e) {
                  throw new RuntimeException(e);
              }
          }
      });

      writer.setDaemon(true);
      writer.start();
      int numberOfReaderThreads = 7;
      List<Thread> readers = new ArrayList<>();

      for (int readerIndex = 0; readerIndex < numberOfReaderThreads; readerIndex++) {
          Thread reader = new Thread(() -> {
              for (int i = 0; i <= 5 ; i ++){
                  try {
                      Thread.sleep(100);
                  } catch (InterruptedException e) {
                      throw new RuntimeException(e);
                  }
                  int readVal = loc.readGlobalCount();
                  System.out.println(readVal);
              }
          });
          reader.setDaemon(true);
          readers.add(reader);
      }
      long startReadingTime = System.currentTimeMillis();
      for (Thread reader : readers) {
          reader.start();
      }

      for (Thread reader : readers) {
          reader.join();
      }

      long endReadingTime = System.currentTimeMillis();

      System.out.println(String.format("Reading took %d ms", endReadingTime - startReadingTime));


  }

}


