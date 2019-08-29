package com.autonavi.minimap.dexfilter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.autonavi.minimap.multidexload.MdLoadingActivity;

public class DexActivityProxy extends Activity {
    /* access modifiers changed from: protected */
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        drx.a("--DexActivityProxy.onCreate :");
        a();
    }

    private void a() {
        Intent intent = getIntent();
        drx.a("--NewMapActivity.onCreate : it :".concat(String.valueOf(intent)));
        if (intent == null) {
            intent = new Intent();
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable("com.autonavi.minimap.multidexload.mdloadingactivity.dex_intent_tag", intent);
        Intent intent2 = new Intent(this, MdLoadingActivity.class);
        intent2.addFlags(32768);
        intent2.putExtra("com.autonavi.minimap.multidexload.mdloadingactivity.dex_intent_tag", bundle);
        startActivity(intent2);
        finish();
    }

    /* access modifiers changed from: protected */
    public void onRestart() {
        super.onRestart();
        drx.a("--DexActivityProxy.onRestart :");
        a();
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        drx.a("--DexActivityProxy.onNewIntent :");
        a();
    }
}
