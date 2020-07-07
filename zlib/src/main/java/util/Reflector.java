package util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取反射 注解 工具类
 */
public class Reflector {

    public static ParameterizedType pt(Object o) {
        Type type = o.getClass().getGenericSuperclass();
        if (type != null && ParameterizedType.class.isAssignableFrom(type.getClass())) {
            return (ParameterizedType) type;
        }
        return null;
    }

    public static Class<?> cls(Object o, int... index) {
        ParameterizedType pt = pt(o);
        if (pt == null) return null;
        Type[] types = pt.getActualTypeArguments();
        if (index.length == 0) return (Class<?>) types[0];
        else if (types.length <= index[0]) return null;
        return (Class<?>) types[index[0]];
    }

    public static String name(Object o, int... index) {
        Class<?> cls = cls(o, index);
        return cls == null ? null : cls.getSimpleName();
    }

    public static <T> T get(Object o, int... index) {
        Class<?> cls = cls(o, index);
        try {
            return cls == null ? null : (T) cls.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> get(Object o, Class annotationCls) {
        List<T> instances = null;
        for (Field field : o.getClass().getFields()) {
            if (field.getAnnotation(annotationCls) != null) {
                try {
                    Object instance = field.getType().newInstance();
                    field.setAccessible(true);
                    field.set(o, instance);
                    if (instances == null) instances = new ArrayList<>();
                    instances.add((T) instance);
                } catch (IllegalAccessException | InstantiationException e) {
                    e.printStackTrace();
                }
            }
        }
        return instances;
    }
}
