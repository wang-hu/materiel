package com.nordson.materiel.mapper;

import com.nordson.materiel.common.pagation.Page;
import com.nordson.materiel.domain.ReceiveMaterielInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReceiveMaterielInfoMapper {
    int insert(ReceiveMaterielInfo record);

    Long queryReceiveMaterielCount(ReceiveMaterielInfo record);

    List<ReceiveMaterielInfo> listReceiveMateriel(@Param("record") ReceiveMaterielInfo record, @Param("page") Page page);

    int batchDeleteByPrimaryKeys(Integer[] ids);
}