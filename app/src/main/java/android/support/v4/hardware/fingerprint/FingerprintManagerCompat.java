package android.support.v4.hardware.fingerprint;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.os.CancellationSignal;
import java.security.Signature;
import javax.crypto.Cipher;
import javax.crypto.Mac;

public class FingerprintManagerCompat {
    static final FingerprintManagerCompatImpl IMPL;
    private Context mContext;

    static class Api23FingerprintManagerCompatImpl implements FingerprintManagerCompatImpl {
        public final boolean a(Context context) {
            return FingerprintManagerCompatApi23.hasEnrolledFingerprints(context);
        }

        public final boolean b(Context context) {
            return FingerprintManagerCompatApi23.isHardwareDetected(context);
        }

        /* JADX WARNING: Removed duplicated region for block: B:14:0x0037  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void a(android.content.Context r9, android.support.v4.hardware.fingerprint.FingerprintManagerCompat.CryptoObject r10, int r11, android.support.v4.os.CancellationSignal r12, final android.support.v4.hardware.fingerprint.FingerprintManagerCompat.AuthenticationCallback r13, android.os.Handler r14) {
            /*
                r8 = this;
                r0 = 0
                if (r10 == 0) goto L_0x0034
                javax.crypto.Cipher r1 = r10.getCipher()
                if (r1 == 0) goto L_0x0014
                android.support.v4.hardware.fingerprint.FingerprintManagerCompatApi23$CryptoObject r1 = new android.support.v4.hardware.fingerprint.FingerprintManagerCompatApi23$CryptoObject
                javax.crypto.Cipher r10 = r10.getCipher()
                r1.<init>(r10)
            L_0x0012:
                r3 = r1
                goto L_0x0035
            L_0x0014:
                java.security.Signature r1 = r10.getSignature()
                if (r1 == 0) goto L_0x0024
                android.support.v4.hardware.fingerprint.FingerprintManagerCompatApi23$CryptoObject r1 = new android.support.v4.hardware.fingerprint.FingerprintManagerCompatApi23$CryptoObject
                java.security.Signature r10 = r10.getSignature()
                r1.<init>(r10)
                goto L_0x0012
            L_0x0024:
                javax.crypto.Mac r1 = r10.getMac()
                if (r1 == 0) goto L_0x0034
                android.support.v4.hardware.fingerprint.FingerprintManagerCompatApi23$CryptoObject r1 = new android.support.v4.hardware.fingerprint.FingerprintManagerCompatApi23$CryptoObject
                javax.crypto.Mac r10 = r10.getMac()
                r1.<init>(r10)
                goto L_0x0012
            L_0x0034:
                r3 = r0
            L_0x0035:
                if (r12 == 0) goto L_0x003b
                java.lang.Object r0 = r12.getCancellationSignalObject()
            L_0x003b:
                r5 = r0
                android.support.v4.hardware.fingerprint.FingerprintManagerCompat$Api23FingerprintManagerCompatImpl$1 r6 = new android.support.v4.hardware.fingerprint.FingerprintManagerCompat$Api23FingerprintManagerCompatImpl$1
                r6.<init>(r13)
                r2 = r9
                r4 = r11
                r7 = r14
                android.support.v4.hardware.fingerprint.FingerprintManagerCompatApi23.authenticate(r2, r3, r4, r5, r6, r7)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v4.hardware.fingerprint.FingerprintManagerCompat.Api23FingerprintManagerCompatImpl.a(android.content.Context, android.support.v4.hardware.fingerprint.FingerprintManagerCompat$CryptoObject, int, android.support.v4.os.CancellationSignal, android.support.v4.hardware.fingerprint.FingerprintManagerCompat$AuthenticationCallback, android.os.Handler):void");
        }

        static /* synthetic */ CryptoObject a(android.support.v4.hardware.fingerprint.FingerprintManagerCompatApi23.CryptoObject cryptoObject) {
            if (cryptoObject != null) {
                if (cryptoObject.getCipher() != null) {
                    return new CryptoObject(cryptoObject.getCipher());
                }
                if (cryptoObject.getSignature() != null) {
                    return new CryptoObject(cryptoObject.getSignature());
                }
                if (cryptoObject.getMac() != null) {
                    return new CryptoObject(cryptoObject.getMac());
                }
            }
            return null;
        }
    }

    public static abstract class AuthenticationCallback {
        public void onAuthenticationError(int i, CharSequence charSequence) {
        }

        public void onAuthenticationFailed() {
        }

        public void onAuthenticationHelp(int i, CharSequence charSequence) {
        }

        public void onAuthenticationSucceeded(AuthenticationResult authenticationResult) {
        }
    }

    public static final class AuthenticationResult {
        private CryptoObject mCryptoObject;

        public AuthenticationResult(CryptoObject cryptoObject) {
            this.mCryptoObject = cryptoObject;
        }

        public final CryptoObject getCryptoObject() {
            return this.mCryptoObject;
        }
    }

    public static class CryptoObject {
        private final Cipher mCipher;
        private final Mac mMac;
        private final Signature mSignature;

        public CryptoObject(Signature signature) {
            this.mSignature = signature;
            this.mCipher = null;
            this.mMac = null;
        }

        public CryptoObject(Cipher cipher) {
            this.mCipher = cipher;
            this.mSignature = null;
            this.mMac = null;
        }

        public CryptoObject(Mac mac) {
            this.mMac = mac;
            this.mCipher = null;
            this.mSignature = null;
        }

        public Signature getSignature() {
            return this.mSignature;
        }

        public Cipher getCipher() {
            return this.mCipher;
        }

        public Mac getMac() {
            return this.mMac;
        }
    }

    interface FingerprintManagerCompatImpl {
        void a(Context context, CryptoObject cryptoObject, int i, CancellationSignal cancellationSignal, AuthenticationCallback authenticationCallback, Handler handler);

        boolean a(Context context);

        boolean b(Context context);
    }

    static class LegacyFingerprintManagerCompatImpl implements FingerprintManagerCompatImpl {
        public final void a(Context context, CryptoObject cryptoObject, int i, CancellationSignal cancellationSignal, AuthenticationCallback authenticationCallback, Handler handler) {
        }

        public final boolean a(Context context) {
            return false;
        }

        public final boolean b(Context context) {
            return false;
        }
    }

    public static FingerprintManagerCompat from(Context context) {
        return new FingerprintManagerCompat(context);
    }

    private FingerprintManagerCompat(Context context) {
        this.mContext = context;
    }

    static {
        if (VERSION.SDK_INT >= 23) {
            IMPL = new Api23FingerprintManagerCompatImpl();
        } else {
            IMPL = new LegacyFingerprintManagerCompatImpl();
        }
    }

    public boolean hasEnrolledFingerprints() {
        return IMPL.a(this.mContext);
    }

    public boolean isHardwareDetected() {
        return IMPL.b(this.mContext);
    }

    public void authenticate(@Nullable CryptoObject cryptoObject, int i, @Nullable CancellationSignal cancellationSignal, @NonNull AuthenticationCallback authenticationCallback, @Nullable Handler handler) {
        IMPL.a(this.mContext, cryptoObject, i, cancellationSignal, authenticationCallback, handler);
    }
}
