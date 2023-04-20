package com.example.rjgcproject.entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@ApiModel(value = "添加剂",description = "添加剂实体类")
@Entity
@Table(name="additive")
public class Additive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value="添加剂序号",name="adid",required=true)
    private int adid;
    @ApiModelProperty(value="添加剂名称",name="adname",required=true)
    private String adname;
    @ApiModelProperty(value="添加剂最大使用量",name="adcheck",required=true)
    private String adcheck;
    @ApiModelProperty(value="添加剂是否达标",name="adqualified",required=true)
    private String adqualified;
    @ApiModelProperty(value="添加剂登记员工",name="adstaff",required=true)
    private String adstaff;
    @ApiModelProperty(value="时间",name="datetime",required=true)
    private String datetime;

    public Additive(String adname, String adcheck, String adqualified,String adstaff,String datetime) {
        this.adname=adname;
        this.adcheck=adcheck;
        this.adqualified=adqualified;
        this.adstaff=adstaff;
        this.datetime=datetime;
    }
    public Additive() {
    }

    public int getAd_id() {
        return adid;
    }

    public void setAd_id(int adid) {
        this.adid = adid;
    }

    public String getAd_name() {
        return adname;
    }

    public void setAd_name(String adname) {
        this.adname = adname;
    }

    public String getAd_check() {
        return adcheck;
    }

    public void setAd_check(String adcheck) {
        this.adcheck = adcheck;
    }

    public String getAd_qualified() {
        return adqualified;
    }

    public void setAd_qualified(String adqualified) {
        this.adqualified = adqualified;
    }

    public String getAd_staff() {
        return adstaff;
    }

    public void setAd_staff(String adstaff) {
        this.adstaff = adstaff;
    }

    public String getDatetime(){return datetime;}

    public void setDatetime(String datetime){this.datetime=datetime;}
}
