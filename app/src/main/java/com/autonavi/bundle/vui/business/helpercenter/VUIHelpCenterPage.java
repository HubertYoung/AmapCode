package com.autonavi.bundle.vui.business.helpercenter;

import android.content.Context;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.ajx3.Ajx3Page;
import java.lang.ref.WeakReference;
import org.json.JSONException;
import org.json.JSONObject;

public class VUIHelpCenterPage extends Ajx3Page implements bgm {

    public static class a {
        static WeakReference<bid> a;
    }

    public long getScenesID() {
        return 8796093022208L;
    }

    public boolean isInnerPage() {
        return false;
    }

    public boolean needKeepSessionAlive() {
        return false;
    }

    public void onCreate(Context context) {
        getArguments().putString("url", "path://amap_bundle_globalvoice/src/business/helpcenter/VUIHelpCenterPage.page.js");
        super.onCreate(context);
        PageBundle arguments = getArguments();
        if (arguments != null) {
            int i = arguments.getInt("bundle_key_token", -1);
            if (i > 0) {
                d.a.a(i, 10000, (String) null);
            }
        }
        a.a = new WeakReference<>(this);
    }

    public bgo getPresenter() {
        return new bgo() {
            public final boolean handleVUICmd(bgb bgb, bfb bfb) {
                return false;
            }
        };
    }

    public JSONObject getScenesData() {
        try {
            return new JSONObject("");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void finishSelf() {
        if (a.a != null) {
            bid bid = (bid) a.a.get();
            if (bid != null) {
                bid.finish();
            }
        }
    }
}
