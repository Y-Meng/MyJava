package com.mingtai.base.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zkzc-mcy on 2017/9/12.
 * 分页返回结果封装类
 */
public class Page<T> implements Serializable{

    public static final String ASC = "ASC";
    public static final String DESC = "DESC";

    private Integer pageNumber = 1;

    private Integer pageSize = 20;

    private Integer index = 0;

    private String sort = "create_time";

    private String order = DESC;// asc/desc

    private Long total = 0L; //记录总数

    private List<T> rows = new ArrayList<>();

    public Page(){}

    public Page(Integer pageNum,Integer pageSize){
        this.pageNumber = pageNum >= 0? pageNum:0;
        this.pageSize = pageSize > 0? pageSize :0;
        this.index = (this.pageNumber -1) * this.pageSize;
    }

    public Integer getIndex() {
        return index;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNum) {
        this.pageNumber = pageNum;
        this.index = (this.pageNumber -1) * this.pageSize;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        this.index = (this.pageNumber -1) * this.pageSize;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> result) {
        this.rows = result;
    }
}
