package com.autonavi.minimap.bundle.activities.page;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.autonavi.annotation.PageAction;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;

@PageAction("amap.basemap.action.lookoverpicture")
public class LookOverPicturePage extends AbstractBasePage<cud> implements OnClickListener {
    public ImageView a;
    View b;

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.activities_apply_pay_for_picture);
        View contentView = getContentView();
        this.b = contentView.findViewById(R.id.title_btn_left);
        ((TextView) contentView.findViewById(R.id.title_text_name)).setText(R.string.picture_preview);
        this.a = (ImageView) contentView.findViewById(R.id.pic_iv);
        if (this.b != null) {
            this.b.setOnClickListener(this);
        }
    }

    public void onClick(View view) {
        finish();
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new cud(this);
    }
}
