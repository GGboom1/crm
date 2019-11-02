package com.mage.crm.dao;

import com.mage.crm.query.CusDevPlanQuery;
import com.mage.crm.vo.CusDevPlan;

import java.util.List;

public interface CusDevPlanDao {
    List<CusDevPlan> queryCusDevPlans(Integer saleChanceId);

    Integer saveCusDevPlan(CusDevPlan cusDevPlan);

    Integer deleteCusDevPlan(Integer id);

    Integer updateCusDevPlan(CusDevPlan cusDevPlan);


}
