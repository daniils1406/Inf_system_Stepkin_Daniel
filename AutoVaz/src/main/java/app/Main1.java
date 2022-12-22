package app;

import entities.*;
import javafx.concurrent.Task;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.geometry.Pos;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import threads.InteriorAssembly;


import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


public class Main1 implements Initializable {

    public static final ThreadPoolExecutor carPool=(ThreadPoolExecutor) Executors.newFixedThreadPool(1);
    public static final ThreadPoolExecutor paintPool=(ThreadPoolExecutor) Executors.newFixedThreadPool(1);
    public static final ThreadPoolExecutor dryPool=(ThreadPoolExecutor) Executors.newFixedThreadPool(10);

    private Car car;

    private PaintCar paintCar;

    private InteriorAssembly interiorAssembly;

    public DryCar dryCar;

    @FXML
    public VBox dryVBox;

    @FXML
    public Pane mainPane;

    @FXML
    public ProgressBar progressBar1;

    @FXML
    public ProgressBar progressBar2;

    @FXML
    public ProgressBar progressBar3;

    @FXML
    public ProgressBar progressBar4;

    @FXML
    public ProgressBar progressBar5;

    @FXML
    public ProgressBar progressBar6;

    @FXML
    public ProgressBar progressBar7;

    public List<ProgressBar> progressBarListForWorkers= new LinkedList<>();


//    public List<ProgressBar> progressBarListForWorkers= new LinkedList<>();

    @FXML
    public ProgressBar progressBar8;



    public Main1(){
        this.car=new Car(this,carPool);
        carPool.execute(car);
        this.paintCar=new PaintCar(this);
        paintPool.execute(paintCar);
        interiorAssembly=new InteriorAssembly(this);
        this.dryCar=new DryCar(this);
    }


    public Task createDetail1() {
        return FirstDetail.createDetail();
    }
    public Task createDetail2() {
        return SecondDetail.createDetail();
    }
    public Task createDetail3() {
        return ThirdDetail.createDetail();
    }

    public Task createCar() {
        return Car.createCar();
    }
    public Task createCarInteriar1() {
        return InteriorAssembly.toolList.get(0).createInterior();
    }
    public Task createCarInteriar2() {
        return InteriorAssembly.toolList.get(1).createInterior();
    }
    public Task createCarInteriar3() {
        return InteriorAssembly.toolList.get(2).createInterior();
    }

    public Task paintCar(){
        return paintCar.paintCar();
    }


    public Task dryCar(){
        return dryCar.makeCarDry();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        progressBarListForWorkers.add(progressBar5);
        progressBarListForWorkers.add(progressBar6);
        progressBarListForWorkers.add(progressBar7);
        interiorAssembly.startWork();
    }

    public void login1(ActionEvent actionEvent) {
        Task task = createDetail1();
        progressBar1.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
    }

    public void login2(ActionEvent actionEvent) {
        Task task = createDetail2();
        progressBar2.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
    }

    public void login3(ActionEvent actionEvent) {
        Task task = createDetail3();
        progressBar3.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
    }
    public void login4() {
        Task task = createCar();
        progressBar4.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
    }

    public void workPlace1() {
        Task task = createCarInteriar1();
        progressBar5.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
    }

    public void workPlace2() {
        Task task = createCarInteriar2();
        progressBar6.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
    }

    public void workPlace3() {
        Task task = createCarInteriar3();
        progressBar7.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
    }

    public void paint(){
        Task task = paintCar();
        progressBar8.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
    }

    public void addDryProgressBar(){
        for(int i=0;i<dryVBox.getChildren().size();i++){
            ProgressBar currentBar=(ProgressBar) dryVBox.getChildren().get(i);
            if(currentBar.getProgress()==1.0){
                dryVBox.getChildren().remove(currentBar);
            }
        }
        ProgressBar progressBar=new ProgressBar();
        dryVBox.getChildren().add(progressBar);
        dryVBox.setAlignment(Pos.CENTER);
        Task task = dryCar();
        progressBar.progressProperty().bind(task.progressProperty());
        Thread thread= new Thread(task);
        thread.start();
    }
}