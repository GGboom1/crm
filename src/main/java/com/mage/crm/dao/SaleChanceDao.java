package com.mage.crm.dao;

import com.mage.crm.query.CusDevPlanQuery;
import com.mage.crm.query.SaleChanceQuery;
import com.mage.crm.vo.SaleChance;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface SaleChanceDao {
    /**
     * 多条件查询营销机遇
     * @param saleChanceQuery
     * @return
     */
    List<SaleChance> querySaleChance(SaleChanceQuery saleChanceQuery);

    @Insert("insert into t_sale_chance(chance_source,customer_name,cgjl,overview,link_man,link_phone,"
            + "description,create_man,assign_man,assign_time,state,dev_result,is_valid,create_date,update_date)"
            + " values(#{chanceSource},#{customerName},#{cgjl},#{overview},#{linkMan},#{linkPhone},"
            + " #{description},#{createMan},#{assignMan},#{assignTime},#{state},#{devResult},#{isValid},"
            + "#{createDate},#{updateDate})")
    Integer insert(SaleChance saleChance);


    @Update("update t_sale_chance set chance_source=#{chanceSource},customer_name=#{customerName},"
            + "cgjl=#{cgjl},overview=#{overview},link_man=#{linkMan},link_phone=#{linkPhone},"
            + "description=#{description},assign_man=#{assignMan},assign_time=#{assignTime},state=#{state},"
            + "update_date=#{updateDate} where id=#{id} and is_valid=1")
    Integer update(SaleChance saleChance);

    Integer delete(Integer[] ids);

    SaleChance querySaleChanceById(Integer id);

    Integer updateSaleChanceDevResult(@Param("dev") Integer devResult, @Param("id") Integer saleChanceId);
}
