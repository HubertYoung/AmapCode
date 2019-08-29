package com.amap.bundle.jsadapter;

import android.annotation.SuppressLint;
import android.app.Application;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.amap.bundle.blutils.log.DebugLog;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.PageBundle;
import com.autonavi.inter.IJsActionLoader;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.widget.webview.MultiTabWebView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONException;
import org.json.JSONObject;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMemberNames;

@SuppressLint({"NewApi"})
@KeepClassMemberNames
@Keep
public final class JsAdapter {
    private static final ConcurrentHashMap<String, Class<? extends vz>> sJsActionClsHashMap = new ConcurrentHashMap<>();
    private static final IJsActionLoader sJsActionLoader = ((IJsActionLoader) bqn.a(IJsActionLoader.class));
    private a mActionConfigurable;
    private HashMap<String, JsFunctionCallback> mAjxCallbackFunctionMap = new HashMap<>();
    public final wm mBaseWebView;
    private View mBtnRight;
    private PageBundle mBundle;
    private String mDefaultCallback = "callback";
    private ArrayList<b> mGoBackListeners;
    private final ConcurrentHashMap<String, vz> mJsActionHashMap = new ConcurrentHashMap<>();
    public bid mPageContext;
    private wc mTimeTost;
    private String mTriggerFunction = "";
    public vy mViewLayer;

    public interface a {
        String a();

        boolean b();
    }

    public interface b {
        void a();
    }

    public JsAdapter(bid bid, defpackage.wk.a aVar) {
        this.mPageContext = bid;
        this.mBaseWebView = new wk(aVar);
    }

    public JsAdapter(bid bid, MultiTabWebView multiTabWebView) {
        this.mPageContext = bid;
        this.mBaseWebView = new wn(multiTabWebView, this);
    }

    public JsAdapter(bid bid, MultiTabWebView multiTabWebView, vy vyVar) {
        this.mPageContext = bid;
        this.mBaseWebView = new wn(multiTabWebView, this);
        this.mViewLayer = vyVar;
    }

    public JsAdapter(bid bid) {
        this.mPageContext = bid;
        this.mBaseWebView = new wl(this);
    }

    public final void sendAjx(@Nullable String str, @Nullable JsFunctionCallback jsFunctionCallback) {
        JSONObject jSONObject;
        try {
            jSONObject = new JSONObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
            jSONObject = null;
        }
        sendAjx(jSONObject, jsFunctionCallback);
    }

    public final void sendAjx(@Nullable JSONObject jSONObject, @Nullable JsFunctionCallback jsFunctionCallback) {
        if (this.mPageContext != null && jSONObject != null) {
            try {
                String optString = jSONObject.optString("action");
                String optString2 = jSONObject.optString("_action");
                if (optString2.equals("")) {
                    optString2 = optString;
                }
                if (TextUtils.isEmpty(optString)) {
                    optString = jSONObject.optString("_action", "");
                }
                if (optString.equals("webviewGoBack")) {
                    onClickBack();
                    return;
                }
                StringBuilder sb = new StringBuilder();
                sb.append(optString2);
                sb.append("_timestamp_");
                sb.append(System.currentTimeMillis());
                String sb2 = sb.toString();
                this.mAjxCallbackFunctionMap.put(sb2, jsFunctionCallback);
                wa waVar = new wa();
                waVar.a = sb2;
                waVar.b = sb2;
                dispatchAction(jSONObject, optString, waVar);
            } catch (Throwable th) {
                kf.a(th);
            }
        }
    }

    public final void send(String[] strArr) {
        if (this.mPageContext != null && strArr.length > 0) {
            String str = this.mDefaultCallback;
            if (strArr.length == 2) {
                String str2 = strArr[1];
                if (!TextUtils.isEmpty(str2)) {
                    str = str2;
                }
            }
            try {
                JSONObject jSONObject = new JSONObject(strArr[0]);
                String optString = jSONObject.optString("action");
                String optString2 = jSONObject.optString("_action");
                if (optString2.equals("")) {
                    optString2 = optString;
                }
                if (TextUtils.isEmpty(optString)) {
                    optString = jSONObject.optString("_action", "");
                }
                if (optString.equals("webviewGoBack")) {
                    int optInt = jSONObject.optInt("step", 1);
                    if (!this.mBaseWebView.b() || optInt == 1) {
                        onClickBack();
                    } else {
                        this.mBaseWebView.a(optInt);
                    }
                } else {
                    wa waVar = new wa();
                    waVar.a = str;
                    waVar.b = optString2;
                    if (wi.a(this.mBaseWebView, optString)) {
                        dispatchAction(jSONObject, optString, waVar);
                    }
                }
            } catch (IllegalAccessException | InstantiationException | JSONException e) {
                kf.a(e);
            }
        }
    }

