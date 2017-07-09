package com.example.kashish.ams;

/**
 * Created by Kashish on 7/17/2016.
 */
public class ListBean {
    String subject, time;

    public ListBean() {
    }

    public ListBean(String subject, String time) {
        this.subject = subject;
        this.time = time;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
