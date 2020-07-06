package util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class Reflector {

    public static ParameterizedType pt(Object o) {
        Type type = o.getClass().getGenericSuperclass();
        if (type != null && ParameterizedType.class.isAssignableFrom(type.getClass())) {
            return (ParameterizedType) type;
        }
        return null;
    }


    public static Class<?> cls(Object o, int index) {
        ParameterizedType pt = pt(o);
        if (pt == null) return null;
        Type[] types = pt.getActualTypeArguments();
        if (types.length <= index) return null;
        return (Class<?>) types[index];
    }

    public static Class<?> cls(Object o) {
        return cls(o, 0);
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
