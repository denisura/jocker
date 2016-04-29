package com.github.sharecirclelabs.jocker.task;

import android.os.AsyncTask;

import com.github.sharecirclelabs.jocker.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

public class EndpointsAsyncTask extends AsyncTask<String, Void, String> {

    private static MyApi myApiService = null;
    private AsyncTaskListener mListener = null;
    private Exception mError = null;

    public static interface AsyncTaskListener {
        public void onComplete(String jsonString, Exception e);
    }

    @Override
    protected String doInBackground(String... params) {

        String content = null;

        if (myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://192.168.0.189:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver
            myApiService = builder.build();
        }
        try {
            content = myApiService.getJoke().execute().getData();
        } catch (IOException e) {
            mError = e;
        }

        return content;
    }

    public EndpointsAsyncTask setListener(AsyncTaskListener listener) {
        this.mListener = listener;
        return this;
    }

    @Override
    protected void onPostExecute(String s) {
        if (this.mListener != null)
            this.mListener.onComplete(s, mError);
    }

    @Override
    protected void onCancelled() {
        if (this.mListener != null) {
            mError = new InterruptedException("AsyncTask cancelled");
            this.mListener.onComplete(null, mError);
        }
    }
}