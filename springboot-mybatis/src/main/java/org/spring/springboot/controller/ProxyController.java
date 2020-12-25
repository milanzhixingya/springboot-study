package org.spring.springboot.controller;

import org.spring.springboot.domain.City;
import org.spring.springboot.service.Person;
import org.spring.springboot.service.impl.ReflexServiceImpl;
import org.spring.springboot.service.impl.Student;
import org.spring.springboot.service.impl.StudentsProxy;

import java.lang.reflect.Field;

/***
 * 测试静态代理和动态代理
 */
public class ProxyController {
    public static void main(String[] args) {
        //静态代理
        //staticProxy();
        //reflexTest();
        getClassAttribute();
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
}
