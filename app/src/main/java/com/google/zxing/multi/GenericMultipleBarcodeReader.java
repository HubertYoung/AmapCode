package com.google.zxing.multi;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class GenericMultipleBarcodeReader implements MultipleBarcodeReader {
    private static final int MAX_DEPTH = 4;
    private static final int MIN_DIMENSION_TO_RECUR = 100;
    private final Reader delegate;

    public GenericMultipleBarcodeReader(Reader reader) {
        this.delegate = reader;
    }

    public final Result[] decodeMultiple(BinaryBitmap binaryBitmap) throws NotFoundException {
        return decodeMultiple(binaryBitmap, null);
    }

    public final Result[] decodeMultiple(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map) throws NotFoundException {
        ArrayList arrayList = new ArrayList();
        doDecodeMultiple(binaryBitmap, map, arrayList, 0, 0, 0);
        if (!arrayList.isEmpty()) {
            return (Result[]) arrayList.toArray(new Result[arrayList.size()]);
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private void doDecodeMultiple(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map, List<Result> list, int i, int i2, int i3) {
        int i4;
        boolean z;
        List<Result> list2;
        int i5;
        float f;
        float f2;
        int i6;
        float f3;
        int i7 = i;
        BinaryBitmap binaryBitmap2 = binaryBitmap;
        int i8 = i2;
        int i9 = i3;
        while (i9 <= 4) {
            try {
                Map<DecodeHintType, ?> map2 = map;
                Result decode = this.delegate.decode(binaryBitmap2, map2);
                Iterator<Result> it = list.iterator();
                while (true) {
                    i4 = 0;
                    if (!it.hasNext()) {
                        z = false;
                        break;
                    } else if (it.next().getText().equals(decode.getText())) {
                        z = true;
                        break;
                    } else {
                        List<Result> list3 = list;
                    }
                }
                if (!z) {
                    list2 = list;
                    list2.add(translateResultPoints(decode, i7, i8));
                } else {
                    list2 = list;
                }
                ResultPoint[] resultPoints = decode.getResultPoints();
                if (resultPoints != null && resultPoints.length != 0) {
                    int width = binaryBitmap2.getWidth();
                    int height = binaryBitmap2.getHeight();
                    int length = resultPoints.length;
                    float f4 = 0.0f;
                    float f5 = (float) height;
                    float f6 = 0.0f;
                    float f7 = (float) width;
                    int i10 = 0;
                    while (i10 < length) {
                        int i11 = length;
                        float f8 = f5;
                        float f9 = f6;
                        ResultPoint resultPoint = resultPoints[i10];
                        if (resultPoint != null) {
                            float x = resultPoint.getX();
                            f3 = resultPoint.getY();
                            if (x < f7) {
                                f7 = x;
                            }
                            if (f3 < f8) {
                                f8 = f3;
                            }
                            if (x <= f4) {
                                x = f4;
                            }
                            if (f3 <= f9) {
                                f3 = f9;
                            }
                            f5 = f8;
                            f4 = x;
                        } else {
                            f3 = f9;
                            f5 = f8;
                        }
                        i10++;
                        f6 = f3;
                        length = i11;
                        i4 = 0;
                    }
                    if (f7 > 100.0f) {
                        BinaryBitmap crop = binaryBitmap2.crop(i4, i4, (int) f7, height);
                        f = f4;
                        f2 = f5;
                        i5 = height;
                        doDecodeMultiple(crop, map2, list2, i7, i8, i9 + 1);
                    } else {
                        f = f4;
                        f2 = f5;
                        i5 = height;
                    }
                    if (f2 > 100.0f) {
                        doDecodeMultiple(binaryBitmap2.crop(0, 0, width, (int) f2), map2, list2, i7, i8, i9 + 1);
                    }
                    float f10 = f;
                    if (f10 < ((float) (width - 100))) {
                        int i12 = (int) f10;
                        i6 = i5;
                        doDecodeMultiple(binaryBitmap2.crop(i12, 0, width - i12, i6), map2, list2, i7 + i12, i8, i9 + 1);
                    } else {
                        i6 = i5;
                    }
                    float f11 = f6;
                    if (f11 < ((float) (i6 - 100))) {
                        int i13 = (int) f11;
                        binaryBitmap2 = binaryBitmap2.crop(0, i13, width, i6 - i13);
                        i8 += i13;
                        i9++;
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            } catch (ReaderException unused) {
                return;
            }
        }
    }

    private static Result translateResultPoints(Result result, int i, int i2) {
        ResultPoint[] resultPoints = result.getResultPoints();
        if (resultPoints == null) {
            return result;
        }
        ResultPoint[] resultPointArr = new ResultPoint[resultPoints.length];
        for (int i3 = 0; i3 < resultPoints.length; i3++) {
            ResultPoint resultPoint = resultPoints[i3];
            if (resultPoint != null) {
                resultPointArr[i3] = new ResultPoint(resultPoint.getX() + ((float) i), resultPoint.getY() + ((float) i2));
            }
        }
        Result result2 = new Result(result.getText(), result.getRawBytes(), result.getNumBits(), resultPointArr, result.getBarcodeFormat(), result.getTimestamp());
        result2.putAllMetadata(result.getResultMetadata());
        return result2;
    }
}
