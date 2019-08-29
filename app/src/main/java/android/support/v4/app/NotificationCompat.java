package android.support.v4.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.compat.R;
import android.support.dontuse.app.RemoteInputCompatBase.RemoteInput;
import android.support.dontuse.text.BidiFormatter;
import android.support.v4.app.NotificationCompatBase.Action.Factory;
import android.support.v4.app.NotificationCompatBase.UnreadConversation;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.widget.RemoteViews;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class NotificationCompat {
    public static final int BADGE_ICON_LARGE = 2;
    public static final int BADGE_ICON_NONE = 0;
    public static final int BADGE_ICON_SMALL = 1;
    public static final String CATEGORY_ALARM = "alarm";
    public static final String CATEGORY_CALL = "call";
    public static final String CATEGORY_EMAIL = "email";
    public static final String CATEGORY_ERROR = "err";
    public static final String CATEGORY_EVENT = "event";
    public static final String CATEGORY_MESSAGE = "msg";
    public static final String CATEGORY_PROGRESS = "progress";
    public static final String CATEGORY_PROMO = "promo";
    public static final String CATEGORY_RECOMMENDATION = "recommendation";
    public static final String CATEGORY_REMINDER = "reminder";
    public static final String CATEGORY_SERVICE = "service";
    public static final String CATEGORY_SOCIAL = "social";
    public static final String CATEGORY_STATUS = "status";
    public static final String CATEGORY_SYSTEM = "sys";
    public static final String CATEGORY_TRANSPORT = "transport";
    @ColorInt
    public static final int COLOR_DEFAULT = 0;
    public static final int DEFAULT_ALL = -1;
    public static final int DEFAULT_LIGHTS = 4;
    public static final int DEFAULT_SOUND = 1;
    public static final int DEFAULT_VIBRATE = 2;
    public static final String EXTRA_AUDIO_CONTENTS_URI = "android.audioContents";
    public static final String EXTRA_BACKGROUND_IMAGE_URI = "android.backgroundImageUri";
    public static final String EXTRA_BIG_TEXT = "android.bigText";
    public static final String EXTRA_COMPACT_ACTIONS = "android.compactActions";
    public static final String EXTRA_CONVERSATION_TITLE = "android.conversationTitle";
    public static final String EXTRA_INFO_TEXT = "android.infoText";
    public static final String EXTRA_LARGE_ICON = "android.largeIcon";
    public static final String EXTRA_LARGE_ICON_BIG = "android.largeIcon.big";
    public static final String EXTRA_MEDIA_SESSION = "android.mediaSession";
    public static final String EXTRA_MESSAGES = "android.messages";
    public static final String EXTRA_PEOPLE = "android.people";
    public static final String EXTRA_PICTURE = "android.picture";
    public static final String EXTRA_PROGRESS = "android.progress";
    public static final String EXTRA_PROGRESS_INDETERMINATE = "android.progressIndeterminate";
    public static final String EXTRA_PROGRESS_MAX = "android.progressMax";
    public static final String EXTRA_REMOTE_INPUT_HISTORY = "android.remoteInputHistory";
    public static final String EXTRA_SELF_DISPLAY_NAME = "android.selfDisplayName";
    public static final String EXTRA_SHOW_CHRONOMETER = "android.showChronometer";
    public static final String EXTRA_SHOW_WHEN = "android.showWhen";
    public static final String EXTRA_SMALL_ICON = "android.icon";
    public static final String EXTRA_SUB_TEXT = "android.subText";
    public static final String EXTRA_SUMMARY_TEXT = "android.summaryText";
    public static final String EXTRA_TEMPLATE = "android.template";
    public static final String EXTRA_TEXT = "android.text";
    public static final String EXTRA_TEXT_LINES = "android.textLines";
    public static final String EXTRA_TITLE = "android.title";
    public static final String EXTRA_TITLE_BIG = "android.title.big";
    public static final int FLAG_AUTO_CANCEL = 16;
    public static final int FLAG_FOREGROUND_SERVICE = 64;
    public static final int FLAG_GROUP_SUMMARY = 512;
    @Deprecated
    public static final int FLAG_HIGH_PRIORITY = 128;
    public static final int FLAG_INSISTENT = 4;
    public static final int FLAG_LOCAL_ONLY = 256;
    public static final int FLAG_NO_CLEAR = 32;
    public static final int FLAG_ONGOING_EVENT = 2;
    public static final int FLAG_ONLY_ALERT_ONCE = 8;
    public static final int FLAG_SHOW_LIGHTS = 1;
    public static final int GROUP_ALERT_ALL = 0;
    public static final int GROUP_ALERT_CHILDREN = 2;
    public static final int GROUP_ALERT_SUMMARY = 1;
    static final NotificationCompatImpl IMPL;
    public static final int PRIORITY_DEFAULT = 0;
    public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_LOW = -1;
    public static final int PRIORITY_MAX = 2;
    public static final int PRIORITY_MIN = -2;
    public static final int STREAM_DEFAULT = -1;
    public static final int VISIBILITY_PRIVATE = 0;
    public static final int VISIBILITY_PUBLIC = 1;
    public static final int VISIBILITY_SECRET = -1;

    public static class Action extends android.support.v4.app.NotificationCompatBase.Action {
        @RestrictTo({Scope.LIBRARY_GROUP})
        public static final Factory FACTORY = new Factory() {
            public final android.support.v4.app.NotificationCompatBase.Action build(int i, CharSequence charSequence, PendingIntent pendingIntent, Bundle bundle, RemoteInput[] remoteInputArr, RemoteInput[] remoteInputArr2, boolean z) {
                Action action = new Action(i, charSequence, pendingIntent, bundle, (android.support.dontuse.app.RemoteInput[]) remoteInputArr, (android.support.dontuse.app.RemoteInput[]) remoteInputArr2, z);
                return action;
            }

            public final /* bridge */ /* synthetic */ android.support.v4.app.NotificationCompatBase.Action[] newArray(int i) {
                return new Action[i];
            }
        };
        public PendingIntent actionIntent;
        public int icon;
        private boolean mAllowGeneratedReplies;
        private final android.support.dontuse.app.RemoteInput[] mDataOnlyRemoteInputs;
        final Bundle mExtras;
        private final android.support.dontuse.app.RemoteInput[] mRemoteInputs;
        public CharSequence title;

        public static final class Builder {
            private boolean mAllowGeneratedReplies;
            private final Bundle mExtras;
            private final int mIcon;
            private final PendingIntent mIntent;
            private ArrayList<android.support.dontuse.app.RemoteInput> mRemoteInputs;
            private final CharSequence mTitle;

            public Builder(int i, CharSequence charSequence, PendingIntent pendingIntent) {
                this(i, charSequence, pendingIntent, new Bundle(), null, true);
            }

            public Builder(Action action) {
                this(action.icon, action.title, action.actionIntent, new Bundle(action.mExtras), action.getRemoteInputs(), action.getAllowGeneratedReplies());
            }

            private Builder(int i, CharSequence charSequence, PendingIntent pendingIntent, Bundle bundle, android.support.dontuse.app.RemoteInput[] remoteInputArr, boolean z) {
                ArrayList<android.support.dontuse.app.RemoteInput> arrayList;
                this.mAllowGeneratedReplies = true;
                this.mIcon = i;
                this.mTitle = Builder.limitCharSequenceLength(charSequence);
                this.mIntent = pendingIntent;
                this.mExtras = bundle;
                if (remoteInputArr == null) {
                    arrayList = null;
                } else {
                    arrayList = new ArrayList<>(Arrays.asList(remoteInputArr));
                }
                this.mRemoteInputs = arrayList;
                this.mAllowGeneratedReplies = z;
            }

            public final Builder addExtras(Bundle bundle) {
                if (bundle != null) {
                    this.mExtras.putAll(bundle);
                }
                return this;
            }

            public final Bundle getExtras() {
                return this.mExtras;
            }

            public final Builder addRemoteInput(android.support.dontuse.app.RemoteInput remoteInput) {
                if (this.mRemoteInputs == null) {
                    this.mRemoteInputs = new ArrayList<>();
                }
                this.mRemoteInputs.add(remoteInput);
                return this;
            }

            public final Builder setAllowGeneratedReplies(boolean z) {
                this.mAllowGeneratedReplies = z;
                return this;
            }

            public final Builder extend(Extender extender) {
                extender.extend(this);
                return this;
            }

            public final Action build() {
                android.support.dontuse.app.RemoteInput[] remoteInputArr;
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                if (this.mRemoteInputs != null) {
                    Iterator<android.support.dontuse.app.RemoteInput> it = this.mRemoteInputs.iterator();
                    while (it.hasNext()) {
                        android.support.dontuse.app.RemoteInput next = it.next();
                        if (!next.b && (next.a == null || next.a.length == 0) && next.c != null && !next.c.isEmpty()) {
                            arrayList.add(next);
                        } else {
                            arrayList2.add(next);
                        }
                    }
                }
                android.support.dontuse.app.RemoteInput[] remoteInputArr2 = null;
                if (arrayList.isEmpty()) {
                    remoteInputArr = null;
                } else {
                    remoteInputArr = (android.support.dontuse.app.RemoteInput[]) arrayList.toArray(new android.support.dontuse.app.RemoteInput[arrayList.size()]);
                }
                if (!arrayList2.isEmpty()) {
                    remoteInputArr2 = (android.support.dontuse.app.RemoteInput[]) arrayList2.toArray(new android.support.dontuse.app.RemoteInput[arrayList2.size()]);
                }
                Action action = new Action(this.mIcon, this.mTitle, this.mIntent, this.mExtras, remoteInputArr2, remoteInputArr, this.mAllowGeneratedReplies);
                return action;
            }
        }

        public interface Extender {
            Builder extend(Builder builder);
        }

        public static final class WearableExtender implements Extender {
            private static final int DEFAULT_FLAGS = 1;
            private static final String EXTRA_WEARABLE_EXTENSIONS = "android.wearable.EXTENSIONS";
            private static final int FLAG_AVAILABLE_OFFLINE = 1;
            private static final int FLAG_HINT_DISPLAY_INLINE = 4;
            private static final int FLAG_HINT_LAUNCHES_ACTIVITY = 2;
            private static final String KEY_CANCEL_LABEL = "cancelLabel";
            private static final String KEY_CONFIRM_LABEL = "confirmLabel";
            private static final String KEY_FLAGS = "flags";
            private static final String KEY_IN_PROGRESS_LABEL = "inProgressLabel";
            private CharSequence mCancelLabel;
            private CharSequence mConfirmLabel;
            private int mFlags = 1;
            private CharSequence mInProgressLabel;

            public WearableExtender() {
            }

            public WearableExtender(Action action) {
                Bundle bundle = action.getExtras().getBundle(EXTRA_WEARABLE_EXTENSIONS);
                if (bundle != null) {
                    this.mFlags = bundle.getInt("flags", 1);
                    this.mInProgressLabel = bundle.getCharSequence(KEY_IN_PROGRESS_LABEL);
                    this.mConfirmLabel = bundle.getCharSequence(KEY_CONFIRM_LABEL);
                    this.mCancelLabel = bundle.getCharSequence(KEY_CANCEL_LABEL);
                }
            }

            public final Builder extend(Builder builder) {
                Bundle bundle = new Bundle();
                if (this.mFlags != 1) {
                    bundle.putInt("flags", this.mFlags);
                }
                if (this.mInProgressLabel != null) {
                    bundle.putCharSequence(KEY_IN_PROGRESS_LABEL, this.mInProgressLabel);
                }
                if (this.mConfirmLabel != null) {
                    bundle.putCharSequence(KEY_CONFIRM_LABEL, this.mConfirmLabel);
                }
                if (this.mCancelLabel != null) {
                    bundle.putCharSequence(KEY_CANCEL_LABEL, this.mCancelLabel);
                }
                builder.getExtras().putBundle(EXTRA_WEARABLE_EXTENSIONS, bundle);
                return builder;
            }

            public final WearableExtender clone() {
                WearableExtender wearableExtender = new WearableExtender();
                wearableExtender.mFlags = this.mFlags;
                wearableExtender.mInProgressLabel = this.mInProgressLabel;
                wearableExtender.mConfirmLabel = this.mConfirmLabel;
                wearableExtender.mCancelLabel = this.mCancelLabel;
                return wearableExtender;
            }

            public final WearableExtender setAvailableOffline(boolean z) {
                setFlag(1, z);
                return this;
            }

            public final boolean isAvailableOffline() {
                return (this.mFlags & 1) != 0;
            }

            private void setFlag(int i, boolean z) {
                if (z) {
                    this.mFlags = i | this.mFlags;
                    return;
                }
                this.mFlags = (~i) & this.mFlags;
            }

            public final WearableExtender setInProgressLabel(CharSequence charSequence) {
                this.mInProgressLabel = charSequence;
                return this;
            }

            public final CharSequence getInProgressLabel() {
                return this.mInProgressLabel;
            }

            public final WearableExtender setConfirmLabel(CharSequence charSequence) {
                this.mConfirmLabel = charSequence;
                return this;
            }

            public final CharSequence getConfirmLabel() {
                return this.mConfirmLabel;
            }

            public final WearableExtender setCancelLabel(CharSequence charSequence) {
                this.mCancelLabel = charSequence;
                return this;
            }

            public final CharSequence getCancelLabel() {
                return this.mCancelLabel;
            }

            public final WearableExtender setHintLaunchesActivity(boolean z) {
                setFlag(2, z);
                return this;
            }

            public final boolean getHintLaunchesActivity() {
                return (this.mFlags & 2) != 0;
            }

            public final WearableExtender setHintDisplayActionInline(boolean z) {
                setFlag(4, z);
                return this;
            }

            public final boolean getHintDisplayActionInline() {
                return (this.mFlags & 4) != 0;
            }
        }

        public Action(int i, CharSequence charSequence, PendingIntent pendingIntent) {
            this(i, charSequence, pendingIntent, new Bundle(), null, null, true);
        }

        Action(int i, CharSequence charSequence, PendingIntent pendingIntent, Bundle bundle, android.support.dontuse.app.RemoteInput[] remoteInputArr, android.support.dontuse.app.RemoteInput[] remoteInputArr2, boolean z) {
            this.icon = i;
            this.title = Builder.limitCharSequenceLength(charSequence);
            this.actionIntent = pendingIntent;
            this.mExtras = bundle == null ? new Bundle() : bundle;
            this.mRemoteInputs = remoteInputArr;
            this.mDataOnlyRemoteInputs = remoteInputArr2;
            this.mAllowGeneratedReplies = z;
        }

        public int getIcon() {
            return this.icon;
        }

        public CharSequence getTitle() {
            return this.title;
        }

        public PendingIntent getActionIntent() {
            return this.actionIntent;
        }

        public Bundle getExtras() {
            return this.mExtras;
        }

        public boolean getAllowGeneratedReplies() {
            return this.mAllowGeneratedReplies;
        }

        public android.support.dontuse.app.RemoteInput[] getRemoteInputs() {
            return this.mRemoteInputs;
        }

        public android.support.dontuse.app.RemoteInput[] getDataOnlyRemoteInputs() {
            return this.mDataOnlyRemoteInputs;
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface BadgeIconType {
    }

    public static class BigPictureStyle extends Style {
        private Bitmap mBigLargeIcon;
        private boolean mBigLargeIconSet;
        private Bitmap mPicture;

        public BigPictureStyle() {
        }

        public BigPictureStyle(Builder builder) {
            setBuilder(builder);
        }

        public BigPictureStyle setBigContentTitle(CharSequence charSequence) {
            this.mBigContentTitle = Builder.limitCharSequenceLength(charSequence);
            return this;
        }

        public BigPictureStyle setSummaryText(CharSequence charSequence) {
            this.mSummaryText = Builder.limitCharSequenceLength(charSequence);
            this.mSummaryTextSet = true;
            return this;
        }

        public BigPictureStyle bigPicture(Bitmap bitmap) {
            this.mPicture = bitmap;
            return this;
        }

        public BigPictureStyle bigLargeIcon(Bitmap bitmap) {
            this.mBigLargeIcon = bitmap;
            this.mBigLargeIconSet = true;
            return this;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public void apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (VERSION.SDK_INT >= 16) {
                NotificationCompatJellybean.a(notificationBuilderWithBuilderAccessor, this.mBigContentTitle, this.mSummaryTextSet, this.mSummaryText, this.mPicture, this.mBigLargeIcon, this.mBigLargeIconSet);
            }
        }
    }

    public static class BigTextStyle extends Style {
        private CharSequence mBigText;

        public BigTextStyle() {
        }

        public BigTextStyle(Builder builder) {
            setBuilder(builder);
        }

        public BigTextStyle setBigContentTitle(CharSequence charSequence) {
            this.mBigContentTitle = Builder.limitCharSequenceLength(charSequence);
            return this;
        }

        public BigTextStyle setSummaryText(CharSequence charSequence) {
            this.mSummaryText = Builder.limitCharSequenceLength(charSequence);
            this.mSummaryTextSet = true;
            return this;
        }

        public BigTextStyle bigText(CharSequence charSequence) {
            this.mBigText = Builder.limitCharSequenceLength(charSequence);
            return this;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public void apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (VERSION.SDK_INT >= 16) {
                NotificationCompatJellybean.a(notificationBuilderWithBuilderAccessor, this.mBigContentTitle, this.mSummaryTextSet, this.mSummaryText, this.mBigText);
            }
        }
    }

    public static class Builder {
        private static final int MAX_CHARSEQUENCE_LENGTH = 5120;
        @RestrictTo({Scope.LIBRARY_GROUP})
        public ArrayList<Action> mActions;
        int mBadgeIcon;
        RemoteViews mBigContentView;
        String mCategory;
        String mChannelId;
        int mColor;
        boolean mColorized;
        boolean mColorizedSet;
        @RestrictTo({Scope.LIBRARY_GROUP})
        public CharSequence mContentInfo;
        PendingIntent mContentIntent;
        @RestrictTo({Scope.LIBRARY_GROUP})
        public CharSequence mContentText;
        @RestrictTo({Scope.LIBRARY_GROUP})
        public CharSequence mContentTitle;
        RemoteViews mContentView;
        @RestrictTo({Scope.LIBRARY_GROUP})
        public Context mContext;
        Bundle mExtras;
        PendingIntent mFullScreenIntent;
        /* access modifiers changed from: private */
        public int mGroupAlertBehavior;
        String mGroupKey;
        boolean mGroupSummary;
        RemoteViews mHeadsUpContentView;
        @RestrictTo({Scope.LIBRARY_GROUP})
        public Bitmap mLargeIcon;
        boolean mLocalOnly;
        @RestrictTo({Scope.LIBRARY_GROUP})
        public Notification mNotification;
        @RestrictTo({Scope.LIBRARY_GROUP})
        public int mNumber;
        public ArrayList<String> mPeople;
        int mPriority;
        int mProgress;
        boolean mProgressIndeterminate;
        int mProgressMax;
        Notification mPublicVersion;
        @RestrictTo({Scope.LIBRARY_GROUP})
        public CharSequence[] mRemoteInputHistory;
        String mShortcutId;
        boolean mShowWhen;
        String mSortKey;
        @RestrictTo({Scope.LIBRARY_GROUP})
        public Style mStyle;
        @RestrictTo({Scope.LIBRARY_GROUP})
        public CharSequence mSubText;
        RemoteViews mTickerView;
        long mTimeout;
        @RestrictTo({Scope.LIBRARY_GROUP})
        public boolean mUseChronometer;
        int mVisibility;

        public Builder(@NonNull Context context, @NonNull String str) {
            this.mShowWhen = true;
            this.mActions = new ArrayList<>();
            this.mLocalOnly = false;
            this.mColor = 0;
            this.mVisibility = 0;
            this.mBadgeIcon = 0;
            this.mGroupAlertBehavior = 0;
            this.mNotification = new Notification();
            this.mContext = context;
            this.mChannelId = str;
            this.mNotification.when = System.currentTimeMillis();
            this.mNotification.audioStreamType = -1;
            this.mPriority = 0;
            this.mPeople = new ArrayList<>();
        }

        @Deprecated
        public Builder(Context context) {
            this(context, null);
        }

        public Builder setWhen(long j) {
            this.mNotification.when = j;
            return this;
        }

        public Builder setShowWhen(boolean z) {
            this.mShowWhen = z;
            return this;
        }

        public Builder setUsesChronometer(boolean z) {
            this.mUseChronometer = z;
            return this;
        }

        public Builder setSmallIcon(int i) {
            this.mNotification.icon = i;
            return this;
        }

        public Builder setSmallIcon(int i, int i2) {
            this.mNotification.icon = i;
            this.mNotification.iconLevel = i2;
            return this;
        }

        public Builder setContentTitle(CharSequence charSequence) {
            this.mContentTitle = limitCharSequenceLength(charSequence);
            return this;
        }

        public Builder setContentText(CharSequence charSequence) {
            this.mContentText = limitCharSequenceLength(charSequence);
            return this;
        }

        public Builder setSubText(CharSequence charSequence) {
            this.mSubText = limitCharSequenceLength(charSequence);
            return this;
        }

        public Builder setRemoteInputHistory(CharSequence[] charSequenceArr) {
            this.mRemoteInputHistory = charSequenceArr;
            return this;
        }

        public Builder setNumber(int i) {
            this.mNumber = i;
            return this;
        }

        public Builder setContentInfo(CharSequence charSequence) {
            this.mContentInfo = limitCharSequenceLength(charSequence);
            return this;
        }

        public Builder setProgress(int i, int i2, boolean z) {
            this.mProgressMax = i;
            this.mProgress = i2;
            this.mProgressIndeterminate = z;
            return this;
        }

        public Builder setContent(RemoteViews remoteViews) {
            this.mNotification.contentView = remoteViews;
            return this;
        }

        public Builder setContentIntent(PendingIntent pendingIntent) {
            this.mContentIntent = pendingIntent;
            return this;
        }

        public Builder setDeleteIntent(PendingIntent pendingIntent) {
            this.mNotification.deleteIntent = pendingIntent;
            return this;
        }

        public Builder setFullScreenIntent(PendingIntent pendingIntent, boolean z) {
            this.mFullScreenIntent = pendingIntent;
            setFlag(128, z);
            return this;
        }

        public Builder setTicker(CharSequence charSequence) {
            this.mNotification.tickerText = limitCharSequenceLength(charSequence);
            return this;
        }

        public Builder setTicker(CharSequence charSequence, RemoteViews remoteViews) {
            this.mNotification.tickerText = limitCharSequenceLength(charSequence);
            this.mTickerView = remoteViews;
            return this;
        }

        public Builder setLargeIcon(Bitmap bitmap) {
            this.mLargeIcon = bitmap;
            return this;
        }

        public Builder setSound(Uri uri) {
            this.mNotification.sound = uri;
            this.mNotification.audioStreamType = -1;
            return this;
        }

        public Builder setSound(Uri uri, int i) {
            this.mNotification.sound = uri;
            this.mNotification.audioStreamType = i;
            return this;
        }

        public Builder setVibrate(long[] jArr) {
            this.mNotification.vibrate = jArr;
            return this;
        }

        public Builder setLights(@ColorInt int i, int i2, int i3) {
            this.mNotification.ledARGB = i;
            this.mNotification.ledOnMS = i2;
            this.mNotification.ledOffMS = i3;
            this.mNotification.flags = ((this.mNotification.ledOnMS == 0 || this.mNotification.ledOffMS == 0) ? 0 : 1) | (this.mNotification.flags & -2);
            return this;
        }

        public Builder setOngoing(boolean z) {
            setFlag(2, z);
            return this;
        }

        public Builder setColorized(boolean z) {
            this.mColorized = z;
            this.mColorizedSet = true;
            return this;
        }

        public Builder setOnlyAlertOnce(boolean z) {
            setFlag(8, z);
            return this;
        }

        public Builder setAutoCancel(boolean z) {
            setFlag(16, z);
            return this;
        }

        public Builder setLocalOnly(boolean z) {
            this.mLocalOnly = z;
            return this;
        }

        public Builder setCategory(String str) {
            this.mCategory = str;
            return this;
        }

        public Builder setDefaults(int i) {
            this.mNotification.defaults = i;
            if ((i & 4) != 0) {
                this.mNotification.flags |= 1;
            }
            return this;
        }

        private void setFlag(int i, boolean z) {
            if (z) {
                Notification notification = this.mNotification;
                notification.flags = i | notification.flags;
                return;
            }
            Notification notification2 = this.mNotification;
            notification2.flags = (~i) & notification2.flags;
        }

        public Builder setPriority(int i) {
            this.mPriority = i;
            return this;
        }

        public Builder addPerson(String str) {
            this.mPeople.add(str);
            return this;
        }

        public Builder setGroup(String str) {
            this.mGroupKey = str;
            return this;
        }

        public Builder setGroupSummary(boolean z) {
            this.mGroupSummary = z;
            return this;
        }

        public Builder setSortKey(String str) {
            this.mSortKey = str;
            return this;
        }

        public Builder addExtras(Bundle bundle) {
            if (bundle != null) {
                if (this.mExtras == null) {
                    this.mExtras = new Bundle(bundle);
                } else {
                    this.mExtras.putAll(bundle);
                }
            }
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public Bundle getExtras() {
            if (this.mExtras == null) {
                this.mExtras = new Bundle();
            }
            return this.mExtras;
        }

        public Builder addAction(int i, CharSequence charSequence, PendingIntent pendingIntent) {
            this.mActions.add(new Action(i, charSequence, pendingIntent));
            return this;
        }

        public Builder addAction(Action action) {
            this.mActions.add(action);
            return this;
        }

        public Builder setStyle(Style style) {
            if (this.mStyle != style) {
                this.mStyle = style;
                if (this.mStyle != null) {
                    this.mStyle.setBuilder(this);
                }
            }
            return this;
        }

        public Builder setColor(@ColorInt int i) {
            this.mColor = i;
            return this;
        }

        public Builder setVisibility(int i) {
            this.mVisibility = i;
            return this;
        }

        public Builder setPublicVersion(Notification notification) {
            this.mPublicVersion = notification;
            return this;
        }

        public Builder setCustomContentView(RemoteViews remoteViews) {
            this.mContentView = remoteViews;
            return this;
        }

        public Builder setCustomBigContentView(RemoteViews remoteViews) {
            this.mBigContentView = remoteViews;
            return this;
        }

        public Builder setCustomHeadsUpContentView(RemoteViews remoteViews) {
            this.mHeadsUpContentView = remoteViews;
            return this;
        }

        public Builder setChannelId(@NonNull String str) {
            this.mChannelId = str;
            return this;
        }

        public Builder setTimeoutAfter(long j) {
            this.mTimeout = j;
            return this;
        }

        public Builder setShortcutId(String str) {
            this.mShortcutId = str;
            return this;
        }

        public Builder setBadgeIconType(int i) {
            this.mBadgeIcon = i;
            return this;
        }

        public Builder setGroupAlertBehavior(int i) {
            this.mGroupAlertBehavior = i;
            return this;
        }

        public Builder extend(Extender extender) {
            extender.extend(this);
            return this;
        }

        @Deprecated
        public Notification getNotification() {
            return build();
        }

        public Notification build() {
            return NotificationCompat.IMPL.build(this, getExtender());
        }

        /* access modifiers changed from: protected */
        @RestrictTo({Scope.LIBRARY_GROUP})
        public BuilderExtender getExtender() {
            return new BuilderExtender();
        }

        protected static CharSequence limitCharSequenceLength(CharSequence charSequence) {
            if (charSequence == null) {
                return charSequence;
            }
            if (charSequence.length() > MAX_CHARSEQUENCE_LENGTH) {
                charSequence = charSequence.subSequence(0, MAX_CHARSEQUENCE_LENGTH);
            }
            return charSequence;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public RemoteViews getContentView() {
            return this.mContentView;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public RemoteViews getBigContentView() {
            return this.mBigContentView;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public RemoteViews getHeadsUpContentView() {
            return this.mHeadsUpContentView;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public long getWhenIfShowing() {
            if (this.mShowWhen) {
                return this.mNotification.when;
            }
            return 0;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public int getPriority() {
            return this.mPriority;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public int getColor() {
            return this.mColor;
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public static class BuilderExtender {
        protected BuilderExtender() {
        }

        public Notification build(Builder builder, NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            RemoteViews makeContentView = builder.mStyle != null ? builder.mStyle.makeContentView(notificationBuilderWithBuilderAccessor) : null;
            Notification build = notificationBuilderWithBuilderAccessor.build();
            if (makeContentView != null) {
                build.contentView = makeContentView;
            } else if (builder.mContentView != null) {
                build.contentView = builder.mContentView;
            }
            if (VERSION.SDK_INT >= 16 && builder.mStyle != null) {
                RemoteViews makeBigContentView = builder.mStyle.makeBigContentView(notificationBuilderWithBuilderAccessor);
                if (makeBigContentView != null) {
                    build.bigContentView = makeBigContentView;
                }
            }
            if (VERSION.SDK_INT >= 21 && builder.mStyle != null) {
                RemoteViews makeHeadsUpContentView = builder.mStyle.makeHeadsUpContentView(notificationBuilderWithBuilderAccessor);
                if (makeHeadsUpContentView != null) {
                    build.headsUpContentView = makeHeadsUpContentView;
                }
            }
            return build;
        }
    }

    public static final class CarExtender implements Extender {
        private static final String EXTRA_CAR_EXTENDER = "android.car.EXTENSIONS";
        private static final String EXTRA_COLOR = "app_color";
        private static final String EXTRA_CONVERSATION = "car_conversation";
        private static final String EXTRA_LARGE_ICON = "large_icon";
        private static final String TAG = "CarExtender";
        private int mColor = 0;
        private Bitmap mLargeIcon;
        private UnreadConversation mUnreadConversation;

        public static class UnreadConversation extends android.support.v4.app.NotificationCompatBase.UnreadConversation {
            static final android.support.v4.app.NotificationCompatBase.UnreadConversation.Factory FACTORY = new android.support.v4.app.NotificationCompatBase.UnreadConversation.Factory() {
                public final /* synthetic */ android.support.v4.app.NotificationCompatBase.UnreadConversation build(String[] strArr, RemoteInput remoteInput, PendingIntent pendingIntent, PendingIntent pendingIntent2, String[] strArr2, long j) {
                    UnreadConversation unreadConversation = new UnreadConversation(strArr, (android.support.dontuse.app.RemoteInput) remoteInput, pendingIntent, pendingIntent2, strArr2, j);
                    return unreadConversation;
                }
            };
            private final long mLatestTimestamp;
            private final String[] mMessages;
            private final String[] mParticipants;
            private final PendingIntent mReadPendingIntent;
            private final android.support.dontuse.app.RemoteInput mRemoteInput;
            private final PendingIntent mReplyPendingIntent;

            public static class Builder {
                private long mLatestTimestamp;
                private final List<String> mMessages = new ArrayList();
                private final String mParticipant;
                private PendingIntent mReadPendingIntent;
                private android.support.dontuse.app.RemoteInput mRemoteInput;
                private PendingIntent mReplyPendingIntent;

                public Builder(String str) {
                    this.mParticipant = str;
                }

                public Builder addMessage(String str) {
                    this.mMessages.add(str);
                    return this;
                }

                public Builder setReplyAction(PendingIntent pendingIntent, android.support.dontuse.app.RemoteInput remoteInput) {
                    this.mRemoteInput = remoteInput;
                    this.mReplyPendingIntent = pendingIntent;
                    return this;
                }

                public Builder setReadPendingIntent(PendingIntent pendingIntent) {
                    this.mReadPendingIntent = pendingIntent;
                    return this;
                }

                public Builder setLatestTimestamp(long j) {
                    this.mLatestTimestamp = j;
                    return this;
                }

                public UnreadConversation build() {
                    UnreadConversation unreadConversation = new UnreadConversation((String[]) this.mMessages.toArray(new String[this.mMessages.size()]), this.mRemoteInput, this.mReplyPendingIntent, this.mReadPendingIntent, new String[]{this.mParticipant}, this.mLatestTimestamp);
                    return unreadConversation;
                }
            }

            UnreadConversation(String[] strArr, android.support.dontuse.app.RemoteInput remoteInput, PendingIntent pendingIntent, PendingIntent pendingIntent2, String[] strArr2, long j) {
                this.mMessages = strArr;
                this.mRemoteInput = remoteInput;
                this.mReadPendingIntent = pendingIntent2;
                this.mReplyPendingIntent = pendingIntent;
                this.mParticipants = strArr2;
                this.mLatestTimestamp = j;
            }

            public String[] getMessages() {
                return this.mMessages;
            }

            public android.support.dontuse.app.RemoteInput getRemoteInput() {
                return this.mRemoteInput;
            }

            public PendingIntent getReplyPendingIntent() {
                return this.mReplyPendingIntent;
            }

            public PendingIntent getReadPendingIntent() {
                return this.mReadPendingIntent;
            }

            public String[] getParticipants() {
                return this.mParticipants;
            }

            public String getParticipant() {
                if (this.mParticipants.length > 0) {
                    return this.mParticipants[0];
                }
                return null;
            }

            public long getLatestTimestamp() {
                return this.mLatestTimestamp;
            }
        }

        public CarExtender() {
        }

        public CarExtender(Notification notification) {
            Bundle bundle;
            if (VERSION.SDK_INT >= 21) {
                if (NotificationCompat.getExtras(notification) == null) {
                    bundle = null;
                } else {
                    bundle = NotificationCompat.getExtras(notification).getBundle(EXTRA_CAR_EXTENDER);
                }
                if (bundle != null) {
                    this.mLargeIcon = (Bitmap) bundle.getParcelable(EXTRA_LARGE_ICON);
                    this.mColor = bundle.getInt(EXTRA_COLOR, 0);
                    this.mUnreadConversation = (UnreadConversation) NotificationCompat.IMPL.getUnreadConversationFromBundle(bundle.getBundle(EXTRA_CONVERSATION), UnreadConversation.FACTORY, android.support.dontuse.app.RemoteInput.d);
                }
            }
        }

        public final Builder extend(Builder builder) {
            if (VERSION.SDK_INT < 21) {
                return builder;
            }
            Bundle bundle = new Bundle();
            if (this.mLargeIcon != null) {
                bundle.putParcelable(EXTRA_LARGE_ICON, this.mLargeIcon);
            }
            if (this.mColor != 0) {
                bundle.putInt(EXTRA_COLOR, this.mColor);
            }
            if (this.mUnreadConversation != null) {
                bundle.putBundle(EXTRA_CONVERSATION, NotificationCompat.IMPL.getBundleForUnreadConversation(this.mUnreadConversation));
            }
            builder.getExtras().putBundle(EXTRA_CAR_EXTENDER, bundle);
            return builder;
        }

        public final CarExtender setColor(@ColorInt int i) {
            this.mColor = i;
            return this;
        }

        @ColorInt
        public final int getColor() {
            return this.mColor;
        }

        public final CarExtender setLargeIcon(Bitmap bitmap) {
            this.mLargeIcon = bitmap;
            return this;
        }

        public final Bitmap getLargeIcon() {
            return this.mLargeIcon;
        }

        public final CarExtender setUnreadConversation(UnreadConversation unreadConversation) {
            this.mUnreadConversation = unreadConversation;
            return this;
        }

        public final UnreadConversation getUnreadConversation() {
            return this.mUnreadConversation;
        }
    }

    public static class DecoratedCustomViewStyle extends Style {
        private static final int MAX_ACTION_BUTTONS = 3;

        @RestrictTo({Scope.LIBRARY_GROUP})
        public void apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (VERSION.SDK_INT >= 24) {
                notificationBuilderWithBuilderAccessor.getBuilder().setStyle(new android.app.Notification.DecoratedCustomViewStyle());
            }
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public RemoteViews makeContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (VERSION.SDK_INT < 24 && this.mBuilder.getContentView() != null) {
                return createRemoteViews(this.mBuilder.getContentView(), false);
            }
            return null;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public RemoteViews makeBigContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (VERSION.SDK_INT >= 24) {
                return null;
            }
            RemoteViews bigContentView = this.mBuilder.getBigContentView();
            if (bigContentView == null) {
                bigContentView = this.mBuilder.getContentView();
            }
            if (bigContentView == null) {
                return null;
            }
            return createRemoteViews(bigContentView, true);
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public RemoteViews makeHeadsUpContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            RemoteViews remoteViews;
            if (VERSION.SDK_INT >= 24) {
                return null;
            }
            RemoteViews headsUpContentView = this.mBuilder.getHeadsUpContentView();
            if (headsUpContentView != null) {
                remoteViews = headsUpContentView;
            } else {
                remoteViews = this.mBuilder.getContentView();
            }
            if (headsUpContentView == null) {
                return null;
            }
            return createRemoteViews(remoteViews, true);
        }

        /* JADX WARNING: Removed duplicated region for block: B:11:0x0041  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private android.widget.RemoteViews createRemoteViews(android.widget.RemoteViews r7, boolean r8) {
            /*
                r6 = this;
                int r0 = android.support.compat.R.layout.notification_template_custom_big
                r1 = 1
                r2 = 0
                android.widget.RemoteViews r0 = r6.applyStandardTemplate(r1, r0, r2)
                int r3 = android.support.compat.R.id.actions
                r0.removeAllViews(r3)
                if (r8 == 0) goto L_0x003d
                android.support.v4.app.NotificationCompat$Builder r8 = r6.mBuilder
                java.util.ArrayList<android.support.v4.app.NotificationCompat$Action> r8 = r8.mActions
                if (r8 == 0) goto L_0x003d
                android.support.v4.app.NotificationCompat$Builder r8 = r6.mBuilder
                java.util.ArrayList<android.support.v4.app.NotificationCompat$Action> r8 = r8.mActions
                int r8 = r8.size()
                r3 = 3
                int r8 = java.lang.Math.min(r8, r3)
                if (r8 <= 0) goto L_0x003d
                r3 = 0
            L_0x0025:
                if (r3 >= r8) goto L_0x003e
                android.support.v4.app.NotificationCompat$Builder r4 = r6.mBuilder
                java.util.ArrayList<android.support.v4.app.NotificationCompat$Action> r4 = r4.mActions
                java.lang.Object r4 = r4.get(r3)
                android.support.v4.app.NotificationCompat$Action r4 = (android.support.v4.app.NotificationCompat.Action) r4
                android.widget.RemoteViews r4 = r6.generateActionButton(r4)
                int r5 = android.support.compat.R.id.actions
                r0.addView(r5, r4)
                int r3 = r3 + 1
                goto L_0x0025
            L_0x003d:
                r1 = 0
            L_0x003e:
                if (r1 == 0) goto L_0x0041
                goto L_0x0043
            L_0x0041:
                r2 = 8
            L_0x0043:
                int r8 = android.support.compat.R.id.actions
                r0.setViewVisibility(r8, r2)
                int r8 = android.support.compat.R.id.action_divider
                r0.setViewVisibility(r8, r2)
                r6.buildIntoRemoteViews(r0, r7)
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v4.app.NotificationCompat.DecoratedCustomViewStyle.createRemoteViews(android.widget.RemoteViews, boolean):android.widget.RemoteViews");
        }

        private RemoteViews generateActionButton(Action action) {
            boolean z = action.actionIntent == null;
            RemoteViews remoteViews = new RemoteViews(this.mBuilder.mContext.getPackageName(), z ? R.layout.notification_action_tombstone : R.layout.notification_action);
            remoteViews.setImageViewBitmap(R.id.action_image, createColoredBitmap(action.getIcon(), this.mBuilder.mContext.getResources().getColor(R.color.notification_action_color_filter)));
            remoteViews.setTextViewText(R.id.action_text, action.title);
            if (!z) {
                remoteViews.setOnClickPendingIntent(R.id.action_container, action.actionIntent);
            }
            if (VERSION.SDK_INT >= 15) {
                remoteViews.setContentDescription(R.id.action_container, action.title);
            }
            return remoteViews;
        }
    }

    public interface Extender {
        Builder extend(Builder builder);
    }

    public static class InboxStyle extends Style {
        private ArrayList<CharSequence> mTexts = new ArrayList<>();

        public InboxStyle() {
        }

        public InboxStyle(Builder builder) {
            setBuilder(builder);
        }

        public InboxStyle setBigContentTitle(CharSequence charSequence) {
            this.mBigContentTitle = Builder.limitCharSequenceLength(charSequence);
            return this;
        }

        public InboxStyle setSummaryText(CharSequence charSequence) {
            this.mSummaryText = Builder.limitCharSequenceLength(charSequence);
            this.mSummaryTextSet = true;
            return this;
        }

        public InboxStyle addLine(CharSequence charSequence) {
            this.mTexts.add(Builder.limitCharSequenceLength(charSequence));
            return this;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public void apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (VERSION.SDK_INT >= 16) {
                NotificationCompatJellybean.a(notificationBuilderWithBuilderAccessor, this.mBigContentTitle, this.mSummaryTextSet, this.mSummaryText, this.mTexts);
            }
        }
    }

    public static class MessagingStyle extends Style {
        public static final int MAXIMUM_RETAINED_MESSAGES = 25;
        CharSequence mConversationTitle;
        List<Message> mMessages = new ArrayList();
        CharSequence mUserDisplayName;

        public static final class Message {
            static final String KEY_DATA_MIME_TYPE = "type";
            static final String KEY_DATA_URI = "uri";
            static final String KEY_EXTRAS_BUNDLE = "extras";
            static final String KEY_SENDER = "sender";
            static final String KEY_TEXT = "text";
            static final String KEY_TIMESTAMP = "time";
            private String mDataMimeType;
            private Uri mDataUri;
            private Bundle mExtras = new Bundle();
            private final CharSequence mSender;
            private final CharSequence mText;
            private final long mTimestamp;

            public Message(CharSequence charSequence, long j, CharSequence charSequence2) {
                this.mText = charSequence;
                this.mTimestamp = j;
                this.mSender = charSequence2;
            }

            public final Message setData(String str, Uri uri) {
                this.mDataMimeType = str;
                this.mDataUri = uri;
                return this;
            }

            public final CharSequence getText() {
                return this.mText;
            }

            public final long getTimestamp() {
                return this.mTimestamp;
            }

            public final Bundle getExtras() {
                return this.mExtras;
            }

            public final CharSequence getSender() {
                return this.mSender;
            }

            public final String getDataMimeType() {
                return this.mDataMimeType;
            }

            public final Uri getDataUri() {
                return this.mDataUri;
            }

            private Bundle toBundle() {
                Bundle bundle = new Bundle();
                if (this.mText != null) {
                    bundle.putCharSequence("text", this.mText);
                }
                bundle.putLong("time", this.mTimestamp);
                if (this.mSender != null) {
                    bundle.putCharSequence(KEY_SENDER, this.mSender);
                }
                if (this.mDataMimeType != null) {
                    bundle.putString("type", this.mDataMimeType);
                }
                if (this.mDataUri != null) {
                    bundle.putParcelable("uri", this.mDataUri);
                }
                if (this.mExtras != null) {
                    bundle.putBundle(KEY_EXTRAS_BUNDLE, this.mExtras);
                }
                return bundle;
            }

            static Bundle[] getBundleArrayForMessages(List<Message> list) {
                Bundle[] bundleArr = new Bundle[list.size()];
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    bundleArr[i] = list.get(i).toBundle();
                }
                return bundleArr;
            }

            static List<Message> getMessagesFromBundleArray(Parcelable[] parcelableArr) {
                ArrayList arrayList = new ArrayList(parcelableArr.length);
                for (int i = 0; i < parcelableArr.length; i++) {
                    if (parcelableArr[i] instanceof Bundle) {
                        Message messageFromBundle = getMessageFromBundle(parcelableArr[i]);
                        if (messageFromBundle != null) {
                            arrayList.add(messageFromBundle);
                        }
                    }
                }
                return arrayList;
            }

            static Message getMessageFromBundle(Bundle bundle) {
                try {
                    if (bundle.containsKey("text")) {
                        if (bundle.containsKey("time")) {
                            Message message = new Message(bundle.getCharSequence("text"), bundle.getLong("time"), bundle.getCharSequence(KEY_SENDER));
                            if (bundle.containsKey("type") && bundle.containsKey("uri")) {
                                message.setData(bundle.getString("type"), (Uri) bundle.getParcelable("uri"));
                            }
                            if (bundle.containsKey(KEY_EXTRAS_BUNDLE)) {
                                message.getExtras().putAll(bundle.getBundle(KEY_EXTRAS_BUNDLE));
                            }
                            return message;
                        }
                    }
                    return null;
                } catch (ClassCastException unused) {
                    return null;
                }
            }
        }

        MessagingStyle() {
        }

        public MessagingStyle(@NonNull CharSequence charSequence) {
            this.mUserDisplayName = charSequence;
        }

        public CharSequence getUserDisplayName() {
            return this.mUserDisplayName;
        }

        public MessagingStyle setConversationTitle(CharSequence charSequence) {
            this.mConversationTitle = charSequence;
            return this;
        }

        public CharSequence getConversationTitle() {
            return this.mConversationTitle;
        }

        public MessagingStyle addMessage(CharSequence charSequence, long j, CharSequence charSequence2) {
            this.mMessages.add(new Message(charSequence, j, charSequence2));
            if (this.mMessages.size() > 25) {
                this.mMessages.remove(0);
            }
            return this;
        }

        public MessagingStyle addMessage(Message message) {
            this.mMessages.add(message);
            if (this.mMessages.size() > 25) {
                this.mMessages.remove(0);
            }
            return this;
        }

        public List<Message> getMessages() {
            return this.mMessages;
        }

        public static MessagingStyle extractMessagingStyleFromNotification(Notification notification) {
            Bundle extras = NotificationCompat.getExtras(notification);
            if (extras != null && !extras.containsKey(NotificationCompat.EXTRA_SELF_DISPLAY_NAME)) {
                return null;
            }
            try {
                MessagingStyle messagingStyle = new MessagingStyle();
                messagingStyle.restoreFromCompatExtras(extras);
                return messagingStyle;
            } catch (ClassCastException unused) {
                return null;
            }
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public void apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            CharSequence charSequence;
            if (VERSION.SDK_INT >= 24) {
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                ArrayList arrayList3 = new ArrayList();
                ArrayList arrayList4 = new ArrayList();
                ArrayList arrayList5 = new ArrayList();
                for (Message next : this.mMessages) {
                    arrayList.add(next.getText());
                    arrayList2.add(Long.valueOf(next.getTimestamp()));
                    arrayList3.add(next.getSender());
                    arrayList4.add(next.getDataMimeType());
                    arrayList5.add(next.getDataUri());
                }
                NotificationCompatApi24.a(notificationBuilderWithBuilderAccessor, this.mUserDisplayName, this.mConversationTitle, arrayList, arrayList2, arrayList3, arrayList4, arrayList5);
                return;
            }
            Message findLatestIncomingMessage = findLatestIncomingMessage();
            if (this.mConversationTitle != null) {
                notificationBuilderWithBuilderAccessor.getBuilder().setContentTitle(this.mConversationTitle);
            } else if (findLatestIncomingMessage != null) {
                notificationBuilderWithBuilderAccessor.getBuilder().setContentTitle(findLatestIncomingMessage.getSender());
            }
            if (findLatestIncomingMessage != null) {
                android.app.Notification.Builder builder = notificationBuilderWithBuilderAccessor.getBuilder();
                if (this.mConversationTitle != null) {
                    charSequence = makeMessageLine(findLatestIncomingMessage);
                } else {
                    charSequence = findLatestIncomingMessage.getText();
                }
                builder.setContentText(charSequence);
            }
            if (VERSION.SDK_INT >= 16) {
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
                boolean z = this.mConversationTitle != null || hasMessagesWithoutSender();
                for (int size = this.mMessages.size() - 1; size >= 0; size--) {
                    Message message = this.mMessages.get(size);
                    CharSequence makeMessageLine = z ? makeMessageLine(message) : message.getText();
                    if (size != this.mMessages.size() - 1) {
                        spannableStringBuilder.insert(0, "\n");
                    }
                    spannableStringBuilder.insert(0, makeMessageLine);
                }
                NotificationCompatJellybean.a(notificationBuilderWithBuilderAccessor, (CharSequence) null, false, (CharSequence) null, (CharSequence) spannableStringBuilder);
            }
        }

        @Nullable
        private Message findLatestIncomingMessage() {
            for (int size = this.mMessages.size() - 1; size >= 0; size--) {
                Message message = this.mMessages.get(size);
                if (!TextUtils.isEmpty(message.getSender())) {
                    return message;
                }
            }
            if (!this.mMessages.isEmpty()) {
                return this.mMessages.get(this.mMessages.size() - 1);
            }
            return null;
        }

        private boolean hasMessagesWithoutSender() {
            for (int size = this.mMessages.size() - 1; size >= 0; size--) {
                if (this.mMessages.get(size).getSender() == null) {
                    return true;
                }
            }
            return false;
        }

        private CharSequence makeMessageLine(Message message) {
            BidiFormatter a = BidiFormatter.a();
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            boolean z = VERSION.SDK_INT >= 21;
            int i = z ? -16777216 : -1;
            CharSequence sender = message.getSender();
            if (TextUtils.isEmpty(message.getSender())) {
                sender = this.mUserDisplayName == null ? "" : this.mUserDisplayName;
                if (z && this.mBuilder.getColor() != 0) {
                    i = this.mBuilder.getColor();
                }
            }
            CharSequence a2 = a.a(sender);
            spannableStringBuilder.append(a2);
            spannableStringBuilder.setSpan(makeFontColorSpan(i), spannableStringBuilder.length() - a2.length(), spannableStringBuilder.length(), 33);
            spannableStringBuilder.append("  ").append(a.a(message.getText() == null ? "" : message.getText()));
            return spannableStringBuilder;
        }

        @NonNull
        private TextAppearanceSpan makeFontColorSpan(int i) {
            TextAppearanceSpan textAppearanceSpan = new TextAppearanceSpan(null, 0, 0, ColorStateList.valueOf(i), null);
            return textAppearanceSpan;
        }

        public void addCompatExtras(Bundle bundle) {
            super.addCompatExtras(bundle);
            if (this.mUserDisplayName != null) {
                bundle.putCharSequence(NotificationCompat.EXTRA_SELF_DISPLAY_NAME, this.mUserDisplayName);
            }
            if (this.mConversationTitle != null) {
                bundle.putCharSequence(NotificationCompat.EXTRA_CONVERSATION_TITLE, this.mConversationTitle);
            }
            if (!this.mMessages.isEmpty()) {
                bundle.putParcelableArray(NotificationCompat.EXTRA_MESSAGES, Message.getBundleArrayForMessages(this.mMessages));
            }
        }

        /* access modifiers changed from: protected */
        @RestrictTo({Scope.LIBRARY_GROUP})
        public void restoreFromCompatExtras(Bundle bundle) {
            this.mMessages.clear();
            this.mUserDisplayName = bundle.getString(NotificationCompat.EXTRA_SELF_DISPLAY_NAME);
            this.mConversationTitle = bundle.getString(NotificationCompat.EXTRA_CONVERSATION_TITLE);
            Parcelable[] parcelableArray = bundle.getParcelableArray(NotificationCompat.EXTRA_MESSAGES);
            if (parcelableArray != null) {
                this.mMessages = Message.getMessagesFromBundleArray(parcelableArray);
            }
        }
    }

    @RequiresApi(16)
    static class NotificationCompatApi16Impl extends NotificationCompatBaseImpl {
        NotificationCompatApi16Impl() {
        }

        public Notification build(Builder builder, BuilderExtender builderExtender) {
            Builder builder2 = builder;
            Context context = builder2.mContext;
            Notification notification = builder2.mNotification;
            CharSequence charSequence = builder2.mContentTitle;
            CharSequence charSequence2 = builder2.mContentText;
            CharSequence charSequence3 = builder2.mContentInfo;
            RemoteViews remoteViews = builder2.mTickerView;
            int i = builder2.mNumber;
            PendingIntent pendingIntent = builder2.mContentIntent;
            PendingIntent pendingIntent2 = builder2.mFullScreenIntent;
            Bitmap bitmap = builder2.mLargeIcon;
            int i2 = builder2.mProgressMax;
            int i3 = builder2.mProgress;
            boolean z = builder2.mProgressIndeterminate;
            boolean z2 = builder2.mUseChronometer;
            int i4 = builder2.mPriority;
            int i5 = i4;
            android.support.v4.app.NotificationCompatJellybean.Builder builder3 = r1;
            android.support.v4.app.NotificationCompatJellybean.Builder builder4 = new android.support.v4.app.NotificationCompatJellybean.Builder(context, notification, charSequence, charSequence2, charSequence3, remoteViews, i, pendingIntent, pendingIntent2, bitmap, i2, i3, z, z2, i5, builder2.mSubText, builder2.mLocalOnly, builder2.mExtras, builder2.mGroupKey, builder2.mGroupSummary, builder2.mSortKey, builder2.mContentView, builder2.mBigContentView);
            android.support.v4.app.NotificationCompatJellybean.Builder builder5 = builder3;
            NotificationCompat.addActionsToBuilder(builder5, builder2.mActions);
            if (builder2.mStyle != null) {
                builder2.mStyle.apply(builder5);
            }
            Notification build = builderExtender.build(builder2, builder5);
            if (builder2.mStyle != null) {
                Bundle extras = NotificationCompat.getExtras(build);
                if (extras != null) {
                    builder2.mStyle.addCompatExtras(extras);
                }
            }
            return build;
        }

        public Action getAction(Notification notification, int i) {
            return (Action) NotificationCompatJellybean.a(notification, i, Action.FACTORY, android.support.dontuse.app.RemoteInput.d);
        }

        public Action[] getActionsFromParcelableArrayList(ArrayList<Parcelable> arrayList) {
            return (Action[]) NotificationCompatJellybean.a(arrayList, Action.FACTORY, android.support.dontuse.app.RemoteInput.d);
        }

        public ArrayList<Parcelable> getParcelableArrayListForActions(Action[] actionArr) {
            return NotificationCompatJellybean.a((android.support.v4.app.NotificationCompatBase.Action[]) actionArr);
        }
    }

    @RequiresApi(19)
    static class NotificationCompatApi19Impl extends NotificationCompatApi16Impl {
        NotificationCompatApi19Impl() {
        }

        public Notification build(Builder builder, BuilderExtender builderExtender) {
            Builder builder2 = builder;
            Context context = builder2.mContext;
            Notification notification = builder2.mNotification;
            CharSequence charSequence = builder2.mContentTitle;
            CharSequence charSequence2 = builder2.mContentText;
            CharSequence charSequence3 = builder2.mContentInfo;
            RemoteViews remoteViews = builder2.mTickerView;
            int i = builder2.mNumber;
            PendingIntent pendingIntent = builder2.mContentIntent;
            PendingIntent pendingIntent2 = builder2.mFullScreenIntent;
            Bitmap bitmap = builder2.mLargeIcon;
            int i2 = builder2.mProgressMax;
            int i3 = builder2.mProgress;
            boolean z = builder2.mProgressIndeterminate;
            boolean z2 = builder2.mShowWhen;
            boolean z3 = builder2.mUseChronometer;
            boolean z4 = z3;
            android.support.v4.app.NotificationCompatKitKat.Builder builder3 = r1;
            android.support.v4.app.NotificationCompatKitKat.Builder builder4 = new android.support.v4.app.NotificationCompatKitKat.Builder(context, notification, charSequence, charSequence2, charSequence3, remoteViews, i, pendingIntent, pendingIntent2, bitmap, i2, i3, z, z2, z4, builder2.mPriority, builder2.mSubText, builder2.mLocalOnly, builder2.mPeople, builder2.mExtras, builder2.mGroupKey, builder2.mGroupSummary, builder2.mSortKey, builder2.mContentView, builder2.mBigContentView);
            android.support.v4.app.NotificationCompatKitKat.Builder builder5 = builder3;
            NotificationCompat.addActionsToBuilder(builder5, builder2.mActions);
            if (builder2.mStyle != null) {
                builder2.mStyle.apply(builder5);
            }
            return builderExtender.build(builder2, builder5);
        }

        public Action getAction(Notification notification, int i) {
            return (Action) NotificationCompatKitKat.a(notification, i, Action.FACTORY, android.support.dontuse.app.RemoteInput.d);
        }
    }

    @RequiresApi(20)
    static class NotificationCompatApi20Impl extends NotificationCompatApi19Impl {
        NotificationCompatApi20Impl() {
        }

        public Notification build(Builder builder, BuilderExtender builderExtender) {
            Builder builder2 = builder;
            Context context = builder2.mContext;
            Notification notification = builder2.mNotification;
            CharSequence charSequence = builder2.mContentTitle;
            CharSequence charSequence2 = builder2.mContentText;
            CharSequence charSequence3 = builder2.mContentInfo;
            RemoteViews remoteViews = builder2.mTickerView;
            int i = builder2.mNumber;
            PendingIntent pendingIntent = builder2.mContentIntent;
            PendingIntent pendingIntent2 = builder2.mFullScreenIntent;
            Bitmap bitmap = builder2.mLargeIcon;
            int i2 = builder2.mProgressMax;
            int i3 = builder2.mProgress;
            boolean z = builder2.mProgressIndeterminate;
            boolean z2 = builder2.mShowWhen;
            boolean z3 = z;
            boolean z4 = builder2.mUseChronometer;
            int i4 = builder2.mPriority;
            CharSequence charSequence4 = builder2.mSubText;
            boolean z5 = builder2.mLocalOnly;
            ArrayList<String> arrayList = builder2.mPeople;
            Bundle bundle = builder2.mExtras;
            String str = builder2.mGroupKey;
            boolean z6 = builder2.mGroupSummary;
            String str2 = builder2.mSortKey;
            RemoteViews remoteViews2 = builder2.mContentView;
            boolean z7 = z4;
            RemoteViews remoteViews3 = builder2.mBigContentView;
            android.support.v4.app.NotificationCompatApi20.Builder builder3 = r1;
            android.support.v4.app.NotificationCompatApi20.Builder builder4 = new android.support.v4.app.NotificationCompatApi20.Builder(context, notification, charSequence, charSequence2, charSequence3, remoteViews, i, pendingIntent, pendingIntent2, bitmap, i2, i3, z3, z2, z7, i4, charSequence4, z5, arrayList, bundle, str, z6, str2, remoteViews2, remoteViews3, builder.mGroupAlertBehavior);
            android.support.v4.app.NotificationCompatApi20.Builder builder5 = builder3;
            NotificationCompat.addActionsToBuilder(builder5, builder2.mActions);
            if (builder2.mStyle != null) {
                builder2.mStyle.apply(builder5);
            }
            Notification build = builderExtender.build(builder2, builder5);
            if (builder2.mStyle != null) {
                builder2.mStyle.addCompatExtras(NotificationCompat.getExtras(build));
            }
            return build;
        }

        public Action getAction(Notification notification, int i) {
            return (Action) NotificationCompatApi20.a(notification, i, Action.FACTORY, android.support.dontuse.app.RemoteInput.d);
        }

        public Action[] getActionsFromParcelableArrayList(ArrayList<Parcelable> arrayList) {
            return (Action[]) NotificationCompatApi20.a(arrayList, Action.FACTORY, android.support.dontuse.app.RemoteInput.d);
        }

        public ArrayList<Parcelable> getParcelableArrayListForActions(Action[] actionArr) {
            return NotificationCompatApi20.a((android.support.v4.app.NotificationCompatBase.Action[]) actionArr);
        }
    }

    @RequiresApi(21)
    static class NotificationCompatApi21Impl extends NotificationCompatApi20Impl {
        NotificationCompatApi21Impl() {
        }

        public Notification build(Builder builder, BuilderExtender builderExtender) {
            Builder builder2 = builder;
            Context context = builder2.mContext;
            Notification notification = builder2.mNotification;
            CharSequence charSequence = builder2.mContentTitle;
            CharSequence charSequence2 = builder2.mContentText;
            CharSequence charSequence3 = builder2.mContentInfo;
            RemoteViews remoteViews = builder2.mTickerView;
            int i = builder2.mNumber;
            PendingIntent pendingIntent = builder2.mContentIntent;
            PendingIntent pendingIntent2 = builder2.mFullScreenIntent;
            Bitmap bitmap = builder2.mLargeIcon;
            int i2 = builder2.mProgressMax;
            int i3 = builder2.mProgress;
            boolean z = builder2.mProgressIndeterminate;
            boolean z2 = builder2.mShowWhen;
            boolean z3 = z;
            boolean z4 = builder2.mUseChronometer;
            int i4 = builder2.mPriority;
            CharSequence charSequence4 = builder2.mSubText;
            boolean z5 = builder2.mLocalOnly;
            String str = builder2.mCategory;
            ArrayList<String> arrayList = builder2.mPeople;
            Bundle bundle = builder2.mExtras;
            int i5 = builder2.mColor;
            int i6 = builder2.mVisibility;
            Notification notification2 = builder2.mPublicVersion;
            String str2 = builder2.mGroupKey;
            boolean z6 = builder2.mGroupSummary;
            String str3 = builder2.mSortKey;
            RemoteViews remoteViews2 = builder2.mContentView;
            RemoteViews remoteViews3 = builder2.mBigContentView;
            boolean z7 = z4;
            RemoteViews remoteViews4 = builder2.mHeadsUpContentView;
            android.support.v4.app.NotificationCompatApi21.Builder builder3 = r1;
            android.support.v4.app.NotificationCompatApi21.Builder builder4 = new android.support.v4.app.NotificationCompatApi21.Builder(context, notification, charSequence, charSequence2, charSequence3, remoteViews, i, pendingIntent, pendingIntent2, bitmap, i2, i3, z3, z2, z7, i4, charSequence4, z5, str, arrayList, bundle, i5, i6, notification2, str2, z6, str3, remoteViews2, remoteViews3, remoteViews4, builder.mGroupAlertBehavior);
            android.support.v4.app.NotificationCompatApi21.Builder builder5 = builder3;
            NotificationCompat.addActionsToBuilder(builder5, builder2.mActions);
            if (builder2.mStyle != null) {
                builder2.mStyle.apply(builder5);
            }
            Notification build = builderExtender.build(builder2, builder5);
            if (builder2.mStyle != null) {
                builder2.mStyle.addCompatExtras(NotificationCompat.getExtras(build));
            }
            return build;
        }

        public Bundle getBundleForUnreadConversation(UnreadConversation unreadConversation) {
            return NotificationCompatApi21.a(unreadConversation);
        }

        public UnreadConversation getUnreadConversationFromBundle(Bundle bundle, UnreadConversation.Factory factory, RemoteInput.Factory factory2) {
            return NotificationCompatApi21.a(bundle, factory, factory2);
        }
    }

    @RequiresApi(24)
    static class NotificationCompatApi24Impl extends NotificationCompatApi21Impl {
        NotificationCompatApi24Impl() {
        }

        public Notification build(Builder builder, BuilderExtender builderExtender) {
            Builder builder2 = builder;
            Context context = builder2.mContext;
            Notification notification = builder2.mNotification;
            CharSequence charSequence = builder2.mContentTitle;
            CharSequence charSequence2 = builder2.mContentText;
            CharSequence charSequence3 = builder2.mContentInfo;
            RemoteViews remoteViews = builder2.mTickerView;
            int i = builder2.mNumber;
            PendingIntent pendingIntent = builder2.mContentIntent;
            PendingIntent pendingIntent2 = builder2.mFullScreenIntent;
            Bitmap bitmap = builder2.mLargeIcon;
            int i2 = builder2.mProgressMax;
            int i3 = builder2.mProgress;
            boolean z = builder2.mProgressIndeterminate;
            boolean z2 = builder2.mShowWhen;
            boolean z3 = z;
            boolean z4 = builder2.mUseChronometer;
            int i4 = builder2.mPriority;
            CharSequence charSequence4 = builder2.mSubText;
            boolean z5 = builder2.mLocalOnly;
            String str = builder2.mCategory;
            ArrayList<String> arrayList = builder2.mPeople;
            Bundle bundle = builder2.mExtras;
            int i5 = builder2.mColor;
            int i6 = builder2.mVisibility;
            Notification notification2 = builder2.mPublicVersion;
            String str2 = builder2.mGroupKey;
            boolean z6 = builder2.mGroupSummary;
            String str3 = builder2.mSortKey;
            CharSequence[] charSequenceArr = builder2.mRemoteInputHistory;
            RemoteViews remoteViews2 = builder2.mContentView;
            RemoteViews remoteViews3 = builder2.mBigContentView;
            boolean z7 = z4;
            RemoteViews remoteViews4 = builder2.mHeadsUpContentView;
            android.support.v4.app.NotificationCompatApi24.Builder builder3 = r1;
            android.support.v4.app.NotificationCompatApi24.Builder builder4 = new android.support.v4.app.NotificationCompatApi24.Builder(context, notification, charSequence, charSequence2, charSequence3, remoteViews, i, pendingIntent, pendingIntent2, bitmap, i2, i3, z3, z2, z7, i4, charSequence4, z5, str, arrayList, bundle, i5, i6, notification2, str2, z6, str3, charSequenceArr, remoteViews2, remoteViews3, remoteViews4, builder.mGroupAlertBehavior);
            android.support.v4.app.NotificationCompatApi24.Builder builder5 = builder3;
            NotificationCompat.addActionsToBuilder(builder5, builder2.mActions);
            if (builder2.mStyle != null) {
                builder2.mStyle.apply(builder5);
            }
            Notification build = builderExtender.build(builder2, builder5);
            if (builder2.mStyle != null) {
                builder2.mStyle.addCompatExtras(NotificationCompat.getExtras(build));
            }
            return build;
        }

        public Action getAction(Notification notification, int i) {
            return (Action) NotificationCompatApi24.a(notification, i, Action.FACTORY, android.support.dontuse.app.RemoteInput.d);
        }

        public Action[] getActionsFromParcelableArrayList(ArrayList<Parcelable> arrayList) {
            return (Action[]) NotificationCompatApi24.a(arrayList, Action.FACTORY, android.support.dontuse.app.RemoteInput.d);
        }

        public ArrayList<Parcelable> getParcelableArrayListForActions(Action[] actionArr) {
            return NotificationCompatApi24.a((android.support.v4.app.NotificationCompatBase.Action[]) actionArr);
        }
    }

    @RequiresApi(26)
    static class NotificationCompatApi26Impl extends NotificationCompatApi24Impl {
        NotificationCompatApi26Impl() {
        }

        public Notification build(Builder builder, BuilderExtender builderExtender) {
            Builder builder2 = builder;
            Context context = builder2.mContext;
            Notification notification = builder2.mNotification;
            CharSequence charSequence = builder2.mContentTitle;
            CharSequence charSequence2 = builder2.mContentText;
            CharSequence charSequence3 = builder2.mContentInfo;
            RemoteViews remoteViews = builder2.mTickerView;
            int i = builder2.mNumber;
            PendingIntent pendingIntent = builder2.mContentIntent;
            PendingIntent pendingIntent2 = builder2.mFullScreenIntent;
            Bitmap bitmap = builder2.mLargeIcon;
            int i2 = builder2.mProgressMax;
            int i3 = builder2.mProgress;
            boolean z = builder2.mProgressIndeterminate;
            boolean z2 = builder2.mShowWhen;
            boolean z3 = z;
            boolean z4 = builder2.mUseChronometer;
            int i4 = builder2.mPriority;
            CharSequence charSequence4 = builder2.mSubText;
            boolean z5 = builder2.mLocalOnly;
            String str = builder2.mCategory;
            ArrayList<String> arrayList = builder2.mPeople;
            Bundle bundle = builder2.mExtras;
            int i5 = builder2.mColor;
            int i6 = builder2.mVisibility;
            Notification notification2 = builder2.mPublicVersion;
            String str2 = builder2.mGroupKey;
            boolean z6 = builder2.mGroupSummary;
            String str3 = builder2.mSortKey;
            CharSequence[] charSequenceArr = builder2.mRemoteInputHistory;
            RemoteViews remoteViews2 = builder2.mContentView;
            RemoteViews remoteViews3 = builder2.mBigContentView;
            RemoteViews remoteViews4 = builder2.mHeadsUpContentView;
            String str4 = builder2.mChannelId;
            int i7 = builder2.mBadgeIcon;
            int i8 = i3;
            String str5 = builder2.mShortcutId;
            long j = builder2.mTimeout;
            boolean z7 = builder2.mColorized;
            boolean z8 = builder2.mColorizedSet;
            int i9 = i8;
            boolean z9 = z4;
            boolean z10 = z7;
            boolean z11 = z3;
            android.support.v4.app.NotificationCompatApi26.Builder builder3 = r1;
            boolean z12 = z2;
            boolean z13 = z9;
            int i10 = i4;
            CharSequence charSequence5 = charSequence4;
            boolean z14 = z5;
            String str6 = str;
            ArrayList<String> arrayList2 = arrayList;
            Bundle bundle2 = bundle;
            int i11 = i5;
            int i12 = i6;
            Notification notification3 = notification2;
            String str7 = str2;
            boolean z15 = z6;
            String str8 = str3;
            CharSequence[] charSequenceArr2 = charSequenceArr;
            RemoteViews remoteViews5 = remoteViews2;
            RemoteViews remoteViews6 = remoteViews3;
            RemoteViews remoteViews7 = remoteViews4;
            String str9 = str4;
            int i13 = i7;
            String str10 = str5;
            android.support.v4.app.NotificationCompatApi26.Builder builder4 = new android.support.v4.app.NotificationCompatApi26.Builder(context, notification, charSequence, charSequence2, charSequence3, remoteViews, i, pendingIntent, pendingIntent2, bitmap, i2, i9, z11, z12, z13, i10, charSequence5, z14, str6, arrayList2, bundle2, i11, i12, notification3, str7, z15, str8, charSequenceArr2, remoteViews5, remoteViews6, remoteViews7, str9, i13, str10, j, z10, z8, builder.mGroupAlertBehavior);
            android.support.v4.app.NotificationCompatApi26.Builder builder5 = builder3;
            NotificationCompat.addActionsToBuilder(builder5, builder2.mActions);
            if (builder2.mStyle != null) {
                builder2.mStyle.apply(builder5);
            }
            Notification build = builderExtender.build(builder2, builder5);
            if (builder2.mStyle != null) {
                builder2.mStyle.addCompatExtras(NotificationCompat.getExtras(build));
            }
            return build;
        }
    }

    static class NotificationCompatBaseImpl implements NotificationCompatImpl {

        public static class BuilderBase implements NotificationBuilderWithBuilderAccessor {
            private android.app.Notification.Builder mBuilder;

            BuilderBase(Context context, Notification notification, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, RemoteViews remoteViews, int i, PendingIntent pendingIntent, PendingIntent pendingIntent2, Bitmap bitmap, int i2, int i3, boolean z) {
                PendingIntent pendingIntent3;
                Notification notification2 = notification;
                boolean z2 = false;
                android.app.Notification.Builder deleteIntent = new android.app.Notification.Builder(context).setWhen(notification2.when).setSmallIcon(notification2.icon, notification2.iconLevel).setContent(notification2.contentView).setTicker(notification2.tickerText, remoteViews).setSound(notification2.sound, notification2.audioStreamType).setVibrate(notification2.vibrate).setLights(notification2.ledARGB, notification2.ledOnMS, notification2.ledOffMS).setOngoing((notification2.flags & 2) != 0).setOnlyAlertOnce((notification2.flags & 8) != 0).setAutoCancel((notification2.flags & 16) != 0).setDefaults(notification2.defaults).setContentTitle(charSequence).setContentText(charSequence2).setContentInfo(charSequence3).setContentIntent(pendingIntent).setDeleteIntent(notification2.deleteIntent);
                if ((notification2.flags & 128) != 0) {
                    pendingIntent3 = pendingIntent2;
                    z2 = true;
                } else {
                    pendingIntent3 = pendingIntent2;
                }
                this.mBuilder = deleteIntent.setFullScreenIntent(pendingIntent3, z2).setLargeIcon(bitmap).setNumber(i).setProgress(i2, i3, z);
            }

            public android.app.Notification.Builder getBuilder() {
                return this.mBuilder;
            }

            public Notification build() {
                return this.mBuilder.getNotification();
            }
        }

        public Action getAction(Notification notification, int i) {
            return null;
        }

        public Action[] getActionsFromParcelableArrayList(ArrayList<Parcelable> arrayList) {
            return null;
        }

        public Bundle getBundleForUnreadConversation(UnreadConversation unreadConversation) {
            return null;
        }

        public ArrayList<Parcelable> getParcelableArrayListForActions(Action[] actionArr) {
            return null;
        }

        public UnreadConversation getUnreadConversationFromBundle(Bundle bundle, UnreadConversation.Factory factory, RemoteInput.Factory factory2) {
            return null;
        }

        NotificationCompatBaseImpl() {
        }

        public Notification build(Builder builder, BuilderExtender builderExtender) {
            Builder builder2 = builder;
            BuilderBase builderBase = new BuilderBase(builder2.mContext, builder2.mNotification, builder2.mContentTitle, builder2.mContentText, builder2.mContentInfo, builder2.mTickerView, builder2.mNumber, builder2.mContentIntent, builder2.mFullScreenIntent, builder2.mLargeIcon, builder2.mProgressMax, builder2.mProgress, builder2.mProgressIndeterminate);
            return builderExtender.build(builder2, builderBase);
        }
    }

    interface NotificationCompatImpl {
        Notification build(Builder builder, BuilderExtender builderExtender);

        Action getAction(Notification notification, int i);

        Action[] getActionsFromParcelableArrayList(ArrayList<Parcelable> arrayList);

        Bundle getBundleForUnreadConversation(UnreadConversation unreadConversation);

        ArrayList<Parcelable> getParcelableArrayListForActions(Action[] actionArr);

        UnreadConversation getUnreadConversationFromBundle(Bundle bundle, UnreadConversation.Factory factory, RemoteInput.Factory factory2);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface NotificationVisibility {
    }

    public static abstract class Style {
        CharSequence mBigContentTitle;
        @RestrictTo({Scope.LIBRARY_GROUP})
        protected Builder mBuilder;
        CharSequence mSummaryText;
        boolean mSummaryTextSet = false;

        private static float constrain(float f, float f2, float f3) {
            return f < f2 ? f2 : f > f3 ? f3 : f;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public void addCompatExtras(Bundle bundle) {
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public void apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public RemoteViews makeBigContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            return null;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public RemoteViews makeContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            return null;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public RemoteViews makeHeadsUpContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            return null;
        }

        /* access modifiers changed from: protected */
        @RestrictTo({Scope.LIBRARY_GROUP})
        public void restoreFromCompatExtras(Bundle bundle) {
        }

        public void setBuilder(Builder builder) {
            if (this.mBuilder != builder) {
                this.mBuilder = builder;
                if (this.mBuilder != null) {
                    this.mBuilder.setStyle(this);
                }
            }
        }

        public Notification build() {
            if (this.mBuilder != null) {
                return this.mBuilder.build();
            }
            return null;
        }

        /* JADX WARNING: Removed duplicated region for block: B:63:0x01a7  */
        /* JADX WARNING: Removed duplicated region for block: B:64:0x01b7  */
        /* JADX WARNING: Removed duplicated region for block: B:70:0x01c5  */
        /* JADX WARNING: Removed duplicated region for block: B:74:0x01e7  */
        /* JADX WARNING: Removed duplicated region for block: B:83:0x022f  */
        /* JADX WARNING: Removed duplicated region for block: B:84:0x0231  */
        /* JADX WARNING: Removed duplicated region for block: B:87:0x023a  */
        @android.support.annotation.RestrictTo({android.support.annotation.RestrictTo.Scope.LIBRARY_GROUP})
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public android.widget.RemoteViews applyStandardTemplate(boolean r13, int r14, boolean r15) {
            /*
                r12 = this;
                android.support.v4.app.NotificationCompat$Builder r0 = r12.mBuilder
                android.content.Context r0 = r0.mContext
                android.content.res.Resources r0 = r0.getResources()
                android.widget.RemoteViews r7 = new android.widget.RemoteViews
                android.support.v4.app.NotificationCompat$Builder r1 = r12.mBuilder
                android.content.Context r1 = r1.mContext
                java.lang.String r1 = r1.getPackageName()
                r7.<init>(r1, r14)
                android.support.v4.app.NotificationCompat$Builder r14 = r12.mBuilder
                int r14 = r14.getPriority()
                r1 = -1
                r8 = 1
                r9 = 0
                if (r14 >= r1) goto L_0x0022
                r14 = 1
                goto L_0x0023
            L_0x0022:
                r14 = 0
            L_0x0023:
                int r2 = android.os.Build.VERSION.SDK_INT
                r3 = 21
                r10 = 16
                if (r2 < r10) goto L_0x005a
                int r2 = android.os.Build.VERSION.SDK_INT
                if (r2 >= r3) goto L_0x005a
                if (r14 == 0) goto L_0x0046
                int r14 = android.support.compat.R.id.notification_background
                java.lang.String r2 = "setBackgroundResource"
                int r4 = android.support.compat.R.drawable.notification_bg_low
                r7.setInt(r14, r2, r4)
                int r14 = android.support.compat.R.id.icon
                java.lang.String r2 = "setBackgroundResource"
                int r4 = android.support.compat.R.drawable.notification_template_icon_low_bg
                r7.setInt(r14, r2, r4)
                goto L_0x005a
            L_0x0046:
                int r14 = android.support.compat.R.id.notification_background
                java.lang.String r2 = "setBackgroundResource"
                int r4 = android.support.compat.R.drawable.notification_bg
                r7.setInt(r14, r2, r4)
                int r14 = android.support.compat.R.id.icon
                java.lang.String r2 = "setBackgroundResource"
                int r4 = android.support.compat.R.drawable.notification_template_icon_bg
                r7.setInt(r14, r2, r4)
            L_0x005a:
                android.support.v4.app.NotificationCompat$Builder r14 = r12.mBuilder
                android.graphics.Bitmap r14 = r14.mLargeIcon
                r11 = 8
                if (r14 == 0) goto L_0x00c3
                int r14 = android.os.Build.VERSION.SDK_INT
                if (r14 < r10) goto L_0x0075
                int r14 = android.support.compat.R.id.icon
                r7.setViewVisibility(r14, r9)
                int r14 = android.support.compat.R.id.icon
                android.support.v4.app.NotificationCompat$Builder r2 = r12.mBuilder
                android.graphics.Bitmap r2 = r2.mLargeIcon
                r7.setImageViewBitmap(r14, r2)
                goto L_0x007a
            L_0x0075:
                int r14 = android.support.compat.R.id.icon
                r7.setViewVisibility(r14, r11)
            L_0x007a:
                if (r13 == 0) goto L_0x010e
                android.support.v4.app.NotificationCompat$Builder r13 = r12.mBuilder
                android.app.Notification r13 = r13.mNotification
                int r13 = r13.icon
                if (r13 == 0) goto L_0x010e
                int r13 = android.support.compat.R.dimen.notification_right_icon_size
                int r13 = r0.getDimensionPixelSize(r13)
                int r14 = android.support.compat.R.dimen.notification_small_icon_background_padding
                int r14 = r0.getDimensionPixelSize(r14)
                int r14 = r14 * 2
                int r14 = r13 - r14
                int r2 = android.os.Build.VERSION.SDK_INT
                if (r2 < r3) goto L_0x00ae
                android.support.v4.app.NotificationCompat$Builder r1 = r12.mBuilder
                android.app.Notification r1 = r1.mNotification
                int r1 = r1.icon
                android.support.v4.app.NotificationCompat$Builder r2 = r12.mBuilder
                int r2 = r2.getColor()
                android.graphics.Bitmap r13 = r12.createIconWithBackground(r1, r13, r14, r2)
                int r14 = android.support.compat.R.id.right_icon
                r7.setImageViewBitmap(r14, r13)
                goto L_0x00bd
            L_0x00ae:
                int r13 = android.support.compat.R.id.right_icon
                android.support.v4.app.NotificationCompat$Builder r14 = r12.mBuilder
                android.app.Notification r14 = r14.mNotification
                int r14 = r14.icon
                android.graphics.Bitmap r14 = r12.createColoredBitmap(r14, r1)
                r7.setImageViewBitmap(r13, r14)
            L_0x00bd:
                int r13 = android.support.compat.R.id.right_icon
                r7.setViewVisibility(r13, r9)
                goto L_0x010e
            L_0x00c3:
                if (r13 == 0) goto L_0x010e
                android.support.v4.app.NotificationCompat$Builder r13 = r12.mBuilder
                android.app.Notification r13 = r13.mNotification
                int r13 = r13.icon
                if (r13 == 0) goto L_0x010e
                int r13 = android.support.compat.R.id.icon
                r7.setViewVisibility(r13, r9)
                int r13 = android.os.Build.VERSION.SDK_INT
                if (r13 < r3) goto L_0x00ff
                int r13 = android.support.compat.R.dimen.notification_large_icon_width
                int r13 = r0.getDimensionPixelSize(r13)
                int r14 = android.support.compat.R.dimen.notification_big_circle_margin
                int r14 = r0.getDimensionPixelSize(r14)
                int r13 = r13 - r14
                int r14 = android.support.compat.R.dimen.notification_small_icon_size_as_large
                int r14 = r0.getDimensionPixelSize(r14)
                android.support.v4.app.NotificationCompat$Builder r1 = r12.mBuilder
                android.app.Notification r1 = r1.mNotification
                int r1 = r1.icon
                android.support.v4.app.NotificationCompat$Builder r2 = r12.mBuilder
                int r2 = r2.getColor()
                android.graphics.Bitmap r13 = r12.createIconWithBackground(r1, r13, r14, r2)
                int r14 = android.support.compat.R.id.icon
                r7.setImageViewBitmap(r14, r13)
                goto L_0x010e
            L_0x00ff:
                int r13 = android.support.compat.R.id.icon
                android.support.v4.app.NotificationCompat$Builder r14 = r12.mBuilder
                android.app.Notification r14 = r14.mNotification
                int r14 = r14.icon
                android.graphics.Bitmap r14 = r12.createColoredBitmap(r14, r1)
                r7.setImageViewBitmap(r13, r14)
            L_0x010e:
                android.support.v4.app.NotificationCompat$Builder r13 = r12.mBuilder
                java.lang.CharSequence r13 = r13.mContentTitle
                if (r13 == 0) goto L_0x011d
                int r13 = android.support.compat.R.id.title
                android.support.v4.app.NotificationCompat$Builder r14 = r12.mBuilder
                java.lang.CharSequence r14 = r14.mContentTitle
                r7.setTextViewText(r13, r14)
            L_0x011d:
                android.support.v4.app.NotificationCompat$Builder r13 = r12.mBuilder
                java.lang.CharSequence r13 = r13.mContentText
                if (r13 == 0) goto L_0x012e
                int r13 = android.support.compat.R.id.text
                android.support.v4.app.NotificationCompat$Builder r14 = r12.mBuilder
                java.lang.CharSequence r14 = r14.mContentText
                r7.setTextViewText(r13, r14)
                r13 = 1
                goto L_0x012f
            L_0x012e:
                r13 = 0
            L_0x012f:
                int r14 = android.os.Build.VERSION.SDK_INT
                if (r14 >= r3) goto L_0x013b
                android.support.v4.app.NotificationCompat$Builder r14 = r12.mBuilder
                android.graphics.Bitmap r14 = r14.mLargeIcon
                if (r14 == 0) goto L_0x013b
                r14 = 1
                goto L_0x013c
            L_0x013b:
                r14 = 0
            L_0x013c:
                android.support.v4.app.NotificationCompat$Builder r1 = r12.mBuilder
                java.lang.CharSequence r1 = r1.mContentInfo
                if (r1 == 0) goto L_0x0153
                int r13 = android.support.compat.R.id.info
                android.support.v4.app.NotificationCompat$Builder r14 = r12.mBuilder
                java.lang.CharSequence r14 = r14.mContentInfo
                r7.setTextViewText(r13, r14)
                int r13 = android.support.compat.R.id.info
                r7.setViewVisibility(r13, r9)
            L_0x0150:
                r13 = 1
                r14 = 1
                goto L_0x018e
            L_0x0153:
                android.support.v4.app.NotificationCompat$Builder r1 = r12.mBuilder
                int r1 = r1.mNumber
                if (r1 <= 0) goto L_0x0189
                int r13 = android.support.compat.R.integer.status_bar_notification_info_maxnum
                int r13 = r0.getInteger(r13)
                android.support.v4.app.NotificationCompat$Builder r14 = r12.mBuilder
                int r14 = r14.mNumber
                if (r14 <= r13) goto L_0x0171
                int r13 = android.support.compat.R.id.info
                int r14 = android.support.compat.R.string.status_bar_notification_info_overflow
                java.lang.String r14 = r0.getString(r14)
                r7.setTextViewText(r13, r14)
                goto L_0x0183
            L_0x0171:
                java.text.NumberFormat r13 = java.text.NumberFormat.getIntegerInstance()
                int r14 = android.support.compat.R.id.info
                android.support.v4.app.NotificationCompat$Builder r1 = r12.mBuilder
                int r1 = r1.mNumber
                long r1 = (long) r1
                java.lang.String r13 = r13.format(r1)
                r7.setTextViewText(r14, r13)
            L_0x0183:
                int r13 = android.support.compat.R.id.info
                r7.setViewVisibility(r13, r9)
                goto L_0x0150
            L_0x0189:
                int r1 = android.support.compat.R.id.info
                r7.setViewVisibility(r1, r11)
            L_0x018e:
                android.support.v4.app.NotificationCompat$Builder r1 = r12.mBuilder
                java.lang.CharSequence r1 = r1.mSubText
                if (r1 == 0) goto L_0x01bc
                int r1 = android.os.Build.VERSION.SDK_INT
                if (r1 < r10) goto L_0x01bc
                int r1 = android.support.compat.R.id.text
                android.support.v4.app.NotificationCompat$Builder r2 = r12.mBuilder
                java.lang.CharSequence r2 = r2.mSubText
                r7.setTextViewText(r1, r2)
                android.support.v4.app.NotificationCompat$Builder r1 = r12.mBuilder
                java.lang.CharSequence r1 = r1.mContentText
                if (r1 == 0) goto L_0x01b7
                int r1 = android.support.compat.R.id.text2
                android.support.v4.app.NotificationCompat$Builder r2 = r12.mBuilder
                java.lang.CharSequence r2 = r2.mContentText
                r7.setTextViewText(r1, r2)
                int r1 = android.support.compat.R.id.text2
                r7.setViewVisibility(r1, r9)
                r1 = 1
                goto L_0x01bd
            L_0x01b7:
                int r1 = android.support.compat.R.id.text2
                r7.setViewVisibility(r1, r11)
            L_0x01bc:
                r1 = 0
            L_0x01bd:
                if (r1 == 0) goto L_0x01db
                int r1 = android.os.Build.VERSION.SDK_INT
                if (r1 < r10) goto L_0x01db
                if (r15 == 0) goto L_0x01d1
                int r15 = android.support.compat.R.dimen.notification_subtext_size
                int r15 = r0.getDimensionPixelSize(r15)
                float r15 = (float) r15
                int r0 = android.support.compat.R.id.text
                r7.setTextViewTextSize(r0, r9, r15)
            L_0x01d1:
                int r2 = android.support.compat.R.id.line1
                r3 = 0
                r4 = 0
                r5 = 0
                r6 = 0
                r1 = r7
                r1.setViewPadding(r2, r3, r4, r5, r6)
            L_0x01db:
                android.support.v4.app.NotificationCompat$Builder r15 = r12.mBuilder
                long r0 = r15.getWhenIfShowing()
                r2 = 0
                int r15 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
                if (r15 == 0) goto L_0x022b
                android.support.v4.app.NotificationCompat$Builder r14 = r12.mBuilder
                boolean r14 = r14.mUseChronometer
                if (r14 == 0) goto L_0x0217
                int r14 = android.os.Build.VERSION.SDK_INT
                if (r14 < r10) goto L_0x0217
                int r14 = android.support.compat.R.id.chronometer
                r7.setViewVisibility(r14, r9)
                int r14 = android.support.compat.R.id.chronometer
                java.lang.String r15 = "setBase"
                android.support.v4.app.NotificationCompat$Builder r0 = r12.mBuilder
                long r0 = r0.getWhenIfShowing()
                long r2 = android.os.SystemClock.elapsedRealtime()
                long r4 = java.lang.System.currentTimeMillis()
                long r2 = r2 - r4
                long r0 = r0 + r2
                r7.setLong(r14, r15, r0)
                int r14 = android.support.compat.R.id.chronometer
                java.lang.String r15 = "setStarted"
                r7.setBoolean(r14, r15, r8)
                goto L_0x022a
            L_0x0217:
                int r14 = android.support.compat.R.id.time
                r7.setViewVisibility(r14, r9)
                int r14 = android.support.compat.R.id.time
                java.lang.String r15 = "setTime"
                android.support.v4.app.NotificationCompat$Builder r0 = r12.mBuilder
                long r0 = r0.getWhenIfShowing()
                r7.setLong(r14, r15, r0)
            L_0x022a:
                r14 = 1
            L_0x022b:
                int r15 = android.support.compat.R.id.right_side
                if (r14 == 0) goto L_0x0231
                r14 = 0
                goto L_0x0233
            L_0x0231:
                r14 = 8
            L_0x0233:
                r7.setViewVisibility(r15, r14)
                int r14 = android.support.compat.R.id.line3
                if (r13 == 0) goto L_0x023b
                r11 = 0
            L_0x023b:
                r7.setViewVisibility(r14, r11)
                return r7
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v4.app.NotificationCompat.Style.applyStandardTemplate(boolean, int, boolean):android.widget.RemoteViews");
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public Bitmap createColoredBitmap(int i, int i2) {
            return createColoredBitmap(i, i2, 0);
        }

        private Bitmap createColoredBitmap(int i, int i2, int i3) {
            Drawable drawable = this.mBuilder.mContext.getResources().getDrawable(i);
            int intrinsicWidth = i3 == 0 ? drawable.getIntrinsicWidth() : i3;
            if (i3 == 0) {
                i3 = drawable.getIntrinsicHeight();
            }
            Bitmap createBitmap = Bitmap.createBitmap(intrinsicWidth, i3, Config.ARGB_8888);
            drawable.setBounds(0, 0, intrinsicWidth, i3);
            if (i2 != 0) {
                drawable.mutate().setColorFilter(new PorterDuffColorFilter(i2, Mode.SRC_IN));
            }
            drawable.draw(new Canvas(createBitmap));
            return createBitmap;
        }

        private Bitmap createIconWithBackground(int i, int i2, int i3, int i4) {
            int i5 = R.drawable.notification_icon_background;
            if (i4 == 0) {
                i4 = 0;
            }
            Bitmap createColoredBitmap = createColoredBitmap(i5, i4, i2);
            Canvas canvas = new Canvas(createColoredBitmap);
            Drawable mutate = this.mBuilder.mContext.getResources().getDrawable(i).mutate();
            mutate.setFilterBitmap(true);
            int i6 = (i2 - i3) / 2;
            int i7 = i3 + i6;
            mutate.setBounds(i6, i6, i7, i7);
            mutate.setColorFilter(new PorterDuffColorFilter(-1, Mode.SRC_ATOP));
            mutate.draw(canvas);
            return createColoredBitmap;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public void buildIntoRemoteViews(RemoteViews remoteViews, RemoteViews remoteViews2) {
            hideNormalContent(remoteViews);
            remoteViews.removeAllViews(R.id.notification_main_column);
            remoteViews.addView(R.id.notification_main_column, remoteViews2.clone());
            remoteViews.setViewVisibility(R.id.notification_main_column, 0);
            if (VERSION.SDK_INT >= 21) {
                remoteViews.setViewPadding(R.id.notification_main_column_container, 0, calculateTopPadding(), 0, 0);
            }
        }

        private void hideNormalContent(RemoteViews remoteViews) {
            remoteViews.setViewVisibility(R.id.title, 8);
            remoteViews.setViewVisibility(R.id.text2, 8);
            remoteViews.setViewVisibility(R.id.text, 8);
        }

        private int calculateTopPadding() {
            Resources resources = this.mBuilder.mContext.getResources();
            int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.notification_top_pad);
            int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.notification_top_pad_large_text);
            float constrain = (constrain(resources.getConfiguration().fontScale, 1.0f, 1.3f) - 1.0f) / 0.29999995f;
            return Math.round(((1.0f - constrain) * ((float) dimensionPixelSize)) + (constrain * ((float) dimensionPixelSize2)));
        }
    }

    public static final class WearableExtender implements Extender {
        private static final int DEFAULT_CONTENT_ICON_GRAVITY = 8388613;
        private static final int DEFAULT_FLAGS = 1;
        private static final int DEFAULT_GRAVITY = 80;
        private static final String EXTRA_WEARABLE_EXTENSIONS = "android.wearable.EXTENSIONS";
        private static final int FLAG_BIG_PICTURE_AMBIENT = 32;
        private static final int FLAG_CONTENT_INTENT_AVAILABLE_OFFLINE = 1;
        private static final int FLAG_HINT_AVOID_BACKGROUND_CLIPPING = 16;
        private static final int FLAG_HINT_CONTENT_INTENT_LAUNCHES_ACTIVITY = 64;
        private static final int FLAG_HINT_HIDE_ICON = 2;
        private static final int FLAG_HINT_SHOW_BACKGROUND_ONLY = 4;
        private static final int FLAG_START_SCROLL_BOTTOM = 8;
        private static final String KEY_ACTIONS = "actions";
        private static final String KEY_BACKGROUND = "background";
        private static final String KEY_BRIDGE_TAG = "bridgeTag";
        private static final String KEY_CONTENT_ACTION_INDEX = "contentActionIndex";
        private static final String KEY_CONTENT_ICON = "contentIcon";
        private static final String KEY_CONTENT_ICON_GRAVITY = "contentIconGravity";
        private static final String KEY_CUSTOM_CONTENT_HEIGHT = "customContentHeight";
        private static final String KEY_CUSTOM_SIZE_PRESET = "customSizePreset";
        private static final String KEY_DISMISSAL_ID = "dismissalId";
        private static final String KEY_DISPLAY_INTENT = "displayIntent";
        private static final String KEY_FLAGS = "flags";
        private static final String KEY_GRAVITY = "gravity";
        private static final String KEY_HINT_SCREEN_TIMEOUT = "hintScreenTimeout";
        private static final String KEY_PAGES = "pages";
        public static final int SCREEN_TIMEOUT_LONG = -1;
        public static final int SCREEN_TIMEOUT_SHORT = 0;
        public static final int SIZE_DEFAULT = 0;
        public static final int SIZE_FULL_SCREEN = 5;
        public static final int SIZE_LARGE = 4;
        public static final int SIZE_MEDIUM = 3;
        public static final int SIZE_SMALL = 2;
        public static final int SIZE_XSMALL = 1;
        public static final int UNSET_ACTION_INDEX = -1;
        private ArrayList<Action> mActions = new ArrayList<>();
        private Bitmap mBackground;
        private String mBridgeTag;
        private int mContentActionIndex = -1;
        private int mContentIcon;
        private int mContentIconGravity = 8388613;
        private int mCustomContentHeight;
        private int mCustomSizePreset = 0;
        private String mDismissalId;
        private PendingIntent mDisplayIntent;
        private int mFlags = 1;
        private int mGravity = 80;
        private int mHintScreenTimeout;
        private ArrayList<Notification> mPages = new ArrayList<>();

        public WearableExtender() {
        }

        public WearableExtender(Notification notification) {
            Bundle extras = NotificationCompat.getExtras(notification);
            Bundle bundle = extras != null ? extras.getBundle(EXTRA_WEARABLE_EXTENSIONS) : null;
            if (bundle != null) {
                Action[] actionsFromParcelableArrayList = NotificationCompat.IMPL.getActionsFromParcelableArrayList(bundle.getParcelableArrayList(KEY_ACTIONS));
                if (actionsFromParcelableArrayList != null) {
                    Collections.addAll(this.mActions, actionsFromParcelableArrayList);
                }
                this.mFlags = bundle.getInt("flags", 1);
                this.mDisplayIntent = (PendingIntent) bundle.getParcelable(KEY_DISPLAY_INTENT);
                Notification[] notificationArrayFromBundle = NotificationCompat.getNotificationArrayFromBundle(bundle, KEY_PAGES);
                if (notificationArrayFromBundle != null) {
                    Collections.addAll(this.mPages, notificationArrayFromBundle);
                }
                this.mBackground = (Bitmap) bundle.getParcelable("background");
                this.mContentIcon = bundle.getInt(KEY_CONTENT_ICON);
                this.mContentIconGravity = bundle.getInt(KEY_CONTENT_ICON_GRAVITY, 8388613);
                this.mContentActionIndex = bundle.getInt(KEY_CONTENT_ACTION_INDEX, -1);
                this.mCustomSizePreset = bundle.getInt(KEY_CUSTOM_SIZE_PRESET, 0);
                this.mCustomContentHeight = bundle.getInt(KEY_CUSTOM_CONTENT_HEIGHT);
                this.mGravity = bundle.getInt(KEY_GRAVITY, 80);
                this.mHintScreenTimeout = bundle.getInt(KEY_HINT_SCREEN_TIMEOUT);
                this.mDismissalId = bundle.getString(KEY_DISMISSAL_ID);
                this.mBridgeTag = bundle.getString(KEY_BRIDGE_TAG);
            }
        }

        public final Builder extend(Builder builder) {
            Bundle bundle = new Bundle();
            if (!this.mActions.isEmpty()) {
                bundle.putParcelableArrayList(KEY_ACTIONS, NotificationCompat.IMPL.getParcelableArrayListForActions((Action[]) this.mActions.toArray(new Action[this.mActions.size()])));
            }
            if (this.mFlags != 1) {
                bundle.putInt("flags", this.mFlags);
            }
            if (this.mDisplayIntent != null) {
                bundle.putParcelable(KEY_DISPLAY_INTENT, this.mDisplayIntent);
            }
            if (!this.mPages.isEmpty()) {
                bundle.putParcelableArray(KEY_PAGES, (Parcelable[]) this.mPages.toArray(new Notification[this.mPages.size()]));
            }
            if (this.mBackground != null) {
                bundle.putParcelable("background", this.mBackground);
            }
            if (this.mContentIcon != 0) {
                bundle.putInt(KEY_CONTENT_ICON, this.mContentIcon);
            }
            if (this.mContentIconGravity != 8388613) {
                bundle.putInt(KEY_CONTENT_ICON_GRAVITY, this.mContentIconGravity);
            }
            if (this.mContentActionIndex != -1) {
                bundle.putInt(KEY_CONTENT_ACTION_INDEX, this.mContentActionIndex);
            }
            if (this.mCustomSizePreset != 0) {
                bundle.putInt(KEY_CUSTOM_SIZE_PRESET, this.mCustomSizePreset);
            }
            if (this.mCustomContentHeight != 0) {
                bundle.putInt(KEY_CUSTOM_CONTENT_HEIGHT, this.mCustomContentHeight);
            }
            if (this.mGravity != 80) {
                bundle.putInt(KEY_GRAVITY, this.mGravity);
            }
            if (this.mHintScreenTimeout != 0) {
                bundle.putInt(KEY_HINT_SCREEN_TIMEOUT, this.mHintScreenTimeout);
            }
            if (this.mDismissalId != null) {
                bundle.putString(KEY_DISMISSAL_ID, this.mDismissalId);
            }
            if (this.mBridgeTag != null) {
                bundle.putString(KEY_BRIDGE_TAG, this.mBridgeTag);
            }
            builder.getExtras().putBundle(EXTRA_WEARABLE_EXTENSIONS, bundle);
            return builder;
        }

        public final WearableExtender clone() {
            WearableExtender wearableExtender = new WearableExtender();
            wearableExtender.mActions = new ArrayList<>(this.mActions);
            wearableExtender.mFlags = this.mFlags;
            wearableExtender.mDisplayIntent = this.mDisplayIntent;
            wearableExtender.mPages = new ArrayList<>(this.mPages);
            wearableExtender.mBackground = this.mBackground;
            wearableExtender.mContentIcon = this.mContentIcon;
            wearableExtender.mContentIconGravity = this.mContentIconGravity;
            wearableExtender.mContentActionIndex = this.mContentActionIndex;
            wearableExtender.mCustomSizePreset = this.mCustomSizePreset;
            wearableExtender.mCustomContentHeight = this.mCustomContentHeight;
            wearableExtender.mGravity = this.mGravity;
            wearableExtender.mHintScreenTimeout = this.mHintScreenTimeout;
            wearableExtender.mDismissalId = this.mDismissalId;
            wearableExtender.mBridgeTag = this.mBridgeTag;
            return wearableExtender;
        }

        public final WearableExtender addAction(Action action) {
            this.mActions.add(action);
            return this;
        }

        public final WearableExtender addActions(List<Action> list) {
            this.mActions.addAll(list);
            return this;
        }

        public final WearableExtender clearActions() {
            this.mActions.clear();
            return this;
        }

        public final List<Action> getActions() {
            return this.mActions;
        }

        public final WearableExtender setDisplayIntent(PendingIntent pendingIntent) {
            this.mDisplayIntent = pendingIntent;
            return this;
        }

        public final PendingIntent getDisplayIntent() {
            return this.mDisplayIntent;
        }

        public final WearableExtender addPage(Notification notification) {
            this.mPages.add(notification);
            return this;
        }

        public final WearableExtender addPages(List<Notification> list) {
            this.mPages.addAll(list);
            return this;
        }

        public final WearableExtender clearPages() {
            this.mPages.clear();
            return this;
        }

        public final List<Notification> getPages() {
            return this.mPages;
        }

        public final WearableExtender setBackground(Bitmap bitmap) {
            this.mBackground = bitmap;
            return this;
        }

        public final Bitmap getBackground() {
            return this.mBackground;
        }

        public final WearableExtender setContentIcon(int i) {
            this.mContentIcon = i;
            return this;
        }

        public final int getContentIcon() {
            return this.mContentIcon;
        }

        public final WearableExtender setContentIconGravity(int i) {
            this.mContentIconGravity = i;
            return this;
        }

        public final int getContentIconGravity() {
            return this.mContentIconGravity;
        }

        public final WearableExtender setContentAction(int i) {
            this.mContentActionIndex = i;
            return this;
        }

        public final int getContentAction() {
            return this.mContentActionIndex;
        }

        public final WearableExtender setGravity(int i) {
            this.mGravity = i;
            return this;
        }

        public final int getGravity() {
            return this.mGravity;
        }

        public final WearableExtender setCustomSizePreset(int i) {
            this.mCustomSizePreset = i;
            return this;
        }

        public final int getCustomSizePreset() {
            return this.mCustomSizePreset;
        }

        public final WearableExtender setCustomContentHeight(int i) {
            this.mCustomContentHeight = i;
            return this;
        }

        public final int getCustomContentHeight() {
            return this.mCustomContentHeight;
        }

        public final WearableExtender setStartScrollBottom(boolean z) {
            setFlag(8, z);
            return this;
        }

        public final boolean getStartScrollBottom() {
            return (this.mFlags & 8) != 0;
        }

        public final WearableExtender setContentIntentAvailableOffline(boolean z) {
            setFlag(1, z);
            return this;
        }

        public final boolean getContentIntentAvailableOffline() {
            return (this.mFlags & 1) != 0;
        }

        public final WearableExtender setHintHideIcon(boolean z) {
            setFlag(2, z);
            return this;
        }

        public final boolean getHintHideIcon() {
            return (this.mFlags & 2) != 0;
        }

        public final WearableExtender setHintShowBackgroundOnly(boolean z) {
            setFlag(4, z);
            return this;
        }

        public final boolean getHintShowBackgroundOnly() {
            return (this.mFlags & 4) != 0;
        }

        public final WearableExtender setHintAvoidBackgroundClipping(boolean z) {
            setFlag(16, z);
            return this;
        }

        public final boolean getHintAvoidBackgroundClipping() {
            return (this.mFlags & 16) != 0;
        }

        public final WearableExtender setHintScreenTimeout(int i) {
            this.mHintScreenTimeout = i;
            return this;
        }

        public final int getHintScreenTimeout() {
            return this.mHintScreenTimeout;
        }

        public final WearableExtender setHintAmbientBigPicture(boolean z) {
            setFlag(32, z);
            return this;
        }

        public final boolean getHintAmbientBigPicture() {
            return (this.mFlags & 32) != 0;
        }

        public final WearableExtender setHintContentIntentLaunchesActivity(boolean z) {
            setFlag(64, z);
            return this;
        }

        public final boolean getHintContentIntentLaunchesActivity() {
            return (this.mFlags & 64) != 0;
        }

        public final WearableExtender setDismissalId(String str) {
            this.mDismissalId = str;
            return this;
        }

        public final String getDismissalId() {
            return this.mDismissalId;
        }

        public final WearableExtender setBridgeTag(String str) {
            this.mBridgeTag = str;
            return this;
        }

        public final String getBridgeTag() {
            return this.mBridgeTag;
        }

        private void setFlag(int i, boolean z) {
            if (z) {
                this.mFlags = i | this.mFlags;
                return;
            }
            this.mFlags = (~i) & this.mFlags;
        }
    }

    static void addActionsToBuilder(NotificationBuilderWithActions notificationBuilderWithActions, ArrayList<Action> arrayList) {
        Iterator<Action> it = arrayList.iterator();
        while (it.hasNext()) {
            notificationBuilderWithActions.addAction(it.next());
        }
    }

    static {
        if (VERSION.SDK_INT >= 26) {
            IMPL = new NotificationCompatApi26Impl();
        } else if (VERSION.SDK_INT >= 24) {
            IMPL = new NotificationCompatApi24Impl();
        } else if (VERSION.SDK_INT >= 21) {
            IMPL = new NotificationCompatApi21Impl();
        } else if (VERSION.SDK_INT >= 20) {
            IMPL = new NotificationCompatApi20Impl();
        } else if (VERSION.SDK_INT >= 19) {
            IMPL = new NotificationCompatApi19Impl();
        } else if (VERSION.SDK_INT >= 16) {
            IMPL = new NotificationCompatApi16Impl();
        } else {
            IMPL = new NotificationCompatBaseImpl();
        }
    }

    static Notification[] getNotificationArrayFromBundle(Bundle bundle, String str) {
        Parcelable[] parcelableArray = bundle.getParcelableArray(str);
        if ((parcelableArray instanceof Notification[]) || parcelableArray == null) {
            return (Notification[]) parcelableArray;
        }
        Notification[] notificationArr = new Notification[parcelableArray.length];
        for (int i = 0; i < parcelableArray.length; i++) {
            notificationArr[i] = (Notification) parcelableArray[i];
        }
        bundle.putParcelableArray(str, notificationArr);
        return notificationArr;
    }

    public static Bundle getExtras(Notification notification) {
        if (VERSION.SDK_INT >= 19) {
            return notification.extras;
        }
        if (VERSION.SDK_INT >= 16) {
            return NotificationCompatJellybean.a(notification);
        }
        return null;
    }

    public static int getActionCount(Notification notification) {
        if (VERSION.SDK_INT >= 19) {
            if (notification.actions != null) {
                return notification.actions.length;
            }
            return 0;
        } else if (VERSION.SDK_INT >= 16) {
            return NotificationCompatJellybean.b(notification);
        } else {
            return 0;
        }
    }

    public static Action getAction(Notification notification, int i) {
        return IMPL.getAction(notification, i);
    }

    public static String getCategory(Notification notification) {
        if (VERSION.SDK_INT >= 21) {
            return notification.category;
        }
        return null;
    }

    public static boolean getLocalOnly(Notification notification) {
        if (VERSION.SDK_INT >= 20) {
            if ((notification.flags & 256) != 0) {
                return true;
            }
            return false;
        } else if (VERSION.SDK_INT >= 19) {
            return notification.extras.getBoolean(NotificationCompatExtras.EXTRA_LOCAL_ONLY);
        } else {
            if (VERSION.SDK_INT >= 16) {
                return NotificationCompatJellybean.a(notification).getBoolean(NotificationCompatExtras.EXTRA_LOCAL_ONLY);
            }
            return false;
        }
    }

    public static String getGroup(Notification notification) {
        if (VERSION.SDK_INT >= 20) {
            return notification.getGroup();
        }
        if (VERSION.SDK_INT >= 19) {
            return notification.extras.getString(NotificationCompatExtras.EXTRA_GROUP_KEY);
        }
        if (VERSION.SDK_INT >= 16) {
            return NotificationCompatJellybean.a(notification).getString(NotificationCompatExtras.EXTRA_GROUP_KEY);
        }
        return null;
    }

    public static boolean isGroupSummary(Notification notification) {
        if (VERSION.SDK_INT >= 20) {
            if ((notification.flags & 512) != 0) {
                return true;
            }
            return false;
        } else if (VERSION.SDK_INT >= 19) {
            return notification.extras.getBoolean(NotificationCompatExtras.EXTRA_GROUP_SUMMARY);
        } else {
            if (VERSION.SDK_INT >= 16) {
                return NotificationCompatJellybean.a(notification).getBoolean(NotificationCompatExtras.EXTRA_GROUP_SUMMARY);
            }
            return false;
        }
    }

    public static String getSortKey(Notification notification) {
        if (VERSION.SDK_INT >= 20) {
            return notification.getSortKey();
        }
        if (VERSION.SDK_INT >= 19) {
            return notification.extras.getString(NotificationCompatExtras.EXTRA_SORT_KEY);
        }
        if (VERSION.SDK_INT >= 16) {
            return NotificationCompatJellybean.a(notification).getString(NotificationCompatExtras.EXTRA_SORT_KEY);
        }
        return null;
    }

    public static String getChannelId(Notification notification) {
        if (VERSION.SDK_INT >= 26) {
            return notification.getChannelId();
        }
        return null;
    }

    public static long getTimeoutAfter(Notification notification) {
        if (VERSION.SDK_INT >= 26) {
            return notification.getTimeoutAfter();
        }
        return 0;
    }

    public static int getBadgeIconType(Notification notification) {
        if (VERSION.SDK_INT >= 26) {
            return notification.getBadgeIconType();
        }
        return 0;
    }

    public static String getShortcutId(Notification notification) {
        if (VERSION.SDK_INT >= 26) {
            return notification.getShortcutId();
        }
        return null;
    }

    public static int getGroupAlertBehavior(Notification notification) {
        if (VERSION.SDK_INT >= 26) {
            return notification.getGroupAlertBehavior();
        }
        return 0;
    }
}
