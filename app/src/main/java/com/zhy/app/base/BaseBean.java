package com.zhy.app.base;

import base.BResponse;
import base.BSub;

public class BaseBean<T> extends BResponse<T> {

    private String code;
    private String msg;
    private T data;
    private boolean success;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public boolean setOnNext(BSub<? extends BResponse<T>, T> sub) {
        if (success && data != null) sub.onSuccess(data);
        else if (sub.onCode(code)) sub.onFail(msg);
        return true;
    }
}
