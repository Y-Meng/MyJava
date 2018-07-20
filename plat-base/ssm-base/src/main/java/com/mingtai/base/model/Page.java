package com.mingtai.base.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zkzc-mcy on 2017/9/12.
 * 分页返回结果封装类
 */
public class Page<T> implements Serializable{

    private Integer pageNumber = 1;

    private Integer pageSize = 20;

    private Integer index = 0;

    private String orderBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    private Long total = 0L; //记录总数

    private List<T> rows = new ArrayList<>();

    public Page(){}

    public Page(Integer pageNum,Integer pageSize){
        this.pageNumber = pageNum >= 0? pageNum:0;
        this.pageSize = pageSize > 0? pageSize :0;
        this.index = (this.pageNumber -1) * this.pageSize;
    }

    public Integer getIndex() {

        this.index = (this.pageNumber -1) * this.pageSize;
        return index;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNum) {
        this.pageNumber = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
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

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
