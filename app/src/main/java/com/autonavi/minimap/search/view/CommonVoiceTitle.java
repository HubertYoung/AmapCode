package com.autonavi.minimap.search.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class CommonVoiceTitle extends RelativeLayout {
    protected elx mVoiceTitleImpl;

    public void init(ViewGroup viewGroup) {
    }

    public CommonVoiceTitle(Context context) {
        super(context);
        initTarget(context);
    }

    public CommonVoiceTitle(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initTarget(context);
    }

    public void initTarget(Context context) {
        this.mVoiceTitleImpl = new elx();
        this.mVoiceTitleImpl.a((ViewGroup) this);
    }

    public void setBShowStyleBtnControlByOutSide(boolean z) {
        if (this.mVoiceTitleImpl != null) {
            this.mVoiceTitleImpl.h = z;
        }
    }

    public void setShowStyleVisible(boolean z) {
        if (this.mVoiceTitleImpl != null) {
            this.mVoiceTitleImpl.a(z);
        }
    }

    public void setShownStyleType(int i) {
        if (this.mVoiceTitleImpl != null) {
            this.mVoiceTitleImpl.a(i);
        }
    }

    public void onClick(View view) {
        if (this.mVoiceTitleImpl != null) {
            this.mVoiceTitleImpl.onClick(view);
        }
    }

    public void setKeyword(String str) {
        if (this.mVoiceTitleImpl != null) {
            this.mVoiceTitleImpl.a(str);
        }
    }

    public String getmKeyword() {
        return this.mVoiceTitleImpl != null ? this.mVoiceTitleImpl.a() : "";
    }

    public void setBackMode() {
        if (this.mVoiceTitleImpl != null) {
            this.mVoiceTitleImpl.b();
        }
    }

    public void setCloseMode() {
        if (this.mVoiceTitleImpl != null) {
            this.mVoiceTitleImpl.c();
        }
    }

    public void setCutLineVisibility(int i) {
        if (this.mVoiceTitleImpl != null) {
            this.mVoiceTitleImpl.b(i);
        }
    }

    public boolean isBackMode() {
        if (this.mVoiceTitleImpl != null) {
            return this.mVoiceTitleImpl.g;
        }
        return false;
    }
}
