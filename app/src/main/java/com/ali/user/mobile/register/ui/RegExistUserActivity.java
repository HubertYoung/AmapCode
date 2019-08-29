package com.ali.user.mobile.register.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.ali.user.mobile.base.BaseActivity;
import com.ali.user.mobile.common.api.UIConfigManager;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.log.LoggerUtils;
import com.ali.user.mobile.register.Account;
import com.ali.user.mobile.register.LogUtils;
import com.ali.user.mobile.register.RegContext;
import com.ali.user.mobile.register.model.State;
import com.ali.user.mobile.register.model.StateUtils;
import com.ali.user.mobile.register.router.IRouterHandler;
import com.ali.user.mobile.register.router.RouterPages;
import com.ali.user.mobile.register.store.ActionCenter;
import com.ali.user.mobile.security.ui.R;
import com.ali.user.mobile.ui.widget.ImageLoader;
import com.ali.user.mobile.util.ResizeScrollView;
import com.ali.user.mobile.util.ShowRegionHelper;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobileapp.biz.rpc.unifyregister.vo.pb.ExistUserInfo;

public class RegExistUserActivity extends BaseActivity implements OnClickListener, IRouterHandler {
    private static final String sTag = "Reg_Exist";
    private ImageView mAvatar;
    private LinearLayout mDetailWrapper;
    private Button mFirstButton;
    private LinearLayout mNameWrapper;
    private Button mSecondButton;
    private State mTmpState = null;
    private LinearLayout mValueWrapper;

    public void afterDialog() {
    }

    public BaseActivity getActivity() {
        return this;
    }

    public boolean handleStateChange(State state) {
        return false;
    }

    public void handleVerifySuccess(String str) {
    }

