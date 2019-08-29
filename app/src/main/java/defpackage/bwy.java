package defpackage;

import android.app.Activity;
import android.widget.ListView;
import com.amap.bundle.searchservice.api.ISearchService;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bl.search.InfoliteParam;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.bundle.routecommon.model.OfflineMsgCode;
import com.autonavi.common.model.POI;
import com.autonavi.map.search.adapter.PoiSelectAdapter;
import com.autonavi.minimap.widget.ListDialog;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.d;
import java.util.ArrayList;

/* renamed from: bwy reason: default package */
/* compiled from: PoiSearcherDialogHelper */
public final class bwy {
    public Activity a;
    public InfoliteResult b = null;
    public ListDialog c;
    public PoiSelectAdapter d;
    int e;
    a f = new a(this, 0);
    private d<ListView> g = new d<ListView>() {
        public final void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
            pullToRefreshBase.postDelayed(new Runnable() {
                public final void run() {
                    AnonymousClass1.a(AnonymousClass1.this, false);
                }
            }, 10);
        }

        public final void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
            pullToRefreshBase.postDelayed(new Runnable() {
                public final void run() {
                    AnonymousClass1.a(AnonymousClass1.this, true);
                }
            }, 10);
        }

        static /* synthetic */ void a(AnonymousClass1 r3, boolean z) {
            if (!aaw.c(bwy.this.a)) {
                ToastHelper.showToast(OfflineMsgCode.CODE_NATIVE_POI_NORESULT_INOFFLIEDATA.getStrCodeMsg());
                bwy.this.c.updatePullList(bwy.this.b.mWrapper.pagenum, bcy.m(bwy.this.b));
                return;
            }
            int i = 1;
            if (z) {
                i = 1 + bwy.this.b.mWrapper.pagenum;
                if (i >= bcy.m(bwy.this.b)) {
                    i = bcy.m(bwy.this.b);
                }
            } else {
                int i2 = bwy.this.b.mWrapper.pagenum - 1;
                if (i2 > 1) {
                    i = i2;
                }
            }
            InfoliteParam clone = bwy.this.b.mWrapper.clone();
            clone.pagenum = i;
            new ekv().a(clone, 2, bwy.this.f);
            ISearchService iSearchService = (ISearchService) defpackage.esb.a.a.a(ISearchService.class);
            if (iSearchService != null) {
                iSearchService.a(clone, 2, (aeb<InfoliteResult>) new aeb<InfoliteResult>() {
                    public final /* synthetic */ void callback(Object obj) {
                        bwy.this.f.callback((InfoliteResult) obj);
                    }

                    public final void error(int i) {
                        bwy.this.f.error(i);
                    }
                });
            }
        }
    };

    /* renamed from: bwy$a */
    /* compiled from: PoiSearcherDialogHelper */
    class a extends aea<InfoliteResult> {
        private a() {
        }

        /* synthetic */ a(bwy bwy, byte b2) {
            this();
        }

        /* renamed from: a */
        public final void callback(InfoliteResult infoliteResult) {
            if (infoliteResult != null && infoliteResult.mWrapper != null) {
                bwy.this.b = infoliteResult;
                final int i = bwy.this.b.mWrapper.pagenum;
                final int m = bcy.m(bwy.this.b);
                if (bwy.this.c != null && (bwy.this.c.getAdapter() instanceof PoiSelectAdapter)) {
                    final PoiSelectAdapter poiSelectAdapter = (PoiSelectAdapter) bwy.this.c.getAdapter();
                    ArrayList<POI> arrayList = null;
                    if (bwy.this.b.routingInfo != null) {
                        if (bwy.this.e == 1) {
                            arrayList = bwy.this.b.routingInfo.c;
                        } else if (bwy.this.e == 2) {
                            arrayList = bwy.this.b.routingInfo.h;
                        }
                        final ArrayList<POI> arrayList2 = arrayList;
                        if (poiSelectAdapter != null && arrayList2 != null) {
                            AnonymousClass1 r0 = new Runnable() {
                                public final void run() {
                                    bwy.this.c.updatePullList(i, m);
                                    poiSelectAdapter.updateListData(arrayList2);
                                }
                            };
                            aho.a(r0);
                        }
                    }
                }
            }
        }

        public final void error(int i) {
            aho.a(new Runnable() {
                public final void run() {
                    ToastHelper.showLongToast(OfflineMsgCode.CODE_NATIVE_POI_NORESULT.getStrCodeMsg());
                    bwy.this.c.updatePullList(bwy.this.b.mWrapper.pagenum, bcy.m(bwy.this.b));
                }
            });
        }
    }

    public bwy(Activity activity, InfoliteResult infoliteResult, int i) {
        this.a = activity;
        this.b = infoliteResult;
        this.c = new ListDialog(this.a, this.g);
        this.e = i;
    }
}
