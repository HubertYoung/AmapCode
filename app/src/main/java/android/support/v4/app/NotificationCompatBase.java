package android.support.v4.app;

import android.app.PendingIntent;
import android.os.Bundle;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.dontuse.app.RemoteInputCompatBase.RemoteInput;

@RestrictTo({Scope.LIBRARY_GROUP})
public class NotificationCompatBase {

    public static abstract class Action {

        public interface Factory {
            Action build(int i, CharSequence charSequence, PendingIntent pendingIntent, Bundle bundle, RemoteInput[] remoteInputArr, RemoteInput[] remoteInputArr2, boolean z);

            Action[] newArray(int i);
        }

        public abstract PendingIntent getActionIntent();

        public abstract boolean getAllowGeneratedReplies();

        public abstract RemoteInput[] getDataOnlyRemoteInputs();

        public abstract Bundle getExtras();

        public abstract int getIcon();

        public abstract RemoteInput[] getRemoteInputs();

        public abstract CharSequence getTitle();
    }

    public static abstract class UnreadConversation {

        public interface Factory {
            UnreadConversation build(String[] strArr, RemoteInput remoteInput, PendingIntent pendingIntent, PendingIntent pendingIntent2, String[] strArr2, long j);
        }

        /* access modifiers changed from: 0000 */
        public abstract long getLatestTimestamp();

        /* access modifiers changed from: 0000 */
        public abstract String[] getMessages();

        /* access modifiers changed from: 0000 */
        public abstract String getParticipant();

        /* access modifiers changed from: 0000 */
        public abstract String[] getParticipants();

        /* access modifiers changed from: 0000 */
        public abstract PendingIntent getReadPendingIntent();

        /* access modifiers changed from: 0000 */
        public abstract RemoteInput getRemoteInput();

        /* access modifiers changed from: 0000 */
        public abstract PendingIntent getReplyPendingIntent();
    }
}
