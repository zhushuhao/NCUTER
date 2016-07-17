package com.d.dao.ncuter.bean;

import java.io.Serializable;

/**
 * Created by dao on 7/14/16.
 * 教学信息网个人信息
 */
public class PersonInfo implements Serializable{
    private String student_id;//学号
    private String name;//姓名
    private String id_number;//身份证号码
    private String entry_class;//入学班级
    private String sex;//性别
    private String entry_year;// 入学年份
    private String season;//季别
    private String default_class;//默认班级
    private String major;//专业
    private String major_field;//专业方向


    public PersonInfo() {
    }

    public PersonInfo(String student_id) {
        this.student_id = student_id;
    }


    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId_number() {
        return id_number;
    }

    public void setId_number(String id_number) {
        this.id_number = id_number;
    }

    public String getEntry_class() {
        return entry_class;
    }

    public void setEntry_class(String entry_class) {
        this.entry_class = entry_class;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEntry_year() {
        return entry_year;
    }

    public void setEntry_year(String entry_year) {
        this.entry_year = entry_year;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getDefault_class() {
        return default_class;
    }

    public void setDefault_class(String default_class) {
        this.default_class = default_class;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getMajor_field() {
        return major_field;
    }

    public void setMajor_field(String major_field) {
        this.major_field = major_field;
    }


    @Override
    public String toString() {
        return "PersonInfo{" +
                "student_id='" + student_id + '\'' +
                ", name='" + name + '\'' +
                ", id_number='" + id_number + '\'' +
                ", entry_class='" + entry_class + '\'' +
                ", sex='" + sex + '\'' +
                ", entry_year='" + entry_year + '\'' +
                ", season='" + season + '\'' +
                ", default_class='" + default_class + '\'' +
                ", major='" + major + '\'' +
                ", major_field='" + major_field + '\'' +
                '}';
    }
}
