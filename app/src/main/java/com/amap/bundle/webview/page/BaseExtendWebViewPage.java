package com.amap.bundle.webview.page;

import com.alipay.mobile.antui.basic.AUCardOptionView;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.bundle.webview.widget.AbstractBaseWebView;
import com.amap.bundle.webview.widget.AbstractBaseWebView.d;
import com.amap.bundle.webview.widget.AbstractBaseWebView.e;
import com.autonavi.common.Callback;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.ajx3.util.Constants;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import defpackage.aje;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressFBWarnings({"NP_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD"})
public class BaseExtendWebViewPage<Presenter extends aje> extends AbstractBasePage<Presenter> implements d, e {
    protected JsAdapter a;
    public AbstractBaseWebView b;
    Callback<Boolean> c = new Callback<Boolean>() {
        public void error(Throwable th, boolean z) {
        }

        public void callback(Boolean bool) {
            BaseExtendWebViewPage.this.b.reload();
        }
    };

    public final boolean a(String str) {
        return false;
    }

    public final void c() {
    }

    public final void a(boolean z) {
        a(z ? Constants.EVENT_PAUSE : Constants.EVENT_RESUME, (String) "2");
    }

    public final void a() {
        a((String) Constants.EVENT_RESUME, (String) "1");
    }

    public final void b() {
        a((String) Constants.EVENT_PAUSE, (String) "1");
    }

    private void a(String str, String str2) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("action", "activeEvent");
            jSONObject.put("type", str);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("reson", str2);
            jSONObject.put("data", jSONObject2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (this.a != null) {
            this.a.callJs("activeEvent", jSONObject.toString());
        }
    }

    public final void a(int i, PageBundle pageBundle) {
        if (i == 1 && pageBundle != null) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("id", pageBundle.getString("COMMENT_PUBLISH_ID", ""));
                jSONObject.put("desc", pageBundle.getString("EDIT_COMMENT_CONTENT"));
                jSONObject.put("score", pageBundle.getInt("EDIT_COMMENT_RATING"));
                jSONObject.put("picCount", pageBundle.getInt("EDIT_COMMENT_PICCOUNT"));
                jSONObject.put("type", pageBundle.getInt("COMMENT_TYPE"));
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put(AUCardOptionView.TYPE_COMMENT, jSONObject);
                jSONObject2.put(TrafficUtil.POIID, pageBundle.getString("EDIT_COMMENT_POI_ID"));
                jSONObject2.put("status", pageBundle.getInt("COMMENT_PUBLISH_STATUS"));
                jSONObject2.put("poiName", pageBundle.getString("POI_NAME"));
                jSONObject2.put(com.alipay.mobile.beehive.audio.Constants.KEY_AUDIO_BUSINESS_ID, pageBundle.getString("POI_BUSINESS"));
                Object obj = pageBundle.get("EDIT_COMMENT_CALLBACK");
                if (obj instanceof wa) {
                    wa waVar = (wa) obj;
                    jSONObject2.put("_action", waVar.b);
                    this.a.callJs(waVar.a, jSONObject2.toString());
                }
            } catch (JSONException e) {
                kf.a((Throwable) e);
            }
        }
    }

    public final void b(String str) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString("code", str);
        setResult(ResultType.OK, pageBundle);
        finish();
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new aje(this);
    }
}
