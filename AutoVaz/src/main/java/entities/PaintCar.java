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
//            while(assembledCar.isEmpty()){
//                try {
//                    sleep(300);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//            assembledCar.remove(0);

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
                System.out.println("Я работаю только до создания нового поток?");
                synchronized (Main1.dryPool){
                    Main1.dryPool.execute(controller.dryCar);
                }
//                controller.dryCar.start();
                System.out.println(paintedCars+" дошло автомобилей");
                return true;
            }
        };
//        System.out.println(controller.progressBar4.getProgress());
        return task;
    }
}
