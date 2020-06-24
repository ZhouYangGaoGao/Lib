package util;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class BodyMap extends HashMap<String, Object> {

    public BodyMap(String k, Object v) {
        put(k,v);
    }

    public BodyMap add(String k, Object v) {
        put(k,v);
        return this;
    }

    public RequestBody body(String k, Object v) {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(TextUtils.isEmpty(k) ? v : add(k,v)));
    }
}
