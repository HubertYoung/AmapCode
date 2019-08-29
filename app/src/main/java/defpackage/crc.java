package defpackage;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.amap.bundle.utils.ui.CompatDialog;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.d;
import com.autonavi.widget.pulltorefresh.PullToRefreshListView;
import com.autonavi.widget.pulltorefresh.internal.LoadingLayout;

/* renamed from: crc reason: default package */
/* compiled from: SaveSearchSelectCityDialog */
public final class crc extends CompatDialog {
    public ListAdapter a;
    public ListView b;
    public TextView c;
    public String d;
    public OnItemClickListener e;
    public Button f = null;
    /* access modifiers changed from: private */
    public PullToRefreshListView g;
    private LoadingLayout h;
    private Context i;
    private TextView j;
    private Button k;
    /* access modifiers changed from: private */
    public d<ListView> l;

    public crc(Activity activity) {
        super(activity, R.style.custom_dlg);
        this.i = activity;
        this.l = null;
        setContentView(R.layout.v3_list_dlg);
        if (this.l == null) {
            this.b = (ListView) findViewById(R.id.list);
            this.b.setVisibility(0);
        } else {
            this.g = (PullToRefreshListView) findViewById(R.id.vouchers_pull_refresh_list);
            this.g.setMode(Mode.BOTH);
            this.g.setFootershowflag(false);
            this.g.setVisibility(0);
            this.b = (ListView) this.g.getRefreshableView();
            this.h = this.g.changeFooter();
            this.g.mLvFooterLoadingFrame.removeView(this.g.mFooterLoadingView);
            this.h.setVisibility(0);
            this.b.addFooterView(this.h, null, false);
            this.g.setOnRefreshListener((d<T>) new d<ListView>() {
                public final void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                    crc.this.l.onPullDownToRefresh(pullToRefreshBase);
                    crc.this.g.mHeaderLoadingView.setVisibility(8);
                }

                public final void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                    crc.this.l.onPullUpToRefresh(pullToRefreshBase);
                    crc.this.g.mFooterLoadingView.setVisibility(8);
                }
            });
            if (this.h != null) {
                this.h.setVisibility(0);
            }
            this.g.mHeaderLoadingView.setRefreshingLabel(this.i.getString(R.string.isloading));
            this.g.onRefreshComplete();
            this.g.setMode(Mode.BOTH);
            this.g.showImageFoot();
            this.g.hideImageHead();
            this.g.setNeedRefreshing(false);
            this.g.setHeaderText(this.i.getString(R.string.first_page_no_last_apage), this.i.getString(R.string.first_page_no_last_apage), this.i.getString(R.string.isloading));
            this.g.setFooterText(this.i.getString(R.string.first_page_pull_to_second_page), this.i.getString(R.string.release_to_second_page), this.i.getString(R.string.isloading));
            this.g.setFooterText(this.i.getString(R.string.current_page_no_next_page, new Object[]{Integer.valueOf(1)}), this.i.getString(R.string.current_page_no_next_page, new Object[]{Integer.valueOf(1)}), this.i.getString(R.string.isloading));
            this.g.setMode(Mode.PULL_FROM_START);
            if (this.a != null && this.a.getCount() > 0 && this.a.getCount() < 10) {
                this.g.hideImageFoot();
            }
            if (this.a != null && this.a.getCount() > 0) {
                this.b.setSelection(0);
            }
            this.b = this.b;
            this.b.setVisibility(0);
        }
        this.c = (TextView) findViewById(R.id.title);
        this.j = (TextView) findViewById(R.id.btn_confirm);
        this.k = (Button) findViewById(R.id.clean_history);
        this.k.setText(R.string.cancel);
        this.k.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                crc.this.dismiss();
            }
        });
        this.f = (Button) findViewById(R.id.btn_netsearch);
    }

    public final void show() {
        Window window = getWindow();
        window.setGravity(80);
        window.setLayout(-1, -2);
        Activity topActivity = AMapAppGlobal.getTopActivity();
        if (topActivity != null) {
            if (topActivity.getResources().getConfiguration().orientation == 1) {
                topActivity.setRequestedOrientation(1);
            } else {
                topActivity.setRequestedOrientation(0);
            }
        }
        super.show();
    }

    public final void dismiss() {
        super.dismiss();
        Activity topActivity = AMapAppGlobal.getTopActivity();
        if (topActivity != null) {
            topActivity.setRequestedOrientation(4);
        }
    }
}
