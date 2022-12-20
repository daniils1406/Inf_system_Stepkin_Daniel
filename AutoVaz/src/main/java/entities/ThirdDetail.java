package entities;

import app.Main1;
import javafx.concurrent.Task;


import java.util.LinkedList;
import java.util.List;

import static entities.FirstDetail.firstDetails;
import static entities.SecondDetail.secondDetails;


public class ThirdDetail extends Thread{
    static int value=1;
    public static List<Integer> thirdDetails=new LinkedList<>();

    public static Task createDetail() {
        Task task=new Task() {
            @Override
            protected Object call() throws Exception {
                int i=0;
                while(i<20){
                    i++;
                    Thread.sleep(300);
                    updateProgress(i,10);
                }
                updateProgress(10,10);
                thirdDetails.add(value++);
                if(secondDetails.size()!=0 && firstDetails.size()!=0){
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
