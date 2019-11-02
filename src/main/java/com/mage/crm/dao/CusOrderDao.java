package com.mage.crm.dao;

import com.mage.crm.query.CusOrderQuery;
import com.mage.crm.vo.Customer;

import java.util.List;
import java.util.Map;

public interface CusOrderDao {

    List<Customer> queryOrdersByCid(CusOrderQuery cusOrderQuery);

    Map<String, Object> queryOrderInfoById(Integer orderId);
}
