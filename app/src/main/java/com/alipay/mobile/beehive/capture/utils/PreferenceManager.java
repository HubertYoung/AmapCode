package com.alipay.mobile.beehive.capture.utils;

import android.text.TextUtils;
import com.alipay.android.phone.mobilesdk.storage.sp.APSharedPreferences;
import com.alipay.android.phone.mobilesdk.storage.sp.SharedPreferencesManager;
import com.alipay.mobile.beehive.capture.constant.Constants;
import com.alipay.mobile.beehive.capture.modle.Effect;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PreferenceManager {
    private static final int MAX_USED_COUNT = 50;
    private static final String SPILT_EFFECT = "#";
    private static final String SP_KEY_BEAUTY_ON = "beauty_on";
    private static final String SP_KEY_EFFECT_USED = "SP_KEY_EFFECT_USED";
    private static final String TAG = "PreferenceManager";

    public static APSharedPreferences getPreference() {
        return SharedPreferencesManager.getInstance(LauncherApplicationAgent.getInstance().getMicroApplicationContext().getApplicationContext(), Constants.CAPTURE_APP_SHAREDPREFERENCE);
    }

    public static List<Effect> getUsed(Map<String, Effect> allEffects) {
        String[] split;
        List ret = new LinkedList();
        String used = getPreference().getString(SP_KEY_EFFECT_USED, "");
        if (!TextUtils.isEmpty(used)) {
            for (String s : used.split("#")) {
                Effect effect = checkReplace(new Effect(s), allEffects);
                if (effect != null) {
                    ret.add(effect);
                } else {
                    Logger.debug(TAG, "Ignore used effect :" + s);
                }
            }
        }
        return ret;
    }

    private static Effect checkReplace(Effect effect, Map<String, Effect> allEffects) {
        return allEffects.get(effect.effectId);
    }

    public static void updateUsed(List<Effect> used) {
        if (used != null && !used.isEmpty()) {
            if (used.size() > 50) {
                Logger.debug(TAG, "updateUsed,cut down from" + used.size() + " to50");
                used = used.subList(0, 50);
            }
            APSharedPreferences ps = getPreference();
            StringBuilder sb = new StringBuilder();
            for (Effect e : used) {
                if (!e.isNonEffect()) {
                    sb.append(e.toString());
                    sb.append("#");
                }
            }
            getPreference().putString(SP_KEY_EFFECT_USED, sb.toString());
            ps.commit();
        }
    }

    public static void recordBeauty(boolean isBeautyOn) {
        APSharedPreferences ps = getPreference();
        ps.putBoolean(SP_KEY_BEAUTY_ON, isBeautyOn);
        ps.commit();
    }

    public static boolean getBeauty() {
        return getPreference().getBoolean(SP_KEY_BEAUTY_ON, false);
    }
}
