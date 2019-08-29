package com.autonavi.minimap.drive.navi.navitts.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import com.autonavi.minimap.R;

public class DownloadBackgroundLayout extends RelativeLayout {
    private ProgressBar progressBar;

    public DownloadBackgroundLayout(Context context) {
        super(context);
        initView();
    }

    public DownloadBackgroundLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView();
    }

    public DownloadBackgroundLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView();
    }

    public DownloadBackgroundLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        initView();
    }

    private void initView() {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.offline_layout_navitts_download_bg, null);
        addView(inflate, -1, -1);
        this.progressBar = (ProgressBar) inflate.findViewById(R.id.view_progress_button_pb);
    }

    public void setCurrentProgress(float f) {
        if (f < 0.0f) {
            f = 0.0f;
        }
        if (f > 1.0f) {
            f = 1.0f;
        }
        this.progressBar.setProgress((int) (((float) this.progressBar.getMax()) * f));
    }

    public void setProgressDrawable(int i) {
        this.progressBar.setProgressDrawable(getContext().getResources().getDrawable(i));
    }
}
