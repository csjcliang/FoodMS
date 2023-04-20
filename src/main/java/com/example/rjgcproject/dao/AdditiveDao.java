package com.example.rjgcproject.dao;
import com.example.rjgcproject.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdditiveDao extends JpaRepository<Additive,Integer>{
    //显示全部数据
    @Query(value = "select * from additive",nativeQuery = true)
    List<Additive> findAll2();
    //增加或修改数据(主键不存在则增加，存在则修改)
    Additive save(Additive additive);
    //删除数据
    List<Additive> deleteAdditiveByAdid(int adid);
    List<Additive> deleteAdditiveByAdname(String adname);
    List<Additive> deleteAdditiveByAdstaff(String adstaff);
    //查询数据
    List<Additive> findAdditiveByAdid(int adid);
    List<Additive> findAdditiveByAdname(String adname);
    List<Additive> findAdditiveByAdqualified(String adqualified);
    List<Additive> findAdditiveByAdstaff(String adstaff);
}
