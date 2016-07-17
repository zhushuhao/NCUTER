package com.d.dao.ncuter.bean;

import java.io.Serializable;

/**
 * Created by dao on 7/14/16.
 * 绩点
 */
public class TeachGpa implements Serializable {
    private String course_type;// 课程大类
    private String course_code;//课程编码
    private String course_name;//课程名称
    private String course_credit;//课程学分
    private String score;//成绩
    private String gpa;//绩点
    private String remark;//备注

    public TeachGpa() {

    }

    public TeachGpa(String course_type, String course_code, String course_name,
                    String course_credit, String score, String gpa, String remark) {
        this.course_type = course_type;
        this.course_code = course_code;
        this.course_name = course_name;
        this.course_credit = course_credit;
        this.score = score;
        this.gpa = gpa;
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "TeachGpa{" +
                "course_type='" + course_type + '\'' +
                ", course_code='" + course_code + '\'' +
                ", course_name='" + course_name + '\'' +
                ", course_credit='" + course_credit + '\'' +
                ", score='" + score + '\'' +
                ", gpa='" + gpa + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }

    public String getCourse_type() {
        return course_type;
    }

    public void setCourse_type(String course_type) {
        this.course_type = course_type;
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCourse_credit() {
        return course_credit;
    }

    public void setCourse_credit(String course_credit) {
        this.course_credit = course_credit;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getGpa() {
        return gpa;
    }

    public void setGpa(String gpa) {
        this.gpa = gpa;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
