package com.autonavi.map.search.page;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.TextView;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.annotation.PageAction;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.search.comment.MyCommentedListView;
import com.autonavi.map.search.comment.MyCommentingListView;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.R;
import de.greenrobot.event.EventBus;
import java.util.AbstractMap.SimpleEntry;

@PageAction("comment_list_page")
public class MyCommentListPage extends AbstractSearchBasePage<cay> {
    public ProgressDlg a;
    /* access modifiers changed from: private */
    public ImageButton b;
    /* access modifiers changed from: private */
    public TextView c;
    /* access modifiers changed from: private */
    public TextView d;
    private ViewGroup e;
    private MyCommentedListView f;
    private MyCommentingListView g;
    private OnClickListener h = new OnClickListener() {
        public final void onClick(View view) {
            if (view == MyCommentListPage.this.b) {
                MyCommentListPage.this.finish();
            } else if (view == MyCommentListPage.this.c) {
                ((cay) MyCommentListPage.this.mPresenter).a(bwj.a(0));
                if (!((bwm) ((cay) MyCommentListPage.this.mPresenter).a.a).l) {
                    ((cay) MyCommentListPage.this.mPresenter).c();
                }
                LogManager.actionLogV25("P00244", "B001", new SimpleEntry("from", Integer.valueOf(1)));
            } else {
                if (view == MyCommentListPage.this.d) {
                    ((cay) MyCommentListPage.this.mPresenter).a(bwj.a(1));
                    if (!((bwm) ((cay) MyCommentListPage.this.mPresenter).a.a).m) {
                        ((cay) MyCommentListPage.this.mPresenter).d();
                    }
                    LogManager.actionLogV25("P00244", "B001", new SimpleEntry("from", Integer.valueOf(0)));
                }
            }
        }
    };

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.comment_fragment_list);
        requestScreenOrientation(1);
        View contentView = getContentView();
        this.b = (ImageButton) contentView.findViewById(R.id.title_back_button);
        this.c = (TextView) contentView.findViewById(R.id.title_tab_left);
        this.d = (TextView) contentView.findViewById(R.id.title_tab_right);
        this.e = (ViewGroup) contentView.findViewById(R.id.container_layout);
        this.b.setOnClickListener(this.h);
        this.c.setOnClickListener(this.h);
        this.d.setOnClickListener(this.h);
        this.f = new MyCommentedListView(getContext(), (bwi) this.mPresenter);
        this.g = new MyCommentingListView(getContext(), (bwi) this.mPresenter);
        this.e.addView(this.f, new LayoutParams(-1, -1));
        this.e.addView(this.g, new LayoutParams(-1, -1));
        int i = ((bwm) ((cay) this.mPresenter).a.a).a;
        if (i == 0) {
            ((cay) this.mPresenter).c();
        } else if (i == 1) {
            ((cay) this.mPresenter).d();
        }
        EventBus.getDefault().register(this);
    }

    public final void a(String str, final AosRequest aosRequest) {
        if (this.a != null) {
            this.a.dismiss();
        }
        if (this.a == null) {
            this.a = new ProgressDlg(getActivity(), str, "");
        }
        this.a.setCanceledOnTouchOutside(false);
        this.a.setMessage(str);
        this.a.setCancelable(true);
        this.a.setOnCancelListener(new OnCancelListener() {
            public final void onCancel(DialogInterface dialogInterface) {
                adz adz = (adz) a.a.a(adz.class);
                if (adz != null) {
                    adz.b().a();
                }
                if (aosRequest != null) {
                    yq.a();
                    yq.a(aosRequest);
                }
            }
        });
        this.a.show();
        this.a.show();
    }

    public final void a(int i) {
        if (i == 1) {
            this.f.showEmptyView();
        } else {
            this.g.showEmptyView();
        }
    }

    public final void b(int i) {
        if (i == 1) {
            this.f.onDestroy();
        } else {
            this.g.onDestroy();
        }
    }

    /* access modifiers changed from: protected */
    public void onEventMainThread(bwn bwn) {
        if (bwn.a == 1) {
            ((cay) this.mPresenter).a();
        }
    }

    public final void a(bwm bwm, bwm bwm2) {
        if (bwm.a == 0) {
            this.f.setVisibility(0);
            this.g.setVisibility(8);
            this.c.setBackgroundResource(R.drawable.mine_offlinearrow_tab1_pressed);
            this.d.setBackgroundResource(R.drawable.mine_offlinearrow_tab2_normal);
            if (getContext() != null) {
                this.c.setTextColor(getResources().getColor(R.color.f_c_1));
                this.d.setTextColor(getResources().getColor(R.color.f_c_6));
            }
            this.f.updateUI(bwm, bwm2);
            return;
        }
        if (bwm.a == 1) {
            this.f.setVisibility(8);
            this.g.setVisibility(0);
            this.c.setBackgroundResource(R.drawable.mine_offlinearrow_tab1_normal);
            this.d.setBackgroundResource(R.drawable.mine_offlinearrow_tab2_pressed);
            if (getContext() != null) {
                this.c.setTextColor(getResources().getColor(R.color.f_c_6));
                this.d.setTextColor(getResources().getColor(R.color.f_c_1));
            }
            this.g.updateUI(bwm, bwm2);
        }
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new cay(this);
    }
}
