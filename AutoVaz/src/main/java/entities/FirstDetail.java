//package entities;
//
//
//import static threads.DetailsIPools.firstDetails;
//
//public class FirstDetail implements Runnable{
//    int capacity=3;
//    int value=1;
//    @Override
//    public void run() {
//        while(true){
//            while(firstDetails.size()<capacity){
//                firstDetails.add(value++);
//            }
//        }
//    }
//}



package entities;

import app.Main1;
import javafx.concurrent.Task;

import java.util.LinkedList;
import java.util.List;

import static entities.SecondDetail.secondDetails;
import static entities.ThirdDetail.thirdDetails;

//import static threads.DetailsIPools.firstDetails;

public class FirstDetail extends Thread{
    public static List<Integer> firstDetails=new LinkedList<>();
    static int value=1;


    public static Task createDetail() {
        Task task=new Task() {
            @Override
            protected Object call() throws Exception {
                int i=0;
                while(i<10){
                    i++;
                    Thread.sleep(300);
                    updateProgress(i,10);
                }
                updateProgress(10,10);
                firstDetails.add(value++);
                if(secondDetails.size()!=0 && thirdDetails.size()!=0){
                    System.out.println("будим");
                    synchronized (Main1.carPool){
                        Main1.carPool.notifyAll();
                    }
                }
                return true;
            }
        };
        return task;

    }
}