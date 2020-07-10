package photopicker.lib.language;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;

import photopicker.lib.tools.SPUtils;

import java.lang.ref.WeakReference;
import java.util.Locale;

/**
 * @author：luck
 * @data：2018/3/28 下午1:00
 * @描述: PictureLanguageUtils
 */
public class PictureLanguageUtils {

    private static final String KEY_LOCALE = "KEY_LOCALE";
    private static final String VALUE_FOLLOW_SYSTEM = "VALUE_FOLLOW_SYSTEM";

    private PictureLanguageUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Apply the language.
     *
     * @param locale The language of locale.
     */
    public static void applyLanguage(@NonNull Activity activity, @NonNull final Locale locale) {
        WeakReference<Activity> mActivity = new WeakReference<>(activity);
        if (mActivity.get() != null) {
            applyLanguage(mActivity.get(), locale, false);
        }
    }


    private static void applyLanguage(@NonNull Activity activity, @NonNull final Locale locale,
                                      final boolean isFollowSystem) {
        if (isFollowSystem) {
            SPUtils.getPictureSpUtils().put(KEY_LOCALE, VALUE_FOLLOW_SYSTEM);
        } else {
            String localLanguage = locale.getLanguage();
            String localCountry = locale.getCountry();
            SPUtils.getPictureSpUtils().put(KEY_LOCALE, localLanguage + "$" + localCountry);
        }

        updateLanguage(activity, locale);
    }


    private static void updateLanguage(Context context, Locale locale) {
        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        Locale contextLocale = config.locale;
        if (equals(contextLocale.getLanguage(), locale.getLanguage())
                && equals(contextLocale.getCountry(), locale.getCountry())) {
            return;
        }
        DisplayMetrics dm = resources.getDisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(locale);
            context.createConfigurationContext(config);
        } else {
            config.locale = locale;
        }
        resources.updateConfiguration(config, dm);
    }

    private static boolean equals(final CharSequence s1, final CharSequence s2) {
        if (s1 == s2) return true;
        int length;
        if (s1 != null && s2 != null && (length = s1.length()) == s2.length()) {
            if (s1 instanceof String && s2 instanceof String) {
                return s1.equals(s2);
            } else {
                for (int i = 0; i < length; i++) {
                    if (s1.charAt(i) != s2.charAt(i)) return false;
                }
                return true;
            }
        }
        return false;
    }
}