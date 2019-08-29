package com.alipay.mobile.beehive.capture.magicscan.utils;

import android.opengl.GLES20;
import com.alipay.mobile.beehive.capture.utils.Logger;

public class ShaderUtil {
    private static final String TAG = "ShaderUtil";

    public static int createProgram(String vertexSource, String fragmentSource) {
        int vertexShader = loadShader(35633, vertexSource);
        if (vertexShader == 0) {
            return 0;
        }
        checkGlError("createProgram-1");
        int pixelShader = loadShader(35632, fragmentSource);
        if (pixelShader == 0) {
            return 0;
        }
        checkGlError("createProgram-2");
        int program = GLES20.glCreateProgram();
        if (program == 0) {
            return program;
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
        Logger.e("ES20_ERROR", "Could not link program: ");
        Logger.e("ES20_ERROR", GLES20.glGetProgramInfoLog(program));
        GLES20.glDeleteProgram(program);
        return 0;
    }

    private static int loadShader(int shaderType, String source) {
        int shader = GLES20.glCreateShader(shaderType);
        checkGlError("loadShader-1, type=" + shaderType);
        if (shader == 0) {
            return shader;
        }
        GLES20.glShaderSource(shader, source);
        checkGlError("loadShader-2, type=" + shaderType);
        GLES20.glCompileShader(shader);
        checkGlError("loadShader-3, type=" + shaderType);
        int[] compiled = new int[1];
        GLES20.glGetShaderiv(shader, 35713, compiled, 0);
        checkGlError("loadShader-4, type=" + shaderType);
        if (compiled[0] != 0) {
            return shader;
        }
        Logger.e("ES20_ERROR", "Could not compile shader " + shaderType);
        Logger.e("ES20_ERROR", GLES20.glGetShaderInfoLog(shader));
        GLES20.glDeleteShader(shader);
        return 0;
    }

    public static void checkGlError(String suffix) {
        String result;
        while (true) {
            int err = GLES20.glGetError();
            if (err != 0) {
                if (err == 1280) {
                    result = "Render ERROR INVALID_ENUM in ";
                } else if (err == 1281) {
                    result = "Render ERROR INVALID_VALUE in ";
                } else if (err == 1282) {
                    result = "Render ERROR INVALID_OPERATION in ";
                } else if (err == 1285) {
                    result = "Render ERROR OUT_OF_MEMORY in ";
                } else {
                    result = "Unknown ERROR in ";
                }
                Logger.e(TAG, result + suffix);
            } else {
                return;
            }
        }
    }
}
