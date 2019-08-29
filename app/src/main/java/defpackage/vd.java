package defpackage;

import com.autonavi.annotation.BundleInterface;
import com.autonavi.jni.eyrie.amap.tbt.ITTSPlayer;
import com.autonavi.jni.eyrie.amap.tbt.NaviManager;
import com.iflytek.tts.TtsService.PlaySoundUtils;

@BundleInterface(vh.class)
/* renamed from: vd reason: default package */
/* compiled from: EyrieAdapterService */
public class vd implements vh {
    public final void a() {
        NaviManager.setTTSPlayer(new ITTSPlayer() {
            public final boolean isPlaying() {
                return PlaySoundUtils.getInstance().isPlaying();
            }

            public final void play(String str, int i, int i2) {
                StringBuilder sb = new StringBuilder("   ITTSPlayer   play  text:");
                sb.append(str);
                sb.append("   sceneType:");
                sb.append(i);
                sb.append("    playID:");
                sb.append(i2);
                ve.a(sb.toString());
                ve.this.a(str, i, i2);
            }

            public final void playRing(int i) {
                ve.a(i);
            }

            public final void stop() {
                ve.a((String) "   ITTSPlayer   stop");
                PlaySoundUtils.getInstance().clear();
            }
        });
    }
}
