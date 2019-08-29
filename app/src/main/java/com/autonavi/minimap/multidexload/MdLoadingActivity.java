package com.autonavi.minimap.multidexload;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.amap.bundle.tools.datafreecheck.DataFreeLowActivity;
import com.autonavi.minimap.R;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings({"DM_EXIT"})
public class MdLoadingActivity extends Activity {
    /* access modifiers changed from: private */
    public Intent a;
    /* access modifiers changed from: private */
    public Handler b = new Handler();
    private a c = new a() {
        public final void a(boolean z) {
            StringBuilder sb = new StringBuilder("--MdLoadingActivity.OnLoadResult :result:");
            sb.append(z);
            sb.append("  mIntent:");
            sb.append(MdLoadingActivity.this.a);
            drx.a(sb.toString());
            MdLoadingActivity.this.b.removeCallbacks(MdLoadingActivity.this.d);
            if (!z || MdLoadingActivity.this.a == null) {
                MdLoadingActivity.e(MdLoadingActivity.this);
            } else {
                MdLoadingActivity.d(MdLoadingActivity.this);
            }
        }
    };
    /* access modifiers changed from: private */
    public Runnable d = new Runnable() {
        public final void run() {
            drx.a("--MdLoadingActivity.run : time out ");
            kw.a().a(null);
            MdLoadingActivity.e(MdLoadingActivity.this);
        }
    };

    public void onBackPressed() {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.md_loading_activity);
        a(getIntent());
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        a(intent);
    }

    private void a(Intent intent) {
        if (intent != null) {
            Bundle bundleExtra = intent.getBundleExtra("com.autonavi.minimap.multidexload.mdloadingactivity.dex_intent_tag");
            if (bundleExtra != null) {
                this.a = (Intent) bundleExtra.getParcelable("com.autonavi.minimap.multidexload.mdloadingactivity.dex_intent_tag");
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        drx.a("--MdLoadingActivity.onResume :");
        this.b.postDelayed(this.d, StatisticConfig.MIN_UPLOAD_INTERVAL);
        kw.a().a(this.c);
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        drx.a("--MdLoadingActivity.onPause :");
        this.b.removeCallbacks(this.d);
        kw.a().a(null);
    }

    static /* synthetic */ void d(MdLoadingActivity mdLoadingActivity) {
        mdLoadingActivity.a.addFlags(32768);
        mdLoadingActivity.startActivity(mdLoadingActivity.a);
        mdLoadingActivity.finish();
    }

    static /* synthetic */ void e(MdLoadingActivity mdLoadingActivity) {
        Class cls;
        boolean z;
        Class cls2 = MdLoadErrorActivity.class;
        afu a2 = afu.a((Context) mdLoadingActivity.getApplication());
        a2.a = afu.b((Context) mdLoadingActivity.getApplication());
        if (a2.a) {
            z = true;
            cls = DataFreeLowActivity.class;
        } else {
            cls = cls2;
            z = false;
        }
        if (z) {
            mdLoadingActivity.finish();
            System.exit(0);
        }
        Intent intent = new Intent(mdLoadingActivity, cls);
        intent.setFlags(268468224);
        mdLoadingActivity.startActivity(intent);
        mdLoadingActivity.finish();
    }
}
