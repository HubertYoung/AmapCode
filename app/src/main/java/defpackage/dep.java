package defpackage;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.R;
import com.autonavi.minimap.drive.auto.page.DeleteAutoConnectionFragment;
import com.autonavi.widget.ui.TitleBar;

/* renamed from: dep reason: default package */
/* compiled from: DeleteAutoConnectionPresenter */
public final class dep extends sw<DeleteAutoConnectionFragment, dek> {
    public dep(DeleteAutoConnectionFragment deleteAutoConnectionFragment) {
        super(deleteAutoConnectionFragment);
    }

    public final void onPageCreated() {
        DeleteAutoConnectionFragment deleteAutoConnectionFragment = (DeleteAutoConnectionFragment) this.mPage;
        View contentView = deleteAutoConnectionFragment.getContentView();
        ((TitleBar) contentView.findViewById(R.id.title)).setOnBackClickListener((OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                DeleteAutoConnectionFragment.this.finish();
            }
        });
        deleteAutoConnectionFragment.a = (RelativeLayout) contentView.findViewById(R.id.delete_connection_lativeLayout);
        NoDBClickUtil.a((View) deleteAutoConnectionFragment.a, deleteAutoConnectionFragment.c);
    }

    public final ON_BACK_TYPE onBackPressed() {
        DeleteAutoConnectionFragment deleteAutoConnectionFragment = (DeleteAutoConnectionFragment) this.mPage;
        if (!(deleteAutoConnectionFragment.b == null ? false : deleteAutoConnectionFragment.b.isShown())) {
            return ON_BACK_TYPE.TYPE_NORMAL;
        }
        ((DeleteAutoConnectionFragment) this.mPage).dismissAllViewLayers();
        return ON_BACK_TYPE.TYPE_IGNORE;
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i, resultType, pageBundle);
    }

    public final /* synthetic */ su a() {
        return new dek(this);
    }
}
