package com.nordson.materiel.controller;

import com.nordson.materiel.common.Constants;
import com.nordson.materiel.common.lang.MyCommonResult;
import com.nordson.materiel.common.pagation.PageList;
import com.nordson.materiel.domain.StockInfo;
import com.nordson.materiel.service.MaterielService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Author: wh
 * @Date: 2020/2/5
 */
@Controller
@RequestMapping("/stock/")
public class MaterielController {

    @Autowired
    private MaterielService materielService;

    @RequestMapping("index")
    public String index() {
        return "index";
    }

    @RequestMapping("init")
    public String init() {
        return "stock";
    }

    /**
     * 查询安全数量
     * @param limit
     * @param offset
     * @param info
     * @return
     */
    @RequestMapping("listMaterielStock")
    @ResponseBody
    public PageList<StockInfo> listMaterielStock(Integer limit, Integer offset, StockInfo info) {

        Integer tOffset = Optional.ofNullable(offset).orElse(Constants.DEFAULT_PAGE);
        Integer tSize = Optional.ofNullable(limit).orElse(Constants.PAGE_SIZE);

        return materielService.listMaterielStock(tSize, tOffset, info);
    }

    @RequestMapping("add")
    @ResponseBody
    public MyCommonResult add(StockInfo info) {
        return materielService.add(info);
    }

    @RequestMapping("edit")
    @ResponseBody
    public MyCommonResult edit(StockInfo info) {
        return materielService.edit(info);
    }

    @RequestMapping("remove")
    @ResponseBody
    public MyCommonResult remove(@RequestParam("ids[]") Integer[] ids) {
        return materielService.remove(ids);
    }

    @RequestMapping("listMaterielNoAndName")
    @ResponseBody
    public List<Map<String, String>> listMaterielNoAndName(String materielInfo) {
        return materielService.listMaterielNoAndName(materielInfo);
    }
}
