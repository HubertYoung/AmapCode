package defpackage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import com.amap.bundle.utils.ui.CompatDialog;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.entity.infolite.internal.Condition;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.share.entity.ShareParam;
import com.autonavi.minimap.bundle.share.entity.ShareParam.e;
import com.autonavi.minimap.bundle.share.entity.ShareParam.f;
import com.autonavi.minimap.drive.trafficboard.page.TrafficBoardPage;
import com.autonavi.minimap.drive.trafficboard.presenter.TrafficBoardPresenter$4;
import com.autonavi.minimap.drive.trafficboard.widget.FilterPopup$5;
import com.autonavi.minimap.drive.trafficboard.widget.FilterPopup$6;
import com.autonavi.minimap.drive.trafficboard.widget.TrafficTopListAdapter;
import com.autonavi.minimap.transfer.TransferRequestHolder;
import com.autonavi.minimap.transfer.param.AuthTrafficCongestionRequest;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.d;
import java.util.ArrayList;
import java.util.HashMap;

/* renamed from: djw reason: default package */
/* compiled from: TrafficBoardPresenter */
public final class djw extends AbstractBasePresenter<TrafficBoardPage> {
    public djv a;
    public TrafficTopListAdapter b;
    public final OnClickListener c = new OnClickListener() {
        public final void onClick(View view) {
            ((TrafficBoardPage) djw.this.mPage).finish();
        }
    };
    public final OnClickListener d = new OnClickListener() {
        public final void onClick(View view) {
            if (!aaw.c(AMapAppGlobal.getApplication())) {
                ToastHelper.showLongToast(AMapAppGlobal.getApplication().getResources().getString(R.string.act_update_nonet));
            } else {
                djw.b(djw.this);
            }
        }
    };
    public final defpackage.djz.a e = new defpackage.djz.a() {
        public final void a(int i, Condition condition) {
            if (i == 0) {
                djw.this.b("", condition.checkedValue, djw.this.h);
                return;
            }
            if (1 == i) {
                djw.this.b("", djw.this.a.d(), condition.value);
                djw.this.h = condition.value;
            }
        }
    };
    public final d f = new d() {
        public final void onPullUpToRefresh(PullToRefreshBase pullToRefreshBase) {
        }

        public final void onPullDownToRefresh(PullToRefreshBase pullToRefreshBase) {
            String str;
            if (djw.this.a != null) {
                djw djw = djw.this;
                String d = djw.this.a.d();
                d b = djw.this.a.b();
                if (b != null) {
                    str = b.a;
                } else {
                    str = "1";
                }
                djw.a("", d, str);
                return;
            }
            djw.this.a("", "", djw.this.h);
        }
    };
    private Handler g;
    /* access modifiers changed from: private */
    public String h = "1";

    /* renamed from: djw$a */
    /* compiled from: TrafficBoardPresenter */
    class a implements Runnable {
        a() {
        }

        public final void run() {
            ((TrafficBoardPage) djw.this.mPage).b();
        }
    }

    public djw(TrafficBoardPage trafficBoardPage) {
        super(trafficBoardPage);
    }

    public final void onPageCreated() {
        super.onPageCreated();
        this.g = new Handler();
        this.h = "1";
        a("", "", this.h);
    }

    public final void onStart() {
        super.onStart();
        ((TrafficBoardPage) this.mPage).setSoftInputMode(18);
    }

    /* access modifiers changed from: private */
    public void a(String str, String str2, String str3) {
        if (((TrafficBoardPage) this.mPage).c() || ((TrafficBoardPage) this.mPage).h || this.a == null) {
            b(str, str2, str3);
            ((TrafficBoardPage) this.mPage).a = System.currentTimeMillis();
            return;
        }
        this.g.post(new a());
    }

    /* access modifiers changed from: private */
    public void b(String str, String str2, String str3) {
        AuthTrafficCongestionRequest authTrafficCongestionRequest = new AuthTrafficCongestionRequest();
        authTrafficCongestionRequest.e = str2;
        authTrafficCongestionRequest.f = str3;
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
        if (latestPosition != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(latestPosition.getLongitude());
            authTrafficCongestionRequest.c = sb.toString();
            StringBuilder sb2 = new StringBuilder();
            sb2.append(latestPosition.getLatitude());
            authTrafficCongestionRequest.d = sb2.toString();
        }
        authTrafficCongestionRequest.b = str;
        CompatDialog a2 = aav.a(authTrafficCongestionRequest, "正在请求网络");
        a2.show();
        TransferRequestHolder.getInstance().sendAuthTrafficCongestion(authTrafficCongestionRequest, new TrafficBoardPresenter$4(this, a2));
        ((TrafficBoardPage) this.mPage).a = System.currentTimeMillis();
    }

