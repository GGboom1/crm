package com.mage.crm.service;

import com.mage.crm.dao.DataDicDao;
import com.mage.crm.dto.DataDicDto;
import com.mage.crm.util.AssertUtil;
import com.mage.crm.vo.DataDic;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DataDicService {
    @Resource
    private DataDicDao dataDicDao;

    public List<DataDic> queryDataDicValueByDataDicName(String dataDicName) {
        return dataDicDao.queryDataDicValueByDataDicName(dataDicName);
    }

    public Map<String, Object> queryConstitute() {
        List<DataDicDto> dataDicDtoList = dataDicDao.queryConstitute();
        Map<String, Object> map = new HashMap<>();
        if(dataDicDtoList.size() > 0){
            List<String> dataDicValue = new ArrayList<String>();
            List<String> total = new ArrayList<String>();
            for(DataDicDto dataDicDto:dataDicDtoList){
                dataDicValue.add(dataDicDto.getDataDicValue());
                total.add(dataDicDto.getTotal());
            }
            map.put("dataDicValue", dataDicValue);
            map.put("total", total);
        }
        return map;
    }
}
