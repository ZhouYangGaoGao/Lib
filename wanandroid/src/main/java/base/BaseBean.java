package base;

public class BaseBean<T> extends BResponse<T> {

    private T data;
    private String errorCode;
    private String errorMsg;

    @Override
    public boolean setOnNext(BSub<? extends BResponse<T>, T> sub) {
        if ("0".equals(errorCode)) sub.onSuccess(data);
        else if (sub.onCode(errorCode)) sub.onFail(errorMsg);
        return true;
    }
}
