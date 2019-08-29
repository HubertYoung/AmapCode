package com.autonavi.common.utils;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class PhoneUtil$3 implements OnItemClickListener {
    final /* synthetic */ Activity val$context;
    final /* synthetic */ bje val$dlg;

    public PhoneUtil$3(bje bje, Activity activity) {
        this.val$dlg = bje;
        this.val$context = activity;
    }

    public final void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        this.val$dlg.dismiss();
        String str = (String) this.val$dlg.a.getItem(i);
        if (!TextUtils.isEmpty(str)) {
            if (str.contains("$")) {
                str = str.substring(str.lastIndexOf("$") + 1);
            }
            bnz.a(str);
        }
    }
}
