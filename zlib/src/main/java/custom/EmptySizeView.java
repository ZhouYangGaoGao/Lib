package custom;

import android.content.Context;
import android.view.View;
import android.widget.AbsListView;

import base.BApp;
import util.ScreenUtils;


public class EmptySizeView extends View {
    private EmptySizeView(Context context) {
        super(context);
    }
    public EmptySizeView(int size) {
        this(BApp.app().act());
        setLayoutParams(new AbsListView.LayoutParams(1, ScreenUtils.dip2px(size)));
    }
}
