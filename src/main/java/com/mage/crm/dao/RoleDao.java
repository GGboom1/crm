package com.mage.crm.dao;

import com.mage.crm.query.RoleQuery;
import com.mage.crm.vo.Role;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleDao {
    @Select("SELECT id, role_name roleName, role_remark roleRemark, create_date createDate," +
            "update_date updateDate FROM t_role WHERE is_valid = 1")
    List<Role> queryAllRoles();

    List<Role> queryRolesByParams(RoleQuery roleQuery);

    Integer insert(Role role);

    Integer update(Role role);

    @Select("SELECT id, role_name roleName, role_remark roleRemark, create_date createDate," +
            "update_date updateDate FROM t_role WHERE is_valid = 1 AND id = #{rid}")
    Role queryRoleById(@Param("rid") Integer rid);
}
