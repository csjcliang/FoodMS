package com.example.rjgcproject.entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@ApiModel(value = "包装",description = "包装实体类")
@Entity
@Table(name="pack")
public class Pack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value="包装序号",name="pkid",required=true)
    private int pkid;
    @ApiModelProperty(value="包装名称",name="pkname",required=true)
    private String pkname;
    @ApiModelProperty(value="包装检测结果",name="pkcheck",required=true)
    private String pkcheck;
    @ApiModelProperty(value="包装是否达标",name="pkqualified",required=true)
    private String pkqualified;
    @ApiModelProperty(value="包装登记员工",name="pkstaff",required=true)
    private String pkstaff;
    @ApiModelProperty(value="时间",name="datetime",required=true)
    private String datetime;

    public Pack(String pkname, String pkcheck, String pkqualified,String pkstaff,String datetime) {
        this.pkname=pkname;
        this.pkcheck=pkcheck;
        this.pkqualified=pkqualified;
        this.pkstaff=pkstaff;
        this.datetime=datetime;
    }
    public Pack() {
    }

    public int getPk_id() {
        return pkid;
    }

    public void setPk_id(int pkid) {
        this.pkid = pkid;
    }

    public String getPk_name() {
        return pkname;
    }

    public void setPk_name(String pkname) {
        this.pkname = pkname;
    }

    public String getPk_check() {
        return pkcheck;
    }

    public void setPk_check(String pkcheck) {
        this.pkcheck = pkcheck;
    }

    public String getPk_qualified() {
        return pkqualified;
    }

    public void setPk_qualified(String pkqualified) {
        this.pkqualified = pkqualified;
    }

    public String getPk_staff() {
        return pkstaff;
    }

    public void setPk_staff(String pkstaff) {
        this.pkstaff = pkstaff;
    }

    public String getDatetime(){return datetime;}

    public void setDatetime(String datetime){this.datetime=datetime;}
}
