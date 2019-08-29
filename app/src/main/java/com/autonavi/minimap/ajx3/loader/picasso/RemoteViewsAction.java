package com.autonavi.minimap.ajx3.loader.picasso;

import android.appwidget.AppWidgetManager;
import android.widget.RemoteViews;
import com.autonavi.minimap.ajx3.image.ImageCache.Image;
import com.autonavi.minimap.ajx3.loader.picasso.Picasso.LoadedFrom;

abstract class RemoteViewsAction extends Action<RemoteViewsTarget> {
    final RemoteViews remoteViews;
    private RemoteViewsTarget target;
    final int viewId;

    static class AppWidgetAction extends RemoteViewsAction {
        private final int[] appWidgetIds;

        /* access modifiers changed from: 0000 */
        public /* bridge */ /* synthetic */ Object getTarget() {
            return RemoteViewsAction.super.getTarget();
        }

        AppWidgetAction(Picasso picasso, Request request, RemoteViews remoteViews, int i, int[] iArr, int i2, int i3, String str, Object obj, int i4, boolean z, boolean z2) {
            super(picasso, request, remoteViews, i, i4, i2, i3, obj, str, z, z2);
            this.appWidgetIds = iArr;
        }

        /* access modifiers changed from: 0000 */
        public void update() {
            AppWidgetManager.getInstance(this.picasso.context).updateAppWidget(this.appWidgetIds, this.remoteViews);
        }
    }

    static class RemoteViewsTarget {
        final RemoteViews remoteViews;
        final int viewId;

        RemoteViewsTarget(RemoteViews remoteViews2, int i) {
            this.remoteViews = remoteViews2;
            this.viewId = i;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            RemoteViewsTarget remoteViewsTarget = (RemoteViewsTarget) obj;
            return this.viewId == remoteViewsTarget.viewId && this.remoteViews.equals(remoteViewsTarget.remoteViews);
        }

        public int hashCode() {
            return (this.remoteViews.hashCode() * 31) + this.viewId;
        }
    }

    /* access modifiers changed from: 0000 */
    public abstract void update();

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    RemoteViewsAction(Picasso picasso, Request request, RemoteViews remoteViews2, int i, int i2, int i3, int i4, Object obj, String str, boolean z, boolean z2) {
        super(picasso, null, request, i3, i4, i2, null, str, obj, false, z, z2);
        this.remoteViews = remoteViews2;
        this.viewId = i;
    }

    /* access modifiers changed from: 0000 */
    public void complete(Image image, LoadedFrom loadedFrom) {
        if (image == null || image.bitmap == null) {
            throw new RuntimeException(String.format("Attempted to complete action with no result or no bitmap!\n%s", new Object[]{this}));
        }
        this.remoteViews.setImageViewBitmap(this.viewId, image.bitmap);
        update();
    }

    public void error() {
        if (this.errorResId != 0) {
            setImageResource(this.errorResId);
        }
    }

    /* access modifiers changed from: 0000 */
    public RemoteViewsTarget getTarget() {
        if (this.target == null) {
            this.target = new RemoteViewsTarget(this.remoteViews, this.viewId);
        }
        return this.target;
    }

    /* access modifiers changed from: 0000 */
    public void setImageResource(int i) {
        this.remoteViews.setImageViewResource(this.viewId, i);
        update();
    }
}
