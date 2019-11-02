package com.mage.crm.controller;

import com.mage.crm.base.BaseController;
import com.mage.crm.model.MessageModel;
import com.mage.crm.query.CusDevPlanQuery;
import com.mage.crm.service.CusDevPlanService;
import com.mage.crm.service.SaleChanceService;
import com.mage.crm.vo.CusDevPlan;
import com.mage.crm.vo.SaleChance;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("/cus_dev_plan")
public class CusDevPlanController extends BaseController {
    @Resource
    private CusDevPlanService cusDevPlanService;
    @Resource
    private SaleChanceService saleChanceService;

    @RequestMapping("/index")
    public ModelAndView index(Integer id){
        SaleChance saleChance = saleChanceService.querySaleChanceById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("cus_dev_plan_detail");
        modelAndView.addObject("saleChance", saleChance);
        return modelAndView;
    }

    @RequestMapping("/queryCusDevPlans")
    @ResponseBody
    public Map<String, Object> queryCusDevPlans(CusDevPlanQuery cusDevPlanQuery){
        Integer page = cusDevPlanQuery.getPage();
        Integer rows = cusDevPlanQuery.getRows();
        return cusDevPlanService.queryCusDevPlans(cusDevPlanQuery);
    }

    @RequestMapping("/saveCusDevPlan")
    @ResponseBody
    public MessageModel saveCusDevPlan(CusDevPlan cusDevPlan){
        cusDevPlanService.saveCusDevPlan(cusDevPlan);
        return createMessageModel("添加数据成功");
    }

    @ResponseBody
    @RequestMapping("/deleteCusDevPlan")
    public MessageModel deleteCusDevPlan(Integer id){
        cusDevPlanService.deleteCusDevPlan(id);
        return createMessageModel("删除数据成功");
    }

    @RequestMapping("/updateCusDevPlan")
    @ResponseBody
    public MessageModel updateCusDevPlan(CusDevPlan cusDevPlan){
        cusDevPlanService.updateCusDevPlan(cusDevPlan);
        return createMessageModel("更新数据成功");
    }

}
