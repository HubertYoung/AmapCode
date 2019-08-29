package com.jiuyan.inimage.a;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.android.phone.zoloz.R;

/* compiled from: PasterGalleryRecommendTitleAdapter */
public class l extends ViewHolder {
    public final TextView a;

    public l(View view) {
        super(view);
        this.a = (TextView) view.findViewById(R.id.layout_text);
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }
}
