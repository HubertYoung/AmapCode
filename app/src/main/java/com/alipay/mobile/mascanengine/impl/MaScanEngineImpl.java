package com.alipay.mobile.mascanengine.impl;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.text.TextUtils;
import com.alipay.ma.common.b.c;
import com.alipay.ma.common.b.e;
import com.alipay.ma.decode.MaDecode;
import com.alipay.ma.parser.Ma4GParSer;
import com.alipay.ma.parser.MaARParSer;
import com.alipay.ma.parser.MaBarParSer;
import com.alipay.ma.parser.MaDMParSer;
import com.alipay.ma.parser.MaGen3ParSer;
import com.alipay.ma.parser.MaParSer;
import com.alipay.ma.parser.MaQrParSer;
import com.alipay.ma.parser.MaTBAntiFakeParSer;
import com.alipay.mobile.bqcscanservice.BQCCameraParam.MaEngineType;
import com.alipay.mobile.bqcscanservice.BQCScanEngine;
import com.alipay.mobile.bqcscanservice.BQCScanEngine.EngineCallback;
import com.alipay.mobile.bqcscanservice.BQCScanResult;
import com.alipay.mobile.bqcscanservice.Logger;
import com.alipay.mobile.mascanengine.IOnMaSDKDecodeInfo;
import com.alipay.mobile.mascanengine.MaScanCallback;
import com.alipay.mobile.mascanengine.MultiMaScanResult;
import com.alipay.mobile.strategies.a;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MaScanEngineImpl extends BQCScanEngine {
    public static String USE_COLD_SEPARATE = "use_cold_separate";
    public int GRAY_CALLBACK_PACE = 5;
    public int PORTION_CALLBACK_PACE = 1;
    private MaScanCallback a = null;
    private int b;
    private List<MaParSer> c = new ArrayList();
    private String d;
    private float e;
    private boolean f;
    private boolean g = true;
    private a h;
    private MultiMaScanResult i;
    private MaEngineType j;

    public void setSubScanType(MaEngineType type) {
        this.j = type;
    }

    public void setResultCallback(EngineCallback callback) {
        if (callback != null && (callback instanceof MaScanCallback)) {
            Logger.d("MaScanEngine", "setResultCallback(): " + callback);
            this.a = (MaScanCallback) callback;
        }
    }

    public boolean init(Context ctx, Map<String, Object> cameraParameters) {
        this.h = new a();
        this.i = null;
        if (cameraParameters != null) {
            for (String key : cameraParameters.keySet()) {
                Object value = cameraParameters.get(key);
                if (value != null) {
                    if (TextUtils.equals(key, "scan_lazy_recognize_time")) {
                        this.h.a((String) value);
                    } else if (TextUtils.equals(key, "scan_black_list")) {
                        this.h.b((String) value);
                    } else if (TextUtils.equals(key, MaDecode.USE_OLD_ENCODE)) {
                        MaDecode.useOldEncodeGuess = true;
                    } else if (!TextUtils.equals(key, USE_COLD_SEPARATE)) {
                        MaDecode.setReaderParams(key, value.toString().toUpperCase());
                    } else if ((value instanceof String) && TextUtils.equals((String) value, "NO")) {
                        this.g = false;
                    }
                }
            }
        }
        this.c.add(new MaQrParSer());
        this.c.add(new MaBarParSer());
        this.c.add(new MaGen3ParSer());
        this.c.add(new MaDMParSer());
        this.c.add(new Ma4GParSer());
        this.c.add(new MaTBAntiFakeParSer());
        this.c.add(new MaARParSer());
        return true;
    }

    public void destroy() {
        Logger.d("MaScanEngine", "MaScanEngine Destroy");
        this.a = null;
    }

    public void start() {
        this.b = 0;
        this.i = null;
    }

    /* access modifiers changed from: protected */
    public MultiMaScanResult doProcessBinary(byte[] bitMatrix, Camera mCamera, Rect scanRect, int binarizeMethod, Size previewSize, int previewFormat, int avgGrey) {
        this.b++;
        if (bitMatrix == null || mCamera == null) {
            return null;
        }
        if (previewSize == null || previewFormat < 0) {
            previewSize = mCamera.getParameters().getPreviewSize();
        }
        c[] maResults = null;
        if (com.alipay.ma.analyze.a.a.a()) {
            com.alipay.ma.analyze.a.a.a(this.c);
        }
        if (scanRect != null) {
            if (this.j == null || this.j == MaEngineType.ALL) {
                maResults = com.alipay.ma.analyze.a.a.a(bitMatrix, previewSize.width, previewSize.height, scanRect, binarizeMethod, e.QR, e.PRODUCT, e.EXPRESS, e.MEDICINE, e.GEN3, e.DM, e.TB_4G, e.TB_ANTI_FAKE, e.ARCODE);
            } else if (this.j == MaEngineType.QRCODE) {
                maResults = com.alipay.ma.analyze.a.a.a(bitMatrix, previewSize.width, previewSize.height, scanRect, binarizeMethod, e.QR, e.GEN3, e.TB_4G, e.TB_ANTI_FAKE);
            } else if (this.j == MaEngineType.BAR) {
                maResults = com.alipay.ma.analyze.a.a.a(bitMatrix, previewSize.width, previewSize.height, scanRect, binarizeMethod, e.EXPRESS, e.MEDICINE, e.PRODUCT);
            }
            if (maResults == null || maResults.length == 0) {
                maResults = null;
            } else {
                com.alipay.ma.common.a.a.a("MaScanEngine", "total get " + maResults.length + " codes");
                for (c maResult : maResults) {
                    com.alipay.ma.common.a.a.a("MaScanEngine", String.format("code %s, EC %s, EB %d, LV %s, STR %d", new Object[]{maResult.b(), Character.valueOf(maResult.g), Integer.valueOf(maResult.h), Integer.valueOf(maResult.i), Integer.valueOf(maResult.j)}));
                }
            }
            this.d = MaDecode.getReaderParams();
            if (this.a != null && maResults == null) {
                if (this.g && this.f && this.b <= 8) {
                    com.alipay.ma.common.a.a.a("MaScanEngine", " maCallback not ready");
                } else if (this.a == null || !(this.a instanceof IOnMaSDKDecodeInfo)) {
                    com.alipay.ma.common.a.a.a("MaScanEngine", "maCallback is released or not support IOnMaSDKDecodeInfo");
                } else {
                    if (avgGrey >= 0 && this.b % this.GRAY_CALLBACK_PACE == 0) {
                        this.b %= 10000;
                        if (avgGrey < 70 || avgGrey > 140) {
                            try {
                                ((IOnMaSDKDecodeInfo) this.a).onGetAvgGray(avgGrey);
                            } catch (Throwable tt) {
                                com.alipay.ma.common.a.a.c("MaScanEngine", "onGetAvgGray: " + tt.getMessage());
                            }
                        }
                    }
                    float qrAreaProportion = MaDecode.getMaProportion();
                    if (scanRect != null && qrAreaProportion >= 0.0f) {
                        this.e = qrAreaProportion * qrAreaProportion * ((float) scanRect.width()) * ((float) scanRect.height());
                    }
                    if (qrAreaProportion >= 0.0f && this.b % this.PORTION_CALLBACK_PACE == 0) {
                        try {
                            ((IOnMaSDKDecodeInfo) this.a).onGetMaProportion(qrAreaProportion);
                        } catch (Throwable tt2) {
                            com.alipay.ma.common.a.a.c("MaScanEngine", "onGetQRAreaProportion: " + tt2.getMessage());
                        }
                    }
                }
            }
        }
        return MaScanResultUtils.fromMaResults(maResults);
    }

    /* access modifiers changed from: protected */
    public MultiMaScanResult doProcess(byte[] mData, Camera mCamera, Rect region, Size previewSize, int previewFormat) {
        this.b++;
        if (mData == null || mCamera == null) {
            return null;
        }
        if (previewSize == null || previewFormat < 0) {
            Parameters parameters = mCamera.getParameters();
            previewSize = parameters.getPreviewSize();
            previewFormat = parameters.getPreviewFormat();
        }
        YuvImage yuvImage = new YuvImage(mData, previewFormat, previewSize.width, previewSize.height, null);
        Rect scanRect = null;
        if (region != null) {
            if (region.left < 0) {
                region.left = 0;
            }
            if (region.top < 0) {
                region.top = 0;
            }
            if (region.right > previewSize.width) {
                region.right = previewSize.width;
            }
            if (region.bottom > previewSize.height) {
                region.bottom = previewSize.height;
            }
            scanRect = region;
        }
        c[] maResults = null;
        if (com.alipay.ma.analyze.a.a.a()) {
            com.alipay.ma.analyze.a.a.a(this.c);
        }
        if (scanRect != null) {
            if (this.j == null || this.j == MaEngineType.ALL) {
                maResults = com.alipay.ma.analyze.a.a.a(yuvImage, scanRect, e.QR, e.PRODUCT, e.EXPRESS, e.MEDICINE, e.GEN3, e.DM, e.TB_4G, e.TB_ANTI_FAKE, e.ARCODE);
            } else if (this.j == MaEngineType.QRCODE) {
                maResults = com.alipay.ma.analyze.a.a.a(yuvImage, scanRect, e.QR, e.GEN3, e.TB_4G, e.TB_ANTI_FAKE);
            } else if (this.j == MaEngineType.BAR) {
                maResults = com.alipay.ma.analyze.a.a.a(yuvImage, scanRect, e.EXPRESS, e.MEDICINE, e.PRODUCT);
            }
            if (maResults == null || maResults.length == 0) {
                maResults = null;
            } else {
                com.alipay.ma.common.a.a.a("MaScanEngine", "total get " + maResults.length + " codes");
                for (c maResult : maResults) {
                    com.alipay.ma.common.a.a.a("MaScanEngine", String.format("code %s, EC %s, EB %d, LV %s, STR %d", new Object[]{maResult.b(), Character.valueOf(maResult.g), Integer.valueOf(maResult.h), Integer.valueOf(maResult.i), Integer.valueOf(maResult.j)}));
                }
            }
            this.d = MaDecode.getReaderParams();
            if (this.a == null || maResults != null) {
                com.alipay.ma.common.a.a.a("MaScanEngine", " maCallback not ready");
            } else if (this.a == null || !(this.a instanceof IOnMaSDKDecodeInfo) || this.b <= 8) {
                com.alipay.ma.common.a.a.a("MaScanEngine", "maCallback is released or not support IOnMaSDKDecodeInfo");
            } else {
                int avgGray = MaDecode.getLastFrameAverageGray();
                if (avgGray >= 0 && this.b % this.GRAY_CALLBACK_PACE == 0) {
                    this.b %= 10000;
                    if (avgGray < 70 || avgGray > 140) {
                        try {
                            ((IOnMaSDKDecodeInfo) this.a).onGetAvgGray(avgGray);
                        } catch (Throwable tt) {
                            com.alipay.ma.common.a.a.c("MaScanEngine", "onGetAvgGray: " + tt.getMessage());
                        }
                    }
                }
                float qrAreaProportion = MaDecode.getMaProportion();
                if (scanRect != null && qrAreaProportion >= 0.0f) {
                    this.e = qrAreaProportion * qrAreaProportion * ((float) scanRect.width()) * ((float) scanRect.height());
                }
                if (qrAreaProportion >= 0.0f && this.b % this.PORTION_CALLBACK_PACE == 0) {
                    try {
                        ((IOnMaSDKDecodeInfo) this.a).onGetMaProportion(qrAreaProportion);
                    } catch (Throwable tt2) {
                        com.alipay.ma.common.a.a.c("MaScanEngine", "onGetQRAreaProportion: " + tt2.getMessage());
                    }
                }
            }
        }
        return MaScanResultUtils.fromMaResults(maResults);
    }

    public BQCScanResult process(byte[] mData, Camera mCamera, Rect region, Size previewSize, int previewFormat) {
        return doProcess(mData, mCamera, region, previewSize, previewFormat);
    }

    /* JADX WARNING: type inference failed for: r7v1 */
    /* JADX WARNING: type inference failed for: r2v3 */
    /* JADX WARNING: type inference failed for: r2v7 */
    /* JADX WARNING: type inference failed for: r7v3 */
    /* JADX WARNING: type inference failed for: r7v4, types: [com.alipay.mobile.mascanengine.MultiMaScanResult] */
    /* JADX WARNING: type inference failed for: r7v5 */
    /* JADX WARNING: type inference failed for: r7v6 */
    /* JADX WARNING: type inference failed for: r7v7 */
    /* JADX WARNING: type inference failed for: r7v8 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 4 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onProcessFinish(com.alipay.mobile.bqcscanservice.BQCScanResult r7) {
        /*
            r6 = this;
            r5 = 0
            r1 = 1
            r3 = 0
            com.alipay.mobile.strategies.a r2 = r6.h
            if (r2 == 0) goto L_0x004b
            if (r7 == 0) goto L_0x008d
            r0 = r7
            com.alipay.mobile.mascanengine.MultiMaScanResult r0 = (com.alipay.mobile.mascanengine.MultiMaScanResult) r0
            com.alipay.mobile.mascanengine.MaScanResult[] r2 = r0.maScanResults
            if (r2 == 0) goto L_0x002c
            com.alipay.mobile.mascanengine.MaScanResult[] r2 = r0.maScanResults
            int r2 = r2.length
            if (r2 <= 0) goto L_0x002c
            com.alipay.mobile.strategies.a r2 = r6.h
            com.alipay.mobile.mascanengine.MultiMaScanResult r0 = r2.a(r0)
            if (r0 == 0) goto L_0x008b
            boolean r2 = r0.candidate
            if (r2 == 0) goto L_0x008b
            r6.i = r0
            com.alipay.mobile.strategies.a r2 = r6.h
            int r4 = r2.a
            int r4 = r4 + -1
            r2.a = r4
            r7 = 0
        L_0x002c:
            com.alipay.mobile.strategies.a r2 = r6.h
            int r2 = r2.a
            if (r2 >= 0) goto L_0x004b
            if (r7 != 0) goto L_0x004b
            com.alipay.mobile.mascanengine.MultiMaScanResult r2 = r6.i
            if (r2 == 0) goto L_0x004b
            com.alipay.mobile.mascanengine.MultiMaScanResult r7 = r6.i
            com.alipay.mobile.mascanengine.MultiMaScanResult r2 = r6.i
            boolean r2 = r2.candidate
            com.alipay.mobile.mascanengine.MultiMaScanResult r4 = r6.i
            com.alipay.mobile.mascanengine.MaScanResult[] r4 = r4.maScanResults
            r4 = r4[r3]
            java.lang.String r4 = r4.text
            com.alipay.mobile.mascanengine.BuryRecord.recordLazyRecorgnized(r2, r4)
            r6.i = r5
        L_0x004b:
            if (r7 == 0) goto L_0x009a
            com.alipay.mobile.mascanengine.MaScanCallback r2 = r6.a
            if (r2 == 0) goto L_0x009a
            boolean r2 = r7 instanceof com.alipay.mobile.mascanengine.MultiMaScanResult
            if (r2 == 0) goto L_0x009a
            r2 = r7
            com.alipay.mobile.mascanengine.MultiMaScanResult r2 = (com.alipay.mobile.mascanengine.MultiMaScanResult) r2
            com.alipay.mobile.mascanengine.MaScanResult[] r2 = r2.maScanResults
            r2 = r2[r3]
            com.alipay.mobile.mascanengine.BuryRecord.recordScanSuccess(r2)
            r2 = r7
            com.alipay.mobile.mascanengine.MultiMaScanResult r2 = (com.alipay.mobile.mascanengine.MultiMaScanResult) r2
            com.alipay.mobile.mascanengine.MaScanResult[] r2 = r2.maScanResults
            r2 = r2[r3]
            com.alipay.mobile.mascanengine.BuryRecord.recordProblemCode(r2)
            com.alipay.mobile.mascanengine.MaScanCallback r2 = r6.a
            if (r2 == 0) goto L_0x0074
            com.alipay.mobile.mascanengine.MaScanCallback r2 = r6.a
            com.alipay.mobile.mascanengine.MultiMaScanResult r7 = (com.alipay.mobile.mascanengine.MultiMaScanResult) r7
            r2.onResultMa(r7)
        L_0x0074:
            java.lang.String r2 = "MaScanEngine"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "decode success "
            r3.<init>(r4)
            java.lang.String r4 = r6.d
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            com.alipay.ma.common.a.a.a(r2, r3)
        L_0x008a:
            return r1
        L_0x008b:
            r7 = r0
            goto L_0x002c
        L_0x008d:
            com.alipay.mobile.mascanengine.MultiMaScanResult r2 = r6.i
            if (r2 == 0) goto L_0x002c
            com.alipay.mobile.strategies.a r2 = r6.h
            int r4 = r2.a
            int r4 = r4 + -1
            r2.a = r4
            goto L_0x002c
        L_0x009a:
            if (r7 == 0) goto L_0x00a1
        L_0x009c:
            if (r1 == 0) goto L_0x008a
            r6.i = r5
            goto L_0x008a
        L_0x00a1:
            r1 = r3
            goto L_0x009c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.mascanengine.impl.MaScanEngineImpl.onProcessFinish(com.alipay.mobile.bqcscanservice.BQCScanResult):boolean");
    }

    public boolean isQrCodeEngine() {
        return true;
    }

    public float getCodeSize() {
        return this.e;
    }

    public void setWhetherFirstSetup(boolean firstSetup) {
        this.f = firstSetup;
    }
}
