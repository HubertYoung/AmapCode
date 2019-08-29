package com.autonavi.minimap.bundle.activities.page;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.autonavi.annotation.PageAction;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.bundle.banner.data.BannerItem;
import com.autonavi.bundle.banner.net.BannerResult;
import com.autonavi.common.Callback;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.activities.entity.BannerCallBack;
import com.autonavi.widget.ui.TitleBar;
import java.util.LinkedList;

@PageAction("amap.basemap.action.acticities")
public class ActivitiesPage extends AbstractBasePage<cub> {
    private TitleBar a;
    private ListView b;
    private View c;
    private ActivitiesAdapter d;
    private final String e = "amapuri://webview/amaponline?url=http%3A%2F%2Fgroup.testing.amap.com%2Fpublic%2Factivity%2Fcommon%2Fpage%2Fawardlist.html%3Fgd_from%3Dbanner&hide_title=1";
    private final String f = "amapuri://webview/amaponline?url=https%3A%2F%2Fcache.amap.com%2Factivity%2Fcommon%2Fpage%2Fawardlist.html%3Fgd_from%3Dbanner&hide_title=1";

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.layout_activities);
        View contentView = getContentView();
        this.a = (TitleBar) contentView.findViewById(R.id.title);
        this.a.setTitle(getString(R.string.activities_zone));
        this.a.setActionText(getString(R.string.activities_my_award));
        this.a.setOnActionClickListener(new OnClickListener() {
            public final void onClick(View view) {
                ActivitiesPage.a(ActivitiesPage.this);
            }
        });
        this.a.setOnBackClickListener((OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                ActivitiesPage.this.finish();
            }
        });
        this.b = (ListView) contentView.findViewById(R.id.listview);
        this.c = contentView.findViewById(R.id.empty);
        ash.a((String) "18", (Callback<BannerResult>) new BannerCallBack<BannerResult>((cub) this.mPresenter));
    }

    public final void a() {
        LinkedList<BannerItem> linkedList = cue.a().a.items;
        if (this.d == null) {
            this.d = new ActivitiesAdapter(this, linkedList);
            this.b.setAdapter(this.d);
        } else {
            this.d.updateBannerList(linkedList);
        }
        if (this.d.getCount() != 0) {
            this.c.setVisibility(8);
            this.b.setVisibility(0);
            return;
        }
        this.c.setVisibility(0);
        this.b.setVisibility(8);
    }

    /* access modifiers changed from: private */
    public void b() {
        String str = ConfigerHelper.getInstance().getMyAwardTest() ? "amapuri://webview/amaponline?url=http%3A%2F%2Fgroup.testing.amap.com%2Fpublic%2Factivity%2Fcommon%2Fpage%2Fawardlist.html%3Fgd_from%3Dbanner&hide_title=1" : "amapuri://webview/amaponline?url=https%3A%2F%2Fcache.amap.com%2Factivity%2Fcommon%2Fpage%2Fawardlist.html%3Fgd_from%3Dbanner&hide_title=1";
        Intent intent = new Intent();
        intent.setData(Uri.parse(str));
        startScheme(intent);
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new cub(this);
    }

    static /* synthetic */ void a(ActivitiesPage activitiesPage) {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService != null) {
            if (iAccountService.a()) {
                activitiesPage.b();
                return;
            }
            IAccountService iAccountService2 = (IAccountService) a.a.a(IAccountService.class);
            if (iAccountService2 != null) {
                iAccountService2.a(activitiesPage.getPageContext(), (anq) new anq() {
                    public final void loginOrBindCancel() {
                    }

                    public final void onComplete(boolean z) {
                        if (z) {
                            ActivitiesPage.this.b();
                        }
                    }
                });
            }
        }
    }
}
