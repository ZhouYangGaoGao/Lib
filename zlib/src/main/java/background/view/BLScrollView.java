package background.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import background.BackgroundFactory;


public class BLScrollView extends ScrollView {
    public BLScrollView(Context context) {
        super(context);
    }

    public BLScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BLScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){
        BackgroundFactory.setViewBackground(context, attrs, this);
    }
}
