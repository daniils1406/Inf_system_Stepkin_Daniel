package threads;

import entities.SecondDetail;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class DetailsIIPools extends Thread{
    ThreadPoolExecutor detailIPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);

    SecondDetail secondDetail=new SecondDetail();


    public void run(){
        detailIPool.execute(secondDetail);
    }





}