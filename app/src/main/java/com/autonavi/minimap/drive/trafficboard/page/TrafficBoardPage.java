package com.autonavi.minimap.drive.trafficboard.page;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alipay.mobile.common.share.widget.ResUtils;
import com.autonavi.annotation.PageAction;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.State;
import com.autonavi.widget.pulltorefresh.PullToRefreshListView;
import com.autonavi.widget.ui.TitleBar;
import java.text.SimpleDateFormat;

@PageAction("amap.basemap.action.traffic_board")
public class TrafficBoardPage extends AbstractBasePage<djw> {
    public long a = 0;
    public LinearLayout b;
    public PullToRefreshListView c;
    public View d;
    public djz e;
    public final TextView[] f = new TextView[4];
    public final LinearLayout[] g = new LinearLayout[4];
    public boolean h = true;
    private View i;
    private TitleBar j;

    class a implements com.autonavi.widget.pulltorefresh.PullToRefreshBase.b {
        private a() {
        }

        /* synthetic */ a(TrafficBoardPage trafficBoardPage, byte b) {
            this();
        }

        public final void a(State state) {
            if (state == State.PULL_TO_REFRESH) {
                if (!TrafficBoardPage.this.c()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(TrafficBoardPage.this.getString(R.string.just_refresh));
                    sb.append(TrafficBoardPage.this.getString(R.string.traffic_board_error_points));
                    String sb2 = sb.toString();
                    TrafficBoardPage.this.c.setHeaderText(sb2, sb2, sb2);
                    TrafficBoardPage.this.h = false;
                    return;
                }
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(TrafficBoardPage.this.getString(R.string.last_refresh));
                sb3.append(simpleDateFormat.format(Long.valueOf(TrafficBoardPage.this.a)));
                String sb4 = sb3.toString();
                TrafficBoardPage.this.c.setHeaderText(sb4, sb4, TrafficBoardPage.this.getString(R.string.loading));
                TrafficBoardPage.this.h = true;
            }
        }
    }

    class b implements OnClickListener {
        private b() {
        }

        /* synthetic */ b(TrafficBoardPage trafficBoardPage, byte b) {
            this();
        }

        public final void onClick(View view) {
            for (int i = 0; i < TrafficBoardPage.this.f.length; i++) {
                if (TrafficBoardPage.this.g[i].equals(view)) {
                    TrafficBoardPage.this.e.a(i);
                    djz e = TrafficBoardPage.this.e;
                    try {
                        if (e.b.isShowing()) {
                            e.e.setVisibility(4);
                            e.f.setVisibility(4);
                            e.b.dismiss();
                        } else {
                            e.b.showAsDropDown(e.a);
                            if (e.e.getVisibility() != 0) {
                                new Handler().postDelayed(new Runnable() {
                                    public final void run() {
                                        djz.this.e.setVisibility(0);
                                        djz.this.f.setVisibility(0);
                                    }
                                }, 100);
                            }
                        }
                    } catch (IllegalStateException e2) {
                        kf.a((Throwable) e2);
                    }
                }
            }
        }
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.traffic_board_list_layout);
        this.b = (LinearLayout) findViewById(R.id.traffic_board_title_layout);
        this.c = (PullToRefreshListView) findViewById(R.id.traffic_topboard_lv);
        this.i = findViewById(R.id.title_splitline);
        this.d = findViewById(R.id.tab_layout);
        this.e = new djz(this.i);
        this.j = (TitleBar) findViewById(R.id.title);
        this.j.setOnBackClickListener(((djw) this.mPresenter).c);
        this.j.setOnActionClickListener(((djw) this.mPresenter).d);
        int i2 = 0;
        while (i2 < this.f.length) {
            TextView[] textViewArr = this.f;
            View view = this.d;
            StringBuilder sb = new StringBuilder("textview");
            int i3 = i2 + 1;
            sb.append(i3);
            textViewArr[i2] = (TextView) view.findViewWithTag(sb.toString());
            LinearLayout[] linearLayoutArr = this.g;
            View view2 = this.d;
            StringBuilder sb2 = new StringBuilder(ResUtils.LAYOUT);
            sb2.append(i3);
            linearLayoutArr[i2] = (LinearLayout) view2.findViewWithTag(sb2.toString());
            if (this.g[i2] != null) {
                this.g[i2].setOnClickListener(new b(this, 0));
            }
            i2 = i3;
        }
        this.c.setMode(Mode.PULL_FROM_START);
        this.c.setFootershowflag(false);
        this.c.setOnRefreshListener(((djw) this.mPresenter).f);
        this.c.setOnPullEventListener(new a(this, 0));
        this.e.r = ((djw) this.mPresenter).e;
        if (!aaw.c(getContext())) {
            this.j.setActionImgVisibility(4);
        }
    }

    public final void a() {
        if (this.j != null) {
            djw djw = (djw) this.mPresenter;
            if (djw.b != null && djw.b.getCount() > 0) {
                this.j.setActionImgVisibility(0);
            } else {
                this.j.setActionImgVisibility(4);
            }
        }
    }

    public final void b() {
        if (this.c.isPullToRefreshEnabled()) {
            this.c.onRefreshComplete();
        }
    }

    public final boolean c() {
        return 300000 <= System.currentTimeMillis() - this.a;
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new djw(this);
    }
}
