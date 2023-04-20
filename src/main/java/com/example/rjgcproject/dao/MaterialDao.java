package com.example.rjgcproject.dao;
import com.example.rjgcproject.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialDao extends JpaRepository<Material,Integer>{
    //显示全部数据
    @Query(value = "select * from material",nativeQuery = true)
    List<Material> findAll();
    //增加或修改数据(主键不存在则增加，存在则修改)
    Material save(Material material);
    //删除数据
    List<Material> deleteMaterialByMtid(int mtid);
    List<Material> deleteMaterialByMtname(String mtname);
    List<Material> deleteMaterialByMtstaff(String mtstaff);
    //查询数据
    List<Material> findMaterialByMtid(int mtid);
    List<Material> findMaterialByMtname(String mtname);
    List<Material> findMaterialByMtqualified(String mtqualified);
    List<Material> findMaterialByMtstaff(String mtstaff);
}
