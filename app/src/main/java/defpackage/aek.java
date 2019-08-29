package defpackage;

import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.searchservice.service.search.SearchSuggServiceImpl$3;
import com.amap.bundle.searchservice.service.search.param.SuggestionParam;
import com.autonavi.bundle.entity.common.OfflineSearchMode;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* renamed from: aek reason: default package */
/* compiled from: SearchSuggServiceImpl */
public final class aek {
    final AosPostRequest a;
    AosPostRequest b;
    Map<AosPostRequest, adx> c;

    /* renamed from: aek$a */
    /* compiled from: SearchSuggServiceImpl */
    static class a {
        static final aek a = new aek(0);
    }

    /* synthetic */ aek(byte b2) {
        this();
    }

    private aek() {
        this.a = new AosPostRequest();
        this.b = null;
        this.c = new ConcurrentHashMap();
    }

    public final void a(SuggestionParam suggestionParam, int i, aeb<aux> aeb, adx adx) {
        OfflineSearchMode offlineSearchMode;
        if (i != 2) {
            offlineSearchMode = aez.a(2, suggestionParam.getKeyWord(), suggestionParam.getCenter());
        } else {
            offlineSearchMode = aez.a(suggestionParam.getKeyWord());
        }
        offlineSearchMode.searchType = i;
        new aeg();
        aeg.a(offlineSearchMode, new SearchSuggServiceImpl$3(this, adx, aeb));
    }
}