    public final void callJs(String str, String str2) {
        this.mBaseWebView.a(str, str2);
    }

    public final void setRightBtn(View view) {
        this.mBtnRight = view;
    }

    public final boolean doRightBtnFunction() {
        if (this.mActionConfigurable != null) {
            return this.mActionConfigurable.b();
        }
        return false;
    }

    public final boolean onKeyBackPressed() {
        if (TextUtils.isEmpty(this.mTriggerFunction)) {
            return false;
        }
        callJs(this.mTriggerFunction, "");
        return true;
    }

    public final void addGoBackListener(b bVar) {
        if (this.mGoBackListeners == null) {
            this.mGoBackListeners = new ArrayList<>();
        }
        this.mGoBackListeners.add(bVar);
    }

    public final void setDefaultCallback(String str) {
        this.mDefaultCallback = str;
    }

    public static void registerGlobalJsAction(String str, Class<? extends vz> cls) {
        sJsActionClsHashMap.put(str, cls);
    }

    public final void registerJsAction(String str, vz vzVar) {
        this.mJsActionHashMap.put(str, vzVar);
    }

    public final void setActionConfigurable(a aVar) {
        this.mActionConfigurable = aVar;
        if (TextUtils.isEmpty(this.mActionConfigurable.a())) {
            if (this.mBtnRight != null) {
                this.mBtnRight.setVisibility(4);
            }
        } else if (this.mBtnRight instanceof TextView) {
            ((TextView) this.mBtnRight).setText(this.mActionConfigurable.a());
            this.mBtnRight.setVisibility(0);
        }
    }

    public final void setTriggerFunction(String str) {
        this.mTriggerFunction = str;
    }

    public final void showTimeToast(String str) {
        if (this.mTimeTost != null) {
            this.mTimeTost.a();
        }
        Application application = AMapAppGlobal.getApplication();
        wc wcVar = new wc(application);
        LinearLayout linearLayout = new LinearLayout(application);
        TextView textView = new TextView(application);
        textView.setText(str);
        textView.setTextColor(-1);
        textView.setGravity(17);
        linearLayout.setBackgroundColor(Color.argb(191, 0, 0, 0));
        linearLayout.addView(textView, application.getResources().getDisplayMetrics().widthPixels / 2, application.getResources().getDisplayMetrics().widthPixels / 10);
        wcVar.i = linearLayout;
        this.mTimeTost = wcVar;
        wc wcVar2 = this.mTimeTost;
        wcVar2.a.post(wcVar2.k);
    }

    public final void closeTimeToast() {
        if (this.mTimeTost != null) {
            this.mTimeTost.a();
        }
    }

    public final void setBundle(PageBundle pageBundle) {
        this.mBundle = pageBundle;
    }

    public final PageBundle getBundle() {
        if (this.mBundle == null) {
            this.mBundle = new PageBundle();
        }
        return this.mBundle;
    }

    public final HashMap<String, JsFunctionCallback> getAjxCallbackMap() {
        return this.mAjxCallbackFunctionMap;
    }

    public final void onDestory() {
        this.mPageContext = null;
        if (this.mBtnRight != null) {
            this.mBtnRight.setOnClickListener(null);
            this.mBtnRight = null;
        }
    }

    private void onClickBack() {
        InputMethodManager inputMethodManager = (InputMethodManager) AMapAppGlobal.getApplication().getSystemService("input_method");
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(this.mPageContext.getContentView().getApplicationWindowToken(), 0);
        }
        if (this.mGoBackListeners != null && this.mGoBackListeners.size() > 0) {
            Iterator<b> it = this.mGoBackListeners.iterator();
            while (it.hasNext()) {
                it.next().a();
            }
        }
        if (!this.mBaseWebView.a()) {
            closeTimeToast();
            if (this.mPageContext != null) {
                this.mPageContext.finish();
            }
        }
    }

    private void dispatchAction(@Nullable JSONObject jSONObject, String str, wa waVar) throws JSONException, InstantiationException, IllegalAccessException {
        vz vzVar = this.mJsActionHashMap.get(str);
        if (vzVar != null) {
            vzVar.a(this);
            vzVar.a(jSONObject, waVar);
            return;
        }
        Class cls = sJsActionClsHashMap.get(str);
        if (cls == null) {
            cls = sJsActionLoader.getJsAction(str);
        }
        if (cls != null) {
            Object newInstance = cls.newInstance();
            if (newInstance instanceof vz) {
                vz vzVar2 = (vz) newInstance;
                vzVar2.a(this);
                vzVar2.a(jSONObject, waVar);
            }
        } else if (DebugLog.isDebug()) {
            throw new RuntimeException("Not Found JsAction: ".concat(String.valueOf(str)));
        }
    }
}
