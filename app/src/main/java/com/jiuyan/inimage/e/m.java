package com.jiuyan.inimage.e;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import java.util.ArrayList;

/* compiled from: InFaceLabel */
class m implements f {
    private ArrayList<Integer> b = null;
    private int c = 0;

    public m(String str) {
        if (str.equals("LeftBrow")) {
            this.c = 0;
        } else if (str.equals("RightBrow")) {
            this.c = 1;
        } else if (str.equals("Brow")) {
            this.c = 2;
        } else if (str.equals("LeftEye")) {
            this.c = 3;
        } else if (str.equals("RightEye")) {
            this.c = 4;
        } else if (str.equals("Eye")) {
            this.c = 5;
        } else if (str.equals("NoseBridge")) {
            this.c = 6;
        } else if (str.equals("TipOfNose")) {
            this.c = 7;
        } else if (str.equals("Face")) {
            this.c = 8;
        } else if (str.equals("Beard")) {
            this.c = 9;
        } else if (str.equals("UpperLip")) {
            this.c = 10;
        } else if (str.equals("Mouth")) {
            this.c = 11;
        } else if (str.equals("LowerLip")) {
            this.c = 12;
        } else if (str.equals("LeftCornerOfMouth")) {
            this.c = 13;
        } else if (str.equals("RightCornerOfMouth")) {
            this.c = 14;
        } else if (str.equals("Jar")) {
            this.c = 15;
        } else if (str.equals("ForeHead")) {
            this.c = 16;
        } else if (str.equals("LeftEarRoot")) {
            this.c = 17;
        } else if (str.equals("RightEarRoot")) {
            this.c = 18;
        } else if (str.equals("UnderUpperLip")) {
            this.c = 19;
        } else if (str.equals("LeftCheek")) {
            this.c = 20;
        } else if (str.equals("RightCheek")) {
            this.c = 21;
        } else if (str.equals("LeftNostril")) {
            this.c = 22;
        } else if (str.equals("RightNostril")) {
            this.c = 23;
        } else if (str.equals("RightCornerOfLeftBrow")) {
            this.c = 24;
        } else if (str.equals("LeftCornerOfRightBrow")) {
            this.c = 25;
        }
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public g a(c cVar, int i, int i2, int i3, int i4, int i5) {
        return new g((float) cVar.a(this.c)[i].x, (float) cVar.a(this.c)[i].y, cVar.b()[i].e);
    }
}
