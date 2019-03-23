package com.tzh.entity;

import net.sf.json.JSONObject;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    //0:禁止登录
    public static final Long _0 = new Long(0);
    //1:有效
    public static final Long _1 = new Long(1);
    private Long id;
    /**昵称*/
    private String username;
    /**邮箱 | 登录帐号*/
    private String email;
    /**密码*/
    private transient String password;
    /**创建时间*/
    private Timestamp createTime;
    /**最后登录时间*/
    private Timestamp lastLoginTime;
    /**1:有效，0:禁止登录*/
    private Long status;

    //与角色建立关系
    private Set<Role> roleSet=new HashSet<>();

    public Set<Role> getRoleSet() {
        return roleSet;
    }

    public void setRoleSet(Set<Role> roleSet) {
        this.roleSet = roleSet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public Long getStatus() {
        return status;
    }
    public void setStatus(Long status) {
        this.status = status;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public Timestamp getLastLoginTime() {
        return lastLoginTime;
    }

    public String getPassword() {
        return password;
    }


    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public void setLastLoginTime(Timestamp lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String toString(){
        return JSONObject.fromObject(this).toString();
    }

}
