package base;

public class BaseBean<T> implements BBean<T> {

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
    public void setOnNext(BSub<? extends BBean<T>, T> sub) {
        if ("0".equals(errorCode) && data != null) sub.onSuccess(data);
        else if (sub.onCode(errorCode)) sub.onFail(errorMsg);
    }
}
