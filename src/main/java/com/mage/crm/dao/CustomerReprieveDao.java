package com.mage.crm.dao;

import com.mage.crm.vo.CustomerReprieve;

import java.util.List;

public interface CustomerReprieveDao {
    List<CustomerReprieve> customerReprieveByLossId(Integer lossId);

    Integer insertReprieve(CustomerReprieve customerReprieve);

    Integer updateReprieve(CustomerReprieve customerReprieve);

    Integer delete(Integer id);
}
