package com.mage.crm.dao;

import com.mage.crm.vo.UserRole;

import java.util.List;

public interface UserRoleDao {
    Integer insertBatch(List<UserRole> roleList);

    Integer queryRoleCountsByUserId(String id);

    Integer deleteRolesByUserId(String id);
}
