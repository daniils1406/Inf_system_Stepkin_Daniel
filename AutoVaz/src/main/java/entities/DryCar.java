package entities;

import app.Main1;
import javafx.application.Platform;
import javafx.concurrent.Task;

import static java.lang.Thread.sleep;

public class DryCar implements Runnable{

    private Main1 controller;

    public DryCar(Main1 controller){
        this.controller=controller;
    }


    @Override
    public void run() {
        System.out.println("СУШИМ");
        PaintCar.paintedCars.remove(0);
//        System.out.println(PaintCar.paintedCars);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controller.addDryProgressBar();
            }
        });
    }

    public Task makeCarDry(){
        Task task=new Task() {
            @Override
            protected Object call() throws Exception {
                int i=0;
                while(i<10){
                    i++;
                    sleep(1000);
                    updateProgress(i,10);
                }
                updateProgress(10,10);
                return true;
            }
        };
        return task;
    }
}
