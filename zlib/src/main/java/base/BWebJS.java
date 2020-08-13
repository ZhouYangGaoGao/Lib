package base;

import android.app.Activity;
import android.location.Location;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;

import com.just.agentweb.AgentWeb;

import util.GPSUtils;
import util.LogUtils;

public class BWebJS implements ValueCallback<String>, GPSUtils.OnLocationListener {

    protected Activity activity;
    protected AgentWeb agentWeb;
    protected String returnInfo = "";

    public BWebJS setActivity(Activity activity) {
        this.activity = activity;
        return this;
    }

    public BWebJS setAgentWeb(AgentWeb agentWeb) {
        this.agentWeb = agentWeb;
        return this;
    }

    @JavascriptInterface
    public String getInfo(int code) {
        return getInfo(code, "");
    }

    @JavascriptInterface
    public String getInfo(int code, String info) {
        LogUtils.e("code = " + code, "info = " + info + "\nreturn = " + returnInfo);
        return returnInfo;
    }

    @Override
    public void onReceiveValue(String value) {
        LogUtils.e("onReceive = " + value);
    }

    public void callJs(String jsStr) {
        LogUtils.e("callJs", jsStr);
        if (agentWeb != null)
            agentWeb.getJsAccessEntrace().callJs(jsStr, this);
    }

    @Override
    public void location(Location location) {

    }
}
