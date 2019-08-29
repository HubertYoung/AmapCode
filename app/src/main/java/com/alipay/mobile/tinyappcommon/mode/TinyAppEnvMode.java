package com.alipay.mobile.tinyappcommon.mode;

import android.os.Bundle;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.nebula.util.H5Utils;

public enum TinyAppEnvMode {
    DEVELOP,
    TRIAL,
    EXAMINE,
    RELEASE;
    
    public static final String DEVELOP_NEBULA = "DEBUG";
    public static final String DEVELOP_NEBULA_SOURCE = "debug";
    public static final String DEVELOP_TINY_APP = "develop";
    public static final String EXAMINE_NEBULA = "REVIEW";
    public static final String EXAMINE_NEBULA_SOURCE = "debug";
    public static final String EXAMINE_TINY_APP = "examine";
    public static final String PARAM_ENV_NEBULA = "nbsn";
    public static final String PARAM_ENV_NEBULA_SOURCE = "nbsource";
    public static final String PARAM_ENV_TINY_APP = "envVersion";
    public static final String RELEASE_TINY_APP = "release";
    public static final String TRIAL_NEBULA = "TRIAL";
    public static final String TRIAL_NEBULA_SOURCE = "debug";
    public static final String TRIAL_TINY_APP = "trial";

    public static TinyAppEnvMode valueOf(H5Page h5Page) {
        if (h5Page == null) {
            return RELEASE;
        }
        return valueOfPage(h5Page.getParams());
    }

    public static TinyAppEnvMode valueOfPage(Bundle bundle) {
        String envNebula = H5Utils.getString(bundle, (String) "nbsn");
        if (TextUtils.isEmpty(envNebula)) {
            return RELEASE;
        }
        if ("DEBUG".equals(envNebula)) {
            return DEVELOP;
        }
        if (TRIAL_NEBULA.equals(envNebula)) {
            return TRIAL;
        }
        if (EXAMINE_NEBULA.equals(envNebula)) {
            return EXAMINE;
        }
        return RELEASE;
    }

    public static TinyAppEnvMode valueOf(H5Event event) {
        if (event == null) {
            return RELEASE;
        }
        return valueOfEvent(event.getParam());
    }

    public static TinyAppEnvMode valueOfEvent(JSONObject jsonObject) {
        String envTinyApp = H5Utils.getString(jsonObject, (String) PARAM_ENV_TINY_APP);
        if ("release".equals(envTinyApp)) {
            return RELEASE;
        }
        if (DEVELOP_TINY_APP.equals(envTinyApp)) {
            return DEVELOP;
        }
        if (TRIAL_TINY_APP.equals(envTinyApp)) {
            return TRIAL;
        }
        if (EXAMINE_TINY_APP.equals(envTinyApp)) {
            return EXAMINE;
        }
        return RELEASE;
    }

    public final void putToBundle(Bundle bundle) {
        if (bundle != null) {
            if (this == RELEASE) {
                if (bundle.containsKey("nbsn")) {
                    bundle.remove("nbsn");
                }
                if (bundle.containsKey("nbsource")) {
                    bundle.remove("nbsource");
                }
            } else if (this == DEVELOP) {
                bundle.putString("nbsn", "DEBUG");
                bundle.putString("nbsource", "debug");
            } else if (this == TRIAL) {
                bundle.putString("nbsn", TRIAL_NEBULA);
                bundle.putString("nbsource", "debug");
            } else if (this == EXAMINE) {
                bundle.putString("nbsn", EXAMINE_NEBULA);
                bundle.putString("nbsource", "debug");
            } else {
                if (bundle.containsKey("nbsn")) {
                    bundle.remove("nbsn");
                }
                if (bundle.containsKey("nbsource")) {
                    bundle.remove("nbsource");
                }
            }
        }
    }

    public final String toStringOfNebula() {
        if (this == RELEASE) {
            return "";
        }
        if (this == DEVELOP) {
            return "DEBUG";
        }
        if (this == TRIAL) {
            return TRIAL_NEBULA;
        }
        if (this == EXAMINE) {
            return EXAMINE_NEBULA;
        }
        return "";
    }

    public final String toStringOfTinyApp() {
        if (this == RELEASE) {
            return "release";
        }
        if (this == DEVELOP) {
            return DEVELOP_TINY_APP;
        }
        if (this == TRIAL) {
            return TRIAL_TINY_APP;
        }
        if (this == EXAMINE) {
            return EXAMINE_TINY_APP;
        }
        return "release";
    }
}
