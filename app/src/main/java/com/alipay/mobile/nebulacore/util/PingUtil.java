package com.alipay.mobile.nebulacore.util;

import android.os.Looper;
import android.text.TextUtils;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PingUtil {

    public static final class PingResult {
        public float avgConsumedTimeMs = -1.0f;
        public String ipAddr;
        public String loss;
        public int numReceivedPkt;
        public int numSendPkt;
        public Float[] timePerRound;

        public final String toString() {
            return "PingResult\n\n target IP:" + this.ipAddr + "\nconsumed:" + this.avgConsumedTimeMs + "ms\nnumber of packet(s) sent:" + this.numSendPkt + "\nnumber of package(s) received:" + this.numReceivedPkt + "\nloss:" + this.loss + "\n";
        }

        public final boolean success() {
            return this.avgConsumedTimeMs != -1.0f;
        }
    }

    public static PingResult ping(String host, int count) {
        int originTimeCount;
        if (Looper.myLooper() == null || !Looper.getMainLooper().equals(Looper.myLooper())) {
            PingResult result = new PingResult();
            if (!TextUtils.isEmpty(host) && count > 0) {
                try {
                    Process pingProcess = Runtime.getRuntime().exec("/system/bin/ping -w " + count + " -c " + count + Token.SEPARATOR + host);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(pingProcess.getInputStream()));
                    StringBuffer output = new StringBuffer();
                    Float[] originTime = new Float[count];
                    for (int i = 0; i < count; i++) {
                        originTime[i] = Float.valueOf(0.0f);
                    }
                    int outputLineCount = 0;
                    int originTimeCount2 = 0;
                    Pattern pattern = Pattern.compile(".*?time=(.*?\\s)ms");
                    while (true) {
                        String temp = bufferedReader.readLine();
                        if (temp == null) {
                            break;
                        }
                        if (temp.contains(" bytes from ")) {
                            Matcher m = pattern.matcher(temp);
                            int originTimeCount3 = originTimeCount2;
                            while (m.find()) {
                                try {
                                    String time = m.group(1);
                                    if (originTimeCount3 < count) {
                                        originTimeCount = originTimeCount3 + 1;
                                        try {
                                            originTime[originTimeCount3] = Float.valueOf(Float.parseFloat(time));
                                        } catch (IllegalStateException e) {
                                            e = e;
                                            H5Log.e("PingUtil", "exception detail", e);
                                            originTimeCount3 = originTimeCount;
                                        } catch (NumberFormatException e2) {
                                            e = e2;
                                            H5Log.e("PingUtil", "exception detail", e);
                                            originTimeCount3 = originTimeCount;
                                        } catch (IndexOutOfBoundsException e3) {
                                            e = e3;
                                            H5Log.e("PingUtil", "exception detail", e);
                                            originTimeCount3 = originTimeCount;
                                        }
                                    } else {
                                        originTimeCount = originTimeCount3;
                                    }
                                    originTimeCount3 = originTimeCount;
                                } catch (IllegalStateException e4) {
                                    e = e4;
                                    originTimeCount = originTimeCount3;
                                    H5Log.e("PingUtil", "exception detail", e);
                                    originTimeCount3 = originTimeCount;
                                } catch (NumberFormatException e5) {
                                    e = e5;
                                    originTimeCount = originTimeCount3;
                                    H5Log.e("PingUtil", "exception detail", e);
                                    originTimeCount3 = originTimeCount;
                                } catch (IndexOutOfBoundsException e6) {
                                    e = e6;
                                    originTimeCount = originTimeCount3;
                                    H5Log.e("PingUtil", "exception detail", e);
                                    originTimeCount3 = originTimeCount;
                                }
                            }
                            originTimeCount2 = originTimeCount3;
                        }
                        output.append(temp + "\n");
                        outputLineCount++;
                    }
                    bufferedReader.close();
                    if (outputLineCount > 0) {
                        Matcher m2 = Pattern.compile("^PING\\b[^(]*\\(([^)]*)\\)\\s([^.]*)\\..*?^(\\d+\\sbytes).*?icmp_seq=(\\d+).*?ttl=(\\d+).*?time=(.*?)ms.*?(\\d+)\\spackets\\stransmitted.*?(\\d+)\\sreceived.*?(\\d+%)\\spacket\\sloss.*?time\\s(\\d+ms).*?=\\s([^\\/]*)\\/([^\\/]*)\\/([^\\/]*)\\/(.*?)\\sms", 42).matcher(output.toString());
                        int mIdx = 0;
                        while (m2.find()) {
                            for (int groupIdx = 0; groupIdx < m2.groupCount() + 1; groupIdx++) {
                                new StringBuilder("regex[").append(mIdx).append("][").append(groupIdx).append("] = ").append(m2.group(groupIdx));
                            }
                            try {
                                result.ipAddr = m2.group(1);
                                float sumOfTime = 0.0f;
                                for (int i2 = 0; i2 < count; i2++) {
                                    sumOfTime += originTime[i2].floatValue();
                                }
                                result.avgConsumedTimeMs = sumOfTime / ((float) count);
                                result.numSendPkt = Integer.valueOf(m2.group(7)).intValue();
                                result.numReceivedPkt = Integer.valueOf(m2.group(8)).intValue();
                                result.timePerRound = originTime;
                                result.loss = m2.group(9);
                                mIdx++;
                            } catch (NumberFormatException e7) {
                                H5Log.e("PingUtil", "exception detail", e7);
                            }
                        }
                    }
                    pingProcess.waitFor();
                } catch (InterruptedException ignore) {
                    new StringBuilder("Exception:").append(ignore);
                } catch (IOException e8) {
                    H5Log.e("PingUtil", "exception detail", e8);
                    new StringBuilder("Exception:").append(e8);
                }
            }
            return result;
        }
        throw new IllegalThreadStateException("ping shouldn't be invoked in MainThread!");
    }
}
