package defpackage;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.autonavi.map.suspend.refactor.compass.UICompassWidget;

/* renamed from: cdi reason: default package */
/* compiled from: ICompassView */
public interface cdi extends brh {
    LinearLayout getCompassLayerTip();

    RelativeLayout getCompassLayout();

    UICompassWidget getCompassWidget();

    View getView();

    void setCompassLayerTipVisibility(boolean z);

    void setOnClickListener(OnClickListener onClickListener);
}
