package com.mage.crm.controller;

import com.mage.crm.base.BaseController;
import com.mage.crm.model.MessageModel;
import com.mage.crm.query.CustomerLossQuery;
import com.mage.crm.service.CustomerLossService;
import com.mage.crm.vo.CustomerLoss;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("/customer_loss")
public class CustomerLossController extends BaseController {
    @Resource
    private CustomerLossService customerLossService;

    @RequestMapping("/index")
    public String index(){
        return "customer_loss";
    }

    @RequestMapping("/queryCustomerLossesByParams")
    @ResponseBody
    public Map<String, Object> queryCustomerLossesByParams(CustomerLossQuery customerLossQuery){
        return customerLossService.queryCustomerLossesByParams(customerLossQuery);
    }

    @RequestMapping("/toRepreivePage/{id}")
    public ModelAndView toRepreivePage(@PathVariable("id") Integer id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("customer_repri");
        CustomerLoss customerLoss = customerLossService.queryCustomerLossById(id);
        modelAndView.addObject("customerLoss", customerLoss);
        return modelAndView;
    }

    @RequestMapping("/updateCustomerLossState")
    @ResponseBody
    public MessageModel updateCustomerLossState(Integer lossId, String lossReason){
        customerLossService.updateCustomerLossState(lossId,lossReason);
        return createMessageModel("流失操作执行成功");
    }
}
