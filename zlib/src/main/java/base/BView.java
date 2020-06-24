package base;

import android.view.View;

public interface BView<M> {


    void log(String... message);

    void dialog(String... message);

    void toast(String message);

    View getView(int layoutId);

    void getData();

    void success(M data);

    void fail(String message);

    void completed();


    void initView();

    void beforeView();

    void afterView();
}
