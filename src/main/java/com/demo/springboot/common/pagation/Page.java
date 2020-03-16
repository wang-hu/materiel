package com.demo.springboot.common.pagation;


import lombok.Data;

import java.io.Serializable;

/**
 * 分页
 */
@Data
public class Page implements Serializable {

    /** 每页记录数，默认10条 */
    protected int             pageSize         = 10;

    /** 记录总数 */
    protected long            count;

    /** 当前页的索引 */
    protected int             index;
}


