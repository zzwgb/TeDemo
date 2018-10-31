package com.example.administrator.tedemo;

import android.os.SystemClock;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class LogUtils {

    private static String TAG = "fan";

    private static boolean isShow = true;

    public static boolean DEBUG = Log.isLoggable(TAG, Log.VERBOSE);

    public static void setTag(String tag) {
        d("Changing log tag to %s", tag);
        TAG = tag;

        DEBUG = Log.isLoggable(TAG, Log.VERBOSE);
    }

    public static void v(String msg) {
        if (DEBUG) {
            if (isShow) {
                Log.v(TAG, buildMessage(msg));
            }
        }
    }

    public static void d(String msg) {
        if (isShow) {
            Log.d(TAG, buildMessage(msg));
        }
    }

    public static void e(String msg) {
        if (isShow) {
            Log.e(TAG, buildMessage(msg));
        }
    }

    public static void e(Throwable tr, String msg) {
        if (isShow) {
            Log.e(TAG, buildMessage(msg), tr);
        }
    }

    public static void wtf(String msg) {
        if (isShow) {
            Log.wtf(TAG, buildMessage(msg));
        }
    }

    public static void wtf(Throwable tr, String msg) {
        if (isShow) {
            Log.wtf(TAG, buildMessage(msg), tr);
        }
    }

    private static void v(String format, Object... args) {
        if (DEBUG) {
            if (isShow) {
                Log.v(TAG, buildMessage(format, args));
            }
        }
    }

    private static void d(String format, Object... args) {
        if (isShow) {
            Log.d(TAG, buildMessage(format, args));
        }
    }

    private static void e(String format, Object... args) {
        if (isShow) {
            Log.e(TAG, buildMessage(format, args));
        }
    }

    private static void e(Throwable tr, String format, Object... args) {
        if (isShow) {
            Log.e(TAG, buildMessage(format, args), tr);
        }
    }

    private static void wtf(String format, Object... args) {
        if (isShow) {
            Log.wtf(TAG, buildMessage(format, args));
        }
    }

    private static void wtf(Throwable tr, String format, Object... args) {
        if (isShow) {
            Log.wtf(TAG, buildMessage(format, args), tr);
        }
    }

    /**
     * Formats the caller's provided message and prepends useful info like
     * calling thread ID and method name.
     */
    private static String buildMessage(String format, Object... args) {
        String msg = (args == null) ? format : String.format(Locale.US, format, args);
        StackTraceElement[] trace = new Throwable().fillInStackTrace().getStackTrace();

        String caller = "<unknown>";
        // Walk up the stack looking for the first caller outside of VolleyLog.
        // It will be at least two frames up, so start there.
        for (int i = 2; i < trace.length; i++) {
            Class<?> clazz = trace[i].getClass();
            if (!clazz.equals(LogUtils.class)) {
                String callingClass = trace[i].getClassName();
                callingClass = callingClass.substring(callingClass.lastIndexOf('.') + 1);
                callingClass = callingClass.substring(callingClass.lastIndexOf('$') + 1);

                caller = callingClass + "." + trace[i].getMethodName();
                break;
            }
        }
        
        return String.format(Locale.US, "[%d]:time=%s %s: %s",
                Thread.currentThread().getId(), getCurrentTime(),caller, msg);
    }

    /**
     * Formats the caller's provided message and prepends useful info like
     * calling thread ID and method name.
     */
    private static String buildMessage(String msg) {
        StackTraceElement[] trace = new Throwable().fillInStackTrace().getStackTrace();

        String caller = "<unknown>";
        // Walk up the stack looking for the first caller outside of VolleyLog.
        // It will be at least two frames up, so start there.
        for (int i = 2; i < trace.length; i++) {
            Class<?> clazz = trace[i].getClass();
            if (!clazz.equals(LogUtils.class)) {
                String callingClass = trace[i].getClassName();
                callingClass = callingClass.substring(callingClass.lastIndexOf('.') + 1);
                callingClass = callingClass.substring(callingClass.lastIndexOf('$') + 1);

                caller = callingClass + "." + trace[i].getMethodName();
                break;
            }
        }

        return String.format(Locale.US, "[%d]:时间：%s 位置：%s 内容：%s",
                Thread.currentThread().getId(), getCurrentTime(),caller, msg);
    }

    /**
     * A simple event log with records containing a name, thread ID, and timestamp.
     */
    static class MarkerLog {
        public static final boolean ENABLED = LogUtils.DEBUG;

        /** Minimum duration from first marker to last in an marker log to warrant logging. */
        private static final long MIN_DURATION_FOR_LOGGING_MS = 0;

        private static class Marker {
            public final String name;
            public final long thread;
            public final long time;

            public Marker(String name, long thread, long time) {
                this.name = name;
                this.thread = thread;
                this.time = time;
            }
        }

        private final List<Marker> mMarkers = new ArrayList<Marker>();
        private boolean mFinished = false;

        /** Adds a marker to this log with the specified name. */
        public synchronized void add(String name, long threadId) {
            if (mFinished) {
                throw new IllegalStateException("Marker added to finished log");
            }

            mMarkers.add(new Marker(name, threadId, SystemClock.elapsedRealtime()));
        }

        /**
         * Closes the log, dumping it to logcat if the time difference between
         * the first and last markers is greater than {@link #MIN_DURATION_FOR_LOGGING_MS}.
         * @param header Header string to print above the marker log.
         */
        public synchronized void finish(String header) {
            mFinished = true;

            long duration = getTotalDuration();
            if (duration <= MIN_DURATION_FOR_LOGGING_MS) {
                return;
            }

            long prevTime = mMarkers.get(0).time;
            d("(%-4d ms) %s", duration, header);
            for (Marker marker : mMarkers) {
                long thisTime = marker.time;
                d("(+%-4d) [%2d] %s", (thisTime - prevTime), marker.thread, marker.name);
                prevTime = thisTime;
            }
        }

        @Override
        protected void finalize() throws Throwable {
            // Catch requests that have been collected (and hence end-of-lifed)
            // but had no debugging output printed for them.
            if (!mFinished) {
                finish("Request on the loose");
                e("Marker log finalized without finish() - uncaught exit point for request");
            }
        }

        /** Returns the time difference between the first and last events in this log. */
        private long getTotalDuration() {
            if (mMarkers.size() == 0) {
                return 0;
            }

            long first = mMarkers.get(0).time;
            long last = mMarkers.get(mMarkers.size() - 1).time;
            return last - first;
        }
    }

    /**
     * 返回当前时间的格式为 yyyy-MM-dd HH:mm:ss
     * @return
     */
    private static String  getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        return sdf.format(System.currentTimeMillis());
    }

}