package com.amap.bundle.drive.setting.quicknaviwidget.main.dialog;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import java.util.List;

public class QuickAutoNaviSettingDialogFactory$1 implements OnItemClickListener {
    final /* synthetic */ qv this$0;
    final /* synthetic */ b val$itemClickListener;
    final /* synthetic */ List val$mainList;

    public QuickAutoNaviSettingDialogFactory$1(qv qvVar, b bVar, List list) {
        this.this$0 = qvVar;
        this.val$itemClickListener = bVar;
        this.val$mainList = list;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        if (this.val$itemClickListener != null) {
            this.val$itemClickListener.a(i, (String) this.val$mainList.get(i));
        }
    }
}
