package com.mingtai.base.aop.aspect;

import com.mingtai.base.aop.annotation.valid.Valid;
import com.mingtai.base.aop.annotation.valid.Validator;
import com.mingtai.base.aop.exception.ValidException;
import com.mingtai.base.aop.annotation.util.AnnotationHelper;
import com.mingtai.base.model.ApiResult;
import com.mingtai.base.util.CollectionUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author zkzc-mcy create at 2018/1/8.
 */
@Component
@Aspect
public class ControllerAspect {

    static  final Logger logger = LoggerFactory.getLogger(ControllerAspect.class);

    /**
     * 定义切入点，对所有声明 @ResponseBody方法创建切点
     */
    @Pointcut("execution(com.mingtai.base.model.ApiResult *.*(..))||@annotation(org.springframework.web.bind.annotation.ResponseBody)")
    private void controllerMethod() {
    }

    /**
     * 前置通知
     * @Before("controllerMethod()")
     */
    public void before(JoinPoint call) {

        String className = call.getTarget().getClass().getName();
        String methodName = call.getSignature().getName();

        logger.debug("注解-前置通知:" + className + "类的" + methodName + "方法开始了");
    }

    /**
     * 后置通知（方法执行后）
     * @After("controllerMethod()")
     */
    public void after() {

        logger.debug("注解-执行后通知");
    }

    /**
     * 返回后置通知
     * @AfterReturning("controllerMethod()")
     */
    public void afterReturn() {

        logger.debug("注解-后置通知:方法正常结束了");
    }

    /**
     * 异常后置通知
     * @AfterThrowing("controllerMethod()")
     */
    public void afterThrowing() {

        logger.debug("注解-异常抛出后通知:方法执行时出异常了");
    }



    @Around("controllerMethod()")
    public Object doAround(ProceedingJoinPoint call) throws Throwable {

        Object result = null;

        MethodSignature signature = (MethodSignature) call.getSignature();
        Method method = signature.getMethod();

        try {

            //得到标注@Valid注解的参数
            List<AnnotationHelper.Param> params = AnnotationHelper.getParams(method, call.getArgs(), Valid.class);

            if (CollectionUtil.notEmpty(params)) {
                for (AnnotationHelper.Param param : params) {
                    validateParam(param);
                }
            }

            result = call.proceed();
        } catch (Exception e) {

            if(method.getReturnType() == ApiResult.class){

                ApiResult failResult = new ApiResult();
                failResult.setSuccess(false);
                if(e instanceof ValidException){
                    failResult.setCode(ApiResult.ERROR_VALID);
                }else {
                    failResult.setCode(ApiResult.ERROR_INNER);
                    e.printStackTrace();
                }
                failResult.setMessage(e.toString());

                return failResult;
            }else{
                throw e;
            }
        }

        return result;
    }

    /**
     * 参数检查
     * @param param 参数对象
     * @throws ValidException
     */
    private void validateParam(AnnotationHelper.Param param) throws ValidException{

        Annotation annotation = param.getAnnotation();

        if (annotation instanceof Valid) {
            new Validator(((Valid) annotation)).valid(param.getValue(), param.getName());
        }
    }
}
