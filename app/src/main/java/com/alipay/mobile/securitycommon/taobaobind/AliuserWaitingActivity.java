package com.alipay.mobile.securitycommon.taobaobind;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.alipay.mobile.account.adapter.AccountAdapterActivity;
import com.alipay.mobile.account.adapter.CommonAdapter;
import com.alipay.mobile.account.adapter.LogAdapter;
import com.alipay.mobile.securitycommon.aliauth.AliAuthConstants.Result;

public class AliuserWaitingActivity extends AccountAdapterActivity {
    private static final String TAG = "AliuserWaitingActivity";
    private final BroadcastReceiver mAccountBindReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (intent != null && TaobaoBindService.ACTION_ALIPAY_BIND_TAOBAO.equals(intent.getAction())) {
                String stringExtra = intent.getStringExtra("flag");
                LogAdapter.a((String) AliuserWaitingActivity.TAG, String.format("receive bind result broadcast: %s", new Object[]{stringExtra}));
                if ("2003".equals(stringExtra)) {
                    AliuserWaitingActivity.this.onBindRpcException();
                } else if ("1000".equals(stringExtra) || "1001".equals(stringExtra) || "1002".equals(stringExtra) || Result.TAOBAO_ACTIVE.equals(stringExtra)) {
                    AliuserWaitingActivity.this.finishWithResult(1);
                } else {
                    AliuserWaitingActivity.this.onBindSystemError(stringExtra);
                }
            }
        }
    };
    private AlertDialog mAlertDialog;
    private View mBindErrorLayout;
    private Button mButton;
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private boolean mHasBindError = false;
    private boolean mHasException = false;
    private ImageView mTitleBar;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_account_bind_waiting);
        initViews();
        initEvents();
        String stringExtra = getIntent().getStringExtra("flag");
        if (TextUtils.isEmpty(stringExtra)) {
            showProgress(getResources().getString(R.string.loading));
        } else {
            onBindSystemError(stringExtra);
        }
    }

    public void showProgress(String str) {
        AlertDialog create = new Builder(this).create();
        create.setMessage(str);
        create.setCanceledOnTouchOutside(false);
        create.setCancelable(true);
        create.setOnCancelListener(new OnCancelListener() {
            public void onCancel(DialogInterface dialogInterface) {
                AliuserWaitingActivity.this.finishWithResult(-1);
            }
        });
        try {
            create.show();
        } catch (Exception e) {
            LogAdapter.a((String) TAG, (Throwable) e);
        }
    }

    public void dismissProgress() {
        if (this.mAlertDialog != null && this.mAlertDialog.isShowing() && !isFinishing()) {
            try {
                this.mAlertDialog.dismiss();
            } catch (Throwable th) {
                LogAdapter.a((String) TAG, th);
            } finally {
                this.mAlertDialog = null;
            }
        }
    }

    private void initViews() {
        this.mTitleBar = (ImageView) findViewById(R.id.titleBar);
        this.mBindErrorLayout = findViewById(R.id.bindErrorLayout);
        this.mButton = (Button) findViewById(R.id.comfirmSetting);
    }

    private void initEvents() {
        this.mTitleBar.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                AliuserWaitingActivity.this.onBackKeyDown();
            }
        });
        this.mButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                AliuserWaitingActivity.this.finishWithResult(AliuserWaitingActivity.this.getBindErrorCode());
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.mAccountBindReceiver, new IntentFilter(TaobaoBindService.ACTION_ALIPAY_BIND_TAOBAO));
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.mAccountBindReceiver);
    }

    /* access modifiers changed from: private */
    public void finishWithResult(int i) {
        TaobaoBindService.getInstance(getApplicationContext()).notifyUserWaiting(i);
        finish();
    }

    public void finish() {
        dismissProgress();
        super.finish();
    }

    /* access modifiers changed from: private */
    public void onBackKeyDown() {
        if (this.mHasException) {
            finishWithResult(-1000);
        } else if (this.mHasBindError) {
            finishWithResult(getBindErrorCode());
        } else {
            finishWithResult(-1);
        }
    }

    /* access modifiers changed from: private */
    public void onBindRpcException() {
        LogAdapter.a((String) TAG, (String) "bind rpc exception, will exit after 2s");
        dismissProgress();
        CommonAdapter.a();
        Toast.makeText(CommonAdapter.b(), getString(R.string.network_error_retry), 0).show();
        this.mHasException = true;
        this.mHandler.postDelayed(new Runnable() {
            public void run() {
                AliuserWaitingActivity.this.finishWithResult(-1000);
            }
        }, 2000);
    }

    /* access modifiers changed from: private */
    public void onBindSystemError(String str) {
        dismissProgress();
        this.mHasBindError = true;
        this.mBindErrorLayout.setVisibility(0);
        this.mBindErrorLayout.setTag(str);
    }

    /* access modifiers changed from: private */
    public int getBindErrorCode() {
        try {
            return Integer.parseInt(this.mBindErrorLayout.getTag().toString());
        } catch (Exception e) {
            LogAdapter.a((String) TAG, (Throwable) e);
            return 0;
        }
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4) {
            return super.onKeyDown(i, keyEvent);
        }
        onBackKeyDown();
        return true;
    }
}
