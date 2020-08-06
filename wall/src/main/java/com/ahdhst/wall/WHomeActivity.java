package com.ahdhst.wall;

import android.media.MediaPlayer;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import anylayer.AnyLayer;
import anylayer.Layer;
import base.BActivity;
import base.BConfig;
import base.BWebJS;
import custom.PopView;
import listener.WebEvent;

public class WHomeActivity extends BActivity implements View.OnClickListener,
        MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener,
        Layer.OnDismissListener, Layer.OnClickListener, Layer.OnShowListener {

    private final static String baseUrl = "http://192.168.21.220:8080/";
    private String videoUrl;
    private String[] urls = {baseUrl + "textMode", baseUrl + "sendVideo", baseUrl + "officeMode"};
    private View popView;
    private int index = 0;
    private VideoView mVideoView;
    private FrameLayout videoContent;
    private ImageView actionBtn;
    private Layer layer;
    private List<Video> mVideos = new ArrayList<>();

    @Override
    public void beforeView() {
        contentViewId = R.layout.activity_wall_home;
        initJs();
    }

    @Override
    public void afterView() {
        EventBus.getDefault().post(new WebEvent(WebEvent.HIDE_TOP));
        popView = new PopView("文本", "视频", "记事");
        mVideoView = findViewById(R.id.mVideoView);
        videoContent = findViewById(R.id.videoContent);
        actionBtn = findViewById(R.id.btn_more);
        actionBtn.setOnClickListener(this);
        actionBtn.requestFocus();
        mVideoView.setKeepScreenOn(true);
        mVideoView.setOnPreparedListener(this);
        if (BuildConfig.DEBUG) mVideoView.setMediaController(new MediaController(this));
        mVideoView.setOnCompletionListener(this);
        mVideoView.setKeepScreenOn(true);
        initLayer();
        doAction(index);
    }

    private void initJs() {
        BConfig.get().setWebInterface(new BWebJS() {
            String jsonStr;
            private Runnable runnable = () -> {
                List<Video> videos = new Gson().fromJson(jsonStr, new TypeToken<ArrayList<Video>>() {
                }.getType());
                if (videos != null && videos.size() > 0) {
                    mVideos.clear();
                    mVideos.addAll(videos);
                    player(videos.get(0).getFileUrl());
                }
            };

            @JavascriptInterface
            @Override
            public String getInfo(int code, String info) {
                if (info.equals(jsonStr)) return null;
                jsonStr = info;
                runOnUiThread(runnable);
                return super.getInfo(code, info);
            }

            @Override
            public void onReceiveValue(String value) {
                log("onReceiveValue");
            }
        });
    }

    private void doAction(int id) {
        if (id == 1) {
            if (id != index) player(videoUrl);
            else {
                playNext();
            }
        } else {
            mVideoView.pause();
            videoContent.setVisibility(View.GONE);
        }
        index = id;
        EventBus.getDefault().post(new WebEvent(WebEvent.LOAD_URL, urls[index]));
    }

    private void playNext() {
        for (int i = 0; i < mVideos.size(); i++) {
            if (mVideos.get(i).fileUrl.equals(videoUrl)) {
                if (i != mVideos.size() - 1) {
                    player(mVideos.get(i + 1).getFileUrl());
                } else player(mVideos.get(0).getFileUrl());
                break;
            }
        }
    }

    private void player(String videoUrlTmp) {
        if (TextUtils.isEmpty(videoUrlTmp)) return;
        if (!videoUrlTmp.equals(videoUrl)) {
            mVideoView.setVideoPath(videoUrlTmp);
            videoUrl = videoUrlTmp;
        }
        videoContent.setVisibility(View.VISIBLE);
        mVideoView.start();
    }

    @Override
    public void onClick(View v) {
        layer.show();
    }

    private void initLayer() {
        layer = AnyLayer.popup(findViewById(R.id.target))
                .contentView(popView)
                .onClickToDismiss(this, 0, 1, 2)
                .onDismissListener(this)
                .onShowListener(this);
    }

    @Override
    public void onClick(Layer layer, View v) {
        doAction(v.getId());
    }

    @Override
    public void onDismissing(Layer layer) {
        actionBtn.requestFocus();
    }

    @Override
    public void onDismissed(Layer layer) {
    }

    @Override
    public void onShowing(Layer layer) {
        popView.findViewById(index).requestFocus();
        int next = index + 1, last = index - 1;
        if (index == urls.length - 1) {
            next = 0;
        } else if (index == 0) {
            last = urls.length - 1;
        }
        popView.findViewById(index).setNextFocusUpId(last);
        popView.findViewById(index).setNextFocusLeftId(last);
        popView.findViewById(index).setNextFocusDownId(next);
        popView.findViewById(index).setNextFocusRightId(next);
    }

    @Override
    public void onShown(Layer layer) {
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        playNext();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

    }

    static class Video {
        private String id;
        private String litImg;
        private String fileUrl;

        public String getFileUrl() {
            return fileUrl;
        }
    }
}
