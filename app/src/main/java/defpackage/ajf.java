package defpackage;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.CallSuper;
import android.view.KeyEvent;
import com.amap.bundle.jsadapter.JsAdapter;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.fragmentcontainer.page.utils.IActvitiyStateListener;
import com.autonavi.map.fragmentcontainer.page.utils.IPageStateListener;
import com.autonavi.minimap.ajx3.util.Constants;
import com.autonavi.widget.webview.MultiTabWebView;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: ajf reason: default package */
/* compiled from: BaseWebViewPresenter */
public class ajf implements ajh, IActvitiyStateListener, IPageStateListener {
    public ajc a;
    private boolean b = false;

    public String b() {
        return null;
    }

    public b c() {
        return null;
    }

    public boolean d() {
        return false;
    }

    public boolean e() {
        return false;
    }

    public boolean f() {
        return false;
    }

    public boolean g() {
        return true;
    }

    public boolean h() {
        return false;
    }

    public a l_() {
        return null;
    }

    public void onActivityPause() {
    }

    public void onActivityResult(int i, int i2, Intent intent) {
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        return false;
    }

    public void onNewIntent(PageBundle pageBundle) {
    }

    public void onPageCreated(int i, int i2) {
    }

    public void onResult(int i, ResultType resultType, PageBundle pageBundle) {
    }

    public void onWindowFocusChanged(boolean z) {
    }

    @CallSuper
    public ON_BACK_TYPE onBackPressed() {
        i();
        return this.a.f();
    }

    private void i() {
        if (!this.b) {
            throw new IllegalAccessError("必须先调用 attactToPage,才可以调用其他接口");
        }
    }

    public void onPageCreated() {
        if (this.a instanceof AbstractBasePage) {
            AbstractBasePage abstractBasePage = (AbstractBasePage) this.a;
            AMapPageUtil.setActivityStateListener(abstractBasePage, this);
            AMapPageUtil.setPageStateListener(abstractBasePage, this);
        }
    }

    @CallSuper
    public void onStart() {
        a(Constants.EVENT_RESUME, "2");
    }

    public void onResume() {
        try {
            bmp.a((String) "CrashH5Url", this.a.a().getUrl());
        } catch (Throwable unused) {
        }
    }

    @CallSuper
    public void onStop() {
        a(Constants.EVENT_PAUSE, "2");
    }

    @CallSuper
    public void onDestroy() {
        i();
        if (this.a instanceof AbstractBasePage) {
            AbstractBasePage abstractBasePage = (AbstractBasePage) this.a;
            AMapPageUtil.removeActivityStateListener(abstractBasePage);
            AMapPageUtil.removePageStateListener(abstractBasePage);
        }
        this.a.d();
    }

    public void onConfigurationChanged(Configuration configuration) {
        this.a.e();
    }

    public final void a(ajc ajc) {
        this.a = ajc;
        this.b = this.a != null;
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
        JsAdapter b2 = this.a != null ? this.a.b() : null;
        if (b2 != null) {
            b2.callJs("activeEvent", jSONObject.toString());
        }
    }

    private boolean j() {
        return this.a != null && this.a == AMapPageUtil.getPageContext();
    }

    public void onActivityStart() {
        if (j()) {
            a(Constants.EVENT_RESUME, "1");
        }
    }

    public void onActivityResume() {
        if (this.a.c() && (this.a.getContext() instanceof Activity)) {
            Activity activity = (Activity) this.a.getContext();
            activity.setRequestedOrientation(0);
            activity.getWindow().addFlags(1024);
        }
        MultiTabWebView a2 = this.a.a();
        if (a2 != null) {
            a2.onViewResume();
        }
    }

    public void onActivityStop() {
        if (j()) {
            a(Constants.EVENT_PAUSE, "1");
        }
        MultiTabWebView a2 = this.a.a();
        if (a2 != null) {
            a2.onViewPause();
        }
    }

    public void onCover() {
        a(Constants.EVENT_PAUSE, "2");
    }

    public void onAppear() {
        a(Constants.EVENT_RESUME, "2");
    }

    public void onPause() {
        try {
            bmp.a((String) "CrashH5Url", (String) "");
        } catch (Throwable unused) {
        }
    }
}
