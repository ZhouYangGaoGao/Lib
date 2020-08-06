package base;

public class BaseBean<T> extends BResponse<T> {

    @Override
    public boolean setOnNext(BSub<T> sub) {
        if ("0".equals(code())) sub.onSuccess(data);
        else if (sub.onCode(code())) sub.onFail(msg());
        return true;
    }
}
