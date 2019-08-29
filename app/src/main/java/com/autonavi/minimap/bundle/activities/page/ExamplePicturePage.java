package com.autonavi.minimap.bundle.activities.page;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import com.autonavi.annotation.PageAction;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;

@PageAction("amap.basemap.action.examplepage")
public class ExamplePicturePage extends AbstractBasePage<cuc> implements OnClickListener {
    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.activities_apply_payfor_example_picture_ly);
        getContentView().setOnClickListener(this);
    }

    public void onClick(View view) {
        finish();
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new cuc(this);
    }
}
