package com.autonavi.minimap.photograph.page;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.autonavi.minimap.R;

public class PickPhotoPage extends PickPhotoBasePage {
    /* access modifiers changed from: protected */
    public final int e() {
        return R.layout.pick_photo_activity;
    }

    /* access modifiers changed from: protected */
    public final View a(View view) {
        return view.findViewById(R.id.dialog_root);
    }

    /* access modifiers changed from: protected */
    public final void b(View view) {
        TextView textView = (TextView) view.findViewById(R.id.photo_dialog);
        ((ImageButton) view.findViewById(R.id.take_photo)).setOnClickListener(this);
        ((ImageButton) view.findViewById(R.id.from_files)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.photo_cancle)).setOnClickListener(this);
        if (TextUtils.isEmpty(this.f)) {
            textView.setText(this.f);
        }
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.take_photo) {
            c();
        } else if (id == R.id.from_files) {
            b();
        } else {
            if (id == R.id.photo_cancle) {
                d();
            }
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final dtm createPresenter() {
        return new dtn(this);
    }
}
