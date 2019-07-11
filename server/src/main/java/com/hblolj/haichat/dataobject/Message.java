package com.hblolj.haichat.dataobject;

import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String attach;

    @Column(name = "createdAt")
    private Date createdat;

    @Column(name = "groupId")
    private String groupid;

    @Column(name = "receiverId")
    private String receiverid;

    @Column(name = "senderId")
    private String senderid;

    private Integer type;

    @Column(name = "updatedAt")
    private Date updatedat;

    private String content;

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
     * @return attach
     */
    public String getAttach() {
        return attach;
    }

    /**
     * @param attach
     */
    public void setAttach(String attach) {
        this.attach = attach;
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
     * @return groupId
     */
    public String getGroupid() {
        return groupid;
    }

    /**
     * @param groupid
     */
    public void setGroupid(String groupid) {
        this.groupid = groupid;
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
     * @return type
     */
    public Integer getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * @return updatedAt
     */
    public Date getUpdatedat() {
        return updatedat;
    }

    /**
     * @param updatedat
     */
    public void setUpdatedat(Date updatedat) {
        this.updatedat = updatedat;
    }

    /**
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }
}