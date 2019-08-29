package android.support.v4.app;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import java.util.ArrayList;

/* compiled from: BackStackRecord */
final class BackStackState implements Parcelable {
    public static final Creator<BackStackState> CREATOR = new Creator<BackStackState>() {
        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new BackStackState[i];
        }

        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new BackStackState(parcel);
        }
    };
    final int[] a;
    final int b;
    final int c;
    final String d;
    final int e;
    final int f;
    final CharSequence g;
    final int h;
    final CharSequence i;
    final ArrayList<String> j;
    final ArrayList<String> k;

    public final int describeContents() {
        return 0;
    }

    public BackStackState(BackStackRecord backStackRecord) {
        int i2 = 0;
        for (Op op = backStackRecord.c; op != null; op = op.a) {
            if (op.i != null) {
                i2 += op.i.size();
            }
        }
        this.a = new int[((backStackRecord.e * 7) + i2)];
        if (!backStackRecord.l) {
            throw new IllegalStateException("Not on back stack");
        }
        int i3 = 0;
        for (Op op2 = backStackRecord.c; op2 != null; op2 = op2.a) {
            int i4 = i3 + 1;
            this.a[i3] = op2.c;
            int i5 = i4 + 1;
            this.a[i4] = op2.d != null ? op2.d.mIndex : -1;
            int i6 = i5 + 1;
            this.a[i5] = op2.e;
            int i7 = i6 + 1;
            this.a[i6] = op2.f;
            int i8 = i7 + 1;
            this.a[i7] = op2.g;
            int i9 = i8 + 1;
            this.a[i8] = op2.h;
            if (op2.i != null) {
                int size = op2.i.size();
                int i10 = i9 + 1;
                this.a[i9] = size;
                int i11 = 0;
                while (i11 < size) {
                    this.a[i10] = op2.i.get(i11).mIndex;
                    i11++;
                    i10++;
                }
                i3 = i10;
            } else {
                this.a[i9] = 0;
                i3 = i9 + 1;
            }
        }
        this.b = backStackRecord.j;
        this.c = backStackRecord.k;
        this.d = backStackRecord.n;
        this.e = backStackRecord.p;
        this.f = backStackRecord.q;
        this.g = backStackRecord.r;
        this.h = backStackRecord.s;
        this.i = backStackRecord.t;
        this.j = backStackRecord.u;
        this.k = backStackRecord.v;
    }

    public BackStackState(Parcel parcel) {
        this.a = parcel.createIntArray();
        this.b = parcel.readInt();
        this.c = parcel.readInt();
        this.d = parcel.readString();
        this.e = parcel.readInt();
        this.f = parcel.readInt();
        this.g = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.h = parcel.readInt();
        this.i = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.j = parcel.createStringArrayList();
        this.k = parcel.createStringArrayList();
    }

    public final BackStackRecord a(FragmentManagerImpl fragmentManagerImpl) {
        BackStackRecord backStackRecord = new BackStackRecord(fragmentManagerImpl);
        int i2 = 0;
        int i3 = 0;
        while (i2 < this.a.length) {
            Op op = new Op();
            int i4 = i2 + 1;
            op.c = this.a[i2];
            if (FragmentManagerImpl.a) {
                StringBuilder sb = new StringBuilder("Instantiate ");
                sb.append(backStackRecord);
                sb.append(" op #");
                sb.append(i3);
                sb.append(" base fragment #");
                sb.append(this.a[i4]);
            }
            int i5 = i4 + 1;
            int i6 = this.a[i4];
            if (i6 >= 0) {
                op.d = fragmentManagerImpl.f.get(i6);
            } else {
                op.d = null;
            }
            int i7 = i5 + 1;
            op.e = this.a[i5];
            int i8 = i7 + 1;
            op.f = this.a[i7];
            int i9 = i8 + 1;
            op.g = this.a[i8];
            int i10 = i9 + 1;
            op.h = this.a[i9];
            int i11 = i10 + 1;
            int i12 = this.a[i10];
            if (i12 > 0) {
                op.i = new ArrayList<>(i12);
                int i13 = 0;
                while (i13 < i12) {
                    if (FragmentManagerImpl.a) {
                        StringBuilder sb2 = new StringBuilder("Instantiate ");
                        sb2.append(backStackRecord);
                        sb2.append(" set remove fragment #");
                        sb2.append(this.a[i11]);
                    }
                    op.i.add(fragmentManagerImpl.f.get(this.a[i11]));
                    i13++;
                    i11++;
                }
            }
            i2 = i11;
            backStackRecord.a(op);
            i3++;
        }
        backStackRecord.j = this.b;
        backStackRecord.k = this.c;
        backStackRecord.n = this.d;
        backStackRecord.p = this.e;
        backStackRecord.l = true;
        backStackRecord.q = this.f;
        backStackRecord.r = this.g;
        backStackRecord.s = this.h;
        backStackRecord.t = this.i;
        backStackRecord.u = this.j;
        backStackRecord.v = this.k;
        backStackRecord.a(1);
        return backStackRecord;
    }

    public final void writeToParcel(Parcel parcel, int i2) {
        parcel.writeIntArray(this.a);
        parcel.writeInt(this.b);
        parcel.writeInt(this.c);
        parcel.writeString(this.d);
        parcel.writeInt(this.e);
        parcel.writeInt(this.f);
        TextUtils.writeToParcel(this.g, parcel, 0);
        parcel.writeInt(this.h);
        TextUtils.writeToParcel(this.i, parcel, 0);
        parcel.writeStringList(this.j);
        parcel.writeStringList(this.k);
    }
}
