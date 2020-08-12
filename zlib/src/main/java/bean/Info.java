package bean;

import enums.CacheType;
import hawk.Hawk;
import util.JsonUtil;

public class Info {
    public String TAG;
    public String title = "";
    public String type = "";//预留参数 类型
    public int index = 0;//预留参数 下标
    public boolean useEventBus = false;
    public boolean showTop = true;//显示顶部
    public boolean isRefresh = false;//是否是刷新
    private CacheType cacheType = CacheType.none;

    public void setCacheType(CacheType cacheType) {
        this.cacheType = cacheType;
    }

    public void setLevelCache(long duration) {
        this.cacheType = CacheType.time;
        cacheType.cacheDuration(TAG, duration);
    }

    public boolean save(Object object) {
        if (object == null) return false;
        if (cacheType == null) return false;
        if (cacheType == CacheType.none) return false;
        if (cacheType == CacheType.time && Hawk.contains(TAG)) {
            if (Hawk.get(TAG) != null && JsonUtil.getJson(Hawk.get(TAG)).equals(JsonUtil.getJson(object)))
                return false;
        }
        cacheType.cache(TAG, object);
        return true;
    }

    public <T> boolean needNew(DataListener<T> listener) {
        T data = cacheType.get(TAG);
        if (data != null)
            switch (cacheType) {
                case time:
                case only:
                    if (listener != null) listener.onData(data);
                    return false;
                case replace:
                    if (listener != null) listener.onData(data);
                    break;
                case refresh:
                    if (isRefresh) return true;
                    else {
                        if (listener != null) listener.onData(data);
                        return false;
                    }
            }
        return true;
    }

    public interface DataListener<T> {
        void onData(T t);
    }

}
