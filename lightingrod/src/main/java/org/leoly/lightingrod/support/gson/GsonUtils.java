/**
 * copywrite 2015-2020 智慧享联
 * 不能修改和删除上面的版权声明
 * 此代码属于数据与信息中心部门编写，在未经允许的情况下不得传播复制
 * GsonUtils.java
 * @Date 2017年7月7日 上午11:19:44
 * guguihe
 */
package org.leoly.lightingrod.support.gson;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;

/**
 * TODO:JSON数据转换
 * 
 * @author guguihe
 * @Date 2017年7月7日 上午11:19:44
 */
public class GsonUtils {

    /**
     * TODO: 获取GSON对象
     * 
     * @return
     */
    public static Gson getGson(boolean pretty) {
        GsonBuilder gsonBuilder = new GsonBuilder().enableComplexMapKeySerialization()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return null != f.getAnnotation(GsonIgnore.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                }).setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls()// .setPrettyPrinting()
                .registerTypeAdapter(java.util.Date.class, new GsonDateDeserializer())
                .registerTypeAdapter(Integer.class, new GsonNumberDeserializer())
                .registerTypeAdapter(Long.class, new GsonNumberDeserializer())
                .registerTypeAdapter(Float.class, new GsonNumberDeserializer())
                .registerTypeAdapter(Double.class, new GsonNumberDeserializer())
                .registerTypeAdapter(long.class, new GsonNumberDeserializer())
                .registerTypeAdapter(double.class, new GsonNumberDeserializer())
                .registerTypeAdapter(float.class, new GsonNumberDeserializer())
                .registerTypeAdapter(int.class, new GsonNumberDeserializer());
        if (pretty) {
            gsonBuilder.setPrettyPrinting();
        }

        return gsonBuilder.create();
    }

    /**
     * TODO: JSON字符串转换成对象
     * 
     * @param jsonStr
     *            JSON字符串
     * @param cls
     *            对象class
     * @return
     */
    public static <T> T toObject(String jsonStr, Class<T> cls) {
        if (cls.getName().equals("java.lang.String")) {
            return (T) jsonStr;
        }

        Gson gson = getGson(false);
        return gson.fromJson(jsonStr, cls);
    }

    /**
     * TODO: JSON字符串转换成对象
     * 
     * @param jsonStr
     *            JSON字符串
     * @param cls
     *            对象class
     * @return
     */
    public static <T> T toObject(String jsonStr, Type cls) {
        Gson gson = getGson(false);
        return gson.fromJson(jsonStr, cls);
    }

    /**
     * TODO: 将JAVA对象转换成字符F串
     * 
     * @param obj
     *            JAVA对象
     * @return
     */
    public static String toString(Object obj) {
        if (null == obj) {
            return StringUtils.EMPTY;
        }

        if (obj instanceof String) {
            return java.util.Objects.toString(obj);
        }

        Gson gson = getGson(false);
        return gson.toJson(obj);
    }

    /**
     * TODO: 将JAVA对象转换成字符F串
     * 
     * @param obj
     *            JAVA对象
     * @return
     */
    public static String toString(Object obj, boolean isPretty) {
        if (null == obj) {
            return StringUtils.EMPTY;
        }

        if (obj instanceof String) {
            return java.util.Objects.toString(obj);
        }

        Gson gson = getGson(isPretty);
        return gson.toJson(obj);
    }
}
