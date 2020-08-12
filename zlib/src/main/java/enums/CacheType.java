package enums;

import hawk.Hawk;
import util.LogUtils;

public enum CacheType {
    only, replace, refresh, time/*秒数*/, none;


    public CacheType cache(String key, Object object) {
        Hawk.put(key, object);
        if (this == time && Hawk.get(durationKey(key), 0) > 0) {
            Hawk.put(timeKey(key), System.currentTimeMillis());
        }
        return this;
    }

    public CacheType cacheDuration(String key, long seconds) {
        Hawk.put(durationKey(key), seconds * 1000);
        return this;
    }

    public <T> T get(String key) {
        if (this == time && Hawk.contains(durationKey(key)) && Hawk.contains(timeKey(key))) {
            if ((Hawk.get(timeKey(key), 0) + Hawk.get(durationKey(key), 0)) < System.currentTimeMillis()) {
                Hawk.delete(timeKey(key));
                LogUtils.e(timeKey(key),"缓存时间过,重新获取");
                return null;
            }
        }
        return Hawk.get(key);
    }

    private String timeKey(String key) {
        return key + "TIME";
    }

    private String durationKey(String key) {
        return key + "DURATION";
    }


}
