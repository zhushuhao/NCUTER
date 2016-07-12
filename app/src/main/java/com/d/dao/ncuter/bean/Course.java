package com.d.dao.ncuter.bean;

import java.io.Serializable;

/**
 * Created by dao on 7/3/16.
 * 课程列表
 */
public class Course implements Serializable{

    private String teacher_name;//教师姓名
    private String course_name;//课程民称
    private String course_address;//课程下一级地址
    private String course_code;//课程编号


    public Course() {
    }

    public Course(String teacher_name, String course_name, String course_address, String course_code) {
        this.teacher_name = teacher_name;
        this.course_name = course_name;
        this.course_address = course_address;
        this.course_code = course_code;
    }

    public Course(String course_address, String course_name) {
        this.course_address = course_address;
        this.course_name = course_name;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCourse_address() {
        return course_address;
    }

    public void setCourse_address(String course_address) {
        this.course_address = course_address;
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    @Override
    public String toString() {
        return "Course{" +
                "teacher_name='" + teacher_name + '\'' +
                ", course_name='" + course_name + '\'' +
                ", course_address='" + course_address + '\'' +
                ", course_code='" + course_code + '\'' +
                '}';
    }
}
