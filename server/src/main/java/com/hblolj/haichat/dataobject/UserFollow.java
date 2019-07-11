package com.hblolj.haichat.dataobject;

import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_user_follow")
public class UserFollow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String alias;

    @Column(name = "createAt")
    private Date createat;

    @Column(name = "originId")
    private String originid;

    @Column(name = "targetId")
    private String targetid;

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
     * @return alias
     */
    public String getAlias() {
        return alias;
    }

    /**
     * @param alias
     */
    public void setAlias(String alias) {
        this.alias = alias;
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
     * @return originId
     */
    public String getOriginid() {
        return originid;
    }

    /**
     * @param originid
     */
    public void setOriginid(String originid) {
        this.originid = originid;
    }

    /**
     * @return targetId
     */
    public String getTargetid() {
        return targetid;
    }

    /**
     * @param targetid
     */
    public void setTargetid(String targetid) {
        this.targetid = targetid;
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