package com.sealll.bean;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author sealll
 * @time 2021/5/8 12:04
 */
public class Page<T> {
    private List<T> list;
    private PageInfo<T> page;

    public Page() {
    }

    public Page(List<T> list, PageInfo<T> page) {
        this.list = list;
        this.page = page;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public PageInfo<T> getPage() {
        return page;
    }

    public void setPage(PageInfo<T> page) {
        this.page = page;
    }
}
