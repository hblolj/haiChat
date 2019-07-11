package com.hblolj.haichat.dataobject;

import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_group_member")
public class GroupMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String alias;

    @Column(name = "createAt")
    private Date createat;

    @Column(name = "groupId")
    private String groupid;

    @Column(name = "notifyLevel")
    private Integer notifylevel;

    @Column(name = "permissionType")
    private Integer permissiontype;

    @Column(name = "updateAt")
    private Date updateat;

    @Column(name = "userId")
    private String userid;

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
     * @return notifyLevel
     */
    public Integer getNotifylevel() {
        return notifylevel;
    }

    /**
     * @param notifylevel
     */
    public void setNotifylevel(Integer notifylevel) {
        this.notifylevel = notifylevel;
    }

    /**
     * @return permissionType
     */
    public Integer getPermissiontype() {
        return permissiontype;
    }

    /**
     * @param permissiontype
     */
    public void setPermissiontype(Integer permissiontype) {
        this.permissiontype = permissiontype;
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
     * @return userId
     */
    public String getUserid() {
        return userid;
    }

    /**
     * @param userid
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }
}