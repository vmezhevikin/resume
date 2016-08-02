package net.devstudy.resume.util;

import org.springframework.beans.BeanUtils;

public class ObjectDataUtil {

	public static Object getObjectField(Object object, String field) {
		try {
			return BeanUtils.getPropertyDescriptor(object.getClass(), field).getReadMethod().invoke(object);
		} catch (Exception e) {
			throw new IllegalArgumentException("Can't get property " + field + " from " + object.getClass().getName(),
					e);
		}
	}

	public static void setObjectField(Object object, String field, Object value) {
		try {
			BeanUtils.getPropertyDescriptor(object.getClass(), field).getWriteMethod().invoke(object, value);
		} catch (Exception e) {
			throw new IllegalArgumentException( "Can't set value " + value + " to property " + field + " from " + object.getClass().getName(), e);
		}
	}
}