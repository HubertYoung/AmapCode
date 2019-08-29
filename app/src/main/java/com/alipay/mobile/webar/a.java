package com.alipay.mobile.webar;

import com.alipay.mobile.antui.dialog.AUNoticeDialog.OnClickPositiveListener;
import com.alipay.mobile.webar.GeneralPermissionsManager.AUNoticeDialogEx;

/* compiled from: GeneralPermissionsManager */
final class a implements OnClickPositiveListener {
    final /* synthetic */ AUNoticeDialogEx a;
    final /* synthetic */ GeneralPermissionsManager b;

    a(GeneralPermissionsManager this$0, AUNoticeDialogEx aUNoticeDialogEx) {
        this.b = this$0;
        this.a = aUNoticeDialogEx;
    }

    public final void onClick() {
        this.a.b();
    }
}
