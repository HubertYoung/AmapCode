package com.autonavi.map.search.page;

import android.content.Context;
import android.text.TextUtils;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.autonavi.annotation.PageAction;
import com.autonavi.bundle.searchcommon.view.HotwordGridLayout;
import com.autonavi.bundle.searchcommon.view.SearchKeywordResultTitleView;
import com.autonavi.bundle.searchcommon.view.SearchKeywordResultTitleView.a;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleInstance;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;

@PageAction("amap.search.action.searcherrorindoor")
public class SearchErrorIndoorPage extends AbstractSearchBasePage<cbc> implements launchModeSingleInstance {
    public SearchKeywordResultTitleView a = null;
    public TextView b;
    public String c;
    private HotwordGridLayout d;

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.search_error_indoor_fragment_layout);
        this.a = (SearchKeywordResultTitleView) findViewById(R.id.view_normal_title);
        this.b = (TextView) findViewById(R.id.tip_content);
        this.d = (HotwordGridLayout) findViewById(R.id.hot_words);
        this.d.setTitleVisibility(8);
        this.d.setOnHotwordItemClickListener((OnClickListener) this.mPresenter);
        if (!TextUtils.isEmpty(elc.f) && elc.f.equals(DriveUtil.POI_TYPE_AIRPORT)) {
            this.d.bindHotwords(elc.q);
        } else if (TextUtils.isEmpty(elc.f) || !elc.f.equals(DriveUtil.POI_TYPE_RAILWAY)) {
            this.d.bindHotwords(elc.o);
        } else {
            this.d.bindHotwords(elc.t);
        }
        PageBundle arguments = getArguments();
        if (arguments != null && arguments.containsKey(TrafficUtil.KEYWORD)) {
            this.c = arguments.getString(TrafficUtil.KEYWORD);
        }
        this.a.goneRightButton();
        this.a.setOnSearchKeywordResultTitleViewListener(new a() {
            public final void c() {
            }

            public final void d() {
            }

            public final void a() {
                SearchErrorIndoorPage.this.finish();
            }

            public final void b() {
                elc.c = false;
                bcb bcb = (bcb) a.a.a(bcb.class);
                if (bcb != null) {
                    bcb.a().c(SearchErrorIndoorPage.this.c).a(AMapPageUtil.getPageContext());
                }
            }
        });
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new cbc(this);
    }
}
