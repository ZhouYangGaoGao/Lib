package util;

import com.google.gson.Gson;

public class JsonUtil {
    private Gson gson;
    private static JsonUtil util;

    private JsonUtil() {
        gson = new Gson();
    }

    private static JsonUtil get() {
        if (util == null) {
            util = new JsonUtil();
        }
        return util;
    }

    public static String getJson(Object obj) {
        return get().gson.toJson(obj);
    }
}
