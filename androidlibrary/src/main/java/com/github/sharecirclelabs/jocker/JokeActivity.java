package com.github.sharecirclelabs.jocker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.github.sharecirclelabs.jocker.task.EndpointsAsyncTask;

public class JokeActivity extends AppCompatActivity {

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, JokeActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        EndpointsAsyncTask task = new EndpointsAsyncTask();
        task.setListener(new EndpointsAsyncTask.AsyncTaskListener() {
            @Override
            public void onComplete(String joke, Exception e) {
                TextView jokeTextView = (TextView) findViewById(R.id.joke_textview);
                if (jokeTextView != null && joke != null && joke.length() != 0) {
                    jokeTextView.setText(joke);
                }
            }
        }).execute();

    }

}
