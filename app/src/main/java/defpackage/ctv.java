package defpackage;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.net.Uri;
import android.text.TextUtils;
import com.alipay.mobile.beehive.capture.utils.AudioUtils;
import com.alipay.multimedia.js.video.H5VideoUploadPlugin.UploadVideoParams;
import com.amap.bundle.blutils.log.DebugLog;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.bundle.logs.AMapLog;
import com.tencent.open.SocialConstants;
import java.io.File;
import java.net.CookieHandler;
import org.json.JSONObject;

/* renamed from: ctv reason: default package */
/* compiled from: WebAudioAction */
public class ctv extends vz {
    public final void a(JSONObject jSONObject, wa waVar) {
        JsAdapter a = a();
        if (a != null) {
            try {
                String optString = jSONObject.optString("url");
                String optString2 = jSONObject.optString(SocialConstants.PARAM_ACT);
                String optString3 = jSONObject.optString("loop");
                String optString4 = jSONObject.optString(UploadVideoParams.TYPE_SHORT);
                if (DebugLog.isDebug()) {
                    StringBuilder sb = new StringBuilder("url :");
                    sb.append(optString);
                    sb.append(" act:");
                    sb.append(optString2);
                    sb.append(" loop:");
                    sb.append(optString3);
                    sb.append(" effect:");
                    sb.append(optString4);
                    sb.append("     ");
                    AMapLog.i("========", sb.toString());
                }
                ctq a2 = ctq.a((Context) a.mPageContext.getActivity());
                if (optString4.equals("1")) {
                    if (optString != null && optString.length() != 0) {
                        try {
                            String a3 = ctq.a(optString);
                            File file = new File(a3);
                            if (a3 != null && file.exists()) {
                                if (file.length() > 0) {
                                    SoundPool a4 = a2.a();
                                    a4.load(a3, 0);
                                    a4.setOnLoadCompleteListener(new OnLoadCompleteListener() {
                                        public final void onLoadComplete(SoundPool soundPool, int i, int i2) {
                                            soundPool.play(i, 2.0f, 2.0f, 0, 0, 1.0f);
                                            if (DebugLog.isDebug()) {
                                                AMapLog.i("========", "playEffect play :".concat(String.valueOf(i)));
                                            }
                                        }
                                    });
                                    return;
                                }
                            }
                            a aVar = new a(optString);
                            if (!TextUtils.isEmpty(aVar.e)) {
                                if (!TextUtils.isEmpty(aVar.a)) {
                                    bjg bjg = new bjg(aVar.e);
                                    bjg.setUrl(aVar.a);
                                    bjg.b = aVar.a.startsWith("http");
                                    bjg.a = true;
                                    aVar.b = bjg;
                                    yq.a();
                                    yq.a(bjg, (bjf) aVar);
                                }
                            }
                        } catch (Exception e) {
                            kf.a((Throwable) e);
                        }
                    }
                } else if (optString2.equals(AudioUtils.CMDPLAY)) {
                    boolean z = optString3 != null && optString3.equals("1");
                    if (!TextUtils.isEmpty(optString)) {
                        a2.e = Uri.parse(optString);
                        if (a2.a != null) {
                            try {
                                a2.a.reset();
                                a2.a.setDataSource(a2.f, a2.e);
                                a2.a.prepareAsync();
                            } catch (Exception e2) {
                                kf.a((Throwable) e2);
                            }
                        } else {
                            try {
                                a2.a = new MediaPlayer();
                                a2.a.setWakeMode(a2.f, 1);
                                a2.a.setOnPreparedListener(a2.b);
                                a2.a.setOnCompletionListener(a2.c);
                                a2.a.setOnErrorListener(a2.d);
                                a2.a.setDataSource(a2.f, a2.e);
                                a2.a.setLooping(z);
                                a2.a.prepareAsync();
                            } catch (Exception e3) {
                                kf.a((Throwable) e3);
                            }
                        }
                        if (a2.g != null) {
                            a2.g.setVisibility(0);
                        }
                    }
                } else if (optString2.equals(AudioUtils.CMDPAUSE)) {
                    try {
                        if (a2.a == null || !a2.a.isPlaying()) {
                            if (a2.a != null) {
                                a2.a.start();
                            }
                            return;
                        }
                        a2.a.pause();
                    } catch (Exception e4) {
                        kf.a((Throwable) e4);
                    }
                } else {
                    if (optString2.equals(AudioUtils.CMDSTOP)) {
                        try {
                            if (a2.a != null) {
                                a2.a.stop();
                                a2.a.reset();
                                a2.a.release();
                                a2.a = null;
                            }
                            if (CookieHandler.getDefault() != null) {
                                CookieHandler.setDefault(null);
                            }
                            if (a2.g != null) {
                                a2.g.setVisibility(8);
                            }
                        } catch (Exception e5) {
                            kf.a((Throwable) e5);
                        }
                    }
                }
            } catch (Exception e6) {
                kf.a((Throwable) e6);
            }
        }
    }
}
