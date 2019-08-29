package defpackage;

import android.util.Pair;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.inter.IPageManifest;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

@SuppressFBWarnings({"NP_LOAD_OF_KNOWN_NULL_VALUE"})
/* renamed from: bup reason: default package */
/* compiled from: MvpPageContext */
public abstract class bup implements bid {
    private static final String TAG = "MvpPageContext";
    private bul mActivityContext;
    private WeakReference<bha> mIRoutCallbackWeakReference;
    private PageBundle mResultData;
    private ResultType mResultType;
    private akg mSelf;

    public void attach(akg akg, bul bul) {
        this.mSelf = akg;
        this.mActivityContext = bul;
    }

    public bul getMvpActivityContext() {
        return this.mActivityContext;
    }

    public void startPage(Class<?> cls, PageBundle pageBundle) {
        this.mActivityContext.a(cls, pageBundle, (aki) null);
    }

    public void startPage(String str, PageBundle pageBundle) {
        this.mActivityContext.a(str, pageBundle);
    }

    public void startPage(Class<?> cls, PageBundle pageBundle, int i) {
        this.mActivityContext.a(cls, pageBundle, this.mSelf != null ? new aki(i, this.mSelf) : null);
    }

    public void startPage(String str, PageBundle pageBundle, int i) {
        this.mActivityContext.a(str, pageBundle, this.mSelf != null ? new aki(i, this.mSelf) : null);
    }

    public void finish() {
        bul bul = this.mActivityContext;
        akg akg = this.mSelf;
        ArrayList<akc> c = bul.b.c();
        int size = c.size();
        ake ake = null;
        if (size >= 2) {
            buk buk = (buk) c.get(size - 1);
            buk buk2 = (buk) c.get(size - 2);
            akb a = bus.a((Class) buk2.h().getClass(), (Class) buk.h().getClass());
            aka r6 = a != null ? new aka(buk.h(), buk2.h()) {
                final /* synthetic */ bid a;
                final /* synthetic */ bid b;

                {
                    this.a = r2;
                    this.b = r3;
                }

                public final void a() {
                    if (this.a != null) {
                        this.a.onAnimationStarted(false);
                    }
                    if (this.b != null) {
                        this.b.onAnimationStarted(true);
                    }
                }

                public final void b() {
                    if (this.a != null) {
                        this.a.onAnimationFinished(false);
                    }
                    if (this.b != null) {
                        this.b.onAnimationFinished(true);
                    }
                }
            } : null;
            if (a != null) {
                ake = new ake(r6, a);
            }
        }
        bul.b.a(akg, ake);
    }

    public final void setResult(ResultType resultType, PageBundle pageBundle) {
        this.mResultType = resultType;
        this.mResultData = pageBundle;
        int i = resultType == ResultType.CANCEL ? -1 : resultType == ResultType.OK ? 1 : 0;
        HashMap hashMap = null;
        if (pageBundle != null) {
            hashMap = new HashMap();
            hashMap.put("CUSCTOM_BUNDLE", pageBundle);
        }
        setResult(i, hashMap);
    }

    private void setResult(int i, HashMap<String, Object> hashMap) {
        this.mActivityContext.b.a(this.mSelf, i, new akh(hashMap));
    }

    /* access modifiers changed from: protected */
    public Pair<ResultType, PageBundle> getResult() {
        return new Pair<>(this.mResultType, this.mResultData);
    }

    public final akg getPageId() {
        return this.mSelf;
    }

    public final Class findClassByAction(String str) {
        Class<?> page = ((IPageManifest) bqn.a(IPageManifest.class)).getPage(str);
        if ("amap.basemap.action.default_page".equals(str)) {
            return ((buh) ank.a(buh.class)).a(str);
        }
        if (page == null) {
            StringBuilder sb = new StringBuilder("不存在Action为：");
            sb.append(str);
            sb.append("的Page");
            AMapLog.e(TAG, sb.toString());
        }
        return page;
    }
}
