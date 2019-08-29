package com.autonavi.minimap.life.order.base.page;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.life.order.base.adapter.OrderListAdapterCommon;
import com.autonavi.minimap.life.order.base.adapter.OrderListAdapterCommonOld;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.autonavi.widget.pulltorefresh.PullToRefreshListView;
import com.autonavi.widget.ui.AlertView;
import com.autonavi.widget.ui.TitleBar;
import java.util.ArrayList;

public abstract class BaseOrderListTitlePage<Presenter extends IPresenter> extends AbstractBasePage<Presenter> implements OnClickListener {
    protected View footerParent;
    protected View footerView;
    protected OrderListAdapterCommon mAdapterNew;
    protected OrderListAdapterCommonOld mAdapterOld;
    protected int mCurrentCategory = 2;
    protected View mDeleteLayout;
    protected Handler mHandler;
    protected ListView mListView;
    protected PullToRefreshListView mPullRefreshListView;
    protected CheckBox mSelectAll;
    protected TitleBar mTitleBar;

    public class a implements Runnable {
        public a() {
        }

        public final void run() {
            BaseOrderListTitlePage.this.mPullRefreshListView.onRefreshComplete();
        }
    }

    /* access modifiers changed from: protected */
    public abstract void deleteOrderByOids(String str);

    /* access modifiers changed from: protected */
    public abstract int getEmptyIconRes();

    /* access modifiers changed from: protected */
    public abstract int getEmptyTipStringRes();

    /* access modifiers changed from: protected */
    public abstract int getTitleTextRes();

    /* access modifiers changed from: protected */
    public void haveHistoryOrder(boolean z) {
    }

    /* access modifiers changed from: protected */
    public abstract boolean isShowGoOrdingBtn();

    /* access modifiers changed from: protected */
    public abstract void startGoOrderingFragment();

