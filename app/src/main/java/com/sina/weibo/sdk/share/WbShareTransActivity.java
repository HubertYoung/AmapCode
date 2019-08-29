package com.sina.weibo.sdk.share;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.WeiboAppManager;
import com.sina.weibo.sdk.api.StoryObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.auth.WbAppInfo;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.constant.WBConstants.Base;
import com.sina.weibo.sdk.constant.WBConstants.Response;
import com.sina.weibo.sdk.constant.WBConstants.SDK;
import com.sina.weibo.sdk.utils.MD5;
import com.sina.weibo.sdk.utils.Utility;
import com.sina.weibo.sdk.utils.WbSdkVersion;
import com.sina.weibo.sdk.web.view.WbSdkProgressBar;

public class WbShareTransActivity extends BaseActivity {
    private CopyResourceTask copyResourceTask;
    boolean flag = false;
    private Handler handler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message message) {
            super.handleMessage(message);
            WbShareTransActivity.this.sendCallback(1);
        }
    };
    /* access modifiers changed from: private */
    public FrameLayout rootLayout;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initView();
        checkSource();
    }

    private void checkSource() {
        try {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                finish();
                return;
            }
            WeiboMultiMessage weiboMultiMessage = new WeiboMultiMessage();
            weiboMultiMessage.toObject(extras);
            transPicAndVideoResource(weiboMultiMessage);
        } catch (Exception unused) {
            finish();
        }
    }

    private void initView() {
        View view;
        int intExtra = getIntent().getIntExtra(WBConstants.TRANS_PROGRESS_COLOR, -1);
        int intExtra2 = getIntent().getIntExtra(WBConstants.TRANS_PROGRESS_ID, -1);
        this.rootLayout = new FrameLayout(this);
        if (intExtra2 != -1) {
            try {
                view = ((LayoutInflater) getSystemService("layout_inflater")).inflate(intExtra2, null);
            } catch (Exception unused) {
                view = new WbSdkProgressBar(this);
            }
        } else {
            View wbSdkProgressBar = new WbSdkProgressBar(this);
            if (intExtra != -1) {
                ((WbSdkProgressBar) wbSdkProgressBar).setProgressColor(intExtra);
            }
            view = wbSdkProgressBar;
        }
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.gravity = 17;
        this.rootLayout.addView(view, layoutParams);
        this.rootLayout.setBackgroundColor(855638016);
    }

    /* access modifiers changed from: private */
    public void gotoWeiboComposer(WeiboMultiMessage weiboMultiMessage) {
        Intent intent = getIntent();
        this.flag = true;
        try {
            intent.putExtra(WBConstants.SHARE_START_FLAG, -1);
            Intent intent2 = new Intent(WBConstants.ACTIVITY_WEIBO);
            Bundle extras = intent.getExtras();
            weiboMultiMessage.toBundle(extras);
            intent2.putExtras(extras);
            String packageName = getPackageName();
            intent2.putExtra(Base.SDK_VER, WbSdkVersion.WEIBO_SDK_VERSION_CODE);
            intent2.putExtra(Base.APP_PKG, packageName);
            intent2.putExtra(Base.APP_KEY, WbSdk.getAuthInfo().getAppKey());
            intent2.putExtra(SDK.FLAG, WBConstants.WEIBO_FLAG_SDK);
            intent2.putExtra(WBConstants.SIGN, MD5.hexdigest(Utility.getSign(this, packageName)));
            String stringExtra = intent.getStringExtra(WBConstants.SHARE_START_GOTO_ACTIVITY);
            if (!TextUtils.isEmpty(stringExtra) && "com.sina.weibo.sdk.web.WeiboSdkWebActivity".equals(stringExtra)) {
                intent2.setClassName(this, "com.sina.weibo.sdk.web.WeiboSdkWebActivity");
                startActivity(intent2);
            } else if (WbSdk.isWbInstall(this)) {
                WbAppInfo wbAppInfo = WeiboAppManager.getInstance(this).getWbAppInfo();
                if (wbAppInfo != null) {
                    intent2.setPackage(wbAppInfo.getPackageName());
                }
                startActivityForResult(intent2, WBConstants.SDK_ACTIVITY_FOR_RESULT_CODE);
            } else {
                sendCallback(2);
            }
        } catch (Exception unused) {
            sendCallback(2);
        }
    }

    private void transPicAndVideoResource(WeiboMultiMessage weiboMultiMessage) {
        setContentView(this.rootLayout);
        if (weiboMultiMessage.multiImageObject == null && weiboMultiMessage.videoSourceObject == null) {
            gotoWeiboComposer(weiboMultiMessage);
            return;
        }
        if (this.copyResourceTask != null) {
            this.copyResourceTask.cancel(true);
        }
        this.copyResourceTask = new CopyResourceTask(this, new TransResourceCallback() {
            public void onTransFinish(StoryObject storyObject) {
            }

            public void onTransFinish(TransResourceResult transResourceResult) {
                WbShareTransActivity.this.rootLayout.setVisibility(4);
                if (transResourceResult == null || !transResourceResult.transDone) {
                    WbShareTransActivity.this.sendCallback(2);
                } else {
                    WbShareTransActivity.this.gotoWeiboComposer(transResourceResult.message);
                }
            }
        });
        this.copyResourceTask.execute(new WeiboMultiMessage[]{weiboMultiMessage});
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (this.handler != null) {
            this.handler.sendEmptyMessageDelayed(0, 100);
        }
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4) {
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (this.handler != null) {
            this.handler.removeMessages(0);
            this.handler = null;
        }
        setResult(-1, intent);
        finish();
    }

    /* access modifiers changed from: protected */
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.remove(WBConstants.SHARE_START_FLAG);
    }

    /* access modifiers changed from: private */
    public void sendCallback(int i) {
        if (this.rootLayout != null) {
            this.rootLayout.setVisibility(8);
        }
        try {
            Intent intent = new Intent();
            new Bundle().putInt(Response.ERRCODE, i);
            setResult(-1, intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (this.handler != null) {
            this.handler.removeMessages(0);
            this.handler = null;
        }
        finish();
    }
}
