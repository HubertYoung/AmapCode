package com.ali.user.mobile.external.InSideService;

import android.content.Intent;
import android.os.Bundle;
import com.ali.user.mobile.external.accountmanager.AccountManagerActivity;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.framework.service.IInsideService;
import com.alipay.android.phone.inside.framework.service.IInsideServiceCallback;

public class AccountManagerService implements IInsideService<Bundle, String> {
    public void start(Bundle bundle) throws Exception {
        Intent intent = new Intent();
        intent.putExtras(bundle);
        intent.addFlags(268435456);
        intent.setClass(LauncherApplication.a().getApplicationContext(), AccountManagerActivity.class);
        LauncherApplication.a().startActivity(intent);
    }

    public void start(IInsideServiceCallback<String> iInsideServiceCallback, Bundle bundle) throws Exception {
        throw new UnsupportedOperationException();
    }

    public String startForResult(Bundle bundle) throws Exception {
        throw new UnsupportedOperationException();
    }
}
