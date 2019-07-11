package com.hblolj.haichat.dataobject;

import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_apply")
public class Apply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "applicantId")
    private String applicantid;

    @Column(name = "createdAt")
    private Date createdat;

    private String description;

    @Column(name = "targetId")
    private String targetid;

    private Integer type;

    @Column(name = "updatedAt")
    private Date updatedat;

    private String attach;

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
     * @return applicantId
     */
    public String getApplicantid() {
        return applicantid;
    }

    /**
     * @param applicantid
     */
    public void setApplicantid(String applicantid) {
        this.applicantid = applicantid;
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
}