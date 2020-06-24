package base;

import android.webkit.JavascriptInterface;

public class BWebInterfase {
    @JavascriptInterface
    public String getInfo(int code) {
        return getInfo(code, "");
    }


    @JavascriptInterface
    public String getInfo(int code, String info) {return "";}
}
