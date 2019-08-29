package com.autonavi.minimap.bundle.evaluate.delegate;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public abstract class StatisticOnItemClickListener implements OnItemClickListener, e {
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        try {
            batOnItemClick(adapterView, view, i, j);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
