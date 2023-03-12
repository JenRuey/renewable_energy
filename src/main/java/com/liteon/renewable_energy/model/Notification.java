package com.liteon.renewable_energy.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "notification", schema = "public")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification_generator")
    @SequenceGenerator(name = "notification_generator", sequenceName = "notification_id_seq", allocationSize = 1)
    private Integer id;
    private Integer user_id;
    private String message;
    private Date datetime;
    public Notification(){}

    public Notification(Integer user_id, String message, Date datetime) {
        this.user_id = user_id;
        this.message = message;
        this.datetime = datetime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }
}
