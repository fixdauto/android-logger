package com.fixdapp.android.logger;

import android.support.annotation.Nullable;

import java.text.DateFormat;
import java.util.List;

/**
 * Created by cjk on 2/6/18.
 *
 * A container for individual log lines.
 */
class Log {

    @Nullable
    private String tag;
    private int priority;
    @Nullable
    private Throwable throwable;
    private LazyLogEvaluator evaluator;

    Log(@Nullable String tag, int priority, @Nullable Throwable throwable, Object[] args) {
        this.tag = tag;
        this.priority = priority;
        this.throwable = throwable;
        this.evaluator = new LazyLogEvaluator(args);
    }

    void writeToEndpoints(List<LoggerEndpoint> endpoints, DateFormat defaultDateFormat) {
        for (int i = 0; i < endpoints.size(); i++) {
            LoggerEndpoint endpoint = endpoints.get(i);
            if (endpoint.isEnabled() && priority >= endpoint.getMinimumPriority()
                    && (tag == null || !endpoint.ignoreTag(tag))) {
                evaluator.evaluateIfNecessary();
                DateFormat df = endpoint.getDateFormat();
                if (df == null) {
                    df = defaultDateFormat;
                }
                String dateString = df.format(evaluator.getDate());
                endpoint.log(tag, priority, dateString, evaluator.getThreadName(),
                        evaluator.getMessage(), throwable);
            }
        }
    }
}
