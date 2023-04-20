package com.example.rjgcproject.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@ApiModel(value = "用户",description = "用户实体类")
@Entity
@Table(name="loginuser")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value="序号",name="id",required=true)
    private int id;
    @ApiModelProperty(value="用户名",name="username",required=true)
    private String username;
    @ApiModelProperty(value="密码",name="password",required=true)
    private String password;
    @ApiModelProperty(value="身份",name="identity",required=true)
    private String identity;

    public User(String username, String password, String identity) {
        this.username = username;
        this.password = password;
        this.identity = identity;
    }
    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }
}
