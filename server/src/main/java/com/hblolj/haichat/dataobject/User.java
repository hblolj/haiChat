package com.hblolj.haichat.dataobject;

import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "createAt")
    private Date createat;

    private String description;

    @Column(name = "lastReceivedAt")
    private Date lastreceivedat;

    private String name;

    private String password;

    private String phone;

    private String portrait;

    @Column(name = "pushId")
    private String pushid;

    private Integer sex;

    private String token;

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
     * @return lastReceivedAt
     */
    public Date getLastreceivedat() {
        return lastreceivedat;
    }

    /**
     * @param lastreceivedat
     */
    public void setLastreceivedat(Date lastreceivedat) {
        this.lastreceivedat = lastreceivedat;
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
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return portrait
     */
    public String getPortrait() {
        return portrait;
    }

    /**
     * @param portrait
     */
    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    /**
     * @return pushId
     */
    public String getPushid() {
        return pushid;
    }

    /**
     * @param pushid
     */
    public void setPushid(String pushid) {
        this.pushid = pushid;
    }

    /**
     * @return sex
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * @param sex
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * @return token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token
     */
    public void setToken(String token) {
        this.token = token;
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