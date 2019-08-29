package com.autonavi.minimap.bundle.locationselect.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.autonavi.common.model.GeoPoint;

public class ChooseFixPointContainer extends FrameLayout {
    private ChooseFixPointNewView mChooseFixPointNewView;
    private ChooseFixPointView mChooseFixPointView;
    private boolean mNewType;

    public interface a {
        void a(String str, Object obj);
    }

    public ChooseFixPointContainer(@NonNull Context context) {
        this(context, null);
    }

    public ChooseFixPointContainer(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ChooseFixPointContainer(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void init(boolean z) {
        this.mNewType = z;
        if (this.mNewType) {
            this.mChooseFixPointNewView = new ChooseFixPointNewView(getContext());
            addView(this.mChooseFixPointNewView);
            return;
        }
        this.mChooseFixPointView = new ChooseFixPointView(getContext());
        addView(this.mChooseFixPointView);
    }

    public void registerCallback(final a aVar) {
        if (this.mNewType) {
            this.mChooseFixPointNewView.registerCallback(new com.autonavi.minimap.bundle.locationselect.view.ChooseFixPointNewView.a() {
                public final void a(String str, Object obj) {
                    if (aVar != null) {
                        aVar.a(str, obj);
                    }
                }
            });
        } else {
            this.mChooseFixPointView.registerCallback(new com.autonavi.minimap.bundle.locationselect.view.ChooseFixPointView.a() {
                public final void a(String str, Object obj) {
                    if (aVar != null) {
                        aVar.a(str, obj);
                    }
                }
            });
        }
    }

    public void cancelNetWork() {
        if (this.mNewType) {
            this.mChooseFixPointNewView.cancleNetWork();
        } else {
            this.mChooseFixPointView.cancleNetWork();
        }
    }

    public void requestPoint(GeoPoint geoPoint) {
        if (this.mNewType) {
            this.mChooseFixPointNewView.requestPoint(geoPoint);
        } else {
            this.mChooseFixPointView.requestPoint(geoPoint);
        }
    }
}
