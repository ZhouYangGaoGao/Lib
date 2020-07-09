package photopicker.lib.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import photopicker.lib.PicturePreviewActivity;
import photopicker.lib.PicturePlayVideoActivity;

/**
 * @author：luck
 * @date：2019-11-23 18:57
 * @describe：Activity跳转
 */
public class JumpUtils {
    public static void startPictureVideoPlayActivity(Context context, Bundle bundle) {
        if (!DoubleUtils.isFastDoubleClick()) {
            Intent intent = new Intent();
            intent.setClass(context, PicturePlayVideoActivity.class);
            intent.putExtras(bundle);
            context.startActivity(intent);
        }
    }

    public static void startPicturePreviewActivity(Context context, Bundle bundle, int requestCode) {
        if (!DoubleUtils.isFastDoubleClick()) {
            Intent intent = new Intent();
            intent.setClass(context, PicturePreviewActivity.class);
            intent.putExtras(bundle);
            if (context instanceof Activity) {
                ((Activity) context).startActivityForResult(intent, requestCode);
            }
        }
    }
}
