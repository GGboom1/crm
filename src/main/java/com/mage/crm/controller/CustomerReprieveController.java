package com.mage.crm.controller;

import com.mage.crm.base.BaseController;
import com.mage.crm.model.MessageModel;
import com.mage.crm.query.CustomerReprieveQuery;
import com.mage.crm.service.CustomerReprieveService;
import com.mage.crm.vo.CustomerReprieve;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@RequestMapping("/customer_repri")
@Controller
public class CustomerReprieveController extends BaseController {
    @Resource
    private CustomerReprieveService customerReprieveService;

    @ResponseBody
    @RequestMapping("/customerReprieveByLossId")
    public Map<String, Object> customerReprieveByLossId(CustomerReprieveQuery customerReprieveQuery){
        return customerReprieveService.customerReprieveByLossId(customerReprieveQuery);
    }

    @ResponseBody
    @RequestMapping("/insertReprieve")
    public MessageModel insertReprieve(CustomerReprieve customerReprieve){
        customerReprieveService.insertReprieve(customerReprieve);
        return createMessageModel("保存成功");
    }

    @RequestMapping("/delete")
    @ResponseBody
    public MessageModel delete(Integer id){
        customerReprieveService.delete(id);
        return createMessageModel("删除暂缓措施成功");
    }


}
