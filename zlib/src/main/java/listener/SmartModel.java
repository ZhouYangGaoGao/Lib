package listener;

import org.greenrobot.eventbus.EventBus;

import custom.SmartView;

public abstract class SmartModel implements SmartListener {
    public int[][] res = new int[3][4];
    public String[] text = new String[3];
    public int[] padding = new int[3];
    public int[] textSize = new int[3];
    public int[] indexes;
    public int gravity = -1;

    protected void init(){};

    public SmartModel(int rightRes) {
        initRight(rightRes,0);
    }

    public SmartModel(int rightOneRes, int rightTwoRes) {
        initRight(rightOneRes, rightTwoRes);
    }

    private void initRight(int rightRightRes, int rightLeftRes) {
        res[2][2] = rightRightRes;
        res[2][0] = rightLeftRes;
        indexes = new int[]{2};
        EventBus.getDefault().postSticky(this);
    }

    public SmartModel() {
        init();
    }

    public abstract void onClick(SmartView sv, int viewIndex, int resIndex);
}
