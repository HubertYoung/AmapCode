package com.autonavi.minimap.ajx3;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Binder;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.log.LogManager;
import com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleLifeCycle;
import com.autonavi.minimap.ajx3.util.ToastUtils;
import com.autonavi.minimap.ajx3.views.AmapAjxView;
import com.autonavi.minimap.auidebugger.boommenu.BoomMenuButton;
import com.autonavi.minimap.auidebugger.boommenu.BoomMenuButton.b;
import com.autonavi.minimap.auidebugger.boommenu.BoomMenuButton.d;
import com.autonavi.minimap.auidebugger.boommenu.BoomMenuButton.e;
import com.autonavi.minimap.auidebugger.boommenu.Types.BoomType;
import com.autonavi.minimap.auidebugger.boommenu.Types.ButtonType;
import com.autonavi.minimap.auidebugger.boommenu.Types.PlaceType;
import com.autonavi.minimap.auidebugger.qrcode.DownloadPage;
import com.autonavi.minimap.auidebugger.qrcode.QRCodePage;
import com.autonavi.minimap.auidebugger.qrcode.ScanCodePage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

public class Ajx3DebugService extends Service {
    public static final String ACTION_CHANGE_BASE_JS = "com.autonavi.minimap.action.CHANGE_BASE_JS";
    public static final String ACTION_CREATE_LIFE_CYCLE_VIEW = "com.autonavi.minimap.action.CREATE_LIFE_CYCLE_VIEW";
    private static final String BACK = "返回";
    public static final String CLOSE_DEBUG = "关闭调试";
    public static final String CLOSE_DEBUG_SOCKET = "鹰眼日志已开";
    public static final String IN_APK = "切换至ajx";
    public static final String KEY_TEXT = "text";
    public static final String KEY_URL = "url";
    public static final String MOCK_SERVER_JS = "phoneServer.js";
    public static final String OPEN_DEBUG_SOCKET = "鹰眼日志已关";
    public static final String OUT_APK = "切换至js";
    public static boolean SCAN = true;
    public static final int SCAN_REQUEST_CODE = 1234;
    private static final String STATE_CLOSE = "close";
    private static final String STATE_ERROR = "error";
    private static final String STATE_MESSAGE = "message";
    private static final String STATE_OPEN = "open";
    private static final String TAG = "Ajx3DebugService";
    private BaseJsChangeListener baseJsChangeListener;
    public BoomMenuButton boomMenuButton;
    d boomMenuStateChangeListener = new d() {
        public void stateChange(boolean z) {
            if (!z || TextUtils.isEmpty(Ajx3DebugService.this.mJsPathView.getText())) {
                Ajx3DebugService.this.mJsPathView.setVisibility(8);
            } else {
                Ajx3DebugService.this.mJsPathView.setVisibility(0);
            }
        }
    };
    private boolean init = false;
    /* access modifiers changed from: private */
    public float initWindowX;
    /* access modifiers changed from: private */
    public float initWindowY;
    /* access modifiers changed from: private */
    public float initX;
    /* access modifiers changed from: private */
    public float initY;
    private DebugMenuServiceBinder mBinder = new DebugMenuServiceBinder();
    private DebugReceiver mDebugReceiver;
    RelativeLayout mFloatLayout;
    /* access modifiers changed from: private */
    public Handler mHandler = new Handler();
    public View mJsPathDotView;
    public TextView mJsPathView;
    /* access modifiers changed from: private */
    public e mMenuClickListener;
    private MockReceiver mMockReceiver;
    private AmapAjxView mMockView;
    /* access modifiers changed from: private */
    public HashMap<String, MyWebSocketClient> mSocketClients = new HashMap<>();
    /* access modifiers changed from: private */
    public HashMap<Integer, String> mSocketClientsAddres = new HashMap<>();
    private emo mUiPerformance;
    WindowManager mWindowManager;
    /* access modifiers changed from: private */
    public Callback scanCallback = new Callback() {
        public boolean handleMessage(Message message) {
            if (message.what != 200) {
                ToastUtils.showToast("[Devtools] GET请求失败 请重试", 1);
            } else if (!LogManager.logOpen && Ajx3DebugService.this.mMenuClickListener != null) {
                Ajx3DebugService.this.mMenuClickListener.onClick(7);
            }
            return true;
        }
    };
    e subButtonClickListener = new e() {
        public void onClick(int i) {
            bid pageContext = AMapPageUtil.getPageContext();
            if (pageContext instanceof AbstractBasePage) {
                final AbstractBasePage abstractBasePage = (AbstractBasePage) pageContext;
                if (i == 0) {
                    try {
                        ToastHelper.showToast("AJX AAR为Release，无法开启Analyzer");
                    } catch (Exception unused) {
                        ToastHelper.showToast("此安装包不支持Ajx-Analyzer");
                    }
                } else if (i == 1) {
                    if (abstractBasePage instanceof Ajx3Page) {
                        String ajx3Url = ((Ajx3Page) abstractBasePage).getAjx3Url();
                        PageBundle pageBundle = new PageBundle();
                        pageBundle.putString("url", ajx3Url);
                        abstractBasePage.finish();
                        if (ajx3Url.contains(Ajx3DebugService.MOCK_SERVER_JS)) {
                            Ajx3DebugService.this.createLifeCycleAjxView(ajx3Url);
                            return;
                        }
                        abstractBasePage.startPage(Ajx3Page.class, pageBundle);
                    } else {
                        String str = "file://sdcard/ajx3/index.js";
                        File file = new File("sdcard/ajx3/config.ini");
                        if (file.exists()) {
                            try {
                                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                                while (true) {
                                    String readLine = bufferedReader.readLine();
                                    if (readLine == null) {
                                        break;
                                    } else if (readLine.startsWith("path:")) {
                                        str = readLine.substring(5);
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        ToastHelper.showToast("Native测试接口：加载".concat(String.valueOf(str)));
                        PageBundle pageBundle2 = new PageBundle();
                        pageBundle2.putString("url", str);
                        abstractBasePage.startPage(Ajx3Page.class, pageBundle2);
                    }
                } else if (i == 2) {
                    Ajx3DebugService.this.handleActionRefresh();
                } else if (i == 3) {
                    Class<?> topPageClass = AMapPageUtil.getTopPageClass();
                    if (topPageClass != ScanCodePage.class && topPageClass != QRCodePage.class && topPageClass != DownloadPage.class) {
                        if (topPageClass == Ajx3Page.class) {
                            abstractBasePage.finish();
                        }
                        kj.b(abstractBasePage.getActivity(), new String[]{"android.permission.CAMERA"}, new b() {
                            public void run() {
                                PageBundle pageBundle = new PageBundle();
                                pageBundle.putObject("callback", Ajx3DebugService.this.scanCallback);
                                abstractBasePage.startPage(Ajx3DebugService.SCAN ? ScanCodePage.class : QRCodePage.class, pageBundle);
                            }
                        });
                    } else {
                        return;
                    }
                } else if (i == 4) {
                    Ajx3DebugService.this.changeBaseJs();
                } else if (i == 5) {
                    ArrayList<akc> pagesStacks = AMapPageUtil.getPagesStacks();
                    if (pagesStacks != null && pagesStacks.size() > 1) {
                        AMapPageUtil.getPageContext().finish();
                    }
                }
                if (Ajx3DebugService.this.mMenuClickListener != null) {
                    Ajx3DebugService.this.mMenuClickListener.onClick(i);
                }
            }
        }
    };
    public TextView text;
    LayoutParams wmParams;

    public class DebugMenuServiceBinder extends Binder {
        public DebugMenuServiceBinder() {
        }

        public Ajx3DebugService getService() {
            return Ajx3DebugService.this;
        }
    }

    class DebugReceiver extends BroadcastReceiver {
        private DebugReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if (intent != null && Ajx3DebugService.ACTION_CHANGE_BASE_JS.equals(intent.getAction())) {
                changeBaseJs(intent.getStringExtra("text"));
            }
        }

        private void changeBaseJs(String str) {
            if (str != null && !TextUtils.equals(Ajx3DebugService.this.text.getText(), str)) {
                Ajx3DebugService.this.changeBaseJs();
            }
        }
    }

    class MockReceiver extends BroadcastReceiver {
        private MockReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if (intent != null && ((intent == null || intent.getAction() != null) && intent.getAction().equals(Ajx3DebugService.ACTION_CREATE_LIFE_CYCLE_VIEW))) {
                String stringExtra = intent.getStringExtra("url");
                if (stringExtra != null) {
                    Ajx3DebugService.this.createLifeCycleAjxView(stringExtra);
                }
            }
        }
    }

    class MyWebSocketClient extends WebSocketClient {
        public JsFunctionCallback mJsCallback;
        public String mSocketId = null;
        public String mSocketUrl = null;

        public MyWebSocketClient(String str, String str2, JsFunctionCallback jsFunctionCallback) {
            super(URI.create(str2));
            this.mSocketId = str;
            this.mSocketUrl = str2;
            this.mJsCallback = jsFunctionCallback;
        }

        public void onOpen(ServerHandshake serverHandshake) {
            if (this.mJsCallback != null) {
                this.mJsCallback.callback(Ajx3DebugService.STATE_OPEN, null);
            }
            Ajx3DebugService.this.mHandler.post(new Runnable() {
                public void run() {
                    ToastHelper.showToast("Websocket connect!");
                }
            });
        }

        public void onMessage(String str) {
            if (this.mJsCallback != null) {
                this.mJsCallback.callback("message", str);
            }
        }

        public void onClose(int i, String str, boolean z) {
            if (this.mJsCallback != null) {
                this.mJsCallback.callback("close", null);
            }
            String str2 = (String) Ajx3DebugService.this.mSocketClientsAddres.remove(Integer.valueOf(hashCode()));
            if (str2 != null) {
                Ajx3DebugService.this.mSocketClients.remove(str2);
            }
            Ajx3DebugService.this.mHandler.post(new Runnable() {
                public void run() {
                    ToastHelper.showToast("Websocket closed!");
                }
            });
        }

        public void onError(Exception exc) {
            if (this.mJsCallback != null) {
                this.mJsCallback.callback("error", exc.getMessage());
            }
            Ajx3DebugService.this.mHandler.post(new Runnable() {
                public void run() {
                    ToastHelper.showToast("Web Socket error!");
                }
            });
        }
    }

    public void onCreate() {
        super.onCreate();
        createFloatView();
        registerMockReceiver();
        registerDebugReceiver();
    }

    public IBinder onBind(Intent intent) {
        return this.mBinder;
    }

    private void registerMockReceiver() {
        IntentFilter intentFilter = new IntentFilter(ACTION_CREATE_LIFE_CYCLE_VIEW);
        this.mMockReceiver = new MockReceiver();
        getApplication().registerReceiver(this.mMockReceiver, intentFilter);
    }

    private void registerDebugReceiver() {
        IntentFilter intentFilter = new IntentFilter(ACTION_CHANGE_BASE_JS);
        this.mDebugReceiver = new DebugReceiver();
        getApplication().registerReceiver(this.mDebugReceiver, intentFilter);
    }

    private void unregisterMockReceiver() {
        if (this.mMockReceiver != null) {
            getApplication().unregisterReceiver(this.mMockReceiver);
        }
    }

    private void unregisterDebugReceiver() {
        if (this.mDebugReceiver != null) {
            getApplication().unregisterReceiver(this.mDebugReceiver);
        }
    }

    public void refreshJsPath(String str) {
        if (this.mJsPathView != null && this.boomMenuButton != null) {
            this.mJsPathView.setText(str);
            if (TextUtils.isEmpty(str)) {
                this.mJsPathDotView.setVisibility(8);
            } else {
                this.mJsPathDotView.setVisibility(0);
            }
            if (this.boomMenuButton.isClosed()) {
                this.mJsPathView.setVisibility(8);
            }
        }
    }

    /* access modifiers changed from: private */
    public void createLifeCycleAjxView(String str) {
        if (this.mMockView != null) {
            destroyLifeCycleAjxView();
        }
        LayoutParams layoutParams = new LayoutParams();
        layoutParams.type = VERSION.SDK_INT >= 26 ? 2038 : 2002;
        layoutParams.format = 1;
        layoutParams.flags = 8;
        layoutParams.gravity = 8388659;
        layoutParams.x = 0;
        cnu.a();
        layoutParams.y = (int) cnu.a(120.0f);
        layoutParams.width = 1;
        layoutParams.height = 1;
        this.mMockView = new AmapAjxView(AMapAppGlobal.getTopActivity());
        this.mMockView.setSendLifeCycle(true);
        this.mWindowManager.addView(this.mMockView, layoutParams);
        this.mMockView.load(str, null, "debugger");
    }

    private void destroyLifeCycleAjxView() {
        ((AjxModuleLifeCycle) Ajx.getInstance().getModuleIns(this.mMockView.getAjxContext(), "ajx.lifecycle")).unregisterLifeCycle();
        this.mMockView.setSendLifeCycle(false);
        this.mMockView.destroy();
    }

    private void createFloatView() {
        this.wmParams = new LayoutParams();
        this.mWindowManager = (WindowManager) getApplication().getSystemService(TemplateTinyApp.WINDOW_KEY);
        this.wmParams.type = VERSION.SDK_INT >= 26 ? 2038 : 2002;
        this.wmParams.format = 1;
        this.wmParams.flags = 8;
        this.wmParams.gravity = 8388659;
        this.wmParams.x = 0;
        LayoutParams layoutParams = this.wmParams;
        cnu.a();
        layoutParams.y = (int) cnu.a(120.0f);
        this.wmParams.width = -2;
        this.wmParams.height = -2;
        this.mFloatLayout = (RelativeLayout) LayoutInflater.from(getApplication()).inflate(R.layout.ajx3debug_float_layout, new FrameLayout(getApplicationContext()), false);
        this.boomMenuButton = (BoomMenuButton) this.mFloatLayout.findViewById(R.id.boom);
        this.text = (TextView) this.mFloatLayout.findViewById(R.id.text);
        this.mJsPathView = (TextView) this.mFloatLayout.findViewById(R.id.ajx_path);
        this.mJsPathDotView = this.mFloatLayout.findViewById(R.id.ajx_path_dot);
        this.mWindowManager.addView(this.mFloatLayout, this.wmParams);
        this.mJsPathDotView.setVisibility(8);
        this.mFloatLayout.measure(MeasureSpec.makeMeasureSpec(0, 0), MeasureSpec.makeMeasureSpec(0, 0));
        this.boomMenuButton.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case 0:
                        Ajx3DebugService.this.initX = motionEvent.getRawX();
                        Ajx3DebugService.this.initY = motionEvent.getRawY();
                        Ajx3DebugService.this.initWindowX = (float) Ajx3DebugService.this.wmParams.x;
                        Ajx3DebugService.this.initWindowY = (float) Ajx3DebugService.this.wmParams.y;
                        break;
                    case 1:
                        if (Math.abs(Ajx3DebugService.this.initWindowX - ((float) Ajx3DebugService.this.wmParams.x)) < 10.0f && Math.abs(Ajx3DebugService.this.initWindowY - ((float) Ajx3DebugService.this.wmParams.y)) < 10.0f && !Ajx3DebugService.this.boomMenuButton.boom()) {
                            Ajx3DebugService.this.boomMenuButton.dismiss();
                            break;
                        }
                    case 2:
                        Ajx3DebugService.this.wmParams.x = (int) (Ajx3DebugService.this.initWindowX + (motionEvent.getRawX() - Ajx3DebugService.this.initX));
                        Ajx3DebugService.this.wmParams.y = (int) (Ajx3DebugService.this.initWindowY + (motionEvent.getRawY() - Ajx3DebugService.this.initY));
                        Ajx3DebugService.this.mWindowManager.updateViewLayout(Ajx3DebugService.this.mFloatLayout, Ajx3DebugService.this.wmParams);
                        break;
                }
                return true;
            }
        });
        initBoom();
        this.boomMenuButton.setOnStateChangeListener(this.boomMenuStateChangeListener);
        this.boomMenuButton.setOnSubButtonClickListener(this.subButtonClickListener);
    }

    private void initBoom() {
        int i;
        if (!this.init) {
            this.init = true;
            int[][] iArr = (int[][]) Array.newInstance(int.class, new int[]{8, 2});
            for (int i2 = 0; i2 < 8; i2++) {
                iArr[i2][1] = ContextCompat.getColor(this, R.color.material_white);
                int[] iArr2 = iArr[i2];
                cnu.a();
                int i3 = iArr[i2][1];
                if (cnu.b(i3) == i3) {
                    i = cnu.a(i3);
                } else {
                    i = cnu.b(i3);
                }
                iArr2[0] = i;
            }
            b a = new b().a(ContextCompat.getDrawable(this, R.drawable.ajxdebug_analyzer), iArr[0], "摇一摇").a(ContextCompat.getDrawable(this, R.drawable.ajxdebug_refresh_local), iArr[0], "本地").a(ContextCompat.getDrawable(this, R.drawable.ajxdebug_refresh_online), iArr[0], "刷新").a(ContextCompat.getDrawable(this, R.drawable.ajxdebug_scan), iArr[0], "扫码").a(ContextCompat.getDrawable(this, R.drawable.ajxdebug_load_test_instance), iArr[0], IN_APK).a(ContextCompat.getDrawable(this, R.drawable.ajxdebug_debug_icon), iArr[0], BACK).a(ContextCompat.getDrawable(this, R.drawable.ajxdebug_debug_performance), iArr[0], "调试设置").a(ContextCompat.getDrawable(this, R.drawable.ajxdebug_debug_performance), iArr[0], OPEN_DEBUG_SOCKET).a(ContextCompat.getDrawable(this, R.drawable.ajxdebug_debug_performance), iArr[0], "修改鹰眼IP");
            a.i = ButtonType.CIRCLE;
            a.j = BoomType.PARABOLA;
            a.k = PlaceType.CIRCLE_9_1;
            a.A = ContextCompat.getColor(this, R.color.darkness);
            cnu.a();
            float a2 = cnu.a(2.0f);
            cnu.a();
            float a3 = cnu.a(2.0f);
            a.y = a2;
            a.z = a3;
            BoomMenuButton boomMenuButton2 = this.boomMenuButton;
            if (boomMenuButton2 == null) {
                throw new RuntimeException("BMB is null!");
            }
            Drawable[] drawableArr = new Drawable[a.a.size()];
            for (int i4 = 0; i4 < a.a.size(); i4++) {
                drawableArr[i4] = a.a.get(i4);
            }
            String[] strArr = new String[a.c.size()];
            for (int i5 = 0; i5 < a.c.size(); i5++) {
                strArr[i5] = a.c.get(i5);
            }
            int[][] iArr3 = (int[][]) Array.newInstance(int.class, new int[]{a.b.size(), 2});
            for (int i6 = 0; i6 < a.b.size(); i6++) {
                iArr3[i6] = a.b.get(i6);
            }
            boomMenuButton2.init(drawableArr, strArr, iArr3, a.i, a.j, a.k, a.l, a.n, a.q, a.m, a.o, a.r, Integer.valueOf(a.p));
            boomMenuButton2.setFrames(a.d);
            boomMenuButton2.setDuration(a.e);
            boomMenuButton2.setDelay(a.f);
            boomMenuButton2.setShowOrderType(a.g);
            boomMenuButton2.setHideOrderType(a.h);
            boomMenuButton2.setAutoDismiss(a.s);
            boomMenuButton2.setCancelable(a.t);
            boomMenuButton2.setDimType(a.u);
            boomMenuButton2.setClickEffectType(a.v);
            boomMenuButton2.setBoomButtonShadowOffset(a.w, a.x);
            boomMenuButton2.setSubButtonShadowOffset(a.y, a.z);
            boomMenuButton2.setTextViewColor(a.A);
            boomMenuButton2.setImageViewScaleType(a.B);
            boomMenuButton2.setOnClickListener(a.C);
            boomMenuButton2.setAnimatorListener(a.D);
            boomMenuButton2.setOnSubButtonClickListener(a.E);
            boomMenuButton2.setShareLineWidth(a.F);
            boomMenuButton2.setShareLine1Color(a.G);
            boomMenuButton2.setShareLine2Color(a.H);
        }
    }

    public void handleActionRefresh() {
        String a = cny.a(this);
        if (TextUtils.isEmpty(a)) {
            ToastHelper.showToast("The last scan url is null");
            return;
        }
        cny.a(getBaseContext(), a);
        AMapPageUtil.getPageContext().startPage(DownloadPage.class, (PageBundle) null);
    }

    /* access modifiers changed from: private */
    public void changeBaseJs() {
        if (this.baseJsChangeListener != null) {
            this.baseJsChangeListener.onBaseJsChanged();
        }
        if (TextUtils.equals(this.boomMenuButton.strings[4], IN_APK)) {
            this.text.setText("js");
            this.boomMenuButton.setBoomButtonBackgroundColor(Color.parseColor("#FF4081"), Color.parseColor("#FF4081"));
            return;
        }
        this.text.setText("ajx");
        this.boomMenuButton.setBoomButtonBackgroundColor(Color.parseColor("#0091ff"), Color.parseColor("#0091ff"));
    }

    public void setBaseJsChangeListener(BaseJsChangeListener baseJsChangeListener2) {
        this.baseJsChangeListener = baseJsChangeListener2;
    }

    public void setBoomMenuButtonClickListener(e eVar) {
        this.mMenuClickListener = eVar;
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.mFloatLayout != null) {
            this.mWindowManager.removeView(this.mFloatLayout);
        }
        if (this.mUiPerformance != null && this.mUiPerformance.a.b) {
            this.mUiPerformance.a.b();
            this.mUiPerformance = null;
        }
        closeAllWebSocket();
        if (this.mMockView != null) {
            this.mWindowManager.removeView(this.mMockView);
            destroyLifeCycleAjxView();
        }
        unregisterMockReceiver();
        unregisterDebugReceiver();
    }

    public void createSokcet(final String str, final String str2, final JsFunctionCallback jsFunctionCallback) {
        if (this.mSocketClients.containsKey(str)) {
            this.mHandler.post(new Runnable() {
                public void run() {
                    ToastHelper.showToast("ID already exists!");
                }
            });
        } else {
            new Thread(new Runnable() {
                public void run() {
                    MyWebSocketClient myWebSocketClient = new MyWebSocketClient(str, str2, jsFunctionCallback);
                    myWebSocketClient.connect();
                    Ajx3DebugService.this.mSocketClients.put(str, myWebSocketClient);
                    Ajx3DebugService.this.mSocketClientsAddres.put(Integer.valueOf(myWebSocketClient.hashCode()), str);
                }
            }).start();
        }
    }

    public void closeSokcet(String str) {
        MyWebSocketClient remove = this.mSocketClients.remove(str);
        if (remove != null) {
            this.mSocketClientsAddres.remove(Integer.valueOf(remove.hashCode()));
            remove.close();
        }
    }

    public void sendMessage(String str, String str2) {
        MyWebSocketClient myWebSocketClient = this.mSocketClients.get(str);
        if (myWebSocketClient != null) {
            myWebSocketClient.send(str2);
        }
    }

    public int getSocketClientCount() {
        return this.mSocketClients.size();
    }

    private void closeAllWebSocket() {
        for (MyWebSocketClient close : this.mSocketClients.values()) {
            close.close();
        }
        this.mSocketClients = null;
        this.mSocketClientsAddres = null;
    }
}
