package com.tzh.entity;

import java.io.Serializable;

public class Business implements Serializable{

    private Integer order;//id顺序为0,id倒序为1

    private User user;

    private Role role;

    private Permission permission;

    @Override
    public String toString() {
        return "Business{" +
                "order=" + order +
                ", user=" + user +
                ", role=" + role +
                ", permission=" + permission +
                '}';
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
