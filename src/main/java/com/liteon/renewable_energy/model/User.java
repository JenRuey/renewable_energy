package com.liteon.renewable_energy.model;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "user", schema = "public")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="user_generator")
    @SequenceGenerator(name = "user_generator", sequenceName = "user_id_seq", allocationSize = 1)
    private Integer id;
    @Column(name="email")
    private String email;
    @Column(name="hash_key")
    private String hash_key;
    private String access_token;
    private String refresh_token;
    private Date modified_datetime;

    public User(){}
    public User(String email, String hash_key, Date modified_datetime) {
        this.email = email;
        this.hash_key = hash_key;
        this.modified_datetime = modified_datetime;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", hash_key='" + hash_key + '\'' +
                ", access_token='" + access_token + '\'' +
                ", refresh_token='" + refresh_token + '\'' +
                ", modified_date=" + modified_datetime +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHash_key() {
        return hash_key;
    }

    public void setHash_key(String hash_key) {
        this.hash_key = hash_key;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public Date getModified_datetime() {
        return modified_datetime;
    }

    public void setModified_datetime(Date modified_date) {
        this.modified_datetime = modified_date;
    }
}
