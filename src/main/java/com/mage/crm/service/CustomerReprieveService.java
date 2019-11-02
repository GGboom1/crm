package com.mage.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mage.crm.dao.CustomerReprieveDao;
import com.mage.crm.query.CustomerReprieveQuery;
import com.mage.crm.util.AssertUtil;
import com.mage.crm.vo.CustomerLoss;
import com.mage.crm.vo.CustomerReprieve;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerReprieveService {
    @Resource
    private CustomerReprieveDao customerReprieveDao;
    @Resource
    private CustomerLossService customerLossService;

    public Map<String, Object> customerReprieveByLossId(CustomerReprieveQuery customerReprieveQuery) {
        PageHelper.startPage(customerReprieveQuery.getPage(), customerReprieveQuery.getRows());
        List<CustomerReprieve> customerReprieveList = customerReprieveDao.customerReprieveByLossId(customerReprieveQuery.getLossId());
        PageInfo<CustomerReprieve> customerReprievePageInfo = new PageInfo<>(customerReprieveList);
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", customerReprievePageInfo.getTotal());
        map.put("rows", customerReprievePageInfo.getList());
        return map;
    }

    public void insertReprieve(CustomerReprieve customerReprieve) {
        checkParams(customerReprieve.getLossId(), customerReprieve.getMeasure());
        if(customerReprieve.getId() == null){
            customerReprieve.setIsValid(1);
            customerReprieve.setCreateDate(new Date());
            customerReprieve.setUpdateDate(new Date());
            AssertUtil.isTrue(customerReprieveDao.insertReprieve(customerReprieve) < 1, "添加暂缓措施失败");
        }else {
            customerReprieve.setUpdateDate(new Date());
            AssertUtil.isTrue(customerReprieveDao.updateReprieve(customerReprieve) < 1, "修改暂缓措施失败");
        }

    }

    public void checkParams(Integer lossId,String measure){
        AssertUtil.isTrue(StringUtils.isBlank(measure),"暂缓措施不能为空");
        CustomerLoss customerLoss = customerLossService.queryCustomerLossById(lossId);
        AssertUtil.isTrue(lossId==null||customerLoss==null, "流失记录不存在");
    }

    public void delete(Integer id) {
        AssertUtil.isTrue(customerReprieveDao.delete(id) < 1, "删除暂缓措施失败");
    }
}
