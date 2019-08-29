package defpackage;

import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.mine.feedback.navi.ReportErrorListPage;
import com.autonavi.minimap.basemap.errorback.model.ReportErrorBean;
import org.json.JSONObject;

/* renamed from: cgg reason: default package */
/* compiled from: ReportErrorListPresenter */
public final class cgg extends AbstractBasePresenter<ReportErrorListPage> {
    public cgg(ReportErrorListPage reportErrorListPage) {
        super(reportErrorListPage);
    }

    public final ON_BACK_TYPE onBackPressed() {
        ReportErrorListPage reportErrorListPage = (ReportErrorListPage) this.mPage;
        reportErrorListPage.setResult(ResultType.CANCEL, (PageBundle) null);
        reportErrorListPage.finish();
        return ON_BACK_TYPE.TYPE_IGNORE;
    }

    public final void onStop() {
        ReportErrorListPage reportErrorListPage = (ReportErrorListPage) this.mPage;
        reportErrorListPage.d = reportErrorListPage.c.getText().toString();
        super.onStop();
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i, resultType, pageBundle);
        ReportErrorListPage reportErrorListPage = (ReportErrorListPage) this.mPage;
        if (i == 16400) {
            if (pageBundle != null && resultType == ResultType.OK) {
                Object obj = pageBundle.get("data");
                if (obj instanceof JSONObject) {
                    if (((JSONObject) obj).optInt("submitCode", -1) == 1) {
                        ReportErrorBean reportErrorBean = (ReportErrorBean) pageBundle.get("ReportErrorDescFragment.ReportErrorBean");
                        if (reportErrorBean == null) {
                            reportErrorBean = reportErrorListPage.f;
                        }
                        if (!(reportErrorListPage.a == null || reportErrorBean == null)) {
                            reportErrorListPage.a.del(reportErrorBean);
                            reportErrorListPage.f = null;
                        }
                        reportErrorListPage.b.onRequestFinish(Boolean.TRUE, reportErrorBean);
                        if (reportErrorListPage.b.getCount() == 0) {
                            reportErrorListPage.setResult(ResultType.OK, (PageBundle) null);
                            reportErrorListPage.finish();
                        }
                    } else if (reportErrorListPage.e) {
                        reportErrorListPage.setResult(ResultType.OK, (PageBundle) null);
                        reportErrorListPage.finish();
                    }
                }
            } else if (reportErrorListPage.e) {
                reportErrorListPage.setResult(ResultType.OK, (PageBundle) null);
                reportErrorListPage.finish();
            }
        }
    }
}
