package threads;

import app.Main1;
import entities.Car;
import entities.Worker;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

public class InteriorAssembly {
    int value = 0;
    public static List<Integer> assembledCar = new LinkedList<>();
    public static ThreadPoolExecutor toolsPool;


    public class Tool implements Runnable {
        int numberOfWorkersNow = 0;
        public List<Integer> currentWork = new LinkedList<>();
        ProgressBar progressBar;
        boolean buildIsComplete = false;

        public Tool(ProgressBar progressBar) {
            this.progressBar = progressBar;
        }

        public void run() {
            while (true) {
                synchronized (toolsPool) {
                    try {
                        toolsPool.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                while (Car.cars.size() != 0) {
                        buildIsComplete = false;
                        if(Car.cars.size()!=0){
                            Car.cars.remove(0);
                        }


                        while (!buildIsComplete) {
                            if (progressBar.getProgress() == 0.0 || progressBar.getProgress() == 1.0) {
                                if (buildIsComplete) {
                                    break;
                                }
                                currentWork = new LinkedList<>();
                                numberOfWorkersNow = 0;
                                int indexOfCurrentTool = toolList.indexOf(this);
                                switch (indexOfCurrentTool) {
                                    case 0:
                                        Platform.runLater(new Runnable() {
                                            @Override
                                            public void run() {
                                                main1.workPlace1();
                                            }
                                        });
                                        break;
                                    case 1:
                                        Platform.runLater(new Runnable() {
                                            @Override
                                            public void run() {
                                                main1.workPlace2();
                                            }
                                        });
                                        break;
                                    case 2:
                                        Platform.runLater(new Runnable() {
                                            @Override
                                            public void run() {
                                                main1.workPlace3();
                                            }
                                        });
                                        break;
                                }
                                numberOfFreeSpace = indexOfCurrentTool;
                                synchronized (workersPool) {
                                    if (relaxWorkers / 3 > 0) {
                                        workersPool.notify();
                                        workersPool.notify();
                                        workersPool.notify();
                                        numberOfWorkersNow = 3;
                                    } else {
                                        for (int i = 0; i < relaxWorkers % 3; i++) {
                                            workersPool.notify();
                                            numberOfWorkersNow++;
                                        }
                                    }
                                }
                                try {
                                    sleep(1000);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            } else if (numberOfWorkersNow < 3) {
                                int indexOfCurrentTool = toolList.indexOf(this);
                                numberOfFreeSpace = indexOfCurrentTool;
                                synchronized (workersPool) {
                                    if (numberOfWorkersNow == 1) {
                                        if (relaxWorkers >= 1) {
                                            workersPool.notify();
                                            numberOfWorkersNow++;
                                        }
                                        if (relaxWorkers >= 1) {
                                            workersPool.notify();
                                            numberOfWorkersNow++;
                                        }
                                    } else if (numberOfWorkersNow == 2) {
                                        if (relaxWorkers >= 1) {
                                            workersPool.notify();
                                            numberOfWorkersNow++;
                                        }
                                    }
                                }
                            }
                        }
                }
            }
        }


        public Task createInterior() {
            Task task = new Task() {
                @Override
                protected Object call() throws Exception {
                    int i = 0;
                    while (i < 100) {
                        i = currentWork.size();
                        System.out.println(i + " это поток " + currentThread().getId() + " отдыхает " + relaxWorkers + " количество рабочих за станком " + numberOfWorkersNow + " прогресс" + progressBar.getProgress());
                        sleep(1000);
                        updateProgress(i, 100);
                    }
                    updateProgress(100, 100);
                    assembledCar.add(value++);
                    synchronized (Main1.paintPool){
                        Main1.paintPool.notifyAll();
                    }
                    numberOfWorkersNow = 0;
                    buildIsComplete = true;
                    return true;
                }
            };
            return task;
        }
    }
    static int numberOfWorkers = 7;
    final ThreadPoolExecutor workersPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(numberOfWorkers);
    Worker worker = new Worker(workersPool);
    Main1 main1;
    public static List<Tool> toolList = new LinkedList<>();
    static int relaxWorkers = 0;

    public static void nowWork() {
        relaxWorkers--;
    }

    public static void nowRelax() {
        relaxWorkers++;
    }


    static int numberOfFreeSpace = 0;

    public static int getNumberOfFreeSpace() {
        return numberOfFreeSpace;
    }


    public InteriorAssembly(Main1 main1) {
        this.main1 = main1;
    }

    public void startWork() {
        while (true) {
            synchronized (workersPool) {
                workersPool.execute(worker);
                if (workersPool.getActiveCount() >= numberOfWorkers) {
                    break;
                }
            }
        }

        int numberOfTools = numberOfWorkers / 3;
        if (numberOfWorkers % 3 > 0) {
            numberOfTools = numberOfTools + 1;
        }

        toolsPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(numberOfTools);
        for (int i = 0; i < numberOfTools; i++) {

            Tool tool = new Tool(main1.progressBarListForWorkers.get(i));
            toolList.add(tool);
            toolsPool.execute(tool);
        }


    }
}
