package bean;

import enums.LevelCache;
import hawk.Hawk;
import util.JsonUtil;

public class Info {
    public String TAG;
    public String title = "";
    public String type = "";//预留参数 类型
    public int index = 0;//预留参数 下标
    public boolean useEventBus = false;
    public boolean showTop = true;//显示顶部
    public LevelCache levelCache = LevelCache.none;
    public boolean isRefresh = false;//是否是刷新

    public boolean save(Object object) {
        if (object == null) return false;
        if (levelCache == null) return false;
        if (levelCache == LevelCache.none) return false;
        if (levelCache == LevelCache.time && Hawk.contains(TAG)) {
            if (Hawk.get(TAG) != null && JsonUtil.getJson(Hawk.get(TAG)).equals(JsonUtil.getJson(object)))
                return false;
        }
        levelCache.cache(TAG, object);
        return true;
    }

    public <T> boolean needNew(DataListener<T> listener) {
        T data = levelCache.get(TAG);
        if (data != null)
            switch (levelCache) {
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
