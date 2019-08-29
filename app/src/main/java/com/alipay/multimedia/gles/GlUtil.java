package com.alipay.multimedia.gles;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;
import com.alipay.alipaylogger.Log;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class GlUtil {
    public static final float[] IDENTITY_MATRIX;
    public static final float[] IDENTITY_MATRIX_180;
    public static final float[] IDENTITY_MATRIX_270;
    public static final float[] IDENTITY_MATRIX_90;
    public static final String TAG = "Alipay";
    private static int a = 0;
    private static float b = 20.0f;

    static {
        float[] fArr = new float[16];
        IDENTITY_MATRIX = fArr;
        Matrix.setIdentityM(fArr, 0);
        float[] fArr2 = new float[16];
        IDENTITY_MATRIX_90 = fArr2;
        Matrix.setIdentityM(fArr2, 0);
        float[] fArr3 = new float[16];
        IDENTITY_MATRIX_180 = fArr3;
        Matrix.setIdentityM(fArr3, 0);
        float[] fArr4 = new float[16];
        IDENTITY_MATRIX_270 = fArr4;
        Matrix.setIdentityM(fArr4, 0);
        float[] fArr5 = IDENTITY_MATRIX_90;
        IDENTITY_MATRIX_90[5] = 0.0f;
        fArr5[0] = 0.0f;
        IDENTITY_MATRIX_90[1] = -1.0f;
        IDENTITY_MATRIX_90[4] = 1.0f;
        float[] fArr6 = IDENTITY_MATRIX_180;
        IDENTITY_MATRIX_180[5] = -1.0f;
        fArr6[0] = -1.0f;
        float[] fArr7 = IDENTITY_MATRIX_270;
        IDENTITY_MATRIX_270[5] = 0.0f;
        fArr7[0] = 0.0f;
        IDENTITY_MATRIX_270[1] = 1.0f;
        IDENTITY_MATRIX_270[4] = -1.0f;
    }

    private GlUtil() {
    }

    public static int createProgram(String vertexSource, String fragmentSource) {
        int vertexShader = loadShader(35633, vertexSource);
        if (vertexShader == 0) {
            return 0;
        }
        int pixelShader = loadShader(35632, fragmentSource);
        if (pixelShader == 0) {
            return 0;
        }
        int program = GLES20.glCreateProgram();
        checkGlError("glCreateProgram");
        if (program == 0) {
            Log.d(TAG, "Could not create program");
        }
        GLES20.glAttachShader(program, vertexShader);
        checkGlError("glAttachShader");
        GLES20.glAttachShader(program, pixelShader);
        checkGlError("glAttachShader");
        GLES20.glLinkProgram(program);
        int[] linkStatus = new int[1];
        GLES20.glGetProgramiv(program, 35714, linkStatus, 0);
        if (linkStatus[0] == 1) {
            return program;
        }
        Log.d(TAG, "Could not link program: ");
        Log.d(TAG, GLES20.glGetProgramInfoLog(program));
        GLES20.glDeleteProgram(program);
        return 0;
    }

    public static int loadShader(int shaderType, String source) {
        int shader = GLES20.glCreateShader(shaderType);
        checkGlError("glCreateShader type=" + shaderType);
        GLES20.glShaderSource(shader, source);
        GLES20.glCompileShader(shader);
        int[] compiled = new int[1];
        GLES20.glGetShaderiv(shader, 35713, compiled, 0);
        if (compiled[0] != 0) {
            return shader;
        }
        Log.e(TAG, "Could not compile shader " + shaderType + ":");
        Log.e(TAG, new StringBuilder(Token.SEPARATOR).append(GLES20.glGetShaderInfoLog(shader)).toString());
        GLES20.glDeleteShader(shader);
        return 0;
    }

    public static void checkGlError(String op) {
        int error = GLES20.glGetError();
        if (error != 0) {
            String msg = op + ": glError 0x" + Integer.toHexString(error);
            Log.d(TAG, msg);
            throw new RuntimeException(msg);
        }
    }

    public static void checkLocation(int location, String label) {
        if (location < 0) {
            throw new RuntimeException("Unable to locate '" + label + "' in program");
        }
    }

    public static int createImageTexture(ByteBuffer data, int width, int height, int format) {
        int[] textureHandles = new int[1];
        GLES20.glGenTextures(1, textureHandles, 0);
        int textureHandle = textureHandles[0];
        checkGlError("glGenTextures");
        GLES20.glBindTexture(3553, textureHandle);
        GLES20.glTexParameteri(3553, 10241, 9729);
        GLES20.glTexParameteri(3553, 10240, 9729);
        checkGlError("loadImageTexture");
        GLES20.glTexImage2D(3553, 0, format, width, height, 0, format, 5121, data);
        checkGlError("loadImageTexture");
        return textureHandle;
    }

    public static FloatBuffer createFloatBuffer(float[] coords) {
        ByteBuffer bb = ByteBuffer.allocateDirect(coords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        FloatBuffer fb = bb.asFloatBuffer();
        fb.put(coords);
        fb.position(0);
        return fb;
    }

    public static void logVersionInfo() {
        Log.d(TAG, "vendor  : " + GLES20.glGetString(7936));
        Log.d(TAG, "renderer: " + GLES20.glGetString(7937));
        Log.d(TAG, "version : " + GLES20.glGetString(7938));
    }

    public static int getDefaltTriangleOffsetY(Context context) {
        if (a == 0) {
            a = Dp2Px(context, b);
        }
        return a;
    }

    public static int Dp2Px(Context context, float dp) {
        return (int) ((dp * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static float[] getRotateMatrix(int degree) {
        switch (degree) {
            case 0:
                return IDENTITY_MATRIX;
            case 90:
                return IDENTITY_MATRIX_90;
            case 180:
                return IDENTITY_MATRIX_180;
            case 270:
                return IDENTITY_MATRIX_270;
            default:
                throw new IllegalArgumentException("unsupported degree: " + degree + ", only support: 0, 90, 180, 270");
        }
    }

    public static ByteBuffer createByteBuffer(byte[] coords) {
        ByteBuffer bb = ByteBuffer.allocateDirect(coords.length * 1);
        bb.order(ByteOrder.nativeOrder());
        bb.put(coords);
        bb.position(0);
        return bb;
    }
}
