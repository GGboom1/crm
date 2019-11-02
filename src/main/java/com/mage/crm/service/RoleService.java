package com.mage.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mage.crm.dao.ModuleDao;
import com.mage.crm.dao.PermissionDao;
import com.mage.crm.dao.RoleDao;
import com.mage.crm.query.RoleQuery;
import com.mage.crm.util.AssertUtil;
import com.mage.crm.vo.Permission;
import com.mage.crm.vo.Role;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class RoleService {
    @Resource
    private RoleDao roleDao;
    @Resource
    private PermissionDao permissionDao;
    @Resource
    private ModuleDao moduleDao;

    public List<Role> queryAllRoles() {
        return roleDao.queryAllRoles();
    }

    public Map<String, Object> queryRolesByParams(RoleQuery roleQuery) {
        PageHelper.startPage(roleQuery.getPage(), roleQuery.getRows());
        List<Role> roleList = roleDao.queryRolesByParams(roleQuery);
        PageInfo<Role> rolePageInfo = new PageInfo<>(roleList);
        Map<String, Object> map = new HashMap<>();
        map.put("total", rolePageInfo.getTotal());
        map.put("rows", rolePageInfo.getList());
        return map;
    }

    public void insert(Role role) {
        role.setIsValid(1);
        role.setCreateDate(new Date());
        role.setUpdateDate(new Date());
        AssertUtil.isTrue(roleDao.insert(role) < 1, "添加数据失败");
    }

    public void update(Role role) {
        role.setUpdateDate(new Date());
        AssertUtil.isTrue(roleDao.update(role) < 1, "修改数据失败");
    }

    public void addPermission(Integer rid, Integer[] moduleIds){
        //查询是否存在
        AssertUtil.isTrue(rid == null || roleDao.queryRoleById(rid) == null, "待授权的角色不存在!");
        //查询原来是否有关联角色
        Integer count = permissionDao.queryPermissionCountByRid(rid);
        if (count != null && count != 0){
            //删除原来的关联
            AssertUtil.isTrue(permissionDao.deletePermissionByRid(rid) < count, "修改失败");
        }
        if(null != moduleIds && moduleIds.length > 0){
            //添加关联
            List<Permission> permissionList = new ArrayList<>();
            for(Integer moduleId : moduleIds){
                String optValue = moduleDao.queryOptValueById(moduleId);
                AssertUtil.isTrue(optValue == null || "".equals(optValue), "修改失败");
                Permission permission = new Permission();
                permission.setAclValue(optValue);
                permission.setRoleId(""+rid);
                permission.setModuleId(""+moduleId);
                permission.setCreateDate(new Date());
                permission.setUpdateDate(new Date());
                permissionList.add(permission);
            }
            AssertUtil.isTrue(permissionDao.insertBatch(permissionList) < permissionList.size(), "修改失败");
        }
    }
}
