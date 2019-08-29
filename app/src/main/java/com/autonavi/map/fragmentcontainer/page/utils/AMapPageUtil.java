package com.autonavi.map.fragmentcontainer.page.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IAMapHomePage;
import com.autonavi.minimap.ajx3.Ajx3PageInterface;
import com.autonavi.minimap.ajx3.loader.AjxPathLoader;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import defpackage.akc;
import defpackage.bid;
import defpackage.bui;
import defpackage.buk;
import defpackage.drp;

public class AMapPageUtil {
    private static final String TAG = "AMapPageUtil";
    private static Handler mHandler = new Handler(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public static HashMap<bid, IActvitiyStateListener> mIActvitiyCallbackList = new HashMap<>();
    private static final Map<String, String> mPageIdentifierCache = new HashMap();
    private static WeakReference< bui > sMvpActivityContext;

    @Nullable
    public static bui getMVPActivityContext() {
        return getMvpActivityContext();
    }

    @Deprecated
    public static void startAlertDialogPage(a aVar) {
        bui mvpActivityContext = getMvpActivityContext();
        if (mvpActivityContext != null) {
            mvpActivityContext.a(aVar);
        }
    }

    @Nullable
    @Deprecated
    public static bid getPageContext() {
        bui mvpActivityContext = getMvpActivityContext();
        if (mvpActivityContext == null) {
            return null;
        }
        return mvpActivityContext.b();
    }

    @Nullable
    public static Context getAppContext() {
        return AMapAppGlobal.getApplication();
    }

    @Nullable
    @Deprecated
    public static Class<?> getTopPageClass() {
        bui mvpActivityContext = getMvpActivityContext();
        if (mvpActivityContext == null) {
            return null;
        }
        return mvpActivityContext.c();
    }

    @Deprecated
    public static ArrayList< akc > getPagesStacks() {
        bui mvpActivityContext = getMvpActivityContext();
        if (mvpActivityContext == null) {
            return null;
        }
        return mvpActivityContext.d();
    }

    @Nullable
    @Deprecated
    public static bid getStackFragment(int i) {
        bui mvpActivityContext = getMvpActivityContext();
        if (mvpActivityContext == null) {
            return null;
        }
        ArrayList<akc> pagesStacks = getPagesStacks();
        if (pagesStacks == null || i > pagesStacks.size() - 1) {
            return null;
        }
        return mvpActivityContext.a((pagesStacks.size() - 1) - i);
    }

    @Deprecated
    public static boolean isHomePage() {
        bui mvpActivityContext = getMvpActivityContext();
        if (mvpActivityContext == null) {
            return false;
        }
        bid b = mvpActivityContext.b();
        if (b instanceof IAMapHomePage) {
            return ((IAMapHomePage) b).isMapHomePage();
        }
        return false;
    }

    @Deprecated
    public static void setPageStateListener(bid bid, IPageStateListener iPageStateListener) {
        drp b = drp.b();
        if (iPageStateListener != null && bid != null) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                drp.b.put(bid, iPageStateListener);
            } else {
                drp.c.post(new Runnable(bid, iPageStateListener) {
                    final /* synthetic */ bid a;
                    final /* synthetic */ IPageStateListener b;

                    {
                        this.a = r2;
                        this.b = r3;
                    }

                    public final void run() {
                        drp.b.put(this.a, this.b);
                    }
                });
            }
        }
    }

    @Deprecated
    public static void removePageStateListener(bid bid) {
        drp.b().b(bid);
    }

    public static void setActivityStateListener(final bid bid, final IActvitiyStateListener iActvitiyStateListener) {
        if (bid != null && iActvitiyStateListener != null) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                mIActvitiyCallbackList.put(bid, iActvitiyStateListener);
            } else {
                mHandler.post(new Runnable() {
                    public final void run() {
                        AMapPageUtil.mIActvitiyCallbackList.put(bid, iActvitiyStateListener);
                    }
                });
            }
        }
    }

    public static void removeActivityStateListener(final bid bid) {
        if (bid != null) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                mIActvitiyCallbackList.remove(bid);
            } else {
                mHandler.post(new Runnable() {
                    public final void run() {
                        AMapPageUtil.mIActvitiyCallbackList.remove(bid);
                    }
                });
            }
        }
    }

    @Nullable
    public static IPageStateListener getPageStateListener(bid bid) {
        return drp.b().a(bid);
    }

    @NonNull
    public static HashMap< bid, IActvitiyStateListener> getActvitiyListenerList() {
        return mIActvitiyCallbackList;
    }

    public static String getPageIdentifier(AbstractBasePage abstractBasePage) {
        if (abstractBasePage == null) {
            return "";
        }
        try {
            String str = mPageIdentifierCache.get(abstractBasePage.toString());
            if (TextUtils.isEmpty(str)) {
                if (!(abstractBasePage instanceof Ajx3PageInterface)) {
                    str = abstractBasePage.getClass().getName();
                } else {
                    int lastIndexOf = abstractBasePage.toString().lastIndexOf(AjxPathLoader.DOMAIN);
                    if (-1 != lastIndexOf) {
                        str = abstractBasePage.toString().substring(lastIndexOf + 7);
                    } else {
                        str = abstractBasePage.toString();
                    }
                }
                mPageIdentifierCache.put(abstractBasePage.toString(), str);
            }
            return str;
        } catch (Exception unused) {
            return "";
        }
    }

    public static ArrayList<bid> getPageContextStacks() {
        ArrayList<akc> pagesStacks = getPagesStacks();
        if (pagesStacks == null) {
            return null;
        }
        ArrayList<bid> arrayList = new ArrayList<>(pagesStacks.size());
        Iterator<akc> it = pagesStacks.iterator();
        while (it.hasNext()) {
            akc next = it.next();
            if (next != null && (next instanceof buk )) {
                arrayList.add(((buk) next).h());
            }
        }
        return arrayList;
    }

    public static void setMvpActivityContext(@Nullable bui bui) {
        if (bui != null) {
            sMvpActivityContext = new WeakReference<>(bui);
        }
    }

    @Nullable
    private static bui getMvpActivityContext() {
        if (sMvpActivityContext == null) {
            return null;
        }
        return (bui) sMvpActivityContext.get();
    }

    public static bid isInstance(Class<?> cls, bid bid) {
        if (cls == null || bid == null) {
            return null;
        }
        if (cls.isInstance(bid)) {
            return bid;
        }
        if (bid instanceof AbstractBasePage) {
            AbstractBasePage abstractBasePage = (AbstractBasePage) bid;
            if (abstractBasePage.getPageContainer() != null) {
                AbstractBasePage cureentRecordPage = abstractBasePage.getPageContainer().getCureentRecordPage();
                if (cls == null || !cls.isInstance(cureentRecordPage)) {
                    return null;
                }
                return cureentRecordPage;
            }
        }
        return null;
    }
}
