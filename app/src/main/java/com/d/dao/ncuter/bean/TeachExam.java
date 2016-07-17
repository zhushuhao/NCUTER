package com.d.dao.ncuter.bean;

import java.io.Serializable;

/**
 * Created by dao on 7/15/16.
 * 考试安排
 */
public class TeachExam implements Serializable {
    private String course_code;//课程编码
    private String course_name;//课程名称
    private String exam_site;//考试地点
    private String exam_date;//考试日期
    private String exam_time;//考试时间
    private String mark;//备注

    public TeachExam() {
    }

    @Override
    public String toString() {
        return "TeachExam{" +
                "course_code='" + course_code + '\'' +
                ", course_name='" + course_name + '\'' +
                ", exam_site='" + exam_site + '\'' +
                ", exam_date='" + exam_date + '\'' +
                ", exam_time='" + exam_time + '\'' +
                ", mark='" + mark + '\'' +
                '}';
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

    public String getExam_site() {
        return exam_site;
    }

    public void setExam_site(String exam_site) {
        this.exam_site = exam_site;
    }

    public String getExam_date() {
        return exam_date;
    }

    public void setExam_date(String exam_date) {
        this.exam_date = exam_date;
    }

    public String getExam_time() {
        return exam_time;
    }

    public void setExam_time(String exam_time) {
        this.exam_time = exam_time;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
