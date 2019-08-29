package com.alipay.android.phone.inside.commonbiz.login.uniformity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.alipay.android.phone.inside.commonbiz.R;
import com.alipay.android.phone.inside.log.api.LoggerFactory;

public class AccountUniformityActivity extends Activity {
    public static final String KEY_INSIDE_FLAG = "insideFlag";
    private String insideFlag;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature();
        setContentView(R.layout.a);
        handleIntent(getIntent());
        ((Button) findViewById(R.id.b)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                AccountUniformityActivity.this.onUniformityConfirm();
            }
        });
        LoggerFactory.f().e("inside", "AccountUniformityActivity::onCreate");
    }

    private void requestWindowFeature() {
        try {
            requestWindowFeature(1);
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
        }
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
        LoggerFactory.f().e("inside", "AccountUniformityActivity::onNewIntent");
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        LoggerFactory.f().e("inside", "AccountUniformityActivity::onDestroy");
    }

    public void onBackPressed() {
        LoggerFactory.f().e("inside", "AccountUniformityActivity::onBackPressed");
    }

    /* access modifiers changed from: private */
    public void onUniformityConfirm() {
        notifyUniformityConfirm();
        finish();
        overridePendingTransition(0, 0);
    }

    private void notifyUniformityConfirm() {
        AccountUniformity.a(this.insideFlag);
    }

    private void handleIntent(Intent intent) {
        this.insideFlag = intent.getStringExtra("insideFlag");
    }
}
