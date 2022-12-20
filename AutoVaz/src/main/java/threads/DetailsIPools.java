package threads;

import entities.FirstDetail;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class DetailsIPools extends Thread{
    static ThreadPoolExecutor detailIPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
//    public static List<Integer> firstDetails=new LinkedList<>();

    static final FirstDetail firstDetail=new FirstDetail();


    public void run(){
        detailIPool.execute(firstDetail);
    }


    public static void startProduction(){
        detailIPool.execute(firstDetail);
    }






}