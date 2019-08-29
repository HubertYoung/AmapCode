package defpackage;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.autonavi.map.widget.LaterImageButton;
import com.autonavi.map.widget.LaterTouchListener;

/* renamed from: cev reason: default package */
/* compiled from: IZoomView */
public interface cev extends brh {
    LaterImageButton getZoomInBtn();

    View getZoomInTip();

    TextView getZoomInTipText();

    LaterImageButton getZoomOutBtn();

    View getZoomOutTip();

    TextView getZoomOutTipText();

    void setOnZoomClickListener(OnClickListener onClickListener);

    void setTag(int i, Object obj);

    void setTouchListener(LaterTouchListener laterTouchListener);

    void setVisibility(int i, boolean z);
}
