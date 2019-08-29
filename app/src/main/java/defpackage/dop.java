package defpackage;

import android.content.Context;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import com.amap.bundle.searchservice.api.ISearchService;
import com.amap.bundle.searchservice.service.search.param.SuggestionParam;
import com.autonavi.bundle.entity.sugg.TipItem;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.db.helper.SearchHistoryHelper;
import com.autonavi.minimap.life.common.widget.view.headersearchview.HeaderSearchView;
import com.autonavi.minimap.life.common.widget.view.headersearchview.HeaderSearchViewPresenter$3;
import com.autonavi.server.request.AosInputSuggestionParam;
import java.lang.ref.SoftReference;
import java.util.List;

/* renamed from: dop reason: default package */
/* compiled from: HeaderSearchViewPresenter */
public final class dop {
    public HeaderSearchView a;
    Context b;
    a c;
    public Handler d = new Handler(new Callback() {
        public final boolean handleMessage(Message message) {
            AosInputSuggestionParam aosInputSuggestionParam = (AosInputSuggestionParam) message.getData().getSerializable("suggest_param");
            switch (message.what) {
                case 4097:
                    ISearchService iSearchService = (ISearchService) defpackage.esb.a.a.a(ISearchService.class);
                    if (iSearchService != null) {
                        SuggestionParam suggestionParam = new SuggestionParam();
                        suggestionParam.adcode = Long.valueOf(aosInputSuggestionParam.city).longValue();
                        suggestionParam.keyWord = aosInputSuggestionParam.words;
                        suggestionParam.category = aosInputSuggestionParam.category;
                        suggestionParam.suggestType = aosInputSuggestionParam.datatype;
                        suggestionParam.mCenter = new GeoPoint(Integer.valueOf(aosInputSuggestionParam.x).intValue(), Integer.valueOf(aosInputSuggestionParam.y).intValue());
                        iSearchService.a(suggestionParam, 2, (aeb<aux>) new aeb<aux>() {
                            public final /* bridge */ /* synthetic */ void callback(Object obj) {
                                dop.this.a.callback(((aux) obj).b);
                            }

                            public final void error(int i) {
                                dop.this.a.error(new Exception(), false);
                            }
                        });
                        break;
                    }
                    break;
                case 4098:
                    dop.this.a(aosInputSuggestionParam.words, aosInputSuggestionParam.longitude, aosInputSuggestionParam.latitude);
                    break;
            }
            return false;
        }
    });
    public bid e;
    private com.autonavi.common.Callback.a f;

    /* renamed from: dop$a */
    /* compiled from: HeaderSearchViewPresenter */
    static class a extends Handler {
        private SoftReference<HeaderSearchView> a;

        a(HeaderSearchView headerSearchView) {
            this.a = new SoftReference<>(headerSearchView);
        }

        public final void handleMessage(Message message) {
            HeaderSearchView headerSearchView = this.a.get();
            if (headerSearchView != null) {
                switch (message.what) {
                    case 0:
                        headerSearchView.refreshHistoryUi((List) message.obj);
                        return;
                    case 1:
                        headerSearchView.refreshInputSuggestUi((List) message.obj);
                        break;
                }
            }
        }
    }

    public dop(HeaderSearchView headerSearchView, Context context) {
        this.a = headerSearchView;
        this.b = context;
        this.c = new a(this.a);
    }

    public final void a(final int i, final int i2) {
        new Thread("HeaderSearchViewPresenterThread") {
            public final void run() {
                List<TipItem> tipItems = SearchHistoryHelper.getInstance(dop.this.b).getTipItems(i2);
                dop.this.c.removeMessages(0);
                dop.this.c.removeMessages(1);
                Message message = new Message();
                message.what = i;
                message.obj = tipItems;
                dop.this.c.sendMessage(message);
            }
        }.start();
    }

    public final void a() {
        if (this.f != null) {
            this.f.cancel();
        }
    }

    public final boolean a(String str, double d2, double d3) {
        if (this.e == null) {
            return false;
        }
        return new dpb().a(str, d2, d3, new HeaderSearchViewPresenter$3(this));
    }
}
