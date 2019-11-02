package com.mage.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mage.crm.dao.PermissionDao;
import com.mage.crm.dao.UserDao;
import com.mage.crm.dto.UserDto;
import com.mage.crm.dto.UserModel;
import com.mage.crm.query.UserQuery;
import com.mage.crm.util.AssertUtil;
import com.mage.crm.util.Base64Util;
import com.mage.crm.util.Md5Util;
import com.mage.crm.vo.User;
import com.mage.crm.vo.UserRole;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

@Service
public class UserService {
    @Resource
    private UserDao userDao;
    @Resource
    private UserRoleService userRoleService;
    @Resource
    private HttpSession session;
    @Resource
    private PermissionDao permissionDao;

    public UserModel userLogin(String userName, String userPwd) {
        AssertUtil.isTrue(StringUtils.isBlank(userName), "用户名不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(userPwd), "密码不能为空");
        User user = userDao.queryUserByName(userName);
        AssertUtil.isTrue(null == user, "用户名或者密码错误");
        AssertUtil.isTrue("0".equals(user.getIsValid()), "用户已经注销");
        AssertUtil.isTrue(!user.getUserPwd().equals(Md5Util.encode(userPwd)), "用户名或者密码错误");
        List<String> permissions = permissionDao.queryPermissionsByUserId(user.getId() + "");
        if(null!=permissions&&permissions.size()>0){
            session.setAttribute("userPermission", permissions);
        }
        return createUserModel(user);
    }

    private UserModel createUserModel(User user) {
        UserModel userModel = new UserModel();
        userModel.setUserId(Base64Util.encode(user.getId()));
        userModel.setUserName(user.getUserName());
        userModel.setTrueName(user.getTrueName());
        return userModel;
    }

    public void updatePwd(String userId, String userName, String oldPassword, String newPassword, String confirmPassword) {
        AssertUtil.isTrue(StringUtils.isBlank(userName), "用户名不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(oldPassword), "用户旧密码不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(newPassword), "用户新密码不能为空");
        AssertUtil.isTrue(!newPassword.equals(confirmPassword), "两次密码输入不一致");
        User user = userDao.queryUserById(Base64Util.decode(userId));
        AssertUtil.isTrue(null==user,"用户被冻结，不允许修改密码");
        AssertUtil.isTrue(!user.getUserPwd().equals(Md5Util.encode(oldPassword)),"原密码错误");
        AssertUtil.isTrue(userDao.updatePwd(Base64Util.decode(userId), Md5Util.encode(newPassword)) < 1,"密码修改失败");
    }

    public User queryUserById(String id){
        AssertUtil.isTrue(StringUtils.isBlank(id), "用户id不能为空");
        return userDao.queryUserById(id);
    }

    public List<User> queryAllCustomerManager() {
        return userDao.queryAllCustomerManager();
    }

    public Map<String, Object> queryUsersByParams(UserQuery userQuery) {
        PageHelper.startPage(userQuery.getPage(), userQuery.getRows());
        List<UserDto> userDtoList = userDao.queryUsersByParams(userQuery);
        PageInfo<UserDto> userDtoPageInfo = new PageInfo<>(userDtoList);
        Map<String, Object> map = new HashMap<>();
        map.put("total", userDtoPageInfo.getTotal());
        map.put("rows", userDtoPageInfo.getList());
        return map;
    }

    public void insert(User user) {
        checkParams(user.getUserName(), user.getTrueName(), user.getEmail(), user.getPhone());
        user.setUserPwd(Md5Util.encode("123456"));
        user.setIsValid(1);
        user.setCreateDate(new Date());
        user.setUpdateDate(new Date());
        //插入user
        AssertUtil.isTrue( userDao.insert(user) < 1, "添加用户失败");
        //插入user_role
        List<Integer> roleIds = user.getRoleIds();
        if(roleIds!=null&&roleIds.size()>0){
            relateRoles(roleIds,Integer.parseInt(user.getId()));
        }
    }

    private void relateRoles(List<Integer> roleIds, int userId) {
        List<UserRole> roleList=new ArrayList<UserRole>();
        for (Integer roleId:roleIds){
            UserRole userRole = new UserRole();
            userRole.setIsValid(1);
            userRole.setCreateDate(new Date());
            userRole.setUpdateDate(new Date());
            userRole.setRoleId(roleId);
            userRole.setUserId(userId);
            roleList.add(userRole);
        }
        AssertUtil.isTrue(userRoleService.insertBatch(roleList) < roleList.size(),"用户角色添加失败");
    }

    private void checkParams(String userName, String trueName, String email, String phone){
        AssertUtil.isTrue(StringUtils.isBlank(userName), "用户名不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(trueName), "真实姓名不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(email), "邮箱不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(phone), "电话不能为空");
    }

    public void update(User user) {
        checkParams(user.getUserName(), user.getTrueName(), user.getEmail(), user.getPhone());
        user.setUpdateDate(new Date());
        //修改user
        User u = userDao.queryUserById(user.getId());
        AssertUtil.isTrue(u!=null && u.getUserName().equals(user.getUserName()),"不能有相同用户名");
        AssertUtil.isTrue(userDao.update(user)<1,"用户修改失败");
        //修改user_role
        List<Integer> roleIds = user.getRoleIds();
        if(roleIds!=null&&roleIds.size()>0){
            //先删除，在插入
            //先查询，原来是否有用户角色
            int count = userRoleService.queryRoleCountsByUserId(user.getId());
            if(count>0){
                AssertUtil.isTrue(userRoleService.deleteRolesByUserId(user.getId()) < 1,"用户更新失败");
            }
            //插入
            relateRoles(roleIds,Integer.parseInt(user.getId()));
        }

    }

    public void delete(Integer userId) {
        AssertUtil.isTrue(userDao.delete(userId) < 1, "删除失败");
        int count = userRoleService.queryRoleCountsByUserId(""+userId);
        if(count > 0){
            AssertUtil.isTrue(userRoleService.deleteRolesByUserId(""+userId) < 1, "删除失败");
        }
    }
}
