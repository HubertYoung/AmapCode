package com.alipay.mobile.beehive.photo.ui;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import com.alipay.mobile.antui.basic.AUTitleBar;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.photo.util.PhotoLogger;
import com.alipay.mobile.beehive.photo.wrapper.PhotoActivity;
import com.alipay.mobile.beehive.service.PhotoParam;

public class PhotoPreviewActivity extends PhotoActivity {
    public static final String TAG = "PhotoPreviewActivity";
    private AUTitleBar cashierTitlebar;
    private boolean isShowCashierTitleBar;
    private PhotoBrowseView photoBrowseView;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        setRequestedOrientation(1);
        if (bundle == null) {
            bundle = getIntent().getExtras();
            if (bundle == null) {
                finish();
                return;
            }
        } else {
            PhotoLogger.debug(TAG, "initialize photo preview with save instance.");
        }
        if (bundle.getBoolean(PhotoParam.BROWSE_GALLERY, false)) {
            getWindow().setFlags(1024, 1024);
        }
        setContentView(R.layout.activity_photo_preview);
        this.photoBrowseView = (PhotoBrowseView) findViewById(R.id.photo_browse_view);
        this.photoBrowseView.setActivity(this);
        this.photoBrowseView.setBundle(bundle);
        this.isShowCashierTitleBar = bundle.getBoolean(PhotoParam.USING_PHONE_TITLE_BAR, false);
        this.cashierTitlebar = (AUTitleBar) findViewById(R.id.cashier_top_bar);
        this.cashierTitlebar.setVisibility(this.isShowCashierTitleBar ? 0 : 8);
        this.cashierTitlebar.setBackground(new ColorDrawable(1059465787));
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        this.photoBrowseView.setActivity(null);
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        this.photoBrowseView.pause();
    }

    public void onBackPressed() {
        if (this.photoBrowseView != null) {
            this.photoBrowseView.backPressed();
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        this.photoBrowseView.resume();
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.photoBrowseView.newIntent(intent);
    }

    /* access modifiers changed from: protected */
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        this.photoBrowseView.saveInstanceState(getIntent(), bundle);
    }

    /* access modifiers changed from: protected */
    public Object getSpmObject() {
        return this;
    }

    /* access modifiers changed from: protected */
    public String getSpmID() {
        return "a310.b3479";
    }
}
