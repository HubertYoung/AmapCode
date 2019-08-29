package com.jiuyan.inimage.a;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.View;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.android.phone.zoloz.R;

/* compiled from: PasterGalleryRecommendAdapter */
public class g extends ViewHolder {
    public final View a;

    public g(View view) {
        super(view);
        this.a = view.findViewById(R.id.layout_img);
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }
}
