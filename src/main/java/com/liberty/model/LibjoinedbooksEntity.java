package com.liberty.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * User: Dimitr
 * Date: 24.04.2017
 * Time: 21:29
 */
@Entity
@Table(name = "libjoinedbooks", schema = "neurolib")
public class LibjoinedbooksEntity {
    private int id;
    private Timestamp time;
    private int badId;
    private int goodId;
    private Integer realId;

    @Id
    @Column(name = "Id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Time")
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Basic
    @Column(name = "BadId")
    public int getBadId() {
        return badId;
    }

    public void setBadId(int badId) {
        this.badId = badId;
    }

    @Basic
    @Column(name = "GoodId")
    public int getGoodId() {
        return goodId;
    }

    public void setGoodId(int goodId) {
        this.goodId = goodId;
    }

    @Basic
    @Column(name = "realId")
    public Integer getRealId() {
        return realId;
    }

    public void setRealId(Integer realId) {
        this.realId = realId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LibjoinedbooksEntity that = (LibjoinedbooksEntity) o;

        if (id != that.id) return false;
        if (badId != that.badId) return false;
        if (goodId != that.goodId) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;
        if (realId != null ? !realId.equals(that.realId) : that.realId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + badId;
        result = 31 * result + goodId;
        result = 31 * result + (realId != null ? realId.hashCode() : 0);
        return result;
    }
}
