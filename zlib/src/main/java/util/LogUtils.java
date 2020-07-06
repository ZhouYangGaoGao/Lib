package util;

import android.util.Log;

import com.zhy.android.BuildConfig;


public class LogUtils {
    private static boolean showLog = BuildConfig.DEBUG;//显示日志
    private static String tag = BuildConfig.LIBRARY_PACKAGE_NAME;

    public static void d(String local, String msg) {
        if (!showLog) return;
        Log.d(local, msg);
    }

    public static void t(String local, String msg) {
        if (!showLog) return;
        Log.d(tag, local + "\n" + msg);
    }

    public static void d(String msg) {
        if (!showLog) return;
        Log.d(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (!showLog) return;
        Log.i(tag, msg);
    }

    public static void e(String... message) {
        if (!showLog) return;
        if (message.length > 2) {
            e(message[0], message[1] + "  " + message[2]);
        } else if (message.length > 1) {
            e(message[0], message[1]);
        } else e(message[0]);

    }

    private static void e(String tag, String msg) {
        if (!showLog) return;
        int strLength = msg.length();
        int start = 0;
        int end = 3 * 1024;
        if (strLength < 3 * 1024)
            Log.e(tag, msg);
        else
            for (int i = 0; i < 10; i++) {
                if (strLength > end) {
                    Log.e(tag + i, msg.substring(start, end));
                    start = end;
                    end = end + 3 * 1024;
                } else {
                    Log.e(tag + i, msg.substring(start, strLength));
                    break;
                }
            }

    }

    public static void i(String msg) {
        if (!showLog) return;
        Log.i(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (!showLog) return;
        Log.v(tag, msg);
    }

    public static void v(String msg) {
        if (!showLog) return;
        Log.v(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (!showLog) return;
        Log.w(tag, msg);
    }

    public static void w(String msg) {
        if (!showLog) return;
        Log.w(tag, msg);
    }

    public static void e(String msg) {
        if (!showLog) return;
        Log.e(tag, msg);
    }

    public static void printStack() {
        Throwable throwable = new Throwable();
        Log.w(tag, Log.getStackTraceString(throwable));
    }

}
