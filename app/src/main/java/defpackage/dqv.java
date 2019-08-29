package defpackage;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.searchservice.api.ISearchService;
import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.common.Callback;
import com.autonavi.common.model.POI;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/* renamed from: dqv reason: default package */
/* compiled from: SketchScenicPoiFetcher */
public class dqv {
    /* access modifiers changed from: private */
    public static final String b = "dqv";
    public ArrayList<String> a = new ArrayList<>();
    @NonNull
    private final ISearchService c = ((ISearchService) a.a.a(ISearchService.class));
    /* access modifiers changed from: private */
    public ConcurrentHashMap<String, SearchPoi> d = new ConcurrentHashMap<>();
    /* access modifiers changed from: private */
    public ArrayList<String> e = new ArrayList<>();

    public final boolean a(String str) {
        return this.e.contains(str);
    }

    public final SearchPoi b(String str) {
        return this.d.get(str);
    }

    public final void a(final String str, final Callback<SearchPoi> callback) {
        if (!TextUtils.isEmpty(str) && this.c != null && !this.a.contains(str)) {
            this.a.add(str);
            ael ael = new ael(str);
            ael.l = "q_81";
            this.c.b(aew.a((aem) ael), 2, new aeb<aud>() {
                public final /* synthetic */ void callback(Object obj) {
                    aud aud = (aud) obj;
                    if (!dqv.this.a.contains(str)) {
                        if (bno.a) {
                            AMapLog.i(dqv.b, "SketchScenicPoiFetcher.fetchFromAOS: No need to callback", true);
                        }
                        return;
                    }
                    auc auc = aud.b;
                    if (auc != null) {
                        ArrayList<POI> arrayList = auc.d;
                        if (arrayList != null && !arrayList.isEmpty()) {
                            POI poi = arrayList.get(0);
                            if (poi instanceof SearchPoi) {
                                SearchPoi searchPoi = (SearchPoi) poi;
                                if (TextUtils.equals(searchPoi.getShowSketchingMap(), "1")) {
                                    dqv.this.d.put(str, searchPoi);
                                    callback.callback(searchPoi);
                                } else {
                                    dqv.this.e.add(str);
                                    callback.callback(null);
                                }
                                dqv.this.a.remove(str);
                                return;
                            }
                        }
                    }
                    callback.error(null, true);
                    dqv.this.a.remove(str);
                }

                public final void error(int i) {
                    callback.error(new Exception(), false);
                    dqv.this.a.remove(str);
                }
            });
        }
    }
}
