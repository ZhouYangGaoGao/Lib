package util;

import android.content.DialogInterface;

import com.necer.ndialog.ChoiceDialog;
import com.necer.ndialog.ConfirmDialog;

import base.BApp;

public class Dialogs {
    public static boolean show = true;

    public static void show(ChoiceDialog.OnItemClickListener listener, String... items) {
        if (show && BApp.app().currentActivity() != null)
            new ChoiceDialog(BApp.app().currentActivity(), true)
                    .setItems(items)
                    .hasCancleButton(true)
                    .setOnItemClickListener(listener).create().show();
    }

    public static void show(String title, String message, DialogInterface.OnClickListener listener) {
        if (show && BApp.app().currentActivity() != null)
            new ConfirmDialog(BApp.app().currentActivity(), true)
                    .setTtitle(title)
                    .setMessage(message)
                    .setPositiveButton("确定", listener)
                    .create().show();
    }

    public static void show(String title, String message, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener noListener) {
        if (show && BApp.app().currentActivity() != null)
            new ConfirmDialog(BApp.app().currentActivity(), true)
                    .setTtitle(title)
                    .setMessage(message)
                    .setPositiveButton("确定", okListener)
                    .setNegativeButton("取消", noListener)
                    .create().show();
    }
}
