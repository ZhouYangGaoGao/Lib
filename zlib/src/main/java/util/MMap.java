package util;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MMap extends HashMap<String, Object> {

    public MMap(String k, Object v) {
        put(k,v);
    }

    public MMap add(String k, Object v) {
        put(k,v);
        return this;
    }

    public RequestBody body(String k, Object v) {
        String json = new Gson().toJson(TextUtils.isEmpty(k) ? v : add(k, v));
        LogUtils.e("RequestBody",json);
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
    }

    public RequestBody bodyForm(String k, Object v) {
        String json = new Gson().toJson(TextUtils.isEmpty(k) ? v : add(k, v));
        LogUtils.e("RequestBody",json);
        return RequestBody.create(MediaType.parse("multipart/form-data"), json);
    }
}
