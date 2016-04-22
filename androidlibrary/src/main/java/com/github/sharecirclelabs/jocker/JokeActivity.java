package com.github.sharecirclelabs.jocker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {

    public static String JOKE_KEY = "Joke key";

    public static Intent newIntent(Context packageContext, String joke) {
        Intent intent = new Intent(packageContext, JokeActivity.class);
        intent.putExtra(JokeActivity.JOKE_KEY, joke);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);
        Intent intent = getIntent();
        String joke = intent.getStringExtra(JokeActivity.JOKE_KEY);
        TextView jokeTextView = (TextView) findViewById(R.id.joke_textview);
        if (jokeTextView != null && joke != null && joke.length() != 0) {
            jokeTextView.setText(joke);
        }
    }
}
