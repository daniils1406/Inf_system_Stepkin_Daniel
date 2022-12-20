package entities;


import app.Main1;
import javafx.application.Platform;
import javafx.concurrent.Task;
import threads.InteriorAssembly;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import static entities.FirstDetail.firstDetails;
import static entities.SecondDetail.secondDetails;
import static entities.ThirdDetail.thirdDetails;
import static java.lang.Thread.sleep;

public class Car implements Runnable{
    static int value=1;
    private static Main1 controller;
    ThreadPoolExecutor carPool;

    public  Car(Main1 controller,ThreadPoolExecutor carPool){
        this.controller = controller;
        this.carPool=carPool;
    }


    public static List<Integer> cars=new LinkedList<>();
    public void run(){
        while (true){
//            while(secondDetails.size()==0 || thirdDetails.size()==0 || firstDetails.size()==0){
//                try {
//                    sleep(300);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }

            try {
                synchronized (carPool){
                    carPool.wait();
                }

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            while(secondDetails.size()!=0 && thirdDetails.size()!=0 && firstDetails.size()!=0){
                firstDetails.remove(0);
                secondDetails.remove(0);
                thirdDetails.remove(0);

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        controller.login4();
                    }
                });
            }

        }
    }


    public static Task createCar(){
        Task task=new Task() {
            @Override
            protected Object call() throws Exception {
                int i=0;
                while(i<10){
                    i++;
                    sleep(300);
                    updateProgress(i,10);
                }
                updateProgress(10,10);
                cars.add(value++);
                synchronized (InteriorAssembly.toolsPool){
                    System.out.println(cars.toString());
                    InteriorAssembly.toolsPool.notify();
                }

                return true;
            }
        };
        return task;
    }
}
