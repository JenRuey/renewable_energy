package com.liteon.renewable_energy.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "event", schema = "public")
public class Event {
    @Id
    private String tagname;
    private Integer location_id;
    private String description;
    private BigDecimal alert_upper;
    private BigDecimal alert_lower;
    private String unit;

    public Event() {
    }

    public String getTagname() {
        return tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }

    public Integer getLocation_id() {
        return location_id;
    }

    public void setLocation_id(Integer location_id) {
        this.location_id = location_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAlert_upper() {
        return alert_upper;
    }

    public void setAlert_upper(BigDecimal alert_upper) {
        this.alert_upper = alert_upper;
    }

    public BigDecimal getAlert_lower() {
        return alert_lower;
    }

    public void setAlert_lower(BigDecimal alert_lower) {
        this.alert_lower = alert_lower;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
