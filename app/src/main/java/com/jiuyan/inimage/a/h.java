package com.jiuyan.inimage.a;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.android.phone.zoloz.R;
import com.jiuyan.inimage.widget.RoundProgressBar;

/* compiled from: PasterGalleryRecommendAdapter */
public class h extends ViewHolder {
    public final ImageView a;
    public final RoundProgressBar b;
    public final View c;

    public h(View view) {
        super(view);
        this.a = (ImageView) view.findViewById(R.id.img);
        this.b = (RoundProgressBar) view.findViewById(R.id.progress_bar);
        this.c = view.findViewById(R.id.layout_img);
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }
}
