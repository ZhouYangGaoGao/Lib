package base;

import android.view.View;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;

import com.zhy.android.R;

public class BFragmentActivity extends BActivity {

    protected Fragment mFragment;
    protected View otherView;

    @Override
    public void beforeView() {
        slidFinish = true;
        contentViewId = R.layout.frame_layout;
    }

    @Override
    public void initView() {
        try {
            Class<?> cls = (Class<?>) getIntent().getSerializableExtra("cls");
            mFragment = (cls != null ? (Fragment) cls.newInstance() : new BWebFragment());
            getSupportFragmentManager().beginTransaction().replace(R.id.content, mFragment).commit();
            if (otherView != null) {
                FrameLayout frameLayout = findViewById(R.id.content);
                frameLayout.addView(otherView, new FrameLayout.LayoutParams(-1, -1));
            }
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (BWebFragment.class.isAssignableFrom(mFragment.getClass()) && ((BWebFragment) mFragment).onBackPressed())
            return;
        super.onBackPressed();
    }
}
