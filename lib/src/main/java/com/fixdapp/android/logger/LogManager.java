package com.fixdapp.android.logger;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

/**
 * Created by cjk on 3/11/17.
 */

public final class LogManager {

    private static DateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US);

    private static LogManager instance;

    @NonNull
    public static LogManager getInstance() {
        if (instance == null) {
            instance = new LogManager();
        }
        return instance;
    }

    @NonNull
    public static Logger getLogger(String tag) {
        return getInstance().getNewLogger(tag);
    }

    @NonNull
    public static Logger getLogger() {
        return getInstance().getNewLogger();
    }

    private ArrayList<LoggerEndpoint> endpoints;
    private String[] ignoredPackagePrefixes;
    private boolean hasAddedEndpoints;
    private boolean hasFlushedCachedLogs;
    private ArrayList<Log> cachedLogs = new ArrayList<>();

    public LogManager(LoggerEndpoint[] endpoints, String[] ignoredPackagePrefixes) {
        this.endpoints = new ArrayList<>(Arrays.asList(endpoints));
        this.ignoredPackagePrefixes = ignoredPackagePrefixes;
        this.hasAddedEndpoints = endpoints.length != 0;
        this.hasFlushedCachedLogs = false;
    }

    public LogManager() {
        this(new LoggerEndpoint[0], new String[0]);
    }

    @NonNull
    public synchronized LogManager addEndpoint(LoggerEndpoint endpoint) {
        endpoints.add(endpoint);
        hasAddedEndpoints = true;
        return this;
    }

    @NonNull
    public synchronized LogManager addEndpoints(LoggerEndpoint... endpoints) {
        this.endpoints.addAll(Arrays.asList(endpoints));
        hasAddedEndpoints = true;
        return this;
    }

    @NonNull
    public synchronized LogManager removeEndpoint(LoggerEndpoint endpoint) {
        endpoints.remove(endpoint);
        return this;
    }

    @NonNull
    public synchronized LogManager clearEndpoints() {
        endpoints.clear();
        hasAddedEndpoints = false;
        return this;
    }

    @NonNull
    public synchronized LogManager setIgnoredPackagePrefixes(String[] ignoredPackagePrefixes) {
        this.ignoredPackagePrefixes = ignoredPackagePrefixes;
        return this;
    }

    @NonNull
    String[] getIgnoredPackagePrefixes() {
        return ignoredPackagePrefixes;
    }

    @NonNull
    public Logger getNewLogger() {
        return getNewLogger(null);
    }

    @NonNull
    public Logger getNewLogger(String tag) {
        return new LoggerInstance(this, tag);
    }

    synchronized void handleLog(@Nullable String tag, int priority, @Nullable Throwable throwable, Object[] args) {
        Log log = new Log(tag, priority, throwable, args);
        if (!hasAddedEndpoints) {
            cachedLogs.add(log);
            return;
        }
        if (!hasFlushedCachedLogs) {
            for(Log l : cachedLogs) {
                l.writeToEndpoints(endpoints, DEFAULT_DATE_FORMAT);
            }
            cachedLogs.clear();
            hasFlushedCachedLogs = true;
        }
        log.writeToEndpoints(endpoints, DEFAULT_DATE_FORMAT);
    }
}
