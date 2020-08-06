package enums;

import hawk.Hawk;

public enum LevelCache {
    only, replace, refresh,none;

    public void cache(String key, Object object) {
        Hawk.put(key, object);

    }

    public <T> T get(String key) {
        return Hawk.get(key);
    }
}
