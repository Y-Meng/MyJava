package com.mingtai.base.spring;


import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.math.BigInteger;

/**
 * @author zkzc-mcy create at 2018/8/23.
 *
 * json 数据输出 long型转string转换器
 */
public class LongToStringConverter extends ObjectMapper {

    private static final long serialVersionUID = 1683531771040674386L;

    public LongToStringConverter(){
        registerModule(null);
    }

    @Override
    public ObjectMapper registerModule(Module module) {

        SimpleModule simpleModule = new SimpleModule();
        // 处理封装类型 Long 和基本类型 long
        simpleModule.addSerializer(BigInteger.class, ToStringSerializer.instance)
                .addSerializer(Long.class, ToStringSerializer.instance)
                .addSerializer(Long.TYPE, ToStringSerializer.instance);

        return super.registerModule(simpleModule);
    }
}
