package com.mage.crm.base;


import com.mage.crm.model.MessageModel;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

public class BaseController<T> {

    @ModelAttribute
    public void preMethod(HttpServletRequest request){
        request.setAttribute("ctx",request.getContextPath());
    }
    
    public MessageModel createMessageModel(String msg){
        MessageModel messageModel = new MessageModel();
        messageModel.setMsg(msg);
        return messageModel;
    }

    public MessageModel createMessageModel(Integer code, String msg){
        MessageModel messageModel = new MessageModel();
        messageModel.setCode(code);
        messageModel.setMsg(msg);
        return messageModel;
    }

    public MessageModel<T> createMessageModel(Integer code, String msg, T t){
        MessageModel<T> messageModel = new MessageModel<>();
        messageModel.setCode(code);
        messageModel.setMsg(msg);
        messageModel.setResult(t);
        return messageModel;
    }
}
