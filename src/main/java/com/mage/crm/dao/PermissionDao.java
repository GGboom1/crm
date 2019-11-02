package com.mage.crm.dao;

import com.mage.crm.vo.Permission;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PermissionDao {
    @Select("SELECT module_id FROM t_permission WHERE role_id = #{rid}")
    List<Integer> queryAllModuleIdsByRid(Integer rid);

    @Select("SELECT COUNT(1) FROM t_permission WHERE role_id = #{rid}")
    Integer queryPermissionCountByRid(Integer rid);

    @Delete("DELETE FROM t_permission WHERE role_id = #{rid}")
    Integer deletePermissionByRid(Integer rid);

    Integer insertBatch(List<Permission> permissionList);

    List<String> queryPermissionsByUserId(String id);
}
