package com.example.rjgcproject.entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@ApiModel(value = "原料",description = "原料实体类")
@Entity
@Table(name="material")
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value="原料序号",name="mtid",required=true)
    private int mtid;
    @ApiModelProperty(value="原料名称",name="mtname",required=true)
    private String mtname;
    @ApiModelProperty(value="原料检测结果",name="mtcheck",required=true)
    private String mtcheck;
    @ApiModelProperty(value="原料是否达标",name="mtqualified",required=true)
    private String mtqualified;
    @ApiModelProperty(value="原料登记员工",name="mtstaff",required=true)
    private String mtstaff;
    @ApiModelProperty(value="时间",name="datetime",required=true)
    private String datetime;

    public Material(String mtname, String mtcheck, String mtqualified,String mtstaff,String datetime) {
        this.mtname=mtname;
        this.mtcheck=mtcheck;
        this.mtqualified=mtqualified;
        this.mtstaff=mtstaff;
        this.datetime=datetime;
    }
    public Material() {
    }

    public int getMt_id() {
        return mtid;
    }

    public void setMt_id(int mtid) {
        this.mtid = mtid;
    }

    public String getMt_name() {
        return mtname;
    }

    public void setMt_name(String mtname) {
        this.mtname = mtname;
    }

    public String getMt_check() {
        return mtcheck;
    }

    public void setMt_check(String mtcheck) {
        this.mtcheck = mtcheck;
    }

    public String getMt_qualified() {
        return mtqualified;
    }

    public void setMt_qualified(String mtqualified) {
        this.mtqualified = mtqualified;
    }

    public String getMt_staff() {
        return mtstaff;
    }

    public void setMt_staff(String mtstaff) {
        this.mtstaff = mtstaff;
    }

    public String getDatetime(){return datetime;}

    public void setDatetime(String datetime){this.datetime=datetime;}
}
