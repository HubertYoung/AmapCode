package com.autonavi.minimap.ajx3.scheme;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.autonavi.common.Callback;
import com.autonavi.minimap.ajx3.upgrade.Ajx3ActionLogUtil;
import java.util.List;

public class Ajx3RouterManager {
    private static String logStatId = "";
    private static volatile Ajx3RouterManager mInstance;

    private Ajx3RouterManager() {
    }

    public static Ajx3RouterManager getInstance() {
        if (mInstance == null) {
            synchronized (Ajx3RouterManager.class) {
                try {
                    if (mInstance == null) {
                        mInstance = new Ajx3RouterManager();
                    }
                }
            }
        }
        return mInstance;
    }

    public boolean handleFinal(Intent intent, Callback<Boolean> callback) {
        esk esk;
        if (intent.getData() == null || !isVersionEffective(intent.getData())) {
            return false;
        }
        List<esk> a = esf.a().a((String) "ajx_final_scheme");
        if (a == null || a.size() == 0) {
            esk = null;
        } else {
            esk = a.get(0);
        }
        if (esk == null) {
            return false;
        }
        if (esk instanceof Ajx3SchemeFinalRouter) {
            ((Ajx3SchemeFinalRouter) esk).setResultCallback(callback);
        }
        boolean start = esk.start(new ese(intent));
        if (!start) {
            Ajx3ActionLogUtil.actionLogAjx("B008", intent.getData().toString());
        }
        return start;
    }

    public boolean handlePrepare(Intent intent) {
        esk esk;
        if (intent.getData() == null || !isVersionEffective(intent.getData())) {
            return false;
        }
        Ajx3ActionLogUtil.actionLogAjx("B007", intent.getData().toString());
        List<esk> a = esf.a().a((String) "ajx_prepare_scheme");
        if (a == null || a.size() == 0) {
            esk = null;
        } else {
            esk = a.get(0);
        }
        if (esk == null) {
            return false;
        }
        return esk.start(new ese(intent));
    }

    public boolean isAmapUriIntent(Uri uri) {
        return uri != null && uri.isHierarchical() && TextUtils.equals(uri.getScheme(), "amapuri");
    }

    private boolean isVersionEffective(Uri uri) {
        String queryParameter = uri.getQueryParameter("effectiveVersion");
        if (!TextUtils.isEmpty(queryParameter)) {
            if (!Ajx3SchemeHelper.checkEffectiveVersion(queryParameter.trim(), a.a().a)) {
                return false;
            }
        }
        return true;
    }

    public void updateLogStatId() {
        logStatId = Ajx3ActionLogUtil.generateStatId();
    }

    public String getLogStatId() {
        return logStatId;
    }
}
