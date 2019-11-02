package com.mage.crm.interceptors;

import com.mage.crm.base.BaseController;
import com.mage.crm.base.CrmConstant;
import com.mage.crm.service.UserService;
import com.mage.crm.util.AssertUtil;
import com.mage.crm.util.Base64Util;
import com.mage.crm.util.CookieUtil;
import com.mage.crm.vo.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Resource
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String userId = CookieUtil.getCookieValue(httpServletRequest, "userId");
        AssertUtil.isTrue(null == userId, CrmConstant.UNLOGIN_CODE, CrmConstant.UNLOGIN_MSG);
        User user = userService.queryUserById(Base64Util.decode(userId));
        AssertUtil.isTrue(null == user, "用户不存在");
        AssertUtil.isTrue("0".equals(user.getIsValid()), "用户已注销");
        return true;
    }

}
