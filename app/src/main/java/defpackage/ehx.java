package defpackage;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageFramework;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.route.sharebike.model.RideState;
import com.autonavi.minimap.route.sharebike.page.ShareBikePage;
import com.autonavi.minimap.route.sharebike.page.ShareRidingFinishPage;
import com.autonavi.minimap.route.sharebike.page.ShareRidingMapPage;
import com.autonavi.minimap.statusbar.StatusBarManager;
import com.autonavi.minimap.statusbar.StatusBarManager.FeatureType;
import com.autonavi.minimap.statusbar.StatusBarManager.ShortCompatStatusBar;
import com.autonavi.minimap.widget.ConfirmDlg;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/* renamed from: ehx reason: default package */
/* compiled from: BlueStateBarPolicy */
public final class ehx extends ehw {
    public static volatile ehx f;
    static final Hashtable<String, String> g;
    public volatile String a = "";
    public volatile RideState b = null;
    public HandlerThread c = new HandlerThread(getClass().getName());
    public Handler d;
    public boolean e;
    public d h = new d() {
        public final void onPageLifePaused(@NonNull WeakReference<AbstractBasePage> weakReference) {
        }

        public final void onPageLifeResumed(@NonNull WeakReference<AbstractBasePage> weakReference) {
            AbstractBasePage abstractBasePage = (AbstractBasePage) weakReference.get();
            if (abstractBasePage != null && !abstractBasePage.isTransparent()) {
                ehx.this.c(abstractBasePage.getClass().getSimpleName());
            }
        }
    };

    static {
        Hashtable<String, String> hashtable = new Hashtable<>(20);
        g = hashtable;
        hashtable.put(ShareRidingMapPage.class.getSimpleName(), ShareRidingMapPage.class.getSimpleName());
        avi avi = (avi) a.a.a(avi.class);
        if (avi != null) {
            String simpleName = avi.c().a(1).getSimpleName();
            String simpleName2 = avi.c().a(3).getSimpleName();
            g.put(simpleName, simpleName);
            g.put(simpleName2, simpleName2);
        }
        aww aww = (aww) a.a.a(aww.class);
        if (aww != null) {
            String simpleName3 = aww.a().a(1).getSimpleName();
            g.put(simpleName3, simpleName3);
            String simpleName4 = aww.a().a(2).getSimpleName();
            g.put(simpleName4, simpleName4);
        }
        g.put(NodeAlertDialogPage.class.getSimpleName(), NodeAlertDialogPage.class.getSimpleName());
        g.put(ShareRidingFinishPage.class.getSimpleName(), ShareRidingFinishPage.class.getSimpleName());
        avo avo = (avo) a.a.a(avo.class);
        if (avo != null) {
            String simpleName5 = avo.a().a(2).getSimpleName();
            g.put(simpleName5, simpleName5);
        }
        avn avn = (avn) a.a.a(avn.class);
        if (avn != null) {
            String simpleName6 = avn.a().a(2).getSimpleName();
            g.put(simpleName6, simpleName6);
        }
        g.put(ShareBikePage.class.getSimpleName(), ShareBikePage.class.getSimpleName());
        djk djk = (djk) ank.a(djk.class);
        if (djk != null) {
            List<String> n = djk.n();
            if (n.size() > 0) {
                for (String next : n) {
                    g.put(next, next);
                }
            }
        }
    }

    public ehx() {
        this.c.start();
        this.d = new Handler(this.c.getLooper());
        drp.b().a((c) this.h);
        this.e = false;
        StringBuilder sb = new StringBuilder("blue stat init");
        sb.append(this.e);
        eao.d("wbsww", sb.toString());
        f = this;
    }