    /* access modifiers changed from: protected */
    public abstract void startHistoryOrderFragment();

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.order_base_list_new);
        this.mHandler = new Handler();
        View contentView = getContentView();
        initTitle(contentView);
        initDeleteLayout(contentView);
        initEmptyView(contentView);
        initListView(contentView);
        initAdapter();
    }

    /* access modifiers changed from: protected */
    public void initTitle(View view) {
        this.mTitleBar = (TitleBar) view.findViewById(R.id.title);
        this.mTitleBar.setTitle(getString(getTitleTextRes()));
        this.mTitleBar.setOnBackClickListener((OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                BaseOrderListTitlePage.this.finish();
            }
        });
        this.mTitleBar.setOnActionClickListener(new OnClickListener() {
            public final void onClick(View view) {
                if (BaseOrderListTitlePage.this.mTitleBar.getActionText().getText().equals(BaseOrderListTitlePage.this.getString(R.string.edit))) {
                    BaseOrderListTitlePage.this.mTitleBar.setActionText(BaseOrderListTitlePage.this.getString(R.string.cancel));
                    bif.a(BaseOrderListTitlePage.this.mDeleteLayout, 100, null);
                    BaseOrderListTitlePage.this.footerView.setVisibility(0);
                    BaseOrderListTitlePage.this.mDeleteLayout.setVisibility(0);
                    BaseOrderListTitlePage.this.startEditorMode();
                    return;
                }
                if (BaseOrderListTitlePage.this.mTitleBar.getActionText().getText().equals(BaseOrderListTitlePage.this.getString(R.string.cancel))) {
                    BaseOrderListTitlePage.this.cancelEidtorMode();
                }
            }
        });
        this.mTitleBar.setDivideVisibility(8);
    }

    private void initEmptyView(View view) {
        if (isShowGoOrdingBtn()) {
            View findViewById = view.findViewById(R.id.go_ordering);
            findViewById.setVisibility(0);
            findViewById.setOnClickListener(this);
        }
        TextView textView = (TextView) view.findViewById(R.id.order_empty_tip_textview);
        if (textView != null) {
            textView.setText(getEmptyTipStringRes());
        }
        ImageView imageView = (ImageView) view.findViewById(R.id.order_empty_icon);
        if (imageView != null) {
            imageView.setImageResource(getEmptyIconRes());
        }
    }

    private void initListView(View view) {
        this.mPullRefreshListView = (PullToRefreshListView) view.findViewById(R.id.order_list);
        this.mPullRefreshListView.setMode(Mode.BOTH);
        this.mListView = (ListView) this.mPullRefreshListView.getRefreshableView();
        this.footerParent = getLayoutInflater().inflate(R.layout.footer_base_order_listtitle, null);
        this.footerView = this.footerParent.findViewById(R.id.footer);
        this.mListView.addFooterView(this.footerParent);
        this.footerView.setVisibility(8);
        this.mListView.setSelector(R.drawable.transparent);
        this.mListView.setEmptyView(view.findViewById(R.id.order_empty_layout_new));
    }

    private void initAdapter() {
        initAdapterNew();
        initAdapterOld();
        PageBundle arguments = getArguments();
        if (arguments != null) {
            this.mCurrentCategory = arguments.getInt("category", 2);
        }
        if (this.mCurrentCategory == 2) {
            this.mPullRefreshListView.setAdapter(this.mAdapterNew);
        } else {
            this.mPullRefreshListView.setAdapter(this.mAdapterOld);
        }
    }

    private void initAdapterOld() {
        this.mAdapterOld = new OrderListAdapterCommonOld(getContext(), new ArrayList());
        this.mAdapterOld.setOnCheckChangedListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                BaseOrderListTitlePage.this.mSelectAll.setChecked(BaseOrderListTitlePage.this.mAdapterOld.isSelectAll());
            }
        });
    }

    /* access modifiers changed from: protected */
    public void initAdapterNew() {
        this.mAdapterNew = new OrderListAdapterCommon(getPageContext());
        this.mAdapterNew.setOnCheckedChangedListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                BaseOrderListTitlePage.this.mSelectAll.setChecked(BaseOrderListTitlePage.this.mAdapterNew.isSelectAll());
            }
        });
    }

    /* access modifiers changed from: protected */
    public void initDeleteLayout(View view) {
        this.mDeleteLayout = view.findViewById(R.id.order_delete_layout);
        this.mSelectAll = (CheckBox) view.findViewById(R.id.select_all_checkbox);
        this.mSelectAll.setOnClickListener(this);
        view.findViewById(R.id.order_delete).setOnClickListener(this);
        view.findViewById(R.id.order_select_all_layout).setOnClickListener(this);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.order_delete) {
            String oids = getOids();
            if (!TextUtils.isEmpty(oids)) {
                startDeleteAlertDialog(oids);
            }
        } else if (id == R.id.order_select_all_layout) {
            boolean isChecked = this.mSelectAll.isChecked();
            this.mSelectAll.setChecked(!isChecked);
            adapterInvalidate(!isChecked);
        } else if (id == R.id.select_all_checkbox) {
            adapterInvalidate(this.mSelectAll.isChecked());
        } else {
            if (id == R.id.go_ordering) {
                startGoOrderingFragment();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void listViewRefreshComplete() {
        if (this.mPullRefreshListView != null && this.mPullRefreshListView.isRefreshing()) {
            this.mHandler.post(new a());
        }
    }

    /* access modifiers changed from: protected */
    public void adapterInvalidate(boolean z) {
        if (this.mCurrentCategory == 2) {
            this.mAdapterNew.setSelectAll(z);
            this.mAdapterNew.notifyDataSetInvalidated();
            return;
        }
        this.mAdapterOld.setSelectAll(z);
        this.mAdapterOld.notifyDataSetInvalidated();
    }

    /* access modifiers changed from: protected */
    public String getOids() {
        if (this.mCurrentCategory == 2) {
            return this.mAdapterNew.getOids();
        }
        return this.mAdapterOld.getOids();
    }

    public void startDeleteAlertDialog(final String str) {
        com.autonavi.widget.ui.AlertView.a aVar = new com.autonavi.widget.ui.AlertView.a(getActivity());
        aVar.a(R.string.life_order_del_alert);
        aVar.a(R.string.Ok, (defpackage.ern.a) new defpackage.ern.a() {
            public final void onClick(AlertView alertView, int i) {
                BaseOrderListTitlePage.this.dismissViewLayer(alertView);
                BaseOrderListTitlePage.this.deleteOrderByOids(str);
            }
        });
        aVar.b(R.string.cancel, (defpackage.ern.a) new defpackage.ern.a() {
            public final void onClick(AlertView alertView, int i) {
                BaseOrderListTitlePage.this.dismissViewLayer(alertView);
            }
        });
        aVar.b = new defpackage.ern.a() {
            public final void onClick(AlertView alertView, int i) {
            }
        };
        aVar.c = new defpackage.ern.a() {
            public final void onClick(AlertView alertView, int i) {
            }
        };
        aVar.a(false);
        AlertView a2 = aVar.a();
        showViewLayer(a2);
        a2.startAnimation();
    }

    /* access modifiers changed from: protected */
    public void startEditorMode() {
        if (this.mCurrentCategory == 2) {
            this.mAdapterNew.setEdit(true);
            this.mAdapterNew.notifyDataSetChanged();
            return;
        }
        this.mAdapterOld.setEdit(true);
        this.mAdapterOld.notifyDataSetChanged();
    }

    /* access modifiers changed from: protected */
    public void cancelEidtorMode() {
        setOrderEditorVisibillity();
        if (this.mDeleteLayout.getVisibility() == 0) {
            bif.b(this.mDeleteLayout, 100, null);
            this.mDeleteLayout.setVisibility(8);
            this.footerView.setVisibility(8);
        }
        if (this.mAdapterNew != null) {
            this.mAdapterNew.setEdit(false);
            this.mAdapterNew.setSelectAll(false);
            this.mAdapterNew.notifyDataSetChanged();
        }
        if (this.mAdapterOld != null) {
            this.mAdapterOld.setEdit(false);
            this.mAdapterOld.setSelectAll(false);
            this.mAdapterOld.notifyDataSetChanged();
        }
        this.mSelectAll.setChecked(false);
    }

    private void setOrderEditorVisibillity() {
        if (this.mCurrentCategory == 2 && !this.mAdapterNew.isEmpty()) {
            this.mTitleBar.setActionText(getString(R.string.edit));
        } else if (this.mCurrentCategory != 1 || this.mAdapterOld.isEmpty()) {
            this.mTitleBar.setActionText("");
        } else {
            this.mTitleBar.setActionText(getString(R.string.edit));
        }
    }
}
