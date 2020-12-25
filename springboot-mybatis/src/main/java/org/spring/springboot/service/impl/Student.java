package org.spring.springboot.service.impl;


import org.spring.springboot.service.Person;

public class Student implements Person {
    private String name;
    public Student(String name) {
        this.name = name;
    }

    public void giveTask() {
        System.out.println(name + "交语文作业");
    }
}
