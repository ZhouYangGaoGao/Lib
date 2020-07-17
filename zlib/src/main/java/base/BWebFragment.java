package base;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.WebChromeClient;
import com.zhy.android.BuildConfig;
import com.zhy.android.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.InputStream;

import custom.SmartView;
import listener.SmartModel;
import okhttp3.ResponseBody;
import util.RexUtils;

/**
 * 通用网页
 */
public class BWebFragment extends BFragment {

    protected SmartView mSmartView;
    private TextView titleView;
    public WebView mWebView;
    private AgentWeb mAgentWeb;
    private SmartModel listener;

    @Override
    public void beforeView() {
        useEventBus = true;
        contentViewId = R.layout.fragment_web;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void initView() {
        mSmartView = (SmartView) findViewById(R.id.mSmartView);
        mSmartView.centerTextView.setText(title);
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
        log(tmpUrl = tmpUrl + (tmpUrl.contains("?") ? "&" : "?") + "token=" + BConfig.getConfig().getToken() + "&time=" + System.currentTimeMillis());
        String finalTitle = title;
        WebChromeClient webChromeClient = new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView webView, String s) {
                super.onReceivedTitle(webView, s);
                if (titleView != null)
                    titleView.setText(s);
                else if (TextUtils.isEmpty(finalTitle)) mSmartView.centerTextView.setText(s);
            }
        };

        CoordinatorLayout.LayoutParams layoutParams = new CoordinatorLayout.LayoutParams(-1, -1);
        layoutParams.setBehavior(new AppBarLayout.ScrollingViewBehavior());
        mWebView = new WebView(getActivity());
        mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        WebSettings settings = mWebView.getSettings();
        settings.setDisplayZoomControls(false);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setDefaultTextEncodingName("GBK");
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mSmartView, layoutParams)
                .useDefaultIndicator()
                .setWebView(mWebView)
                .setWebViewClient(new com.just.agentweb.WebViewClient() {//拦截错误链接

                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                        log(BConfig.URL, request.getUrl().toString());
                        if (!request.getUrl().toString().contains("http")) return true;
                        return super.shouldOverrideUrlLoading(view, request);
                    }

                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        log("0" + BConfig.URL, url);
                        if (!url.contains("http")) return true;
                        return super.shouldOverrideUrlLoading(view, url);
                    }
                })
                .setWebChromeClient(webChromeClient)
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)
                .addJavascriptInterface(BConfig.ANDROID, BConfig.getConfig().getWebInterface())
                .createAgentWeb()
                .ready()
                .go(tmpUrl);
    }

    @Override
    public void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
        onBackPressed();
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
    public void setSmartListener(SmartModel listener) {
        if (BApp.app().act().equals(getActivity())) {
            this.listener = listener;
            EventBus.getDefault().removeStickyEvent(listener);
        }

    }
}
