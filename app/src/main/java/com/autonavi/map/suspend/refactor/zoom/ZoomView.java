package com.autonavi.map.suspend.refactor.zoom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.autonavi.map.widget.LaterImageButton;
import com.autonavi.map.widget.LaterTouchListener;
import com.autonavi.minimap.R;

public class ZoomView extends LinearLayout implements cev {
    private LaterImageButton zoomInBtn;
    private View zoomInTip;
    private TextView zoomInTipText;
    private LaterImageButton zoomOutBtn;
    private View zoomOutTip;
    private TextView zoomOutTipText;

    public LinearLayout getZoomViewLayout() {
        return null;
    }

    public ZoomView(Context context) {
        this(context, null);
    }

    public ZoomView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ZoomView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setOrientation(0);
        initView(LayoutInflater.from(context).inflate(R.layout.map_zoom_view, this, true));
    }

    private void initView(View view) {
        this.zoomInTip = view.findViewById(R.id.zoomInTip);
        this.zoomOutTip = view.findViewById(R.id.zoomOutTip);
        this.zoomInBtn = (LaterImageButton) view.findViewById(R.id.MapZoomIn);
        this.zoomOutBtn = (LaterImageButton) view.findViewById(R.id.MapZoomOut);
        this.zoomInTipText = (TextView) view.findViewById(R.id.tv_zoom_in_tip);
        this.zoomOutTipText = (TextView) view.findViewById(R.id.tv_zoom_out_tip);
    }

    public void setZoomInIcon(int i) {
        this.zoomInBtn.setImageResource(i);
    }

    public void setZoomOutIcon(int i) {
        this.zoomOutBtn.setImageResource(i);
    }

    public View getZoomInTip() {
        return this.zoomInTip;
    }

    public View getZoomOutTip() {
        return this.zoomOutTip;
    }

    public LaterImageButton getZoomInBtn() {
        return this.zoomInBtn;
    }

    public LaterImageButton getZoomOutBtn() {
        return this.zoomOutBtn;
    }

    public TextView getZoomOutTipText() {
        return this.zoomOutTipText;
    }

    public TextView getZoomInTipText() {
        return this.zoomInTipText;
    }

    public void setOnZoomClickListener(OnClickListener onClickListener) {
        this.zoomInTip.setOnClickListener(onClickListener);
        this.zoomOutTip.setOnClickListener(onClickListener);
    }

    public void setTouchListener(LaterTouchListener laterTouchListener) {
        this.zoomInBtn.setTouchListener(laterTouchListener);
        this.zoomOutBtn.setTouchListener(laterTouchListener);
    }

    public void setVisibility(int i) {
        setVisibility(i, false);
    }

    public void setVisibility(int i, boolean z) {
        if (!z) {
            Object tag = getTag(R.id.tag_zoom_view_presenter);
            if (tag instanceof ZoomViewPresenter) {
                ZoomViewPresenter zoomViewPresenter = (ZoomViewPresenter) tag;
                zoomViewPresenter.l = i;
                zoomViewPresenter.b();
                return;
            }
        }
        super.setVisibility(i);
    }

    public void setTag(int i, Object obj) {
        super.setTag(i, obj);
    }
}
