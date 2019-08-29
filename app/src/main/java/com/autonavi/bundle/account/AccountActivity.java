package com.autonavi.bundle.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup.LayoutParams;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.bundle.account.AccountActivityDelegate.AccountPageContainer;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.wing.WingActivity;

public class AccountActivity extends WingActivity {
    private AccountActivityDelegate b = AccountActivityDelegate.a();

    public static Intent a(Context context, String str, String str2) {
        Intent intent = new Intent(context, AccountActivity.class);
        intent.putExtra("url", str);
        intent.putExtra("pageId", str2);
        return intent;
    }

    public AccountActivity() {
        AccountActivityDelegate accountActivityDelegate = this.b;
        if (accountActivityDelegate.b != null) {
            accountActivityDelegate.b.finish();
        }
        accountActivityDelegate.b = this;
    }

    public final void a(Bundle bundle) {
        anm.a((Context) this);
        super.a(bundle);
        AccountPageContainer accountPageContainer = new AccountPageContainer(this);
        setContentView(accountPageContainer, new LayoutParams(-1, -1));
        a(accountPageContainer);
        AMapPageUtil.setMvpActivityContext(this.a);
        AccountActivityDelegate accountActivityDelegate = this.b;
        Intent intent = accountActivityDelegate.b.getIntent();
        if (intent == null) {
            AMapLog.e(AccountActivityDelegate.a, "需要传入Intent数据才能执行后续动作");
            accountActivityDelegate.b.finish();
        } else if (!intent.hasExtra("url")) {
            AMapLog.e(AccountActivityDelegate.a, "需要传入Url才能执行后续动作");
            accountActivityDelegate.b.finish();
        } else {
            String stringExtra = intent.getStringExtra("url");
            String stringExtra2 = intent.getStringExtra("pageId");
            String stringExtra3 = intent.getStringExtra("jsData");
            String stringExtra4 = intent.getStringExtra(Ajx3Page.PAGE_LOADING_TYPE);
            PageBundle pageBundle = new PageBundle();
            pageBundle.putString("url", stringExtra);
            if (TextUtils.isEmpty(stringExtra2)) {
                stringExtra2 = "";
            }
            pageBundle.putString("pageId", stringExtra2);
            if (TextUtils.isEmpty(stringExtra3)) {
                stringExtra3 = null;
            }
            pageBundle.putString("jsData", stringExtra3);
            if (TextUtils.isEmpty(stringExtra4)) {
                stringExtra4 = "";
            }
            pageBundle.putString(Ajx3Page.PAGE_LOADING_TYPE, stringExtra4);
            accountActivityDelegate.b.a.a(Ajx3Page.class, pageBundle, (aki) null);
        }
    }

    public final void a() {
        super.a();
        AMapPageUtil.setMvpActivityContext(this.a);
    }

    public final void b() {
        super.b();
    }

    public final void c() {
        super.c();
    }

    public final void d() {
        super.d();
    }

    public final void e() {
        super.e();
        this.b.b = null;
    }

    public void onBackPressed() {
        f();
    }

    public final void a(int i, int i2, Intent intent) {
        super.a(i, i2, intent);
        AccountActivityDelegate.a(i, i2, intent);
    }
}
