package com.ahdhst.wall;


import android.view.View;
import android.widget.ImageView;

import org.greenrobot.eventbus.EventBus;
import org.salient.artplayer.MediaPlayerManager;
import org.salient.artplayer.conduction.PlayerState;
import org.salient.artplayer.ijk.IjkPlayer;
import org.salient.artplayer.ui.VideoView;

import java.io.IOException;

import anylayer.AnyLayer;
import anylayer.Layer;
import base.BActivity;
import base.BConfig;
import base.BWebJS;
import custom.PopView;
import listener.WebEvent;

public class WMainActivity extends BActivity implements View.OnClickListener, Layer.OnShowListener, Layer.OnDismissListener, Layer.OnClickListener {

    private final static String baseUrl = "http://192.168.21.220:8080/";
    private String videoUrl = "http://vfx.mtime.cn/Video/2018/06/27/mp4/180627094726195356.mp4";
    private String videoUrl1 = "http://vfx.mtime.cn/Video/2018/07/06/mp4/180706094003288023.mp4";
    private String[] urls = {baseUrl + "textMode", baseUrl + "sendVideo", baseUrl + "officeMode"};
    private View popView;
    private int index = 1;
    private VideoView mVideoView;
    private IjkPlayer player;
    private ImageView actionBtn;
    private Layer layer;

    @Override
    public void beforeView() {
        contentViewId = R.layout.activity_wall_main;
        initJs();
    }

    private void initJs() {
        BConfig.get().setWebInterface(new BWebJS() {
            private Runnable runnable = () -> player();

            @Override
            public String getInfo(int code, String info) {
                videoUrl = info;
                runOnUiThread(runnable);
                return super.getInfo(code, info);
            }
        });
    }

    @Override
    public void afterView() {
        EventBus.getDefault().post(new WebEvent(WebEvent.HIDE_TOP));
        popView = new PopView("文本", "视频", "记事");
        mVideoView = findViewById(R.id.videoView);
        actionBtn = findViewById(R.id.btn_more);
        actionBtn.setOnClickListener(this);
        actionBtn.requestFocus();
        player = new IjkPlayer();
        try {
            player.setDataSource(videoUrl1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.setLooping(true);
        mVideoView.setMediaPlayer(player);
        initLayer();
        doAction(index);
    }

    private void player() {
        mVideoView.setVisibility(View.VISIBLE);
        //开始播放
        if (mVideoView.getPlayerState() == PlayerState.PAUSED.INSTANCE)
            mVideoView.start();
        else if (mVideoView.getPlayerState() == PlayerState.INITIALIZED.INSTANCE)
            mVideoView.prepare();
        else if (mVideoView.getPlayerState() == PlayerState.STOPPED.INSTANCE)
            mVideoView.prepare();
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

    private void doAction(int id) {
        index = id;
        if (index == 1) {
            player();
        } else {
            mVideoView.pause();
            mVideoView.setVisibility(View.GONE);
        }
        EventBus.getDefault().post(new WebEvent(WebEvent.LOAD_URL, urls[index]));
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
}
