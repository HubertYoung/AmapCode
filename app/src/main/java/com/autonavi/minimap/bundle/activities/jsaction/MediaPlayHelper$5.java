package com.autonavi.minimap.bundle.activities.jsaction;

import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MediaPlayHelper$5 implements OnSeekBarChangeListener {
    final /* synthetic */ ctq this$0;

    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    public MediaPlayHelper$5(ctq ctq) {
        this.this$0 = ctq;
    }

    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        if (z) {
            this.this$0.a.seekTo(i);
        }
    }
}
