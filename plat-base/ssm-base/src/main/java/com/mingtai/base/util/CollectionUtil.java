package com.mingtai.base.util;

import java.util.Collection;

/**
 * @author zkzc-mcy create at 2018/1/9.
 */
public class CollectionUtil{

    /**
     * 检查是否为空
     * @param collection
     * @return
     */
    public static boolean notEmpty(Collection collection){
        return collection!=null && collection.size() > 0;
    }
}
