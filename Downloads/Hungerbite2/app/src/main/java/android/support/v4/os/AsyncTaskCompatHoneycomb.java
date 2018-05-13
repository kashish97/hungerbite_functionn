package android.support.v4.os;

import android.os.AsyncTask;

/**
 * Implementation of AsyncTask compatibility that can call Honeycomb APIs.
 */
class AsyncTaskCompatHoneycomb {

    static <Params, Progress, Result> void executeParallel(
            AsyncTask<Params, Progress, Result> task, Params... params) {
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
    }

}