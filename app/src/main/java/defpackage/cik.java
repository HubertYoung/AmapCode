package defpackage;

import android.content.Context;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.agroup.widget.AGroupAlertFactory;
import com.autonavi.minimap.bundle.agroup.api.IDataService.TeamStatus;
import com.autonavi.widget.ui.AlertView;

/* renamed from: cik reason: default package */
/* compiled from: JoinGroupHandler */
public final class cik {
    cjx a;

    /* renamed from: cik$a */
    /* compiled from: JoinGroupHandler */
    public interface a {
        void a(String str);
    }

    /* renamed from: cik$b */
    /* compiled from: JoinGroupHandler */
    public interface b {
        void a();
    }

    /* renamed from: cik$c */
    /* compiled from: JoinGroupHandler */
    public static class c {
        /* access modifiers changed from: private */
        public static final cik a = new cik(0);
    }

    /* synthetic */ cik(byte b2) {
        this();
    }

    private cik() {
    }

    public final void a(String str, int i, a aVar) {
        if (!a()) {
            bid pageContext = AMapPageUtil.getPageContext();
            if (pageContext != null && pageContext.isAlive() && pageContext.getContext() != null) {
                a(pageContext);
                a(pageContext, str, i, aVar);
                return;
            }
            return;
        }
        cin.a(str, i, aVar);
    }

    public final void a(bid bid) {
        if (bid != null && this.a != null) {
            bid.dismissViewLayer(this.a);
            this.a = null;
        }
    }

    public final void a(final bid bid, String str, int i, a aVar) {
        Context context = bid.getContext();
        TeamStatus teamStatus = TeamStatus.STATUS_USER_NOT_LOGIN;
        AnonymousClass1 r2 = new defpackage.ern.a() {
            public final void onClick(AlertView alertView, int i) {
                if (bid != null && cik.this.a != null) {
                    bid.dismissViewLayer(cik.this.a);
                    cik.this.a = null;
                }
            }
        };
        final bid bid2 = bid;
        final String str2 = str;
        final int i2 = i;
        final a aVar2 = aVar;
        AnonymousClass2 r3 = new defpackage.ern.a() {
            public final void onClick(AlertView alertView, int i) {
                cik cik = cik.this;
                bid bid = bid2;
                AnonymousClass1 r0 = new b() {
                    public final void a() {
                        cin.a(str2, i2, aVar2);
                    }
                };
                IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
                if (iAccountService != null) {
                    iAccountService.a(bid, (anq) new anq(r0) {
                        final /* synthetic */ b a;

                        public final void loginOrBindCancel() {
                        }

                        {
                            this.a = r2;
                        }

                        public final void onComplete(boolean z) {
                            if (z && this.a != null) {
                                this.a.a();
                            }
                        }
                    });
                }
                if (bid2 != null && cik.this.a != null) {
                    bid2.dismissViewLayer(cik.this.a);
                    cik.this.a = null;
                }
            }
        };
        this.a = AGroupAlertFactory.a(context, teamStatus, r2, r3);
        if (this.a != null) {
            bid.showViewLayer(this.a);
            cjs.a(bid);
        }
    }

    public static boolean a() {
        IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
        if (iAccountService == null) {
            return false;
        }
        return iAccountService.a();
    }
}
