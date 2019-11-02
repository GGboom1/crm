package com.mage.crm.controller;

import com.mage.crm.query.CusOrderQuery;
import com.mage.crm.service.CusOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("/customer_order")
public class CusOrderController {
    @Resource
    private CusOrderService cusOrderService;

    @RequestMapping("/queryOrdersByCid")
    @ResponseBody
    public Map<String, Object> queryOrdersByCid(CusOrderQuery cusOrderQuery){
        return cusOrderService.queryOrdersByCid(cusOrderQuery);
    }

    @RequestMapping("/queryOrderInfoById")
    @ResponseBody
    public Map<String, Object> queryOrderInfoById(Integer orderId){
        return cusOrderService.queryOrderInfoById(orderId);
    }

}
