package com.mage.crm.dao;

import com.mage.crm.dto.DataDicDto;
import com.mage.crm.vo.DataDic;

import java.util.List;

public interface DataDicDao {
    List<DataDic> queryDataDicValueByDataDicName(String dataDicName);

    List<DataDicDto> queryConstitute();
}
