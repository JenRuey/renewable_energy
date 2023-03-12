package com.liteon.renewable_energy.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "history", schema = "public")
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "history_generator")
    @SequenceGenerator(name = "history_generator", sequenceName = "history_id_seq", allocationSize = 1)
    private Integer id;
    private Date datetime;
    private String event_tagname;
    private BigDecimal value;

    public History() {
    }

    public History(Date datetime, String event_tagname, BigDecimal value) {
        this.datetime = datetime;
        this.event_tagname = event_tagname;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getEvent_tagname() {
        return event_tagname;
    }

    public void setEvent_tagname(String event_tagname) {
        this.event_tagname = event_tagname;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
