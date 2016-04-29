package com.github.sharecirclelabs.jocker;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.text.TextUtils;

import com.github.sharecirclelabs.jocker.task.EndpointsAsyncTask;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }


    String mJoke = null;
    Exception mError = null;
    CountDownLatch signal = null;

    @Override
    protected void setUp() throws Exception {
        signal = new CountDownLatch(1);
    }

    @Override
    protected void tearDown() throws Exception {
        signal.countDown();
    }

    public void testAsyncGetJokeTask() throws InterruptedException {
        EndpointsAsyncTask task = new EndpointsAsyncTask();
        task.setListener(new EndpointsAsyncTask.AsyncTaskListener() {
            @Override
            public void onComplete(String joke, Exception e) {
                mJoke = joke;
                mError = e;
                signal.countDown();
            }
        }).execute();

        //Set timeout for call to GCE module
        signal.await(10, TimeUnit.SECONDS);

        assertNull(mError);
        assertFalse(TextUtils.isEmpty(mJoke));
    }
}