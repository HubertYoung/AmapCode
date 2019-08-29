package defpackage;

import android.text.TextUtils;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.coach.page.BaseStationListPage;
import com.autonavi.minimap.route.common.view.RouteLoadingView;
import com.autonavi.minimap.route.train.stationlist.StationManager;
import com.autonavi.minimap.route.train.stationlist.StationRequestManger;
import com.autonavi.minimap.route.train.stationlist.StationRequestManger.a;
import com.autonavi.widget.ui.TitleBar;
import org.json.JSONArray;

/* renamed from: dzq reason: default package */
/* compiled from: BaseStationListPresenter */
public final class dzq extends eaf<BaseStationListPage> {
    public boolean a;
    public String b;

    public dzq(BaseStationListPage baseStationListPage) {
        super(baseStationListPage);
    }

    public final void onDestroy() {
        super.onDestroy();
        BaseStationListPage baseStationListPage = (BaseStationListPage) this.mPage;
        if (baseStationListPage.f != null) {
            baseStationListPage.f.cancel();
        }
        StationRequestManger.a();
        StationRequestManger.a((a) null);
    }

    public final ON_BACK_TYPE onBackPressed() {
        BaseStationListPage baseStationListPage = (BaseStationListPage) this.mPage;
        boolean z = false;
        if (baseStationListPage.b == null || !baseStationListPage.b.isShown()) {
            if (baseStationListPage.b != null) {
                baseStationListPage.b.setVisibility(0);
            }
            if (baseStationListPage.c != null) {
                baseStationListPage.c.setVisibility(8);
            }
            if (!(baseStationListPage.a == null || baseStationListPage.a.getEditText() == null)) {
                baseStationListPage.a.getEditText().setText("");
            }
            z = true;
        }
        if (z) {
            return ON_BACK_TYPE.TYPE_IGNORE;
        }
        return super.onBackPressed();
    }

    public final void onPageCreated() {
        PageBundle arguments = ((BaseStationListPage) this.mPage).getArguments();
        if (arguments != null) {
            this.a = arguments.getBoolean("COACH_ARRIVAL_STATION");
            this.b = arguments.getString("STRAT_STATION_ADCODE");
        }
        BaseStationListPage baseStationListPage = (BaseStationListPage) this.mPage;
        baseStationListPage.f = new BaseStationListPage.a(baseStationListPage);
        baseStationListPage.e = (RouteLoadingView) baseStationListPage.getContentView().findViewById(R.id.loading_view);
        boolean z = false;
        baseStationListPage.e.setVisibility(0);
        baseStationListPage.a = (TitleBar) baseStationListPage.findViewById(R.id.title_bar);
        baseStationListPage.a.getEditText().setHint(BaseStationListPage.a(baseStationListPage.getRequestCode()));
        baseStationListPage.d = new StationManager(baseStationListPage.getContext(), baseStationListPage.a(), ((dzq) baseStationListPage.mPresenter).a);
        if (((dzq) baseStationListPage.mPresenter).a && !TextUtils.isEmpty(((dzq) baseStationListPage.mPresenter).b)) {
            z = true;
        }
        if (z) {
            StationRequestManger.a();
            StationRequestManger.a((a) new a() {
                public final void a(boolean z, JSONArray jSONArray) {
                    if (z) {
                        BaseStationListPage.a(BaseStationListPage.this, jSONArray);
                        BaseStationListPage.this.e.setVisibility(8);
                        return;
                    }
                    ahl.b(BaseStationListPage.this.f);
                }
            });
            StationRequestManger.a().a(((dzq) baseStationListPage.mPresenter).b);
            return;
        }
        ahl.b(baseStationListPage.f);
    }
}
