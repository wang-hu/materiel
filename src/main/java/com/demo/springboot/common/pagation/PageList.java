package com.demo.springboot.common.pagation;

import java.util.List;

/**
 * 分页列表
 */
public class PageList<T> {
    /** 分页 */
    protected Page page;

    /** 数据列表 */
    protected List<T> rows;

    /**
     * 构造函数
     */
    public PageList() {
        super();
    }

    /**
     * 构造函数
     *
     * @param page 分页
     * @param rows 数据列表
     */
    public PageList(Page page, List<T> rows) {
        super();
        this.page = page;
        this.rows = rows;
    }

    /**
     * @return the page
     */
    public Page getPage() {
        return page;
    }

    /**
     * @param page the page to set
     */
    public void setPage(Page page) {
        this.page = page;
    }

    /**
     * @return the rows
     */
    public List<T> getRows() {
        return rows;
    }

    /**
     * @param rows the list to set
     */
    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
