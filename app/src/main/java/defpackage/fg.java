package defpackage;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.support.annotation.Nullable;

/* renamed from: fg reason: default package */
/* compiled from: DrawingContent */
public interface fg extends fe {
    void a(Canvas canvas, Matrix matrix, int i);

    void a(RectF rectF, Matrix matrix);

    void a(@Nullable String str, @Nullable String str2, @Nullable ColorFilter colorFilter);
}
