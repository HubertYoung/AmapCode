package com.autonavi.minimap.search.view;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.autonavi.minimap.R;

public class SearchCallBackComponent extends RelativeLayout {
    private View clearBtn;
    private View displayMoreBtn;
    private View historyFooter;
    private View mHistoryHeaderView;
    private ListView mHistoryListView;
    LayoutInflater mLayoutInflater;
    private TextView mMinngSet;
    private View mSuggHeaderView;
    private ListView mSuggListView;
    private View noHistoryTip;
    private TextView noHistoryTipText;
    private Resources res;

    public SearchCallBackComponent(Context context) {
        super(context);
        init(context);
    }

    public SearchCallBackComponent(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.res = context.getResources();
        init(context);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.clearBtn.setOnClickListener(onClickListener);
        this.displayMoreBtn.setOnClickListener(onClickListener);
    }

    private void init(Context context) {
        if (context != null) {
            this.mLayoutInflater = (LayoutInflater) context.getSystemService("layout_inflater");
            RelativeLayout relativeLayout = (RelativeLayout) this.mLayoutInflater.inflate(R.layout.fragment_search_callback_listview_component, null);
            this.mHistoryHeaderView = this.mLayoutInflater.inflate(R.layout.fragment_search_callback_list_header, null);
            this.mSuggHeaderView = this.mLayoutInflater.inflate(R.layout.fragment_search_callback_list_header, null);
            this.mSuggListView = (ListView) relativeLayout.findViewById(R.id.sug_list);
            this.mHistoryListView = (ListView) relativeLayout.findViewById(R.id.history_list);
            this.mSuggListView.addHeaderView(this.mSuggHeaderView);
            this.mHistoryListView.addHeaderView(this.mHistoryHeaderView);
            this.noHistoryTip = this.mLayoutInflater.inflate(R.layout.search_no_history_tip_view, null);
            this.noHistoryTipText = (TextView) this.noHistoryTip.findViewById(R.id.no_history_tip);
            this.noHistoryTipText.setText(String.format(this.res.getString(R.string.bus_favorite_no_history_tip), new Object[]{"地点"}));
            this.historyFooter = this.mLayoutInflater.inflate(R.layout.search_history_list_footer_view, null);
            this.clearBtn = this.historyFooter.findViewById(R.id.delete_all_history_btn);
            this.displayMoreBtn = this.historyFooter.findViewById(R.id.display_more_history_btn);
            this.noHistoryTip.setVisibility(8);
            this.historyFooter.setVisibility(8);
            this.mMinngSet = (TextView) this.mHistoryListView.findViewById(R.id.set_address);
            addView(relativeLayout);
        }
    }

    public void setPositionListener(OnClickListener onClickListener) {
        if (this.mHistoryHeaderView != null) {
            this.mHistoryHeaderView.findViewById(R.id.mylocation).setOnClickListener(onClickListener);
        }
        if (this.mSuggHeaderView != null) {
            this.mSuggHeaderView.findViewById(R.id.mylocation).setOnClickListener(onClickListener);
        }
    }

    public void setFavoriteListener(OnClickListener onClickListener) {
        if (this.mHistoryHeaderView != null) {
            this.mHistoryHeaderView.findViewById(R.id.myfavorite).setOnClickListener(onClickListener);
        }
        if (this.mSuggHeaderView != null) {
            this.mSuggHeaderView.findViewById(R.id.myfavorite).setOnClickListener(onClickListener);
        }
    }

    public void setPointListener(OnClickListener onClickListener) {
        if (this.mHistoryHeaderView != null) {
            this.mHistoryHeaderView.findViewById(R.id.mappoint).setOnClickListener(onClickListener);
        }
        if (this.mSuggHeaderView != null) {
            this.mSuggHeaderView.findViewById(R.id.mappoint).setOnClickListener(onClickListener);
        }
    }

    public void setHistoryAdapter(ArrayAdapter arrayAdapter) {
        if (this.mHistoryListView != null) {
            this.mHistoryListView.setAdapter(arrayAdapter);
        }
    }

    public void setSuggAdapter(BaseAdapter baseAdapter) {
        if (this.mSuggListView != null) {
            this.mSuggListView.setAdapter(baseAdapter);
        }
    }

    public ListView getmHistoryListView() {
        return this.mHistoryListView;
    }

    public ListView getmSuggListView() {
        return this.mSuggListView;
    }

    public void addNoHistoryTip() {
        this.mHistoryListView.removeFooterView(this.historyFooter);
        this.mHistoryListView.removeFooterView(this.noHistoryTip);
        this.mHistoryListView.addFooterView(this.noHistoryTip, null, false);
        this.noHistoryTip.setVisibility(0);
    }

    public void addHistoryFooter(boolean z) {
        this.mHistoryListView.removeFooterView(this.noHistoryTip);
        this.mHistoryListView.removeFooterView(this.historyFooter);
        int i = 0;
        this.mHistoryListView.addFooterView(this.historyFooter, null, false);
        this.historyFooter.setVisibility(0);
        View view = this.displayMoreBtn;
        if (!z) {
            i = 8;
        }
        view.setVisibility(i);
    }

    public void setHomeCompanyListener(OnClickListener onClickListener) {
        if (this.mMinngSet != null) {
            this.mMinngSet.setOnClickListener(onClickListener);
        }
    }
}
