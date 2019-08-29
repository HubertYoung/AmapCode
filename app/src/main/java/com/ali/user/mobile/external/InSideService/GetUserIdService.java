package com.ali.user.mobile.external.InSideService;

import com.ali.user.mobile.accountbiz.sp.SecurityShareStore;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.framework.service.IInsideService;
import com.alipay.android.phone.inside.framework.service.IInsideServiceCallback;

public class GetUserIdService implements IInsideService<Void, String> {
    public void start(Void voidR) throws Exception {
        throw new UnsupportedOperationException();
    }

    public void start(IInsideServiceCallback<String> iInsideServiceCallback, Void voidR) throws Exception {
        if (iInsideServiceCallback != null) {
            iInsideServiceCallback.onComplted(startForResult((Void) null));
        }
    }

    public String startForResult(Void voidR) throws Exception {
        return SecurityShareStore.a(LauncherApplication.a(), "currentUserId");
    }
}
