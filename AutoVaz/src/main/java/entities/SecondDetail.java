package entities;

import app.Main1;
import javafx.concurrent.Task;


import java.util.LinkedList;
import java.util.List;

import static entities.FirstDetail.firstDetails;
import static entities.ThirdDetail.thirdDetails;


public class SecondDetail extends Thread{


    public static List<Integer> secondDetails=new LinkedList<>();
    static int value=1;

    public static Task createDetail() {
        Task task=new Task() {
            @Override
            protected Object call() throws Exception {
                int i=0;
                while(i<15){
                    i++;
                    Thread.sleep(300);
                    updateProgress(i,10);
                }
                updateProgress(10,10);
                secondDetails.add(value++);
                if(firstDetails.size()!=0 && thirdDetails.size()!=0){
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
