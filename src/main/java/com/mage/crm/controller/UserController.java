package com.mage.crm.controller;

import com.mage.crm.base.BaseController;
import com.mage.crm.base.CrmConstant;
import com.mage.crm.base.exceptions.ParamsException;
import com.mage.crm.dto.UserDto;
import com.mage.crm.dto.UserModel;
import com.mage.crm.model.MessageModel;
import com.mage.crm.query.UserQuery;
import com.mage.crm.service.UserService;
import com.mage.crm.util.CookieUtil;
import com.mage.crm.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
    @Resource
    private UserService userService;

    @RequestMapping("/index")
    public String index(){
        return "user";
    }

    @RequestMapping("/queryUsersByParams")
    @ResponseBody
    public Map<String, Object> queryUsersByParams(UserQuery userQuery){
        return userService.queryUsersByParams(userQuery);
    }

    @RequestMapping("/insert")
    @ResponseBody
    public MessageModel insert(User user){
        userService.insert(user);
        return createMessageModel("添加用户成功");
    }

    @RequestMapping("/delete")
    @ResponseBody
    public MessageModel delete(Integer userId){
        userService.delete(userId);
        return createMessageModel("删除用户成功");
    }

    @RequestMapping("/update")
    @ResponseBody
    public MessageModel update(User user){
        userService.update(user);
        return createMessageModel("修改成功");
    }

    @ResponseBody
    @RequestMapping("/login")
    public MessageModel userLogin(String userName, String userPwd){
        MessageModel<UserModel> modelMessageModel = new MessageModel<UserModel>();
        try {
            modelMessageModel.setResult(userService.userLogin(userName, userPwd));
        }catch (ParamsException pe){
            pe.printStackTrace();
            modelMessageModel.setCode(pe.getCode());
            modelMessageModel.setMsg(pe.getMsg());
        }catch (Exception e){
            e.printStackTrace();
            modelMessageModel.setCode(CrmConstant.LOGIN_FAILED_CODE);
            modelMessageModel.setMsg(CrmConstant.LOGIN_FAILED_MSG);
        }
        return modelMessageModel;
    }

    @RequestMapping("/updatePwd")
    @ResponseBody
    public MessageModel updatePwd(HttpServletRequest request, String userName, String oldPassword, String newPassword, String confirmPassword){
        MessageModel modelMessageModel = new MessageModel();
        try{
            userService.updatePwd(CookieUtil.getCookieValue(request, "userId"),userName, oldPassword, newPassword, confirmPassword);
        }catch (ParamsException pe){
            pe.printStackTrace();
            modelMessageModel.setCode(pe.getCode());
            modelMessageModel.setMsg(pe.getMsg());
        }catch (Exception e){
            e.printStackTrace();
            modelMessageModel.setCode(CrmConstant.OPS_FAILED_CODE);
            modelMessageModel.setMsg(CrmConstant.OPS_FAILED_MSG);
        }
        return modelMessageModel;
    }

    @ResponseBody
    @RequestMapping("/queryAllCustomerManager")
    public List<User> queryAllCustomerManager(){
        return userService.queryAllCustomerManager();
    }
}
