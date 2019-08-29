package com.alipay.mobile.webar;

import android.app.Activity;
import android.content.Context;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alipay.android.phone.androidannotations.utils.PermissionUtils;
import com.alipay.mobile.antui.dialog.AUNoticeDialog;
import com.alipay.mobile.base.commonbiz.api.R;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.webar.PermissionDatabaseHelper.PERMISSION_TYPE;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

public class GeneralPermissionsManager {
    private static final long PERMISSION_TIME_LAST = 2592000000L;
    private static final String TAG = "GeneralPermissionsManager";
    private static GeneralPermissionsManager sInstance;
    /* access modifiers changed from: private */
    public PermissionDatabaseHelper mDatabaseHelper;

    public class AUNoticeDialogEx extends AUNoticeDialog {
        private boolean a = false;

        public AUNoticeDialogEx(Context context, CharSequence title, CharSequence msg, String positiveString, String negativeString) {
            super(context, title, msg, positiveString, negativeString, false);
        }

        public final boolean a() {
            return this.a;
        }

        public final void b() {
            this.a = true;
        }
    }

    public interface IGeneralPermissions {
        void onAllow();

        void onDeny();
    }

    enum PermissionStatus {
        ASK,
        DENY,
        GRANT
    }

    public static GeneralPermissionsManager getInstance() {
        if (sInstance == null) {
            synchronized (GeneralPermissionsManager.class) {
                if (sInstance == null) {
                    sInstance = new GeneralPermissionsManager();
                }
            }
        }
        return sInstance;
    }

    private GeneralPermissionsManager() {
        init();
    }

    private void init() {
        Context context = LauncherApplicationAgent.getInstance().getMicroApplicationContext().getApplicationContext();
        if (context != null) {
            this.mDatabaseHelper = new PermissionDatabaseHelper(context);
        }
    }

    private String getUrlIndetity(String url) {
        try {
            URL urlInWhiteList = new URL(url);
            String urlIdentity = urlInWhiteList.getProtocol() + "://" + urlInWhiteList.getHost();
            int port = urlInWhiteList.getPort();
            if (port >= 0) {
                return urlIdentity + ":" + port;
            }
            return urlIdentity;
        } catch (MalformedURLException e) {
            H5Log.e((String) TAG, "url format error:" + url);
            return null;
        }
    }

    private boolean checkWhiteList(String sUrlIndetity, JSONArray whiteListJsArray) {
        if (whiteListJsArray == null) {
            return false;
        }
        for (int i = 0; i < whiteListJsArray.size(); i++) {
            if (Pattern.compile(whiteListJsArray.getString(i)).matcher(sUrlIndetity).matches()) {
                return true;
            }
        }
        return false;
    }

    public void showGeneralPermissionsPrompt(Context context, IGeneralPermissions permissions, String sUrl, JSONArray whiteListJsArray) {
        if (permissions == null) {
            H5Log.e((String) TAG, (String) "permissions is null, showGeneralPermissionsPrompt fail");
        } else if (this.mDatabaseHelper != null) {
            this.mDatabaseHelper.a();
            if (!TextUtils.isEmpty(sUrl)) {
                String identify = getUrlIndetity(sUrl);
                if (!checkWhiteList(identify, whiteListJsArray)) {
                    H5Log.e((String) TAG, "sUrl not in white list, permissions deny, url indetity:" + identify);
                    permissions.onDeny();
                } else if (this.mDatabaseHelper.a(identify, PERMISSION_TYPE.CAMERA_PERMISSION)) {
                    permissions.onAllow();
                } else if (context != null) {
                    AUNoticeDialogEx dialog = new AUNoticeDialogEx(context, context.getString(R.string.webar_permission_camera_title), context.getString(R.string.webar_permission_camera_content), context.getString(R.string.webar_permission_camera_allow), context.getString(R.string.webar_permission_camera_deny));
                    dialog.setPositiveListener(new a(this, dialog));
                    dialog.setOnDismissListener(new b(this, dialog, identify, permissions));
                    dialog.show();
                } else {
                    H5Log.e((String) TAG, (String) "context is null, show showGeneralPermissionsPrompt error");
                    permissions.onDeny();
                }
            } else {
                H5Log.e((String) TAG, (String) "sUrl is null, permissions deny");
                permissions.onDeny();
            }
        } else {
            H5Log.e((String) TAG, (String) "mDatabaseHelper is null, permissions deny");
            permissions.onDeny();
        }
    }

    public boolean onCheckSelfPermission(Context context, String permission) {
        int targetSdkVersion = 23;
        try {
            targetSdkVersion = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).applicationInfo.targetSdkVersion;
        } catch (Throwable e) {
            H5Log.e(TAG, "get PackageInfo fail:", e);
        }
        if (VERSION.SDK_INT < 23) {
            return true;
        }
        if (targetSdkVersion < 23) {
            return PermissionUtils.hasSelfPermissions(context, permission);
        } else if (context.checkSelfPermission(permission) == 0) {
            return true;
        } else {
            return false;
        }
    }

    public int onQueryCameraAuthorizationStatus(Context context) {
        PermissionStatus status = PermissionStatus.DENY;
        int targetSdkVersion = 23;
        try {
            targetSdkVersion = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).applicationInfo.targetSdkVersion;
        } catch (Throwable e) {
            H5Log.e(TAG, "get PackageInfo fail:", e);
        }
        if (VERSION.SDK_INT >= 23) {
            if (targetSdkVersion < 23) {
                if (PermissionUtils.hasSelfPermissions(context, "android.permission.CAMERA")) {
                    status = PermissionStatus.GRANT;
                }
            } else if (context.checkSelfPermission("android.permission.CAMERA") == 0) {
                status = PermissionStatus.GRANT;
            } else if (context instanceof Activity) {
                if (!PermissionUtils.shouldShowRequestPermissionRationale((Activity) context, "android.permission.CAMERA")) {
                    status = PermissionStatus.ASK;
                }
            } else {
                H5Log.w(TAG, "in onQueryCameraAuthorizationStatus, context is not activity");
            }
        }
        return status.ordinal();
    }
}
