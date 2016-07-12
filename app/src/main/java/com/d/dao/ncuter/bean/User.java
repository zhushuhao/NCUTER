package com.d.dao.ncuter.bean;

import java.io.Serializable;

/**
 * Created by dao on 7/11/16.
 * 用户信息
 */
public class User implements Serializable{
    private String user;//用户名
    private String pass;//密码
    private int type;//密码类型0多模式 1教学信息网
    private String name;//名字

    public User(String user, String pass, int type) {
        this.user = user;
        this.pass = pass;
        this.type = type;
    }

    public User(String user, String pass, int type, String name) {
        this.user = user;
        this.pass = pass;
        this.type = type;
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
