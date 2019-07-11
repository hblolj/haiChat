package com.hblolj.haichat.dataobject;

import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_group")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "createAt")
    private Date createat;

    private String description;

    private String name;

    @Column(name = "ownerId")
    private String ownerid;

    private String picture;

    @Column(name = "updateAt")
    private Date updateat;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return createAt
     */
    public Date getCreateat() {
        return createat;
    }

    /**
     * @param createat
     */
    public void setCreateat(Date createat) {
        this.createat = createat;
    }

    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return ownerId
     */
    public String getOwnerid() {
        return ownerid;
    }

    /**
     * @param ownerid
     */
    public void setOwnerid(String ownerid) {
        this.ownerid = ownerid;
    }

    /**
     * @return picture
     */
    public String getPicture() {
        return picture;
    }

    /**
     * @param picture
     */
    public void setPicture(String picture) {
        this.picture = picture;
    }

    /**
     * @return updateAt
     */
    public Date getUpdateat() {
        return updateat;
    }

    /**
     * @param updateat
     */
    public void setUpdateat(Date updateat) {
        this.updateat = updateat;
    }
}