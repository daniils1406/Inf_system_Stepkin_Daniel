package threads;

import entities.FirstDetail;
import entities.SecondDetail;
import entities.ThirdDetail;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class DetailsIIIPools extends Thread{
    ThreadPoolExecutor detailIPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
//    public static List<Integer> thirdDetails=new LinkedList<>();

    ThirdDetail thirdDetail=new ThirdDetail();


    public void run(){
        detailIPool.execute(thirdDetail);
    }






}