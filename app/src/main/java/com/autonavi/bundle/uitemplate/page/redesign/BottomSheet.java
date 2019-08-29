package com.autonavi.bundle.uitemplate.page.redesign;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.RelativeLayout;
import com.autonavi.bundle.uitemplate.container.internal.SlidableLayout.LayoutParams;
import com.autonavi.bundle.uitemplate.container.internal.SlidableLayout.PanelState;
import com.autonavi.bundle.uitemplate.container.internal.SlidableLayout.c;
import com.autonavi.bundle.uitemplate.page.SlidableAjxPage;
import com.autonavi.map.fragmentcontainer.page.PageTheme.Transparent;

public class BottomSheet extends SlidableAjxPage implements Transparent {
    View b = null;

    public void onCreate(Context context) {
        super.onCreate(context);
        View contentView = getContentView();
        LayoutParams layoutParams = new LayoutParams(0);
        RelativeLayout relativeLayout = new RelativeLayout(context);
        relativeLayout.setLayoutParams(layoutParams);
        relativeLayout.setBackgroundColor(0);
        setContentView((View) relativeLayout);
        this.b = new View(context);
        this.b.setLayoutParams(layoutParams);
        this.b.setBackgroundColor(0);
        relativeLayout.addView(this.b);
        relativeLayout.addView(contentView);
        this.a.addPanelSlideListener(new c() {
            public final void a(View view, float f) {
            }

            public final void a(View view, PanelState panelState, PanelState panelState2) {
                if (BottomSheet.this.b != null) {
                    PanelState panelState3 = BottomSheet.this.a.getPanelState();
                    if (panelState3 != PanelState.DRAGGING) {
                        Object customAttribute = BottomSheet.this.a.getCustomAttribute("bottom_sheet_show_background");
                        boolean z = true;
                        int i = 0;
                        if (!(customAttribute instanceof Boolean) || !((Boolean) customAttribute).booleanValue()) {
                            z = false;
                        }
                        if (z && panelState3 != PanelState.HIDDEN) {
                            i = Color.parseColor("#80000000");
                        }
                        BottomSheet.this.b.setBackgroundColor(i);
                    }
                }
            }
        });
        this.a.setPanelState(PanelState.HIDDEN);
    }
}
