package com.autonavi.minimap.ajx3.modules.internalmodules;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.AjxConfig;
import com.autonavi.minimap.ajx3.R;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxField;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import com.autonavi.minimap.ajx3.platform.ackor.AjxFileInfo;
import com.uc.webview.export.WebView;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

@AjxModule("ajx.app")
public class AjxModuleApp extends AbstractModule {
    private static final int CANCEL_BUTTON_INDEX = 0;
    public static final String MODULE_NAME = "ajx.app";
    private static final int MSG_ALERT = 3;
    private static final int MSG_DISMISS_PROGRESS = 2;
    private static final int MSG_SHOW_PROGRESS = 1;
    private static final int MSG_TOAST = 0;
    private static final int OK_BUTTON_INDEX = 1;
    private static final String TAG = "AjxModuleApp";
    @AjxField("buildType")
    public String buildType;
    private LoadingDialog dialog;
    private Map<String, Object> mMetaData;
    @AjxField("name")
    public String name = "ajx";
    @AjxField("version")
    public String version = "default";

    static class LoadingDialog {
        private Dialog mDialog;

        LoadingDialog(Context context) {
            if (this.mDialog == null) {
                View inflate = LayoutInflater.from(context).inflate(R.layout.ajx3_loading_dialog, new FrameLayout(context), false);
                this.mDialog = new Dialog(context, R.style.ajx_loading_dialog);
                this.mDialog.setCanceledOnTouchOutside(false);
                this.mDialog.setCancelable(true);
                this.mDialog.setContentView(inflate);
            }
        }

        public void setMessage(String str) {
            if (this.mDialog != null) {
                ((TextView) this.mDialog.findViewById(R.id.msg)).setText(str);
            }
        }

