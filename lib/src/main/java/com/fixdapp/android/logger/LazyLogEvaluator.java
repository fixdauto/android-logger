package com.fixdapp.android.logger;

import java.util.Date;

/**
 * Created by cjk on 3/11/17.
 */
final class LazyLogEvaluator {

    private boolean hasEvaluated = false;
    private Object[] args;

    private String threadName;
    private String message;
    private Date date;

    LazyLogEvaluator(Object[] args) {
        this.args = args;
    }

    void evaluateIfNecessary() {
        if (hasEvaluated) {
            return;
        }
        hasEvaluated = true;
        date = new Date();
        threadName = Thread.currentThread().getName();
        if (args.length == 0) {
            message = "";
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            sb.append(args[i]);
            if (i != args.length - 1) {
                sb.append(" ");
            }
        }
        message = sb.toString();
    }

    String getMessage() {
        return message;
    }

    Date getDate() {
        return date;
    }

    String getThreadName() {
        return threadName;
    }
}
