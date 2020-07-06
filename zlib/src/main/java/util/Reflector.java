package util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class Reflector {
    public static Class<?> cls(Object o) {
        Type type = o.getClass().getGenericSuperclass();
        if (type != null && type.getClass().isAssignableFrom(ParameterizedType.class)) {
            ParameterizedType pt = (ParameterizedType) type;
            return (Class<?>) pt.getActualTypeArguments()[0];
        }
        return null;
    }

    public static String name(Object o) {
        Class<?> cls = cls(o);
        return cls == null ? null : cls.getSimpleName();
    }

    public static <T> T get(Object o) {
        Class<?> cls = cls(o);
        try {
            return cls == null ? null : (T) cls.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
