package entities;

import app.Main1;
import javafx.application.Platform;
import javafx.concurrent.Task;

import java.util.LinkedList;
import java.util.List;

import static java.lang.Thread.sleep;
import static threads.InteriorAssembly.assembledCar;

public class PaintCar implements Runnable{
    static int value=1;
    private static Main1 controller;

    public  PaintCar(Main1 controller){
        this.controller = controller;
    }
    public static List<Integer> paintedCars=new LinkedList<>();

    public void run(){
        while (true){
            synchronized (Main1.paintPool){
                try {
                    Main1.paintPool.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            while(assembledCar.size()!=0){
                assembledCar.remove(0);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        controller.paint();
                    }
                });
            }

        }
    }


    public Task paintCar(){
        Task task=new Task() {
            @Override
            protected Object call() throws Exception {
                int i=0;
                while(i<10){
                    i++;
                    sleep(100);
                    updateProgress(i,10);
                }
                updateProgress(10,10);
                paintedCars.add(value++);
                synchronized (Main1.dryPool){
                    Main1.dryPool.execute(controller.dryCar);
                }
                return true;
            }
        };
        return task;
    }
}
