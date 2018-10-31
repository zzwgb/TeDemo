package com.example.administrator.tedemo.model;

/**
 * Created by fan on 2017/1/11.
 */
public class AddBean {

    int id;
    String name;
    String age;
    String sex;
    String test;

    public AddBean(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }
}
