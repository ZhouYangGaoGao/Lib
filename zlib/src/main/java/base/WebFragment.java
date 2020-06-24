package base;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.WebChromeClient;
import com.zhy.android.R;

import java.util.Objects;

/**
 * 通用网页
 */
public class WebFragment extends BFragment<Object, BPresenter> {

    RelativeLayout mWebParent;
    private TextView titleView;
    public WebView mWebView;
    private AgentWeb mAgentWeb;
    private String url = "url为空";

    @Override
    public void beforeView() {
        contentViewId = R.layout.fragment_web;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void initView() {
        mWebParent = (RelativeLayout) findViewById(R.id.mWebParent);
        String[] arguments = Objects.requireNonNull(getActivity()).getIntent().getStringArrayExtra(BConstant.ARGUMENTS);
        if (arguments != null && arguments.length > 0) {
            url = arguments[0];
        } else {
            if (getArguments() != null)
                url = getArguments().getString("url");
        }
        if (TextUtils.isEmpty(url)) return;
        url = url + (url.contains("?") ? "&" : "?") + "token=" + BConfig.getConfig().getToken() + "&time=" + System.currentTimeMillis();
        log(url);
        WebChromeClient webChromeClient = new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView webView, String s) {
                super.onReceivedTitle(webView, s);
                if (titleView != null)
                    titleView.setText(s);
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
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setDefaultTextEncodingName("GBK");
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mWebParent, layoutParams)
                .useDefaultIndicator()
                .setWebView(mWebView)
                .setWebChromeClient(webChromeClient)
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)
                .addJavascriptInterface("android", BConfig.getConfig().getWebInterface())
                .createAgentWeb()
                .ready()
                .go(url);
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

}
