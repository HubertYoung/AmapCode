package com.sina.weibo.sdk.share;

import android.content.Intent;
import android.os.Bundle;
import com.sina.weibo.sdk.constant.WBConstants;

public class WbShareResultActivity extends BaseActivity {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        if (WBConstants.ACTIVITY_REQ_SDK.equals(intent.getAction())) {
            intent.setClass(this, WbShareTransActivity.class);
        } else {
            intent.setClass(this, WbShareToStoryActivity.class);
        }
        startActivity(intent);
        finish();
    }
}
