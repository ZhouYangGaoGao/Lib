package util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

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

    public static <T> T getObject(String jsonStr,Class<T> tClass){
       return get().gson.fromJson(jsonStr,tClass);
    }

    public static <T> T getListObject(String jsonStr){
       return get().gson.fromJson(jsonStr,new TypeToken<List<T>>() {
       }.getType());
    }
}
