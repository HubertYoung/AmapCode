package defpackage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import com.amap.bundle.searchservice.api.ISearchService;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.entity.infolite.external.CitySuggestion;
import com.autonavi.bundle.entity.sugg.TipItem;
import com.autonavi.common.PageBundle;
import com.autonavi.common.SuperId;
import com.autonavi.common.model.POI;
import com.autonavi.map.db.helper.SearchHistoryHelper;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.save.controller.SaveSearchUIController$4;
import com.autonavi.minimap.basemap.save.controller.SaveSearchUIController$7;
import com.autonavi.minimap.basemap.save.page.SaveSearchCitySuggestionPage;
import com.autonavi.minimap.basemap.save.page.SaveSearchErrorCityPage;
import com.autonavi.minimap.basemap.save.page.SaveSearchErrorSuggestionPage;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.route.bus.busline.presenter.BusLineStationListPresenter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressLint({"InflateParams"})
/* renamed from: cqz reason: default package */
/* compiled from: SaveSearchUIController */
public final class cqz implements aeb<aud> {
    @SuppressFBWarnings({"MS_PKGPROTECT"})
    public static String a = "";
    boolean b = false;
    public String c;
    private Context d;
    /* access modifiers changed from: private */
    public ProgressDlg e;
    private aud f;
    /* access modifiers changed from: private */
    public a g;
    /* access modifiers changed from: private */
    public aen h;
    private com.autonavi.minimap.basemap.save.page.SaveSearchErrorCityPage.a i = new com.autonavi.minimap.basemap.save.page.SaveSearchErrorCityPage.a() {
        public final void a(String str) {
            cqz.this.h.a(str);
            cqz.this.h.g = "400003";
            cqz.this.h.d = SuperId.getInstance().getScenceId();
            ISearchService iSearchService = (ISearchService) defpackage.esb.a.a.a(ISearchService.class);
            if (iSearchService != null) {
                cqz.this.a(iSearchService.b(aew.a((aem) cqz.this.h), 0, cqz.this), cqz.this.c);
            }
            cqz.this.a((adx) null, cqz.this.c);
        }

        public final void b(String str) {
            SuperId.getInstance().setBit3("08");
            cqz.this.h.a = str;
            cqz.this.c = str;
            cqz.this.h.d = SuperId.getInstance().getScenceId();
            ISearchService iSearchService = (ISearchService) defpackage.esb.a.a.a(ISearchService.class);
            if (iSearchService != null) {
                cqz.this.a(iSearchService.b(aew.a((aem) cqz.this.h), 0, cqz.this), cqz.this.c);
            }
        }
    };
    private com.autonavi.minimap.basemap.save.page.SaveSearchCitySuggestionPage.a j = new com.autonavi.minimap.basemap.save.page.SaveSearchCitySuggestionPage.a() {
        public final void a(String str) {
            cqz.a = str;
            cqz.this.h.a(cqz.a);
            cqz.this.h.g = "400003";
            SuperId.getInstance().setBit3("09");
            cqz.this.h.d = SuperId.getInstance().getScenceId();
            ISearchService iSearchService = (ISearchService) defpackage.esb.a.a.a(ISearchService.class);
            if (iSearchService != null) {
                cqz.this.a(iSearchService.b(aew.a((aem) cqz.this.h), 0, cqz.this), cqz.this.c);
            }
        }
    };

    /* renamed from: cqz$a */
    /* compiled from: SaveSearchUIController */
    public interface a {
        void a();

        void a(PageBundle pageBundle);

        void a(String str);
    }

