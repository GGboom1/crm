package com.mage.crm.base.exceptions;


import com.alibaba.fastjson.JSON;
import com.mage.crm.base.CrmConstant;
import com.mage.crm.model.MessageModel;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class GlobalException implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) {
        ModelAndView modelAndView = createDefaultModelAndView(httpServletRequest);
        ParamsException paramsException;
        if(handler instanceof HandlerMethod){
            //处理json异常
            ResponseBody responseBody = ((HandlerMethod) handler).getMethod().getAnnotation(ResponseBody.class);
            if(responseBody != null){
                MessageModel messageModel = new MessageModel();
                messageModel.setMsg(CrmConstant.OPS_FAILED_MSG);
                messageModel.setCode(CrmConstant.OPS_FAILED_CODE);
                if (e instanceof ParamsException){
                    paramsException= (ParamsException) e;
                    messageModel.setCode(paramsException.getCode());
                    messageModel.setMsg(paramsException.getMsg());
                }
                httpServletResponse.setContentType("application/json;charset=uft-8");
                httpServletResponse.setCharacterEncoding("utf-8");
                PrintWriter printWriter = null;
                try {
                    printWriter = httpServletResponse.getWriter();
                } catch (IOException ioE) {
                    ioE.printStackTrace();
                }finally {
                    if(printWriter!=null){
                        printWriter.write(JSON.toJSONString(messageModel));
                        printWriter.flush();
                        printWriter.close();
                    }
                }
                return null;
            }
            //处理视图异常
            if(e instanceof ParamsException){
                paramsException = (ParamsException) e;
                modelAndView.addObject("code",paramsException.getCode());
                modelAndView.addObject("msg",paramsException.getMsg());
                return modelAndView;
            }
        }
        return null;
    }

    public ModelAndView createDefaultModelAndView(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        modelAndView.addObject("code", CrmConstant.OPS_FAILED_CODE);
        modelAndView.addObject("msg", CrmConstant.OPS_FAILED_MSG);
        modelAndView.addObject("uri",request.getRequestURI());
        modelAndView.addObject("ctx",request.getContextPath());
        return modelAndView;
    }
}
