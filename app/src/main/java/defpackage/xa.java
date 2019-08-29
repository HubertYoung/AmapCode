package defpackage;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.mobile.securitycommon.aliauth.AliAuthConstants.Value;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.lotuspool.internal.model.bean.Command;
import com.amap.bundle.lotuspool.internal.model.bean.CommandResult;
import com.autonavi.minimap.offline.model.FilePathHelper;
import com.autonavi.sdk.log.util.LogConstant;
import com.iflytek.tts.TtsService.alc.ALCTtsConstant;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONObject;

/* renamed from: xa reason: default package */
/* compiled from: SoHotfixExecutor */
public class xa implements wv {
    private static final String a = "xa";
    private Context b;

    public final boolean a(Command command) {
        return command != null;
    }

    public xa(Context context) {
        this.b = context;
    }

    /* JADX INFO: finally extract failed */
    public final CommandResult a(String str, int i, Command command) {
        eni eni;
        String str2;
        int i2;
        Command command2 = command;
        try {
            String e = command2.e("url");
            String e2 = command2.e("sign");
            int b2 = command2.b("version");
            int c = command2.c("network");
            if (TextUtils.isEmpty(e)) {
                CommandResult commandResult = new CommandResult(str, command2.b, command2.d, command2.e, command2.i, i, 101, "url is empty");
                return commandResult;
            } else if (TextUtils.isEmpty(e2)) {
                CommandResult commandResult2 = new CommandResult(str, command2.b, command2.d, command2.e, command2.i, i, 101, "sign is empty");
                return commandResult2;
            } else if (b2 < 0) {
                CommandResult commandResult3 = new CommandResult(str, command2.b, command2.d, command2.e, command2.i, i, 101, "version is negative number");
                return commandResult3;
            } else if (!xi.b(c)) {
                CommandResult commandResult4 = new CommandResult(str, command2.b, command2.d, command2.e, command2.i, i, 2007, "network is err");
                return commandResult4;
            } else {
                eni eni2 = new eni(this.b);
                eni2.c = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCIBb9Y6TnI/BUjWZXOKyygiwXvLsKYVAK9wtvEpk02c/EP5hjUWBWjBs+wN3OFZDN/lQy6nT1uBiw5a+U9bAUAiaW+9Zc21pYavI2nY1F/h4NYvMpLa3E2CTbii/wVTD7vZ835/b65oVFNFiCxkX5njry4PFk6tTnZ1WylEYLFnQIDAQAB";
                StringBuilder sb = new StringBuilder();
                sb.append(eni2.a.e);
                sb.append("/");
                sb.append(b2);
                sb.append("_");
                sb.append(agy.a(e));
                sb.append(".patch");
                final String sb2 = sb.toString();
                File file = new File(sb2);
                if (!file.exists()) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(sb2);
                    sb3.append(FilePathHelper.SUFFIX_DOT_TMP);
                    final File file2 = new File(sb3.toString());
                    bjg bjg = new bjg(file2.getAbsolutePath());
                    bjg.setUrl(e);
                    bjg.b = true;
                    AtomicBoolean atomicBoolean = new AtomicBoolean(false);
                    AnonymousClass1 r10 = r1;
                    final File file3 = file;
                    String str3 = e2;
                    bjh a2 = bjh.a();
                    final AtomicBoolean atomicBoolean2 = atomicBoolean;
                    eni eni3 = eni2;
                    AtomicBoolean atomicBoolean3 = atomicBoolean;
                    final int i3 = b2;
                    AnonymousClass1 r1 = new bjf() {
                        public final void onProgressUpdate(long j, long j2) {
                        }

                        public final void onStart(long j, Map<String, List<String>> map, int i) {
                        }

                        public final void onFinish(bpk bpk) {
                            if (file2.renameTo(file3)) {
                                atomicBoolean2.set(true);
                            }
                            int i = i3;
                            String str = sb2;
                            HashMap hashMap = new HashMap();
                            hashMap.put("version", String.valueOf(i));
                            hashMap.put("event", LogConstant.SPLASH_SCREEN_DOWNLOADED);
                            hashMap.put("value", str);
                            String valueOf = String.valueOf(new JSONObject(hashMap));
                            if (bno.a) {
                                String.format("上报数据(%s-%s):%s", new Object[]{"P0060", ALCTtsConstant.EVENT_ID_TTS_INIT_ERROR, valueOf});
                            }
                            synchronized (xa.this) {
                                xa.this.notifyAll();
                            }
                        }

                        public final void onError(int i, int i2) {
                            atomicBoolean2.set(false);
                            synchronized (xa.this) {
                                xa.this.notifyAll();
                            }
                        }
                    };
                    a2.a(bjg, (bjf) r10);
                    synchronized (this) {
                        try {
                            wait();
                        } catch (InterruptedException e3) {
                            long j = command2.b;
                            long j2 = command2.d;
                            int i4 = command2.e;
                            CommandResult commandResult5 = new CommandResult(str, j, j2, i4, command2.i, i, 1201, e3.toString());
                            return commandResult5;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    if (!atomicBoolean3.get()) {
                        CommandResult commandResult6 = new CommandResult(str, command2.b, command2.d, command2.e, command2.i, i, 1201, "download failed");
                        return commandResult6;
                    }
                    str2 = str3;
                    eni = eni3;
                } else {
                    str2 = e2;
                    eni = eni2;
                }
                try {
                    int a3 = eni.a(file, str2, b2);
                    if (bno.a) {
                        i2 = 1;
                        AMapLog.e(a, "hot fix code:".concat(String.valueOf(a3)), true);
                    } else {
                        i2 = 1;
                    }
                    if (a3 != i2) {
                        switch (a3) {
                            case 3:
                                if (!file.delete() && bno.a) {
                                    String str4 = a;
                                    StringBuilder sb4 = new StringBuilder("delete file error,file name:");
                                    sb4.append(file.getAbsolutePath());
                                    AMapLog.e(str4, sb4.toString(), true);
                                }
                                CommandResult commandResult7 = new CommandResult(str, command2.b, command2.d, command2.e, command2.i, i, 2002, "verify patch sign failed");
                                return commandResult7;
                            case 4:
                                CommandResult commandResult8 = new CommandResult(str, command2.b, command2.d, command2.e, command2.i, i, 2003, "patch process failed");
                                return commandResult8;
                            case 5:
                                if (!file.delete() && bno.a) {
                                    String str5 = a;
                                    StringBuilder sb5 = new StringBuilder("delete file error,file name:");
                                    sb5.append(file.getAbsolutePath());
                                    AMapLog.e(str5, sb5.toString(), true);
                                }
                                CommandResult commandResult9 = new CommandResult(str, command2.b, command2.d, command2.e, command2.i, i, 2004, "unzip failed");
                                return commandResult9;
                            case 6:
                                CommandResult commandResult10 = new CommandResult(str, command2.b, command2.d, command2.e, command2.i, i, 2005, "check md5 failed");
                                return commandResult10;
                            case 7:
                                CommandResult commandResult11 = new CommandResult(str, command2.b, command2.d, command2.e, command2.i, i, 2006, "hotfix version exists");
                                return commandResult11;
                            default:
                                CommandResult commandResult12 = new CommandResult(str, command2.b, command2.d, command2.e, command2.i, i, 2001, "unknown");
                                return commandResult12;
                        }
                    } else {
                        if (!file.delete() && bno.a) {
                            String str6 = a;
                            StringBuilder sb6 = new StringBuilder("delete file error file name:");
                            sb6.append(file.getAbsolutePath());
                            AMapLog.e(str6, sb6.toString(), true);
                        }
                        CommandResult commandResult13 = new CommandResult(str, command2.b, command2.d, command2.e, command2.i, i, 1, Value.OK);
                        return commandResult13;
                    }
                } catch (Exception e4) {
                    long j3 = command2.b;
                    long j4 = command2.d;
                    int i5 = command2.e;
                    CommandResult commandResult14 = new CommandResult(str, j3, j4, i5, command2.i, i, 2001, e4.toString());
                    return commandResult14;
                }
            }
        } catch (Exception e5) {
            Exception exc = e5;
            if (bno.a) {
                String str7 = a;
                StringBuilder sb7 = new StringBuilder("get param error:");
                sb7.append(Log.getStackTraceString(exc));
                AMapLog.e(str7, sb7.toString(), true);
            }
            String str8 = str;
            CommandResult commandResult15 = new CommandResult(str8, command2.b, command2.d, command2.e, command2.i, i, 101, exc.toString());
            return commandResult15;
        }
    }
}
