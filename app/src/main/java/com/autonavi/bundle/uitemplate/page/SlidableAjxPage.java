package com.autonavi.bundle.uitemplate.page;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import com.autonavi.bundle.uitemplate.ajx.ModuleSlideContainer;
import com.autonavi.bundle.uitemplate.container.SlideContainer;
import com.autonavi.bundle.uitemplate.container.SlideContainer.c;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.context.IAjxContext;

public class SlidableAjxPage extends Ajx3Page implements c {
    public SlideContainer a;

    public boolean a() {
        return false;
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        this.a = new SlideContainer(context);
        this.a.setLayoutParams(new LayoutParams(-1, -1));
        View contentView = getContentView();
        this.a.setOnCloseButtonClickListener(this);
        this.a.setContentView(contentView);
        this.a.setContentBackgroundColor(Color.parseColor("#FFFFFF"));
        setContentView((View) this.a);
    }

    public void onAjxContxtCreated(IAjxContext iAjxContext) {
        super.onAjxContxtCreated(iAjxContext);
        ModuleSlideContainer moduleSlideContainer = (ModuleSlideContainer) this.mAjxView.getJsModule(ModuleSlideContainer.MODULE_NAME);
        if (moduleSlideContainer != null) {
            moduleSlideContainer.attachContainer(this.a);
            moduleSlideContainer.attachRelativeAnimationAjxView(this.mAjxView);
        }
    }
}
