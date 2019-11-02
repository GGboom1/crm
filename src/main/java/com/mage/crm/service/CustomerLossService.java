package com.mage.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mage.crm.dao.CustomerLossDao;
import com.mage.crm.query.CustomerLossQuery;
import com.mage.crm.util.AssertUtil;
import com.mage.crm.vo.CustomerLoss;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerLossService {
    @Resource
    private CustomerLossDao customerLossDao;

    public Map<String, Object> queryCustomerLossesByParams(CustomerLossQuery customerLossQuery) {
        PageHelper.startPage(customerLossQuery.getPage(), customerLossQuery.getRows());
        List<CustomerLoss> customerLossList = customerLossDao.queryCustomerLossesByParams(customerLossQuery);
        PageInfo<CustomerLoss> customerLossPageInfo = new PageInfo<>(customerLossList);
        Map<String, Object> map = new HashMap<>();
        map.put("total", customerLossPageInfo.getTotal());
        map.put("rows", customerLossPageInfo.getList());
        return map;
    }

    public Integer insertBatch(List<CustomerLoss> lossList) {
        return customerLossDao.insertBatch(lossList);
    }

    public CustomerLoss queryCustomerLossById(Integer id) {
        return customerLossDao.queryCustomerLossById(id);
    }


    public void updateCustomerLossState(Integer lossId, String lossReason) {
        AssertUtil.isTrue(customerLossDao.updateCustomerLossState(lossId, lossReason) < 1, "流失操作失败");
    }
}
