package com.autonavi.bundle.uitemplate.mapwidget.widget.scenicarea;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.R;

public class ScenicAreaView extends RelativeLayout {
    private ViewGroup mRootView;
    /* access modifiers changed from: private */
    public String mSchema;
    private TextView mTextView;

    public ScenicAreaView(Context context) {
        super(context);
        init(context);
    }

    public ScenicAreaView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    /* access modifiers changed from: 0000 */
    public void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.scenic_area_view, this);
        initView();
        setVisible(false);
    }

    /* access modifiers changed from: 0000 */
    public void initView() {
        this.mRootView = (ViewGroup) findViewById(R.id.scenic_area_root);
        this.mTextView = (TextView) this.mRootView.findViewById(R.id.scenic_area_text);
        this.mRootView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (!TextUtils.isEmpty(ScenicAreaView.this.mSchema)) {
                    DoNotUseTool.startScheme(new Intent("android.intent.action.VIEW", Uri.parse(ScenicAreaView.this.mSchema)));
                }
            }
        });
    }

    private void setText(String str) {
        if (this.mTextView != null) {
            this.mTextView.setText(str);
        }
    }

    public void initRootView(String str, String str2) {
        setText(str);
        this.mSchema = str2;
    }

    public void setVisible(boolean z) {
        int i = z ? 0 : 8;
        if (getVisibility() != i) {
            setVisibility(i);
        }
    }
}
