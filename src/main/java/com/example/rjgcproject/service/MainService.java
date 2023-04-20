package com.example.rjgcproject.service;
import com.example.rjgcproject.dao.UserDao;
import com.example.rjgcproject.entity.Additive;
import com.example.rjgcproject.entity.Material;
import com.example.rjgcproject.entity.Pack;
import com.example.rjgcproject.entity.User;

import java.util.List;

public interface MainService {
    //按用户名和密码查询用户
    User findUserByUsernameAndPassowrd(String username,String password);
    //增加用户
    User addUser(User user);

    /*原料*/
    //显示全部数据
    List<Material> findAll();
    //增加数据
    Material addMaterial(Material material);
    //删除数据
    List<Material> deleteMaterialByMtid(int mt_id);
    List<Material> deleteMaterialByMtname(String mt_name);
    List<Material> deleteMaterialByMtstaff(String mt_staff);
    //查询数据
    List<Material> findMaterialByMtid(int mt_id);
    List<Material> findMaterialByMtname(String mt_name);
    List<Material> findMaterialByMtqualified(String mt_qualified);
    List<Material> findMaterialByMtstaff(String mt_staff);
    //修改数据
    Material updateMaterial(Material material, String mt_name, String mt_check, String mt_qualified, String mt_staff,String datetime);

    /*添加剂*/
    //显示全部数据
    List<Additive> findAll2();
    //增加数据
    Additive addAdditive(Additive additive);
    //删除数据
    List<Additive> deleteAdditiveByAdid(int ad_id);
    List<Additive> deleteAdditiveByAdname(String ad_name);
    List<Additive> deleteAdditiveByAdstaff(String ad_staff);
    //查询数据
    List<Additive> findAdditiveByAdid(int ad_id);
    List<Additive> findAdditiveByAdname(String ad_name);
    List<Additive> findAdditiveByAdqualified(String ad_qualified);
    List<Additive> findAdditiveByAdstaff(String ad_staff);
    //修改数据
    Additive updateAdditive(Additive additive, String ad_name, String ad_check, String ad_qualified, String ad_staff,String datetime);

    /*包装*/
    //显示全部数据
    List<Pack> findAll3();
    //增加数据
    Pack addPack(Pack pack);
    //删除数据
    List<Pack> deletePackByPkid(int pk_id);
    List<Pack> deletePackByPkname(String pk_name);
    List<Pack> deletePackByPkstaff(String pk_staff);
    //查询数据
    List<Pack> findPackByPkid(int pk_id);
    List<Pack> findPackByPkname(String pk_name);
    List<Pack> findPackByPkqualified(String pk_qualified);
    List<Pack> findPackByPkstaff(String pk_staff);
    //修改数据
    Pack updatePack(Pack pack, String pk_name, String pk_check, String pk_qualified, String pk_staff,String datetime);

}
