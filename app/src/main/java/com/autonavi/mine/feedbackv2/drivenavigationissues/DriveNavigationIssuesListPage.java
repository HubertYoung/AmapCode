package com.autonavi.mine.feedbackv2.drivenavigationissues;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.annotation.PageAction;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.mine.feedbackv2.drivenavigationissues.DriveNavigationIssuesListPage;
import com.autonavi.minimap.R;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.d;
import com.autonavi.widget.pulltorefresh.PullToRefreshListView;
import com.autonavi.widget.ui.TitleBar;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@PageAction("amap.basemap.action.drive_navigation_issue")
public class DriveNavigationIssuesListPage extends AbstractBasePage<cgj> implements OnClickListener, d<ListView> {
    public DriveNaviIssueListAdapter a;
    public PullToRefreshListView b;
    public View c;
    private TitleBar d;
    private Button e;

    public static class DriveNaviIssueListAdapter extends BaseAdapter {
        List<cgk> mData;
        WeakReference<DriveNavigationIssuesListPage> mPageWeakRef;

        static class a implements OnClickListener {
            TextView a;
            TextView b;
            TextView c;
            Button d;
            SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            private cgk f;
            private WeakReference<DriveNavigationIssuesListPage> g;

            public a(View view, cgk cgk, WeakReference<DriveNavigationIssuesListPage> weakReference) {
                this.a = (TextView) view.findViewById(R.id.date_and_time_tv);
                this.b = (TextView) view.findViewById(R.id.start_name);
                this.c = (TextView) view.findViewById(R.id.end_name);
                this.d = (Button) view.findViewById(R.id.report_button);
                this.f = cgk;
                this.d.setOnClickListener(this);
                this.g = weakReference;
            }

            public final void onClick(View view) {
                DriveNavigationIssuesListPage driveNavigationIssuesListPage = (DriveNavigationIssuesListPage) this.g.get();
                if (driveNavigationIssuesListPage != null) {
                    PageBundle pageBundle = (PageBundle) driveNavigationIssuesListPage.getArguments().clone();
                    if (pageBundle != null) {
                        pageBundle.putObject("drive_issue_content_key", this.f);
                    }
                    col.a();
                    avb.a((bid) driveNavigationIssuesListPage, pageBundle, (String) "driveIssue");
                }
            }
        }

        public long getItemId(int i) {
            return 0;
        }

        public DriveNaviIssueListAdapter(List<cgk> list, DriveNavigationIssuesListPage driveNavigationIssuesListPage) {
            this.mData = (List) age.a(list, "DriveNaviIssueItem list cann't be null.");
            this.mPageWeakRef = new WeakReference<>(driveNavigationIssuesListPage);
        }

        public int getCount() {
            return this.mData.size();
        }

        public Object getItem(int i) {
            return this.mData.get(i);
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            a aVar;
            if (view == null) {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_drive_navi_issue, viewGroup, false);
                aVar = new a(view, this.mData.get(i), this.mPageWeakRef);
                view.setTag(aVar);
            } else {
                aVar = (a) view.getTag();
            }
            cgk cgk = this.mData.get(i);
            aVar.a.setText(aVar.e.format(new Date(cgk.g.longValue() * 1000)));
            aVar.b.setText(cgk.c);
            aVar.c.setText(cgk.d);
            return view;
        }

        public cgk getLastEntity() {
            return (cgk) getItem(getCount() - 1);
        }

        public void appendData(List<cgk> list) {
            this.mData.addAll(list);
        }
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        requestScreenOrientation(1);
        setContentView(R.layout.page_drive_navi_issues_list);
        this.d = (TitleBar) findViewById(R.id.tbTitle);
        this.b = (PullToRefreshListView) findViewById(R.id.navigation_history_listview);
        this.e = (Button) findViewById(R.id.to_other_navigation_issue_btn);
        this.c = LayoutInflater.from(getContext()).inflate(R.layout.feedback_driving_achievement_footer, null);
        this.d.setOnBackClickListener((OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                ((cgj) DriveNavigationIssuesListPage.this.mPresenter).onBackPressed();
            }
        });
        this.b.getListView().addFooterView(this.c);
        this.b.setMode(Mode.BOTH);
        this.b.setOnRefreshListener((d<T>) this);
        ListView listView = (ListView) this.b.getRefreshableView();
        listView.setChoiceMode(1);
        listView.setOverScrollMode(2);
        this.b.setOverScrollMode(2);
        this.b.setEmptyView(findViewById(R.id.empty));
        this.e.setOnClickListener(this);
    }

    public final void a(int i) {
        this.e.setVisibility(i);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.to_other_navigation_issue_btn) {
            col.a();
            avb.a((bid) this, getArguments(), (String) "driveIssue");
        }
    }

    public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
        ((cgj) this.mPresenter).a();
    }

    public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
        cgk lastEntity = this.a.getLastEntity();
        if (lastEntity != null) {
            cgj cgj = (cgj) this.mPresenter;
            cgj.a.a(lastEntity.g.longValue(), Integer.valueOf(10), new a<List<cgk>>() {
                public final /* synthetic */ void a(Object obj) {
                    List list = (List) obj;
                    if (((DriveNavigationIssuesListPage) cgj.this.mPage).isAlive()) {
                        ((DriveNavigationIssuesListPage) cgj.this.mPage).b.onRefreshComplete();
                        DriveNavigationIssuesListPage driveNavigationIssuesListPage = (DriveNavigationIssuesListPage) cgj.this.mPage;
                        if (list != null && !list.isEmpty()) {
                            driveNavigationIssuesListPage.b.setMode(Mode.PULL_FROM_END);
                            driveNavigationIssuesListPage.b.getListView().removeFooterView(driveNavigationIssuesListPage.c);
                        } else {
                            driveNavigationIssuesListPage.b.setMode(Mode.DISABLED);
                            driveNavigationIssuesListPage.b.getListView().removeFooterView(driveNavigationIssuesListPage.c);
                            driveNavigationIssuesListPage.b.getListView().addFooterView(driveNavigationIssuesListPage.c);
                        }
                        DriveNavigationIssuesListPage driveNavigationIssuesListPage2 = (DriveNavigationIssuesListPage) cgj.this.mPage;
                        View findViewById = driveNavigationIssuesListPage2.c.findViewById(R.id.tv_footer_info);
                        if (list == null || list.size() == 0) {
                            if (driveNavigationIssuesListPage2.a.isEmpty() && findViewById != null) {
                                findViewById.setVisibility(8);
                            }
                            return;
                        }
                        if (findViewById != null) {
                            findViewById.setVisibility(0);
                        }
                        driveNavigationIssuesListPage2.a.appendData(list);
                    }
                }

                public final void a() {
                    if (((DriveNavigationIssuesListPage) cgj.this.mPage).isAlive()) {
                        ToastHelper.showToast(((DriveNavigationIssuesListPage) cgj.this.mPage).getString(R.string.request_failed));
                        ((DriveNavigationIssuesListPage) cgj.this.mPage).b.onRefreshComplete();
                    }
                }
            });
        }
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new cgj(this);
    }
}
