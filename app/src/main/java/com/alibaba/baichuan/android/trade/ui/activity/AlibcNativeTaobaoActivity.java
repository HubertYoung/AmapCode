package com.alibaba.baichuan.android.trade.ui.activity;

import android.app.Activity;
import android.content.Intent;
import com.alibaba.baichuan.android.trade.component.b;
import com.alibaba.baichuan.android.trade.config.AlibcConfig;
import com.alibaba.baichuan.android.trade.constants.UserTrackerConstants;
import com.alibaba.baichuan.android.trade.model.ApplinkOpenType;
import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;
import com.autonavi.map.core.MapCustomizeManager;
import java.util.Map;

public class AlibcNativeTaobaoActivity extends Activity {
    public static String a() {
        return "alisdk://";
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        Intent intent = getIntent();
        if (intent != null) {
            String str = null;
            setIntent(null);
            String stringExtra = intent.getStringExtra("actionName");
            if (UserTrackerConstants.P_SHOWNATIVE.equals(stringExtra)) {
                String stringExtra2 = intent.getStringExtra("id");
                Map map = (Map) intent.getSerializableExtra("actionParameters");
                if (intent.getSerializableExtra("pid") != null) {
                    str = (String) intent.getSerializableExtra("pid");
                }
                String str2 = str;
                ApplinkOpenType applinkOpenType = (ApplinkOpenType) intent.getSerializableExtra("type");
                StringBuilder sb = new StringBuilder("AlibcNativeTaobaoActivity.start()--Back From NativeTaobao: action:showNative itemId:");
                sb.append(stringExtra2 == null ? "null" : stringExtra2);
                sb.append(" taokePid:");
                sb.append(str2 == null ? "null" : str2);
                AlibcLogger.i("BaichuanTLOG", sb.toString());
                if (!b.a(this, applinkOpenType, null, stringExtra2, AlibcConfig.getInstance().getIsvCode(), str2, a(), map)) {
                    finish();
                }
                return;
            }
            if (isTaskRoot()) {
                StringBuilder sb2 = new StringBuilder("AlibcNativeTaobaoActivity.start()--Back From NativeTaobao(jump through scheme url): action:");
                if (stringExtra == null) {
                    stringExtra = "null";
                }
                sb2.append(stringExtra);
                AlibcLogger.i("BaichuanTLOG", sb2.toString());
                Intent launchIntentForPackage = getPackageManager().getLaunchIntentForPackage(getPackageName());
                launchIntentForPackage.addFlags(MapCustomizeManager.VIEW_SEARCH_ALONG);
                startActivity(launchIntentForPackage);
            }
            finish();
            return;
        }
        finish();
    }
}
