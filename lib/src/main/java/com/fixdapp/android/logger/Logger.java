package com.fixdapp.android.logger;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by cjk on 3/11/17.
 */
public abstract class Logger {

    public abstract void v(@Nullable Object... args);
    public abstract void v(@NonNull Throwable t, @Nullable Object... args);

    public abstract void d(@Nullable Object... args);
    public abstract void d(@NonNull Throwable t, @Nullable Object... args);

    public abstract void i(@Nullable Object... args);
    public abstract void i(@NonNull Throwable t, @Nullable Object... args);

    public abstract void w(@Nullable Object... args);
    public abstract void w(@NonNull Throwable t, @Nullable Object... args);

    public abstract void e(@Nullable Object... args);
    public abstract void e(@NonNull Throwable t, @Nullable Object... args);

    public abstract void wtf(@Nullable Object... args);
    public abstract void wtf(@NonNull Throwable t, @Nullable Object... args);

    public abstract void log(int priority, @Nullable Object... args);
    public abstract void log(int priority, @NonNull Throwable t, @Nullable Object... args);

    public abstract Logger tag(String tag);

    abstract String[] getIgnoredPackagePrefixes();

    /**
     * Convert a priority value into a string, e.g. `Log.VERBOSE` -> `"verbose"`.
     * @param priority one of the {@link Log} priority constants
     * @return a lowercase string representation of the priority
     */
    @NonNull
    public static String priorityToString(int priority) {
        switch (priority) {
            case Log.VERBOSE:
                return "verbose";
            case Log.DEBUG:
                return "debug";
            case Log.INFO:
                return "info";
            case Log.WARN:
                return "warn";
            case Log.ERROR:
                return "error";
            case Log.ASSERT:
                return "assert";
            default:
                throw new IllegalArgumentException("Invalid priority: " + priority);
        }
    }

    /**
     * Get the stack trace of a {@link Throwable} as a {@link String} for logging.
     * @param t the throwable in question. If null, will return an empty string.
     * @return a string representation of the stack trace
     */
    @NonNull
    public static String getStackTraceString(@Nullable Throwable t) {
        if (t == null) return "";
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}
