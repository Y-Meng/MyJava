package com.mingtai.base.service;

import com.github.pagehelper.PageHelper;
import com.mingtai.base.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

/**
 * Created by zkzc-mcy on 2017/10/31.
 * D : DAO
 * PK : 主键类型
 * T ：实体
 * E ： EXAMPLE类
 */
public abstract class BaseServiceImpl<D,T,PK,E> implements IBaseService<T,PK,E>{

    @Autowired
    protected D dao;

    protected D getDao(){
        return dao;
    }

    @Override
    public T insert(T record) {
        try {
            Method insert = getDao().getClass().getDeclaredMethod("insert", record.getClass());
            insert.invoke(getDao(), record);
            return record;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public T insertSelective(T record) {
        try {
            Method insertSelective =
                    getDao().getClass().getDeclaredMethod("insertSelective", record.getClass());
            insertSelective.invoke(getDao(), record);
            return record;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public T update(T record) {

        try {
            Method updateByPrimaryKey =
                    getDao().getClass().getDeclaredMethod("updateByPrimaryKey",record.getClass());
            updateByPrimaryKey.invoke(getDao(), record);
            return record;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public T updateSelective(T record) {
        try {
            Method updateByPrimaryKeySelective =
                    getDao().getClass().getDeclaredMethod("updateByPrimaryKeySelective",record.getClass());
            updateByPrimaryKeySelective.invoke(getDao(), record);
            return record;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int updateSelectiveByExample(T record, E example) {

        try {
            Method updateByExampleSelective =
                    getDao().getClass().getDeclaredMethod("updateByExampleSelective",record.getClass(), example.getClass());
            Object result = updateByExampleSelective.invoke(getDao(), record, example);
            return (int)result;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int deleteById(PK id) {
        try {
            Method deleteByPrimaryKey =
                    getDao().getClass().getDeclaredMethod("deleteByPrimaryKey",id.getClass());
            Object result = deleteByPrimaryKey.invoke(getDao(), id);
            return (int)result;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int deleteByIds(List<PK> ids) {
        int count = 0;
        for(PK id : ids){
            count += deleteById(id);
        }
        return count;
    }

    @Override
    public T getById(PK id) {
        try {
            Method selectByPrimaryKey =
                    getDao().getClass().getDeclaredMethod("selectByPrimaryKey",id.getClass());
            T result = (T)selectByPrimaryKey.invoke(getDao(), id);
            return result;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<T> listByFilter(T filter, Date startTime, Date endTime) {
        E example = createExample(filter, startTime, endTime);
        return listByExample(example);
    }

    @Override
    public List<T> listByExample(E example) {
        try {
            Method selectByExample =
                    getDao().getClass().getDeclaredMethod("selectByExample", example.getClass());
            List<T> result = (List<T>) selectByExample.invoke(getDao(), example);
            return result;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Long countByExample(E example) {
        try {
            Method countByExample =
                    getDao().getClass().getDeclaredMethod("countByExample", example.getClass());
            Object result = countByExample.invoke(getDao(), example);
            return (Long) result;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Page<T> pageByFilter(Page page, T filter) {
        E example = createExample(filter, page.getStartTime(), page.getEndTime());
        return pageByExample(page, example);
    }

    @Override
    public Page<T> pageByExample(Page page, E example) {
        Long count = countByExample(example);
        if(count != null && count > 0){
            page.setTotal(count);
            PageHelper.startPage(page.getPageNumber(),page.getPageSize());
            List<T> rows = listByExample(example);
            page.setRows(rows);
        }
        return page;
    }
}
