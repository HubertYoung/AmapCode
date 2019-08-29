package com.ali.user.mobile.login.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.ali.user.mobile.account.bean.UserInfo;
import com.ali.user.mobile.security.ui.R;
import com.ali.user.mobile.util.StringUtil;
import java.util.List;

public class LoginHistoryAdapter<T> extends BaseAdapter {
    private List<T> a;
    private String b;
    private LayoutInflater c;
    /* access modifiers changed from: private */
    public OnLongClickListener d;
    /* access modifiers changed from: private */
    public OnClickListener e;
    private int f;

    final class HistoryViews {
        public TextView a;

        HistoryViews() {
        }
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public LoginHistoryAdapter(Context context, OnLongClickListener onLongClickListener, OnClickListener onClickListener, List<T> list, String str) {
        this.d = onLongClickListener;
        this.e = onClickListener;
        this.a = list;
        this.b = str;
        this.c = LayoutInflater.from(context);
    }

    public final void a(int i) {
        this.f = i;
    }

    public int getCount() {
        if (this.a == null) {
            return 0;
        }
        return this.a.size();
    }

    public T getItem(int i) {
        if (this.a == null) {
            return null;
        }
        return this.a.get(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        HistoryViews historyViews;
        if (view == null) {
            view = this.c.inflate(R.layout.G, viewGroup, false);
            historyViews = new HistoryViews();
            historyViews.a = (TextView) view.findViewById(R.id.aS);
            TextView textView = historyViews.a;
            LayoutParams layoutParams = (LayoutParams) textView.getLayoutParams();
            layoutParams.addRule(9);
            layoutParams.setMargins(this.f, 0, 0, 0);
            textView.setLayoutParams(layoutParams);
            view.setTag(historyViews);
        } else {
            historyViews = (HistoryViews) view.getTag();
        }
        UserInfo userInfo = (UserInfo) getItem(i);
        historyViews.a.setText(StringUtil.a(userInfo.getLogonId(), this.b));
        historyViews.a.setTag(userInfo);
        if (this.e != null) {
            view.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    LoginHistoryAdapter.this.e.onClick(((HistoryViews) view.getTag()).a);
                }
            });
        }
        if (this.d != null) {
            view.setOnLongClickListener(new OnLongClickListener() {
                public boolean onLongClick(View view) {
                    return LoginHistoryAdapter.this.d.onLongClick(((HistoryViews) view.getTag()).a);
                }
            });
        }
        return view;
    }
}
