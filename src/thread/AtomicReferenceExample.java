package com.mewebstudio.javaspringbootboilerplate;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceExample {

    public static void main(String[] args){
         String oldName = "old_name";
         String newName = "new_name";

        AtomicReference<String> atomicReference = new AtomicReference<>(oldName);

        if(atomicReference.compareAndSet(oldName , newName)){
            System.out.println("Name changed atomically to " + atomicReference.toString());
        }
        else{
            System.out.println("CompareAndSet is not called.");
        }




    }

    }
