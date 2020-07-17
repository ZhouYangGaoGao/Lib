package listener;

import custom.SmartView;

public abstract class SmartModel implements SmartListener {
    public int[][] drawableRes = new int[3][4];
    public String[] text = new String[3];
    public int[] padding = new int[3];
    public int[] textSize = new int[3];
    public int[] indexes;
    public int gravity = -1;

    protected abstract void init() ;

    public SmartModel() {
        init();
    }

   public abstract void onClick(SmartView smartView, int textViewIndex, int drawableIndex);
}
