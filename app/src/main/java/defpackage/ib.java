package defpackage;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.airbnb.lottie.model.layer.Layer;
import java.util.Collections;

/* renamed from: ib reason: default package */
/* compiled from: ShapeLayer */
public final class ib extends hx {
    private final ff g;

    ib(ew ewVar, Layer layer) {
        super(ewVar, layer);
        this.g = new ff(ewVar, this, new hv(layer.c, layer.a));
        this.g.a(Collections.emptyList(), Collections.emptyList());
    }

    /* access modifiers changed from: 0000 */
    public final void b(@NonNull Canvas canvas, Matrix matrix, int i) {
        this.g.a(canvas, matrix, i);
    }

    public final void a(RectF rectF, Matrix matrix) {
        super.a(rectF, matrix);
        this.g.a(rectF, this.a);
    }

    public final void a(@Nullable String str, @Nullable String str2, @Nullable ColorFilter colorFilter) {
        this.g.a(str, str2, colorFilter);
    }
}
