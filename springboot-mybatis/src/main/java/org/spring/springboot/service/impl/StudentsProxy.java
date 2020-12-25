package org.spring.springboot.service.impl;


import org.spring.springboot.service.Person;

/**
 * Created by Mapei on 2018/11/7
 * 学生代理类，也实现了Person接口，保存一个学生实体，这样就可以代理学生产生行为
 */
public class StudentsProxy implements Person {
    //被代理的学生
    Student stu;

    public StudentsProxy(Person stu) {
        // 只代理学生对象
        if (stu.getClass() == Student.class) {
            this.stu = (Student) stu;
        }
    }

    //代理交作业，调用被代理学生的交作业的行为
    public void giveTask() {
        stu.giveTask();
    }
}
