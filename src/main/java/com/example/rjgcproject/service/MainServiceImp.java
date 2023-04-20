package com.example.rjgcproject.service;

import com.example.rjgcproject.dao.*;
import com.example.rjgcproject.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainServiceImp implements MainService {
    @Autowired
    private UserDao userDao;
    //匹配用户名和密码
    public User findUserByUsernameAndPassowrd(String username, String password) {
        return userDao.findUserByUsernameAndPassword(username,password);
    }
    //注册保存
    public User addUser(User user){
        return userDao.save(user);
    }
    /*原料*/
    @Autowired
    private MaterialDao materialDao;
    //显示所有数据
    @Override
    public List<Material> findAll() {
        return materialDao.findAll();
    }
    //增加数据
    @Override
    public Material addMaterial(Material material){
        return materialDao.save(material);
    }
    //删除数据
    @Override
    public List<Material> deleteMaterialByMtid(int mt_id) {
        return materialDao.deleteMaterialByMtid(mt_id);
    }
    @Override
    public List<Material> deleteMaterialByMtname(String mt_name){
        return materialDao.deleteMaterialByMtname(mt_name);
    }
    @Override
    public List<Material> deleteMaterialByMtstaff(String mt_staff){
        return materialDao.deleteMaterialByMtstaff(mt_staff);
    }
    //查询数据
    public List<Material> findMaterialByMtid(int mt_id){
        return materialDao.findMaterialByMtid(mt_id);
    }
    public List<Material> findMaterialByMtname(String mt_name){
        return materialDao.findMaterialByMtname(mt_name);
    }
    public List<Material> findMaterialByMtqualified(String mt_qualified){
        return materialDao.findMaterialByMtqualified(mt_qualified);
    }
    public List<Material> findMaterialByMtstaff(String mt_staff){
        return materialDao.findMaterialByMtstaff(mt_staff);
    }
    //修改数据
    public Material updateMaterial(Material material, String mt_name, String mt_check, String mt_qualified, String mt_staff, String datetime){
        material.setMt_name(mt_name);
        material.setMt_check(mt_check);
        material.setMt_qualified(mt_qualified);
        material.setMt_staff(mt_staff);
        material.setDatetime(datetime);
        return materialDao.save(material);
    }

    /*添加剂*/
    @Autowired
    private AdditiveDao additiveDao;
    //显示所有数据
    @Override
    public List<Additive> findAll2() {
        return additiveDao.findAll2();
    }
    //增加数据
    @Override
    public Additive addAdditive(Additive additive){
        return additiveDao.save(additive);
    }
    //删除数据
    @Override
    public List<Additive> deleteAdditiveByAdid(int ad_id) {
        return additiveDao.deleteAdditiveByAdid(ad_id);
    }
    @Override
    public List<Additive> deleteAdditiveByAdname(String ad_name){
        return additiveDao.deleteAdditiveByAdname(ad_name);
    }
    @Override
    public List<Additive> deleteAdditiveByAdstaff(String ad_staff){
        return additiveDao.deleteAdditiveByAdstaff(ad_staff);
    }
    //查询数据
    public List<Additive> findAdditiveByAdid(int ad_id){
        return additiveDao.findAdditiveByAdid(ad_id);
    }
    public List<Additive> findAdditiveByAdname(String ad_name){
        return additiveDao.findAdditiveByAdname(ad_name);
    }
    public List<Additive> findAdditiveByAdqualified(String ad_qualified){
        return additiveDao.findAdditiveByAdqualified(ad_qualified);
    }
    public List<Additive> findAdditiveByAdstaff(String ad_staff){
        return additiveDao.findAdditiveByAdstaff(ad_staff);
    }
    //修改数据
    public Additive updateAdditive(Additive additive, String ad_name, String ad_check, String ad_qualified, String ad_staff, String datetime){
        additive.setAd_name(ad_name);
        additive.setAd_check(ad_check);
        additive.setAd_qualified(ad_qualified);
        additive.setAd_staff(ad_staff);
        additive.setDatetime(datetime);
        return additiveDao.save(additive);
    }

    /*包装*/
    @Autowired
    private PackDao packDao;
    //显示所有数据
    @Override
    public List<Pack> findAll3() {
        return packDao.findAll3();
    }
    //增加数据
    @Override
    public Pack addPack(Pack pack){
        return packDao.save(pack);
    }
    //删除数据
    @Override
    public List<Pack> deletePackByPkid(int pk_id) {
        return packDao.deletePackByPkid(pk_id);
    }
    @Override
    public List<Pack> deletePackByPkname(String pk_name){
        return packDao.deletePackByPkname(pk_name);
    }
    @Override
    public List<Pack> deletePackByPkstaff(String pk_staff){
        return packDao.deletePackByPkstaff(pk_staff);
    }
    //查询数据
    public List<Pack> findPackByPkid(int pk_id){
        return packDao.findPackByPkid(pk_id);
    }
    public List<Pack> findPackByPkname(String pk_name){
        return packDao.findPackByPkname(pk_name);
    }
    public List<Pack> findPackByPkqualified(String pk_qualified){
        return packDao.findPackByPkqualified(pk_qualified);
    }
    public List<Pack> findPackByPkstaff(String pk_staff){
        return packDao.findPackByPkstaff(pk_staff);
    }
    //修改数据
    public Pack updatePack(Pack pack, String pk_name, String pk_check, String pk_qualified, String pk_staff, String datetime){
        pack.setPk_name(pk_name);
        pack.setPk_check(pk_check);
        pack.setPk_qualified(pk_qualified);
        pack.setPk_staff(pk_staff);
        pack.setDatetime(datetime);
        return packDao.save(pack);
    }

}
