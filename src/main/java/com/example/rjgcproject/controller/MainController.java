package com.example.rjgcproject.controller;

import com.example.rjgcproject.dao.*;
import com.example.rjgcproject.entity.Additive;
import com.example.rjgcproject.entity.Material;
import com.example.rjgcproject.entity.Pack;
import io.swagger.annotations.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.example.rjgcproject.entity.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.rjgcproject.service.*;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Api(tags = "系统接口")
@Controller
public class MainController {
    @Autowired
    private MainServiceImp userServiceImp;
    private String account; //记录账户
    private String idid; //记录身份
    //登录
    @ApiOperation(value = "登录核对", notes = "根据用户名称和密码核对")
    @ApiImplicitParams({@ApiImplicitParam(name = "username", paramType = "query", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", paramType = "query", value = "密码", required = true, dataType = "String")})
    @RequestMapping(value = "/loginVerify",method = RequestMethod.GET)
    @ResponseBody
    public String checkLogin(HttpServletRequest request, HttpSession session){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (userServiceImp.findUserByUsernameAndPassowrd(username,password)!=null && !StringUtils.isEmpty(username)){
            session.setAttribute("tname", username);
            account = username;
            User user = userServiceImp.findUserByUsernameAndPassowrd(username,password);
            idid = user.getIdentity();
            session.setAttribute("tid",idid);
            return "{\"stat\":\"login_ok\"}";
        }else {
            return "{\"stat\":\"login_wrong\"}";
        }
    }
    //注册
    @ApiOperation(value="注册操作",notes = "利用用户名、密码、身份注册用户")
    @ApiImplicitParams({@ApiImplicitParam(name="re_username",paramType = "query",value = "注册用户名",required = true,dataType = "String"),
            @ApiImplicitParam(name="re_password",paramType = "query",value = "注册密码",required = true,dataType = "String"),
            @ApiImplicitParam(name="re_password2",paramType = "query",value = "注册确认密码",required = true,dataType = "String"),
            @ApiImplicitParam(name="re_identity",paramType = "query",value = "注册身份",required = true,dataType = "String")})
    @RequestMapping(value = "/toRegister",method = RequestMethod.GET)
    @Transactional
    @ResponseBody
    public String checkRegister(HttpServletRequest request,HttpSession session){
        String re_username = request.getParameter("re_username");
        String re_password = request.getParameter("re_password");
        String re_password2 = request.getParameter("re_password2");
        String re_identity = request.getParameter("re_identity");
        if (!StringUtils.isEmpty(re_password)&&!StringUtils.isEmpty(re_password2)&&re_password.equals(re_password2)){
            User user = new User(re_username,re_password,re_identity);
            userServiceImp.addUser(user);
            return "{\"stat\":\"register_ok\"}";
        }else {
            return "{\"stat\":\"register_wrong_password\"}";
        }
    }
    /*原料*/
    @Autowired
    private MainServiceImp materialServiceImp;
    //显示数据库中对应表的全部数据
    @ApiOperation(value = "显示全部原料数据",notes = "显示原料登记表中的记录")
    @RequestMapping(value = "/mt_showall",method = RequestMethod.POST)
    public String showAll(HttpSession session){
        List<Material> all_material=materialServiceImp.findAll();
        session.setAttribute("all_material",all_material);
        return "material_register";
    }
    //增加数据
    @ApiOperation(value = "增加原料数据",notes = "增加原料登记表中的记录")
    @ApiImplicitParams({@ApiImplicitParam(name="type_mt_name",paramType = "query",value = "原料名",required = true,dataType = "String"),
            @ApiImplicitParam(name="type_mt_check",paramType = "query",value = "检测结果",required = true,dataType = "String"),
            @ApiImplicitParam(name="select_mt_qualified",paramType = "query",value = "是否达标",required = true,dataType = "String"),
            @ApiImplicitParam(name="account",paramType = "query",value = "用户名",required = true,dataType = "String")})
    @RequestMapping(value = "/mt_add",method = RequestMethod.GET)
    @ResponseBody
    public String mt_add(HttpServletRequest request){
        String mt_name = request.getParameter("type_mt_name");
        String mt_check = request.getParameter("type_mt_check");
        String mt_qualified = request.getParameter("select_mt_qualified");
        String mt_staff = account;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        String datetime = format.format(now);
        if (!StringUtils.isEmpty(mt_name)&&!StringUtils.isEmpty(mt_check)&&!StringUtils.isEmpty(mt_qualified)&&!StringUtils.isEmpty(mt_staff)){
            Material material =new Material(mt_name,mt_check,mt_qualified,mt_staff,datetime);
            materialServiceImp.addMaterial(material);
            return "{\"stat\":\"add_ok\"}";
        }else {
            return "{\"stat\":\"add_wrong\"}";
        }
    }
    //删除数据
    @ApiOperation(value = "删除原料数据",notes = "删除原料登记表中的记录")
    @ApiImplicitParams({@ApiImplicitParam(name="select_mt_index",paramType = "query",value = "删除索引",required = true,dataType = "String"),
            @ApiImplicitParam(name="mt_delete1",paramType = "query",value = "删除记录的序号",required = false,dataType = "String"),
            @ApiImplicitParam(name="mt_delete2",paramType = "query",value = "删除记录的名称",required = false,dataType = "String"),
            @ApiImplicitParam(name="mt_delete3",paramType = "query",value = "删除记录的登记员工",required = false,dataType = "String")})
    @RequestMapping(value = "/mt_delete",method = RequestMethod.GET)
    @Transactional
    @ResponseBody
    public String mt_delete(HttpServletRequest request){
        String delete_index = request.getParameter("select_mt_index");
        if(delete_index.equals("mt_id")) {
            String temp=request.getParameter("mt_delete1");
            int mt_id=Integer.parseInt(temp);
            List<Material> materialList=materialServiceImp.findMaterialByMtid(mt_id);
            if(!materialList.isEmpty()){
                materialServiceImp.deleteMaterialByMtid(mt_id);
                return "{\"stat\":\"delete_ok\"}";
            }else{
                return "{\"stat\":\"delete_wrong\"}";
            }
        }else if(delete_index.equals("mt_name")){
            String mt_name=request.getParameter("mt_delete2");
            List<Material> materialList=materialServiceImp.findMaterialByMtname(mt_name);
            if(!materialList.isEmpty()){
                materialServiceImp.deleteMaterialByMtname(mt_name);
                return "{\"stat\":\"delete_ok\"}";
            }else{
                return "{\"stat\":\"delete_wrong\"}";
            }
        }
        else if(delete_index.equals("mt_staff")){
            String mt_staff=request.getParameter("mt_delete3");
            List<Material> materialList=materialServiceImp.findMaterialByMtstaff(mt_staff);
            if(!materialList.isEmpty()){
                materialServiceImp.deleteMaterialByMtstaff(mt_staff);
                return "{\"stat\":\"delete_ok\"}";
            }else{
                return "{\"stat\":\"delete_wrong\"}";
            }
        }
        else{
            return "{\"stat\":\"delete_wrong\"}";
        }
    }
    //查询数据
    @ApiOperation(value = "查询原料数据",notes = "查询原料登记表中的记录")
    @ApiImplicitParams({@ApiImplicitParam(name = "select_mt_index2", paramType = "query", value = "查询索引", required = true, dataType = "String"),
            @ApiImplicitParam(name = "mt_query1", paramType = "query", value = "查询记录的序号", required = false, dataType = "String"),
            @ApiImplicitParam(name = "mt_query2", paramType = "query", value = "查询记录的名称", required = false, dataType = "String"),
            @ApiImplicitParam(name = "mt_query3", paramType = "query", value = "查询记录是否达标", required = false, dataType = "String"),
            @ApiImplicitParam(name = "mt_query4", paramType = "query", value = "查询记录的登记员工", required = false, dataType = "String")})
    @RequestMapping(value = "/mt_query",method = RequestMethod.GET)
    @Transactional
    @ResponseBody
    public String mt_query(HttpServletRequest request,HttpSession session){
        String query_index = request.getParameter("select_mt_index2");
        //查询返回的Material列表
        List<Material> query_material = new ArrayList<>();
        if(query_index.equals("mt_id2")) {
            String temp=request.getParameter("mt_query1");
            int mt_id=Integer.parseInt(temp);
            query_material = materialServiceImp.findMaterialByMtid(mt_id);
        }else if(query_index.equals("mt_name2")){
            String mt_name=request.getParameter("mt_query2");
            query_material = materialServiceImp.findMaterialByMtname(mt_name);
        }
        else if(query_index.equals("mt_qualified2")){
            String mt_qualified=request.getParameter("mt_query3");
            query_material = materialServiceImp.findMaterialByMtqualified(mt_qualified);
        }
        else if(query_index.equals("mt_staff2")){
            String mt_staff=request.getParameter("mt_query4");
            query_material = materialServiceImp.findMaterialByMtstaff(mt_staff);
        }
        else{
            return "{\"stat\":\"query_wrong\"}";
        }
        if(!query_material.isEmpty())
        {
            session.setAttribute("all_material",query_material);
            return "{\"stat\":\"query_ok\"}";
        }
        else{
            return "{\"stat\":\"query_wrong\"}";
        }
    }
    //修改数据
    @ApiOperation(value = "修改原料数据",notes = "修改原料登记表中的记录")
    @ApiImplicitParams({@ApiImplicitParam(name = "UMI", paramType = "query", value = "查询序号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "get_mt_id", paramType = "query", value = "修改记录的序号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "update_mt_name", paramType = "query", value = "修改记录的名称", required = true, dataType = "String"),
            @ApiImplicitParam(name = "update_mt_check", paramType = "query", value = "修改记录的检测结果", required = true, dataType = "String"),
            @ApiImplicitParam(name = "update_mt_qualified", paramType = "query", value = "修改记录是否达标", required = true, dataType = "String"),
            @ApiImplicitParam(name = "update_mt_staff", paramType = "query", value = "修改记录的登记员工", required = true, dataType = "String")})
    @RequestMapping(value = "/mt_update",method = RequestMethod.GET)
    @Transactional
    @ResponseBody
    public String mt_update(HttpServletRequest request,HttpSession session) {
        //从查询输入框获得序号值
        String temp = request.getParameter("UMI");
        String temp2 = request.getParameter("get_mt_id");
        int mt_update_id=0;
        int flag=0;
        if(!StringUtils.isEmpty(temp))
        {
            flag=1;//查询
            mt_update_id = Integer.parseInt(temp);
        }
        else if(!StringUtils.isEmpty(temp2))
        {
            flag=2;//修改
            mt_update_id = Integer.parseInt(temp2);
        }
        List<Material> materialList = materialServiceImp.findMaterialByMtid(mt_update_id);
        if (!materialList.isEmpty() && !StringUtils.isEmpty(mt_update_id)) {  //查询到存在数据
            Material material=materialList.get(0);
            if (flag == 1) {
                String info = "{\"stat\":\"check_ok\",\"update_mtname\":" + '"' + material.getMt_name() + '"' + ",\"update_mtcheck\":" + '"' + material.getMt_check() + '"' + ",\"update_mtqualified\":" + '"' + material.getMt_qualified() + '"' + ",\"update_mtstaff\":" + '"' + material.getMt_staff() + '"' + ",\"update_mtid\":" + '"' + mt_update_id + '"' + "}";
                return info;
            } else {
                //取输入框中的值
                String mt_name = request.getParameter("update_mt_name");
                if (StringUtils.isEmpty(mt_name)) {  //如果输入框为空则保留原数据
                    mt_name = material.getMt_name();
                }
                String mt_check = request.getParameter("update_mt_check");
                if (StringUtils.isEmpty(mt_check)) {
                    mt_check = material.getMt_check();
                }
                String mt_qualified = request.getParameter("update_mt_qualified");
                if (StringUtils.isEmpty(mt_qualified)) {
                    mt_qualified = material.getMt_qualified();
                }
                String mt_staff = request.getParameter("update_mt_staff");
                if (StringUtils.isEmpty(mt_staff)) {
                    mt_staff = material.getMt_staff();
                }
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date now = new Date();
                String datetime = format.format(now);
                materialServiceImp.updateMaterial(material, mt_name, mt_check, mt_qualified, mt_staff, datetime);
                return "{\"stat\":\"update_ok\"}";
            }
        } else {
            System.out.println("failupdate");
            return "{\"stat\":\"update_wrong\"}";
        }
    }

    /*添加剂*/
    @Autowired
    private MainServiceImp additiveServiceImp;
    //显示数据库中对应表的全部数据
    @ApiOperation(value = "显示全部添加剂数据",notes = "显示添加剂登记表中的记录")
    @RequestMapping(value = "/ad_showall",method = RequestMethod.POST)
    public String showAll2(HttpSession session){
        List<Additive> all_additive=additiveServiceImp.findAll2();
        session.setAttribute("all_additive",all_additive);
        return "additive_register";
    }
    //增加数据
    @ApiOperation(value = "增加添加剂数据",notes = "增加添加剂登记表中的记录")
    @ApiImplicitParams({@ApiImplicitParam(name="type_ad_name",paramType = "query",value = "添加剂名",required = true,dataType = "String"),
            @ApiImplicitParam(name="type_ad_check",paramType = "query",value = "检测结果",required = true,dataType = "String"),
            @ApiImplicitParam(name="select_ad_qualified",paramType = "query",value = "是否达标",required = true,dataType = "String"),
            @ApiImplicitParam(name="account",paramType = "query",value = "用户名",required = true,dataType = "String")})
    @RequestMapping(value = "/ad_add",method = RequestMethod.GET)
    @ResponseBody
    public String ad_add(HttpServletRequest request){
        String ad_name = request.getParameter("type_ad_name");
        String ad_check = request.getParameter("type_ad_check");
        String ad_qualified = request.getParameter("select_ad_qualified");
        String ad_staff = account;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        String datetime = format.format(now);
        if (!StringUtils.isEmpty(ad_name)&&!StringUtils.isEmpty(ad_check)&&!StringUtils.isEmpty(ad_qualified)&&!StringUtils.isEmpty(ad_staff)){
            Additive additive =new Additive(ad_name,ad_check,ad_qualified,ad_staff,datetime);
            additiveServiceImp.addAdditive(additive);
            return "{\"stat\":\"add_ok\"}";
        }else {
            return "{\"stat\":\"add_wrong\"}";
        }
    }
    //删除数据
    @ApiOperation(value = "删除添加剂数据",notes = "删除添加剂登记表中的记录")
    @ApiImplicitParams({@ApiImplicitParam(name="select_ad_index",paramType = "query",value = "删除索引",required = true,dataType = "String"),
            @ApiImplicitParam(name="ad_delete1",paramType = "query",value = "删除记录的序号",required = false,dataType = "String"),
            @ApiImplicitParam(name="ad_delete2",paramType = "query",value = "删除记录的名称",required = false,dataType = "String"),
            @ApiImplicitParam(name="ad_delete3",paramType = "query",value = "删除记录的登记员工",required = false,dataType = "String")})
    @RequestMapping(value = "/ad_delete",method = RequestMethod.GET)
    @Transactional
    @ResponseBody
    public String ad_delete(HttpServletRequest request){
        String delete_index = request.getParameter("select_ad_index");
        if(delete_index.equals("ad_id")) {
            String temp=request.getParameter("ad_delete1");
            int ad_id=Integer.parseInt(temp);
            List<Additive> additiveList=additiveServiceImp.findAdditiveByAdid(ad_id);
            if(!additiveList.isEmpty()){
                additiveServiceImp.deleteAdditiveByAdid(ad_id);
                return "{\"stat\":\"delete_ok\"}";
            }else{
                return "{\"stat\":\"delete_wrong\"}";
            }
        }else if(delete_index.equals("ad_name")){
            String ad_name=request.getParameter("ad_delete2");
            List<Additive> additiveList=additiveServiceImp.findAdditiveByAdname(ad_name);
            if(!additiveList.isEmpty()){
                additiveServiceImp.deleteAdditiveByAdname(ad_name);
                return "{\"stat\":\"delete_ok\"}";
            }else{
                return "{\"stat\":\"delete_wrong\"}";
            }
        }
        else if(delete_index.equals("ad_staff")){
            String ad_staff=request.getParameter("ad_delete3");
            List<Additive> additiveList=additiveServiceImp.findAdditiveByAdstaff(ad_staff);
            if(!additiveList.isEmpty()){
                additiveServiceImp.deleteAdditiveByAdstaff(ad_staff);
                return "{\"stat\":\"delete_ok\"}";
            }else{
                return "{\"stat\":\"delete_wrong\"}";
            }
        }
        else{
            return "{\"stat\":\"delete_wrong\"}";
        }
    }
    //查询数据
    @ApiOperation(value = "查询添加剂数据",notes = "查询添加剂登记表中的记录")
    @ApiImplicitParams({@ApiImplicitParam(name = "select_ad_index2", paramType = "query", value = "查询索引", required = true, dataType = "String"),
            @ApiImplicitParam(name = "ad_query1", paramType = "query", value = "查询记录的序号", required = false, dataType = "String"),
            @ApiImplicitParam(name = "ad_query2", paramType = "query", value = "查询记录的名称", required = false, dataType = "String"),
            @ApiImplicitParam(name = "ad_query3", paramType = "query", value = "查询记录是否达标", required = false, dataType = "String"),
            @ApiImplicitParam(name = "ad_query4", paramType = "query", value = "查询记录的登记员工", required = false, dataType = "String")})
    @RequestMapping(value = "/ad_query",method = RequestMethod.GET)
    @Transactional
    @ResponseBody
    public String ad_query(HttpServletRequest request,HttpSession session){
        String query_index = request.getParameter("select_ad_index2");
        //查询返回的Additive列表
        List<Additive> query_additive = new ArrayList<>();
        if(query_index.equals("ad_id2")) {
            String temp=request.getParameter("ad_query1");
            int ad_id=Integer.parseInt(temp);
            query_additive = additiveServiceImp.findAdditiveByAdid(ad_id);
        }else if(query_index.equals("ad_name2")){
            String ad_name=request.getParameter("ad_query2");
            query_additive = additiveServiceImp.findAdditiveByAdname(ad_name);
        }
        else if(query_index.equals("ad_qualified2")){
            String ad_qualified=request.getParameter("ad_query3");
            query_additive = additiveServiceImp.findAdditiveByAdqualified(ad_qualified);
        }
        else if(query_index.equals("ad_staff2")){
            String ad_staff=request.getParameter("ad_query4");
            query_additive = additiveServiceImp.findAdditiveByAdstaff(ad_staff);
        }
        else{
            return "{\"stat\":\"query_wrong\"}";
        }
        if(!query_additive.isEmpty())
        {
            session.setAttribute("all_additive",query_additive);
            return "{\"stat\":\"query_ok\"}";
        }
        else{
            return "{\"stat\":\"query_wrong\"}";
        }
    }
    //修改数据
    @ApiOperation(value = "修改添加剂数据",notes = "修改添加剂登记表中的记录")
    @ApiImplicitParams({@ApiImplicitParam(name = "UMI", paramType = "query", value = "查询序号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "get_ad_id", paramType = "query", value = "修改记录的序号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "update_ad_name", paramType = "query", value = "修改记录的名称", required = true, dataType = "String"),
            @ApiImplicitParam(name = "update_ad_check", paramType = "query", value = "修改记录的最大使用量", required = true, dataType = "String"),
            @ApiImplicitParam(name = "update_ad_qualified", paramType = "query", value = "修改记录是否达标", required = true, dataType = "String"),
            @ApiImplicitParam(name = "update_ad_staff", paramType = "query", value = "修改记录的登记员工", required = true, dataType = "String")})
    @RequestMapping(value = "/ad_update",method = RequestMethod.GET)
    @Transactional
    @ResponseBody
    public String ad_update(HttpServletRequest request,HttpSession session) {
        //从查询输入框获得序号值
        String temp = request.getParameter("UMI");
        String temp2 = request.getParameter("get_ad_id");
        int ad_update_id=0;
        int flag=0;
        if(!StringUtils.isEmpty(temp))
        {
            flag=1;//查询
            ad_update_id = Integer.parseInt(temp);
        }
        else if(!StringUtils.isEmpty(temp2))
        {
            flag=2;//修改
            ad_update_id = Integer.parseInt(temp2);
        }
        List<Additive> additiveList = additiveServiceImp.findAdditiveByAdid(ad_update_id);
        if (!additiveList.isEmpty() && !StringUtils.isEmpty(ad_update_id)) {  //查询到存在数据
            Additive additive=additiveList.get(0);
            if (flag == 1) {
                String info = "{\"stat\":\"check_ok\",\"update_adname\":" + '"' + additive.getAd_name() + '"' + ",\"update_adcheck\":" + '"' + additive.getAd_check() + '"' + ",\"update_adqualified\":" + '"' + additive.getAd_qualified() + '"' + ",\"update_adstaff\":" + '"' + additive.getAd_staff() + '"' + ",\"update_adid\":" + '"' + ad_update_id + '"' + "}";
                return info;
            } else {
                //取输入框中的值
                String ad_name = request.getParameter("update_ad_name");
                if (StringUtils.isEmpty(ad_name)) {  //如果输入框为空则保留原数据
                    ad_name = additive.getAd_name();
                }
                String ad_check = request.getParameter("update_ad_check");
                if (StringUtils.isEmpty(ad_check)) {
                    ad_check = additive.getAd_check();
                }
                String ad_qualified = request.getParameter("update_ad_qualified");
                if (StringUtils.isEmpty(ad_qualified)) {
                    ad_qualified = additive.getAd_qualified();
                }
                String ad_staff = request.getParameter("update_ad_staff");
                if (StringUtils.isEmpty(ad_staff)) {
                    ad_staff = additive.getAd_staff();
                }
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date now = new Date();
                String datetime = format.format(now);
                additiveServiceImp.updateAdditive(additive, ad_name, ad_check, ad_qualified, ad_staff, datetime);
                return "{\"stat\":\"update_ok\"}";
            }
        } else {
            System.out.println("failupdate");
            return "{\"stat\":\"update_wrong\"}";
        }
    }

    /*包装*/
    @Autowired
    private MainServiceImp packServiceImp;
    //显示数据库中对应表的全部数据
    @ApiOperation(value = "显示全部包装数据",notes = "显示包装登记表中的记录")
    @RequestMapping(value = "/pk_showall",method = RequestMethod.POST)
    public String showAll3(HttpSession session){
        List<Pack> all_pack=packServiceImp.findAll3();
        session.setAttribute("all_pack",all_pack);
        return "pack_register";
    }
    //增加数据
    @ApiOperation(value = "增加包装数据",notes = "增加包装登记表中的记录")
    @ApiImplicitParams({@ApiImplicitParam(name="type_pk_name",paramType = "query",value = "包装名",required = true,dataType = "String"),
            @ApiImplicitParam(name="type_pk_check",paramType = "query",value = "检测结果",required = true,dataType = "String"),
            @ApiImplicitParam(name="select_pk_qualified",paramType = "query",value = "是否达标",required = true,dataType = "String"),
            @ApiImplicitParam(name="account",paramType = "query",value = "用户名",required = true,dataType = "String")})
    @RequestMapping(value = "/pk_add",method = RequestMethod.GET)
    @ResponseBody
    public String pk_add(HttpServletRequest request){
        String pk_name = request.getParameter("type_pk_name");
        String pk_check = request.getParameter("type_pk_check");
        String pk_qualified = request.getParameter("select_pk_qualified");
        String pk_staff = account;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        String datetime = format.format(now);
        if (!StringUtils.isEmpty(pk_name)&&!StringUtils.isEmpty(pk_check)&&!StringUtils.isEmpty(pk_qualified)&&!StringUtils.isEmpty(pk_staff)){
            Pack pack =new Pack(pk_name,pk_check,pk_qualified,pk_staff,datetime);
            packServiceImp.addPack(pack);
            return "{\"stat\":\"add_ok\"}";
        }else {
            return "{\"stat\":\"add_wrong\"}";
        }
    }
    //删除数据
    @ApiOperation(value = "删除包装数据",notes = "删除包装登记表中的记录")
    @ApiImplicitParams({@ApiImplicitParam(name="select_pk_index",paramType = "query",value = "删除索引",required = true,dataType = "String"),
            @ApiImplicitParam(name="pk_delete1",paramType = "query",value = "删除记录的序号",required = false,dataType = "String"),
            @ApiImplicitParam(name="pk_delete2",paramType = "query",value = "删除记录的名称",required = false,dataType = "String"),
            @ApiImplicitParam(name="pk_delete3",paramType = "query",value = "删除记录的登记员工",required = false,dataType = "String")})
    @RequestMapping(value = "/pk_delete",method = RequestMethod.GET)
    @Transactional
    @ResponseBody
    public String pk_delete(HttpServletRequest request){
        String delete_index = request.getParameter("select_pk_index");
        if(delete_index.equals("pk_id")) {
            String temp=request.getParameter("pk_delete1");
            int pk_id=Integer.parseInt(temp);
            List<Pack> packList=packServiceImp.findPackByPkid(pk_id);
            if(!packList.isEmpty()){
                packServiceImp.deletePackByPkid(pk_id);
                return "{\"stat\":\"delete_ok\"}";
            }else{
                return "{\"stat\":\"delete_wrong\"}";
            }
        }else if(delete_index.equals("pk_name")){
            String pk_name=request.getParameter("pk_delete2");
            List<Pack> packList=packServiceImp.findPackByPkname(pk_name);
            if(!packList.isEmpty()){
                packServiceImp.deletePackByPkname(pk_name);
                return "{\"stat\":\"delete_ok\"}";
            }else{
                return "{\"stat\":\"delete_wrong\"}";
            }
        }
        else if(delete_index.equals("pk_staff")){
            String pk_staff=request.getParameter("pk_delete3");
            List<Pack> packList=packServiceImp.findPackByPkstaff(pk_staff);
            if(!packList.isEmpty()){
                packServiceImp.deletePackByPkstaff(pk_staff);
                return "{\"stat\":\"delete_ok\"}";
            }else{
                return "{\"stat\":\"delete_wrong\"}";
            }
        }
        else{
            return "{\"stat\":\"delete_wrong\"}";
        }
    }
    //查询数据
    @ApiOperation(value = "查询包装数据",notes = "查询包装登记表中的记录")
    @ApiImplicitParams({@ApiImplicitParam(name = "select_pk_index2", paramType = "query", value = "查询索引", required = true, dataType = "String"),
            @ApiImplicitParam(name = "pk_query1", paramType = "query", value = "查询记录的序号", required = false, dataType = "String"),
            @ApiImplicitParam(name = "pk_query2", paramType = "query", value = "查询记录的名称", required = false, dataType = "String"),
            @ApiImplicitParam(name = "pk_query3", paramType = "query", value = "查询记录是否达标", required = false, dataType = "String"),
            @ApiImplicitParam(name = "pk_query4", paramType = "query", value = "查询记录的登记员工", required = false, dataType = "String")})
    @RequestMapping(value = "/pk_query",method = RequestMethod.GET)
    @Transactional
    @ResponseBody
    public String pk_query(HttpServletRequest request,HttpSession session){
        String query_index = request.getParameter("select_pk_index2");
        //查询返回的Pack列表
        List<Pack> query_pack = new ArrayList<>();
        if(query_index.equals("pk_id2")) {
            String temp=request.getParameter("pk_query1");
            int pk_id=Integer.parseInt(temp);
            query_pack = packServiceImp.findPackByPkid(pk_id);
        }else if(query_index.equals("pk_name2")){
            String pk_name=request.getParameter("pk_query2");
            query_pack = packServiceImp.findPackByPkname(pk_name);
        }
        else if(query_index.equals("pk_qualified2")){
            String pk_qualified=request.getParameter("pk_query3");
            query_pack = packServiceImp.findPackByPkqualified(pk_qualified);
        }
        else if(query_index.equals("pk_staff2")){
            String pk_staff=request.getParameter("pk_query4");
            query_pack = packServiceImp.findPackByPkstaff(pk_staff);
        }
        else{
            return "{\"stat\":\"query_wrong\"}";
        }
        if(!query_pack.isEmpty())
        {
            session.setAttribute("all_pack",query_pack);
            return "{\"stat\":\"query_ok\"}";
        }
        else{
            return "{\"stat\":\"query_wrong\"}";
        }
    }
    //修改数据
    @ApiOperation(value = "修改包装数据",notes = "修改包装登记表中的记录")
    @ApiImplicitParams({@ApiImplicitParam(name = "UMI", paramType = "query", value = "查询序号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "get_pk_id", paramType = "query", value = "修改记录的序号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "update_pk_name", paramType = "query", value = "修改记录的名称", required = true, dataType = "String"),
            @ApiImplicitParam(name = "update_pk_check", paramType = "query", value = "修改记录的检测结果", required = true, dataType = "String"),
            @ApiImplicitParam(name = "update_pk_qualified", paramType = "query", value = "修改记录的是否达标", required = true, dataType = "String"),
            @ApiImplicitParam(name = "update_pk_staff", paramType = "query", value = "修改记录的登记员工", required = true, dataType = "String")})
    @RequestMapping(value = "/pk_update",method = RequestMethod.GET)
    @Transactional
    @ResponseBody
    public String pk_update(HttpServletRequest request,HttpSession session) {
        //从查询输入框获得序号值
        String temp = request.getParameter("UMI");
        String temp2 = request.getParameter("get_pk_id");
        int pk_update_id=0;
        int flag=0;
        if(!StringUtils.isEmpty(temp))
        {
            flag=1;//查询
            pk_update_id = Integer.parseInt(temp);
        }
        else if(!StringUtils.isEmpty(temp2))
        {
            flag=2;//修改
            pk_update_id = Integer.parseInt(temp2);
        }
        List<Pack> packList = packServiceImp.findPackByPkid(pk_update_id);
        if (!packList.isEmpty() && !StringUtils.isEmpty(pk_update_id)) {  //查询到存在数据
            Pack pack=packList.get(0);
            if (flag == 1) {
                String info = "{\"stat\":\"check_ok\",\"update_pkname\":" + '"' + pack.getPk_name() + '"' + ",\"update_pkcheck\":" + '"' + pack.getPk_check() + '"' + ",\"update_pkqualified\":" + '"' + pack.getPk_qualified() + '"' + ",\"update_pkstaff\":" + '"' + pack.getPk_staff() + '"' + ",\"update_pkid\":" + '"' + pk_update_id + '"' + "}";
                return info;
            } else {
                //取输入框中的值
                String pk_name = request.getParameter("update_pk_name");
                if (StringUtils.isEmpty(pk_name)) {  //如果输入框为空则保留原数据
                    pk_name = pack.getPk_name();
                }
                String pk_check = request.getParameter("update_pk_check");
                if (StringUtils.isEmpty(pk_check)) {
                    pk_check = pack.getPk_check();
                }
                String pk_qualified = request.getParameter("update_pk_qualified");
                if (StringUtils.isEmpty(pk_qualified)) {
                    pk_qualified = pack.getPk_qualified();
                }
                String pk_staff = request.getParameter("update_pk_staff");
                if (StringUtils.isEmpty(pk_staff)) {
                    pk_staff = pack.getPk_staff();
                }
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date now = new Date();
                String datetime = format.format(now);
                packServiceImp.updatePack(pack, pk_name, pk_check, pk_qualified, pk_staff, datetime);
                return "{\"stat\":\"update_ok\"}";
            }
        } else {
            System.out.println("failupdate");
            return "{\"stat\":\"update_wrong\"}";
        }
    }

}
