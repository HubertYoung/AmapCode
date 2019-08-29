package test.tinyapp.alipay.com.testlibrary.service.a;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.nebula.util.H5Utils;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* compiled from: PageSwitchNotifyTestService */
public class a implements test.tinyapp.alipay.com.testlibrary.core.a {
    private static final String a = a.class.getSimpleName();
    private String[] b = {"pushWindow", "h5_switch_sessiontab", CommonEvents.H5_PAGE_RESUME};
    private Set<String> c = new HashSet(Arrays.asList(this.b));
    private final LocalBroadcastManager d = LocalBroadcastManager.getInstance(H5Utils.getContext());

    public final void a() {
    }

    public final void b() {
    }

    public final boolean a(H5Event event, H5BridgeContext context) {
        String eventAction = event.getAction();
        String userAction = "";
        Bundle extraData = new Bundle();
        if ("pushWindow".equals(eventAction)) {
            userAction = "com.tinyapp.alipay.action.switchPage";
        } else if ("h5_switch_sessiontab".equals(eventAction)) {
            userAction = "com.tinyapp.alipay.action.switchTab";
        } else if (CommonEvents.H5_PAGE_RESUME.equals(eventAction)) {
            Log.i(a, "tiny app resume");
            userAction = "com.tinyapp.alipay.action.pageResume";
            extraData.putLong("page_resume_start_time", SystemClock.elapsedRealtime());
        } else {
            Log.i(a, "action: " + eventAction);
        }
        if (!TextUtils.isEmpty(userAction)) {
            Intent intent = new Intent(userAction);
            intent.putExtras(extraData);
            this.d.sendBroadcast(intent);
        }
        return false;
    }

    public final boolean a(H5Event event) {
        return this.c.contains(event.getAction());
    }

    public final List<String> c() {
        return Arrays.asList(this.b);
    }
}
