package com.mage.crm.controller;

import com.mage.crm.PermissionProxy;
import com.mage.crm.base.BaseController;
import com.mage.crm.base.exceptions.ParamsException;
import com.mage.crm.model.MessageModel;
import com.mage.crm.query.CusDevPlanQuery;
import com.mage.crm.query.SaleChanceQuery;
import com.mage.crm.service.SaleChanceService;
import com.mage.crm.vo.SaleChance;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("/sale_chance")
public class SaleChanceController extends BaseController {
    @Resource
    private SaleChanceService saleChanceService;

    @RequestMapping("/index/{id}")
    public String index(@PathVariable("id") String id){
        switch (id){
            case "1":
                return "sale_chance";
            case "2":
                return "cus_dev_plan";
            default:
                return "error";
        }
    }

    @RequestMapping("/querySaleChancesByParams")
    @ResponseBody
    public Map<String, Object> querySaleChancesByParams(SaleChanceQuery saleChanceQuery){
        return saleChanceService.querySaleChancesByParams(saleChanceQuery);
    }

    @RequestMapping("/insert")
    @ResponseBody
    public MessageModel insert(SaleChance saleChance){
        saleChanceService.insert(saleChance);
        return createMessageModel("添加数据成功");
    }

    @ResponseBody
    @RequestMapping("/update")
    public MessageModel update(SaleChance saleChance){
        saleChanceService.update(saleChance);
        return createMessageModel("修改数据成功");
    }

    @ResponseBody
    @RequestMapping("/delete")
    @PermissionProxy(aclVal = "101003")
    public MessageModel delete(Integer[] ids){
        saleChanceService.delete(ids);
        return createMessageModel("删除数据成功");
    }

    @RequestMapping("/updateSaleChanceDevResult")
    @ResponseBody
    public MessageModel updateSaleChanceDevResult(Integer devResult,Integer saleChanceId){
        saleChanceService.updateSaleChanceDevResult(devResult, saleChanceId);
        return createMessageModel("操作成功");
    }
}
