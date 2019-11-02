package com.mage.crm.dao;

import com.mage.crm.dto.CustomerServeDto;
import com.mage.crm.vo.CustomerServe;

import java.util.List;

public interface CustomerServeDao {
    Integer insert(CustomerServe customerServe);

    Integer update(CustomerServe customerServe);

    List<CustomerServe> queryCustomerServesByParams(String state);

    List<CustomerServeDto> queryServe();
}
