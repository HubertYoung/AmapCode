package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.silk;

import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.multimedia.img.base.SoLibLoader;
import com.alipay.streammedia.mmengine.MMNativeEngineApi;
import com.alipay.streammedia.mmengine.MMNativeException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class SilkEncoder {
    /* access modifiers changed from: private */
    public AtomicBoolean a = new AtomicBoolean(false);
    /* access modifiers changed from: private */
    public SilkApi b = new SilkApi();
    /* access modifiers changed from: private */
    public List<DataPacket> c = Collections.synchronizedList(new ArrayList());
    private Thread d;
    /* access modifiers changed from: private */
    public int e;
    /* access modifiers changed from: private */
    public int f;
    /* access modifiers changed from: private */
    public int g;
    /* access modifiers changed from: private */
    public EncodeOutputHandler h;
    /* access modifiers changed from: private */
    public boolean i;

    public class DataPacket {
        public byte[] data;
        public int shortSize;
        public short[] shorts;
        public int size;

        public DataPacket(byte[] data2, int size2) {
            this.data = new byte[size2];
            System.arraycopy(data2, 0, this.data, 0, size2);
            this.size = size2;
        }

        public DataPacket(short[] data2, int size2) {
            this.shorts = new short[size2];
            System.arraycopy(data2, 0, this.shorts, 0, size2);
            this.shortSize = size2;
        }

        public short[] getShorts() {
            if (this.shorts == null && this.data != null) {
                this.shorts = SilkUtils.getShortArray(this.data, this.size);
                this.shortSize = this.shorts.length;
            }
            return this.shorts;
        }

        public int getShortSize() {
            return this.shortSize;
        }
    }

    public interface EncodeOutputHandler {
        APAudioInfo getAudioInfo();

        void handle(byte[] bArr, int i);

        void handleFinished();
    }

    public SilkEncoder(int compression, int sampleRate, int targetRate) {
        this.e = compression;
        this.f = sampleRate;
        this.g = targetRate;
        this.i = ConfigManager.getInstance().enableVoiceEffect();
        if (this.i) {
            try {
                MMNativeEngineApi.audioNsAgcProcessInit(sampleRate, (sampleRate * 20) / 1000, 1);
            } catch (MMNativeException e2) {
                Logger.E((String) "SilkEncoder", (Throwable) e2, "SilkEncoder audioNsAgcProcessInit exp code=" + e2.getCode(), new Object[0]);
            }
        }
    }

    public void setEncodeHandler(EncodeOutputHandler handler) {
        this.h = handler;
    }

    public void setCompression(int compression) {
        this.e = compression;
    }

    public void setSampleRate(int sampleRate) {
        this.f = sampleRate;
    }

    public void setTargetRate(int targetRate) {
        this.g = targetRate;
    }

    public void add(byte[] data, int size) {
        this.c.add(new DataPacket(data, size));
    }

    public void add(short[] data, int size) {
        this.c.add(new DataPacket(data, size));
    }

    public void stop() {
        this.a.set(false);
    }

    public void reset() {
        this.a.set(false);
        if (this.d != null) {
            this.d.interrupt();
            this.d = null;
        }
        this.c.clear();
    }

    static {
        IjkMediaPlayer.loadLibrariesOnce(new SoLibLoader());
    }

    public void start() {
        this.a.set(true);
        if (this.d == null) {
            this.d = new Thread(new Runnable() {
                public void run() {
                    SilkEncoder.this.b.openEncoder(SilkEncoder.this.e, SilkEncoder.this.f, SilkEncoder.this.g);
                    byte[] buf = new byte[4096];
                    boolean first = true;
                    while (true) {
                        try {
                            if (!SilkEncoder.this.a.get() && SilkEncoder.this.c.isEmpty() && !first) {
                                break;
                            } else if (SilkEncoder.this.c.isEmpty() || first) {
                                try {
                                    Thread.sleep(20);
                                    first = false;
                                } catch (InterruptedException e) {
                                }
                            } else {
                                long count = 0;
                                long total_time = 0;
                                while (SilkEncoder.this.c.size() > 0) {
                                    if (SilkEncoder.this.h != null) {
                                        DataPacket packet = (DataPacket) SilkEncoder.this.c.remove(0);
                                        long ts = System.nanoTime();
                                        if (SilkEncoder.this.i) {
                                            packet = SilkEncoder.this.a(packet);
                                        }
                                        total_time += System.nanoTime() - ts;
                                        count++;
                                        SilkEncoder.this.h.handle(buf, SilkEncoder.this.b.encode(packet.getShorts(), 0, buf, packet.getShortSize()));
                                    }
                                    Arrays.fill(buf, 0);
                                }
                                SilkEncoder.this.h.getAudioInfo().getExtra().putLong("encode_avg_time", total_time / count);
                            }
                        } catch (ArrayIndexOutOfBoundsException e2) {
                            Logger.E((String) "SilkEncoder", (Throwable) e2, (String) "start ArrayIndexOutOfBoundsException exp", new Object[0]);
                        } catch (Throwable th) {
                            SilkEncoder.this.b.closeEncoder();
                            try {
                                if (SilkEncoder.this.i) {
                                    MMNativeEngineApi.audioNsAgcProcessDestory();
                                }
                            } catch (MMNativeException e3) {
                                Logger.E((String) "SilkEncoder", (Throwable) e3, "audioNsAgcProcessDestory error code=" + e3.getCode(), new Object[0]);
                            }
                            if (SilkEncoder.this.h != null) {
                                SilkEncoder.this.h.handleFinished();
                            }
                            throw th;
                        }
                    }
                    SilkEncoder.this.b.closeEncoder();
                    try {
                        if (SilkEncoder.this.i) {
                            MMNativeEngineApi.audioNsAgcProcessDestory();
                        }
                    } catch (MMNativeException e4) {
                        Logger.E((String) "SilkEncoder", (Throwable) e4, "audioNsAgcProcessDestory error code=" + e4.getCode(), new Object[0]);
                    }
                    if (SilkEncoder.this.h != null) {
                        SilkEncoder.this.h.handleFinished();
                    }
                }
            });
            this.d.setName("SilkEncoder");
            this.d.start();
        }
    }

    /* access modifiers changed from: private */
    public DataPacket a(DataPacket packet) {
        if (packet == null) {
            return packet;
        }
        try {
            if (packet.data == null) {
                return packet;
            }
            byte[] out = MMNativeEngineApi.audioNsAgcProcess(packet.data);
            return new DataPacket(out, out.length);
        } catch (MMNativeException e2) {
            Logger.E((String) "SilkEncoder", (Throwable) e2, (String) "processVoice error", new Object[0]);
            return packet;
        }
    }
}
