package com.mage.crm.model;

import com.mage.crm.base.CrmConstant;

public class MessageModel<T> {
    private Integer code = CrmConstant.OPS_SUCCESS_CODE;
    private String msg = CrmConstant.OPS_SUCCESS_MSG;
    private T result;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
