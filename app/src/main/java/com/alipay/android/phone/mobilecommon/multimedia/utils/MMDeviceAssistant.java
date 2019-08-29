package com.alipay.android.phone.mobilecommon.multimedia.utils;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class MMDeviceAssistant {
    public static final int CODE_RET_REQ_FAIL = 1;
    public static final int CODE_RET_REQ_OK = 0;
    public static final int DEVICE_CAMERA = 2;
    public static final int DEVICE_MIC = 1;
    public static final int STATUS_BUSY = 1;
    public static final int STATUS_IDLE = 0;
    private static MMDeviceAssistant mAssistant = new MMDeviceAssistant();
    private Map<String, InUsingHolder> mCurrentInUsingDevices = new HashMap();
    private Map<String, OnDeviceReleaseListener> mReleaseListenerMap = new HashMap();
    private Map<String, WeakReference<OnDeviceReleaseListener>> mWeakReleaseListenerMap = new HashMap();

    private class InUsingHolder {
        OnDeviceRequestListener mListener;
        Request mRequest;

        public InUsingHolder(OnDeviceRequestListener listener, Request request) {
            this.mListener = listener;
            this.mRequest = request;
        }

        public String toString() {
            return "InUsingHolder{mListener=" + this.mListener + ", mRequest=" + this.mRequest + '}';
        }
    }

    public interface OnDeviceReleaseListener {
        boolean onRelease(Release release);
    }

    public interface OnDeviceRequestListener {
        int onRequestResult(Request request);
    }

    public static class Release {
        public String business;
        public int device;

        public Release(int device2, String business2) {
            this.device = device2;
            this.business = business2;
        }

        public String toString() {
            return "Release{device=" + this.device + ", business='" + this.business + '\'' + '}';
        }
    }

    public static class Request {
        public String business;
        public int device;

        public Request(int device2, String business2) {
            this.device = device2;
            this.business = business2;
        }

        public String toString() {
            return "Request{device=" + this.device + ", business='" + this.business + '\'' + '}';
        }
    }

    public static MMDeviceAssistant get() {
        return mAssistant;
    }

    public int queryDeviceStatus(int device) {
        int status = 0;
        synchronized (this) {
            Iterator<InUsingHolder> it = this.mCurrentInUsingDevices.values().iterator();
            while (true) {
                if (it.hasNext()) {
                    if ((it.next().mRequest.device & device) != 0) {
                        status = 1;
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        return status;
    }

    public int requestDevice(Request request) {
        return requestDevice(request, new OnDeviceRequestListener() {
            public int onRequestResult(Request request) {
                return 1;
            }
        });
    }

    public int requestDevice(Request request, OnDeviceRequestListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("OnDeviceRequestListener must be set!!");
        }
        int code = 0;
        synchronized (this) {
            Iterator<InUsingHolder> it = this.mCurrentInUsingDevices.values().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                InUsingHolder h = it.next();
                Request r = h.mRequest;
                if ((r.device & request.device) != 0) {
                    code = h.mListener.onRequestResult(request);
                    if (code == 0) {
                        this.mCurrentInUsingDevices.remove(r.business);
                    }
                }
            }
            if (code == 0) {
                this.mCurrentInUsingDevices.put(request.business, new InUsingHolder(listener, request));
            }
        }
        return code;
    }

    public void releaseDevice(Release release) {
        InUsingHolder h = this.mCurrentInUsingDevices.remove(release.business);
        if (h != null) {
            synchronized (this) {
                boolean flag = false;
                Iterator<Entry<String, OnDeviceReleaseListener>> it = this.mReleaseListenerMap.entrySet().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    Entry entry = it.next();
                    if (!((String) entry.getKey()).equals(h.mRequest.business) && ((OnDeviceReleaseListener) entry.getValue()).onRelease(release)) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    for (Entry entry2 : this.mWeakReleaseListenerMap.entrySet()) {
                        if (!((String) entry2.getKey()).equals(h.mRequest.business)) {
                            OnDeviceReleaseListener listener = (OnDeviceReleaseListener) ((WeakReference) entry2.getValue()).get();
                            if (listener != null && listener.onRelease(release)) {
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    public void registerDeviceReleaseListener(String business, OnDeviceReleaseListener listener, boolean weak) {
        synchronized (this) {
            if (weak) {
                this.mWeakReleaseListenerMap.put(business, new WeakReference(listener));
            } else {
                this.mReleaseListenerMap.put(business, listener);
            }
        }
    }

    public void unregisterDeviceReleaseListener(String business) {
        synchronized (this) {
            this.mWeakReleaseListenerMap.remove(business);
            this.mReleaseListenerMap.remove(business);
        }
    }
}
