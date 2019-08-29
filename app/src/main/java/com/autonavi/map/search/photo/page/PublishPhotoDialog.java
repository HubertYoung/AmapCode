package com.autonavi.map.search.photo.page;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.core.LocationMode.LocationNone;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.fragmentcontainer.page.PageTheme.Transparent;
import com.autonavi.map.search.page.AbstractSearchBasePage;
import com.autonavi.minimap.R;
import java.util.List;

public class PublishPhotoDialog extends AbstractSearchBasePage<cae> implements OnClickListener, LocationNone, Transparent {
    private ProgressBar a;
    private TextView b;

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.comment_publish_dialog);
        View contentView = getContentView();
        this.a = (ProgressBar) contentView.findViewById(R.id.progress_bar);
        contentView.findViewById(R.id.btn_cancle).setOnClickListener(this);
        this.b = (TextView) contentView.findViewById(R.id.txt_title);
        this.b.setText(getActivity().getResources().getString(R.string.poi_photo_upload));
        a(getArguments());
    }

    public final void a(PageBundle pageBundle) {
        if (pageBundle == null) {
            finish();
            return;
        }
        a(0);
        List list = null;
        Object obj = pageBundle.get("PHOTOUPLOAD");
        if (obj instanceof List) {
            list = (List) obj;
        }
        ((cae) this.mPresenter).a(pageBundle.getString("POI_ID"), pageBundle.getString("POI_X"), pageBundle.getString("POI_Y"), list);
        ((cae) this.mPresenter).a();
    }

    public final void a(int i) {
        this.a.setProgress(i);
        this.a.invalidate();
    }

    public void onClick(View view) {
        if (R.id.btn_cancle == view.getId()) {
            cae cae = (cae) this.mPresenter;
            cae.b = true;
            if (cae.c != null) {
                cae.c.cancel(true);
            }
            PublishPhotoDialog publishPhotoDialog = (PublishPhotoDialog) cae.a.get();
            if (publishPhotoDialog != null) {
                publishPhotoDialog.finish();
                publishPhotoDialog.setResult(ResultType.CANCEL, (PageBundle) null);
            }
        }
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new cae(this);
    }
}