    static /* synthetic */ void b(djw djw) {
        if (!(djw.a == null || djw.a.e == null)) {
            if (!TextUtils.isEmpty(djw.a.e.d)) {
                HashMap hashMap = new HashMap();
                Bitmap decodeResource = BitmapFactory.decodeResource(((TrafficBoardPage) djw.mPage).getResources(), R.drawable.ic_action_social_share);
                String[] strArr = {"weibo", "weixin", "pengyou"};
                for (int i = 0; i < 3; i++) {
                    String str = strArr[i];
                    dcq dcq = new dcq();
                    StringBuilder sb = new StringBuilder();
                    sb.append(djw.a.e.b);
                    sb.append(((TrafficBoardPage) djw.mPage).getString(R.string.traffic_board_congestion_ranking));
                    dcq.title = sb.toString();
                    dcq.content = djw.a.e.c;
                    dcq.url = djw.a.e.d;
                    dcq.imgBitmap = decodeResource;
                    hashMap.put(str, dcq);
                }
                dct dct = new dct(0);
                dct.f = true;
                dct.d = true;
                dct.e = true;
                dct.g = true;
                dct.h = true;
                if (hashMap.size() == 0) {
                    ToastHelper.showLongToast(AMapAppGlobal.getApplication().getApplicationContext().getResources().getString(R.string.share_content_is_empty));
                    return;
                }
                final dcq dcq2 = (dcq) hashMap.get("weibo");
                final dcq dcq3 = (dcq) hashMap.get("weixin");
                final dcq dcq4 = (dcq) hashMap.get("pengyou");
                dcb dcb = (dcb) defpackage.esb.a.a.a(dcb.class);
                if (dcb != null) {
                    final dcb dcb2 = dcb;
                    AnonymousClass6 r3 = new dcd() {
                        public final ShareParam getShareDataByType(int i) {
                            switch (i) {
                                case 3:
                                    e eVar = new e(0);
                                    eVar.f = dcq3.title;
                                    eVar.a = dcq3.content;
                                    eVar.g = dcq3.imgBitmap;
                                    eVar.b = dcb2.a(dcq3);
                                    eVar.c = dcq3.needToShortUrl;
                                    eVar.e = 0;
                                    return eVar;
                                case 4:
                                    e eVar2 = new e(1);
                                    eVar2.f = dcq4.title;
                                    eVar2.a = dcq4.content;
                                    eVar2.g = dcq4.imgBitmap;
                                    eVar2.b = dcb2.a(dcq4);
                                    eVar2.c = dcq4.needToShortUrl;
                                    eVar2.e = 0;
                                    return eVar2;
                                case 5:
                                    f fVar = new f();
                                    fVar.a = dcq2.content;
                                    fVar.b = dcb2.a(dcq2);
                                    fVar.c = dcq2.needToShortUrl;
                                    return fVar;
                                default:
                                    return null;
                            }
                        }
                    };
                    dcb.a((bid) djw.mPage, dct, (dcd) r3);
                }
                return;
            }
            TrafficBoardPage trafficBoardPage = (TrafficBoardPage) djw.mPage;
            if (((djw) trafficBoardPage.mPresenter).b != null) {
                String sharePicPath = djy.getSharePicPath(djy.PIC_FILE_NAME);
                if (!TextUtils.isEmpty(sharePicPath)) {
                    ahd.c(sharePicPath);
                }
                String sharePicPath2 = djy.getSharePicPath(djy.PIC_THUMBNAIL_FILE_NAME);
                if (!TextUtils.isEmpty(sharePicPath2)) {
                    ahd.c(sharePicPath2);
                }
                trafficBoardPage.b.setDrawingCacheEnabled(true);
                Bitmap newBitmap = djy.newBitmap(trafficBoardPage.b.getDrawingCache(), djy.convertViewToBitmap(trafficBoardPage.c.getListView(), ((djw) trafficBoardPage.mPresenter).b));
                djy.setShareBitmap(ahc.a(newBitmap, newBitmap.getWidth() >> 3, newBitmap.getHeight() >> 3), djy.PIC_THUMBNAIL_FILE_NAME);
                djy.setShareBitmap(newBitmap, djy.PIC_FILE_NAME);
                dct dct2 = new dct(0);
                dct2.f = true;
                dct2.d = true;
                dct2.e = true;
                dct2.g = true;
                dct2.h = true;
                djw djw2 = (djw) trafficBoardPage.mPresenter;
                final String str2 = ((djw) trafficBoardPage.mPresenter).a.c;
                dcb dcb3 = (dcb) defpackage.esb.a.a.a(dcb.class);
                if (dcb3 != null) {
                    dcb3.a((bid) djw2.mPage, dct2, (dcd) new dcd() {
                        public final ShareParam getShareDataByType(int i) {
                            switch (i) {
                                case 3:
                                    e eVar = new e(0);
                                    eVar.g = djy.getThumbnailsBitmap(djy.getSharePicPath(djy.PIC_THUMBNAIL_FILE_NAME));
                                    eVar.h = djy.getSharePicPath(djy.PIC_FILE_NAME);
                                    eVar.e = 3;
                                    return eVar;
                                case 4:
                                    e eVar2 = new e(1);
                                    eVar2.g = djy.getThumbnailsBitmap(djy.getSharePicPath(djy.PIC_THUMBNAIL_FILE_NAME));
                                    eVar2.h = djy.getSharePicPath(djy.PIC_FILE_NAME);
                                    eVar2.e = 3;
                                    return eVar2;
                                case 5:
                                    f fVar = new f();
                                    fVar.a = TextUtils.isEmpty(str2) ? ((TrafficBoardPage) djw.this.mPage).getString(R.string.traffic_board_congestion_result) : str2;
                                    fVar.j = true;
                                    fVar.h = djy.getSharePicPath(djy.PIC_FILE_NAME);
                                    return fVar;
                                default:
                                    return null;
                            }
                        }
                    });
                }
                trafficBoardPage.b.setDrawingCacheEnabled(false);
            }
        }
    }

