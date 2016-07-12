package com.d.dao.ncuter.bean;

import java.io.Serializable;

/**
 * Created by dao on 7/6/16.
 * 每一个课程的简介包含的12个小item
 */
public class CourseSummary implements Serializable{
    private String title;
    private String address;

    @Override
    public String toString() {
        return "CourseSummary{" +
                "title='" + title + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public CourseSummary(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public CourseSummary(String title, String address) {

        this.title = title;
        this.address = address;
    }
}
