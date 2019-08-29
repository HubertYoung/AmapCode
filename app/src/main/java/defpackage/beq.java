package defpackage;

import android.content.Intent;
import android.content.res.Configuration;
import com.autonavi.bundle.uitemplate.tab.TabHostPage;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;

/* renamed from: beq reason: default package */
/* compiled from: TabHostPresenter */
public abstract class beq<THP extends TabHostPage> extends AbstractBasePresenter {
    protected final THP a;

    public void onPageCreated() {
        super.onPageCreated();
        this.a.h();
    }

    public beq(THP thp) {
        super(thp);
        this.a = thp;
    }

    public void onNewIntent(PageBundle pageBundle) {
        super.onNewIntent(pageBundle);
        if (!this.a.a(pageBundle)) {
            TabHostPage.a(this.a.e(), pageBundle);
        }
    }

    public void onStart() {
        super.onStart();
        TabHostPage.a(this.a.e());
    }

    public void onResume() {
        super.onResume();
        TabHostPage.b(this.a.e());
    }

    public void onPause() {
        super.onPause();
        TabHostPage.c(this.a.e());
    }

    public void onStop() {
        super.onStop();
        TabHostPage.d(this.a.e());
    }

    public void onDestroy() {
        super.onDestroy();
        THP thp = this.a;
        for (bep bep : thp.b) {
            TabHostPage.e(thp.f.get(bep.d));
        }
    }

    public void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i, resultType, pageBundle);
        TabHostPage.a(this.a.e(), i, resultType, pageBundle);
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        TabHostPage.a(this.a.e(), i, i2, intent);
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        TabHostPage.a(this.a.e(), configuration);
    }
}
