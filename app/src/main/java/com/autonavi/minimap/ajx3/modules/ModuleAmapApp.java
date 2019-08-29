package com.autonavi.minimap.ajx3.modules;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.alibaba.wireless.security.SecExceptionCode;
import com.alipay.mobile.tinyappcommon.utils.WalletTinyappUtils;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageFramework;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleApp;
import com.autonavi.minimap.ajx3.platform.ackor.AjxFileInfo;
import com.autonavi.minimap.ajx3.util.RomUtil;
import com.autonavi.minimap.ajx3.widget.view.toast.AjxToast;
import com.autonavi.minimap.vui.IVUIManager;
import com.autonavi.widget.ui.AlertView;
import com.autonavi.widget.ui.AlertView.a;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@AjxModule("ajx.app")
public class ModuleAmapApp extends AjxModuleApp {
    public static final int ALIPAY_INSTALLED = 1;
    public static final int ALIPAY_UNINSTALLED = 0;
    private static final int BACK_KEY_INDEX = -1;
    private static ForegroundColorSpan BLACK_BUTTON_TEXT = null;
    private static ForegroundColorSpan BLUE_BUTTON_TEXT = null;
    private static AbsoluteSizeSpan BUTTON_TEXT_SIZE = null;
    private static final int CANCEL_BUTTON_INDEX = 0;
    public static final String MODULE_NAME = "ajx.app";
    private static final int MSG_ALERT = 3;
    private static final int MSG_DISMISS_PROGRESS = 2;
    private static final int MSG_SHOW_PROGRESS = 1;
    private static final int MSG_TOAST = 0;
    private static final int OK_BUTTON_INDEX = 1;
    private static ForegroundColorSpan RED_BUTTON_TEXT;
    private LoadingDialog dialog;
    private b mActivityCreateAndDestroyListener;
    private f mActivityStartAndStopListener;
    private Map<WeakReference<AlertView>, WeakReference<bid>> mAlertViewMap = new HashMap();
    private JsFunctionCallback mCallback;
    Dialog miniappDialog;
    private boolean showListButtons = false;
    private Handler uiMessenger;

    static class LoadingDialog {
        private ProgressDlg mDialog;

        LoadingDialog(Context context) {
            this.mDialog = new ProgressDlg((Activity) context);
            this.mDialog.getLoadingView().setCloseIconVisibility(0);
            this.mDialog.setCanceledOnTouchOutside(false);
        }

        public void updateCancelable(boolean z) {
            if (z) {
                this.mDialog.getLoadingView().setCloseIconVisibility(0);
                this.mDialog.setOnKeyListener(null);
                return;
            }
            this.mDialog.getLoadingView().setCloseIconVisibility(8);
            this.mDialog.setOnKeyListener(new OnKeyListener() {
                public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                    return i == 4;
                }
            });
        }

        public void setMessage(String str) {
            if (this.mDialog != null) {
                this.mDialog.setMessage(str);
            }
        }

        /* access modifiers changed from: 0000 */
        public void setOnCancelListener(OnCancelListener onCancelListener) {
            if (this.mDialog != null) {
                this.mDialog.setOnCancelListener(onCancelListener);
            }
        }

