package com.autonavi.widget.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.autonavi.map.fragmentcontainer.IViewLayer;
import com.autonavi.minimap.R;
import java.util.List;

public class ActionSheet extends LinearLayout implements OnTouchListener, OnItemClickListener, IViewLayer {
    public static final int ACTION_SHEET_A1 = 0;
    public static final int ACTION_SHEET_A2 = 1;
    private TextView mCancelButton;
    private boolean mCancelable;
    private ActionAdapter mContentAdapter;
    private AbsListView mContentListView;
    private LinearLayout mContentPanel;
    private int mCurrentActionStyle;
    private defpackage.erl.a mOnBackClickListener;
    /* access modifiers changed from: private */
    public defpackage.erl.a mOnCancelClickListener;
    private OnClickListener mOnClickListener;
    private defpackage.erl.a mOnItemClickListener;
    private defpackage.erl.a mOnOutSideClickListener;
    private TextView mTitle;
    private View mTitleLayout;

    static class ActionAdapter extends BaseAdapter {
        private int mActionStyle;
        private Context mContext;
        private List<a> mItems;

        public long getItemId(int i) {
            return 0;
        }

        public ActionAdapter(Context context, List<a> list, int i) {
            this.mContext = context;
            this.mItems = list;
            this.mActionStyle = i;
        }

        public void setItems(List<a> list) {
            this.mItems = list;
        }

        public int getCount() {
            return this.mItems.size();
        }

        public Object getItem(int i) {
            return this.mItems.get(i);
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            if (this.mActionStyle == 0) {
                return getActionB1ItemView(i, view, viewGroup);
            }
            if (this.mActionStyle == 1) {
                return getActionB2ItemView(i, view, viewGroup);
            }
            return null;
        }

        private void setB1ItemWidth(View view) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            if (layoutParams != null) {
                layoutParams.width = (this.mContext.getResources().getDisplayMetrics().widthPixels - this.mContext.getResources().getDimensionPixelOffset(R.dimen.action_sheet_left_padding)) / 5;
                view.setLayoutParams(layoutParams);
            }
        }

        private View getActionB1ItemView(int i, View view, ViewGroup viewGroup) {
            View view2;
            c cVar;
            if (view == null) {
                cVar = new c();
                view2 = LayoutInflater.from(this.mContext).inflate(R.layout.view_actionsheet_b1_item, viewGroup, false);
                cVar.b = (ImageView) view2.findViewById(R.id.icon);
                cVar.a = (TextView) view2.findViewById(R.id.text);
                view2.setTag(cVar);
                setB1ItemWidth(view2);
            } else {
                view2 = view;
                cVar = (c) view.getTag();
            }
            if (this.mItems.get(i) != null) {
                a aVar = this.mItems.get(i);
                cVar.b.setImageResource(aVar.a);
                cVar.a.setText(aVar.b);
                cVar.a.setTextColor(aVar.c != null ? aVar.c.intValue() : this.mContext.getResources().getColor(R.color.f_c_3));
                cVar.a.setTextSize(0, (float) (aVar.d > 0 ? aVar.d : this.mContext.getResources().getDimensionPixelSize(R.dimen.f_s_10)));
            }
            return view2;
        }

