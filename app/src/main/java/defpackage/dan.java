package defpackage;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

/* renamed from: dan reason: default package */
/* compiled from: IAutoRemoteView */
public interface dan extends brh {
    View getAutoRemoteTip();

    ImageView getAutoRemoteView();

    void setAutoViewOnClickListener(OnClickListener onClickListener);

    void setTipClickListener(OnClickListener onClickListener);

    void setTipViewVisibility(int i);

    void setViewVisibility(int i);
}
