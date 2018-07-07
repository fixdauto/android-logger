package com.fixdapp.android.logger;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by cjk on 3/12/17.
 */

public class NoOpLogger extends Logger {

    @Override
    public void v(@Nullable Object... args) {

    }

    @Override
    public void v(@NonNull Throwable t, @Nullable Object... args) {

    }

    @Override
    public void d(@Nullable Object... args) {

    }

    @Override
    public void d(@NonNull Throwable t, @Nullable Object... args) {

    }

    @Override
    public void i(@Nullable Object... args) {

    }

    @Override
    public void i(@NonNull Throwable t, @Nullable Object... args) {

    }

    @Override
    public void w(@Nullable Object... args) {

    }

    @Override
    public void w(@NonNull Throwable t, @Nullable Object... args) {

    }

    @Override
    public void e(@Nullable Object... args) {

    }

    @Override
    public void e(@NonNull Throwable t, @Nullable Object... args) {

    }

    @Override
    public void wtf(@Nullable Object... args) {

    }

    @Override
    public void wtf(@NonNull Throwable t, @Nullable Object... args) {

    }

    @Override
    public void log(int priority, @Nullable Object... args) {

    }

    @Override
    public void log(int priority, @NonNull Throwable t, @Nullable Object... args) {

    }

    @Override
    public Logger tag(String tag) {
        return this;
    }

    @Override
    String[] getIgnoredPackagePrefixes() {
        return new String[0];
    }
}