    public final /* synthetic */ void callback(Object obj) {
        aud aud = (aud) obj;
        bck bck = (bck) defpackage.esb.a.a.a(bck.class);
        if (bck != null) {
            bck.c();
        }
        if (this.e != null && this.e.isShowing()) {
            this.e.dismiss();
        }
        if (!aud.c.isOnLine) {
            if ((aud.b == null || aud.b.d == null || aud.b.d.size() == 0) && aud.b != null && !TextUtils.isEmpty(aud.b.a.c)) {
                int parseInt = Integer.parseInt(aud.b.a.c);
                adz adz = (adz) defpackage.esb.a.a.a(adz.class);
                if (adz != null) {
                    ady b2 = adz.b();
                    if (b2 != null) {
                        b2.a(parseInt);
                    }
                }
            }
        } else if (aud.c.errorCode != 1) {
            ToastHelper.showLongToast(this.d.getResources().getString(R.string.ic_net_error_noresult));
            if (this.g != null) {
                this.g.a();
            }
            return;
        }
        this.f = aud;
        this.f.d = this.c;
        final TipItem tipItem = new TipItem();
        ArrayList<POI> arrayList = null;
        tipItem.name = this.c != null ? this.c.trim() : null;
        tipItem.historyType = 0;
        tipItem.time = new Date();
        ahm.a(new Runnable() {
            public final void run() {
                SearchHistoryHelper.getInstance(AMapAppGlobal.getApplication()).saveTipItem(tipItem);
            }
        });
        if (this.h.e > 1) {
            b();
        }
        ArrayList<String> arrayList2 = this.f.b.b;
        ArrayList<POI> arrayList3 = this.f.b.d;
        if (arrayList2 == null || arrayList2.size() <= 0 || arrayList3 == null || arrayList3.size() != 0) {
            ArrayList<CitySuggestion> arrayList4 = this.f.b.c;
            if (arrayList4 == null || arrayList4.size() <= 0) {
                ArrayList<POI> arrayList5 = this.f.b.d;
                ArrayList<String> arrayList6 = this.f.b.b;
                int i2 = this.f.b.e;
                if (arrayList5 != null && arrayList5.size() != 0 && i2 > 0) {
                    if (arrayList5.size() > 0 && i2 > 0) {
                        b();
                    }
                } else if (arrayList6 == null || arrayList6.size() <= 0) {
                    if (!(this.f == null || this.f.a == null)) {
                        arrayList = this.f.a.POIList;
                    }
                    if (arrayList != null) {
                        if (arrayList.size() > 1) {
                            a((List<POI>) arrayList, this.d.getString(R.string.Title_SelectLocate));
                        } else if (arrayList.size() == 1) {
                            this.f.b.d = this.f.a.POIList;
                            PageBundle pageBundle = new PageBundle();
                            pageBundle.putInt("PAGE_NUM", this.h.e);
                            pageBundle.putObject("poi_search_result", this.f);
                            pageBundle.putString(TrafficUtil.KEYWORD, this.c);
                            if (this.g != null) {
                                this.g.a(pageBundle);
                            }
                        } else {
                            ToastHelper.showLongToast(this.d.getResources().getString(R.string.ic_net_error_noresult));
                            if (this.g != null) {
                                this.g.a();
                            }
                        }
                    }
                } else {
                    b();
                }
            } else {
                PageBundle pageBundle2 = new PageBundle();
                pageBundle2.putObject(BusLineStationListPresenter.BUNDLE_KEY_RESULT_OBJ, this.f);
                pageBundle2.putObject(SaveSearchErrorSuggestionPage.BUNDLE_KEY_LISTENER, this.j);
                bid pageContext = AMapPageUtil.getPageContext();
                if (pageContext != null) {
                    pageContext.startPage(SaveSearchCitySuggestionPage.class, pageBundle2);
                }
            }
        } else if (this.f == null || this.f.b.c == null || this.f.b.c.size() == 0) {
            a(arrayList2);
        } else {
            PageBundle pageBundle3 = new PageBundle();
            pageBundle3.putObject(BusLineStationListPresenter.BUNDLE_KEY_RESULT_OBJ, this.f);
            pageBundle3.putString("bundle_key_keyword", this.c);
            pageBundle3.putObject("bundle_key_callback", this.i);
            bid pageContext2 = AMapPageUtil.getPageContext();
            if (pageContext2 != null) {
                pageContext2.startPage(SaveSearchErrorCityPage.class, pageBundle3);
            }
        }
    }

