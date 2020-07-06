package util;

import android.content.Intent;

import androidx.fragment.app.Fragment;

import base.BApp;
import base.FragmentActivity;

public class GoTo {

    public static void start(Class<?> cls, Intent... intents) {
        BApp.app().act().startActivity(intent(cls, intents));
    }

    private static Intent intent(Class<?> cls, Intent[] intents) {
        Intent intent;
        if (intents.length > 0) {
            intent = intents[0];
        } else intent = new Intent();

        if (Fragment.class.isAssignableFrom(cls)) {
            intent.putExtra("cls", cls);
            cls = FragmentActivity.class;
        }
        return intent.setClass(BApp.app().act(), cls);
    }

    public static void startResult(Class<?> cls, int reQuestCode, Intent... intents) {
        BApp.app().act().startActivityForResult(intent(cls, intents), reQuestCode);
    }
}
