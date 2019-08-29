package com.autonavi.minimap.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
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
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class ListDialog extends CompatDialog {
    private ListAdapter adapter;
    private boolean animUpIn = true;
    protected Button cancel;
    protected Button comfirm;
    private Context context;
    private String dlgTitle;
    protected ListView listView;
    private LoadingLayout mFooterLayout;
    private int mLastPage;
    protected d<ListView> mRefreshListener;
    public Button m_btnNetSearch = null;
    private OnItemClickListener onItemClickListener;
    protected TextView tvTitle;
    /* access modifiers changed from: private */
    public PullToRefreshListView vouchers_pull_refresh_list;

    public ListDialog(Activity activity) {
        super(activity, R.style.custom_dlg);
        this.context = activity;
        this.mRefreshListener = null;
        setView();
    }

    public ListDialog(Activity activity, d<ListView> dVar) {
        super(activity, R.style.custom_dlg);
        this.context = activity;
        this.mRefreshListener = dVar;
        setView();
    }

    public void setView() {
        setContentView(R.layout.v3_list_dlg);
        findViewById(R.id.wrapper_layout).setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() != 0) {
                    return false;
                }
                ListDialog.this.dismiss();
                return true;
            }
        });
        if (this.mRefreshListener == null) {
            this.listView = (ListView) findViewById(R.id.list);
            this.listView.setVisibility(0);
        } else {
            this.listView = initPullList();
            this.listView.setVisibility(0);
        }
        this.tvTitle = (TextView) findViewById(R.id.title);
        this.comfirm = (Button) findViewById(R.id.btn_confirm);
        this.cancel = (Button) findViewById(R.id.clean_history);
        this.cancel.setText(R.string.cancel);
        this.cancel.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ListDialog.this.dismiss();
            }
        });
        this.m_btnNetSearch = (Button) findViewById(R.id.btn_netsearch);
    }

    public Adapter getAdapter() {
        return this.adapter;
    }

    public ListView getListView() {
        return this.listView;
    }

    public void setAdapter(ListAdapter listAdapter) {
        this.adapter = listAdapter;
        this.listView.setAdapter(this.adapter);
    }

    public void setDlgTitle(String str) {
        this.dlgTitle = str;
        this.tvTitle.setText(this.dlgTitle);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener2) {
        this.onItemClickListener = onItemClickListener2;
        this.listView.setOnItemClickListener(this.onItemClickListener);
    }

    public void setComfirmBtnTitle(String str) {
        this.comfirm.setText(str);
    }

    public void setCancleBtnTitle(String str) {
        this.cancel.setText(str);
    }

    public void setComfirmBtnClickListener(OnClickListener onClickListener) {
        this.comfirm.setOnClickListener(onClickListener);
    }

    public void setCancleBtnClickListener(OnClickListener onClickListener) {
        this.cancel.setOnClickListener(onClickListener);
    }

    public void setComfirmBtnVisibility(int i) {
        this.comfirm.setVisibility(i);
    }

    public void show() {
        Window window = getWindow();
        window.setGravity(80);
        window.setLayout(-1, -2);
        window.setWindowAnimations(R.style.custom_dlg_animation);
        super.show();
    }

    public ListView initPullList() {
        this.vouchers_pull_refresh_list = (PullToRefreshListView) findViewById(R.id.vouchers_pull_refresh_list);
        this.vouchers_pull_refresh_list.setMode(Mode.BOTH);
        this.vouchers_pull_refresh_list.setFootershowflag(false);
        this.vouchers_pull_refresh_list.setVisibility(0);
        this.listView = (ListView) this.vouchers_pull_refresh_list.getRefreshableView();
        this.mFooterLayout = this.vouchers_pull_refresh_list.changeFooter();
        this.vouchers_pull_refresh_list.mLvFooterLoadingFrame.removeView(this.vouchers_pull_refresh_list.mFooterLoadingView);
        this.mFooterLayout.setVisibility(0);
        this.listView.addFooterView(this.mFooterLayout, null, false);
        this.vouchers_pull_refresh_list.setOnRefreshListener((d<T>) new d<ListView>() {
            public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                ListDialog.this.mRefreshListener.onPullDownToRefresh(pullToRefreshBase);
            }

            public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                ListDialog.this.mRefreshListener.onPullUpToRefresh(pullToRefreshBase);
                ListDialog.this.vouchers_pull_refresh_list.mFooterLoadingView.setVisibility(8);
            }
        });
        updatePullList(1, 1);
        return this.listView;
    }

    public void updatePullList(int i, int i2) {
        Resources resources = AMapAppGlobal.getApplication().getResources();
        if (i == 0) {
            this.vouchers_pull_refresh_list.onRefreshComplete();
            return;
        }
        if (this.mFooterLayout != null) {
            this.mFooterLayout.setVisibility(0);
        }
        this.vouchers_pull_refresh_list.onRefreshComplete();
        this.vouchers_pull_refresh_list.setMode(Mode.BOTH);
        this.vouchers_pull_refresh_list.mHeaderLoadingView.setRefreshingLabel(resources.getString(R.string.refresh_loading));
        if (i == 1) {
            this.vouchers_pull_refresh_list.hideImageHead();
            this.vouchers_pull_refresh_list.setNeedRefreshing(false);
            this.vouchers_pull_refresh_list.setHeaderText(resources.getString(R.string.at_first_page_no_last), resources.getString(R.string.at_first_page_no_last), resources.getString(R.string.refresh_loading));
            this.vouchers_pull_refresh_list.setFooterText(resources.getString(R.string.at_first_page_pull_next), resources.getString(R.string.release_for_second_page), resources.getString(R.string.refresh_loading));
        } else {
            this.vouchers_pull_refresh_list.showImageHead();
            this.vouchers_pull_refresh_list.setNeedRefreshing(true);
            PullToRefreshListView pullToRefreshListView = this.vouchers_pull_refresh_list;
            StringBuilder sb = new StringBuilder();
            sb.append(resources.getString(R.string.listdialog_drag_downward_to_load));
            int i3 = i - 1;
            sb.append(i3);
            sb.append(resources.getString(R.string.page));
            String sb2 = sb.toString();
            StringBuilder sb3 = new StringBuilder();
            sb3.append(resources.getString(R.string.release_to_refresh));
            sb3.append(i3);
            sb3.append(resources.getString(R.string.page));
            pullToRefreshListView.setHeaderText(sb2, sb3.toString(), resources.getString(R.string.refresh_loading));
            PullToRefreshListView pullToRefreshListView2 = this.vouchers_pull_refresh_list;
            StringBuilder sb4 = new StringBuilder();
            sb4.append(resources.getString(R.string.listdialog_drag_upward_to_load));
            int i4 = i + 1;
            sb4.append(i4);
            sb4.append(resources.getString(R.string.page));
            String sb5 = sb4.toString();
            StringBuilder sb6 = new StringBuilder();
            sb6.append(resources.getString(R.string.release_to_refresh));
            sb6.append(i4);
            sb6.append(resources.getString(R.string.page));
            pullToRefreshListView2.setFooterText(sb5, sb6.toString(), resources.getString(R.string.refresh_loading));
        }
        if (i >= i2) {
            PullToRefreshListView pullToRefreshListView3 = this.vouchers_pull_refresh_list;
            StringBuilder sb7 = new StringBuilder();
            sb7.append(resources.getString(R.string.current_at));
            sb7.append(i);
            sb7.append(resources.getString(R.string.page));
            sb7.append("，");
            sb7.append(resources.getString(R.string.next_page_does_not_exist));
            String sb8 = sb7.toString();
            StringBuilder sb9 = new StringBuilder();
            sb9.append(resources.getString(R.string.current_at));
            sb9.append(i);
            sb9.append(resources.getString(R.string.page));
            sb9.append("，");
            sb9.append(resources.getString(R.string.next_page_does_not_exist));
            pullToRefreshListView3.setFooterText(sb8, sb9.toString(), resources.getString(R.string.refresh_loading));
            this.vouchers_pull_refresh_list.setMode(Mode.PULL_FROM_START);
        }
        if (i <= this.mLastPage) {
            if (i != 1) {
                if (isAnimUpIn()) {
                    this.listView.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.autonavi_top_in));
                }
            }
            this.mLastPage = i;
        } else if (!isAnimUpIn()) {
            this.listView.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.autonavi_top_in));
            this.mLastPage = i;
        }
        this.listView.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.autonavi_bottom_in));
        this.mLastPage = i;
    }

    public boolean isAnimUpIn() {
        return this.animUpIn;
    }

    public void dismiss() {
        super.dismiss();
    }
}
