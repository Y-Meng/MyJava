package com.meng.util;

import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 反射工具类 提供了对java Class文件获取原型的操作和对属性的赋值、取值操作
 */
public class ReflectUtils {

	/**
	 * 通过反射, 获得Class定义中声明的父类的泛型参数的类型. 如无法找到, 返回Object.class. public UserDao extends HibernateDao<User>
	 * 
	 * @param clazz The class to introspect
	 * @return the first generic declaration, or Object.class if cannot be determined
	 */
	@SuppressWarnings("unchecked")
	public static <T> Class<T> getSuperClassGenricType(@SuppressWarnings("rawtypes") final Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	/**
	 * 通过反射, 获得Class定义中声明的父类的泛型参数的类型. 如无法找到, 返回Object.class. 如public UserDao extends HibernateDao<User,Long>
	 * 
	 * @param clazz clazz The class to introspect
	 * @param index the Index of the generic ddeclaration,start from 0.
	 * @return the index generic declaration, or Object.class if cannot be determined
	 */
	@SuppressWarnings("rawtypes")
	public static Class getSuperClassGenricType(final Class clazz, final int index) {

		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			return Object.class;
		}

		return (Class) params[index];
	}

	/**
	 * 循环向上转型, 获取对象的DeclaredField.如向上转型到Object仍无法找到, 返回null.
	 * 
	 * @param object
	 * @param fieldName
	 * @return
	 */
	public static Field getDeclaredField(final Object object, final String fieldName) {
		for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
				// Field不在当前类定义,继续向上转型
			}
		}
		return null;
	}

	/**
	 * 获取所有属性
	 * @param object
	 * @return
     */
	protected static Field[] getDeclaredFields(final Object object) {
		for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			return superClass.getDeclaredFields();
		}
		return null;
	}
	/**
	 * 获取所有属性
	 * @param object
	 * @return
	 */
	protected static Method[] getDeclaredMethods(final Object object) {
		for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			return superClass.getDeclaredMethods();
		}
		return null;
	}
	/**
	 * 强行设置Field可访问.
	 * 
	 * @param field
	 */
	protected static void makeAccessible(final Field field) {
		if (!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
			field.setAccessible(true);
		}
	}

	/**
	 * 直接读取对象属性值, 无视private/protected修饰符, 不经过getter函数.
	 * 
	 * @param object
	 * @param fieldName
	 * @return
	 * @throws IllegalAccessException
	 */
	public static Object getFieldValue(final Object object, final String fieldName) throws IllegalAccessException {
		Field field = getDeclaredField(object, fieldName);
		if (field == null)
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
		makeAccessible(field);
		return field.get(object);
	}

	/**
	 * 直接设置对象属性值, 无视private/protected修饰符, 不经过setter函数.
	 * 
	 * @param object
	 * @param fieldName
	 * @param value
	 * @throws Exception 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	public static void appendFieldValue(final Object object, final String fieldName, final Object value) throws Exception {
		Field field = getDeclaredField(object, fieldName);
		if (field == null)
			return;
			//throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
		makeAccessible(field);
		
		// 附件上传时，用param 为S听， 在这里强转
		// 如果类型是Long
		if (field.getGenericType().toString().equals("class java.lang.Long")) {
			Long v = null;
			if (!StringUtils.isBlank(String.valueOf(value))){
				 v = Long.valueOf(String.valueOf(value));
			}
			field.set(object, v);
		} else if (field.getGenericType().toString().equals("class java.lang.Integer")) {
			Integer v = null;
			if (!StringUtils.isBlank(String.valueOf(value))){
				v = Integer.parseInt(String.valueOf(value));
			}
			field.set(object, v);
		} else if (field.getGenericType().toString().equals("class java.lang.Boolean")) {
			Boolean v = null;
			if (!StringUtils.isBlank(String.valueOf(value))){
				v = Boolean.valueOf(String.valueOf(value));
			}
			field.set(object, v);
		} else if (field.getGenericType().toString().equals("class java.math.BigDecimal")) {
			BigDecimal v = null;
			if (!StringUtils.isBlank(String.valueOf(value))){
				v = new BigDecimal(String.valueOf(value));
			}
			field.set(object, v);
		} else if (field.getGenericType().toString().equals("class java.util.Date")) {
			Date v = null;
			if (!StringUtils.isBlank(String.valueOf(value))){
				v = DateUtil.stringToDate(String.valueOf(value),DateUtil.DATETIME_PATTERN);
			}
			field.set(object, v);
		} else {
			field.set(object, value);
		}
	}
	/**
	 * 直接设置对象属性值, 无视private/protected修饰符, 不经过setter函数.
	 *
	 * @param object
	 * @param fieldName
	 * @param value
	 * @throws IllegalAccessException
	 */
	public static void setFieldValue(final Object object, final String fieldName, final Object value) throws IllegalAccessException {
		Field field = getDeclaredField(object, fieldName);
		if (field == null)
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
		makeAccessible(field);
		field.set(object, value);
	}
	/**
	 * 设置实体默认值，如createTime
	 *
	 * @param object
	 * @throws IllegalAccessException
	 */
	public static void setEntityFieldValue(final Object object) {
		try {
			//Field idf = getDeclaredField(object,"id");
			Date day=new Date();
			SimpleDateFormat df = new SimpleDateFormat(DateUtil.DATETIME_PATTERN);
			setStringFeildNull(object);
			 Object feildPkValue = getFeildPkValue(object,"persistence.Id");
			if(feildPkValue!=null){//update
				if( getDeclaredField(object,"updateTime")!=null){
						setFieldValue(object,"updateTime", Timestamp.valueOf(df.format(day)));
				}
			}else{//insert
				if( getDeclaredField(object,"createTime")!=null){
					if(getFieldValue(object,"createTime")==null){
						setFieldValue(object,"createTime", Timestamp.valueOf(df.format(day)));
					}
				}
				if( getDeclaredField(object,"updateTime")!=null){
						setFieldValue(object,"updateTime", Timestamp.valueOf(df.format(day)));
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据annotation名称获取object对应方法值
	 * @param object
	 * @param annotation
     * @return
     */
	public static Object getFeildPkValue (final Object object,final String annotation){
		Method[] methods = getDeclaredMethods(object);
		for(Method m : methods){
			Annotation[] allAnnotations = m.getAnnotations();
			for(Annotation an : allAnnotations){
				Class a = an.annotationType();
				if(a.getName().indexOf(annotation)!=-1){
					try {
						return m.invoke(object);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}

	/**
	 *
	 * @param object
	 * @return
     */
	public  static Object setStringFeildNull(Object object) throws IllegalAccessException {
		Field [] fs = getDeclaredFields(object);
		for(Field f :fs){
			f.setAccessible(true);
			if (f.getType().equals(String.class)&& StringUtils.isBlank((String) f.get(object))) {
				f.set(object,null);
			}
		}
		return object;
	}
}
