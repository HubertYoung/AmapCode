package defpackage;

import com.alipay.sdk.widget.a;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.mine.feedbackv2.drivenavigationissues.DriveNavigationIssuesListPage;
import com.autonavi.mine.feedbackv2.drivenavigationissues.DriveNavigationIssuesListPage.DriveNaviIssueListAdapter;
import com.autonavi.minimap.R;
import java.util.List;

/* renamed from: cgj reason: default package */
/* compiled from: DriveNavigationIssuesListPresenter */
public final class cgj extends AbstractBasePresenter<DriveNavigationIssuesListPage> {
    public cgl a = new cgl();
    /* access modifiers changed from: private */
    public boolean b = true;
    private ProgressDlg c;

    public cgj(DriveNavigationIssuesListPage driveNavigationIssuesListPage) {
        super(driveNavigationIssuesListPage);
    }

    public final void onPageCreated() {
        super.onPageCreated();
        a();
    }

    public final void onStart() {
        int i;
        super.onStart();
        if (!this.b) {
            DriveNavigationIssuesListPage driveNavigationIssuesListPage = (DriveNavigationIssuesListPage) this.mPage;
            if (driveNavigationIssuesListPage.a == null) {
                i = 0;
            } else {
                i = driveNavigationIssuesListPage.a.getCount();
            }
            if (i == 0) {
                ((DriveNavigationIssuesListPage) this.mPage).finish();
            }
        }
    }

    public final ON_BACK_TYPE onBackPressed() {
        ((DriveNavigationIssuesListPage) this.mPage).finish();
        return ON_BACK_TYPE.TYPE_IGNORE;
    }

    public final void a() {
        ((DriveNavigationIssuesListPage) this.mPage).a(8);
        b();
        this.a.a(System.currentTimeMillis() / 1000, Integer.valueOf(10), new a<List<cgk>>() {
            public final /* synthetic */ void a(Object obj) {
                List list = (List) obj;
                cgj.h(cgj.this);
                if (((DriveNavigationIssuesListPage) cgj.this.mPage).isAlive()) {
                    ((DriveNavigationIssuesListPage) cgj.this.mPage).a(0);
                    if (list != null && list.size() != 0) {
                        DriveNavigationIssuesListPage driveNavigationIssuesListPage = (DriveNavigationIssuesListPage) cgj.this.mPage;
                        driveNavigationIssuesListPage.a = new DriveNaviIssueListAdapter(list, driveNavigationIssuesListPage);
                        driveNavigationIssuesListPage.b.setAdapter(driveNavigationIssuesListPage.a);
                    } else if (!cgj.this.b) {
                        ((DriveNavigationIssuesListPage) cgj.this.mPage).finish();
                    } else {
                        col.a();
                        avb.a((bid) cgj.this.mPage, ((DriveNavigationIssuesListPage) cgj.this.mPage).getArguments(), (String) "driveIssue");
                    }
                    ((DriveNavigationIssuesListPage) cgj.this.mPage).b.onRefreshComplete();
                    cgj.this.b = false;
                }
            }

            public final void a() {
                if (((DriveNavigationIssuesListPage) cgj.this.mPage).isAlive()) {
                    cgj.h(cgj.this);
                    ToastHelper.showToast(((DriveNavigationIssuesListPage) cgj.this.mPage).getString(R.string.request_failed));
                    avb.a((bid) cgj.this.mPage, ((DriveNavigationIssuesListPage) cgj.this.mPage).getArguments(), (String) "driveIssue");
                    ((DriveNavigationIssuesListPage) cgj.this.mPage).b.onRefreshComplete();
                    cgj.this.b = false;
                }
            }
        });
    }

    private void b() {
        if (this.c == null) {
            this.c = new ProgressDlg(((DriveNavigationIssuesListPage) this.mPage).getActivity(), a.a, "");
        }
        this.c.setMessage(a.a);
        this.c.setCancelable(false);
        this.c.show();
    }

    static /* synthetic */ void h(cgj cgj) {
        if (cgj.c != null) {
            cgj.c.dismiss();
        }
    }
}
