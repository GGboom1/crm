package com.mage.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mage.crm.dao.CustomerDao;
import com.mage.crm.dto.CustomerDto;
import com.mage.crm.dto.DataDicDto;
import com.mage.crm.query.ContributionQuery;
import com.mage.crm.query.CustomerQuery;
import com.mage.crm.util.AssertUtil;
import com.mage.crm.vo.Customer;
import com.mage.crm.vo.CustomerLoss;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class CustomerService {
    @Resource
    private CustomerDao customerDao;
    @Resource
    private CustomerLossService customerLossService;

    public List<Customer> queryAllCustomers() {
        return customerDao.queryAllCustomers();
    }

    public Map<String, Object> queryCustomersByParams(CustomerQuery customerQuery) {
        PageHelper.startPage(customerQuery.getPage(), customerQuery.getRows());
        List<Customer> customers = customerDao.queryCustomersByParams(customerQuery);
        PageInfo<Customer> customerPageInfo = new PageInfo<>(customers);
        Map<String, Object> map = new HashMap<>();
        map.put("total", customerPageInfo.getTotal());
        map.put("rows", customerPageInfo.getList());
        return map;
    }

    public void insert(Customer customer) {
        checkParams(customer.getName(),customer.getFr(),customer.getPhone());
        customer.setKhno("KH"+System.currentTimeMillis());
        customer.setState(0);
        customer.setIsValid(1);
        AssertUtil.isTrue(customerDao.insert(customer) < 1, "添加数据失败");
    }

    public void checkParams(String customerName,String fr,String phone){
        AssertUtil.isTrue(StringUtils.isBlank(customerName),"客户名称不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(fr),"法人不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(phone),"联系电话不能为空");
    }

    public void update(Customer customer) {
        checkParams(customer.getName(),customer.getFr(),customer.getPhone());
        customer.setUpdateDate(new Date());
        AssertUtil.isTrue(customerDao.update(customer) < 1, "修改失败");
    }

    public void delete(Integer[] ids) {
        AssertUtil.isTrue(ids.length < 1, "数据异常");
        customerDao.delete(ids);
    }

    public Customer queryCustomerById(Integer id) {
        AssertUtil.isTrue(id == null, "数据异常");
        return customerDao.queryCustomerById(id);
    }


    public void updateCustomerLossState() {
        List<CustomerLoss> lossList = customerDao.queryCustomerLoss();
        if (lossList.size() > 1){
            for (CustomerLoss customerLoss : lossList){
                customerLoss.setState(1);
                customerLoss.setIsValid(1);
                customerLoss.setCreateDate(new Date());
                customerLoss.setUpdateDate(new Date());
            }
        }
        AssertUtil.isTrue(customerLossService.insertBatch(lossList) < 1, "客户流失数据添加失败");
    }

    public Map<String, Object> queryCustomersContribution(ContributionQuery contributionQuery) {
        PageHelper.startPage(contributionQuery.getPage(), contributionQuery.getRows());
        List<CustomerDto> customerDtoList = customerDao.queryCustomersContribution(contributionQuery.getCustomerName());
        PageInfo<CustomerDto> customerDtoPageInfo = new PageInfo<>(customerDtoList);
        Map<String, Object> map = new HashMap<>();
        map.put("total", customerDtoPageInfo.getTotal());
        map.put("rows", customerDtoPageInfo.getList());
        return map;
    }

}
