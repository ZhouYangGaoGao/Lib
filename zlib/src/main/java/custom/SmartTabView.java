package custom;

import android.content.Context;
import android.util.AttributeSet;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

public class SmartTabView extends SmartTabLayout {
    public SmartTabView(Context context) {
        this(context,null);
    }

    public SmartTabView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SmartTabView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
