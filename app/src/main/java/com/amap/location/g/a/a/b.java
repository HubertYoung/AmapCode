package com.amap.location.g.a.a;

import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import java.util.List;

/* compiled from: PhoneStateDispatcher */
public class b {
    PhoneStateListener a;
    private Handler b;

    /* compiled from: PhoneStateDispatcher */
    static class a extends Handler {
        private PhoneStateListener a;

        a(PhoneStateListener phoneStateListener, Looper looper) {
            super(looper);
            this.a = phoneStateListener;
        }

        public void handleMessage(Message message) {
            int i = message.what;
            if (i != 4) {
                if (i != 8) {
                    if (i != 16) {
                        if (i != 32) {
                            if (i == 64) {
                                if (message.arg2 != -1) {
                                    this.a.onDataConnectionStateChanged(message.arg1, message.arg2);
                                }
                                this.a.onDataConnectionStateChanged(message.arg1);
                            } else if (i == 128) {
                                this.a.onDataActivity(message.arg1);
                            } else if (i != 256) {
                                if (i != 1024) {
                                    switch (i) {
                                        case 1:
                                            if (message.obj != null) {
                                                this.a.onServiceStateChanged((ServiceState) message.obj);
                                                return;
                                            }
                                            break;
                                        case 2:
                                            this.a.onSignalStrengthChanged(message.arg1);
                                            return;
                                    }
                                } else if (VERSION.SDK_INT >= 17 && message.obj != null) {
                                    this.a.onCellInfoChanged((List) message.obj);
                                }
                            } else if (message.obj != null) {
                                this.a.onSignalStrengthsChanged((SignalStrength) message.obj);
                            }
                        } else if (message.obj != null) {
                            this.a.onCallStateChanged(message.arg1, (String) message.obj);
                        }
                    } else if (message.obj != null) {
                        this.a.onCellLocationChanged((CellLocation) message.obj);
                    }
                } else if (message.obj != null) {
                    this.a.onCallForwardingIndicatorChanged(((Boolean) message.obj).booleanValue());
                }
            } else if (message.obj != null) {
                this.a.onMessageWaitingIndicatorChanged(((Boolean) message.obj).booleanValue());
            }
        }
    }

    public b(PhoneStateListener phoneStateListener, Looper looper) {
        looper = looper == null ? Looper.getMainLooper() : looper;
        this.a = phoneStateListener;
        this.b = new a(this.a, looper);
    }

    /* access modifiers changed from: 0000 */
    public void a(int i, Object obj, Object obj2) {
        Message obtainMessage = this.b.obtainMessage(i);
        if (!(i == 4 || i == 8 || i == 16)) {
            if (i != 32) {
                if (i != 64) {
                    if (i != 128) {
                        if (!(i == 256 || i == 1024)) {
                            switch (i) {
                                case 1:
                                    break;
                                case 2:
                                    break;
                            }
                        }
                    }
                    obtainMessage.arg1 = ((Integer) obj).intValue();
                } else {
                    obtainMessage.arg1 = ((Integer) obj).intValue();
                    if (obj2 != null) {
                        obtainMessage.arg2 = ((Integer) obj2).intValue();
                    } else {
                        obtainMessage.arg2 = -1;
                    }
                }
            } else if (obj2 != null) {
                obtainMessage.arg1 = ((Integer) obj).intValue();
                obtainMessage.obj = obj2;
            } else {
                return;
            }
            obtainMessage.sendToTarget();
        }
        obtainMessage.obj = obj;
        obtainMessage.sendToTarget();
    }
}
