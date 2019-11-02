package com.mage.crm.controller;

import com.mage.crm.service.OrderDetailService;
import com.mage.crm.vo.OrderDetail;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/order_detail")
public class OrderDetailController {
    @Resource
    private OrderDetailService orderDetailService;

    @ResponseBody
    @RequestMapping("/queryOrderDetailsByOrderId")
    public List<OrderDetail> queryOrderDetailsByOrderId(Integer orderId){
        return orderDetailService.queryOrderDetailsByOrderId(orderId);
    }

}
