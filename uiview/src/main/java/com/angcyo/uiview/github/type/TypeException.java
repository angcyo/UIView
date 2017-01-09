package com.angcyo.uiview.github.type;

import android.annotation.TargetApi;
import android.os.Build;

public class TypeException extends RuntimeException {
    public TypeException() {
    }

    public TypeException(String message) {
        super(message);
    }

    public TypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public TypeException(Throwable cause) {
        super(cause);
    }

    @TargetApi(Build.VERSION_CODES.N)
    public TypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}