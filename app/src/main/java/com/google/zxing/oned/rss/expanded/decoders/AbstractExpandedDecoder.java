package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;
import com.tencent.connect.common.Constants;

public abstract class AbstractExpandedDecoder {
    private final GeneralAppIdDecoder generalDecoder;
    private final BitArray information;

    public abstract String parseInformation() throws NotFoundException, FormatException;

    AbstractExpandedDecoder(BitArray bitArray) {
        this.information = bitArray;
        this.generalDecoder = new GeneralAppIdDecoder(bitArray);
    }

    /* access modifiers changed from: protected */
    public final BitArray getInformation() {
        return this.information;
    }

    /* access modifiers changed from: protected */
    public final GeneralAppIdDecoder getGeneralDecoder() {
        return this.generalDecoder;
    }

    public static AbstractExpandedDecoder createDecoder(BitArray bitArray) {
        if (bitArray.get(1)) {
            return new AI01AndOtherAIs(bitArray);
        }
        if (!bitArray.get(2)) {
            return new AnyAIDecoder(bitArray);
        }
        switch (GeneralAppIdDecoder.extractNumericValueFromBitArray(bitArray, 1, 4)) {
            case 4:
                return new AI013103decoder(bitArray);
            case 5:
                return new AI01320xDecoder(bitArray);
            default:
                switch (GeneralAppIdDecoder.extractNumericValueFromBitArray(bitArray, 1, 5)) {
                    case 12:
                        return new AI01392xDecoder(bitArray);
                    case 13:
                        return new AI01393xDecoder(bitArray);
                    default:
                        switch (GeneralAppIdDecoder.extractNumericValueFromBitArray(bitArray, 1, 7)) {
                            case 56:
                                return new AI013x0x1xDecoder(bitArray, Constants.VIA_REPORT_TYPE_SSO_LOGIN, "11");
                            case 57:
                                return new AI013x0x1xDecoder(bitArray, "320", "11");
                            case 58:
                                return new AI013x0x1xDecoder(bitArray, Constants.VIA_REPORT_TYPE_SSO_LOGIN, "13");
                            case 59:
                                return new AI013x0x1xDecoder(bitArray, "320", "13");
                            case 60:
                                return new AI013x0x1xDecoder(bitArray, Constants.VIA_REPORT_TYPE_SSO_LOGIN, "15");
                            case 61:
                                return new AI013x0x1xDecoder(bitArray, "320", "15");
                            case 62:
                                return new AI013x0x1xDecoder(bitArray, Constants.VIA_REPORT_TYPE_SSO_LOGIN, "17");
                            case 63:
                                return new AI013x0x1xDecoder(bitArray, "320", "17");
                            default:
                                throw new IllegalStateException("unknown decoder: ".concat(String.valueOf(bitArray)));
                        }
                }
        }
    }
}
