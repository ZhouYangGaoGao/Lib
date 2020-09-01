package base;

import android.annotation.SuppressLint;
import android.net.http.SslError;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;

import com.google.android.material.appbar.AppBarLayout;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebViewClient;
import com.zhy.android.BuildConfig;
import com.zhy.android.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import custom.SmartView;
import bean.Smart;
import listener.WebEvent;

import static android.webkit.WebSettings.*;

/**
 * 通用网页
 */
public class BWebFragment extends BFragment {

    protected SmartView mSmartView;
    private TextView titleView;
    public WebView mWebView;
    public AgentWeb mAgentWeb;
    private Smart listener;

    @Override
    public void beforeView() {
        info.useEventBus = true;
        contentViewId = R.layout.fragment_web;
    }

    @SuppressLint({"SetJavaScriptEnabled", "ClickableViewAccessibility"})
    @Override
    public void initView() {
        mSmartView = (SmartView) findViewById(R.id.mSmartView);
        mSmartView.centerTextView.setText(info.title);
        boolean back = getIntent().getBooleanExtra(BConfig.BACK, false);
        if (getArguments() != null && !back) back = getArguments().getBoolean(BConfig.BACK, false);
        mSmartView.setBack(back);
        if (!getIntent().getBooleanExtra(BConfig.TOP_SHOW, true))
            mSmartView.topContent.setVisibility(View.GONE);
        if (listener != null) mSmartView.setListener(listener);
        String tmpUrl = "";
        if (getArguments() != null) {
            tmpUrl = getArguments().getString(BConfig.URL);
        } else {
            tmpUrl = getIntent().getStringExtra(BConfig.URL);
        }
        if (TextUtils.isEmpty(tmpUrl)) {
            tmpUrl = BuildConfig.DEBUG ? "https://www.baidu.com/" : "url为空";
        }
        log(tmpUrl);
        String finalTitle = info.title;
        CoordinatorLayout.LayoutParams layoutParams = new CoordinatorLayout.LayoutParams(-1, -1);
        layoutParams.setBehavior(new AppBarLayout.ScrollingViewBehavior());
        mWebView = new WebView(BApp.app());
        mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        WebSettings settings = mWebView.getSettings();
        settings.setSavePassword(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setAppCacheEnabled(true);
        settings.setAllowContentAccess(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setDisplayZoomControls(false);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setDatabaseEnabled(true);
        settings.setGeolocationEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setCacheMode(LOAD_NO_CACHE);
        settings.setDefaultTextEncodingName("GBK");
        settings.setLayoutAlgorithm(LayoutAlgorithm.NORMAL);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportMultipleWindows(true);// 新加
        settings.setAllowFileAccess(true); // 允许访问文件
        settings.setSaveFormData(true);// 保存表单数据
        settings.setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        if (!settings.getUserAgentString().contains("Mobile") && !settings.getUserAgentString().contains("TV"))
            settings.setUserAgentString(settings.getUserAgentString().replace("Safari", "Mobile Safari"));
        AgentWeb.CommonBuilder builder = AgentWeb.with(this)
                .setAgentWebParent(mSmartView, layoutParams)
                .useDefaultIndicator()
                .setWebView(mWebView)
                .setWebViewClient(getWebClient())
                .setWebChromeClient(getWebChromeClient(finalTitle))
                .setMainFrameErrorView(R.layout.layout_web_error, R.id.error)
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK);
        Object instance = null;
        if (BConfig.get().getWebInterface() != null) {
            try {
                instance = BConfig.get().getWebInterface().newInstance();
                builder.addJavascriptInterface(BConfig.ANDROID, instance);
            } catch (IllegalAccessException | java.lang.InstantiationException e) {
                e.printStackTrace();
            }
        }
        mAgentWeb = builder
                .createAgentWeb()
                .ready()
                .go(tmpUrl);
        if (instance != null && instance instanceof BWebJS)
            ((BWebJS) instance).setAgentWeb(mAgentWeb).setActivity(getActivity());
    }

    @NonNull
    private WebChromeClient getWebChromeClient(String finalTitle) {
        return new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView webView, String s) {
                super.onReceivedTitle(webView, s);
                if (titleView != null)
                    titleView.setText(s);
                else if (TextUtils.isEmpty(finalTitle)) mSmartView.centerTextView.setText(s);
            }
        };
    }

    @NonNull
    private WebViewClient getWebClient() {
        return new WebViewClient() {//拦截错误链接
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                // 不要使用super，否则有些手机访问不了，因为包含了一条 handler.cancel()
                // super.onReceivedSslError(view, handler, error);
                // 接受所有网站的证书，忽略SSL错误，执行访问网页
                handler.proceed();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                log("shouldOverrideUrlLoading", request.getUrl().toString());
                if (!request.getUrl().toString().contains("http")) return true;
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                log("shouldOverrideUrlLoading", url);
                if (!url.contains("http")) return true;
                return super.shouldOverrideUrlLoading(view, url);
            }
        };
    }

    public SmartView getSmartView() {
        return mSmartView;
    }

    @Override
    public void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    public boolean onBackPressed() {
        return mAgentWeb.back();
    }

    @Override
    public void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mWebView.removeAllViews();
        mAgentWeb.getWebLifeCycle().onDestroy();
        mWebView = null;
        mAgentWeb = null;
        super.onDestroy();
    }

    public void setTitleView(TextView titleView) {
        this.titleView = titleView;
    }

    @Subscribe(sticky = true)
    public void setSmartListener(Smart listener) {
        if (BApp.app().act().equals(getActivity())) {
            this.listener = listener;
            EventBus.getDefault().removeStickyEvent(listener);
        }

    }

    @Subscribe()
    public void setWebEvent(WebEvent event) {
        if (BApp.app().act().equals(getActivity())) {
            log(event.toString());
            switch (event.getType()) {
                case WebEvent.LOAD_JS:
//                    mWebView.evaluateJavascript(event.getValue(),this);
                case WebEvent.REFRESH:
                    mWebView.reload();
                    break;
                case WebEvent.LOAD_URL:
                    mWebView.loadUrl(event.getValue());
                    break;
                case WebEvent.BACK:
                    mWebView.goBack();
                    break;
                case WebEvent.FORWARD:
                    mWebView.goForward();
                    break;
                case WebEvent.HIDE_TOP:
                    mSmartView.topContent.setVisibility(View.GONE);
                    break;
                case WebEvent.SHOW_TOP:
                    mSmartView.topContent.setVisibility(View.VISIBLE);
                    break;
            }

        }
    }


}
