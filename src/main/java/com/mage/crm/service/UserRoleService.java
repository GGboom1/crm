package com.mage.crm.service;

import com.mage.crm.dao.UserRoleDao;
import com.mage.crm.vo.User;
import com.mage.crm.vo.UserRole;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserRoleService {
    @Resource
    private UserRoleDao userRoleDao;


    public Integer insertBatch(List<UserRole> roleList) {
        return userRoleDao.insertBatch(roleList);
    }

    public int queryRoleCountsByUserId(String id) {
        return userRoleDao.queryRoleCountsByUserId(id);
    }

    public Integer deleteRolesByUserId(String id) {
        return userRoleDao.deleteRolesByUserId(id);
    }
}
