package com.mingtai.base.service;

import com.mingtai.base.model.Page;

import java.util.Date;
import java.util.List;

/**
 * Created by zkzc-mcy on 2017/9/22.
 */
public interface IBaseService<T,PK,E> {


    T insert(T record);

    T insertSelective(T record);

    T update(T record);

    T updateSelective(T record);

    int updateSelectiveByExample(T record, E example);

    int deleteById(PK id);

    int deleteByIds(List<PK> ids);

    T getById(PK id);

    List<T> listByFilter(T filter, Date startTime, Date endTime);

    List<T> listByExample(E example);

    Long countByExample(E example);

    Page<T> pageByFilter(Page page, T filter);

    Page<T> pageByExample(Page page, E example);

    E createExample(T filter, Date startTime, Date endTime);
}
