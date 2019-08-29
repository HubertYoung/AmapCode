package com.autonavi.minimap.ajx3.widget.property;

import android.support.annotation.NonNull;
import android.view.View;
import com.autonavi.minimap.ajx3.dom.AjxDomNode;
import com.autonavi.minimap.ajx3.dom.ajxnode.AjxListCell;
import com.autonavi.minimap.ajx3.image.PictureFactory;
import com.autonavi.minimap.ajx3.image.PictureParams;

public class ShadowHelper {
    private final BaseProperty mAttribute;
    private View mContent;
    private ShadowView mShadowView;

    public ShadowHelper(BaseProperty baseProperty) {
        this.mContent = baseProperty.mView;
        this.mAttribute = baseProperty;
    }

    public boolean canSupportDropShadow(int i, int i2, int i3) {
        return PictureFactory.hasBoxShadow(i, i2, i3) && !isListCell(this.mAttribute.getNode());
    }

    public boolean isListCell(AjxDomNode ajxDomNode) {
        return (ajxDomNode instanceof AjxListCell) && ajxDomNode.getParent() == null;
    }

    public void updateDropShadow(PictureParams pictureParams) {
        if (!canSupportDropShadow(pictureParams.shadowX, pictureParams.shadowY, pictureParams.shadowBlur)) {
            if (this.mShadowView != null) {
                this.mShadowView.removeShadow();
                this.mShadowView = null;
            }
        } else if (this.mShadowView == null) {
            this.mShadowView = new ShadowView(this.mAttribute.getNode(), this.mContent, pictureParams);
            this.mShadowView.addShadow();
        } else {
            this.mShadowView.updateShadow();
        }
    }

    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (this.mShadowView != null) {
            this.mShadowView.onContentLayout(z, i, i2, i3, i4);
        }
    }

    public void beforeViewRemoved(View view) {
        if (this.mShadowView != null) {
            this.mShadowView.removeShadow();
            this.mShadowView = null;
        }
    }

    public void afterViewAdded() {
        if (this.mShadowView != null) {
            this.mShadowView.addShadow();
        }
    }

    public static void checkContentAttribute(@NonNull View view, @NonNull ShadowView shadowView) {
        shadowView.setVisibility(view.getVisibility());
        shadowView.setAlpha(view.getAlpha());
        shadowView.setTranslationX(view.getTranslationX());
        shadowView.setTranslationY(view.getTranslationY());
        shadowView.setScaleX(view.getScaleX());
        shadowView.setScaleY(view.getScaleY());
        shadowView.setRotation(view.getRotation());
    }

    public void setTranslationX(float f) {
        this.mContent.setTranslationX(f);
        if (this.mShadowView != null) {
            this.mShadowView.setTranslationX(f);
        }
    }

    public void setTranslationY(float f) {
        this.mContent.setTranslationY(f);
        if (this.mShadowView != null) {
            this.mShadowView.setTranslationY(f);
        }
    }

    public void setScaleX(float f) {
        this.mContent.setScaleX(f);
        if (this.mShadowView != null) {
            this.mShadowView.setScaleX(f);
        }
    }

    public void setScaleY(float f) {
        this.mContent.setScaleY(f);
        if (this.mShadowView != null) {
            this.mShadowView.setScaleY(f);
        }
    }

    public void setRotation(float f) {
        this.mContent.setRotation(f);
        if (this.mShadowView != null) {
            this.mShadowView.setRotation(f);
        }
    }

    public void setPivotX(float f) {
        this.mContent.setPivotX(f);
        if (this.mShadowView != null) {
            this.mShadowView.setPivotX(f + ((float) this.mShadowView.getPaddingX()));
        }
    }

    public void setPivotY(float f) {
        this.mContent.setPivotY(f);
        if (this.mShadowView != null) {
            this.mShadowView.setPivotY(f + ((float) this.mShadowView.getPaddingY()));
        }
    }

    public void setVisibility(int i) {
        this.mContent.setVisibility(i);
        if (this.mShadowView != null) {
            this.mShadowView.setVisibility(i);
        }
    }

    public void setAlpha(float f) {
        this.mContent.setAlpha(f);
        if (this.mShadowView != null) {
            this.mShadowView.setAlpha(f);
        }
    }

    public void setY(float f) {
        this.mContent.setY(f);
        if (this.mShadowView != null) {
            this.mShadowView.setY(f);
        }
    }

    public void setX(float f) {
        this.mContent.setX(f);
        if (this.mShadowView != null) {
            this.mShadowView.setY(f);
        }
    }
}
