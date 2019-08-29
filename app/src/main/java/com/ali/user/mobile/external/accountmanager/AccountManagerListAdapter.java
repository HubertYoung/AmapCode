package com.ali.user.mobile.external.accountmanager;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.security.ui.R;
import com.ali.user.mobile.ui.widget.APTextView;
import java.util.List;

public class AccountManagerListAdapter extends BaseAdapter {
    private String TAG = "AccountManagerListAdapter";
    private List<AuthInfo> mAuthList;
    private Context mContext;
    private LayoutInflater mInflater;

    public final class ViewHolder {
        public APTextView mLeftView;
        public APTextView mRightView;

        public ViewHolder() {
        }
    }

    public Object getItem(int i) {
        return null;
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public AccountManagerListAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
    }

    public void setData(List<AuthInfo> list) {
        this.mAuthList = list;
        notifyDataSetChanged();
    }

    public int getCount() {
        if (this.mAuthList == null || this.mAuthList.isEmpty()) {
            return 0;
        }
        return this.mAuthList.size();
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        Resources resources;
        int i2;
        AliUserLog.c(this.TAG, "AccountManager adapter getView");
        boolean z = false;
        if (view == null) {
            view = this.mInflater.inflate(R.layout.c, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.mRightView = (APTextView) view.findViewById(R.id.bQ);
            viewHolder.mLeftView = (APTextView) view.findViewById(R.id.bP);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        int count = getCount();
        boolean z2 = i == 0;
        if (i == count - 1) {
            z = true;
        }
        if (z2 && z) {
            view.setBackgroundResource(R.drawable.aE);
        } else if (z2 && !z) {
            view.setBackgroundResource(R.drawable.aH);
        } else if (z2 || !z) {
            view.setBackgroundResource(R.drawable.aB);
        } else {
            view.setBackgroundResource(R.drawable.ay);
        }
        if (i >= this.mAuthList.size()) {
            String str = this.TAG;
            StringBuilder sb = new StringBuilder("position: ");
            sb.append(i);
            sb.append(" listsize: ");
            sb.append(this.mAuthList.size());
            AliUserLog.d(str, sb.toString());
            return view;
        }
        AuthInfo authInfo = this.mAuthList.get(i);
        viewHolder.mLeftView.setText(authInfo.authServiceName);
        APTextView aPTextView = viewHolder.mRightView;
        if (authInfo.isOpen) {
            resources = this.mContext.getResources();
            i2 = R.string.r;
        } else {
            resources = this.mContext.getResources();
            i2 = R.string.q;
        }
        aPTextView.setText(resources.getString(i2));
        return view;
    }
}
