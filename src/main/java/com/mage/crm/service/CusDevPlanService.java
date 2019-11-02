package com.mage.crm.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mage.crm.dao.CusDevPlanDao;
import com.mage.crm.model.MessageModel;
import com.mage.crm.query.CusDevPlanQuery;
import com.mage.crm.util.AssertUtil;
import com.mage.crm.vo.CusDevPlan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CusDevPlanService {
    @Resource
    private CusDevPlanDao cusDevPlanDao;

    public Map<String, Object> queryCusDevPlans(CusDevPlanQuery cusDevPlanQuery) {
        PageHelper.startPage(cusDevPlanQuery.getPage(), cusDevPlanQuery.getRows());
        List<CusDevPlan> cusDevPlans = cusDevPlanDao.queryCusDevPlans(cusDevPlanQuery.getSaleChanceId());
        PageInfo<CusDevPlan> listPageInfo = new PageInfo<>(cusDevPlans);
        Map<String, Object> map = new HashMap<>();
        map.put("total", listPageInfo.getTotal());
        map.put("rows", listPageInfo.getList());
        return map;
    }

    public void saveCusDevPlan(CusDevPlan cusDevPlan) {
        cusDevPlan.setCreateDate(new Date());
        setCusDevPlan(cusDevPlan);
        AssertUtil.isTrue(cusDevPlanDao.saveCusDevPlan(cusDevPlan) < 1, "保存数据失败");
    }

    private void setCusDevPlan(CusDevPlan cusDevPlan){
        cusDevPlan.setIsValid(1);
        cusDevPlan.setUpdateDate(new Date());
    }

    public void deleteCusDevPlan(Integer id) {
        AssertUtil.isTrue(id == null || id == 0, "数据异常");
        AssertUtil.isTrue(cusDevPlanDao.deleteCusDevPlan(id) < 1, "删除数据失败");
    }

    public void updateCusDevPlan(CusDevPlan cusDevPlan) {
        AssertUtil.isTrue(cusDevPlan == null, "数据异常");
        AssertUtil.isTrue(cusDevPlanDao.updateCusDevPlan(cusDevPlan) < 1, "更新数据失败");
    }


}
