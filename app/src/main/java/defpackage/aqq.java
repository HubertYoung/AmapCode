package defpackage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import com.autonavi.bundle.amaphome.components.centralcard.ModuleCentralCard;
import com.autonavi.bundle.amaphome.widget.MapHomePageWidgetManager;
import com.autonavi.bundle.uitemplate.container.SlideContainer;
import com.autonavi.bundle.uitemplate.container.internal.SlidableLayout.PanelState;
import com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManager.Stub;
import com.autonavi.bundle.uitemplate.mapwidget.OnFooterChangeListener;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetProperty;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.bundle.uitemplate.mapwidget.widget.AbstractMapWidget;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.core.MemoryStorageRef;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.ajx3.views.AmapAjxView;

/* renamed from: aqq reason: default package */
/* compiled from: CentralCardVirtualPage */
public final class aqq implements com.autonavi.bundle.amaphome.components.centralcard.ModuleCentralCard.a, OnFooterChangeListener {
    public static float a = 200.0f;
    public AmapAjxView b;
    public AbstractMapWidget c;
    public MapHomePageWidgetManager d;
    public boolean e = true;
    public a f;
    boolean g = false;
    private SlideContainer h;

    /* renamed from: aqq$a */
    /* compiled from: CentralCardVirtualPage */
    public interface a {
        void a(AmapAjxView amapAjxView);
    }

    public aqq(@NonNull Context context, MapHomePageWidgetManager mapHomePageWidgetManager, SlideContainer slideContainer) {
        this.b = new AmapAjxView(context);
        this.b.setClipChildren(false);
        this.d = mapHomePageWidgetManager;
        this.h = slideContainer;
        Stub.getMapWidgetManager().setOnFooterChangeListener(this);
    }

    public final void a(float f2, boolean z) {
        this.e = true;
        this.g = z;
        if (z) {
            a(127);
        }
        b(f2);
        if (this.c == null) {
            AnonymousClass3 r5 = new AbstractMapWidget(this.b.getContext()) {
                public final View createContentView(Context context) {
                    return aqq.this.b;
                }
            };
            WidgetProperty widgetProperty = new WidgetProperty();
            widgetProperty.setAlignType(10);
            widgetProperty.setWidgetType(WidgetType.CENTRAL_CARD);
            if (this.d != null) {
                widgetProperty.setWillBindPages(new String[]{this.d.getPageSimpleName()});
            }
            r5.setWidgetProperty(widgetProperty);
            this.c = r5;
        }
        if (this.c != null && AMapPageUtil.isHomePage()) {
            Stub.getMapWidgetManager().addWidget(this.c);
        }
        if (this.b.getParent() != null) {
            ((ViewGroup) this.b.getParent()).setClipChildren(false);
        }
    }

    public final void a() {
        this.e = false;
        if (this.g) {
            a(this.d == null ? 262 : this.d.getSlideAnchorHeight());
        }
        if (this.c != null) {
            Stub.getMapWidgetManager().removeWidget(this.c);
        }
    }

    public final void a(float f2) {
        b(f2);
        Stub.getMapWidgetManager().requestContainerLayout();
    }

    public final void b(float f2) {
        int cCardContainerWidth = this.d.getCCardContainerWidth();
        int standardUnitToPixel = DimensionUtils.standardUnitToPixel(f2);
        LayoutParams layoutParams = this.b.getLayoutParams();
        if (layoutParams == null) {
            this.b.setLayoutParams(new LayoutParams(cCardContainerWidth, standardUnitToPixel));
            return;
        }
        if (!(layoutParams.height == standardUnitToPixel && layoutParams.width == cCardContainerWidth)) {
            layoutParams.width = cCardContainerWidth;
            layoutParams.height = standardUnitToPixel;
            this.b.setLayoutParams(layoutParams);
        }
    }

    public final void b() {
        if (this.e && this.c != null) {
            Stub.getMapWidgetManager().addWidget(this.c);
        }
    }

    private void a(int i) {
        this.h.setAnchorHeight(bet.a(this.b.getContext(), i));
        if (this.h.getPanelState() == PanelState.ANCHORED) {
            int a2 = i + bet.a(this.b.getContext(), 10);
            int height = this.h.getHeight();
            if (height != 0) {
                this.h.smoothSlideTo(((float) a2) / ((float) height), 0);
            }
        }
    }

    public final void onFooterChange(boolean z) {
        MemoryStorageRef memoryStorage = Ajx.getInstance().getMemoryStorage("map_widget_container");
        if (!z) {
            memoryStorage.setItem("footerWidgetShowing", "0");
        } else if (Stub.getMapWidgetManager().findWidgetByWidgetType(WidgetType.CENTRAL_CARD) == null) {
            memoryStorage.setItem("footerWidgetShowing", "1");
            ((ModuleCentralCard) this.b.getJsModule("centralCard")).notifyCentralCardClose();
        } else {
            memoryStorage.setItem("footerWidgetShowing", "0");
        }
    }

    static /* synthetic */ void d(aqq aqq) {
        LayoutParams layoutParams = aqq.b.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.width = aqq.d.getCCardContainerWidth();
            aqq.b.setLayoutParams(layoutParams);
        }
    }
}
