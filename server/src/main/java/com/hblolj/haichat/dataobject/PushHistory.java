package com.hblolj.haichat.dataobject;

import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_push_history")
public class PushHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "arrivalAt")
    private Date arrivalat;

    @Column(name = "createdAt")
    private Date createdat;

    @Column(name = "entityType")
    private Integer entitytype;

    @Column(name = "receiverId")
    private String receiverid;

    @Column(name = "receiverPushId")
    private String receiverpushid;

    @Column(name = "senderId")
    private String senderid;

    @Column(name = "updateAt")
    private Date updateat;

    private byte[] entity;

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
     * @return arrivalAt
     */
    public Date getArrivalat() {
        return arrivalat;
    }

    /**
     * @param arrivalat
     */
    public void setArrivalat(Date arrivalat) {
        this.arrivalat = arrivalat;
    }

    /**
     * @return createdAt
     */
    public Date getCreatedat() {
        return createdat;
    }

    /**
     * @param createdat
     */
    public void setCreatedat(Date createdat) {
        this.createdat = createdat;
    }

    /**
     * @return entityType
     */
    public Integer getEntitytype() {
        return entitytype;
    }

    /**
     * @param entitytype
     */
    public void setEntitytype(Integer entitytype) {
        this.entitytype = entitytype;
    }

    /**
     * @return receiverId
     */
    public String getReceiverid() {
        return receiverid;
    }

    /**
     * @param receiverid
     */
    public void setReceiverid(String receiverid) {
        this.receiverid = receiverid;
    }

    /**
     * @return receiverPushId
     */
    public String getReceiverpushid() {
        return receiverpushid;
    }

    /**
     * @param receiverpushid
     */
    public void setReceiverpushid(String receiverpushid) {
        this.receiverpushid = receiverpushid;
    }

    /**
     * @return senderId
     */
    public String getSenderid() {
        return senderid;
    }

    /**
     * @param senderid
     */
    public void setSenderid(String senderid) {
        this.senderid = senderid;
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

    /**
     * @return entity
     */
    public byte[] getEntity() {
        return entity;
    }

    /**
     * @param entity
     */
    public void setEntity(byte[] entity) {
        this.entity = entity;
    }
}