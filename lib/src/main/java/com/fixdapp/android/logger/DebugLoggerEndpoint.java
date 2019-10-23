package com.fixdapp.android.logger;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.DateFormat;

/**
 * Created by cjk on 3/11/17.
 * <p>
 * An endpoint which uses Android Logcat ({@link Log}) as it's output.
 */
public class DebugLoggerEndpoint implements LoggerEndpoint {

    private int minimumPriority;
    private DateFormat dateFormat;
    private String[] ignoredTags;

    private boolean enabled = true;

    public DebugLoggerEndpoint(int minimumPriority, @Nullable DateFormat dateFormat,
                               @NonNull String[] ignoredTags) {
        this.minimumPriority = minimumPriority;
        this.dateFormat = dateFormat;
        this.ignoredTags = ignoredTags;
    }

    public DebugLoggerEndpoint() {
        this(Log.VERBOSE, null, new String[0]);
    }

    public DebugLoggerEndpoint(int minimumPriority) {
        this(minimumPriority, null, new String[0]);
    }

    @Override
    public int getMinimumPriority() {
        return minimumPriority;
    }

    @Nullable
    @Override
    public DateFormat getDateFormat() {
        return dateFormat;
    }

    @Override
    public boolean ignoreTag(@NonNull String tag) {
        if (ignoredTags.length == 0) {
            return false;
        }
        for (String ignoredTag : ignoredTags) {
            if (tag.equals(ignoredTag)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public void log(@Nullable String tag, int priority, @NonNull String dateString,
                    @NonNull String threadName, @NonNull String message,
                    @Nullable Throwable throwable) {

        switch (priority) {

            case Log.VERBOSE:
                if (throwable == null) {
                    Log.v(sanitizeTag(tag), message);
                } else {
                    Log.v(sanitizeTag(tag), message, throwable);
                }
                break;

            case Log.DEBUG:
                if (throwable == null) {
                    Log.d(sanitizeTag(tag), message);
                } else {
                    Log.d(sanitizeTag(tag), message, throwable);
                }
                break;

            case Log.INFO:
                if (throwable == null) {
                    Log.i(sanitizeTag(tag), message);
                } else {
                    Log.i(sanitizeTag(tag), message, throwable);
                }
                break;

            case Log.WARN:
                if (throwable == null) {
                    Log.w(sanitizeTag(tag), message);
                } else {
                    Log.w(sanitizeTag(tag), message, throwable);
                }
                break;

            case Log.ERROR:
                if (throwable == null) {
                    Log.e(sanitizeTag(tag), message);
                } else {
                    Log.e(sanitizeTag(tag), message, throwable);
                }
                break;

            case Log.ASSERT:
                if (throwable == null) {
                    Log.wtf(sanitizeTag(tag), message);
                } else {
                    Log.wtf(sanitizeTag(tag), message, throwable);
                }
                break;
        }
    }

    @NonNull
    private String sanitizeTag(@Nullable String tag) {
        if (tag == null) {
            return "";
        }
        // Log tags can't be more than 23 characters long
        if (tag.length() > 23) {
            return tag.substring(0, 23);
        }
        return tag;
    }
}
