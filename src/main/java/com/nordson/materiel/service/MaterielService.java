package com.nordson.materiel.service;

import com.nordson.materiel.common.factory.CommonResultFactory;
import com.nordson.materiel.common.lang.MyCommonResult;
import com.nordson.materiel.common.pagation.Page;
import com.nordson.materiel.common.pagation.PageList;
import com.nordson.materiel.domain.StockInfo;
import com.nordson.materiel.mapper.StockInfoMapper;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.reflection.ArrayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: wh
 * @Date: 2020/2/5
 */
@Service
public class MaterielService {

    @Autowired
    private StockInfoMapper stockInfoMapper;

    @Autowired
    CommonResultFactory commonResultFactory;

    public PageList<StockInfo> listMaterielStock(Integer limit, Integer offset, StockInfo info) {

        PageList<StockInfo> pageList = new PageList<>();

        Long count = stockInfoMapper.queryStockCount(info);

        Page page = new Page();
        page.setIndex(offset);
        page.setPageSize(limit);
        page.setCount(count);

        pageList.setPage(page);

        if (count == 0L) {
            pageList.setRows(new ArrayList<>());
            return pageList;
        }

        List<StockInfo> list = stockInfoMapper.listStock(info, page);

         pageList.setRows(list);

        return pageList;

    }

    public MyCommonResult add(StockInfo info) {
        int ret;
        try {
            //新增物料，设置初始总数为0
            info.setTotal(0L);
            ret = stockInfoMapper.insert(info);
        } catch (DuplicateKeyException e){
            return commonResultFactory.failResult("新增失败,物料号重复");
        } catch (Exception e) {
            return commonResultFactory.failResult("新增失败,未知异常");
        }

        if (ret != 0) {
            return commonResultFactory.successResult("新增成功");
        } else {
            return commonResultFactory.failResult("新增失败");
        }
    }

    public MyCommonResult edit(StockInfo info) {
        int ret;
        try {
            ret = stockInfoMapper.updateByPrimaryKeySelective(info);
        } catch (Exception e) {
            return commonResultFactory.failResult("修改失败,物料号不能重复");
        }

        if (ret != 0) {
            return commonResultFactory.successResult("修改成功");
        } else {
            return commonResultFactory.failResult("修改失败");
        }
    }

    public MyCommonResult remove(Integer[] ids) {
        int ret;

        if (ArrayUtils.isEmpty(ids)) {
            return commonResultFactory.successResult("删除失败，缺少主键！");
        }

        try {
            ret = stockInfoMapper.batchDeleteByPrimaryKeys(ids);
        } catch (Exception e) {
            return commonResultFactory.failResult("删除失败,发生未知异常");
        }

        if (ret != 0) {
            return commonResultFactory.successResult("删除成功");
        } else {
            return commonResultFactory.failResult("删除失败");
        }
    }

    public List<Map<String, String>> listMaterielNoAndName(String materielInfo) {

        return stockInfoMapper.listMaterielNoAndName(materielInfo);
    }

}
