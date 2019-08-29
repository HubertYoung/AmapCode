package com.google.zxing.qrcode.encoder;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Mode;
import com.google.zxing.qrcode.decoder.Version;

public final class QRCode {
    public static final int NUM_MASK_PATTERNS = 8;
    private ErrorCorrectionLevel ecLevel;
    private int maskPattern = -1;
    private ByteMatrix matrix;
    private Mode mode;
    private Version version;

    public static boolean isValidMaskPattern(int i) {
        return i >= 0 && i < 8;
    }

    public final Mode getMode() {
        return this.mode;
    }

    public final ErrorCorrectionLevel getECLevel() {
        return this.ecLevel;
    }

    public final Version getVersion() {
        return this.version;
    }

    public final int getMaskPattern() {
        return this.maskPattern;
    }

    public final ByteMatrix getMatrix() {
        return this.matrix;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(200);
        sb.append("<<\n");
        sb.append(" mode: ");
        sb.append(this.mode);
        sb.append("\n ecLevel: ");
        sb.append(this.ecLevel);
        sb.append("\n version: ");
        sb.append(this.version);
        sb.append("\n maskPattern: ");
        sb.append(this.maskPattern);
        if (this.matrix == null) {
            sb.append("\n matrix: null\n");
        } else {
            sb.append("\n matrix:\n");
            sb.append(this.matrix);
        }
        sb.append(">>\n");
        return sb.toString();
    }

    public final void setMode(Mode mode2) {
        this.mode = mode2;
    }

    public final void setECLevel(ErrorCorrectionLevel errorCorrectionLevel) {
        this.ecLevel = errorCorrectionLevel;
    }

    public final void setVersion(Version version2) {
        this.version = version2;
    }

    public final void setMaskPattern(int i) {
        this.maskPattern = i;
    }

    public final void setMatrix(ByteMatrix byteMatrix) {
        this.matrix = byteMatrix;
    }
}
