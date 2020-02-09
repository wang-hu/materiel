package com.nordson.materiel.controller;

import com.nordson.materiel.common.Constants;
import com.nordson.materiel.common.lang.MyCommonResult;
import com.nordson.materiel.common.pagation.PageList;
import com.nordson.materiel.domain.ReceiveMaterielInfo;
import com.nordson.materiel.service.ReceiveMaterielService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.Optional;

/**
 * @Author: wh
 * @Date: 2020/2/6
 */
@Controller
@RequestMapping("/receive/")
public class ReceiveMaterielController {

    @Autowired
    private ReceiveMaterielService receiveMaterielService;

    @RequestMapping("init")
    public String init() {
        return "receive";
    }

    /**
     * 查询领料人信息
     * @param limit
     * @param offset
     * @param info
     * @return
     */
    @RequestMapping("listReceiveMateriel")
    @ResponseBody
    public PageList<ReceiveMaterielInfo> listReceiveMateriel(Integer limit, Integer offset, ReceiveMaterielInfo info) {

        Integer tOffset = Optional.ofNullable(offset).orElse(Constants.DEFAULT_PAGE);
        Integer tSize = Optional.ofNullable(limit).orElse(Constants.PAGE_SIZE);

        return receiveMaterielService.listMaterielStock(tSize, tOffset, info);
    }

    @RequestMapping("add")
    @ResponseBody
    public MyCommonResult add(ReceiveMaterielInfo info) {
        info.setReceiveDate(new Date());
        return receiveMaterielService.add(info);
    }

    @RequestMapping("remove")
    @ResponseBody
    public MyCommonResult remove(@RequestParam("ids[]") Integer[] ids) {
        return receiveMaterielService.remove(ids);
    }
}
