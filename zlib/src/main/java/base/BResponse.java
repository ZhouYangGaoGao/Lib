package base;

import android.text.TextUtils;

public class BResponse<T> {
    /**
     * @param sub
     * @return 是否已定义处理数据结果
     */
    protected boolean setOnNext(BSub<T> sub) {
        if (success || data != null) sub.onSuccess(data);
        else if (sub.onCode(code())) sub.onFail(msg());
        return true;
    }

    private String code, errorCode;
    private String msg, errorMsg;
    private boolean success;
    protected T data;

    protected String code() {
        if (!TextUtils.isEmpty(errorCode)) return errorCode;
        return code;
    }

    protected String msg() {
        if (!TextUtils.isEmpty(errorMsg)) return errorMsg;
        return msg;
    }
}
