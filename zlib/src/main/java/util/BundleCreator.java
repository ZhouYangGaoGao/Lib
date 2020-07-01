package util;

import android.os.Bundle;

import java.util.ArrayList;

public class BundleCreator {

    public static Bundle create(Object... keyValues) {
        if (keyValues.length % 2 == 1) {
            LogUtils.e("BundleBuilder", "数量长度需为2的倍数");
            return null;
        }
        String key;
        Object value;
        Bundle bundle = new Bundle();
        for (int i = 0; i < keyValues.length; i++) {
            key = keyValues[i] + "";
            value = keyValues[++i];
            if (value != null)
                switch (value.getClass().getSimpleName()) {
                    case "Integer":
                        bundle.putInt(key, (int) value);
                        break;
                    case "String":
                        bundle.putString(key, (String) value);
                        break;
                    case "Double":
                        bundle.putDouble(key, (double) value);
                        break;
                    case "Bundle":
                        bundle.putAll((Bundle) value);
                        break;
                    case "Float":
                        bundle.putFloat(key, (float) value);
                        break;
                    case "String[]":
                        bundle.putStringArray(key, (String[]) value);
                        break;
                    case "ArrayList":
                        bundle.putStringArrayList(key, (ArrayList<String>) value);
                        break;
                }
        }
        return bundle;
    }
}
