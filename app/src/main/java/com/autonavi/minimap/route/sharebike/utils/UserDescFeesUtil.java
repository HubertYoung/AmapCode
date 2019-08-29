package com.autonavi.minimap.route.sharebike.utils;

import android.view.View;
import android.widget.TextView;
import com.autonavi.minimap.R;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class UserDescFeesUtil {
    private View mCutLine;
    private TextView mUserTagDescMobike;
    private TextView mUserTagDescOfo;
    private TextView mUserTagFees;

    public UserDescFeesUtil(View view) {
        if (view != null) {
            this.mUserTagFees = (TextView) view.findViewById(R.id.tip_fees);
            this.mCutLine = view.findViewById(R.id.msg_cut_line);
            this.mUserTagDescOfo = (TextView) view.findViewById(R.id.ofo_fee_desc);
            this.mUserTagDescMobike = (TextView) view.findViewById(R.id.mobike_fee_desc);
        }
    }

    public void setTagDescVisible(boolean z, int i) {
        if (z) {
            this.mUserTagDescOfo.setVisibility(i);
        } else {
            this.mUserTagDescMobike.setVisibility(i);
        }
    }

    public void setTagDescGone() {
        setTagDescVisible(true, 8);
        setTagDescVisible(false, 8);
    }

    public void setTagFeesVisible(int i) {
        this.mUserTagFees.setVisibility(i);
        if (this.mCutLine != null) {
            this.mCutLine.setVisibility(i);
        }
    }

    public void setTagFeesText(String str) {
        if (this.mUserTagFees != null) {
            this.mUserTagFees.setText(str);
        }
    }

    public void setTagFeesTextColor(int i) {
        if (this.mUserTagFees != null) {
            this.mUserTagFees.setTextColor(i);
        }
    }

    public void setTagDesc(boolean z, String str) {
        if (z) {
            if (this.mUserTagDescOfo != null) {
                this.mUserTagDescOfo.setText(str);
            }
        } else if (this.mUserTagDescMobike != null) {
            this.mUserTagDescMobike.setText(str);
        }
    }
}