        private View getActionB2ItemView(int i, View view, ViewGroup viewGroup) {
            View view2;
            c cVar;
            if (view == null) {
                cVar = new c();
                view2 = LayoutInflater.from(this.mContext).inflate(R.layout.view_actionsheet_b2_item, viewGroup, false);
                cVar.a = (TextView) view2.findViewById(R.id.text1);
                view2.setTag(cVar);
            } else {
                view2 = view;
                cVar = (c) view.getTag();
            }
            if (this.mItems.get(i) != null) {
                a aVar = this.mItems.get(i);
                cVar.a.setText(aVar.b);
                cVar.a.setTextColor(aVar.c != null ? aVar.c.intValue() : this.mContext.getResources().getColor(R.color.f_c_6));
                cVar.a.setTextSize(0, (float) (aVar.d > 0 ? aVar.d : this.mContext.getResources().getDimensionPixelSize(R.dimen.f_s_17)));
            }
            return view2;
        }
    }

    public static class a {
        public int a;
        public CharSequence b;
        public Integer c;
        public int d;

        public a(CharSequence charSequence, int i) {
            this.b = charSequence;
            this.a = i;
        }

        public a(CharSequence charSequence) {
            this.b = charSequence;
        }
    }

    public static class b<T extends ActionSheet> {
        public T a;
        public CharSequence b;
        public List<a> c;
        public CharSequence d;
        public defpackage.erl.a e;
        public defpackage.erl.a f;
        public defpackage.erl.a g;
        private int h;
        private Context i;
        private defpackage.erl.a j;
        private boolean k = false;

        public b(Context context) {
            this.i = context;
            this.h = 1;
        }

        public final T a() {
            if (this.a == null) {
                this.a = new ActionSheet(this.i, this.h);
            }
            if (this.b != null) {
                this.a.setTitle(this.b);
            }
            if (this.c != null) {
                this.a.setItems(this.c);
            }
            if (this.d != null) {
                this.a.setCancelText(this.d);
            }
            this.a.setOnCancelClickListener(this.f);
            this.a.setOnBackClickListener(this.j);
            this.a.setOnOutSideClickListener(this.e);
            this.a.setOnItemClickListener(this.g);
            this.a.setCancelable(this.k);
            return this.a;
        }
    }

    static class c {
        TextView a;
        ImageView b;

        c() {
        }
    }

    public View getView() {
        return this;
    }

    public void showBackground(boolean z) {
    }

    public ActionSheet(Context context) {
        this(context, 0);
    }

    public ActionSheet(Context context, int i) {
        super(context);
        this.mCancelable = false;
        this.mOnClickListener = new OnClickListener() {
            public final void onClick(View view) {
                if (ActionSheet.this.mOnCancelClickListener != null) {
                    ActionSheet.this.mOnCancelClickListener.a(ActionSheet.this, -1);
                }
            }
        };
        initView(context, i);
    }

    private void initView(Context context, int i) {
        this.mCurrentActionStyle = i;
        LayoutInflater.from(context).inflate(R.layout.view_action_sheet, this, true);
        this.mTitleLayout = findViewById(R.id.title_layout);
        this.mTitle = (TextView) findViewById(R.id.title);
        this.mContentPanel = (LinearLayout) findViewById(R.id.contentPanel);
        this.mCancelButton = (TextView) findViewById(R.id.cancel);
        setBackgroundResource(R.color.c_5_b);
        setOrientation(1);
        setGravity(80);
        setOnTouchListener(this);
        this.mCancelButton.setOnClickListener(this.mOnClickListener);
    }

    public TextView getTitle() {
        return this.mTitle;
    }

    public View getTitleLayout() {
        return this.mTitleLayout;
    }

    public void setCancelable(boolean z) {
        this.mCancelable = z;
    }

    public boolean isCancelable() {
        return this.mCancelable;
    }

    public void setOnCancelClickListener(defpackage.erl.a aVar) {
        this.mOnCancelClickListener = aVar;
    }

    public void setOnOutSideClickListener(defpackage.erl.a aVar) {
        this.mOnOutSideClickListener = aVar;
    }

    public void setOnBackClickListener(defpackage.erl.a aVar) {
        this.mOnBackClickListener = aVar;
    }

    public void setTitle(CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            this.mTitleLayout.setVisibility(8);
            return;
        }
        this.mTitleLayout.setVisibility(0);
        this.mTitle.setText(charSequence);
    }

    public void setCancelText(CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            this.mCancelButton.setText(charSequence);
        }
    }

    public void setItems(List<a> list) {
        if (this.mContentListView == null) {
            if (this.mCurrentActionStyle == 0) {
                this.mContentListView = (AbsListView) LayoutInflater.from(getContext()).inflate(R.layout.view_actionsheet_b1_content, this.mContentPanel, false);
                this.mContentPanel.setPadding(0, getResources().getDimensionPixelOffset(R.dimen.action_sheet_top_padding), 0, getResources().getDimensionPixelOffset(R.dimen.action_sheet_bottom_padding));
            } else if (this.mCurrentActionStyle == 1) {
                this.mContentListView = (AbsListView) LayoutInflater.from(getContext()).inflate(R.layout.view_actionsheet_b2_content, this.mContentPanel, false);
                this.mContentListView.setBackgroundResource(R.color.c_1);
            }
            this.mContentAdapter = new ActionAdapter(getContext(), list, this.mCurrentActionStyle);
            this.mContentListView.setAdapter(this.mContentAdapter);
            this.mContentListView.setOnItemClickListener(this);
            this.mContentPanel.addView(this.mContentListView);
            return;
        }
        this.mContentAdapter.setItems(list);
        this.mContentAdapter.notifyDataSetChanged();
    }

    public void setOnItemClickListener(defpackage.erl.a aVar) {
        this.mOnItemClickListener = aVar;
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == 1 && this.mOnOutSideClickListener != null) {
            this.mOnOutSideClickListener.a(this, -2);
        }
        return true;
    }

    public boolean onBackPressed() {
        if (this.mOnBackClickListener != null) {
            this.mOnBackClickListener.a(this, -3);
        }
        return this.mCancelable;
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        if (this.mOnItemClickListener != null) {
            this.mOnItemClickListener.a(this, i);
        }
    }
}
