package base;

import androidx.fragment.app.Fragment;

import com.zhy.android.R;

public class FragmentActivity extends BActivity {

    @Override
    public void beforeView() {
        slidFinish = true;
        contentView = R.layout.frame_layout;
    }

    @Override
    public void afterView() {
        try {
            Class<?> cls = (Class<?>) getIntent().getSerializableExtra("cls");
            getSupportFragmentManager().beginTransaction().replace(R.id.content, (Fragment) cls.newInstance()).commit();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
