package base;

import photopicker.lib.tools.ToastUtils;

public class BaseBean<T> extends BResponse<T> {

    private T data;
    private String errorCode;
    private String errorMsg;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public boolean setOnNext(BSub<? extends BResponse<T>, T> sub) {
        if ("0".equals(errorCode)) sub.onSuccess(data);
        else if (sub.onCode(errorCode)) sub.onFail(errorMsg);
        return true;
    }
}
