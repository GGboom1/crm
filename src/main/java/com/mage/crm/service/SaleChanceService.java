package com.mage.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mage.crm.dao.SaleChanceDao;
import com.mage.crm.query.CusDevPlanQuery;
import com.mage.crm.query.SaleChanceQuery;
import com.mage.crm.util.AssertUtil;
import com.mage.crm.vo.SaleChance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SaleChanceService {
    @Resource
    private SaleChanceDao saleChanceDao;

    public Map<String, Object> querySaleChancesByParams(SaleChanceQuery saleChanceQuery) {
        AssertUtil.isTrue(saleChanceQuery == null, "参数错误");
        PageHelper.startPage(saleChanceQuery.getPage(), saleChanceQuery.getRows());
        List<SaleChance> saleChances = saleChanceDao.querySaleChance(saleChanceQuery);
        PageInfo<SaleChance> saleChancePageInfo = new PageInfo<>(saleChances);
        Map<String, Object> map = new HashMap<>();
        map.put("total", saleChancePageInfo.getTotal());
        map.put("rows", saleChances);
        return map;
    }

    public SaleChance querySaleChanceById(Integer id){
        SaleChance saleChance = saleChanceDao.querySaleChanceById(id);
        AssertUtil.isTrue(saleChance == null, "参数异常");
        return saleChance;
    }

    public void insert(SaleChance saleChance) {
        assertParams(saleChance.getLinkMan(), saleChance.getLinkPhone(), saleChance.getCgjl());
        saleChance.setIsValid(1);
        saleChance.setUpdateDate(new Date());
        saleChance.setCreateDate(new Date());
        saleChance.setDevResult(0);//未开发的状态，默认的初始状态
        if (StringUtils.isBlank(saleChance.getAssignMan())){//未分配
            saleChance.setState(0);
        }else {//已分配
            saleChance.setState(1);
            saleChance.setAssignTime(new Date());
        }
        AssertUtil.isTrue(saleChanceDao.insert(saleChance) < 1, "营销机会添加失败");
    }

    public void update(SaleChance saleChance) {
        assertParams(saleChance.getLinkMan(), saleChance.getLinkPhone(), saleChance.getCgjl());
        saleChance.setUpdateDate(new Date());
        saleChance.setDevResult(0);//未开发的状态，默认的初始状态
        if (StringUtils.isBlank(saleChance.getAssignMan())){//未分配
            saleChance.setState(0);
        }else {//已分配
            saleChance.setState(1);
        }
        AssertUtil.isTrue(saleChanceDao.update(saleChance) < 1, "营销机会添加失败");
    }

    private void assertParams(String str1, String str2, String str3){
        AssertUtil.isTrue(StringUtils.isBlank(str1),"联系人不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(str2),"联系电话不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(str3),"成功几率不能为空");
    }

    public void delete(Integer[] ids) {
        AssertUtil.isTrue(ids.length == 0, "请至少选择一条数据");
        saleChanceDao.delete(ids);
    }

    public void updateSaleChanceDevResult(Integer devResult,Integer saleChanceId) {
        AssertUtil.isTrue(devResult == null || saleChanceId == null, "数据异常");
        AssertUtil.isTrue(saleChanceDao.updateSaleChanceDevResult(devResult, saleChanceId) < 1, "修改失败");
    }
}
