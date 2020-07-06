package util;

import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import com.necer.ndialog.ChoiceDialog;
import com.necer.ndialog.ConfirmDialog;

import base.BApp;

public class Dialogs {

    public static AlertDialog show(ChoiceDialog.OnItemClickListener listener, String... items) {
        return show(null, listener, items);
    }

    public static AlertDialog show(AlertDialog dialog, ChoiceDialog.OnItemClickListener listener, String... items) {
        if (dialog == null)
            dialog = new ChoiceDialog(BApp.app().act(), true)
                    .setItems(items)
                    .hasCancleButton(true)
                    .setOnItemClickListener(listener)
                    .create();
        dialog.show();
        return dialog;
    }


    public static AlertDialog show(String title, String message, DialogInterface.OnClickListener listener) {
        return show(null, title, message, listener);
    }

    public static AlertDialog show(AlertDialog dialog, String title, String message, DialogInterface.OnClickListener listener) {
        if (dialog == null)
            dialog = new ConfirmDialog(BApp.app().act(), true)
                    .setTtitle(title)
                    .setMessage(message)
                    .setPositiveButton("确定", listener)
                    .create();
        dialog.show();
        return dialog;
    }


    public static AlertDialog show(String title, String message, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener noListener) {
        return show(null, title, message, okListener, noListener);
    }

    public static AlertDialog show(AlertDialog dialog, String title, String
            message, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener
                                           noListener) {
        if (dialog == null)
            dialog = new ConfirmDialog(BApp.app().act(), true)
                    .setTtitle(title)
                    .setMessage(message)
                    .setPositiveButton("确定", okListener)
                    .setNegativeButton("取消", noListener)
                    .create();
        dialog.show();
        return dialog;
    }
}
