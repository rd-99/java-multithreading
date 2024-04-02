package com.mewebstudio.javaspringbootboilerplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

public class AtomicQueue {
    public static void main(String[] args) {
        StandardStack<Integer> stack = new StandardStack<>();
        Random random = new Random();

        for (int i = 0; i <= 10000; i++) {
            stack.push(random.nextInt(100));
        }

        List<Thread> popThreads = new ArrayList();

        for (int i = 0; i <= 2; i++) {
            Thread thread = new Thread(() -> {
                Long startTime = System.currentTimeMillis();
                while (System.currentTimeMillis() - startTime <= 1000) {
                    stack.pop();
                }
            });
            thread.setDaemon(on);
            popThreads.add(thread);
        }
        List<Thread> pushThreads = new ArrayList();

        for (int i = 0; i <= 2; i++) {
            Random random2 = new Random();
            Thread thread = new Thread(() -> {
                Long startTime = System.currentTimeMillis();
                while (System.currentTimeMillis() - startTime <= 1000) {

                    stack.push(random2.nextInt(100));
                }
            });
            thread.setDaemon(on);
            popThreads.add(thread);
        }

        for (Thread thread : pushThreads) {
            thread.start();
        }
        for (Thread thread : popThreads) {
            thread.start();
        }

        System.out.println("Counter  == " + stack.getCount());

    }

    public static class AtomicStack {
        public AtomicInteger count;
        private AtomicReference<StackNode<T>> head = new AtomicReference<>();

        public int getCount() {
            return count;
        }

        public void push(T val) {
            StackNode<T> newHeadNode = new StackNode<T>(val);
            while (true) {
                StackNode<T> currentHeadNode = head.get();
                currentHeadNode.next = newHeadNode;
                if (head.compareAndSet(currentHeadNode, newHeadNode)) {
                    break;
                } else {
                    LockSupport.parkNanos(1);
                }
            }
            count.incrementAndGet();
        }

        public T pop() {
            StackNode currHeadNode = head.get();
            StackNode newNode;
            while (currHeadNode != null) {
                newNode = currHeadNode.next;
                if (head.compareAndSet(currHeadNode, newNode)) {
                    break;
                } else {
                    currHeadNode = head.get();
                    LockSupport.parkNanos(1);
                }
            }
            count.incrementAndGet();
            return currHeadNode != null ? currHeadNode.value : null;
        }
    }

    public static class StandardStack<T> {
        private AtomicReference<StackNode> head;
        private int count;

        public int getCount() {
            return count;
        }

        public synchronized T pop() {
            if (head == null) {
                count++;
                return null;
            }
            T value = head.val;
            head = head.next;
            count++;
            return value;
        }

        public synchronized T push(T val) {
            StackNode newHead = new StackNode<>(val);
            newHead.next = head;
            head = newHead;
            count++;
        }

    }

    public static class StackNode<T> {
        public T value;
        private StackNode<T> next;

        public StackNode(T value) {
            this.value = value;
            this.next = next;
        }
    }
}
