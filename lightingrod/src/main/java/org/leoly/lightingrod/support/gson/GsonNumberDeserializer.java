/**
 * 
 */
package org.leoly.lightingrod.support.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description：
 *
 * @author Guguihe
 *
 * @date 2014年12月16日 上午11:20:45
 */
public class GsonNumberDeserializer implements JsonDeserializer<Number> {

    private final String integerPattern = "^[+-]?[0-9]+$";
    private final String doublePattern = "^[-+]?(\\d+(\\.\\d*)?|\\.\\d+)[dD]?$";

    @Override
    public Number deserialize(JsonElement jele, Type type, JsonDeserializationContext arg2) throws JsonParseException {
        String number = "int";
        String value = "0";
        if (jele.isJsonPrimitive()) {
            value = jele.getAsString();
            value = value.trim();
            Pattern p = null;
            Matcher m = null;
            switch (type.getTypeName()) {
                case "int":
                case "java.lang.Integer":
                    value = StringUtils.defaultIfEmpty(value, "0");
                    p = Pattern.compile(integerPattern);
                    m = p.matcher(value);
                    if (m.matches()) {
                        number = "int";
                    }
                    break;
                case "long":
                case "java.lang.Long":
                    value = StringUtils.defaultIfEmpty(value, "0");
                    p = Pattern.compile(integerPattern);
                    m = p.matcher(value);
                    if (m.matches()) {
                        number = "long";
                    }
                    break;
                case "java.lang.Float":
                case "float":
                    value = StringUtils.defaultIfEmpty(value, "0.0");
                    p = Pattern.compile(doublePattern);
                    m = p.matcher(value);
                    if (m.matches()) {
                        number = "float";
                    }
                    break;
                case "double":
                case "java.lang.Double":
                    value = StringUtils.defaultIfEmpty(value, "0.0");
                    p = Pattern.compile(doublePattern);
                    m = p.matcher(value);
                    if (m.matches()) {
                        number = "double";
                    }
                    break;
                default:
                    break;
            }

        }

        Number result = null;
        switch (number) {
            case "int":
                result = Integer.valueOf(value);
                break;
            case "long":
                result = Long.valueOf(value);
                break;
            case "float":
                result = Float.valueOf(value);
                break;
            case "double":
                result = Double.valueOf(value);
                break;
            default:
                result = new Number() {

                    /**
                     * 
                     */
                    private static final long serialVersionUID = 1L;

                    @Override
                    public long longValue() {
                        return 0;
                    }

                    @Override
                    public int intValue() {
                        return 0;
                    }

                    @Override
                    public float floatValue() {
                        return 0;
                    }

                    @Override
                    public double doubleValue() {
                        return 0;
                    }
                };
        }

        return result;
    }
}
