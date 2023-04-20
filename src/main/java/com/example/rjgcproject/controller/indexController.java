package com.example.rjgcproject.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
@Api(tags = "地址接口")
@Controller
public class indexController {
    @ApiOperation(value = "初始页面",notes = "跳转到初始页面")
    @RequestMapping(value="/",method = RequestMethod.GET)
    public String index1(){
        return "login";
    }

    @ApiOperation(value = "登录页面",notes = "跳转到登录页面")
    @RequestMapping(value="/login",method = RequestMethod.GET)
    public String index2(){
        return "login";
    }

    @ApiOperation(value = "欢迎页面",notes = "跳转到欢迎页面")
    @ApiImplicitParam(name = "tname", paramType = "query", value = "用户名", required = true, dataType = "String")
    @RequestMapping(value="/welcome",method = RequestMethod.GET)
    public String index3(HttpSession session){
        //System.out.println(request.getParameter("tname"));
        if(session.getAttribute("tname")!=null) {
            return "welcome";
        }else{
            return "login";
        }
    }

    @ApiOperation(value = "注册页面",notes = "跳转到注册页面")
    @RequestMapping(value="/register",method = RequestMethod.GET)
    public String index4() { return "register"; }

    @ApiOperation(value = "原料质量登记页面",notes = "跳转到原料质量登记页面")
    @ApiImplicitParam(name = "tname", paramType = "query", value = "用户名", required = true, dataType = "String")
    @RequestMapping(value="/material_register",method = RequestMethod.GET)
    public String index5(HttpSession session) {
        if(session.getAttribute("tname")!=null) {
            return "material_register";
        }else{
            return "login";
        }
    }

    @ApiOperation(value = "添加剂质量登记页面",notes = "跳转到添加剂质量登记页面")
    @ApiImplicitParam(name = "tname", paramType = "query", value = "用户名", required = true, dataType = "String")
    @RequestMapping(value="/additive_register",method = RequestMethod.GET)
    public String index6(HttpSession session) {
        if(session.getAttribute("tname")!=null) {
            return "additive_register";
        }else{
            return "login";
        }
    }

    @ApiOperation(value = "包装质量登记页面",notes = "跳转到包装质量登记页面")
    @ApiImplicitParam(name = "tname", paramType = "query", value = "用户名", required = true, dataType = "String")
    @RequestMapping(value="/pack_register",method = RequestMethod.GET)
    public String index7(HttpSession session) {
        if(session.getAttribute("tname")!=null) {
            return "pack_register";
        }else{
            return "login";
        }
    }
}
