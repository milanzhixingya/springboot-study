package org.spring.springboot.controller;

import org.spring.springboot.domain.City;
import org.spring.springboot.domain.Text;
import org.spring.springboot.service.Person;
import org.spring.springboot.service.impl.ReflexServiceImpl;
import org.spring.springboot.service.impl.Student;
import org.spring.springboot.service.impl.StudentsProxy;

import java.lang.reflect.Field;
import java.sql.Time;
import java.util.concurrent.*;

/***
 * 测试静态代理和动态代理
 */
public class ProxyController {
    public static void main(String[] args) {
        //静态代理
        //staticProxy();
        //reflexTest();
        //getClassAttribute();
        //appointTest();
        //threadTest();
        threadPoolTest();
    }

    /***
     * 静态代理
     */
    public static void staticProxy(){
        //被代理的学生林浅，他的作业上交有代理对象monitor完成
        Person linqian = new Student("林浅");

        //生成代理对象，并将林浅传给代理对象
        Person monitor = new StudentsProxy(linqian);

        //班长代理交作业
        monitor.giveTask();
    }

    /***
     * 反射测试
     * 注意事项：1、构造方法必须写
     * 2、构造方法必须为public
     * 3、构造方法必须无参数
     * 4、类要被访问到，即类应该为public或者能访问的范围
     */
    public static void reflexTest(){
        try {
            Class reflexServiceImpl = Class.forName("org.spring.springboot.service.impl.ReflexServiceImpl");
            ReflexServiceImpl s = (ReflexServiceImpl) reflexServiceImpl.newInstance();
            s.reflexTest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * 动态的获取类的属性
     */
    public static void getClassAttribute(){
       Field[] fields = City.class.getDeclaredFields();
       for(Field field:fields){
            System.out.println(field.getName());
       }
    }

    /***
     * 堆栈的引用存储测试
     */
    public static void appointTest(){
        Text t1 = new Text();
        t1.setWord("Hello");
        t1.printWord();
        System.out.println(t1);
        Text t2 = t1;
        System.out.println(t2);
        t2.setWord("HelloWorld");
        System.out.println(t2);
        t1.printWord();
    }

    /***
     * 多线程
     */
    public static void threadTest(){
        //int count = 0;

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("线程名称："+Thread.currentThread()+"当前数字为："+ 559);
                /*for(int i = 0 ;i<1000;i++){
                    System.out.println("线程名称："+Thread.currentThread()+"当前数字为："+ i);
                }*/
            }
        }).start();

        for(int i = 0 ;i<1000;i++){
            System.out.println("大家好："+i);
        }
    }
    /*public static void threadPoolTest(){
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2,5,1000, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>());
        Future<Integer> futureValue = threadPool.execute(new Runnable() {
            @Override
            public void run() {
                getCount();
            }
        });
    }*/
    /***
     * 线程池实现等待响应时间最慢的请求返回后再继续执行主线程，注意submit里面创建的是callable对象不是runnable
     */
    public static void threadPoolTest(){
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Integer endvalue = 0;
        Future<Object> result = executor.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return getCount();
            }
        });
        try {
            //endvalue = futureValue.get(20,TimeUnit.SECONDS);
           System.out.println(result.get().toString());
           // System.out.println(getCount());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /***
     * futuretask实现等待响应时间最慢的请求返回后再继续执行主线程,，注意FutureTask里面创建的是callable对象不是runnable
     */
    public static void threadPoolTest2(){
        //ExecutorService executor = Executors.newFixedThreadPool(2);

       FutureTask futureTask = new FutureTask(new Callable() {
           @Override
           public Object call() throws Exception {
               return getCount();
           }
       });
        FutureTask futureTask2 = new FutureTask(new Callable() {
            @Override
            public Object call() throws Exception {
                return getCount2();
            }
        });
       new Thread(futureTask).start();
        new Thread(futureTask2).start();
        try {
            System.out.println(futureTask.get().toString());
            System.out.println(futureTask2.get().toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        /*for(int i = 0;i<500;i++){
            System.out.println("主线程"+i);
        }*/
    }
    public static Integer getCount(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("我在这里");
        return 350;
    }
    public static Integer getCount2(){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("我在这里");
        return 450;
    }
}
