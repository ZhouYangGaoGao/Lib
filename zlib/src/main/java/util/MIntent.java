package util;

public class MIntent extends android.content.Intent {
    public MIntent(String key, Object value) {
        Class<?> aClass = value.getClass();
        if (String.class.equals(aClass)) {
            putExtra(key, (String) value);
        } else if (Integer.class.equals(aClass)) {
            putExtra(key, (Integer) value);
        } else if (Boolean.class.equals(aClass)) {
            putExtra(key, (Boolean) value);
        }
    }
}
