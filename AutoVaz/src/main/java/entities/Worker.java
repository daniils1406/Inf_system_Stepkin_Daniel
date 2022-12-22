package entities;

import threads.InteriorAssembly;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import static java.lang.Thread.sleep;

public class Worker implements Runnable {
    ThreadPoolExecutor workersPool;
    public Worker(ThreadPoolExecutor workersPool){
        this.workersPool=workersPool;
    }

    public void run() {
        int value=1;
        while (true) {
            try {
                synchronized (workersPool){
                    InteriorAssembly.nowRelax();
                    workersPool.wait();
                }

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            InteriorAssembly.nowWork();
            int freeWorkPlace = InteriorAssembly.getNumberOfFreeSpace();

            List<InteriorAssembly.Tool> p = InteriorAssembly.toolList;
            while(p.get(freeWorkPlace).currentWork.size()<100){
                p.get(freeWorkPlace).currentWork.add(value++);
                try {
                    sleep(300);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
