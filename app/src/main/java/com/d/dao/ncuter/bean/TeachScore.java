package com.d.dao.ncuter.bean;

import java.io.Serializable;

/**
 * Created by dao on 7/14/16.
 * 教学信息网考试成绩信息
 */
public class TeachScore implements Serializable{

    private String year;//年度
    private String season;//季别
    private String class_code;//课程编码
    private String class_name;//课程名称
    private String score_normal;//平时
    private String score_final;//期末
    private String score_overall;//总评
    private String percent_normal;//平时%
    private String exam_nature;//考试性质
    private String credit;//学分
    private String teacher_name;//教师姓名

    public TeachScore() {
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getClass_code() {
        return class_code;
    }

    public void setClass_code(String class_code) {
        this.class_code = class_code;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getScore_normal() {
        return score_normal;
    }

    public void setScore_normal(String score_normal) {
        this.score_normal = score_normal;
    }

    public String getScore_final() {
        return score_final;
    }

    public void setScore_final(String score_final) {
        this.score_final = score_final;
    }

    public String getScore_overall() {
        return score_overall;
    }

    public void setScore_overall(String score_overall) {
        this.score_overall = score_overall;
    }

    public String getPercent_normal() {
        return percent_normal;
    }

    public void setPercent_normal(String percent_normal) {
        this.percent_normal = percent_normal;
    }

    public String getExam_nature() {
        return exam_nature;
    }

    public void setExam_nature(String exam_nature) {
        this.exam_nature = exam_nature;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    @Override
    public String toString() {
        return "TeachScore{" +
                "year='" + year + '\'' +
                ", season='" + season + '\'' +
                ", class_code='" + class_code + '\'' +
                ", class_name='" + class_name + '\'' +
                ", score_normal='" + score_normal + '\'' +
                ", score_final='" + score_final + '\'' +
                ", score_overall='" + score_overall + '\'' +
                ", percent_normal='" + percent_normal + '\'' +
                ", exam_nature='" + exam_nature + '\'' +
                ", credit='" + credit + '\'' +
                ", teacher_name='" + teacher_name + '\'' +
                '}';
    }
}
