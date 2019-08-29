package com.alipay.mobile.beehive.photo.wrapper;

import android.content.Intent;
import com.alipay.mobile.beehive.global.impl.BeehiveBaseActivity;

public class PhotoActivity extends BeehiveBaseActivity {
    public void startExtActivityForResult(Intent intent, int RequestCode) {
        getActivityApplication().getMicroApplicationContext().startExtActivityForResult(getActivityApplication(), intent, RequestCode);
    }
}
