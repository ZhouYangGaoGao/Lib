package base;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.zhy.android.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class PermissionFragment extends Fragment implements EasyPermissions.PermissionCallbacks {
    public static final String 录音 = Manifest.permission.RECORD_AUDIO;
    public static final String 锁屏唤醒 = Manifest.permission.WAKE_LOCK;
    public static final String 账户 = Manifest.permission.GET_ACCOUNTS;
    public static final String 音频设置 = Manifest.permission.MODIFY_AUDIO_SETTINGS;
    public static final String 手机状态 = Manifest.permission.READ_PHONE_STATE;
    public static final String 拔打电话 = Manifest.permission.CALL_PHONE;
    public static final String 相机 = Manifest.permission.CAMERA;
    public static final String 精确位置 = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String 大致位置 = Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final String 读取文件 = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String 文件写入 = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private String[] mPerms = {录音, 账户, 锁屏唤醒, 手机状态, 音频设置, 拔打电话,
            相机, 精确位置, 大致位置, 读取文件, 文件写入};
    private OnPermissionListener listener;
    private static PermissionFragment fragment;
    private static Map<String, String> names;

    public static void request(OnPermissionListener listener, String... mPerms) {
        if (fragment == null) {
            fragment = new PermissionFragment();
            fragment.listener = listener;
            names = new HashMap<>();
            names.put(录音, "录音");
            names.put(账户, "账户");
            names.put(锁屏唤醒, "锁屏唤醒");
            names.put(手机状态, "录手机状态音");
            names.put(音频设置, "音频设置");
            names.put(拔打电话, "拔打电话");
            names.put(相机, "相机");
            names.put(精确位置, "精确位置");
            names.put(大致位置, "大致位置");
            names.put(读取文件, "读取文件");
            names.put(文件写入, "文件写入");
            if (mPerms.length > 0)
                fragment.mPerms = mPerms;
            ((AppCompatActivity) BApp.app().act()).getSupportFragmentManager().beginTransaction().add(R.id.mRootView, fragment).commit();
        } else {
            fragment.listener = listener;
            if (mPerms.length > 0)
                fragment.mPerms = mPerms;
            fragment.requestPermission();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragment.requestPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (listener != null) listener.onPermissionSuccess();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {

            for (int i = 0; i < mPerms.length; i++) {
                if (perms.get(0).equals(mPerms[i])) {
                    perms.set(0, names.get(mPerms[i]) + "权限");
                    break;
                }
            }
            new AppSettingsDialog.Builder(this)
                    .setRationale(R.string.permission_rationale)
                    .setTitle(perms.get(0))
                    .build()
                    .show();
        }
    }

    @AfterPermissionGranted(11)
    private void requestPermission() {
        if (EasyPermissions.hasPermissions(BApp.app(), mPerms)) {
            if (listener != null) listener.onPermissionSuccess();
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.permission_tips),
                    11, mPerms);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView view = new TextView(getActivity());
        view.setVisibility(View.GONE);
        return view;
    }

    public interface OnPermissionListener {
        void onPermissionSuccess();
    }

    private PermissionFragment() {
    }

    private PermissionFragment(int contentLayoutId) {
        super(contentLayoutId);
    }
}
