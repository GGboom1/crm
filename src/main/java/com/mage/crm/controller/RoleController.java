package com.mage.crm.controller;

import com.mage.crm.base.BaseController;
import com.mage.crm.model.MessageModel;
import com.mage.crm.query.RoleQuery;
import com.mage.crm.service.RoleService;
import com.mage.crm.vo.Role;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {
    @Resource
    private RoleService roleService;

    @ResponseBody
    @RequestMapping("/addPermission")
    public MessageModel addPermission(Integer rid, Integer[] moduleIds){
        roleService.addPermission(rid, moduleIds);
        return createMessageModel("修改成功");
    }

    @RequestMapping("/index")
    public String index(){
        return "role";
    }

    @ResponseBody
    @RequestMapping("/insert")
    public MessageModel insert(Role role){
        roleService.insert(role);
        return createMessageModel("添加数据成功");
    }

    @ResponseBody
    @RequestMapping("/update")
    public MessageModel update(Role role){
        roleService.update(role);
        return createMessageModel("修改数据成功");
    }

    @ResponseBody
    @RequestMapping("/queryAllRoles")
    public List<Role> queryAllRoles(){
        return roleService.queryAllRoles();
    }

    @ResponseBody
    @RequestMapping("/queryRolesByParams")
    public Map<String, Object> queryRolesByParams(RoleQuery roleQuery){
        return roleService.queryRolesByParams(roleQuery);
    }
}