        /* access modifiers changed from: 0000 */
        public void setOnCloseClickListener(@Nullable final OnClickListener onClickListener) {
            if (this.mDialog != null) {
                this.mDialog.getLoadingView().setOnCloseClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        if (onClickListener != null) {
                            onClickListener.onClick(view);
                        }
                        LoadingDialog.this.dismiss();
                    }
                });
            }
        }

        /* access modifiers changed from: 0000 */
        public void show() {
            if (this.mDialog != null) {
                this.mDialog.show();
            }
        }

        /* access modifiers changed from: 0000 */
        public void dismiss() {
            if (this.mDialog != null && this.mDialog.isShowing()) {
                this.mDialog.dismiss();
            }
        }
    }

    static class LoadingDialogCancelListener implements OnCancelListener {
        private final WeakReference<JsFunctionCallback> mJsRef;

        LoadingDialogCancelListener(JsFunctionCallback jsFunctionCallback) {
            this.mJsRef = new WeakReference<>(jsFunctionCallback);
        }

        public void onCancel(DialogInterface dialogInterface) {
            JsFunctionCallback jsFunctionCallback = (JsFunctionCallback) this.mJsRef.get();
            if (jsFunctionCallback != null) {
                jsFunctionCallback.callback(new Object[0]);
            }
        }
    }

    static class LoadingDialogCloseListener implements OnClickListener {
        private final WeakReference<JsFunctionCallback> mJsRef;

        public LoadingDialogCloseListener(JsFunctionCallback jsFunctionCallback) {
            this.mJsRef = new WeakReference<>(jsFunctionCallback);
        }

        public void onClick(View view) {
            JsFunctionCallback jsFunctionCallback = (JsFunctionCallback) this.mJsRef.get();
            if (jsFunctionCallback != null) {
                jsFunctionCallback.callback(new Object[0]);
            }
        }
    }

    @AjxMethod(invokeMode = "sync", value = "handleQrCode")
    public boolean handleQrCode(String str) {
        return false;
    }

    @AjxMethod("log")
    @SuppressFBWarnings({"UC_USELESS_VOID_METHOD"})
    public void log(String str) {
        if (str != null) {
        }
    }

    public ModuleAmapApp(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    /* access modifiers changed from: private */
    public void removeAlertView(AlertView alertView, boolean z) {
        Iterator<Entry<WeakReference<AlertView>, WeakReference<bid>>> it = this.mAlertViewMap.entrySet().iterator();
        WeakReference weakReference = null;
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Entry next = it.next();
            WeakReference weakReference2 = (WeakReference) next.getKey();
            if (weakReference2.get() == alertView) {
                if (z) {
                    bid bid = (bid) ((WeakReference) next.getValue()).get();
                    if (!(alertView == null || bid == null)) {
                        bid.dismissViewLayer(alertView);
                    }
                }
                weakReference = weakReference2;
            } else {
                weakReference = weakReference2;
            }
        }
        if (weakReference != null) {
            this.mAlertViewMap.remove(weakReference);
        }
    }

    private void createNewAlert(JSONObject jSONObject, a aVar, final JsFunctionCallback jsFunctionCallback, boolean z) throws JSONException {
        JSONArray jSONArray = jSONObject.getJSONArray("buttonTypes");
        JSONArray jSONArray2 = jSONObject.getJSONArray("buttons");
        int length = jSONArray2.length();
        aVar.c = new a() {
            public void onClick(AlertView alertView, int i) {
                if (alertView.isDismiss()) {
                    ModuleAmapApp.this.removeAlertView(alertView, false);
                }
                if (jsFunctionCallback != null) {
                    jsFunctionCallback.callback(Integer.valueOf(-1));
                }
            }
        };
        if (length > 2) {
            createListButtons(jSONArray2, jSONArray, aVar, jsFunctionCallback, z);
        } else {
            createNormalButtons(jSONArray2, jSONArray, aVar, jsFunctionCallback, z);
        }
    }

    private void createNormalButtons(JSONArray jSONArray, JSONArray jSONArray2, a aVar, final JsFunctionCallback jsFunctionCallback, final boolean z) throws JSONException {
        int length = jSONArray.length();
        AnonymousClass2 r1 = new a() {
            public void onClick(AlertView alertView, int i) {
                int i2 = 1;
                ModuleAmapApp.this.removeAlertView(alertView, true);
                if (jsFunctionCallback != null) {
                    JsFunctionCallback jsFunctionCallback = jsFunctionCallback;
                    Object[] objArr = new Object[1];
                    if (i == -2) {
                        i2 = 0;
                    }
                    objArr[0] = Integer.valueOf(i2);
                    jsFunctionCallback.callback(objArr);
                }
            }
        };
        for (final int i = 0; i < length; i++) {
            int parseInt = Integer.parseInt(jSONArray2.getString(i));
            CharSequence createFormatText = createFormatText(parseInt, jSONArray.getString(i));
            if (i == 0) {
                aVar.b(createFormatText, (a) r1);
            } else {
                aVar.a(createFormatText, (a) r1);
            }
            if (parseInt == 2) {
                aVar.c = new a() {
                    public void onClick(AlertView alertView, int i) {
                        if (alertView.isDismiss()) {
                            ModuleAmapApp.this.removeAlertView(alertView, false);
                        }
                        if (jsFunctionCallback != null) {
                            JsFunctionCallback jsFunctionCallback = jsFunctionCallback;
                            Object[] objArr = new Object[1];
                            objArr[0] = Integer.valueOf(z ? i : -1);
                            jsFunctionCallback.callback(objArr);
                        }
                    }
                };
            }
        }
    }

    private void createListButtons(JSONArray jSONArray, JSONArray jSONArray2, a aVar, final JsFunctionCallback jsFunctionCallback, boolean z) throws JSONException {
        int length = jSONArray.length();
        CharSequence[] charSequenceArr = new CharSequence[length];
        for (int i = 0; i < length; i++) {
            charSequenceArr[i] = createFormatText(Integer.parseInt(jSONArray2.getString(i)), jSONArray.getString(i));
        }
        aVar.a(charSequenceArr, (a) new a() {
            public void onClick(AlertView alertView, int i) {
                ModuleAmapApp.this.removeAlertView(alertView, true);
                if (jsFunctionCallback != null) {
                    jsFunctionCallback.callback(Integer.valueOf(i));
                }
            }
        });
        this.showListButtons = true;
    }

    private CharSequence createFormatText(int i, CharSequence charSequence) {
        SpannableString spannableString = new SpannableString(charSequence);
        if (BUTTON_TEXT_SIZE == null) {
            BUTTON_TEXT_SIZE = new AbsoluteSizeSpan(getNativeContext().getResources().getDimensionPixelOffset(R.dimen.f_s_17));
        }
        spannableString.setSpan(BUTTON_TEXT_SIZE, 0, charSequence.length(), 33);
        switch (i) {
            case 0:
                if (BLACK_BUTTON_TEXT == null) {
                    BLACK_BUTTON_TEXT = new ForegroundColorSpan(getNativeContext().getResources().getColor(R.color.f_c_2));
                }
                spannableString.setSpan(BLACK_BUTTON_TEXT, 0, charSequence.length(), 33);
                break;
            case 1:
                if (BLUE_BUTTON_TEXT == null) {
                    BLUE_BUTTON_TEXT = new ForegroundColorSpan(getNativeContext().getResources().getColor(R.color.f_c_6));
                }
                spannableString.setSpan(BLUE_BUTTON_TEXT, 0, charSequence.length(), 33);
                break;
            case 2:
                if (RED_BUTTON_TEXT == null) {
                    RED_BUTTON_TEXT = new ForegroundColorSpan(getNativeContext().getResources().getColor(R.color.f_c_8));
                }
                spannableString.setSpan(RED_BUTTON_TEXT, 0, charSequence.length(), 33);
                break;
        }
        return spannableString;
    }

    private void createOldAlert(JSONObject jSONObject, a aVar, final JsFunctionCallback jsFunctionCallback, final boolean z) throws JSONException {
        CharSequence charSequence;
        JSONArray jSONArray = jSONObject.getJSONArray("buttons");
        String str = null;
        if (jSONArray.length() == 1) {
            str = jSONArray.getString(0);
            charSequence = null;
        } else if (jSONArray.length() > 1) {
            str = jSONArray.getString(0);
            charSequence = jSONArray.getString(1);
        } else {
            charSequence = null;
        }
        if (!TextUtils.isEmpty(str)) {
            aVar.b((CharSequence) str, (a) new a() {
                public void onClick(AlertView alertView, int i) {
                    ModuleAmapApp.this.removeAlertView(alertView, true);
                    if (jsFunctionCallback != null) {
                        jsFunctionCallback.callback(Integer.valueOf(0));
                    }
                }
            });
        }
        if (!TextUtils.isEmpty(charSequence)) {
            aVar.a(charSequence, (a) new a() {
                public void onClick(AlertView alertView, int i) {
                    ModuleAmapApp.this.removeAlertView(alertView, true);
                    if (jsFunctionCallback != null) {
                        jsFunctionCallback.callback(Integer.valueOf(1));
                    }
                }
            });
        }
        aVar.c = new a() {
            public void onClick(AlertView alertView, int i) {
                if (alertView.isDismiss()) {
                    ModuleAmapApp.this.removeAlertView(alertView, false);
                }
                if (jsFunctionCallback != null) {
                    JsFunctionCallback jsFunctionCallback = jsFunctionCallback;
                    Object[] objArr = new Object[1];
                    objArr[0] = Integer.valueOf(z ? 0 : -1);
                    jsFunctionCallback.callback(objArr);
                }
            }
        };
    }

    @AjxMethod("toast")
    public void toast(String str, String str2) {
        boolean z;
        Context nativeContext;
        if (!(str == null || getNativeContext() == null)) {
            int i = SecExceptionCode.SEC_ERROR_SIMULATORDETECT;
            int i2 = 0;
            if (str2 != null) {
                try {
                    JSONObject jSONObject = new JSONObject(str2);
                    int optInt = jSONObject.optInt("time");
                    try {
                        boolean optBoolean = jSONObject.optBoolean("aboveKeyboard", false);
                        try {
                            i2 = jSONObject.optInt("type", 0);
                            boolean z2 = optBoolean;
                            i = optInt;
                            z = z2;
                        } catch (JSONException e) {
                            e = e;
                            boolean z3 = optBoolean;
                            i = optInt;
                            z = z3;
                            e.printStackTrace();
                            nativeContext = getNativeContext();
                            nativeContext = nativeContext.getApplicationContext();
                            AjxToast.make(nativeContext).setAboveKeyboard(z).setText(str).setDuration((long) i).show();
                        }
                    } catch (JSONException e2) {
                        e = e2;
                        i = optInt;
                        z = false;
                        e.printStackTrace();
                        nativeContext = getNativeContext();
                        nativeContext = nativeContext.getApplicationContext();
                        AjxToast.make(nativeContext).setAboveKeyboard(z).setText(str).setDuration((long) i).show();
                    }
                } catch (JSONException e3) {
                    e = e3;
                    z = false;
                    e.printStackTrace();
                    nativeContext = getNativeContext();
                    nativeContext = nativeContext.getApplicationContext();
                    AjxToast.make(nativeContext).setAboveKeyboard(z).setText(str).setDuration((long) i).show();
                }
            } else {
                z = false;
            }
            nativeContext = getNativeContext();
            if (i2 == 1 && RomUtil.isOppo() && nativeContext.getApplicationContext() != null) {
                nativeContext = nativeContext.getApplicationContext();
            }
            AjxToast.make(nativeContext).setAboveKeyboard(z).setText(str).setDuration((long) i).show();
        }
    }

    @AjxMethod("showProgress")
    public void showProgress(String str, JsFunctionCallback jsFunctionCallback) {
        if (!TextUtils.isEmpty(str) && getNativeContext() != null) {
            if (this.dialog == null) {
                this.dialog = new LoadingDialog(DoNotUseTool.getActivity());
            }
            if (jsFunctionCallback != null) {
                this.dialog.setOnCancelListener(new LoadingDialogCancelListener(jsFunctionCallback));
                this.dialog.setOnCloseClickListener(new LoadingDialogCloseListener(jsFunctionCallback));
            } else {
                this.dialog.setOnCloseClickListener(null);
            }
            this.dialog.setMessage(str);
            this.dialog.updateCancelable(true);
            this.dialog.show();
            this.mCallback = jsFunctionCallback;
        }
    }

    @AjxMethod("showProgressNoCancel")
    public void showProgressNoCancel(String str, JsFunctionCallback jsFunctionCallback) {
        if (!TextUtils.isEmpty(str) && getNativeContext() != null) {
            if (this.dialog == null) {
                this.dialog = new LoadingDialog(DoNotUseTool.getActivity());
            }
            if (jsFunctionCallback != null) {
                this.dialog.setOnCancelListener(new LoadingDialogCancelListener(jsFunctionCallback));
                this.dialog.setOnCloseClickListener(new LoadingDialogCloseListener(jsFunctionCallback));
            } else {
                this.dialog.setOnCloseClickListener(null);
            }
            this.dialog.setMessage(str);
            this.dialog.updateCancelable(false);
            this.dialog.show();
            this.mCallback = jsFunctionCallback;
        }
    }

    public void onModuleDestroy() {
        super.onModuleDestroy();
        dismissProgress();
    }

    @AjxMethod("dismissProgress")
    public void dismissProgress() {
        if (this.dialog != null) {
            this.dialog.dismiss();
        }
    }

    @AjxMethod("alert")
    public void alert(String str, JsFunctionCallback jsFunctionCallback) {
        if (!TextUtils.isEmpty(str)) {
            JSONObject jSONObject = null;
            try {
                jSONObject = new JSONObject(str);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (jSONObject != null && getNativeContext() != null) {
                a aVar = new a(getNativeContext());
                String optString = jSONObject.optString("title");
                if (!TextUtils.isEmpty(optString)) {
                    aVar.a((CharSequence) optString);
                }
                String optString2 = jSONObject.optString("message");
                if (!TextUtils.isEmpty(optString2)) {
                    aVar.b((CharSequence) optString2);
                }
                boolean optBoolean = jSONObject.optBoolean("cancelAble", true);
                boolean z = false;
                boolean optBoolean2 = jSONObject.optBoolean("interruptBackEvent", false);
                aVar.a(true);
                aVar.b(optBoolean2);
                this.showListButtons = false;
                try {
                    if (jSONObject.has("buttonTypes")) {
                        if (optBoolean && !optBoolean2) {
                            z = true;
                        }
                        createNewAlert(jSONObject, aVar, jsFunctionCallback, z);
                    } else {
                        if (optBoolean && !optBoolean2) {
                            z = true;
                        }
                        createOldAlert(jSONObject, aVar, jsFunctionCallback, z);
                    }
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                bid pageContext = AMapPageFramework.getPageContext();
                if (pageContext != null) {
                    AlertView a = aVar.a();
                    this.mAlertViewMap.put(new WeakReference(a), new WeakReference(pageContext));
                    if (this.showListButtons) {
                        ((TextView) a.findViewById(R.id.alertTitle)).getPaint().setFakeBoldText(true);
                        a.findViewById(R.id.parentPanel).setBackgroundDrawable(getNativeContext().getResources().getDrawable(R.drawable.bg_alert));
                        a.findViewById(R.id.messageDivider).setVisibility(8);
                        ((LayoutParams) a.findViewById(R.id.alert_select_listview).getLayoutParams()).bottomMargin = -30;
                    }
                    pageContext.showViewLayer(a);
                    IVUIManager iVUIManager = (IVUIManager) ank.a(IVUIManager.class);
                    if (iVUIManager != null) {
                        iVUIManager.tryRestartListening();
                    }
                }
            }
        }
    }

    @AjxMethod("alertForMiniapp")
    public void alertForMiniapp(String str, final JsFunctionCallback jsFunctionCallback) {
        JSONObject jSONObject;
        Activity activity;
        CharSequence charSequence;
        if (!TextUtils.isEmpty(str)) {
            String str2 = null;
            try {
                jSONObject = new JSONObject(str);
            } catch (JSONException e) {
                e.printStackTrace();
                jSONObject = null;
            }
            if (jSONObject != null) {
                try {
                    activity = (Activity) getNativeContext();
                } catch (Exception unused) {
                    activity = null;
                }
                if (activity != null && !activity.isDestroyed() && !activity.isFinishing()) {
                    if (this.miniappDialog != null && this.miniappDialog.isShowing()) {
                        this.miniappDialog.dismiss();
                    }
                    a aVar = new a(activity);
                    String optString = jSONObject.optString("title");
                    if (!TextUtils.isEmpty(optString)) {
                        aVar.a((CharSequence) optString);
                    }
                    String optString2 = jSONObject.optString("message");
                    if (!TextUtils.isEmpty(optString2)) {
                        aVar.b((CharSequence) optString2);
                    }
                    jSONObject.optBoolean("cancelAble", true);
                    boolean optBoolean = jSONObject.optBoolean("interruptBackEvent", false);
                    aVar.a(true);
                    aVar.b(optBoolean);
                    aVar.c = new a() {
                        public void onClick(AlertView alertView, int i) {
                            if (jsFunctionCallback != null) {
                                jsFunctionCallback.callback(Integer.valueOf(-1));
                            }
                        }
                    };
                    try {
                        JSONArray jSONArray = jSONObject.getJSONArray("buttons");
                        if (jSONArray.length() == 1) {
                            String string = jSONArray.getString(0);
                            charSequence = null;
                            str2 = string;
                        } else if (jSONArray.length() > 1) {
                            str2 = jSONArray.getString(0);
                            charSequence = jSONArray.getString(1);
                        } else {
                            charSequence = null;
                        }
                        if (!TextUtils.isEmpty(str2)) {
                            aVar.b((CharSequence) str2, (a) new a() {
                                public void onClick(AlertView alertView, int i) {
                                    if (ModuleAmapApp.this.miniappDialog != null && ModuleAmapApp.this.miniappDialog.isShowing()) {
                                        ModuleAmapApp.this.miniappDialog.dismiss();
                                        ModuleAmapApp.this.miniappDialog = null;
                                    }
                                    if (jsFunctionCallback != null) {
                                        jsFunctionCallback.callback(Integer.valueOf(0));
                                    }
                                }
                            });
                        }
                        if (!TextUtils.isEmpty(charSequence)) {
                            aVar.a(charSequence, (a) new a() {
                                public void onClick(AlertView alertView, int i) {
                                    if (ModuleAmapApp.this.miniappDialog != null && ModuleAmapApp.this.miniappDialog.isShowing()) {
                                        ModuleAmapApp.this.miniappDialog.dismiss();
                                        ModuleAmapApp.this.miniappDialog = null;
                                    }
                                    if (jsFunctionCallback != null) {
                                        jsFunctionCallback.callback(Integer.valueOf(1));
                                    }
                                }
                            });
                        }
                        aVar.c = new a() {
                            public void onClick(AlertView alertView, int i) {
                                if (ModuleAmapApp.this.miniappDialog != null && ModuleAmapApp.this.miniappDialog.isShowing()) {
                                    ModuleAmapApp.this.miniappDialog.dismiss();
                                    ModuleAmapApp.this.miniappDialog = null;
                                }
                                if (jsFunctionCallback != null) {
                                    jsFunctionCallback.callback(Integer.valueOf(0));
                                }
                            }
                        };
                    } catch (JSONException unused2) {
                    }
                    this.miniappDialog = new Dialog(activity);
                    this.miniappDialog.requestWindowFeature(1);
                    this.miniappDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                    AlertView a = aVar.a();
                    a.setBackgroundColor(0);
                    this.miniappDialog.setContentView(a);
                    this.miniappDialog.show();
                }
            }
        }
    }

    @AjxMethod("dismissAlert")
    public void dismissAlert() {
        for (Entry next : this.mAlertViewMap.entrySet()) {
            WeakReference weakReference = (WeakReference) next.getKey();
            WeakReference weakReference2 = (WeakReference) next.getValue();
            if (!(weakReference == null || weakReference2 == null)) {
                AlertView alertView = (AlertView) weakReference.get();
                bid bid = (bid) weakReference2.get();
                if (!(alertView == null || bid == null)) {
                    bid.dismissViewLayer(alertView);
                }
            }
        }
        this.mAlertViewMap.clear();
    }

    @AjxMethod("isCameraGranted")
    public void isCameraGranted(JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            jsFunctionCallback.callback(Boolean.valueOf(kj.a(DoNotUseTool.getActivity(), new String[]{"android.permission.CAMERA"})));
        }
    }

    @AjxMethod("getUserPermission")
    public void getUserPermission(String str, JsFunctionCallback jsFunctionCallback) {
        if (str != null && jsFunctionCallback != null) {
            char c = 65535;
            switch (str.hashCode()) {
                case -1367751899:
                    if (str.equals(WalletTinyappUtils.CONST_SCOPE_CAMERA)) {
                        c = 0;
                        break;
                    }
                    break;
                case -1060266576:
                    if (str.equals("callPhone")) {
                        c = 3;
                        break;
                    }
                    break;
                case 106642994:
                    if (str.equals("photo")) {
                        c = 4;
                        break;
                    }
                    break;
                case 827111836:
                    if (str.equals("writeStorage")) {
                        c = 2;
                        break;
                    }
                    break;
                case 1289767429:
                    if (str.equals("recordAudio")) {
                        c = 5;
                        break;
                    }
                    break;
                case 1671277573:
                    if (str.equals("readStorage")) {
                        c = 1;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    dealUserPermission("android.permission.CAMERA", jsFunctionCallback);
                    return;
                case 1:
                    if (VERSION.SDK_INT >= 16) {
                        dealUserPermission("android.permission.READ_EXTERNAL_STORAGE", jsFunctionCallback);
                        return;
                    }
                    jsFunctionCallback.callback(Boolean.TRUE);
                    return;
                case 2:
                    dealUserPermission("android.permission.WRITE_EXTERNAL_STORAGE", jsFunctionCallback);
                    return;
                case 3:
                    dealUserPermission("android.permission.CALL_PHONE", jsFunctionCallback);
                    return;
                case 4:
                    dealUserPermission("android.permission.WRITE_EXTERNAL_STORAGE", jsFunctionCallback);
                    return;
                case 5:
                    dealUserPermission("android.permission.RECORD_AUDIO", jsFunctionCallback);
                    break;
            }
        }
    }

    private void dealUserPermission(final String str, final JsFunctionCallback jsFunctionCallback) {
        if (!(ContextCompat.checkSelfPermission(getNativeContext(), str) == 0)) {
            kj.a(DoNotUseTool.getActivity(), new String[]{str}, (b) new b() {
                public void run() {
                    super.run();
                    jsFunctionCallback.callback(Boolean.TRUE, Boolean.TRUE);
                }

                public void reject() {
                    super.reject();
                    jsFunctionCallback.callback(Boolean.FALSE, Boolean.valueOf(VERSION.SDK_INT >= 23 ? DoNotUseTool.getActivity().shouldShowRequestPermissionRationale(str) : false));
                }
            });
        } else {
            jsFunctionCallback.callback(Boolean.TRUE, Boolean.FALSE);
        }
    }

    @AjxMethod("openSettingsPage")
    public void openSettingsPage() {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", DoNotUseTool.getActivity().getPackageName(), null));
        DoNotUseTool.getActivity().startActivityForResult(intent, 1);
    }

    @AjxMethod("openPermissionsPage")
    public void openAmapPermissions() {
        kj.e(DoNotUseTool.getActivity());
    }

    @AjxMethod(invokeMode = "sync", value = "getInstalledTime")
    public long getInstalledTime() {
        try {
            Application application = AMapAppGlobal.getApplication();
            PackageInfo packageInfo = application.getPackageManager().getPackageInfo(application.getPackageName(), 0);
            if (VERSION.SDK_INT >= 9) {
                return packageInfo.lastUpdateTime;
            }
            ApplicationInfo applicationInfo = application.getPackageManager().getApplicationInfo(application.getPackageName(), 0);
            if (applicationInfo != null) {
                return new File(applicationInfo.sourceDir).lastModified();
            }
            return -1;
        } catch (NameNotFoundException | RuntimeException e) {
            e.printStackTrace();
        }
    }

    @AjxMethod(invokeMode = "sync", value = "getAJXBundleVersion")
    public String getAJXBundleVersion(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        String loadedDiffAjxFileVersion = AjxFileInfo.getLoadedDiffAjxFileVersion(str);
        String baseAjxFileVersion = AjxFileInfo.getBaseAjxFileVersion(str);
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("baseVersion", baseAjxFileVersion);
            jSONObject.put("latestPatchVersion", loadedDiffAjxFileVersion);
        } catch (JSONException unused) {
        }
        return jSONObject.toString();
    }

    @AjxMethod(invokeMode = "sync", value = "isAlipayInstalled")
    public int isAlipayInstalled() {
        return new Intent("android.intent.action.VIEW", Uri.parse("alipays://platformapi/startApp")).resolveActivity(AMapAppGlobal.getApplication().getPackageManager()) != null ? 1 : 0;
    }

    @AjxMethod(invokeMode = "sync", value = "getBundleConfigInfo")
    public String getBundleConfigInfo(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return "";
        }
        int patchIndex = getContext().getPatchIndex(str);
        if (patchIndex >= 0) {
            return AjxFileInfo.getBundleConfigInfo(str, patchIndex, str2);
        }
        return AjxFileInfo.getBundleConfigInfo(str, str2);
    }
}
