package com.squareup.picasso;

import android.app.Notification;
import android.app.NotificationManager;
import android.appwidget.AppWidgetManager;
import android.graphics.Bitmap;
import android.widget.RemoteViews;
import com.squareup.picasso.Picasso.LoadedFrom;

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

        AppWidgetAction(Picasso picasso, Request request, RemoteViews remoteViews, int i, int[] iArr, int i2, int i3, String str, Object obj, int i4) {
            super(picasso, request, remoteViews, i, i4, i2, i3, obj, str);
            this.appWidgetIds = iArr;
        }

        /* access modifiers changed from: 0000 */
        public void update() {
            AppWidgetManager.getInstance(this.picasso.context).updateAppWidget(this.appWidgetIds, this.remoteViews);
        }
    }

    static class NotificationAction extends RemoteViewsAction {
        private final Notification notification;
        private final int notificationId;

        /* access modifiers changed from: 0000 */
        public /* bridge */ /* synthetic */ Object getTarget() {
            return RemoteViewsAction.super.getTarget();
        }

        /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
        NotificationAction(Picasso picasso, Request request, RemoteViews remoteViews, int i, int i2, Notification notification2, int i3, int i4, String str, Object obj, int i5) {
            super(picasso, request, remoteViews, i, i5, i3, i4, obj, str);
            this.notificationId = i2;
            this.notification = notification2;
        }

        /* access modifiers changed from: 0000 */
        public void update() {
            ((NotificationManager) Utils.getService(this.picasso.context, "notification")).notify(this.notificationId, this.notification);
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
    RemoteViewsAction(Picasso picasso, Request request, RemoteViews remoteViews2, int i, int i2, int i3, int i4, Object obj, String str) {
        super(picasso, null, request, i3, i4, i2, null, str, obj, false);
        this.remoteViews = remoteViews2;
        this.viewId = i;
    }

    /* access modifiers changed from: 0000 */
    public void complete(Bitmap bitmap, LoadedFrom loadedFrom) {
        this.remoteViews.setImageViewBitmap(this.viewId, bitmap);
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
