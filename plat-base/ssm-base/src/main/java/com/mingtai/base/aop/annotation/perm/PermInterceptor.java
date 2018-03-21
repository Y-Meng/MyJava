package com.mingtai.base.aop.annotation.perm;

import com.mingtai.base.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zkzc-mcy create at 2018/1/26.
 */
public abstract class PermInterceptor extends HandlerInterceptorAdapter{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // !!! 需配置 mvc:annotation-driven
        if (handler instanceof HandlerMethod) {

            HandlerMethod method = (HandlerMethod) handler;
            Perm permission = method.getMethodAnnotation(Perm.class);
            if (permission != null) {

                String perm = permission.perm();
                if(!StringUtils.isBlank(perm)){

                    return checkPermission(perm, request, response);
                }
            }
        }

        return true;
    }

    /**
     * 检查权限
     * @param perm
     * @param request
     * @param response
     * @return
     */
    protected abstract boolean checkPermission(String perm, HttpServletRequest request, HttpServletResponse response);
}
