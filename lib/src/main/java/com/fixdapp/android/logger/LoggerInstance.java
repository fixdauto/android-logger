package com.fixdapp.android.logger;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by cjk on 3/11/17.
 */

final class LoggerInstance extends Logger {

    private LogManager manager;
    private String tag;

    LoggerInstance(LogManager manager, @Nullable String tag) {
        this.manager = manager;
        this.tag = tag;
    }

    @Override
    String[] getIgnoredPackagePrefixes() {
        return manager.getIgnoredPackagePrefixes();
    }

    @Override
    public void v(Object... args) {
        manager.handleLog(getTag(), Log.VERBOSE, null, args);
    }

    @Override
    public void v(@NonNull Throwable t, Object... args) {
        manager.handleLog(getTag(), Log.VERBOSE, t, args);
    }

    @Override
    public void d(Object... args) {
        manager.handleLog(getTag(), Log.DEBUG, null, args);
    }

    @Override
    public void d(@NonNull Throwable t, Object... args) {
        manager.handleLog(getTag(), Log.DEBUG, t, args);
    }

    @Override
    public void i(Object... args) {
        manager.handleLog(getTag(), Log.INFO, null, args);
    }

    @Override
    public void i(@NonNull Throwable t, Object... args) {
        manager.handleLog(getTag(), Log.INFO, t, args);
    }

    @Override
    public void w(Object... args) {
        manager.handleLog(getTag(), Log.WARN, null, args);
    }

    @Override
    public void w(@NonNull Throwable t, Object... args) {
        manager.handleLog(getTag(), Log.WARN, t, args);
    }

    @Override
    public void e(Object... args) {
        manager.handleLog(getTag(), Log.ERROR, null, args);
    }

    @Override
    public void e(@NonNull Throwable t, Object... args) {
        manager.handleLog(getTag(), Log.ERROR, t, args);
    }

    @Override
    public void wtf(Object... args) {
        manager.handleLog(getTag(), Log.ASSERT, null, args);
    }

    @Override
    public void wtf(@NonNull Throwable t, Object... args) {
        manager.handleLog(getTag(), Log.ASSERT, t, args);
    }

    @Override
    public void log(int priority, @Nullable Object... args) {
        manager.handleLog(getTag(), priority, null, args);
    }

    @Override
    public void log(int priority, @NonNull Throwable t, @Nullable Object... args) {
        manager.handleLog(getTag(), priority, t, args);
    }

    @Override
    public Logger tag(@NonNull String tag) {
        if(tag.equals(this.tag)) {
            return this;
        }
        return new LoggerInstance(manager, tag);
    }

    String getTag() {
        if (tag == null) {
            tag = getCaller();
        }
        return tag;
    }

    private String getCaller() {
        StackTraceElement[] stackTraceElements = new Throwable().getStackTrace();
        for (int i = 0; i < stackTraceElements.length; i++) {
            StackTraceElement e = stackTraceElements[i];
            String className = e.getClassName();
            if (className == null) {
                continue;
            }
            if (className.startsWith("com.fixdapp.android.logger")) {
                continue;
            }
            boolean isIgnored = false;
            String[] ignoredPackagePrefixes = getIgnoredPackagePrefixes();
            for (int j = 0; j < ignoredPackagePrefixes.length; j++) {
                if (className.startsWith(ignoredPackagePrefixes[i])) {
                    isIgnored = true;
                    break;
                }
            }
            if (isIgnored) {
                continue;
            }
            return e.getFileName() + ":" + e.getLineNumber();
        }
        return null;
    }
}
