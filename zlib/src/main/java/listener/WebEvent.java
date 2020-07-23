package listener;

import org.greenrobot.eventbus.EventBus;

public class WebEvent {
    public static final int REFRESH = 0;
    public static final int LOAD_URL = 1;
    public static final int FORWARD = 2;
    public static final int BACK = 3;
    private int type;
    private String value;

    public WebEvent(int type) {
        this.type = type;
        EventBus.getDefault().post(this);
    }

    public int getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