    /* access modifiers changed from: protected */
    public final boolean a(String str) {
        StringBuilder sb = new StringBuilder("isInNavigating(className: ");
        sb.append(str);
        sb.append(")");
        eao.a((String) "wbsww", sb.toString());
        if (g == null) {
            return false;
        }
        if (g.containsKey(str)) {
            StringBuilder sb2 = new StringBuilder("AVOID_PAGES contains (className: ");
            sb2.append(str);
            sb2.append(")");
            eao.a((String) "wbsww", sb2.toString());
            return true;
        }
        bid pageContext = AMapPageFramework.getPageContext();
        if (pageContext != null && g.containsKey(pageContext.getClass().getSimpleName())) {
            return true;
        }
        eao.d("wbsww", String.valueOf(pageContext));
        if (pageContext instanceof Ajx3Page) {
            String ajx3Url = ((Ajx3Page) pageContext).getAjx3Url();
            if (ajx3Url != null && ajx3Url.endsWith("ShareBikeScanResult.page.js")) {
                return true;
            }
        }
        aix aix = (aix) a.a.a(aix.class);
        if (aix != null && aix.a(pageContext)) {
            bui mVPActivityContext = AMapPageUtil.getMVPActivityContext();
            if (mVPActivityContext != null) {
                bid a2 = mVPActivityContext.a(0);
                bid a3 = mVPActivityContext.a(1);
                StringBuilder sb3 = new StringBuilder("1ST=");
                sb3.append(a2);
                sb3.append("2nd=");
                sb3.append(a3);
                eao.d("wbsww", sb3.toString());
                if (a3 instanceof Ajx3Page) {
                    String ajx3Url2 = ((Ajx3Page) a3).getAjx3Url();
                    if (ajx3Url2 == null || !ajx3Url2.endsWith("ShareBikeScanResult.page.js")) {
                        return false;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public final void a(int i) {
        StringBuilder sb = new StringBuilder("blueBarController, status =");
        sb.append(i);
        sb.append(", currentTopPage:");
        sb.append(this.a);
        eao.d("wbsww", sb.toString());
        int i2 = (!Boolean.parseBoolean(ehs.b("share_bike_unlocking_status_id")) || i == 3) ? 0 : 2;
        if (i == 2) {
            i2 = 2;
        }
        Handler handler = new Handler(AMapPageFramework.getAppContext().getMainLooper());
        eao.d("wbsww", "unlocking =".concat(String.valueOf(i2)));
        if ((i == 0 || i == 3) && i2 != 2) {
            handler.post(new Runnable() {
                public final void run() {
                    StatusBarManager.d().a(FeatureType.TYPE_BICYCLE);
                }
            });
            return;
        }
        if (i == 1 || i2 == 2) {
            if (a(this.a) || b()) {
                handler.post(new Runnable() {
                    public final void run() {
                        StatusBarManager.d().d(FeatureType.TYPE_BICYCLE);
                    }
                });
            } else {
                eao.a((String) "wbsww", (String) "notifyBlueBar()");
                Context appContext = AMapPageFramework.getAppContext();
                final ShortCompatStatusBar shortCompatStatusBar = new ShortCompatStatusBar(appContext);
                StringBuilder sb2 = new StringBuilder();
                sb2.append(appContext.getString(R.string.share_bike_content_in_blue_bar));
                sb2.append("  >");
                shortCompatStatusBar.setTextCompatShortCut(sb2.toString(), appContext.getString(R.string.share_bike_content_in_blue_bar_short));
                shortCompatStatusBar.setOnClickListener(new OnClickListener() {
                    public final void onClick(View view) {
                        if (ehx.g != null && !ehx.g.containsKey(ShareRidingMapPage.class.getSimpleName())) {
                            ehx.g.put(ShareRidingMapPage.class.getSimpleName(), ShareRidingMapPage.class.getSimpleName());
                        }
                        bid pageContext = AMapPageFramework.getPageContext();
                        if (pageContext != null) {
                            boolean a2 = ehx.a();
                            Boolean.parseBoolean(ehs.b("share_bike_unlocking_status_id"));
                            if (!a2) {
                                pageContext.startPage((String) "amap.basemap.action.default_page", (PageBundle) null);
                            }
                            PageBundle pageBundle = new PageBundle();
                            String b = ehs.b("share_bike_cp_source");
                            String b2 = ehs.b("share_bike_id");
                            String b3 = ehs.b("share_bike_order_id");
                            StringBuilder sb = new StringBuilder("cpsource=");
                            sb.append(b);
                            sb.append("|bikeid=");
                            sb.append(b2);
                            sb.append("|orderId=");
                            sb.append(b3);
                            eao.d("wbsww", sb.toString());
                            pageBundle.putString("CpSource", b);
                            pageBundle.putString("OrderId", b3);
                            pageBundle.putString("BikeId", b2);
                            pageBundle.putString("bundle_key_page_from", "5");
                            if (a2) {
                                pageBundle.putString("gjxq", "gjxq");
                            }
                            pageContext.startPage(ShareRidingMapPage.class, pageBundle);
                        }
                    }
                });
                new Handler(appContext.getMainLooper()).post(new Runnable() {
                    public final void run() {
                        eao.a((String) "wbsww", (String) "notifyBlueBar, runnable run");
                        if (!StatusBarManager.d().b(FeatureType.TYPE_BICYCLE)) {
                            StatusBarManager.d().a(FeatureType.TYPE_BICYCLE, shortCompatStatusBar);
                        }
                        StatusBarManager.d().c(FeatureType.TYPE_BICYCLE);
                    }
                });
            }
        }
    }

    public static void b(String str) {
        if (ConfirmDlg.class.getSimpleName().equals(str) && g != null) {
            g.remove(ShareRidingMapPage.class.getSimpleName());
        }
        if (ShareRidingMapPage.class.getSimpleName().equals(str) && g != null) {
            g.put(ShareRidingMapPage.class.getSimpleName(), ShareRidingMapPage.class.getSimpleName());
        }
        if (f != null) {
            f.c(str);
        }
    }

    /* access modifiers changed from: private */
    public void c(String str) {
        StringBuilder sb = new StringBuilder("mCurrentTopPage=");
        sb.append(this.a);
        sb.append("|pagename=");
        sb.append(str);
        eao.d("wbsww", sb.toString());
        this.a = str;
        this.d.post(new Runnable() {
            public final void run() {
                if (!"".equals(ehs.b("share_bike_riding_status_id"))) {
                    boolean parseBoolean = Boolean.parseBoolean(ehs.b("share_bike_riding_status_id"));
                    ehz.a(parseBoolean ? 1 : 0);
                    eht.c();
                    ehx.this.a((int) parseBoolean);
                    return;
                }
                if (ehx.this.b != null) {
                    ehx.this.a(ehx.this.b.status);
                }
            }
        });
    }

    static /* synthetic */ boolean a() {
        atb atb = (atb) a.a.a(atb.class);
        String simpleName = atb != null ? atb.a().a(2).getSimpleName() : null;
        ArrayList<akc> pagesStacks = AMapPageUtil.getPagesStacks();
        if (pagesStacks == null || pagesStacks.isEmpty()) {
            return false;
        }
        int size = pagesStacks.size() - 1;
        boolean z = false;
        for (int i = size; i >= 0; i--) {
            bid stackFragment = AMapPageUtil.getStackFragment(i);
            if (stackFragment != null && (stackFragment instanceof AbstractBaseMapPage) && stackFragment.getClass() != null && TextUtils.equals(stackFragment.getClass().getSimpleName(), simpleName)) {
                z = true;
            }
        }
        if (z) {
            while (size >= 0) {
                bid stackFragment2 = AMapPageUtil.getStackFragment(size);
                if (stackFragment2 != null && (stackFragment2 instanceof AbstractBasePage)) {
                    if (stackFragment2.getClass() != null && TextUtils.equals(stackFragment2.getClass().getSimpleName(), simpleName)) {
                        break;
                    }
                    stackFragment2.finish();
                }
                size--;
            }
        }
        return z;
    }
}
