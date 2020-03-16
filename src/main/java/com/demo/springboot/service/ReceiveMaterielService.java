package com.demo.springboot.service;

import com.demo.springboot.common.factory.CommonResultFactory;
import com.demo.springboot.common.lang.MyCommonResult;
import com.demo.springboot.common.pagation.Page;
import com.demo.springboot.common.pagation.PageList;
import com.demo.springboot.domain.ReceiveMaterielInfo;
import com.demo.springboot.domain.StockInfo;
import com.demo.springboot.mapper.ReceiveMaterielInfoMapper;
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
public class ReceiveMaterielService {

    @Autowired
    private ReceiveMaterielInfoMapper receiveMaterielInfoMapper;

    @Autowired
    CommonResultFactory commonResultFactory;

    @Autowired
    private StockInfoMapper stockInfoMapper;

    public PageList<ReceiveMaterielInfo> listMaterielStock(Integer limit, Integer offset, ReceiveMaterielInfo info) {

        PageList<ReceiveMaterielInfo> pageList = new PageList<>();

        Long count = receiveMaterielInfoMapper.queryReceiveMaterielCount(info);

        Page page = new Page();
        page.setIndex(offset);
        page.setPageSize(limit);
        page.setCount(count);

        pageList.setPage(page);

        if (count == 0L) {
            pageList.setRows(new ArrayList<>());
            return pageList;
        }

        List<ReceiveMaterielInfo> list = receiveMaterielInfoMapper.listReceiveMateriel(info, page);

        pageList.setRows(list);

        return pageList;

    }

    @Transactional
    public MyCommonResult add(ReceiveMaterielInfo info) {

        if(info == null || StringUtils.isEmpty(info.getMaterielNo())) {
            return commonResultFactory.failResult("物料号不能为空");
        }

        Long receiveNum = Optional.ofNullable(info.getReceiveNum()).orElse(0L);
        info.setReceiveNum(receiveNum);

        //1.获取该物料号库存
        StockInfo stock = stockInfoMapper.queryStockByMaterielNo(info.getMaterielNo());

        if (stock == null) {
            return commonResultFactory.failResult("物料号对应的库存不存在");
        }

        //领取数量超出库存
        if (stock.getTotal() - receiveNum < 0) {
            return commonResultFactory.failResult("领取数量:" + receiveNum + ", 已超出库存:" + stock.getTotal());
        }

        boolean safe = true;
        Long lastTotal = stock.getTotal() - receiveNum;
        if (lastTotal <= stock.getStockNum()) {
            safe = false;
        }

        //2.领料表新增记录
        int ret = receiveMaterielInfoMapper.insert(info);

        //3.库存表减去记录
        StockInfo update = new StockInfo();
        update.setMaterielNo(info.getMaterielNo());
        update.setTotal(lastTotal);
        stockInfoMapper.updateTotalByMaterielNo(update);

        if (ret != 0 && safe) {
            return commonResultFactory.successResult("新增记录成功");
        } else if (ret != 0 && !safe) {
            return commonResultFactory.successResult("新增记录成功，该物料号库存总量已低于安全库存");
        } else  {
            return commonResultFactory.failResult("新增失败");
        }
    }

    public MyCommonResult remove(Integer[] ids) {
        int ret;

        if (ArrayUtils.isEmpty(ids)) {
            return commonResultFactory.successResult("删除失败，缺少主键！");
        }

        try {
            ret = receiveMaterielInfoMapper.batchDeleteByPrimaryKeys(ids);
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