    public cqz(Context context, aen aen, a aVar) {
        this.d = context;
        this.h = aen;
        this.g = aVar;
        this.b = true;
    }

    public final void a() {
        if (!a.trim().equals("")) {
            this.h.a(a);
        }
        ISearchService iSearchService = (ISearchService) defpackage.esb.a.a.a(ISearchService.class);
        if (iSearchService != null) {
            a(iSearchService.b(aew.a((aem) this.h), 0, this), this.c);
        }
    }

    private void a(List<POI> list, String str) {
        if (this.d != null && (this.d instanceof Activity)) {
            int size = list.size();
            String[] strArr = new String[size];
            for (int i2 = 0; i2 < size; i2++) {
                strArr[i2] = list.get(i2).getName();
            }
            crc crc = new crc((Activity) this.d);
            crc.d = str;
            crc.c.setText(crc.d);
            crc.a = new ArrayAdapter(this.d, R.layout.list_dialog_item_1, strArr);
            crc.b.setAdapter(crc.a);
            crc.e = new SaveSearchUIController$4(this, crc);
            crc.b.setOnItemClickListener(crc.e);
            crc.show();
        }
    }

    private void a(ArrayList<String> arrayList) {
        int size = arrayList.size();
        String[] strArr = new String[size];
        for (int i2 = 0; i2 < size; i2++) {
            strArr[i2] = arrayList.get(i2);
        }
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString("bundle_key_keyword", this.c);
        pageBundle.putObject("bunde_key_selected", strArr);
        pageBundle.putObject(SaveSearchErrorSuggestionPage.BUNDLE_KEY_LISTENER, new SaveSearchUIController$7(this, strArr));
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            pageContext.startPage(SaveSearchErrorSuggestionPage.class, pageBundle);
        }
    }

    private void b() {
        ArrayList<POI> a2 = crs.a(this.h.e, this.f);
        if (a2 != null && !this.f.c.isOnLine) {
            this.f.b.d = a2;
        }
        PageBundle pageBundle = new PageBundle();
        pageBundle.putInt("PAGE_NUM", this.h.e);
        pageBundle.putObject("poi_search_result", this.f);
        pageBundle.putString(TrafficUtil.KEYWORD, this.c);
        if (this.g != null) {
            this.g.a(pageBundle);
        }
    }

    public final void a(final adx adx, String str) {
        String str2;
        if (this.d != null && (this.d instanceof Activity)) {
            if (str == null || "".equals(str)) {
                str2 = "正在搜索";
            } else {
                StringBuilder sb = new StringBuilder("正在搜索\"");
                sb.append(str);
                sb.append("\"");
                str2 = sb.toString();
            }
            if (this.e == null) {
                this.e = new ProgressDlg((Activity) this.d, str2, "");
            }
            this.e.setMessage(str2);
            this.e.setCancelable(true);
            this.e.setOnCancelListener(new OnCancelListener() {
                public final void onCancel(DialogInterface dialogInterface) {
                    if (adx != null) {
                        adx.a();
                        if (cqz.this.g != null) {
                            cqz.this.g.a();
                        }
                    }
                }
            });
            this.e.getLoadingView().setOnCloseClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    if (adx != null && cqz.this.e != null) {
                        adx.a();
                        if (cqz.this.g != null) {
                            cqz.this.g.a();
                            cqz.this.e.dismiss();
                        }
                    }
                }
            });
            this.e.show();
        }
    }

    public final void error(int i2) {
        bck bck = (bck) defpackage.esb.a.a.a(bck.class);
        if (bck != null) {
            bck.c();
        }
        if (this.e != null && this.e.isShowing()) {
            this.e.dismiss();
        }
        ToastHelper.showLongToast(this.d.getString(R.string.network_error_message));
    }
}
