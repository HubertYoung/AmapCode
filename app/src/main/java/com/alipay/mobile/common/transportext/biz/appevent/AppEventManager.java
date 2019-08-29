package com.alipay.mobile.common.transportext.biz.appevent;

import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transportext.biz.util.LogUtilAmnet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AppEventManager {
    private static final String LOGTAG = "amnet_AppEventManager";
    private static volatile EventInterface eventlistener = new EventInterface() {
        public final void onSeceenOnEvent() {
            if (check()) {
                for (EventInterface eventInterface : (EventInterface[]) AppEventManager.listeners.toArray(new EventInterface[AppEventManager.listeners.size()])) {
                    try {
                        eventInterface.onSeceenOnEvent();
                    } catch (Exception e) {
                        LogCatUtil.warn((String) AppEventManager.LOGTAG, (Throwable) e);
                    }
                }
            }
        }

        public final void onSeceenOffEvent() {
            if (check()) {
                for (EventInterface eventInterface : (EventInterface[]) AppEventManager.listeners.toArray(new EventInterface[AppEventManager.listeners.size()])) {
                    try {
                        eventInterface.onSeceenOffEvent();
                    } catch (Exception e) {
                        LogCatUtil.warn((String) AppEventManager.LOGTAG, (Throwable) e);
                    }
                }
            }
        }

        public final void onAppResumeEvent() {
            if (check()) {
                for (EventInterface eventInterface : (EventInterface[]) AppEventManager.listeners.toArray(new EventInterface[AppEventManager.listeners.size()])) {
                    try {
                        eventInterface.onAppResumeEvent();
                    } catch (Exception e) {
                        LogCatUtil.warn((String) AppEventManager.LOGTAG, (Throwable) e);
                    }
                }
            }
        }

        public final void onSyncInitChanged(Map<String, String> initMap) {
            if (check()) {
                for (EventInterface eventInterface : (EventInterface[]) AppEventManager.listeners.toArray(new EventInterface[AppEventManager.listeners.size()])) {
                    try {
                        eventInterface.onSyncInitChanged(initMap);
                    } catch (Exception e) {
                        LogCatUtil.warn((String) AppEventManager.LOGTAG, (Throwable) e);
                    }
                }
            }
        }

        public final void onAppLeaveEvent() {
            if (check()) {
                for (EventInterface eventInterface : (EventInterface[]) AppEventManager.listeners.toArray(new EventInterface[AppEventManager.listeners.size()])) {
                    try {
                        eventInterface.onAppLeaveEvent();
                    } catch (Exception e) {
                        LogCatUtil.warn((String) AppEventManager.LOGTAG, (Throwable) e);
                    }
                }
            }
        }

        private boolean check() {
            if (AppEventManager.listeners.isEmpty()) {
                return false;
            }
            return true;
        }
    };
    private static volatile boolean inited = false;
    /* access modifiers changed from: private */
    public static final List<EventInterface> listeners = new ArrayList(2);

    public interface EventInterface {
        void onAppLeaveEvent();

        void onAppResumeEvent();

        void onSeceenOffEvent();

        void onSeceenOnEvent();

        void onSyncInitChanged(Map<String, String> map);
    }

    public static void register(EventInterface eventlistener2) {
        listeners.add(eventlistener2);
        if (!inited) {
            init();
        }
    }

    public static void unregister(EventInterface eventlistener2) {
        listeners.remove(eventlistener2);
    }

    public static void init() {
        if (!inited) {
            inited = true;
            LogUtilAmnet.i(LOGTAG, "init: [ AppEventManager ] ");
            AmnetScreenOnOffReceiver.start();
        }
    }

    public static EventInterface getListener() {
        return eventlistener;
    }
}
