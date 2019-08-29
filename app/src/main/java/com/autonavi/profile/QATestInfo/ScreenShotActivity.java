package com.autonavi.profile.QATestInfo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.projection.MediaProjectionManager;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.widget.Toast;

public class ScreenShotActivity extends Activity {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (b.a) {
            requestWindowFeature(1);
            getWindow().setBackgroundDrawable(new ColorDrawable(0));
            getWindow().setDimAmount(0.0f);
            if (VERSION.SDK_INT >= 21) {
                startActivityForResult(((MediaProjectionManager) getSystemService("media_projection")).createScreenCaptureIntent(), 10387);
            }
        }
    }

    /* access modifiers changed from: private */
    public void a(String str) {
        Toast.makeText(this, str, 0).show();
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        eok.a = intent;
        if (i == 10387) {
            if (i2 == -1 && intent != null) {
                eol.a(getApplicationContext(), i2, intent).a((a) new a() {
                    public final void a() {
                        ScreenShotActivity.this.a("截屏已完成!");
                        ScreenShotActivity.this.finish();
                    }
                });
            } else if (i2 == 0) {
                a("请选择可以截屏");
            } else {
                a("unknow exceptions!");
            }
        }
    }
}
