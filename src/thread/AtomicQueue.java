package com.mewebstudio.javaspringbootboilerplate;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicQueue {
    public static void main(String[] args){

    }

    public static class LockFreeStack{
        private AtomicReference<StackNode>
    }

    public static class StackNode<T>{
        public T value;
        private StackNode<T> next;

        public StackNode(T value){
            this.value = value;
            this.next = next;
        }
    }
}
