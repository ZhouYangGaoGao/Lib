package com.zhy.wlthd.manager;

import android.text.TextUtils;
import android.webkit.JavascriptInterface;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.photo.FileModel;

import java.util.ArrayList;
import java.util.List;

import base.BWebJS;
import photopicker.lib.entity.LocalMedia;
import util.JsonUtil;

public class WLTJS extends BWebJS implements FileModel.OnFilesGetListener{
    private final static String FILE = "file(";
    private FileModel fileModel;

    @JavascriptInterface
    @Override
    public String getInfo(int code) {
        return getInfo(code, "");
    }

    @JavascriptInterface
    @Override
    public String getInfo(int code, String info) {
        returnInfo = "";
        switch (code) {
            case 103://图片上传
                if (fileModel == null) {
                    fileModel = new FileModel(activity,this);
                }
                List<LocalMedia> list;
                if (TextUtils.isEmpty(info)) {
                    list = new ArrayList<>();
                } else {
                    list = new Gson().fromJson(info, new TypeToken<List<LocalMedia>>() {
                    }.getType());
                }
                fileModel.go(list);
                break;
        }
        return super.getInfo(code, info);
    }

    @Override
    public void files(List<LocalMedia> mediaList) {
        callJs(FILE + JsonUtil.getJson(mediaList) + ")");
    }
}
