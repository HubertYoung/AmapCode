package com.autonavi.bundle.uitemplate.mapwidget.widget.diy;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.autonavi.minimap.R;

public class DIYEntryView extends RelativeLayout {
    private ImageView mIcon;
    private TextView mInfo;

    public DIYEntryView(Context context) {
        super(context);
        init(context);
    }

    public DIYEntryView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    /* access modifiers changed from: 0000 */
    public void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_diy_main_map_entry_item, this);
        initView();
    }

    /* access modifiers changed from: 0000 */
    public void initView() {
        this.mIcon = (ImageView) findViewById(R.id.icon);
        this.mInfo = (TextView) findViewById(R.id.info);
    }

    public ImageView getIcon() {
        return this.mIcon;
    }

    public TextView getInfo() {
        return this.mInfo;
    }
}
