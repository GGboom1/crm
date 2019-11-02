package com.mage.crm.dao;

import com.mage.crm.dto.UserDto;
import com.mage.crm.query.UserQuery;
import com.mage.crm.vo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserDao {
    /**
     * 通过用户名查询User
     * @param userName
     * @return
     */
    User queryUserByName(String userName);

    /**
     * 通过id查询user
     * @param userId
     * @return
     */
    User queryUserById(String userId);

    /**
     * 修改密码
     * @param userId
     * @param newPassword
     * @return
     */
    int updatePwd(@Param("id") String userId, @Param("userPwd") String newPassword);

    @Select("SELECT\n" +
            "\tu.true_name trueName \n" +
            "FROM\n" +
            "\tt_user u\n" +
            "\tLEFT JOIN t_user_role ur ON u.id = ur.user_id\n" +
            "\tLEFT JOIN t_role r ON ur.role_id = r.id \n" +
            "WHERE\n" +
            "\tr.role_name = '客户经理' \n" +
            "\tAND u.is_valid = 1 \n" +
            "\tAND ur.is_valid = 1 \n" +
            "\tAND r.is_valid = 1")
    List<User> queryAllCustomerManager();

    List<UserDto> queryUsersByParams(UserQuery userQuery);

    Integer insert(User user);

    Integer update(User user);

    Integer delete(Integer userId);
}
