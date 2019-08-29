package com.autonavi.map.search.comment.widget;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.core.LocationMode.LocationNone;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.fragmentcontainer.page.PageTheme.Transparent;
import com.autonavi.map.search.page.AbstractSearchBasePage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map.Entry;

public class PublishCommentDialog extends AbstractSearchBasePage<bwq> implements OnClickListener, LocationNone, Transparent {
    private ProgressBar a;

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.comment_publish_dialog);
        this.a = (ProgressBar) findView(R.id.progress_bar);
        findView(R.id.btn_cancle).setOnClickListener(this);
        a(getArguments());
    }

    public final void a(PageBundle pageBundle) {
        if (pageBundle == null) {
            finish();
            return;
        }
        a(0);
        int i = pageBundle.getInt("RATING", 0);
        String string = pageBundle.getString("COMMENT");
        List list = null;
        Object obj = pageBundle.get("PHOTOUPLOAD");
        if (obj instanceof List) {
            list = (List) obj;
        }
        ((bwq) this.mPresenter).a(pageBundle.getString("POI_ID"), i, string, list);
        ((bwq) this.mPresenter).a();
    }

    public final void a(int i) {
        this.a.setProgress(i);
        this.a.invalidate();
    }

    public void onClick(View view) {
        if (R.id.btn_cancle == view.getId()) {
            bwq bwq = (bwq) this.mPresenter;
            bwq.c = true;
            if (bwq.d != null) {
                bwq.d.cancel(true);
            }
            PublishCommentDialog publishCommentDialog = (PublishCommentDialog) bwq.a.get();
            if (publishCommentDialog != null) {
                publishCommentDialog.finish();
                publishCommentDialog.setResult(ResultType.CANCEL, (PageBundle) null);
            }
            PublishCommentDialog publishCommentDialog2 = (PublishCommentDialog) bwq.mPage;
            Entry[] entryArr = new Entry[2];
            int i = 0;
            entryArr[0] = new SimpleEntry("status", aaw.a(((PublishCommentDialog) bwq.mPage).getContext()));
            if (bwq.b.size() != 0) {
                i = 1;
            }
            entryArr[1] = new SimpleEntry(TrafficUtil.KEYWORD, Integer.valueOf(i));
            publishCommentDialog2.recordActionLog((String) "P00176", (String) "B007", entryArr);
        }
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new bwq(this);
    }
}
