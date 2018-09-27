/**
 * copywrite 2015-2020 �����ҵ
 * �����޸ĺ�ɾ������İ�Ȩ����
 * �˴���������������Ϣ���Ĳ��ű�д����δ�����������²��ô�������
 * ReflactUtils.java
 * @Date 2016��6��21�� ����4:39:26
 * guguihe
 */
package org.leoly.lightingrod.support;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO: 反射工具类
 * 
 * @author guguihe
 * @Date 2016年6月24日 下午4:52:57
 */
public class ReflactUtils {

    public static final String GETTER = "get";
    public static final String SETTER = "set";

    /**
     * TODO: 过虑器
     * 
     * @author guguihe
     * @Date 2016年6月24日 下午4:53:22
     */
    public static interface ReflactFilter {
        boolean accept(String name);
    };

    /**
     * TODO: 获取类定义的方法，一直检查到Object类为止ֹ
     * 
     * @param clazz
     * @return
     */
    private static void getDeclaredMethods(Class clazz, List<Method> methods, ReflactFilter filter) {
        Method[] temp = clazz.getDeclaredMethods();
        if(temp!=null){
        for (Method m : temp) {
            if (((null == filter) || (null != filter && filter.accept(m.getName())))
                    && !Modifier.isStatic(m.getModifiers())) {
                methods.add(m);
            }
        }
        }

        if (!clazz.getSuperclass().equals(Object.class)) {
            getDeclaredMethods(clazz.getSuperclass(), methods, filter);
        }
    }

    /**
     * TODO: 获取类定义的方法，一直检查到Object类为止ֹ
     * 
     * @param clazz
     * @return
     */
    public static List<Method> getDeclaredMethods(Class clazz) {
        List<Method> result = new ArrayList<>();
        getDeclaredMethods(clazz, result, null);
        return result;
    }

    /**
     * TODO: 获取类定义的方法，一直检查到Object类为止ֹ
     * 
     * @param clazz
     * @return
     */
    public static List<Method> getDeclaredMethods(Class clazz, ReflactFilter filter) {
        List<Method> result = new ArrayList<>();
        getDeclaredMethods(clazz, result, filter);
        return result;
    }

    /**
     * TODO:获取类定义的属性，一直检查到Object类为止
     * 
     * @param clazz
     * @param methods
     */
    private static void getDeclaredFields(Class clazz, List<Field> methods, ReflactFilter filter) {
        Field[] temp = clazz.getDeclaredFields();
        for (Field m : temp) {
            if (((null == filter) || (null != filter && filter.accept(m.getName())))
                    && !Modifier.isStatic(m.getModifiers())) {
                methods.add(m);
            }
        }

        if (!clazz.getSuperclass().equals(Object.class)) {
            getDeclaredFields(clazz.getSuperclass(), methods, filter);
        }
    }

    /**
     * TODO: 获取类定义的属性，一直检查到Object类为止ֹ
     * 
     * @param clazz
     * @return
     */
    public static List<Field> getDeclaredFields(Class clazz) {
        List<Field> result = new ArrayList<>();
        getDeclaredFields(clazz, result, null);
        return result;
    }

    /**
     * TODO: 获取类定义的属性，一直检查到Object类为止ֹ
     * 
     * @param clazz
     * @return
     */
    public static List<Field> getDeclaredFields(Class clazz, ReflactFilter filter) {
        List<Field> result = new ArrayList<>();
        getDeclaredFields(clazz, result, filter);
        return result;
    }

    /**
     * TODO: 从getter或者setter方法名获取属性名
     * 
     * @param fieldName
     * @return
     */
    public static String getMethodFromField(String fieldName, String getterOrSetter) {
        String prefix = fieldName.substring(0, 1);
        String subfix = fieldName.substring(1);
        return getterOrSetter + prefix.toUpperCase() + subfix;
    }

    /**
     * TODO: 从getter或者setter方法名获取属性名
     * 
     * @param methodName
     * @return
     */
    public static String getFieldFromMethod(String methodName) {
        String fieldName = methodName.substring(3);
        String prefix = fieldName.substring(0, 1);
        String subfix = fieldName.substring(1);
        return prefix.toLowerCase() + subfix;
    }

    /**
     * TODO: 获取指定字符串开始的方法
     * 
     * @param c
     * @param getterOrSetter
     * @return
     * @throws Exception
     */
    public static Map<String, Method> getSpecialMethods(Class c, String getterOrSetter) throws Exception {
        List<Method> methods = getDeclaredMethods(c);
        Map<String, Method> methodMap = new HashMap<>();
        for (Method m : methods) {
            if (m.getName().startsWith(getterOrSetter)) {
                methodMap.put(m.getName(), m);
            }
        }

        return methodMap;
    }

    public static void main(String[] args) throws Exception {
    }
}
