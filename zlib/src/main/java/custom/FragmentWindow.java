package custom;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.zhy.android.R;

import background.drawable.DrawableCreator;

public class FragmentWindow extends PopupWindow {
    private Context context;

    public FragmentWindow(Context context) {
        super(context);
        this.context = context;
    }

    public Fragment setContentView(int contentViewId, int fragmentId) {
        View layout = View.inflate(context, contentViewId, null);
        setContentView(layout);
        setWidth(-1);
        setHeight(-2);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            layout.setForeground(new DrawableCreator.Builder()
                    .setStrokeColor(0x88000000)
                    .setStrokeWidth(2)
                    .build());
        }
        setBackgroundDrawable(new DrawableCreator.Builder()
                .setSolidColor(0xff999999)
                .build());
        setOutsideTouchable(true);
        if (context instanceof AppCompatActivity)
       return  ((AppCompatActivity)context).getSupportFragmentManager().findFragmentById(fragmentId);
        else return null;
    }

    public Fragment setContentView(int contentViewId){
        return setContentView(contentViewId, R.id.historyFragment);
    }
}