        /* access modifiers changed from: 0000 */
        public void setOnCancelListener(OnCancelListener onCancelListener) {
            if (this.mDialog != null) {
                this.mDialog.setOnCancelListener(onCancelListener);
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

    @AjxMethod("log")
    public void log(String str) {
        if (str != null) {
        }
    }

    public AjxModuleApp(IAjxContext iAjxContext) {
        super(iAjxContext);
        try {
            Context nativeContext = getNativeContext();
            this.name = nativeContext.getPackageManager().getPackageInfo(nativeContext.getPackageName(), 0).applicationInfo.loadLabel(nativeContext.getPackageManager()).toString();
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        AjxConfig ajxConfig = iAjxContext.getAjxConfig();
        this.version = ajxConfig.getAppVersion();
        this.buildType = ajxConfig.getAppBuildType();
        this.mMetaData = new HashMap();
        this.mMetaData.put("ajx_base_version", AjxFileInfo.getAllAjxFileBaseVersion());
        this.mMetaData.put("ajx_patch_version", AjxFileInfo.getAllAjxLatestPatchVersion());
        this.mMetaData.put("ajx_engine_version", Ajx.getInstance().getAjxEngineVersion());
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0030  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0045  */
    @com.autonavi.minimap.ajx3.modules.AjxMethod("toast")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void toast(java.lang.String r6, java.lang.String r7) {
        /*
            r5 = this;
            if (r6 != 0) goto L_0x0003
            return
        L_0x0003:
            android.content.Context r0 = r5.getNativeContext()
            if (r0 == 0) goto L_0x0059
            r1 = 2000(0x7d0, float:2.803E-42)
            r2 = 0
            if (r7 == 0) goto L_0x002d
            org.json.JSONObject r3 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0029 }
            r3.<init>(r7)     // Catch:{ JSONException -> 0x0029 }
            java.lang.String r7 = "time"
            int r7 = r3.optInt(r7)     // Catch:{ JSONException -> 0x0029 }
            java.lang.String r1 = "type"
            int r1 = r3.optInt(r1, r2)     // Catch:{ JSONException -> 0x0024 }
            r2 = r1
            r1 = r7
            goto L_0x002d
        L_0x0024:
            r1 = move-exception
            r4 = r1
            r1 = r7
            r7 = r4
            goto L_0x002a
        L_0x0029:
            r7 = move-exception
        L_0x002a:
            r7.printStackTrace()
        L_0x002d:
            r7 = 1
            if (r2 != 0) goto L_0x0045
            com.autonavi.minimap.ajx3.widget.view.toast.IToast r0 = com.autonavi.minimap.ajx3.widget.view.toast.AjxToast.make(r0)
            com.autonavi.minimap.ajx3.widget.view.toast.IToast r7 = r0.setAboveKeyboard(r7)
            com.autonavi.minimap.ajx3.widget.view.toast.IToast r6 = r7.setText(r6)
            long r0 = (long) r1
            com.autonavi.minimap.ajx3.widget.view.toast.IToast r6 = r6.setDuration(r0)
            r6.show()
            return
        L_0x0045:
            com.autonavi.minimap.ajx3.widget.view.toast.IToast r0 = com.autonavi.minimap.ajx3.widget.view.toast.AjxToast.make(r0, r2)
            com.autonavi.minimap.ajx3.widget.view.toast.IToast r7 = r0.setAboveKeyboard(r7)
            com.autonavi.minimap.ajx3.widget.view.toast.IToast r6 = r7.setText(r6)
            long r0 = (long) r1
            com.autonavi.minimap.ajx3.widget.view.toast.IToast r6 = r6.setDuration(r0)
            r6.show()
        L_0x0059:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleApp.toast(java.lang.String, java.lang.String):void");
    }

    @AjxMethod("showProgress")
    public void showProgress(String str, JsFunctionCallback jsFunctionCallback) {
        if (!TextUtils.isEmpty(str)) {
            if (this.dialog == null) {
                this.dialog = new LoadingDialog(getNativeContext());
            }
            if (jsFunctionCallback != null) {
                this.dialog.setOnCancelListener(new LoadingDialogCancelListener(jsFunctionCallback));
            }
            this.dialog.setMessage(str);
            this.dialog.show();
        }
    }

    @AjxMethod("dismissProgress")
    public void dismissProgress() {
        if (this.dialog != null) {
            this.dialog.dismiss();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:35:0x0071  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x007f  */
    @com.autonavi.minimap.ajx3.modules.AjxMethod("alert")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void alert(java.lang.String r6, final com.autonavi.minimap.ajx3.core.JsFunctionCallback r7) {
        /*
            r5 = this;
            boolean r0 = android.text.TextUtils.isEmpty(r6)
            if (r0 == 0) goto L_0x0007
            return
        L_0x0007:
            r0 = 0
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x000e }
            r1.<init>(r6)     // Catch:{ JSONException -> 0x000e }
            goto L_0x0013
        L_0x000e:
            r6 = move-exception
            r6.printStackTrace()
            r1 = r0
        L_0x0013:
            if (r1 != 0) goto L_0x0016
            return
        L_0x0016:
            android.app.AlertDialog$Builder r6 = new android.app.AlertDialog$Builder
            android.content.Context r2 = r5.getNativeContext()
            r6.<init>(r2)
            java.lang.String r2 = "title"
            java.lang.String r2 = r1.optString(r2)
            boolean r3 = android.text.TextUtils.isEmpty(r2)
            if (r3 != 0) goto L_0x002f
            r6.setTitle(r2)
        L_0x002f:
            java.lang.String r2 = "message"
            java.lang.String r2 = r1.optString(r2)
            boolean r3 = android.text.TextUtils.isEmpty(r2)
            if (r3 != 0) goto L_0x003e
            r6.setMessage(r2)
        L_0x003e:
            java.lang.String r2 = "buttons"
            org.json.JSONArray r1 = r1.getJSONArray(r2)     // Catch:{ JSONException -> 0x0065 }
            int r2 = r1.length()     // Catch:{ JSONException -> 0x0065 }
            r3 = 0
            r4 = 1
            if (r2 != r4) goto L_0x0051
            java.lang.String r1 = r1.getString(r3)     // Catch:{ JSONException -> 0x0065 }
            goto L_0x006b
        L_0x0051:
            int r2 = r1.length()     // Catch:{ JSONException -> 0x0065 }
            if (r2 <= r4) goto L_0x0063
            java.lang.String r2 = r1.getString(r3)     // Catch:{ JSONException -> 0x0065 }
            java.lang.String r1 = r1.getString(r4)     // Catch:{ JSONException -> 0x0061 }
            r0 = r1
            goto L_0x006a
        L_0x0061:
            r1 = move-exception
            goto L_0x0067
        L_0x0063:
            r1 = r0
            goto L_0x006b
        L_0x0065:
            r1 = move-exception
            r2 = r0
        L_0x0067:
            r1.printStackTrace()
        L_0x006a:
            r1 = r2
        L_0x006b:
            boolean r2 = android.text.TextUtils.isEmpty(r1)
            if (r2 != 0) goto L_0x0079
            com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleApp$1 r2 = new com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleApp$1
            r2.<init>(r7)
            r6.setNegativeButton(r1, r2)
        L_0x0079:
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto L_0x0087
            com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleApp$2 r1 = new com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleApp$2
            r1.<init>(r7)
            r6.setPositiveButton(r0, r1)
        L_0x0087:
            android.app.AlertDialog r6 = r6.create()
            com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleApp$3 r0 = new com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleApp$3
            r0.<init>(r7)
            r6.setOnKeyListener(r0)
            r6.show()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleApp.alert(java.lang.String, com.autonavi.minimap.ajx3.core.JsFunctionCallback):void");
    }

    @AjxMethod("dial")
    public void dial(String str) {
        Intent intent = new Intent("android.intent.action.DIAL", Uri.parse(WebView.SCHEME_TEL.concat(String.valueOf(str))));
        Context nativeContext = getNativeContext();
        if (nativeContext != null) {
            try {
                nativeContext.startActivity(intent);
            } catch (Exception unused) {
            }
        }
    }

    @AjxMethod("getMetaData")
    public void getMetaData(JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            jsFunctionCallback.callback(new JSONObject(this.mMetaData));
        }
    }

    @AjxMethod("forceHideInputMethod")
    public boolean forceHideInputMethod() {
        InputMethodManager inputMethodManager = (InputMethodManager) getNativeContext().getSystemService("input_method");
        if (inputMethodManager == null) {
            return false;
        }
        try {
            return inputMethodManager.hideSoftInputFromWindow(getContext().getDomTree().getRootView().getWindowToken(), 0);
        } catch (Exception unused) {
            return false;
        }
    }
}
