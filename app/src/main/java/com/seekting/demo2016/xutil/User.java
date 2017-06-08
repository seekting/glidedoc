package com.seekting.demo2016.xutil;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;

/**
 * Created by seekting on 17-2-10.
 */

public class User {

    @Id(column = "_ID")
    String id;
    @Column(column = "name")
    String name;
    @Column(column = "age")
    int age;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}