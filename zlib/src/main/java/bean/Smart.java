package bean;

import android.graphics.drawable.Drawable;

import org.greenrobot.eventbus.EventBus;

import custom.SmartView;
import listener.SmartListener;

public abstract class Smart implements SmartListener {
    public int[][] res = new int[4][4];
    public Drawable[][] drawable = new Drawable[4][4];
    public String[] text = new String[5];
    public int[] padding = new int[4];
    public int[] textSize = new int[4];
    public int[] indexes;//设置点击事件的下标
    public int gravity = -1;

    protected void init() {
    }

    protected void send() {
        EventBus.getDefault().post(this);
    }

    protected void sendSticky() {
        EventBus.getDefault().postSticky(this);
    }

    public Smart(int... indexes) {
        this.indexes = indexes;
        init();
    }

    public abstract void onClick(SmartView sv, int viewIndex, int resIndex);

    protected String getId(int viewIndex, int resIndex){
        return viewIndex+""+resIndex;
    }
}
