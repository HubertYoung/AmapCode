package com.autonavi.minimap.drive.quicknaviwidget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.drivecommon.view.ListViewWithHeaderAdapter;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.banner.view.DBanner;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.drive.view.ListViewWithHeader;
import com.autonavi.minimap.drive.widget.RouteFragmentHomeAddressView;
import com.autonavi.widget.ui.AlertView;
import java.util.List;

public class RemoteControlFragmentHistoryView extends ListViewWithHeader implements OnClickListener {
    private DBanner mDbBanner;
    private LinearLayout mDbBannerContainer;
    /* access modifiers changed from: private */
    public List<sj> mNaviHistoryList;
    private a mOnCategoryClickListener;
    /* access modifiers changed from: private */
    public b mOnRouteHistoryClickListener;
    /* access modifiers changed from: private */
    public QuickNaviHistoryAdapter mQuickNaviHistoryAdapter;
    private RouteFragmentHomeAddressView mRouteFragmentHomeAddressView;

    public interface a {
        void a();

        void b();

        void c();
    }

    public interface b {
        void a(sj sjVar);
    }

    public RemoteControlFragmentHistoryView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mQuickNaviHistoryAdapter = new QuickNaviHistoryAdapter(context);
        setAdapter((ListViewWithHeaderAdapter) this.mQuickNaviHistoryAdapter);
        setOnChildItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                sj item = RemoteControlFragmentHistoryView.this.mQuickNaviHistoryAdapter.getItem(i);
                if (RemoteControlFragmentHistoryView.this.mOnRouteHistoryClickListener != null && item != null) {
                    RemoteControlFragmentHistoryView.this.mOnRouteHistoryClickListener.a(item);
                }
            }
        });
    }

    public void loadHistory() {
        ahm.a(new Runnable() {
            public final void run() {
                RemoteControlFragmentHistoryView.this.mNaviHistoryList = DriveUtil.getNaviHistoryList();
                aho.a(new Runnable() {
                    public final void run() {
                        RemoteControlFragmentHistoryView.this.mQuickNaviHistoryAdapter.setHistoryQuickNaviList(RemoteControlFragmentHistoryView.this.mNaviHistoryList);
                    }
                });
            }
        });
    }

    public View initHeaderView() {
        View inflate = inflate(getContext(), R.layout.quickautonavi_history_header, null);
        this.mRouteFragmentHomeAddressView = (RouteFragmentHomeAddressView) inflate.findViewById(R.id.home_and_comany_address);
        this.mDbBanner = (DBanner) inflate.findViewById(R.id.navi_banner);
        this.mDbBannerContainer = (LinearLayout) inflate.findViewById(R.id.navi_banner_container);
        inflate.findViewById(R.id.control_search_oil).setOnClickListener(this);
        inflate.findViewById(R.id.control_search_parking).setOnClickListener(this);
        inflate.findViewById(R.id.control_search_toilet).setOnClickListener(this);
        return inflate;
    }

    public DBanner getDbBanner() {
        return this.mDbBanner;
    }

    public View getDbContainerView() {
        return this.mDbBannerContainer;
    }

    public RouteFragmentHomeAddressView getRouteCustomAddressView() {
        return this.mRouteFragmentHomeAddressView;
    }

    public View initFooterView() {
        View inflate = inflate(getContext(), R.layout.route_drive_fragment_clean_history_item, null);
        inflate.findViewById(R.id.clean_history).setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                RemoteControlFragmentHistoryView.this.deleteAllHistory();
            }
        });
        inflate.findViewById(R.id.blank_view).setVisibility(0);
        return inflate;
    }

    public int getFooterHeight() {
        return getResources().getDimensionPixelSize(R.dimen.route_fragment_home_com_height);
    }

    /* access modifiers changed from: private */
    public void deleteAllHistory() {
        final bid pageContext = AMapPageUtil.getPageContext();
        com.autonavi.widget.ui.AlertView.a aVar = new com.autonavi.widget.ui.AlertView.a(AMapAppGlobal.getApplication());
        aVar.a((CharSequence) getContext().getString(R.string.clean_history_)).a((CharSequence) getContext().getString(R.string.del_now), (defpackage.ern.a) new defpackage.ern.a() {
            public final void onClick(AlertView alertView, int i) {
                if (pageContext != null) {
                    pageContext.dismissViewLayer(alertView);
                }
                ahn.b().execute(new Runnable() {
                    public final void run() {
                        try {
                            DriveUtil.delNaviHistoryList();
                            RemoteControlFragmentHistoryView.this.loadHistory();
                        } catch (Exception e) {
                            kf.a((Throwable) e);
                        }
                    }
                });
                if (RemoteControlFragmentHistoryView.this.mOnRouteHistoryClickListener != null) {
                    RemoteControlFragmentHistoryView.this.mOnRouteHistoryClickListener;
                }
            }
        }).b((CharSequence) getContext().getString(R.string.cancel), (defpackage.ern.a) new defpackage.ern.a() {
            public final void onClick(AlertView alertView, int i) {
                if (pageContext != null) {
                    pageContext.dismissViewLayer(alertView);
                }
            }
        });
        aVar.a(true);
        if (pageContext != null) {
            pageContext.showViewLayer(aVar.a());
        }
    }

    public void setOnRouteHistoryClickListener(b bVar) {
        this.mOnRouteHistoryClickListener = bVar;
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.control_search_oil) {
            if (this.mOnCategoryClickListener != null) {
                this.mOnCategoryClickListener.a();
            }
        } else if (id == R.id.control_search_parking) {
            if (this.mOnCategoryClickListener != null) {
                this.mOnCategoryClickListener.b();
            }
        } else if (id == R.id.control_search_toilet && this.mOnCategoryClickListener != null) {
            this.mOnCategoryClickListener.c();
        }
    }

    public void setOnCategoryClickListener(a aVar) {
        this.mOnCategoryClickListener = aVar;
    }
}
