package com.nordson.materiel.mapper;

import com.nordson.materiel.common.pagation.Page;
import com.nordson.materiel.domain.IncomeMaterielInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IncomeMaterielInfoMapper {
    int insert(IncomeMaterielInfo record);

    Long queryIncomeMaterielCount(IncomeMaterielInfo record);

    List<IncomeMaterielInfo> listIncomeMateriel(@Param("record") IncomeMaterielInfo record, @Param("page") Page page);

    int batchDeleteByPrimaryKeys(Integer[] ids);
}