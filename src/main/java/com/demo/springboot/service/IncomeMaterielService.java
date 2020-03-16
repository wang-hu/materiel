package com.demo.springboot.service;

import com.demo.springboot.common.factory.CommonResultFactory;
import com.demo.springboot.common.lang.MyCommonResult;
import com.demo.springboot.common.pagation.Page;
import com.demo.springboot.common.pagation.PageList;
import com.demo.springboot.domain.IncomeMaterielInfo;
import com.demo.springboot.domain.StockInfo;
import com.demo.springboot.mapper.IncomeMaterielInfoMapper;
import com.demo.springboot.mapper.StockInfoMapper;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Author: wh
 * @Date: 2020/2/6
 */
@Service
public class IncomeMaterielService {

    @Autowired
    private IncomeMaterielInfoMapper incomeMaterielInfoMapper;

    @Autowired
    CommonResultFactory commonResultFactory;

    @Autowired
    private StockInfoMapper stockInfoMapper;

    public PageList<IncomeMaterielInfo> listMaterielStock(Integer limit, Integer offset, IncomeMaterielInfo info) {

        PageList<IncomeMaterielInfo> pageList = new PageList<>();

        Long count = incomeMaterielInfoMapper.queryIncomeMaterielCount(info);

        Page page = new Page();
        page.setIndex(offset);
        page.setPageSize(limit);
        page.setCount(count);

        pageList.setPage(page);

        if (count == 0L) {
            pageList.setRows(new ArrayList<>());
            return pageList;
        }

        List<IncomeMaterielInfo> list = incomeMaterielInfoMapper.listIncomeMateriel(info, page);

        pageList.setRows(list);

        return pageList;
    }

    @Transactional
    public MyCommonResult add(IncomeMaterielInfo info) {

        if(info == null || StringUtils.isEmpty(info.getMaterielNo())) {
            return commonResultFactory.failResult("物料号不能为空");
        }

        Long receiveNum = Optional.ofNullable(info.getIncomeNum()).orElse(0L);
        info.setIncomeNum(receiveNum);

        //1.获取该物料号库存
        StockInfo stock = stockInfoMapper.queryStockByMaterielNo(info.getMaterielNo());

        if (stock == null) {
            return commonResultFactory.failResult("物料号对应的库存不存在");
        }

        //2.领料表新增记录
        int ret = incomeMaterielInfoMapper.insert(info);

        //3.库存表减去记录
        StockInfo update = new StockInfo();
        update.setMaterielNo(info.getMaterielNo());
        update.setTotal(stock.getTotal() + info.getIncomeNum());
        stockInfoMapper.updateTotalByMaterielNo(update);

        if (ret != 0) {
            return commonResultFactory.successResult("新增记录成功");
        } else {
            return commonResultFactory.failResult("新增失败");
        }
    }

    public MyCommonResult remove(Integer[] ids) {
        int ret;

        if (ArrayUtils.isEmpty(ids)) {
            return commonResultFactory.successResult("删除失败，缺少主键！");
        }

        try {
            ret = incomeMaterielInfoMapper.batchDeleteByPrimaryKeys(ids);
        } catch (Exception e) {
            return commonResultFactory.failResult("删除失败,发生未知异常");
        }

        if (ret != 0) {
            return commonResultFactory.successResult("删除成功");
        } else {
            return commonResultFactory.failResult("删除失败");
        }
    }
}
