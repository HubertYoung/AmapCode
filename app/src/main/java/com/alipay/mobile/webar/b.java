package com.alipay.mobile.webar;

import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.webar.GeneralPermissionsManager.AUNoticeDialogEx;
import com.alipay.mobile.webar.GeneralPermissionsManager.IGeneralPermissions;
import com.alipay.mobile.webar.PermissionDatabaseHelper.PERMISSION_TYPE;
import java.util.Date;

/* compiled from: GeneralPermissionsManager */
final class b implements OnDismissListener {
    final /* synthetic */ AUNoticeDialogEx a;
    final /* synthetic */ String b;
    final /* synthetic */ IGeneralPermissions c;
    final /* synthetic */ GeneralPermissionsManager d;

    b(GeneralPermissionsManager this$0, AUNoticeDialogEx aUNoticeDialogEx, String str, IGeneralPermissions iGeneralPermissions) {
        this.d = this$0;
        this.a = aUNoticeDialogEx;
        this.b = str;
        this.c = iGeneralPermissions;
    }

    public final void onDismiss(DialogInterface dialogInterface) {
        if (this.a.a()) {
            this.d.mDatabaseHelper.a(this.b, PERMISSION_TYPE.CAMERA_PERMISSION, new Date().getTime() + 2592000000L);
            this.c.onAllow();
            H5Log.d("GeneralPermissionsManager", "general permissions allow");
            return;
        }
        this.c.onDeny();
        H5Log.d("GeneralPermissionsManager", "general permissions deny");
    }
}
