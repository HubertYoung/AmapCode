package com.autonavi.mine.feedback.navi;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import com.autonavi.annotation.PageAction;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.operation.inter.IReportErrorFragment;

@PageAction("amap.basemap.action.feedback_report_pic_full_screen_page")
public class ReportErrorPicFullScreenPage extends AbstractBasePage<cgh> implements IReportErrorFragment {
    public static final String a;
    private ImageView b;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(ReportErrorPicFullScreenPage.class.getName());
        sb.append(".ReportErrorPicFullScreen");
        a = sb.toString();
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        this.b = new ImageView(getContext());
        this.b.setLayoutParams(new LayoutParams(-1, -1));
        this.b.setBackgroundColor(-16777216);
        this.b.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                ReportErrorPicFullScreenPage.this.finish();
            }
        });
        PageBundle arguments = getArguments();
        if (arguments != null && arguments.containsKey(a)) {
            String string = arguments.getString(a);
            if (!TextUtils.isEmpty(string)) {
                ko.a(this.b, string);
            }
        }
        setContentView((View) this.b);
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new cgh(this);
    }
}
