package com.demo.springboot.mapper;

import com.demo.springboot.common.pagation.Page;
import com.demo.springboot.domain.IncomeMaterielInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IncomeMaterielInfoMapper {
    int insert(IncomeMaterielInfo record);

    Long queryIncomeMaterielCount(IncomeMaterielInfo record);

    List<IncomeMaterielInfo> listIncomeMateriel(@Param("record") IncomeMaterielInfo record, @Param("page") Page page);

    int batchDeleteByPrimaryKeys(Integer[] ids);
}