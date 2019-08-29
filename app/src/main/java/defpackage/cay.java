package defpackage;

import android.content.Intent;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.autonavi.common.Callback;
import com.autonavi.map.search.comment.common.net.JsonParserCallback;
import com.autonavi.map.search.comment.model.MyCommentedListResponse;
import com.autonavi.map.search.comment.model.MyCommentingListResponse;
import com.autonavi.map.search.comment.net.CommentParam.CommentedListParam;
import com.autonavi.map.search.comment.net.CommentParam.CommentingListParam;
import com.autonavi.map.search.page.MyCommentListPage;
import com.autonavi.map.search.presenter.MyCommentListPresenter$1;
import com.autonavi.map.search.presenter.MyCommentListPresenter$2;
import de.greenrobot.event.EventBus;
import java.lang.ref.WeakReference;

/* renamed from: cay reason: default package */
/* compiled from: MyCommentListPresenter */
public final class cay extends cau<MyCommentListPage> implements bwi {
    public bwf<bwm> a;
    private AosRequest b;
    private AosRequest c;
    private JsonParserCallback<MyCommentedListResponse> d;
    private JsonParserCallback<MyCommentingListResponse> e;
    private bwg f;
    private Callback<MyCommentedListResponse> g = new MyCommentListPresenter$1(this);
    private Callback<MyCommentingListResponse> h = new MyCommentListPresenter$2(this);
    private bwc<bwm> i = new bwc<bwm>() {
        public final /* synthetic */ void a(bwe bwe, bwe bwe2) {
            ((MyCommentListPage) cay.this.mPage).a((bwm) bwe, (bwm) bwe2);
        }
    };

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x004c, code lost:
        r2 = 1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public cay(com.autonavi.map.search.page.MyCommentListPage r6) {
        /*
            r5 = this;
            r5.<init>(r6)
            com.autonavi.map.search.presenter.MyCommentListPresenter$1 r6 = new com.autonavi.map.search.presenter.MyCommentListPresenter$1
            r6.<init>(r5)
            r5.g = r6
            com.autonavi.map.search.presenter.MyCommentListPresenter$2 r6 = new com.autonavi.map.search.presenter.MyCommentListPresenter$2
            r6.<init>(r5)
            r5.h = r6
            cay$1 r6 = new cay$1
            r6.<init>()
            r5.i = r6
            bwm r6 = new bwm
            esb r0 = defpackage.esb.a.a
            java.lang.Class<com.autonavi.bundle.account.api.IAccountService> r1 = com.autonavi.bundle.account.api.IAccountService.class
            esc r0 = r0.a(r1)
            com.autonavi.bundle.account.api.IAccountService r0 = (com.autonavi.bundle.account.api.IAccountService) r0
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L_0x0032
            boolean r0 = r0.a()
            if (r0 == 0) goto L_0x0032
            r0 = 1
            goto L_0x0033
        L_0x0032:
            r0 = 0
        L_0x0033:
            com.autonavi.map.fragmentcontainer.page.IPage r3 = r5.mPage
            com.autonavi.map.search.page.MyCommentListPage r3 = (com.autonavi.map.search.page.MyCommentListPage) r3
            com.autonavi.common.PageBundle r3 = r3.getArguments()
            if (r3 == 0) goto L_0x004d
            java.lang.String r4 = "state"
            java.lang.String r3 = r3.getString(r4)
            java.lang.String r4 = "1"
            boolean r3 = android.text.TextUtils.equals(r4, r3)
            if (r3 == 0) goto L_0x004d
            r2 = 1
        L_0x004d:
            r6.<init>(r0, r2)
            bwl r0 = new bwl
            r0.<init>()
            bwf r1 = new bwf
            r1.<init>(r6)
            defpackage.bwh.a()
            r1.c = r0
            r5.a = r1
            bwf<bwm> r6 = r5.a
            bwc<bwm> r0 = r5.i
            defpackage.bwh.a()
            bwf$a r1 = new bwf$a
            r1.<init>(r6, r0)
            r5.f = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.cay.<init>(com.autonavi.map.search.page.MyCommentListPage):void");
    }

    public final void onPageCreated() {
        super.onPageCreated();
        ((MyCommentListPage) this.mPage).a((bwm) this.a.a, (bwm) this.a.b);
    }

    public final void c() {
        e();
        this.b = aax.a(new CommentedListParam(1));
        ((MyCommentListPage) this.mPage).a((String) "加载中...", this.b);
        this.d = new JsonParserCallback<>(new WeakReference(this.g), ((MyCommentListPage) this.mPage).a);
        yq.a();
        yq.a(this.b, (AosResponseCallback<T>) this.d);
    }

    public final void d() {
        f();
        this.c = aax.a(new CommentingListParam());
        ((MyCommentListPage) this.mPage).a((String) "加载中...", this.c);
        this.e = new JsonParserCallback<>(new WeakReference(this.h), ((MyCommentListPage) this.mPage).a);
        yq.a();
        yq.a(this.c, (AosResponseCallback<T>) this.e);
    }

    private void e() {
        if (this.b != null) {
            yq.a();
            yq.a(this.b);
        }
    }

    private void f() {
        if (this.c != null) {
            yq.a();
            yq.a(this.c);
        }
    }

    public final void onDestroy() {
        super.onDestroy();
        if (this.f != null && !this.f.b()) {
            this.f.a();
        }
        e();
        f();
        ((MyCommentListPage) this.mPage).b(1);
        ((MyCommentListPage) this.mPage).b(2);
        EventBus.getDefault().unregister((MyCommentListPage) this.mPage);
    }

    public final void a(bwa bwa) {
        this.a.a(bwa);
    }

    public final void a(Intent intent) {
        ((MyCommentListPage) this.mPage).startScheme(intent);
    }

    public final void a() {
        c();
        d();
    }

    public final bid b() {
        return (bid) this.mPage;
    }

    public final void a(aja aja) {
        aix aix = (aix) a.a.a(aix.class);
        if (aix != null) {
            aix.a((bid) this.mPage, aja);
        }
    }
}
