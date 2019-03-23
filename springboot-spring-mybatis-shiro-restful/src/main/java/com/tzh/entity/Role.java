package com.tzh.entity;

import java.util.HashSet;
import java.util.Set;

public class Role {

    private Integer id;

    //角色名称
    private String roleName;

    private String sentence; //判断该用户是否有这个角色

    //与权限建立关系
    private Set<Permission> permissionSet =new HashSet<>();

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                ", sentence='" + sentence + '\'' +
                ", permissionSet=" + permissionSet +
                '}';
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }


    public Set<Permission> getPermissionSet() {
        return permissionSet;
    }

    public void setPermissionSet(Set<Permission> permissionSet) {
        this.permissionSet = permissionSet;
    }

}