    public static /* synthetic */ void b(djw djw, djv djv) {
        djw.b = new TrafficTopListAdapter(AMapAppGlobal.getApplication().getApplicationContext(), djv.h.get(0).b);
        ((TrafficBoardPage) djw.mPage).c.setAdapter(djw.b);
        TrafficBoardPage trafficBoardPage = (TrafficBoardPage) djw.mPage;
        djv djv2 = ((djw) trafficBoardPage.mPresenter).a;
        ArrayList arrayList = new ArrayList();
        Condition a2 = djv2.a();
        Condition c2 = djv2.c();
        arrayList.add(a2);
        arrayList.add(c2);
        if (arrayList.size() > 0) {
            trafficBoardPage.d.setVisibility(0);
            djz djz = trafficBoardPage.e;
            djz.k = arrayList;
            if (!(djz.k == null || djz.k.size() == 0)) {
                if (djz.b != null && djz.b.isShowing()) {
                    djz.b.dismiss();
                }
                if (!djz.n) {
                    djz.i[djz.i.length - 1].setVisibility(8);
                    djz.h[djz.h.length - 1].setVisibility(8);
                }
                for (int i = 0; i < djz.h.length - 1; i++) {
                    if (i < arrayList.size()) {
                        Condition condition = (Condition) arrayList.get(i);
                        if (condition == null) {
                            djz.h[i].setEnabled(false);
                            djz.i[i].setEnabled(false);
                        } else if (condition.subConditions == null) {
                            djz.h[i].setText(condition.name);
                            djz.h[i].setEnabled(false);
                            djz.h[i].setTextColor(djz.m.getResources().getColor(R.color.f_c_3));
                            djz.i[i].setEnabled(false);
                        } else {
                            if (TextUtils.isEmpty(condition.displayName)) {
                                djz.h[i].setText(condition.name);
                            } else {
                                djz.h[i].setText(condition.displayName);
                            }
                            djz.h[i].setEnabled(true);
                            djz.i[i].setEnabled(true);
                            djz.h[i].setTextColor(djz.m.getResources().getColorStateList(R.color.filter_text_click_selector));
                        }
                        djz.h[i].setVisibility(0);
                        djz.i[i].setVisibility(0);
                        if (djz.o[i] == null || djz.o[i].length() == 0) {
                            djz.o[i] = djz.h[i].getText().toString();
                        }
                    } else {
                        djz.h[i].setVisibility(8);
                        djz.i[i].setVisibility(8);
                    }
                    djz.i[i].setOnClickListener(new OnClickListener(i) {
                        final /* synthetic */ int a;

                        {
                            this.a = r2;
                        }

                        public final void onClick(View view) {
                            djz.this.a(this.a);
                        }
                    });
                }
                if (djz.j != null) {
                    djz.j.setOnClickListener(new OnClickListener() {
                        public final void onClick(View view) {
                            djz.this.r.a(1048712, null);
                        }
                    });
                }
                djz.e.setOnItemClickListener(new FilterPopup$5(djz));
                djz.f.setOnItemClickListener(new FilterPopup$6(djz));
                djz.g.setOnClickListener(new OnClickListener() {
                    public final void onClick(View view) {
                        if (djz.this.b != null && djz.this.b.isShowing()) {
                            djz.this.b.dismiss();
                        }
                    }
                });
            }
            for (int i2 = 0; i2 < trafficBoardPage.f.length; i2++) {
                if (i2 < arrayList.size()) {
                    Condition condition2 = (Condition) arrayList.get(i2);
                    if (condition2 == null) {
                        trafficBoardPage.f[i2].setEnabled(false);
                        trafficBoardPage.g[i2].setEnabled(false);
                    } else if (TextUtils.isEmpty(condition2.displayName)) {
                        trafficBoardPage.f[i2].setText(condition2.name);
                    } else {
                        trafficBoardPage.f[i2].setText(condition2.displayName);
                    }
                    trafficBoardPage.f[i2].setTextColor(trafficBoardPage.getResources().getColor(R.color.f_c_3));
                } else {
                    trafficBoardPage.f[i2].setVisibility(8);
                    trafficBoardPage.g[i2].setVisibility(8);
                }
            }
        } else {
            trafficBoardPage.d.setVisibility(8);
        }
        ((TrafficBoardPage) djw.mPage).b();
        ((TrafficBoardPage) djw.mPage).a();
    }
}
