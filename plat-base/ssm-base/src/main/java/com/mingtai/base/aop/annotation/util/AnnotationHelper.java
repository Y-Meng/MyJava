package com.mingtai.base.aop.annotation.util;

import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zkzc-mcy create at 2018/1/9.
 */
public class AnnotationHelper {


    private static ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();


    /**
     * 验证是否有某个注解
     * @param annos
     * @param clz
     * @return
     */
    public static boolean validateParameterAnnotation(Annotation[][] annos, Class clz){
        boolean flag = false;
        for(Annotation[] at : annos){
            for(Annotation a : at){
                if(a.annotationType() == clz){
                    flag = true;
                }
            }
        }
        return flag;
    }

    /**
     * 获取参数的描述
     * @param method
     * @param objs
     * @return
     */
    public static List<Param> getParams(Method method, Object[] objs, Class clz){

        // 此处获得参数对象中参数名称为编译器生成
        Parameter[] parameters = method.getParameters();

        // 使用spring方法获得参数名称列表
        String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);

        List<Param> params = new ArrayList<>();

        for(int i = 0, len = parameters.length ; i < len; i++){

            Annotation[] annotations = parameters[i].getAnnotations();

            for(int j = 0; j < annotations.length; j++){

                if(annotations[j].annotationType() == clz){

                    Param param = new Param(
                            parameterNames[i],
                            parameters[i].getType(),
                            objs[i],
                            annotations[j]);
                    params.add(param);
                }
            }
        }

        return params;
    }

    public static class Param {
        /** 参数名 */
        private String name;
        /** 类型 */
        private Class<?> type;
        /** 值 */
        private Object value;
        /** 注解 */
        private Annotation annotation;

        public Param(String name, Class<?> type, Object value, Annotation anno) {
            super();
            this.name = name;
            this.type = type;
            this.value = value;
            this.annotation = anno;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Class<?> getType() {
            return type;
        }

        public void setType(Class<?> type) {
            this.type = type;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public Annotation getAnnotation() {
            return annotation;
        }

        public void setAnnotation(Annotation annotation) {
            this.annotation = annotation;
        }

        @Override
        public String toString() {
            return "Param [name=" + name + ", type=" + type + ", value=" + value + ", annotation=" + annotation + "]";
        }
    }
}
