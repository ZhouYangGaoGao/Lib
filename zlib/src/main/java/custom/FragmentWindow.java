package custom;

import android.os.Build;
import android.view.View;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.zhy.android.R;

import base.BApp;

public class FragmentWindow extends PopupWindow {

    public FragmentWindow() {
        super(BApp.app().act());
    }

    public Fragment setContentView(int contentViewId, int fragmentId) {
        View layout = View.inflate(BApp.app().act(), contentViewId, null);
        setContentView(layout);
        setWidth(-1);
        setHeight(-2);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setOverlapAnchor(true);
        }
        setOutsideTouchable(true);
        setBackgroundDrawable(null);
        if (BApp.app().act() instanceof AppCompatActivity)
            return ((AppCompatActivity) BApp.app().act()).getSupportFragmentManager().findFragmentById(fragmentId);
        return null;
    }

    public Fragment setContentView(int contentViewId) {
        return setContentView(contentViewId, R.id.fragment);
    }

}
