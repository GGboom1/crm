package com.mage.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mage.crm.dao.CustomerServeDao;
import com.mage.crm.dto.CustomerServeDto;
import com.mage.crm.query.CustomerServeQuery;
import com.mage.crm.util.AssertUtil;
import com.mage.crm.util.CookieUtil;
import com.mage.crm.vo.CustomerServe;
import com.mage.crm.vo.ServeType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class CustomerServeService {
    @Resource
    private CustomerServeDao customerServeDao;

    public void insert(CustomerServe customerServe){
        cheakCustomerServeParams(customerServe.getServeType(),customerServe.getCustomer(),customerServe.getServiceRequest());
        customerServe.setState(ServeType.CREATE.getType());
        customerServe.setCreateDate(new Date());
        customerServe.setUpdateDate(new Date());
        customerServe.setIsValid(1);
        AssertUtil.isTrue(customerServeDao.insert(customerServe)<1,"服务创建失败");
    }
    private void cheakCustomerServeParams(String serveType, String customer,String serviceRequest) {
        AssertUtil.isTrue(StringUtils.isBlank(serveType), "服务类型非空!");
        AssertUtil.isTrue(StringUtils.isBlank(customer), "客户名称非空!");
        AssertUtil.isTrue(StringUtils.isBlank(serviceRequest), "内容非空!");
    }

    public void update(CustomerServe customerServe , HttpServletRequest request){
        customerServe.setUpdateDate(new Date());
        if(customerServe.getState().equals(ServeType.ASSIGN.getType())){
            customerServe.setAssigner(CookieUtil.getCookieValue(request,"trueName"));
            customerServe.setAssignTime(new Date());
        }else if(customerServe.getState().equals(ServeType.PROCEED.getType())){
            AssertUtil.isTrue(StringUtils.isBlank(customerServe.getServiceProce()),"处理内容不能为空");
            customerServe.setServiceProceTime(new Date());
        }else if(customerServe.getState().equals(ServeType.FEEDBACK.getType())) {
            AssertUtil.isTrue(StringUtils.isBlank(customerServe.getServiceProceResult()),"处理结果不能为空");

            AssertUtil.isTrue(StringUtils.isBlank(customerServe.getMyd()),"满意度不能为空");

            customerServe.setState(ServeType.ARCHIVE.getType());
        }
        AssertUtil.isTrue(customerServeDao.update(customerServe)<1,"操作失败");
    }

    public Map<String,Object> queryCustomerServesByParams(CustomerServeQuery customerServeQuery){
        PageHelper.startPage(customerServeQuery.getPage(),customerServeQuery.getRows());
        List<CustomerServe> orderList = customerServeDao.queryCustomerServesByParams(customerServeQuery.getState());
        PageInfo<CustomerServe> pageInfo = new PageInfo<>(orderList);
        Map<String,Object> map = new HashMap<>();
        map.put("total",pageInfo.getTotal());
        map.put("rows",pageInfo.getList());
        return map;

    }

    public Map<String, Object> queryServe() {
        List<CustomerServeDto> customerServeDtoList = customerServeDao.queryServe();
        Map<String, Object> map = new HashMap<>();
        if(customerServeDtoList != null && customerServeDtoList.size() > 0){
            map.put("map", customerServeDtoList);
            List<String> title = new ArrayList<>();
            for (CustomerServeDto customerServeDto : customerServeDtoList){
                title.add(customerServeDto.getName());
            }
            map.put("title", title);
        }
        return map;
    }
}

