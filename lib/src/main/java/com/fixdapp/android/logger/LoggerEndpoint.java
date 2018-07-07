package com.fixdapp.android.logger;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.text.DateFormat;

/**
 * Created by cjk on 3/11/17.
 */

public interface LoggerEndpoint {

    /**
     * The minimum priority level required to log to this endpoint.
     * @return one of {@link android.util.Log}.* values (e.g. `Log.INFO`).
     */
    int getMinimumPriority();

    /**
     * Used to check if this endpoint should not log logs with a particular tag.
     * @param tag the tag of the log
     * @return `true` if the endpoint should ignore this tag
     */
    boolean ignoreTag(@NonNull String tag);

    /**
     * Called to check if this endpoint is accepting logs
     * @return `true` if this endpoint can log logs
     */
    boolean isEnabled();

    /**
     * The formatter to use to create date strings. If null, will use `"yyyy-MM-dd'T'HH:mm:ss.SSSZ"`
     * @return a date formatter, or null for the default
     */
    @Nullable
    DateFormat getDateFormat();

    /**
     * Called when the endpoint should handle a log.
     * @param tag the tag of the log (if set), or the filename and line number if not
     * @param priority the priority of the log
     * @param dateString a string representation of the date the log occurred. Created using {@link #getDateFormat()}
     * @param threadName the name of the thread which launched log
     * @param message the log's content
     * @param throwable an optional error included with the log
     */
    void log(@Nullable String tag, int priority, @NonNull String dateString,
             @NonNull String threadName, @NonNull String message, @Nullable Throwable throwable);
}
