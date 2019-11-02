package com.mage.crm.proxy;

import com.mage.crm.PermissionProxy;
import com.mage.crm.util.AssertUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.List;

@Component
@Aspect
public class PermissionInterceptors {

    @Resource
    private HttpSession session;

    @Pointcut(value = "@annotation(com.mage.crm.PermissionProxy)")
    public void cut(){}

    @Before(value = "cut()")
    public  void before(JoinPoint jp) throws Throwable {
        // 方法执行前进行拦截
        MethodSignature methodSignature= (MethodSignature) jp.getSignature();
        Method method= methodSignature.getMethod();
        PermissionProxy requestPermission= method.getAnnotation(PermissionProxy.class);
        if(null !=requestPermission){
            System.out.println("权限值:"+requestPermission.aclVal());
            List<String> permissions= (List<String>) session.getAttribute("userPermission");
            AssertUtil.isTrue(CollectionUtils.isEmpty(permissions),"暂无权限!");
            AssertUtil.isTrue(!(permissions.contains(requestPermission.aclVal())),"暂无权限!");
        }
    }
}
