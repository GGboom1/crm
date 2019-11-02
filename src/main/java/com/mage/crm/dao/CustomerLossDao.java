package com.mage.crm.dao;

import com.mage.crm.query.CustomerLossQuery;
import com.mage.crm.vo.CustomerLoss;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomerLossDao {
    List<CustomerLoss> queryCustomerLossesByParams(CustomerLossQuery customerLossQuery);

    Integer insertBatch(List<CustomerLoss> lossList);

    CustomerLoss queryCustomerLossById(Integer id);

    Integer updateCustomerLossState(@Param("id") Integer lossId, @Param("lossReason") String lossReason);
}
