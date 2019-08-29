package com.google.zxing;

import com.google.zxing.aztec.AztecWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.datamatrix.DataMatrixWriter;
import com.google.zxing.oned.CodaBarWriter;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.oned.Code39Writer;
import com.google.zxing.oned.Code93Writer;
import com.google.zxing.oned.EAN13Writer;
import com.google.zxing.oned.EAN8Writer;
import com.google.zxing.oned.ITFWriter;
import com.google.zxing.oned.UPCAWriter;
import com.google.zxing.oned.UPCEWriter;
import com.google.zxing.pdf417.PDF417Writer;
import com.google.zxing.qrcode.QRCodeWriter;
import java.util.Map;

public final class MultiFormatWriter implements Writer {
    private static /* synthetic */ int[] $SWITCH_TABLE$com$google$zxing$BarcodeFormat;

    /* JADX WARNING: Can't wrap try/catch for region: R(37:3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31|32|33|34|35|36|37|38|40) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:10:0x0027 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:12:0x0030 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:14:0x0039 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:16:0x0042 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:18:0x004c */
    /* JADX WARNING: Missing exception handler attribute for start block: B:20:0x0055 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:22:0x005f */
    /* JADX WARNING: Missing exception handler attribute for start block: B:24:0x0069 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:26:0x0073 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:28:0x007d */
    /* JADX WARNING: Missing exception handler attribute for start block: B:30:0x0087 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:32:0x0091 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:34:0x009b */
    /* JADX WARNING: Missing exception handler attribute for start block: B:36:0x00a5 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:6:0x0015 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:8:0x001e */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ int[] $SWITCH_TABLE$com$google$zxing$BarcodeFormat() {
        /*
            int[] r0 = $SWITCH_TABLE$com$google$zxing$BarcodeFormat
            if (r0 == 0) goto L_0x0005
            return r0
        L_0x0005:
            com.google.zxing.BarcodeFormat[] r0 = com.google.zxing.BarcodeFormat.values()
            int r0 = r0.length
            int[] r0 = new int[r0]
            com.google.zxing.BarcodeFormat r1 = com.google.zxing.BarcodeFormat.AZTEC     // Catch:{ NoSuchFieldError -> 0x0015 }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0015 }
            r2 = 1
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0015 }
        L_0x0015:
            com.google.zxing.BarcodeFormat r1 = com.google.zxing.BarcodeFormat.CODABAR     // Catch:{ NoSuchFieldError -> 0x001e }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001e }
            r2 = 2
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001e }
        L_0x001e:
            com.google.zxing.BarcodeFormat r1 = com.google.zxing.BarcodeFormat.CODE_128     // Catch:{ NoSuchFieldError -> 0x0027 }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0027 }
            r2 = 5
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0027 }
        L_0x0027:
            com.google.zxing.BarcodeFormat r1 = com.google.zxing.BarcodeFormat.CODE_39     // Catch:{ NoSuchFieldError -> 0x0030 }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0030 }
            r2 = 3
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0030 }
        L_0x0030:
            com.google.zxing.BarcodeFormat r1 = com.google.zxing.BarcodeFormat.CODE_93     // Catch:{ NoSuchFieldError -> 0x0039 }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0039 }
            r2 = 4
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0039 }
        L_0x0039:
            com.google.zxing.BarcodeFormat r1 = com.google.zxing.BarcodeFormat.DATA_MATRIX     // Catch:{ NoSuchFieldError -> 0x0042 }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0042 }
            r2 = 6
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0042 }
        L_0x0042:
            com.google.zxing.BarcodeFormat r1 = com.google.zxing.BarcodeFormat.EAN_13     // Catch:{ NoSuchFieldError -> 0x004c }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x004c }
            r2 = 8
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x004c }
        L_0x004c:
            com.google.zxing.BarcodeFormat r1 = com.google.zxing.BarcodeFormat.EAN_8     // Catch:{ NoSuchFieldError -> 0x0055 }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0055 }
            r2 = 7
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0055 }
        L_0x0055:
            com.google.zxing.BarcodeFormat r1 = com.google.zxing.BarcodeFormat.ITF     // Catch:{ NoSuchFieldError -> 0x005f }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x005f }
            r2 = 9
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x005f }
        L_0x005f:
            com.google.zxing.BarcodeFormat r1 = com.google.zxing.BarcodeFormat.MAXICODE     // Catch:{ NoSuchFieldError -> 0x0069 }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0069 }
            r2 = 10
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0069 }
        L_0x0069:
            com.google.zxing.BarcodeFormat r1 = com.google.zxing.BarcodeFormat.PDF_417     // Catch:{ NoSuchFieldError -> 0x0073 }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0073 }
            r2 = 11
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0073 }
        L_0x0073:
            com.google.zxing.BarcodeFormat r1 = com.google.zxing.BarcodeFormat.QR_CODE     // Catch:{ NoSuchFieldError -> 0x007d }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x007d }
            r2 = 12
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x007d }
        L_0x007d:
            com.google.zxing.BarcodeFormat r1 = com.google.zxing.BarcodeFormat.RSS_14     // Catch:{ NoSuchFieldError -> 0x0087 }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0087 }
            r2 = 13
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0087 }
        L_0x0087:
            com.google.zxing.BarcodeFormat r1 = com.google.zxing.BarcodeFormat.RSS_EXPANDED     // Catch:{ NoSuchFieldError -> 0x0091 }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0091 }
            r2 = 14
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0091 }
        L_0x0091:
            com.google.zxing.BarcodeFormat r1 = com.google.zxing.BarcodeFormat.UPC_A     // Catch:{ NoSuchFieldError -> 0x009b }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x009b }
            r2 = 15
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x009b }
        L_0x009b:
            com.google.zxing.BarcodeFormat r1 = com.google.zxing.BarcodeFormat.UPC_E     // Catch:{ NoSuchFieldError -> 0x00a5 }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x00a5 }
            r2 = 16
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x00a5 }
        L_0x00a5:
            com.google.zxing.BarcodeFormat r1 = com.google.zxing.BarcodeFormat.UPC_EAN_EXTENSION     // Catch:{ NoSuchFieldError -> 0x00af }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x00af }
            r2 = 17
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x00af }
        L_0x00af:
            $SWITCH_TABLE$com$google$zxing$BarcodeFormat = r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.MultiFormatWriter.$SWITCH_TABLE$com$google$zxing$BarcodeFormat():int[]");
    }

    public final BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i, int i2) throws WriterException {
        return encode(str, barcodeFormat, i, i2, null);
    }

    public final BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i, int i2, Map<EncodeHintType, ?> map) throws WriterException {
        Writer writer;
        switch ($SWITCH_TABLE$com$google$zxing$BarcodeFormat()[barcodeFormat.ordinal()]) {
            case 1:
                writer = new AztecWriter();
                break;
            case 2:
                writer = new CodaBarWriter();
                break;
            case 3:
                writer = new Code39Writer();
                break;
            case 4:
                writer = new Code93Writer();
                break;
            case 5:
                writer = new Code128Writer();
                break;
            case 6:
                writer = new DataMatrixWriter();
                break;
            case 7:
                writer = new EAN8Writer();
                break;
            case 8:
                writer = new EAN13Writer();
                break;
            case 9:
                writer = new ITFWriter();
                break;
            case 11:
                writer = new PDF417Writer();
                break;
            case 12:
                writer = new QRCodeWriter();
                break;
            case 15:
                writer = new UPCAWriter();
                break;
            case 16:
                writer = new UPCEWriter();
                break;
            default:
                throw new IllegalArgumentException("No encoder available for format ".concat(String.valueOf(barcodeFormat)));
        }
        return writer.encode(str, barcodeFormat, i, i2, map);
    }
}
