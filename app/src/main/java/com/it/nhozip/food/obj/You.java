package com.it.nhozip.food.obj;

import java.io.Serializable;

/**
 * Created by huyen on 9/14/2016.
 */
public class You implements Serializable {
    private String id;
    private String name;
    private String avata;
    private String user;
    private String pass;

    public You(String id, String name, String avata, String user, String pass) {
        this.id = id;
        this.name = name;
        this.avata = avata;
        this.user = user;
        this.pass = pass;
    }

    public You() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvata() {
        return avata;
    }

    public void setAvata(String avata) {
        this.avata = avata;
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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "You{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", avata='" + avata + '\'' +
                ", user='" + user + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }
}
