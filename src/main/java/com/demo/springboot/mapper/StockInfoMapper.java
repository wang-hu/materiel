package com.demo.springboot.mapper;

import com.demo.springboot.common.pagation.Page;
import com.demo.springboot.domain.StockInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface StockInfoMapper {

    int insert(StockInfo record);

    int updateByPrimaryKeySelective(StockInfo record);

    Long queryStockCount(StockInfo record);

    List<Map<String, String>> listMaterielNoAndName(String materielInfo);

    int batchDeleteByPrimaryKeys(Integer[] ids);

    List<StockInfo> listStock(@Param("record") StockInfo record, @Param("page") Page page);

    StockInfo queryStockByMaterielNo(String materielNo);

    int updateTotalByMaterielNo(StockInfo record);

}