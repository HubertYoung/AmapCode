package defpackage;

import android.widget.ListView;
import com.autonavi.bundle.entity.sugg.TipItem;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.route.bus.realtimebus.page.RTBusSearchBasePage;
import com.autonavi.minimap.search.view.SearchHistoryList;
import com.autonavi.minimap.search.view.SearchSuggestList;
import com.autonavi.minimap.widget.SearchEdit;
import com.tencent.connect.common.Constants;

/* renamed from: dyq reason: default package */
/* compiled from: RTBusSearchBasePresenter */
public abstract class dyq<Page extends RTBusSearchBasePage> extends eaf<Page> {
    public TipItem a;
    public String b = "";

    /* renamed from: dyq$1 reason: invalid class name */
    /* compiled from: RTBusSearchBasePresenter */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a = new int[ResultType.values().length];

        static {
            try {
                a[ResultType.OK.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    public abstract SearchHistoryList a(ListView listView);

    public abstract SearchSuggestList a(SearchEdit searchEdit, ListView listView);

    public abstract void a();

    public abstract void a(SearchEdit searchEdit);

    public abstract void a(String str);

    public abstract void a(String str, String str2);

    public abstract void b();

    public dyq(Page page) {
        super(page);
    }

    public void onPageCreated() {
        super.onPageCreated();
        a();
    }

    public void onStart() {
        super.onStart();
        ((RTBusSearchBasePage) this.mPage).a.b();
    }

    public void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i, resultType, pageBundle);
        if (AnonymousClass1.a[resultType.ordinal()] == 1 && i == 1 && "action_switch_city".equals(pageBundle.getString(Constants.KEY_ACTION))) {
            String str = "";
            if (pageBundle.containsKey("key_city_adcode")) {
                str = pageBundle.getString("key_city_adcode");
                ((RTBusSearchBasePage) this.mPage).a.b(str);
            }
            if (!str.equals(this.b)) {
                ((RTBusSearchBasePage) this.mPage).a.e().clearSuggestionData();
            }
            this.b = str;
            if (pageBundle.containsKey("key_area_name")) {
                ((RTBusSearchBasePage) this.mPage).a(pageBundle.getString("key_area_name"));
                ((RTBusSearchBasePage) this.mPage).c();
                b();
            }
        }
    }

    public void onDestroy() {
        super.onDestroy();
        ((RTBusSearchBasePage) this.mPage).a.c();
        ((RTBusSearchBasePage) this.mPage).a.d();
    }

    public void onNewIntent(PageBundle pageBundle) {
        super.onNewIntent(pageBundle);
        a();
    }
}
