package com.autonavi.minimap.intent;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.net.Uri;
import android.text.TextUtils;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.network.request.param.NetworkParam;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.PageBundle;
import com.autonavi.common.utils.Constant;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.R;
import com.autonavi.minimap.widget.ConfirmDlgLifeCircle;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public abstract class BaseIntentDispatcher {
    public static final String HOST_OPENFEATURE = "openFeature";
    public static final String INTENT_CALL_APP_XIAOMI = "GDSmallA";
    public static final String INTENT_CALL_DIRCTJUMP = "dirctjump";
    public static final String INTENT_CALL_FROMOWNER = "from_owner";
    public static final String INTENT_CALL_HOTWORD = "hotword";
    public static final String INTENT_CALL_OWNER_BANNER = "banner";
    public static final String INTENT_CALL_OWNER_GEOFENCE = "geofence";
    public static final String INTENT_CALL_OWNER_JS = "js";
    public static final String INTENT_CALL_OWNER_UMENG_PUSH = "umengPush";
    public static final String INTENT_CALL_SPLASH = "splash";
    protected static final String PAGE_PARAMS_MY_WEALTH = "Fortune";
    protected static final String PAGE_PARAMS_TOOL_BOX = "ToolBox";
    protected static final String PARAMS_MINE = "Mine";
    protected static final String PARAMS_RIDE = "Ride";
    protected static final String PARAMS_RUN = "Run";
    public boolean mUriCancleTask = false;
    private ProgressDlg mUriProgressDialog;

    public bid getPageContext() {
        return AMapPageUtil.getPageContext();
    }

    public final void startPage(Class<? extends AbstractBasePage> cls, PageBundle pageBundle) {
        startPageForResult(cls, pageBundle, -1);
    }

    public void startPage(String str, PageBundle pageBundle) {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            pageContext.startPage(str, pageBundle);
        }
    }

    public final void startPageForResult(Class<? extends AbstractBasePage> cls, PageBundle pageBundle, int i) {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            pageContext.startPageForResult(cls, pageBundle, i);
        }
    }

    /* access modifiers changed from: protected */
    public void removeAllFragmentsWithoutRoot() {
        try {
            bid pageContext = AMapPageUtil.getPageContext();
            if (pageContext != null) {
                pageContext.startPage((String) "amap.basemap.action.default_page", (PageBundle) null);
            }
            ConfirmDlgLifeCircle.removeAll();
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append(e.getMessage());
            AMapLog.e("BaseIntentDispatcher", sb.toString());
        }
    }

    /* access modifiers changed from: protected */
    public dlg getBackSchemeHole(Uri uri) {
        if (uri == null) {
            return null;
        }
        String queryParameter = uri.getQueryParameter("backScheme");
        Uri parse = !TextUtils.isEmpty(queryParameter) ? Uri.parse(queryParameter) : null;
        String queryParameter2 = uri.getQueryParameter(DriveUtil.SOURCE_APPLICATION);
        String queryParameter3 = uri.getQueryParameter("backCategory");
        String queryParameter4 = uri.getQueryParameter("backAction");
        if (TextUtils.isEmpty(queryParameter2)) {
            queryParameter2 = AMapAppGlobal.getApplication().getString(R.string.third_part);
        }
        if (TextUtils.isEmpty(queryParameter3)) {
            queryParameter3 = "android.intent.category.DEFAULT";
        }
        if (parse == null || TextUtils.isEmpty(queryParameter3)) {
            return null;
        }
        dlg dlg = new dlg();
        dlg.a = true;
        dlg.b = parse;
        dlg.c = queryParameter2;
        dlg.d = queryParameter3;
        dlg.e = queryParameter4;
        return dlg;
    }

    /* access modifiers changed from: protected */
    public boolean isFromSina() {
        return NetworkParam.getSa() != null && NetworkParam.getSa().equals(Constant.SOURCE_SINA);
    }

    /* access modifiers changed from: protected */
    public void showUriProgressDialog(String str) {
        try {
            if (this.mUriProgressDialog == null) {
                this.mUriProgressDialog = new ProgressDlg(AMapAppGlobal.getTopActivity(), str, "");
                this.mUriProgressDialog.setCancelable(true);
                this.mUriProgressDialog.setOnCancelListener(new OnCancelListener() {
                    public final void onCancel(DialogInterface dialogInterface) {
                        BaseIntentDispatcher.this.mUriCancleTask = true;
                    }
                });
            }
            this.mUriCancleTask = false;
            this.mUriProgressDialog.setMessage(str);
            if (!this.mUriProgressDialog.isShowing()) {
                this.mUriProgressDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dismissUriProgressDialog() {
        if (this.mUriProgressDialog != null) {
            this.mUriProgressDialog.dismiss();
            this.mUriProgressDialog = null;
        }
    }
}