    public void onBackPressed() {
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.e);
        initView();
        initData();
        LogUtils.b("UC-ZC-161209-03", "zcnograb");
        LoggerUtils.a("", "RegExistUserActivity", "login", "");
        initTmpState();
    }

    private void initTmpState() {
        ActionCenter actionCenter = RegContext.a().c;
        if (actionCenter != null) {
            this.mTmpState = actionCenter.a();
            StateUtils.c();
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        RouterPages.a(this);
    }

    public void setAppId() {
        this.mAppId = "20000009";
    }

    private void initView() {
        initTitle();
        initUserDetail();
        initButtons();
        initWrapper();
    }

    private void initWrapper() {
        ResizeScrollView resizeScrollView = (ResizeScrollView) findViewById(R.id.bz);
        new ShowRegionHelper(resizeScrollView).a(this.mNameWrapper, this.mSecondButton, true);
        resizeScrollView.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                RegExistUserActivity.this.closeInputMethod(view);
                return false;
            }
        });
    }

    private void initTitle() {
        TextView textView = (TextView) findViewById(R.id.be);
        ActionCenter actionCenter = RegContext.a().c;
        if (actionCenter == null) {
            AliUserLog.d(sTag, "null action center");
            return;
        }
        State a = actionCenter.a();
        if (a == null || a.c == null) {
            AliUserLog.d(sTag, "null response");
            return;
        }
        Account a2 = a.a();
        if (a2 == null) {
            AliUserLog.d(sTag, "null account");
        } else {
            textView.setText(a2.accountForDisplay());
        }
    }

    private void initButtons() {
        this.mFirstButton = (Button) findViewById(R.id.bb);
        this.mSecondButton = (Button) findViewById(R.id.bd);
        this.mFirstButton.setOnClickListener(this);
        this.mSecondButton.setOnClickListener(this);
        UIConfigManager.a(this.mFirstButton);
        UIConfigManager.b(this.mSecondButton);
    }

    private void initUserDetail() {
        this.mDetailWrapper = (LinearLayout) findViewById(R.id.br);
        this.mAvatar = (ImageView) findViewById(R.id.ba);
        this.mNameWrapper = (LinearLayout) findViewById(R.id.bc);
        this.mValueWrapper = (LinearLayout) findViewById(R.id.bf);
    }

    private void initData() {
        ActionCenter actionCenter = RegContext.a().c;
        if (actionCenter == null) {
            AliUserLog.d(sTag, "null action center");
            return;
        }
        State a = actionCenter.a();
        if (a == null || a.c == null || a.c.existUserInfo == null) {
            AliUserLog.d(sTag, "null state");
            return;
        }
        ExistUserInfo existUserInfo = a.c.existUserInfo;
        initUserAvatar(existUserInfo);
        if (initUserInfo(existUserInfo)) {
            this.mDetailWrapper.setVisibility(0);
        } else {
            this.mDetailWrapper.setVisibility(8);
        }
        initButtonText(existUserInfo);
    }

    private void initButtonText(ExistUserInfo existUserInfo) {
        if (!TextUtils.isEmpty(existUserInfo.ButtonFstMemo) && this.mFirstButton != null) {
            this.mFirstButton.setText(existUserInfo.ButtonFstMemo);
        }
        if (!TextUtils.isEmpty(existUserInfo.ButtonSedMemo) && this.mSecondButton != null) {
            this.mSecondButton.setText(existUserInfo.ButtonSedMemo);
        }
    }

    private boolean initUserInfo(ExistUserInfo existUserInfo) {
        if (existUserInfo.displayTags == null) {
            return false;
        }
        LayoutInflater from = LayoutInflater.from(this);
        int size = existUserInfo.displayTags.size();
        boolean z = false;
        for (int i = 0; i < size; i++) {
            String[] split = existUserInfo.displayTags.get(i).split(":");
            if (2 != split.length) {
                StringBuilder sb = new StringBuilder("tag split not 2 ");
                sb.append(split.length);
                AliUserLog.d(sTag, sb.toString());
            } else {
                TextView textView = (TextView) from.inflate(R.layout.L, this.mNameWrapper, false);
                textView.setText(split[0]);
                this.mNameWrapper.addView(textView);
                TextView textView2 = (TextView) from.inflate(R.layout.L, this.mValueWrapper, false);
                textView2.setText(split[1]);
                this.mValueWrapper.addView(textView2);
                if (i != 0) {
                    fitTop(textView);
                    fitTop(textView2);
                }
                z = true;
            }
        }
        return z;
    }

    private void fitTop(TextView textView) {
        LayoutParams layoutParams = (LayoutParams) textView.getLayoutParams();
        layoutParams.topMargin = getResources().getDimensionPixelOffset(R.dimen.k);
        textView.setLayoutParams(layoutParams);
    }

    private void initUserAvatar(ExistUserInfo existUserInfo) {
        if (TextUtils.isEmpty(existUserInfo.avatar)) {
            setDefaultUserAvatar(existUserInfo);
            return;
        }
        ImageLoader.a(existUserInfo.avatar, this.mAvatar, getResources().getDrawable(R.drawable.y));
    }

    private void setDefaultUserAvatar(ExistUserInfo existUserInfo) {
        AliUserLog.c("aliuser", "setDefaultUserAvatar > ".concat(String.valueOf(existUserInfo)));
        this.mAvatar.setImageResource(R.drawable.y);
    }

    public void startActivity(Intent intent) {
        StateUtils.c();
        super.startActivity(intent);
        finish();
    }

    public void onClick(View view) {
        if (view != null) {
            int id = view.getId();
            if (R.id.bb == id) {
                doLocalJump(H5Param.PREFETCH_LOCATION);
                LogUtils.a("UC-ZC-150512-20", "zcnograblogin");
                return;
            }
            if (R.id.bd == id) {
                doLocalJump("m");
                LogUtils.a("UC-ZC-150512-21", "zcnograbphone");
            }
        }
    }

    private void doLocalJump(String str) {
        ActionCenter actionCenter = RegContext.a().c;
        if (actionCenter != null) {
            actionCenter.a(this.mTmpState);
            actionCenter.a(str);
        }
    }
}
