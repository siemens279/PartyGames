package com.example.partygames;

public class Question {

    public Integer id;
    public String idFirebase;
    public Integer colUser;
    public Integer hard;
    public String subject;
    public String text;

    public Question(Integer id, String idFirebase, Integer colUser, Integer hard, String subject, String text) {
        this.id = id;
        this.idFirebase = idFirebase;
        this.colUser = colUser;
        this.hard = hard;
        this.subject = subject;
        this.text = text;
    }

    public Question() {
    }

    public String getIdFirebase() {
        return idFirebase;
    }

    public void setIdFirebase(String idFirebase) {
        this.idFirebase = idFirebase;
    }

    public Integer getId() {
        return id;
    }

    public Integer getColUser() {
        return colUser;
    }

    public Integer getHard() {
        return hard;
    }

    public String getSubject() {
        return subject;
    }

    public String getText() {
        return text;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setColUser(Integer colUser) {
        this.colUser = colUser;
    }

    public void setHard(Integer hard) {
        this.hard = hard;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setText(String text) {
        this.text = text;
    }
}
