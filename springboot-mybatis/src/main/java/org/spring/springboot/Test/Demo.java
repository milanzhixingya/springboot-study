package org.spring.springboot.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.*;

public class Demo {
    public static void main(String[] args){
       /* ThreadPoolExecutor fixedThreadPool = new ThreadPoolExecutor(3,5,1000,
                TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>(), Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());*/
        ThreadPoolExecutor fixedThreadPool = new ThreadPoolExecutor(3,8,1000,
                TimeUnit.MILLISECONDS,new ArrayBlockingQueue<Runnable>(5), Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());
        for(int i = 0;i<5;i++){
            fixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName());
                    //System.out.println();
                }
            });
        }

    }
}
