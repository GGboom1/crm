package com.mage.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mage.crm.dao.CusOrderDao;
import com.mage.crm.query.CusOrderQuery;
import com.mage.crm.vo.Customer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CusOrderService {
    @Resource
    private CusOrderDao cusOrderDao;

    public Map<String, Object> queryOrdersByCid(CusOrderQuery cusOrderQuery) {
        PageHelper.startPage(cusOrderQuery.getPage(), cusOrderQuery.getRows());
        System.out.println(cusOrderQuery == null);
        System.out.println(cusOrderQuery.getCid());
        List<Customer> customerList = cusOrderDao.queryOrdersByCid(cusOrderQuery);
        PageInfo<Customer> customerPageInfo = new PageInfo<>(customerList);
        Map<String, Object> map = new HashMap<>();
        map.put("total", customerPageInfo.getTotal());
        map.put("rows", customerPageInfo.getList());
        return map;
    }

    public Map<String, Object> queryOrderInfoById(Integer orderId) {
        return cusOrderDao.queryOrderInfoById(orderId);
    }
}
