package com.mingtai.base.aop.annotation.valid;

import com.mingtai.base.aop.exception.ValidException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;

/**
 * @author zkzc-mcy create at 2018/1/9.
 */
public class Validator {

    private Valid valid;

    public Validator(Valid valid){
        this.valid = valid;
    }

    public void valid(Object value, String name) throws ValidException{
        switch (valid.rule()){
            case NotNull:
                validNotNull(value, name);
                break;

            case Value:
                validValue(value, name);
                break;

            case Length:
                validLength(value, name);
                break;

            case Field:
                validField(value, name);
                break;
            default:
                    break;
        }
    }

    private void validField(Object target, String name) throws ValidException{

        validNotNull(target, name);

        Field[] fields = target.getClass().getFields();

        for(Field field : fields){
            Annotation[] annotations = field.getAnnotations();
            for(int i = 0, len = annotations.length; i < len; i++){
                if(annotations[i].getClass() == Valid.class){
                    try {
                        new Validator((Valid)annotations[i]).valid(field.get(target), field.getName());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void validLength(Object value, String name) throws ValidException{

        validNotNull(value, name);

        int length = -1;
        if(value instanceof String){

            length = ((String) value).length();
        }else if(value instanceof Collection){

            length = ((Collection) value).size();
        }

        if(length != -1){
            if(length < valid.min()){

                if("".equals(valid.message())){
                    throw new ValidException("参数" + name + "长度不得小于" + valid.min());
                }else{
                    throw new ValidException(valid.message());
                }
            }

            if(valid.max() != -1 && length > valid.max()){
                if("".equals(valid.message())){
                    throw new ValidException("参数" + name + "长度不得大于" + valid.max());
                }else {
                    throw new ValidException(valid.message());
                }
            }
        }
    }

    private void validValue(Object value, String name) throws ValidException{

        validNotNull(value, name);

        if(value instanceof Number){
            if(((Number) value).intValue() < valid.min()){
                if("".equals(valid.message())){
                    throw new ValidException("参数" + name + "值不得小于" + valid.min());
                }else {
                    throw new ValidException(valid.message());
                }
            }

            if(valid.max() != -1 && ((Number) value).intValue() > valid.max()){
                if("".equals(valid.message())) {
                    throw new ValidException("参数" + name + "值不得大于" + valid.max());
                }else {
                    throw new ValidException(valid.message());
                }
            }
        }
    }

    private void validNotNull(Object value, String name) throws ValidException{

        if(value == null){
            if("".equals(valid.message())) {
                throw new ValidException("参数" + name + "不能为空");
            }else {
                throw new ValidException(valid.message());
            }
        }
    }

}
