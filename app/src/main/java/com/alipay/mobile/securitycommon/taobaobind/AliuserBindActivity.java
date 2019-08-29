package com.alipay.mobile.securitycommon.taobaobind;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.alipay.mobile.account.adapter.AccountAdapterActivity;
import com.alipay.mobile.account.adapter.CommonAdapter;
import com.alipay.mobile.account.adapter.LogAdapter;
import com.alipay.mobile.securitycommon.taobaobind.TaobaoBindConstans.Key;
import com.alipay.mobileapp.biz.rpc.taobao.bind.vo.BindTaobaoRes;

public class AliuserBindActivity extends AccountAdapterActivity {
    private static final String TAG = "AliuserBindActivity";
    private Button mBindButton;
    private BindTaobaoRes mBindTaobaoRes;
    private ImageView mTitleBar;
    private TextView mTvTip;
    private TextView mTvTip1;
    private TextView mTvTip2;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_account_bind);
        initView();
        initData();
    }

    private void initView() {
        this.mTitleBar = (ImageView) findViewById(R.id.titleBar);
        this.mTvTip = (TextView) findViewById(R.id.textTip);
        this.mTvTip1 = (TextView) findViewById(R.id.textTip1);
        this.mTvTip2 = (TextView) findViewById(R.id.textTip2);
        this.mBindButton = (Button) findViewById(R.id.comfirmSetting);
    }

    private void initData() {
        try {
            this.mBindTaobaoRes = (BindTaobaoRes) getIntent().getSerializableExtra(Key.BIND_TOKEN);
        } catch (Throwable th) {
            LogAdapter.a((String) TAG, th);
        }
        if (this.mBindTaobaoRes == null) {
            CommonAdapter.a();
            Toast.makeText(CommonAdapter.b(), getString(R.string.system_error_try_later), 0).show();
            finish();
            return;
        }
        if ("1002".equals(this.mBindTaobaoRes.resultCode)) {
            this.mTvTip.setVisibility(0);
            if (!TextUtils.isEmpty(this.mBindTaobaoRes.memo)) {
                this.mTvTip.setText(this.mBindTaobaoRes.memo);
            }
            if (!TextUtils.isEmpty(this.mBindTaobaoRes.btnMemo)) {
                this.mBindButton.setText(this.mBindTaobaoRes.btnMemo);
            } else {
                this.mBindButton.setText(getResources().getString(R.string.toBind));
            }
        } else {
            this.mTvTip1.setVisibility(0);
            this.mTvTip2.setVisibility(0);
            if (!TextUtils.isEmpty(this.mBindTaobaoRes.memo)) {
                this.mTvTip1.setText(this.mBindTaobaoRes.memo);
            }
            if (!TextUtils.isEmpty(this.mBindTaobaoRes.txtMemo)) {
                this.mTvTip2.setText(this.mBindTaobaoRes.txtMemo);
            }
        }
        this.mTitleBar.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                AliuserBindActivity.this.cancelBind();
            }
        });
        this.mBindButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                AliuserBindActivity.this.doBind();
            }
        });
    }

    /* access modifiers changed from: private */
    public void doBind() {
        TaobaoBindService.getInstance(getApplicationContext()).notifyUserGrant("1002".equals(this.mBindTaobaoRes.resultCode) ? 1 : 0);
        finish();
    }

    /* access modifiers changed from: private */
    public void cancelBind() {
        TaobaoBindService.getInstance(getApplicationContext()).notifyUserGrant("1002".equals(this.mBindTaobaoRes.resultCode) ? -1 : 0);
        finish();
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4) {
            return super.onKeyDown(i, keyEvent);
        }
        cancelBind();
        return true;
    }
}
