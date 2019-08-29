package com.ali.user.mobile.login.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.LinearLayout;
import com.ali.user.mobile.AliUserInit;
import com.ali.user.mobile.account.bean.UserInfo;
import com.ali.user.mobile.common.api.UIConfigManager;
import com.ali.user.mobile.context.AliuserLoginContext;
import com.ali.user.mobile.db.UserInfoDaoHelper;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.log.LogAgent;
import com.ali.user.mobile.log.LoggerUtils;
import com.ali.user.mobile.login.LoginParam;
import com.ali.user.mobile.register.RegContext;
import com.ali.user.mobile.register.RegistParam;
import com.ali.user.mobile.register.model.State;
import com.ali.user.mobile.register.model.StateUtils;
import com.ali.user.mobile.security.ui.R;
import java.util.List;

public class AliuserGuideActivity extends BaseLoginActivity implements OnClickListener {
    private static final String TAG = "AliuserGuideActivity";

    /* access modifiers changed from: protected */
    public void clearAccount() {
    }

    /* access modifiers changed from: protected */
    public void clearPassword() {
    }

    /* access modifiers changed from: protected */
    public String getLoginAccount() {
        return "";
    }

    /* access modifiers changed from: protected */
    public String getLoginPassword() {
        return "";
    }

    /* access modifiers changed from: protected */
    public String getShownAccount() {
        return "";
    }

    /* access modifiers changed from: protected */
    public void onNewAccount(String str, int i) {
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.f);
        initRds();
        initViews();
        if (!checkRegisterRecover()) {
            LoggerUtils.a("", TAG, "login", "");
            LogAgent.a("UC-start-161225-01", "startpage", null, null, null);
        }
    }

    private boolean checkRegisterRecover() {
        RegContext a = RegContext.a();
        List<UserInfo> a2 = UserInfoDaoHelper.a(AliUserInit.b()).a();
        if (a2 == null || a2.isEmpty()) {
            State a3 = StateUtils.a();
            if (a3.b() || a.c == null) {
                return false;
            }
            a.c.a(a3);
            RegistParam registParam = new RegistParam();
            registParam.country = a3.a().getFullAreaCode();
            registParam.countryName = a3.a().getAreaName();
            registParam.registAccount = a3.a().getAccountForRPC();
            a.a(this, null, null);
            return true;
        }
        StateUtils.c();
        return false;
    }

    private void initRds() {
        initRdsPage("");
    }

    private void initViews() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.D);
        ((Button) findViewById(R.id.ax)).setOnClickListener(this);
        ((Button) findViewById(R.id.bv)).setOnClickListener(this);
        configRightCornerView((LinearLayout) findViewById(R.id.cb));
        Adapter a = UIConfigManager.a();
        View view = null;
        if (a != null) {
            view = a.getView(0, null, linearLayout);
        }
        if (view != null) {
            AliUserLog.c(TAG, String.format("use customed brand view:%s", new Object[]{view}));
            linearLayout.removeAllViews();
            linearLayout.addView(view);
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.ax) {
            LogAgent.b("UC-start-161225-02", "startlogin", null, null, null);
            goLogin(null);
            return;
        }
        if (view.getId() == R.id.bv) {
            State.a = "";
            LogAgent.b("UC-start-161225-03", "startregistered", null, null, null);
            goReg();
        }
    }

    public void goReg() {
        RegContext.a().a(this, null, null);
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        StringBuilder sb = new StringBuilder("onActivityResult, requestCode:");
        sb.append(i);
        sb.append(",resultCode:");
        sb.append(i2);
        sb.append(",data:");
        sb.append(intent);
        AliUserLog.c(TAG, sb.toString());
        super.onActivityResult(i, i2, intent);
    }

    public void goLogin(LoginParam loginParam) {
        Intent a = AliuserLoginContext.a(getApplicationContext());
        a.putExtra("login_param", loginParam);
        a.putExtra("come_back", true);
        a.putExtra("flag", "firstpage");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            extras.putLong("RenderStartTime", 0);
            a.putExtras(extras);
            int[] intArray = extras.getIntArray("intentFlags");
            if (intArray != null && intArray.length > 0) {
                AliUserLog.c(TAG, "there is external intent flags, add to login intent");
                for (int addFlags : intArray) {
                    a.addFlags(addFlags);
                }
            }
        }
        startActivity(a);
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4) {
            return super.onKeyDown(i, keyEvent);
        }
        callBackInsideOnKeyBack();
        finish();
        return true;
    }

    public void setAppId() {
        this.mAppId = "20000008";
    }
}
