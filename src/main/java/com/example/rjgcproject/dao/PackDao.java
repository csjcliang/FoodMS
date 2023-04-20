package com.example.rjgcproject.dao;
import com.example.rjgcproject.entity.*;
import com.example.rjgcproject.entity.Pack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackDao extends JpaRepository<Pack,Integer>{
    //显示全部数据
    @Query(value = "select * from pack",nativeQuery = true)
    List<Pack> findAll3();
    //增加或修改数据(主键不存在则增加，存在则修改)
    Pack save(Pack pack);
    //删除数据
    List<Pack> deletePackByPkid(int pkid);
    List<Pack> deletePackByPkname(String pkname);
    List<Pack> deletePackByPkstaff(String pkstaff);
    //查询数据
    List<Pack> findPackByPkid(int pkid);
    List<Pack> findPackByPkname(String pkname);
    List<Pack> findPackByPkqualified(String pkqualified);
    List<Pack> findPackByPkstaff(String pkstaff);
}



