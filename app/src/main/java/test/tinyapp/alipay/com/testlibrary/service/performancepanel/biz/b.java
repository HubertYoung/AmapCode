package test.tinyapp.alipay.com.testlibrary.service.performancepanel.biz;

import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.tinyappcommon.h5plugin.H5VConsolePlugin;

/* compiled from: PerformancePanelPermissionChecker */
public final class b implements test.tinyapp.alipay.com.testlibrary.core.b {
    public final boolean a(H5Page page) {
        return H5VConsolePlugin.debugAllowed(page);
    }
}
