package com.taobao.agoo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.autonavi.minimap.ajx3.util.Constants;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.AppMonitorAdapter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.android.agoo.common.MsgDO;
import org.android.agoo.control.AgooFactory;
import org.android.agoo.control.NotifManager;
import org.android.agoo.message.MessageService;

public class BaseNotifyClickActivity extends Activity {
    private static final String TAG = "accs.BaseNotifyClickActivity";
    private static final String TAOBAO_PACKAGE_NAME = "com.taobao.taobao";
    private static Set<INotifyListener> notifyListeners;
    /* access modifiers changed from: private */
    public AgooFactory agooFactory;
    /* access modifiers changed from: private */
    public String msgSource;
    /* access modifiers changed from: private */
    public NotifManager notifyManager;

    public interface INotifyListener {
        String getMsgSource();

        String parseMsgFromIntent(Intent intent);
    }

    public void onMessage(Intent intent) {
    }

    public static void addNotifyListener(INotifyListener iNotifyListener) {
        if (notifyListeners == null) {
            notifyListeners = new HashSet();
        }
        notifyListeners.add(iNotifyListener);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ALog.i(TAG, "onCreate", new Object[0]);
        buildMessage(getIntent());
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ALog.i(TAG, "onNewIntent", new Object[0]);
        buildMessage(intent);
    }

    private void buildMessage(final Intent intent) {
        ThreadPoolExecutorFactory.execute(new Runnable() {
            public void run() {
                Intent intent = null;
                try {
                    if (intent != null) {
                        String access$000 = BaseNotifyClickActivity.this.parseMsgByThirdPush(intent);
                        if (TextUtils.isEmpty(access$000) || TextUtils.isEmpty(BaseNotifyClickActivity.this.msgSource)) {
                            ALog.e(BaseNotifyClickActivity.TAG, "parseMsgFromNotifyListener null!!", "source", BaseNotifyClickActivity.this.msgSource);
                        } else {
                            if (BaseNotifyClickActivity.this.notifyManager == null) {
                                BaseNotifyClickActivity.this.notifyManager = new NotifManager();
                            }
                            if (BaseNotifyClickActivity.this.agooFactory == null) {
                                BaseNotifyClickActivity.this.agooFactory = new AgooFactory();
                                BaseNotifyClickActivity.this.agooFactory.a(BaseNotifyClickActivity.this.getApplicationContext(), BaseNotifyClickActivity.this.notifyManager, (MessageService) null);
                            }
                            Bundle a = BaseNotifyClickActivity.this.agooFactory.a(access$000.getBytes("UTF-8"), BaseNotifyClickActivity.this.msgSource, null, false);
                            String string = a.getString(Constants.BODY);
                            ALog.i(BaseNotifyClickActivity.TAG, "begin parse EncryptedMsg", new Object[0]);
                            BaseNotifyClickActivity.this.agooFactory;
                            String a2 = AgooFactory.a(string);
                            if (!TextUtils.isEmpty(a2)) {
                                a.putString(Constants.BODY, a2);
                            } else {
                                ALog.e(BaseNotifyClickActivity.TAG, "parse EncryptedMsg fail, empty", new Object[0]);
                            }
                            Intent intent2 = new Intent();
                            try {
                                intent2.putExtras(a);
                                BaseNotifyClickActivity.this.agooFactory.a(access$000.getBytes("UTF-8"), (String) "2");
                                BaseNotifyClickActivity.this.reportClickNotifyMsg(intent2);
                                intent = intent2;
                            } catch (Throwable th) {
                                th = th;
                                intent = intent2;
                                BaseNotifyClickActivity.this.onMessage(intent);
                                throw th;
                            }
                        }
                    }
                    try {
                        BaseNotifyClickActivity.this.onMessage(intent);
                    } catch (Throwable th2) {
                        ALog.e(BaseNotifyClickActivity.TAG, "onMessage", th2, new Object[0]);
                    }
                } catch (Throwable th3) {
                    th = th3;
                    ALog.e(BaseNotifyClickActivity.TAG, "buildMessage", th, new Object[0]);
                    BaseNotifyClickActivity.this.onMessage(intent);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public String parseMsgByThirdPush(Intent intent) {
        String str;
        if (notifyListeners != null && notifyListeners.size() > 0) {
            Iterator<INotifyListener> it = notifyListeners.iterator();
            str = null;
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                INotifyListener next = it.next();
                String parseMsgFromIntent = next.parseMsgFromIntent(intent);
                if (!TextUtils.isEmpty(parseMsgFromIntent)) {
                    this.msgSource = next.getMsgSource();
                    str = parseMsgFromIntent;
                    break;
                }
                str = parseMsgFromIntent;
            }
        } else {
            ALog.e(TAG, "no impl, try use default impl to parse intent!", new Object[0]);
            INotifyListener defaultHuaweiMsgParseImpl = new DefaultHuaweiMsgParseImpl();
            String parseMsgFromIntent2 = defaultHuaweiMsgParseImpl.parseMsgFromIntent(intent);
            if (TextUtils.isEmpty(parseMsgFromIntent2)) {
                defaultHuaweiMsgParseImpl = new DefaultXiaomiMsgParseImpl();
                parseMsgFromIntent2 = defaultHuaweiMsgParseImpl.parseMsgFromIntent(intent);
            }
            if (TextUtils.isEmpty(parseMsgFromIntent2)) {
                defaultHuaweiMsgParseImpl = new DefaultOppoMsgParseImpl();
                parseMsgFromIntent2 = defaultHuaweiMsgParseImpl.parseMsgFromIntent(intent);
            }
            if (TextUtils.isEmpty(parseMsgFromIntent2)) {
                defaultHuaweiMsgParseImpl = new DefaultMeizuMsgParseImpl();
                parseMsgFromIntent2 = defaultHuaweiMsgParseImpl.parseMsgFromIntent(intent);
            }
            if (TextUtils.isEmpty(str)) {
                AppMonitorAdapter.commitCount("accs", "error", "parse 3push error", 0.0d);
            } else {
                this.msgSource = defaultHuaweiMsgParseImpl.getMsgSource();
                StringBuilder sb = new StringBuilder("parse 3push default ");
                sb.append(this.msgSource);
                AppMonitorAdapter.commitCount("accs", "error", sb.toString(), 0.0d);
            }
        }
        ALog.i(TAG, "parseMsgByThirdPush", "result", str, "msgSource", this.msgSource);
        return str;
    }

    /* access modifiers changed from: private */
    public void reportClickNotifyMsg(Intent intent) {
        try {
            String stringExtra = intent.getStringExtra("id");
            String stringExtra2 = intent.getStringExtra("message_source");
            String stringExtra3 = intent.getStringExtra("report");
            String stringExtra4 = intent.getStringExtra("extData");
            MsgDO msgDO = new MsgDO();
            msgDO.a = stringExtra;
            msgDO.b = stringExtra4;
            msgDO.e = stringExtra2;
            msgDO.j = stringExtra3;
            msgDO.l = "8";
            StringBuilder sb = new StringBuilder("reportClickNotifyMsg messageId:");
            sb.append(stringExtra);
            sb.append(" source:");
            sb.append(stringExtra2);
            sb.append(" reportStr:");
            sb.append(stringExtra3);
            sb.append(" status:");
            sb.append(msgDO.l);
            ALog.i(TAG, sb.toString(), new Object[0]);
            NotifManager.b(msgDO, null);
        } catch (Exception e) {
            ALog.e(TAG, "reportClickNotifyMsg exception: ".concat(String.valueOf(e)), new Object[0]);
        }
    }
}
