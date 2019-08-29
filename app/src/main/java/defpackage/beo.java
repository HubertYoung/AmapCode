package defpackage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPage;

/* renamed from: beo reason: default package */
/* compiled from: ITabPage */
public interface beo extends bid, IPage {
    void a(ben ben);

    void attach(Context context, LayoutInflater layoutInflater, AbstractBasePage abstractBasePage, akg akg, bul bul);

    boolean o();

    void onActivityResult(int i, int i2, Intent intent);

    void onNewIntent(PageBundle pageBundle);

    void onResult(int i, ResultType resultType, PageBundle pageBundle);

    void performCreate(Context context);

    void s();

    void t();
}
