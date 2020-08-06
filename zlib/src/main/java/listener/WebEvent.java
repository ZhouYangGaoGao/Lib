package listener;

public class WebEvent {
    public static final int LOAD_JS = -1;
    public static final int REFRESH = 0;
    public static final int LOAD_URL = 1;
    public static final int FORWARD = 2;
    public static final int BACK = 3;
    public static final int HIDE_TOP = 4;
    public static final int SHOW_TOP = 5;
    private int type;
    private String value;

    public WebEvent(int type) {
        this.type = type;
    }

    public WebEvent(int type, String value) {
        this.type = type;
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public WebEvent setValue(String value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return "WebEvent{" +
                "type=" + type +
                ", value='" + value + '\'' +
                '}';
    }
}
