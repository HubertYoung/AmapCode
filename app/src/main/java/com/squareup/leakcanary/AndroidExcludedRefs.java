package com.squareup.leakcanary;

import android.os.Build;
import android.os.Build.VERSION;
import com.alipay.mobile.beehive.capture.utils.AudioUtils;
import com.squareup.leakcanary.ExcludedRefs.Builder;
import com.squareup.leakcanary.internal.LeakCanaryInternals;
import java.util.EnumSet;
import java.util.Iterator;

public enum AndroidExcludedRefs {
    ;
    
    final boolean applies;

    /* access modifiers changed from: 0000 */
    public abstract void add(Builder builder);

    static {
        ACTIVITY_CLIENT_RECORD__NEXT_IDLE = new AndroidExcludedRefs("ACTIVITY_CLIENT_RECORD__NEXT_IDLE", 0, VERSION.SDK_INT >= 19 && VERSION.SDK_INT <= 21) {
            /* access modifiers changed from: 0000 */
            public final void add(Builder builder) {
                builder.instanceField("android.app.ActivityThread$ActivityClientRecord", "nextIdle");
            }
        };
        SPAN_CONTROLLER = new AndroidExcludedRefs("SPAN_CONTROLLER", 1, VERSION.SDK_INT <= 19) {
            /* access modifiers changed from: 0000 */
            public final void add(Builder builder) {
                builder.instanceField("android.widget.Editor$EasyEditSpanController", "this$0");
                builder.instanceField("android.widget.Editor$SpanController", "this$0");
            }
        };
        MEDIA_SESSION_LEGACY_HELPER__SINSTANCE = new AndroidExcludedRefs("MEDIA_SESSION_LEGACY_HELPER__SINSTANCE", 2, VERSION.SDK_INT == 21) {
            /* access modifiers changed from: 0000 */
            public final void add(Builder builder) {
                builder.staticField("android.media.session.MediaSessionLegacyHelper", "sInstance");
            }
        };
        TEXT_LINE__SCACHED = new AndroidExcludedRefs("TEXT_LINE__SCACHED", 3, VERSION.SDK_INT < 22) {
            /* access modifiers changed from: 0000 */
            public final void add(Builder builder) {
                builder.staticField("android.text.TextLine", "sCached");
            }
        };
        BLOCKING_QUEUE = new AndroidExcludedRefs("BLOCKING_QUEUE", 4, VERSION.SDK_INT < 21) {
            /* access modifiers changed from: 0000 */
            public final void add(Builder builder) {
                builder.instanceField("android.os.Message", "obj");
                builder.instanceField("android.os.Message", AudioUtils.CMDNEXT);
                builder.instanceField("android.os.Message", "target");
            }
        };
        INPUT_METHOD_MANAGER__SERVED_VIEW = new AndroidExcludedRefs("INPUT_METHOD_MANAGER__SERVED_VIEW", 5, VERSION.SDK_INT >= 16 && VERSION.SDK_INT <= 22) {
            /* access modifiers changed from: 0000 */
            public final void add(Builder builder) {
                builder.instanceField("android.view.inputmethod.InputMethodManager", "mNextServedView");
                builder.instanceField("android.view.inputmethod.InputMethodManager", "mServedView");
                builder.instanceField("android.view.inputmethod.InputMethodManager", "mServedInputConnection");
            }
        };
        INPUT_METHOD_MANAGER__ROOT_VIEW = new AndroidExcludedRefs("INPUT_METHOD_MANAGER__ROOT_VIEW", 6, VERSION.SDK_INT >= 15 && VERSION.SDK_INT <= 22) {
            /* access modifiers changed from: 0000 */
            public final void add(Builder builder) {
                builder.instanceField("android.view.inputmethod.InputMethodManager", "mCurRootView");
            }
        };
        LAYOUT_TRANSITION = new AndroidExcludedRefs("LAYOUT_TRANSITION", 7, VERSION.SDK_INT >= 14 && VERSION.SDK_INT <= 22) {
            /* access modifiers changed from: 0000 */
            public final void add(Builder builder) {
                builder.instanceField("android.animation.LayoutTransition$1", "val$parent");
            }
        };
        SPELL_CHECKER_SESSION = new AndroidExcludedRefs("SPELL_CHECKER_SESSION", 8, VERSION.SDK_INT >= 16 || VERSION.SDK_INT <= 22) {
            /* access modifiers changed from: 0000 */
            public final void add(Builder builder) {
                builder.instanceField("android.view.textservice.SpellCheckerSession$1", "this$0");
            }
        };
        ACTIVITY_CHOOSE_MODEL = new AndroidExcludedRefs("ACTIVITY_CHOOSE_MODEL", 9, VERSION.SDK_INT > 14 && VERSION.SDK_INT <= 22) {
            /* access modifiers changed from: 0000 */
            public final void add(Builder builder) {
                builder.staticField("android.support.v7.internal.widget.ActivityChooserModel", "mActivityChoserModelPolicy");
                builder.staticField("android.widget.ActivityChooserModel", "mActivityChoserModelPolicy");
            }
        };
        SPEECH_RECOGNIZER = new AndroidExcludedRefs("SPEECH_RECOGNIZER", 10, VERSION.SDK_INT < 21) {
            /* access modifiers changed from: 0000 */
            public final void add(Builder builder) {
                builder.instanceField("android.speech.SpeechRecognizer$InternalListener", "this$0");
            }
        };
        ACCOUNT_MANAGER = new AndroidExcludedRefs("ACCOUNT_MANAGER", 11, VERSION.SDK_INT > 5 && VERSION.SDK_INT <= 22) {
            /* access modifiers changed from: 0000 */
            public final void add(Builder builder) {
                builder.instanceField("android.accounts.AccountManager$AmsTask$Response", "this$1");
            }
        };
        DEVICE_POLICY_MANAGER__SETTINGS_OBSERVER = new AndroidExcludedRefs("DEVICE_POLICY_MANAGER__SETTINGS_OBSERVER", 12, LeakCanaryInternals.MOTOROLA.equals(Build.MANUFACTURER) && VERSION.SDK_INT == 19) {
            /* access modifiers changed from: 0000 */
            public final void add(Builder builder) {
                if (LeakCanaryInternals.MOTOROLA.equals(Build.MANUFACTURER) && VERSION.SDK_INT == 19) {
                    builder.instanceField("android.app.admin.DevicePolicyManager$SettingsObserver", "this$0");
                }
            }
        };
        SPEN_GESTURE_MANAGER = new AndroidExcludedRefs("SPEN_GESTURE_MANAGER", 13, "samsung".equals(Build.MANUFACTURER) && VERSION.SDK_INT == 19) {
            /* access modifiers changed from: 0000 */
            public final void add(Builder builder) {
                builder.staticField("com.samsung.android.smartclip.SpenGestureManager", "mContext");
            }
        };
        CLIPBOARD_UI_MANAGER__SINSTANCE = new AndroidExcludedRefs("CLIPBOARD_UI_MANAGER__SINSTANCE", 14, "samsung".equals(Build.MANUFACTURER) && VERSION.SDK_INT >= 19 && VERSION.SDK_INT <= 21) {
            /* access modifiers changed from: 0000 */
            public final void add(Builder builder) {
                builder.staticField("android.sec.clipboard.ClipboardUIManager", "sInstance");
            }
        };
        BUBBLE_POPUP_HELPER__SHELPER = new AndroidExcludedRefs("BUBBLE_POPUP_HELPER__SHELPER", 15, LeakCanaryInternals.LG.equals(Build.MANUFACTURER) && VERSION.SDK_INT >= 19 && VERSION.SDK_INT <= 21) {
            /* access modifiers changed from: 0000 */
            public final void add(Builder builder) {
                builder.staticField("android.widget.BubblePopupHelper", "sHelper");
            }
        };
        AW_RESOURCE__SRESOURCES = new AndroidExcludedRefs("AW_RESOURCE__SRESOURCES", 16, "samsung".equals(Build.MANUFACTURER) && VERSION.SDK_INT == 19) {
            /* access modifiers changed from: 0000 */
            public final void add(Builder builder) {
                builder.staticField("com.android.org.chromium.android_webview.AwResource", "sResources");
            }
        };
        MAPPER_CLIENT = new AndroidExcludedRefs("MAPPER_CLIENT", 17, LeakCanaryInternals.NVIDIA.equals(Build.MANUFACTURER) && VERSION.SDK_INT == 19) {
            /* access modifiers changed from: 0000 */
            public final void add(Builder builder) {
                builder.instanceField("com.nvidia.ControllerMapper.MapperClient$ServiceClient", "this$0");
            }
        };
        TEXT_VIEW__MLAST_HOVERED_VIEW = new AndroidExcludedRefs("TEXT_VIEW__MLAST_HOVERED_VIEW", 18, "samsung".equals(Build.MANUFACTURER) && VERSION.SDK_INT == 19) {
            /* access modifiers changed from: 0000 */
            public final void add(Builder builder) {
                builder.staticField("android.widget.TextView", "mLastHoveredView");
            }
        };
        PERSONA_MANAGER = new AndroidExcludedRefs("PERSONA_MANAGER", 19, "samsung".equals(Build.MANUFACTURER) && VERSION.SDK_INT == 19) {
            /* access modifiers changed from: 0000 */
            public final void add(Builder builder) {
                builder.instanceField("android.os.PersonaManager", "mContext");
            }
        };
        RESOURCES__MCONTEXT = new AndroidExcludedRefs("RESOURCES__MCONTEXT", 20, "samsung".equals(Build.MANUFACTURER) && VERSION.SDK_INT == 19) {
            /* access modifiers changed from: 0000 */
            public final void add(Builder builder) {
                builder.instanceField("android.content.res.Resources", "mContext");
            }
        };
        VIEW_CONFIGURATION__MCONTEXT = new AndroidExcludedRefs("VIEW_CONFIGURATION__MCONTEXT", 21, "samsung".equals(Build.MANUFACTURER) && VERSION.SDK_INT == 19) {
            /* access modifiers changed from: 0000 */
            public final void add(Builder builder) {
                builder.instanceField("android.view.ViewConfiguration", "mContext");
            }
        };
        AUDIO_MANAGER__MCONTEXT_STATIC = new AndroidExcludedRefs("AUDIO_MANAGER__MCONTEXT_STATIC", 22, "samsung".equals(Build.MANUFACTURER) && VERSION.SDK_INT == 19) {
            /* access modifiers changed from: 0000 */
            public final void add(Builder builder) {
                builder.staticField("android.media.AudioManager", "mContext_static");
            }
        };
        FINALIZER_WATCHDOG_DAEMON = new AndroidExcludedRefs("FINALIZER_WATCHDOG_DAEMON", 23) {
            /* access modifiers changed from: 0000 */
            public final void add(Builder builder) {
                builder.thread("FinalizerWatchdogDaemon");
            }
        };
        MAIN = new AndroidExcludedRefs("MAIN", 24) {
            /* access modifiers changed from: 0000 */
            public final void add(Builder builder) {
                builder.thread("main");
            }
        };
        LEAK_CANARY_THREAD = new AndroidExcludedRefs("LEAK_CANARY_THREAD", 25) {
            /* access modifiers changed from: 0000 */
            public final void add(Builder builder) {
                builder.thread("LeakCanary-Heap-Dump");
            }
        };
        EVENT_RECEIVER__MMESSAGE_QUEUE = new AndroidExcludedRefs("EVENT_RECEIVER__MMESSAGE_QUEUE", 26) {
            /* access modifiers changed from: 0000 */
            public final void add(Builder builder) {
                builder.instanceField("android.view.Choreographer.FrameDisplayEventReceiver", "mMessageQueue");
            }
        };
        $VALUES = new AndroidExcludedRefs[]{ACTIVITY_CLIENT_RECORD__NEXT_IDLE, SPAN_CONTROLLER, MEDIA_SESSION_LEGACY_HELPER__SINSTANCE, TEXT_LINE__SCACHED, BLOCKING_QUEUE, INPUT_METHOD_MANAGER__SERVED_VIEW, INPUT_METHOD_MANAGER__ROOT_VIEW, LAYOUT_TRANSITION, SPELL_CHECKER_SESSION, ACTIVITY_CHOOSE_MODEL, SPEECH_RECOGNIZER, ACCOUNT_MANAGER, DEVICE_POLICY_MANAGER__SETTINGS_OBSERVER, SPEN_GESTURE_MANAGER, CLIPBOARD_UI_MANAGER__SINSTANCE, BUBBLE_POPUP_HELPER__SHELPER, AW_RESOURCE__SRESOURCES, MAPPER_CLIENT, TEXT_VIEW__MLAST_HOVERED_VIEW, PERSONA_MANAGER, RESOURCES__MCONTEXT, VIEW_CONFIGURATION__MCONTEXT, AUDIO_MANAGER__MCONTEXT_STATIC, FINALIZER_WATCHDOG_DAEMON, MAIN, LEAK_CANARY_THREAD, EVENT_RECEIVER__MMESSAGE_QUEUE};
    }

    public static Builder createAndroidDefaults() {
        return createBuilder(EnumSet.of(FINALIZER_WATCHDOG_DAEMON, MAIN, LEAK_CANARY_THREAD));
    }

    public static Builder createAppDefaults() {
        return createBuilder(EnumSet.allOf(AndroidExcludedRefs.class));
    }

    public static Builder createBuilder(EnumSet<AndroidExcludedRefs> enumSet) {
        Builder builder = new Builder();
        Iterator it = enumSet.iterator();
        while (it.hasNext()) {
            AndroidExcludedRefs androidExcludedRefs = (AndroidExcludedRefs) it.next();
            if (androidExcludedRefs.applies) {
                androidExcludedRefs.add(builder);
            }
        }
        return builder;
    }

    private AndroidExcludedRefs(boolean z) {
        this.applies = z;
    }
}
