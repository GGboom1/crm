package com.mage.crm.dao;

import com.mage.crm.dto.CustomerDto;
import com.mage.crm.dto.DataDicDto;
import com.mage.crm.query.CusOrderQuery;
import com.mage.crm.query.CustomerQuery;
import com.mage.crm.vo.Customer;
import com.mage.crm.vo.CustomerLoss;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface CustomerDao {
    @Select("SELECT id, name FROM t_customer WHERE is_valid = 1 and state = 0 ")
    List<Customer> queryAllCustomers();

    List<Customer> queryCustomersByParams(CustomerQuery customerQuery);

    Integer insert(Customer customer);

    Integer update(Customer customer);

    Integer delete(Integer[] ids);

    Customer queryCustomerById(Integer id);

    List<CustomerLoss> queryCustomerLoss();

    List<CustomerDto> queryCustomersContribution(@Param("customerName") String customerName);

}
