package com.autonavi.minimap.bundle.evaluate.handle;

import android.view.View;
import android.view.ViewGroup;
import com.autonavi.minimap.bundle.evaluate.overwrite.BatViewArrayList;
import java.util.ArrayList;

public class DelegateComponent$1 extends BatViewArrayList {
    final /* synthetic */ cwz a;

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    public DelegateComponent$1(cwz cwz, ArrayList arrayList) {
        // this.a = cwz;
        super(arrayList);
    }

    public final void a(View view) {
        if (view instanceof ViewGroup) {
            cwz.a().a((ViewGroup) view, cxk.a().a);
        } else {
            cwz.a().a(view);
        }
    }
}
