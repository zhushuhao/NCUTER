package com.d.dao.ncuter.bean;

import java.io.Serializable;

/**
 * Created by dao on 7/7/16.
 * 课件信息
 */
public class CourseWare implements Serializable{
    private String course_name;//课程名
    private String address;//下载地址
    private String size;//大小
    private String time;//时间
    private String name;//课件名


    public CourseWare(String course_name, String address, String size, String time ,String name) {
        this.course_name = course_name;
        this.address = address;
        this.size = size;
        this.time = time;
        this.name = name;
    }

    public CourseWare() {
    }

    public CourseWare(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "CourseWare{" +
                "course_name='" + course_name + '\'' +
                ", address='" + address + '\'' +
                ", size='" + size + '\'' +
                ", time='" + time + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
